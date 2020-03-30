## Summary

Recipes online are usually preceded by paragraph after paragraph of unnecessary text, followed by a comment section, and littered with ads. Choppit will extract only the ingredients and instructions and display them in an easy-to-read interface.  The purpose is to get the best of both family cookbooks and internet recipes by storing a collection offline, simplifying the recipe display, allowing customization, and displaying ingredient amounts while following instructions without scrolling.

Choppit will receive a recipe url, identify relevant text through user input and Jsoup HTML parsing, and confirm ingredients, measurements  and individual steps before saving locally.  There will also be a "recipe from scratch" option to help digitize and preserve important family recipes.

---

## State of the App

Choppit is not fully operational yet.  I struggled with the implementation of ReactiveX, and went down several blind alleys before I figured out how to handle an HTML request *not* on a UI thread.  If I had started with a loading screen between Selection and Editing, I could have saved two or three days of work.  Switching over from findViewById to Data Binding in my Recycler Views was challenging, but the effort will be worthwhile, especially when I incorporate two-way binding in user-editable fields.

### // TODO list

#### Critical problems

###### • Saving recipes
###### • Editing saved recipes
###### • Viewing saved recipes

#### Basic Functions

- Support Recipes from scratch in the Editing Frgament.
	
- Access Editing screen from a saved Recipe.

- Search option to the Cookbook Fragment.

- Receive shared url instead of copying/pasting.

- Turn Unit selection on the Editing Screen into a drop-down displaying the Enum values.

- Changing font through Settings *(this is not cosmetic because readability from a few feet away is a basic feature)*
    - Typeface and size
    - Possibly vertical and horizontal spacing
	- I would like to add the [Open Dyslexic](opendyslexic.org) font to increase accessibility.
	
- Figure out how to display the right ingredient (and only the right ingredient) as a pop-up while cooking.
	- An alternative to my original idea is to have a "Cooking mode" that displays Steps full-screen, then opens an AlertDialog with a checklist of Ingredients, so they can be cleared one at a time. 

	
#### Bugs & Errors

There are very few bugs in the functionality I do have, but work needs to be done in error handling.

- Bug: UI hangs when trying to navigate away from Editing & Recipe screens.
- Error handling: There should be a UI message if a link fails to load.
- Error handling: If there are zero or multiple HTML class results for either search string, the Loading Screen will display indefinitely.  The UI should return to the Selection screen and ask for valid input, or a longer string.
	- Searching for two ingredient strings that are wrapped in matching classes, and ruling out any clas that contains both strings should address sites where the author talks about the recipe in depth before the actual recipe.

#### Cosmetic Improvements

The interface is sparse by design, since the purpose of the app is to remove unnecessary clutter.  I don't want to add anything that isn't functional.

- Improve Editing Fragment by using Divider Decorations (or another method) to display **Ingredient** and **Step** headers before each section.
- Add a Theme setting, including (maybe starting with) dark mode.  
	- Multiple versions of the logo to match whatever theme is selected.
	
#### Functional Stretch Goals

- A Conversion Chart AlertDialog
- A Multiplier button to halve/double/etc. Ingredient Quantity values.\*
- A Metric / US "Standard" conversion option.\*
- Additional Recipe Metadata: meal, time to cook, family members who like it, etc.
- Timer utility
	- Configure multiple count-down timers with labels at a Recipe level to track different steps.
	- (Maybe) an overall Recipe timer to track how long it *actually* takes you to cook it.

		- \* The simplest approach is probably to write internal conversions of all US Standard values into teaspoons, and incorporate the Apache Commons Math library to help.
---

## Intended Users

* Anybody who cooks will appreciate seeing ingredient amounts from the instructions without having to scroll, especially for unfamiliar recipes.

* Casual cooks will be able to refer back to saved recipes quickly without loading lots of unwanted content, and account for important differences like altitude and regional ingredients.

* Families can preserve and share their favorite recipes.

* Serious home chefs will also be able to tweak and perfect their meals and note suggestions for the next time they use a recipe.

### [User Stories](user-stories.md)

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

---

## Design Documentation

### [Wireframe Diagram](wireframe.md)

### [Entity-Relationship Diagram](erd.md)

### [Data Model Implementation](data-models.md)

---

## Build Instructions

- Clone the Choppit Git Repository [here](git@github.com:semartinez147/choppit.git).

- From your IDE, import Choppit as a **Gradle** project