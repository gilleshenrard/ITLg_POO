# ITLg_POO
## Project Awele - Part 1 - v4.0

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

Once the scattering is done and there are no seeds left to scatter : if the last slot populated contains 2 or 3 seeds, the capture phase is engaged.

#### 3. Capture phase
Once a capture occurs, all the slots following the one selected by the player up to the one which triggered the captured are checked.

All those containing 2 or 3 seeds (including the last slot) are emptied and the seeds are stored by the player, thus rising its score.

It is to be mentioned that a player can not starve its opponent (no seeds left on its side of the board). Should a starvation occur,
the season is cancelled and the player can play again.

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

As per the pattern rules, a state is always active and present as a member of the game controller.

#### 6. UI update as an Observer design pattern
In order to decouple the game mechanics and the UI technology as much as possible, the BoardController and the views implement
the Observer design pattern.

Each time the UI needs to be refreshed, the controller will call updateObservers() on each of its observers (the views),
instead of just updating the UI directly.

#### 7. Unit tests
The unit tests for each class can be found in the mirrored directory tests/ (tests/Models, tests/Views, tests/Controllers).

---
### 4. Change list (since v3.4)

- States in the Machine State pattern are now enclosed in an emuneration to ease up their use
- The Board Controller now uses the Observer design pattern to update a UI independently of the technology used
- Console views now implement the Observer design pattern
- BoardController is now a member of GameController, to ease up Constructors manipulations
- Game views now implement a base class. They only provide system messages
- UI update has been moved from the Prompting State to the player switching state
- Previously implemented views have been renamed to indicate those are console views
- BoardJFXView has been implemented as a JavaFX-based board view
- GameJFXView has been implemented as a JavaFX-based system messages view
- JFXSelect has been implemented to deal with player slot selection via JavaFX click events
- The game can now be launched either in console mode or in JFX mode via a program argument
- Headers have been added to the newly implemented methods
- The main game loop runs in a separate thread in the JFX version

---
### 5. Known issues
n/a

---
### 6. To do

- Optimise the unit tests
- Add UML diagram links to README.md