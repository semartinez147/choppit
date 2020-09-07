## Summary

Recipes online are usually preceded by paragraph after paragraph of unnecessary text, followed by a comment section, and littered with ads. Choppit will extract only the ingredients and instructions and display them in an easy-to-read interface.  The purpose is to get the best of both family cookbooks and internet recipes by storing a collection offline, simplifying the recipe display, allowing customization, and displaying ingredient amounts while following instructions without scrolling.

Choppit will receive a recipe url, identify relevant text through user input and Jsoup HTML parsing, and confirm ingredients, measurements  and individual steps before saving locally.  There will also be a "recipe from scratch" option to help digitize and preserve important family recipes.

---

## State of the App

Choppit is not fully operational yet.  I was able to build the database without much difficulty, but without the benefit of in-person classes I struggled to understand how to implement ReactiveX tasks, and explored several blind alleys before I figured out how to handle an HTML request on its own thread.  

Switching over from findViewById to Data Binding in my Recycler Views was challenging, but the effort will be worthwhile, especially when I incorporate two-way binding for Recipe editing.  I also had some issues getting the app to build successfully with DataBinding, Jetpack Navigation and SafeArgs all enabled, but I got IntelliJ through it with some strategic refactoring/un-refactoring/cleaning/rebuilding.

The process was complicated by responses to the COVID-19 pandemic, and I had to change gears to my group project before I had Choppit in a state I was happy with.  This project was a huge learning experience, and my current familiarity with concepts I initially struggled with has been worth the trouble.  I learned a lot more in the few weeks I spent on the group project, and I am working steadily on applying those lessons to making this app publishable.

### // TODO list

#### Critical problems


- Saving recipes
  - ~~The save method needs to assign the returned Recipe Id to the Recipe's Step entities, and each Step Id to that Step's Ingredient Entities.~~
      - *Resolved 4/15/2020 by expanding the save method using .map().*
  - ~~Edited recipes need to be re-processed before saving.~~
      - *Resolved as part of hierarchy overhaul.*

- Editing saved recipes
  - ~~Navigation from a single Recipe to the Editing screen needs to be added.~~
      - *Implemented on 8/19/2020.*
  - ~~I want to handle the changes to the database with two-way data binding.~~
      - *Used two-way databinding for the "Favorite" button to toggle true/false.*

- Viewing saved recipes
  - ~~I have problems with navigation methods, but I believe I have a strategy to simplify them.~~
    - *Resolved 4/16/20 using SafeArgs to pass a recipeId into the fragment.*
  - ~~Currently only retrieving the Recipe object without Steps or Ingredients.~~
    - *Resolved 8/19/2020 using POJOs.*

#### Basic Functions

- ~~Considering flattening hierarchy by nesting Ingredients directly in Recipes rather than Steps.~~  
    - *This will simplify processing and avoid complications processing natural language (i.e. 
    understanding that "fresh black pepper" is equivalent to "pepper", "black pepper", "pepper, 
    black", etc. but "fresh black pepper" and "fresh black currants" are totally different).*
    - *Completed 9/6/2020*

- ~~Support Recipes from scratch in the Editing Fragment.~~ Done.
	
- ~~Access Editing screen from a saved Recipe.~~ Done.

- Add search option to the Cookbook Fragment.

- ~~Receive shared url instead of copying/pasting.~~ Done.

- *(added 9/7/2020)* Redesign Recipe Selection process:
    - User submits URL (paste or share)
    - Jsoup scans page, separates text elements and displays them in a WebView with minimal formatting
    - Choppit prompts user to tap an ingredient; ingredient HTML class is recorded, ingredients are removed from the display
    - Choppit prompts user to tap a step; step HTML class is recorded, WebView navigates to Loading Screen

- Turn Unit selection on the Editing Screen into a drop-down displaying the Enum values including OTHER.

- Changing font through Settings *(readability from a few feet away is a basic feature)*
    - Text formatting in general needs some work, particularly font sizes.
    - Typeface and size
    - Possibly vertical and horizontal spacing
	- I would like to add the [Open Dyslexic](http://www.opendyslexic.org) font to increase accessibility.
	
- Figure out how to display the right ingredient (and only the right ingredient) as a pop-up while cooking.
    - I plan to do this using Apache OpenNLP.  I have found some useful datasets, but will need to do a significant amount of work to adapt them.  This will have to be a distant-future stretch goal.
	- An alternative to my original idea is to have a "Cooking mode" that displays Steps full-screen and opens an AlertDialog with a checklist of Ingredients, so they can be cleared one at a time. 

	
#### Bugs & Errors

There are very few bugs in the functionality I do have, but work needs to be done in error handling.

- ~~Bug: UI hangs when trying to navigate away from Editing & Recipe screens.~~
    - *Resolved 5/6/20 using Jetpack Navigation and removing Loading fragment from the backstack.*
- Error handling: There should be a UI message if a link fails to load.
- Error handling: If there are zero or multiple HTML class results for either search string, the Loading Screen will display indefinitely.  The UI should return to the Selection screen and ask for valid input, or a longer string.
	- Searching for two ingredient strings that are wrapped in matching classes, and ruling out any clas that contains both strings should address sites where the author talks about the recipe in depth before the actual recipe.

#### Cosmetic Improvements

The interface is sparse by design, since the purpose of the app is to remove unnecessary clutter.  I don't want to add anything that isn't functional.

- ~~Improve Editing Fragment by using Divider Decorations (or another method) to display **Ingredient** and **Step** headers before each section.~~
    - Resolved by splitting the fragment into two RecyclerViews to simplify header and add/remove buttons.  
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

\* The simplest approach is probably to write internal conversions of all US Standard values into teaspoons, and incorporate the Apache Commons Math library to help.

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
* [Jsoup Java library](https://jsoup.org/) to parse HTML and extract relevant information.
  * The Jsoup library is fully integrated as Choppit relies on Jsoup to parse Html into recipes.

---

## Design Documentation

### [Wireframe Diagram](wireframe.md)

### [Entity-Relationship Diagram](erd.md)

### [Data Model Implementation](data-models.md)

---

## Build Instructions

- Clone the [Choppit Git Repository](https://github.com/semartinez147/choppit).

- From your IDE, import Choppit as a **Gradle** project