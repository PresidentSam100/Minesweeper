# Minesweeper
My JavaFX implementation on a minesweeper game.

Game Modes:
  -Easy 10 by 10 with 15 mines
  -Medium 15 by 15 with 40 mines
  -Hard 20 by 20 with 100 mines
  -Custom Game Mode (to be implemented)
Rules:
  -Winning:
    *You win when all the non-mine squares have been revealed.
  -Losing:
    *You lose when you reveal a mine.
  -Controls:
    *Right clicking an un-revealed square cycles the following marks:
      -Flag - Denotes the square as a mine
      -Question - Denotes the square as unknown
      -Un-marked - Removes all markings
    *Left clicking
      -Reveals the clicked square
    *Pressing the left button down, then holding the right button down and releasing the left button.
      -Works on revealed numbers
      -Reveals the nighboring locations
      -Only functions when there are enough locations marked with flags to correspond to the numbers
Symbols:
  -1 to 8: number of mines near by
  -Mine: unfound mine
  -Mine with x: Incorrect Flag
  -Red Mine: Exploded mine


# Demos

