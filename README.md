# Minesweeper
My JavaFX implementation on a minesweeper game in 1 day.

[Link to Source Code](https://github.com/PresidentSam100/Minesweeper/tree/main/MineSweeeper/src)

[Link to Images](https://github.com/PresidentSam100/Minesweeper/tree/main/MineSweeeper/Images)

## Game Modes:

  __Easy:__
    
    10 by 10 with 15 mines
  
  __Medium:__
  
    15 by 15 with 40 mines
  
  __Hard:__
  
    20 by 20 with 100 mines
  
  __Custom Game Mode (to be implemented)__


https://user-images.githubusercontent.com/56271444/161407789-2f5fafe1-9ddf-45bb-a9db-6f4d05bfda9c.mp4

  
## Rules:

  __Winning:__
  
    You win when all the non-mine squares have been revealed.
   
   
https://user-images.githubusercontent.com/56271444/161408147-f9f625d1-65e2-4ea4-83bf-203bcab0df8d.mp4


  __Losing:__
  
    You lose when you reveal a mine.
    

https://user-images.githubusercontent.com/56271444/161408516-2075d06f-9e4f-4ecf-9c53-be31da08d8b1.mp4
    
    
  __Controls:__
  
    Right clicking an un-revealed square cycles the following marks:
      -Flag - Denotes the square as a mine
      -Question - Denotes the square as unknown
      -Un-marked - Removes all markings
    Left clicking
      -Reveals the clicked square
    Pressing the left button down, then holding the right button down and releasing the left button.
      -Works on revealed numbers
      -Reveals the neighboring locations
      -Only functions when there are enough locations marked with flags to correspond to the numbers




https://user-images.githubusercontent.com/56271444/161408877-64e8a057-611e-4570-89da-581c406f304c.mp4


## Symbols:
    1 to 8: number of mines near by
    Mine: unfound mine
    Mine with x: Incorrect Flag
    Red Mine: Exploded mine
