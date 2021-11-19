# Testing Documentation

## Unit Testing Coverage Report
![Jacoco Coverage Report](/documentation/imgs/screenshot.png)

## Breakdown
Excluded from testing:
- GUI package
- Game.java

Testing is done by writing small but efficient unit tests. The most challenging thing when making unit tests for this project is to write efficient tests and bear in mind the randomness of the game. As mentioned on the design document, when at least one tile has moved, a new tile is created at a random position on the board. This is why we can only test tile positions up to one move. As the newly spawned tile can have the value of two or four, merging becomes a problem as well. Making multiple moves and then trying to predict what the board score or tile positions should be is impossible.

Coming soon: system testing with the GUI!

## How to Reproduce the Test Results
Unit tests are always ran when the application is started or with command ``gradle test``.

