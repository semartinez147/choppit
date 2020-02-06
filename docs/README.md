## Description

Choppit is an app that easily saves and edits recipes from the internet.  The purpose is to get the best of both family cookbooks and internet recipes by collecting recipes, removing unnecessary information, allowing user edits, and tracking ingredient amounts while reading through instructions.

Recipes online are usually preceded by paragraph after paragraph of unnecessary text, followed by a comment section, and littered with ads. Choppit will extract only the ingredients and instructions and display them in an easy-to-read interface.

The first iteration of Choppit will support recipes from [Food Network](www.foodnetwork.com).  A user will begin by finding an interesting recipe. Android's "share" function will send the recipe to Choppit, which will edit it and save the simplified version locally.  

Choppit will convert the ingredients and instructions to a clear, uncluttered format, and support editing so that ingredients can be added/removed, measurements adjusted, and individual steps edited.  There will also be a "recipe from scratch" option to help digitize and preserve important family recipes.

## Intended Users

* Anybody who cooks will appreciate seeing ingredient amounts from the instructions without having to scroll.
* Casual cooks will be able to refer back to saved recipes quickly without loading lots of unwanted content, and account for important differences like altitude and regional ingredients.
* Family recipes can be preserved and shared.
* Serious home chefs will also be able to tweak and perfect their meals and note suggestions for the next time they use a recipe.

### [User Stories](user-stories.md)

## Design Documentation

### [Wireframe Diagram](wireframe.md)

### [Entity-Relationship Diagram](erd.md)

## External Services

* [Food Network](http://www.foodnetwork.com).
  * Additional websites as they are supported.
  * HTML will be retrieved from recipe sites and processed to extract ingredient and instruction details.
  * The websites will not be needed once data is retrieved.
* [jsoup Java library](https://jsoup.org/) to parse HTML and extract relevant information.
  * jsoup is an offline service that will be fully integrated as Choppit will rely on jsoup to parse websites into recipes.
* [USDA Agricultural Research Service](https://www.ars.usda.gov/northeast-area/beltsville-md-bhnrc/beltsville-human-nutrition-research-center/methods-and-application-of-food-composition-laboratory/mafcl-site-pages/sr17-sr28/)  database of ingredients.
  * This USDA dataset will be integral to Choppit's ingredient logic and necessary for the app to function properly.
  * The unformatted ASCII database file is small enough (~1.3MB) to be used as an offline reference.  