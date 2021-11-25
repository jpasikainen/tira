# Testing Documentation

## Unit Testing Coverage Report
![Jacoco Coverage Report](/documentation/imgs/screenshot.png)

## Unit Testing Breakdown
Excluded from testing:
- GUI package
- Game.java

Testing is done by writing small but efficient unit tests. The most challenging thing when making unit tests for this project is to bear in mind the randomness of the game. As mentioned on the design document, when at least one tile has moved, a new tile is created at a random position on the board. This is why we cannot really test tile positions since there will always be a new one at a random position. As the newly spawned tile can have the value of two or four, merging score becomes a problem as well. Making multiple moves and then trying to predict what the board score or tile positions are is impossible.

GameLoop is only partially tested since some of the methods are specifically for the GUI.

Solver testing is more like integeration testing where all the methods of the class are tested simultaneously. The same board variations are tested with multiple depths. Sometimes there is variance, especially when comparing the solver's solution at depth one and at a greater depth.

## Performance Testing

The solver algorithm produces different results when using varying levels of depth and also takes more time depending on the magnitude of the depth. This is something that could be tested but it doesn't really answer the question "can it solve the game" so it will be discarded for now.

## Score Testing

Score testing runs the game a hundred times starting with the depth of one incrementally increasing it all the way up to seven. Higher levels of depth take too much time on an 8th generation Intel i5 chip. Each run starts with an empty board and ends when no more moves can be done. During each run the following are recorded: depth, the highest tile, score.

### Results for 100 games
#### Legend
**Highest tile:** highest value the solver was able to achieve\
**256,512,1024,2048:** The percantage of the times the solver was able to achieve that value in a game\
**Mean score:** sum of scores divided by 100

| Depth        | 1   | 2   | 3    | 4    | 5    | 6    | 7     |
|--------------|-----|-----|------|------|------|------|-------|
| Highest Tile | 256 | 256 | 1024 | 1024 | 2048 | 2048 | 2048  |
| 256          | 18% | 29% | 100% | 96%  | 100% | 100% | 100%  |
| 512          | 0%  | 0%  | 94%  | 82%  | 99%  | 99%  | 100%  |
| 1024         | 0%  | 0%  | 25%  | 30%  | 71%  | 78%  | 90%   |
| 2048         | 0%  | 0%  | 0%   | 0%   | 10%  | 14%  | 48%   |
| Mean score   | 696 | 836 | 4096 | 4112 | 6779 | 7492 | 10477 |

## How to Reproduce the Test Results
Unit tests are always ran when the application is started or with command ``gradle test``.

Score testing can be done by running script ``analyze.sh``. Use at your own risk.
