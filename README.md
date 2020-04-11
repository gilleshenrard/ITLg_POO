# ITLg_POO
##Project Awele - Part 1

---
###1. Introduction
This assignment aims to learn how to program in Java.

It has been developed IntelliJ Idea IDE, compiled with Amazon's Corretto OpenJDK 11, and unit tests have been made with Junit 5.

 It consists of an Awele game (more in the Rules section), on console for the Part 1.
 
 This game is an african traditional two-players turn-based reflection game based on resources capture.

---
###2. Rules of the Awele
####1. Game board
The board consists of 12 slots (or holes) divided in two rows (6 slots per player).
Each slot is filled with 4 seeds each, thus 48 in total.

PLAYER 2

| 0 |

| 6 || 5 || 4 || 3 || 2 || 1 |

| 1 || 2 || 3 || 4 || 5 || 6 |

| 0 |

PLAYER 1

####2. Functioning of a turn
At each turn (named seasons), a player chooses a slot on his side to harvest.
Said slot is then emptied of all its seeds, and each of the latter is scattered one at a time in the next slots in an anti-clockwise manner.
The slot the player chose is to remain empty and is to be skipped on the scattering.

Once the scattering is done and there are no seeds left to scatter : if the last slot populated contains 2 or 3 seeds, the capture phase is engaged.

####3. Case of a capture
Once a capture occurs, all the slots following the one selected by the player up to the one which triggered the captured are checked.

All those containing 2 or 3 seeds (including the last one) are emptied and the seeds are stored by the player, thus rising its score.

It is to be mentioned that a player can not starve its opponent (no seeds left on its side of the board). Should a starvation occur,
the season is cancelled and the player can play again.

####4. Victory
A player's victory occurs when he managed to store more than 24 seeds.

---
###3. Structure of the program
The program has been designed to match as much as possible to a MVC model. It also contains unit tests for all the classes and methods.
####1. Models
Model classes can be found in src/Models/. They are as following :

- Player : This contains the player's information (name, ID)
- Slot : This reprents a slot. It contains coordinates (x,y) and the amount of seeds contained
- Game : This is a Singleton Pattern which contains all the game-wise information (players, seeds stored, board)
- Board : This represents the board. It contains the board (an ArrayList of Slot) and the season mechanics

####2. Views
Views classes can be found in src/Views/. They are as following :

- GameView : Contains methods to display messages game-wide
- BoardView : Contains methods to display the game board (stored seeds, slots, players name)
- iSelectable : base interface of a Strategy Pattern allowing to set the player selection behaviour on-the-fly
- KeyboardSelect : Allows a player to be prompted to select a slot with the keyboard
- RandomSelect : Allows a player to randomly select a slot

####3. Unit tests
The unit tests for each class can be found in the mirrored directory tests/ (tests/Models, tests/Views).

---
###4. Known issues
n/a
