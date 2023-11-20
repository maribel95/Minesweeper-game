# Minesweeper-game
Classic minesweeper game.

The game consists of clearing all the boxes on a screen that do not hide a mine.

Some squares have a number, this number indicates the mines that are in all the surrounding squares. Thus, if a box has the number 3, it means that of the eight boxes around it (if it is not in a corner or border) there are 3 with mines and 5 without mines. If a square without a number is discovered, it indicates that none of the neighboring squares has a mine and these are discovered automatically.

If a square with a mine is discovered, the game is lost.

You can put a mark in the boxes that the player thinks there are mines to help discover the one they are looking for.

There are three levels of difficulty: easy, intermediate and difficult.

The board is generated once the difficulty is chosen, so there is no safe start. There is a possibility that the first click will be lost directly.

You can save and retrieve the previous game.

The process of revealing contiguous squares without mines is done recursively.
