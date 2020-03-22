package edu.cnm.deepdive.choppit.service;

import android.os.AsyncTask;
import edu.cnm.deepdive.choppit.model.entity.Ingredient;
import edu.cnm.deepdive.choppit.model.entity.Step;
import io.reactivex.Single;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupRetriever {

  // TODO add any new measurement enum values to the parentheses here.
  public static final String MAGIC_INGREDIENT_REGEX = "^([\\d\\W]*)\\s(tsp|teaspoon|tbsp|tablespoon|oz|ounce|c|cup*)*s??\\b(.*)";
  private Document document;
  private String url;
  private List<String> listRawIngredients = new ArrayList<>();
  private List<String> listInstructions = new ArrayList<>();
  private static final int NETWORK_POOL_SIZE = 10;
  private final Executor networkPool;

  private JsoupRetriever() {
    networkPool = Executors.newFixedThreadPool(NETWORK_POOL_SIZE);
  }

  public static JsoupRetriever getInstance() {
    return InstanceHolder.INSTANCE;
  }

  // TODO send data to database
  public void getData(String url, String ingredient, String instruction) throws IOException {
    this.url = url;
    new GetPage(instruction, ingredient)
        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
  }

  private void processDoc(String ingredient, String instruction, Document document) {
    this.document = document;
    String ingredientClass = getClass(ingredient); // identify wrapper classes
    String instructionClass = getClass(instruction);
    listRawIngredients = getClassContents(ingredientClass); // list all ingredients
    listInstructions = getClassContents(instructionClass); // list all instructions
  }

  private String getClass(String text) {
    Elements e = document.select(String.format("*:containsOwn(%s)", text));
    // TODO error handling (no matching text just returns an empty List).

    return e.get(0).attr("class");
  }

  private List<String> getClassContents(String klass) {
    Elements e = document.getElementsByClass(klass);
    return e.eachText();
  }

  public Single<List<Step>> buildSteps() {
    List<Step> steps = new ArrayList<>();
    for (int i = 0; i < this.listInstructions.size(); i++) {
      Step step = new Step();
      step.setRecipeOrder(i + 1);
      step.setInstructions(this.listInstructions.get(i));
      steps.add(step);
    }
    return Single.just(steps);
  }

  public Single<List<Ingredient>> buildIngredients() {
    List<Ingredient> ingredients = new ArrayList<>();
    Pattern pattern = Pattern.compile(MAGIC_INGREDIENT_REGEX);
    for (String rawIngredient : this.listRawIngredients) {
      Matcher matcher = pattern.matcher(rawIngredient);
      Ingredient ingredient = new Ingredient();
      if (matcher.find()) {
        ingredient.setQuantity(matcher.group(1));
        ingredient.setUnit(Ingredient.Unit.toUnit(matcher.group(2)));
        ingredient.setName(matcher.group(3).trim());
      }
      ingredients.add(ingredient);
    }
    return Single.just(ingredients);
  }

  private static class InstanceHolder {

    private static final JsoupRetriever INSTANCE = new JsoupRetriever();
  }

  private class GetPage extends AsyncTask<String, Void, Document> {

    private final String instruction;
    private final String ingredient;

    private GetPage(String instruction, String ingredient) {
      this.instruction = instruction;
      this.ingredient = ingredient;
    }

    @Override
    protected Document doInBackground(String... strings) {
      try {
        Document doc = Jsoup.connect(url).get();
        return doc;
      } catch (Exception e) { // TODO change back to IOException after debugging.
        e.printStackTrace();
        return null;
      }
    }

    @Override
    protected void onPostExecute(Document doc) {
      if (doc != null) {
        processDoc(ingredient, instruction, doc);
      }

    }
  }

}
