package com.semartinez.projects.choppit.model.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblyRecipe {
  private long id = 0;
  private String url;
  private String title;
  private boolean favorite;
  private List<Step> steps;
  private List<Ingredient> ingredients;

  private List<String> reduction;
  private Map<String, String> samples;

  public long getId() {
    return id;
  }
  public void setId(long id) {
    this.id = id;
  }
  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public boolean isFavorite() {
    return favorite;
  }
  public void setFavorite(boolean favorite) {
    this.favorite = favorite;
  }
  public List<Step> getSteps() {
    return steps;
  }
  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }
  public List<Ingredient> getIngredients() {
    return ingredients;
  }
  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<String> getReduction() {
    return reduction;
  }

  public void setReduction(List<String> reduction) {
    this.reduction = reduction;
  }

  public Map<String, String> getSamples() {
    return samples;
  }

  public void setSamples(Map<String, String> samples) {
    if (samples == null) {
      samples = new HashMap<>();
    }
    this.samples.put("ingredient", samples.get("ingredient"));
    this.samples.put("instruction", samples.get("instruction"));
  }
}
