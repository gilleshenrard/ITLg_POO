# ITLg_POO
## Project Awele - Part 3 - v5.1

---
### 1. Introduction
This assignment aims to learn how to develop in Java.

It has been developed with the IntelliJ Idea IDE, compiled with Amazon's Corretto OpenJDK 11, and unit tests have been made with Junit 5.

 It consists of an Awele game (more in the Rules section), on console for the Part 1.
 
 This game is an african traditional two-players turn-based reflection game based on resources capture.

---
### 2. Rules of the Awele
#### 1. Game board
The board consists of 12 slots (or holes) divided in two rows (6 slots per player).
Each slot is filled with 4 seeds each, thus 48 in total.

PLAYER 2

| 0 |

| 6 || 5 || 4 || 3 || 2 || 1 |

| 1 || 2 || 3 || 4 || 5 || 6 |

| 0 |

PLAYER 1

#### 2. Functioning of a turn
At each turn (named seasons), a player chooses a slot on his side to harvest.
Said slot is then emptied of all its seeds, and each of the latter is scattered one at a time in the next slots in an anti-clockwise manner.
The slot the player chose is to remain empty and to be skipped on the scattering phase.

Once the scattering is done and there are no seeds left to scatter : if the last slot populated contains 2 or 3 seeds
and is on the opponent side, the capture phase is engaged.

#### 3. Capture phase
Once a capture occurs, all the slots following the one selected by the player up to the one which triggered the captured are checked.

Starting from the last slot in which a seed has been scattered up to the initial slot played by the player,
all those containing 2 or 3 seeds are emptied and the seeds are stored by the player, thus rising its score.

The capture phase continues until the player's row or a non-capturable slot is reached.
Example :

PLAYER 2

| 2 || 1 || 6 || 6 || 0 || 1 |

| 3 || 4 || 2 || 2 || 7 || 1 |

PLAYER 1

In this case, player 1 selects the slot 5, and captures the slots 5 and 6 of its opponent, thus storing 5 seeds (2 + 1 + 2 scattered).

It is to be mentioned that a player can not starve its opponent (no seeds left on its side of the board). Should a starvation occur,
the season is cancelled and the player can play again. A player can however starve himself.

#### 4. Victory
A player wins the game when he manages to store more than 24 seeds.

---
### 3. Structure of the program
The program has been designed to match as much as possible to a MVC model. It also contains unit tests for all the classes and methods.

#### 1. Models
Model classes can be found in src/Models/. They basically hold the serialisable data + setters and getters.

They are as following :

- Player : This contains the player's information (name, ID)
- Slot : This represents a slot. It contains coordinates (x,y) and the amount of seeds contained.
- Game : This is a Singleton Pattern which contains all the game-wise information (players, board)
- Board : This represents the board. It contains the board (an ArrayList of Slot, seeds stored, seeds remaining for each player)
- DBSQLite : Allows for SQLite databases manipulations

##### 1.a SQLite database
A simple table database is kept updated by the game. SQLite has been chosen as the technology used for its simplicity.

Ideally, the database should contain two tables (Game and Board), and a DB view would conceal the information needed,
but for the sake of simplicity and to keep only one table in the database, the information from both tables will be
inserted and retrieved manually, one at a time.

The table holds the following columns : 
- Start date and time
- Duration of a game
- Name of the winner
- Seeds the player 1 has stored
- Seeds the player 2 has stored

#### 2. Views
Views classes can be found in src/Views/. They hold all the methods to interact with the user.

They are as following :

- GameConsoleView : Contains methods to display system messages on the console
- GameJFXView : Contains methods to display system messages on JavaFX Alert stages
- BoardConsoleView : Contains methods to display the game board (stored seeds, slots, players name) on the console
- BoardJFXView : Contains methods to display the game board (stored seeds, slots, players name) on a JavaFX stage
- iSelectable : base interface of a Strategy Pattern allowing to set the player selection behaviour on-the-fly
- KeyboardSelect : Allows a player to be prompted to select a slot with the keyboard
- RandomSelect : Allows a player to randomly select a slot (including a collision avoidance mechanic)
- MinimaxSelect : Allows a player to select a slot using the minimax algorithm (with alpha and beta pruning)
- JFXSelect : Allows a player to select a slot by clicking on the JavaFX game board stage

#### 3. Controllers
Controllers classes can be found in src/Controllers/. They hold the game mechanics, are the loop entry point
and link the views and the models together. 

They are as following :

- GameController : Encapsulates all the game entry point methods. The main game loop should always call GameControllers methods.
It also handles the state machine pattern management.
- BoardController : Contains the harvest and capture mechanics (extensively unit tested)

#### 4. Players' slot selection mechanism behind a Strategy design pattern
To allow the game to change each player's slot selection mechanism on the fly, each selection behaviour has been based on the
strategy pattern and is decoupled from the player itself.

#### 5. Seasons' mechanism as a State Machine design pattern
To implement and manage the seasons' mechanism, a State Machine pattern (or Finite State Machine) has been implemented.

It consists of four classes (plus the base class and an enumeration to hold them) :

- SwitchingPlayerState : Sets the ID of the player which will play the current season
- PromptingState : Deals with prompting the user for a slot selection
- PlayingState : Performs the scattering (+capture) and eventual error management, plus determines if a player forfeits
- StoringState : Performs the eventual storage of captured seeds and determines if a player wins
- MenuState : Displays a menu panel, allowing the player to change a player name or behaviour

As per the pattern rules, a state is always active and present as a member of the game controller.

#### 6. UI update as an Observer design pattern
In order to decouple the game mechanics and the UI technology as much as possible, the BoardController and the views implement
the Observer design pattern.

Each time the UI needs to be refreshed, the controller will call updateObservers() on each of its observers (the views),
instead of just updating the UI directly.

#### 7. Unit tests
The unit tests for each class can be found in the mirrored directory tests/ (tests/Models, tests/Views, tests/Controllers).

---
### 4. Change list (since v5.0)

- Values sent to the database are now accurate
- Start date and time of each game is now saved in the DB
- Games duration timing is now saved in the DB
- The scores button in the menu now shows a new window with all rows (also in the console)
- The DB window is now modal
- The DB can now be used by placing it either at the project root directory or the same one as the .JAR

---
### 5. Known issues
The Minimax algorithm is predictable and will make invalid choices (letting the opponent starve)

---
### 6. To do

- Make the main stage moveable
- Make java determine the working directory to locate the database
- build a custom window (with a console animation) for the easter egg
- Add seasons history
- Update the last edit dates on the files to make it accurate
- Optimise/complete the unit tests
- Check if every method throws an exception if supposed to
- Improve logging (log file for FINE level and lower, console for INFO and higher + add FINER everywhere for verbose)
- Add UML diagram links to README.md