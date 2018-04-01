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

For the presentation layer I went with an MVP approach, allowing the model to persist for the session on the application scope.


Typically I would use dagger for DI, but i opted to leave it out in the interest of time 
The composition of the objects lend themselves to to easy DI.


