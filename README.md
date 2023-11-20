# Minesweeper-game
Classic minesweeper game.

The game consists of clearing all the boxes on a screen that do not hide a mine.

Some squares have a number, this number indicates the mines that are in all the surrounding squares. Thus, if a box has the number 3, it means that of the eight boxes around it (if it is not in a corner or border) there are 3 with mines and 5 without mines. If a square without a number is discovered, it indicates that none of the neighboring squares has a mine and these are discovered automatically.


## TO DO:

- [X] If a square with a mine is discovered, the game is lost.
- [X] Put a mark in the boxes that the player thinks there are mines to help discover the one they are looking for.
- [X] Three levels of difficulty: easy, intermediate and difficult.
- [X] The board is generated once the difficulty is chosen, so there is no safe start. There is a possibility that the first click will be lost directly.
- [X] You can save and retrieve the previous game.
- [X] The process of revealing contiguous squares without mines is done recursively.
- [X] A maximum of 4 mines can be generated around the box.
- [X] If loses, then all the board is shown.


## Images of the game:

Default window:


<img width="395" alt="Captura de pantalla 2023-11-20 a las 18 10 48" src="https://github.com/maribel95/Minesweeper-game/assets/61268027/2153be50-e467-4fe5-ab69-da03c242449d">


Example of a few interactions in the game.


<img width="396" alt="Captura de pantalla 2023-11-20 a las 18 10 58" src="https://github.com/maribel95/Minesweeper-game/assets/61268027/eaf7af3e-c398-4918-ab71-f9923e5c3156">

Winning message:


<img width="397" alt="Captura de pantalla 2023-11-20 a las 18 14 04" src="https://github.com/maribel95/Minesweeper-game/assets/61268027/12153026-200f-4439-a186-10f005e1d50b">


Game menu showing the available options:


<img width="394" alt="Captura de pantalla 2023-11-20 a las 18 14 11" src="https://github.com/maribel95/Minesweeper-game/assets/61268027/d91796d0-1c03-4337-9ca0-c0eb2d1aa35d">






