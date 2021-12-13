## How to run?
Download the project repository and go to /project folder. Use ``./gradlew run`` to play the game yourself.

To use the solver, run ``./gradlew run --args="--solve --depth x"`` and replace x with the depth you wish to use. Use a number from 1 to 8.\
Example: ``./gradlew run --args="--solve --depth 5"``

If you wish the game to restart when there are no moves left, add argument ``--auto`` inside --args="".