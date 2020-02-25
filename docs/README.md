## Summary

Recipes online are usually preceded by paragraph after paragraph of unnecessary text, followed by a comment section, and littered with ads. Choppit will extract only the ingredients and instructions and display them in an easy-to-read interface.  The purpose is to get the best of both family cookbooks and internet recipes by storing a collection offline, simplifying the recipe display, allowing customization, and displaying ingredient amounts while following instructions without scrolling.

Choppit will receive a recipe url, identify relevant text through user input and Jsoup HTML parsing, and confirm ingredients, measurements  and individual steps before saving locally.  There will also be a "recipe from scratch" option to help digitize and preserve important family recipes.

---

## Intended Users

* Anybody who cooks will appreciate seeing ingredient amounts from the instructions without having to scroll, especially for unfamiliar recipes.

* Casual cooks will be able to refer back to saved recipes quickly without loading lots of unwanted content, and account for important differences like altitude and regional ingredients.

* Families can preserve and share their favorite recipes.

* Serious home chefs will also be able to tweak and perfect their meals and note suggestions for the next time they use a recipe.

### [User Stories](user-stories.md)

---

## Design Documentation

### [Wireframe Diagram](wireframe.md)

### [Entity-Relationship Diagram](erd.md)

### [Data Model Implementation](data-models.md)

---

## External Services

* Any recipe website.
  * HTML will be retrieved from recipe sites and processed to extract ingredient and instruction details.
  * The websites will not be needed once data is retrieved.
* [jsoup Java library](https://jsoup.org/) to parse HTML and extract relevant information.
  * jsoup is an offline service that will be fully integrated as Choppit will rely on jsoup to parse websites into recipes.
* [USDA Agricultural Research Service](https://www.ars.usda.gov/northeast-area/beltsville-md-bhnrc/beltsville-human-nutrition-research-center/methods-and-application-of-food-composition-laboratory/mafcl-site-pages/sr17-sr28/)  database of ingredients.
  * This USDA dataset will be integral to Choppit's ingredient logic and necessary for the app to function properly.
  * The unformatted ASCII database file is small enough (~1.3MB) to be used as an offline reference.  