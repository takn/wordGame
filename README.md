# Word Matching Challenge
a twist on a find and match word game. Find the translations for the given word. 

# Build / Running
Just run ./gradlew build and make sure you look at the tests.

You can install with standard gradle commands or via Android Studio

# Hello!
Thank you for your review. 
A few notes about this project.

This is a project done in Kotlin, please excuse the lack of elegance, i'm still learning!

The general approach was to use Clean Architecture for the overall structure of the project.
Normally the 'layers' would be in separate modules, but in this simple case they are just separated into packages.

I like to write tests first, I find them very helpful when piecing stuff together.
Usually i write tests for logic heavy stuff
For the presentation layer I went with an MVP approach, allowing the model to persist for the session on the application scope.


Typically I would use dagger for DI, but i opted to leave it out in the interest of time  
The composition of the objects lend themselves to to easy DI.

The ui was left pretty barebones. The focus for this project was functionality.
UI updates are handled via a couple of different Observables handled mostly by the GameManager

I used a simple gridlayout instead of a recyclerview with a gridlayoutManager or something like that
I wanted to avoid havig an adapter. After implementing the whole GameGrid class I'm still wondering
about which approach works best. 

## Challenging aspects?
This was one of my first forays into Kotlin, I enjoyed it! I'm late to the party, i know

It would have been interesting to try out coroutines in this  

Parsing the json was laborious. I ended up implementing the pull parser that comes with Gson
this was probably the most challenging part. 

# Enhancements
The ui is very basic, with functionality complete that would be my next area of focus.
The UX could be improved, right now when you complete a game, the 'info panel'
immediately displays.

Dagger needs to be implemented as well, but the structure for it is there.
Two components are needed, an app component scope and an activity component scope.
That would be sufficient



