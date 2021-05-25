## Summary

Recipes online are usually preceded by paragraph after paragraph of unnecessary text, followed by a comment section, and littered with ads. Choppit extracts only the ingredients and instructions and displays them in a simple interface.  The purpose is to get the best of both family cookbooks and internet recipes by storing a collection offline, simplifying the recipe display and allowing customization.

Choppit receives a recipe url by manual input or Share Intent, identifies relevant text through user input and Jsoup HTML parsing, then confirm ingredients, measurements and steps before saving locally.  There is also a "recipe from scratch" option to help digitize and preserve important family recipes.

Choppit began as a solo project in Deep Dive Java + Android Cohort 9.  I am deeply grateful to the Deep Dive community for introducing me to coding and supporting my efforts. 

---

## State of the App

Choppit is at minimum viability.  I was able to build the database while in person at Deep Dive, but in the isolation of COVID I struggled to understand how to implement ReactiveX tasks, and explored several blind alleys before I figured out how to handle HTTP requests and other asynchronous operations.  

Switching away from findViewById in my Recycler Views was challenging, but it was worth the effort to implement View Binding for most of my Fragment classes and Data Binding for the Recipe objects that are rendered in the UI.  I had some issues getting the app to build successfully with DataBinding, Jetpack Navigation and SafeArgs all enabled, but I got IntelliJ through it with some strategic refactoring/un-refactoring/cleaning/rebuilding and a small amount of percussive maintenance.

I had a vision of a Recipe interface supporting typeface changes and scaling sizes - inspired by the Kindle app - and I started attempting to integrate the AirBnB Paris library to handle this, but ultimately decided to pause development at the current stage to devote my time to different learning opportunities.

### // TODO list

#### Basic Functions

- Add search option to the Cookbook Fragment.

- Changing font through Settings *(readability from a few feet away is a basic feature)*
    - Text formatting in general needs some work, particularly font sizes.
    - Typeface and size
    - Possibly vertical and horizontal spacing
	- I would like to add the [Open Dyslexic](http://www.opendyslexic.org) font to increase accessibility.
	
- Figure out how to display the right ingredient (and only the right ingredient) as a pop-up while cooking.
    - I plan to do this using Apache OpenNLP.  I have found some useful datasets, but will need to do a significant amount of work to adapt them.  This will have to be a distant-future stretch goal.
	- An alternative to my original idea is to have a "Cooking mode" that displays Steps full-screen and opens an AlertDialog with a checklist of Ingredients, so they can be cleared one at a time.

#### Cosmetic Improvements

The interface is sparse by design, since the purpose of the app is to remove unnecessary clutter.  I don't want to add anything that isn't functional.

- Add a Theme setting, including (maybe starting with) dark mode.  
	- Multiple versions of the logo to match whatever theme is selected.
	
#### Functional Stretch Goals

- Additional Recipe Metadata: meal, time to cook, family members who like it, etc.
- A Multiplier button to halve/double/etc. Ingredient Quantity values.\*
- A Conversion Chart AlertDialog
- A Metric / US "Standard" conversion option.\*
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

---

## External Services

* Any recipe website.
  * HTML will be retrieved from recipe sites and processed to extract ingredient and instruction details.
  * The websites will not be needed once data is retrieved.
* [Jsoup Java library](https://jsoup.org/) to parse HTML and extract relevant information.
  * The Jsoup library is fully integrated as Choppit relies on Jsoup to parse Html into recipes.

---

## Original Design Documentation

#### (these pages have not been updated to reflect the changes made to the UI and database structure)

### [Wireframe Diagram](wireframe.md)

### [Entity-Relationship Diagram](erd.md)

### [Data Model Implementation](data-models.md)

### [User Stories](user-stories.md)

---

## Build Instructions

- Clone the [Choppit Git Repository](https://github.com/semartinez147/choppit).

- From your IDE, import Choppit as a **Gradle** project

---

## History of Resolved Issues:

- Saving recipes
  - ~~The save method needs to assign the returned Recipe Id to the Recipe's Step entities, and each Step Id to that Step's Ingredient Entities.~~
    - *Resolved April 2020 by expanding the save method using .map().*
  - ~~Edited recipes need to be re-processed before saving.~~
    - *Resolved as part of hierarchy overhaul.*

- Editing saved recipes
  - ~~Navigation from a single Recipe to the Editing screen needs to be added.~~
    - *Implemented in August 2020.*
  - ~~I want to handle the changes to the database with two-way data binding.~~
    - *Used two-way databinding for the "Favorite" button to toggle true/false.*

- Viewing saved recipes
  - ~~I have problems with navigation methods, but I believe I have a strategy to simplify them.~~
    - *Resolved 4/16/20 using SafeArgs to pass a recipeId into the fragment.*
  - ~~Currently only retrieving the Recipe object without Steps or Ingredients.~~
    - *Resolved August 2020 using POJOs.*

- ~~Considering flattening hierarchy by nesting Ingredients directly in Recipes rather than Steps.~~
  - *Completed  September 2020*

- ~~Support Recipes from scratch in the Editing Fragment.~~
  - *Completed  September 2020*

- ~~Access Editing screen from a saved Recipe.~~
  - *Completed  September 2020*

- ~~Receive shared url instead of copying/pasting.~~
  - *Completed  September 2020*

- *(added September 2020)* Redesign Recipe Selection process:
  - User submits URL (paste or share)
  - Jsoup scans page, separates text elements and displays them in a WebView with minimal formatting
  - Choppit prompts user to tap an ingredient; ingredient HTML class is recorded, ingredients are removed from the display
  - Choppit prompts user to tap a step; step HTML class is recorded, WebView navigates to Loading Screen

- ~~Bug: UI hangs when trying to navigate away from Editing & Recipe screens.~~
  - *Resolved  May 2020 using Jetpack Navigation and removing Loading fragment from the backstack.*
  
- ~~Error handling: There should be a UI message if a link fails to load.~~
- ~~Error handling: If there are zero or multiple HTML class results for either search string, the Loading Screen will display indefinitely.  The UI should return to the Selection screen and ask for valid input, or a longer string.~~
  - ~~Searching for two ingredient strings that are wrapped in matching classes, and ruling out any class that contains both strings should address sites where the author talks about the recipe in depth before the actual recipe.~~
  - *Resolved December 2020 with custom Exception classes*

- ~~Improve Editing Fragment by using Divider Decorations (or another method) to display **Ingredient** and **Step** headers before each section.~~
  - Resolved August 2020 by splitting the fragment into two RecyclerViews to simplify header and add/remove buttons.  