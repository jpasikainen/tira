# Testing Documentation

## Unit Testing Coverage Report
![Jacoco Coverage Report](/documentation/imgs/screenshot.png)

## Unit Testing Breakdown
Excluded from testing:
- GUI package
- Game.java

Testing is done by using unit tests. The most challenging part of writing the unit tests for this project is to bear in mind the randomness of the game. As mentioned on the design document, when at least one tile has moved, a new tile is created at a random position on the board. This is why we cannot accurately test tile positions since there will always be a new one at a random position. As the newly spawned tile can have the value of two or four, merging score becomes a problem as well. Making multiple moves and then trying to predict what the board score or tile positions are is impossible.

GameLoop is only partially tested since some of the methods are specifically for the GUI.

Solver testing has some characteristics of integeration testing where all the methods of the class are tested at once. The same board variations are tested with multiple depths. Sometimes there is variance, especially when comparing the solver's solution at depth one to a greater depth.

## Performance Testing

The solver algorithm produces different results when using varying levels of depth and also takes more time depending on the magnitude of the depth. Time spent solving the game is something that could be tested but it doesn't really answer the question "can it solve the game" so it will be discarded.

## Score Testing

Score testing runs the game a hundred times starting with the depth of one incrementally increasing it all the way up to seven. Higher levels of depth take too much time on my machine. Each run starts with an empty board and ends when no more moves can be done. During each run, the following are recorded: depth, highest tile & score.

## How to Reproduce the Test Results
Unit tests can be ran with the command ``gradle test`` or ``./gradlew test``.

Score testing can be done by running script ``analyze.sh``. Use at your own risk.
