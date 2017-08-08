
# Snake Game for Gameboy Advance #
This is a [Snake Game](https://en.wikipedia.org/wiki/Snake_(video_game)), written in C, for Gameboy Advanced. Here is the demo of the gameplay:
![Snake](https://user-images.githubusercontent.com/16352823/29089875-c6c8e552-7c4b-11e7-8c91-60b15b1d741c.gif)  
You get three lives. If you beat the game (a.k.a. your snake reaches the length of 200 units) you will be taken to "Game Over" screen with nice "You won!" writing, while if you lose, you will just get a regular gameover screen with no writing on it (I now, I made it that harsh on purpose). 
# Rules #
__*Note*:__ _I give the controls for Gameboy Advance keys, that may not necessarily overlap with the keys on your actual computer. Check your emulator keyboard bindings for specific details. For example, key "A" of your gameboy might be bound to key "Z" of your actual machine_.  
Snake costantly moves in a given direction. You can alter the direction of its movement by pressing left/right/
up/down. Snake can not go in the opposite direction of its movement (a.k.a if the snake is going right, you
cannot make it go left instantly).   
If you go beyond borders of the screen or higher that the red 
line on top of the screen, you will lose a life. Also, the snake cannot bump into itself. To score points, the snake has to collect apples that can be located anywhere on the screen. Once the snake eats an apple, it gets one unit bigger, so maneuvering gets trickier as you progress through the game. The objective is to collect as many points as you can and/or to achieve the maximum length of 200. If you lose a life, your snake is reset to its initial length, but your score is kept. Once you lose all lives, you get a game over and see your final score. If you restart a game after a game over, the score will be reset to 0.  
The game launches to the title screen. In order to go from the title screen to the game, press "A". If you want to go back
to the title screen at any point in the game press "Select" key (your score will be reset to 0).
The player wins the game, if the length of the snake will be greater than 200. 
# Installation instructions #
_Okay, cool, you convinved me, I wanna play! How do I install the game?_  
Download and install the GBA emulator (there are plenty of good options out there, but I highly recommend [Emulator Zone](http://www.emulator-zone.com/doc.php/gba/)) and dowload the [.gba file](https://github.com/lnikolenko/projects/blob/master/C/Snake.gba) from this repo. Then open your emulator and click "File"->"Open..." and select the .gba file. You are good to go!
 
