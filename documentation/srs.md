# Software Requirements Specification

Tietojenkäsittelytieteen kandiohjelma\
Projektin toteutus Javalla. Dokumentaatio englanniksi.

## Description

2048 is a game where the goal is to get a tile with the value of 2048. By pressing up, down, left or right, the tiles
will move along the chosen axis. When colliding, the tiles with the same values merge together and form a new tile. The
new tile's value is the sum of the collided tiles. The board is a 4x4 grid and the game ends when the entire board is
filled or the goal is reached. When the game starts, two new tiles are placed on the board at random positions. After
each move, a single new tile is created. The newly spawned tiles can have a value of two or four with propability of 90%
and 10%. When combining tiles, the outermost two tiles are prioritised if there happen to be more than two matching
tiles colliding.

The project will be programmed in Java, unit tested with JUnit and the GUI programmed with the help of JavaFX. Necessary
data structures will be from the standard library.

## Implementation

One way to solve the game would be to use Minimax algorithm. Minimax algorithm is used to get the next best move without
knowing the opponent's next move. 2048 is a single player game but we can imagine our opponent to be the computer
placing the tiles. During each turn, the player has 0-4 possible moves. After that, the computer places "2" or "4" tile
at random location. To implement minimax, we need to figure out the utility function that maximises the player's
outcome. But there's a problem. How do we determine the computer's utility since it's based on randomness? During my
research about the algorithm, I came across Expectiminimax. It is more suitable for the occasion as it can account for
the random components of the game.

Pseudocode implementation of the algorithm without min-component since the computer doesn't have a strategy. Based
on [this](https://en.wikipedia.org/wiki/Expectiminimax#Pseudocode) implementation found on Wikipedia.

```
function expectiminimax(node, depth)
    if node is a terminal node or depth = 0
        return heuristic(node)
    if computer's node
        // Return value of minimum-valued child node
        let α := 0
        foreach child of node
            α := α + (probability() * expectiminimax(child, depth-1))
    else if player's node
        // Return value of maximum-valued child node
        let α := -∞
        foreach child of node
            α := max(α, expectiminimax(child, depth-1))
    return α
```

```
function probability()
    return (0.9 or 0.1) * (1 / amount of free tiles)
```

Heuristics for the game are
discussed [here](https://stackoverflow.com/questions/22342854/what-is-the-optimal-algorithm-for-the-game-2048). For the
MVP, "granting "bonuses" for open squares and for having large values on the edge" strategy is enough. More
sophisticated strategies can be implemented later.

One possible implementation for the game board grid is to use a single 64-bit integer. Now we have 4 bits for each tile
which we can use as two's exponent (power of two) to get the actual values.

## Time complexities

Expectiminimax has the worst-case performance of O(b^m n^m) where b is the children node count at each node, m is the
height of the tree and n is the amount of moves.

## Sources

https://stackoverflow.com/questions/22342854/what-is-the-optimal-algorithm-for-the-game-2048  
https://en.wikipedia.org/wiki/Expectiminimax  
http://cs229.stanford.edu/proj2016/report/NieHouAn-AIPlays2048-report.pdf  

