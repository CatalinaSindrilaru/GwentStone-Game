Name: Sindrilaru Catalina-Maria

Group: 322 CA 2022

# Assignment - GwentStone Game

## Presentation of the game

This game consists of a multitude of cards, heroes and commands. The cards on the game table
attack each other, but they can also attack the heroes. Several games can be played, and
each game has several rounds, respectively turns. There are also two players.
For all this, I created different classes, with specific fields and methods.

## Implementation

Types of classes used:

* classes similar to those already created in which the necessary data for
playing the game are retained (I created these classes and made a copy
constructor in each one so as not to modify the data from the initial classes)


* classes for each card type: Minion, Environment, Hero (In these classes I created ArrayLists
with names of cards, by some categories, because it was easy for me to identify what type a card is)


* classes for displaying in json format (classes for normal output and for error output)


* classes for the player and the current game (Game class contains the table, number of rounds
and turns and also the index of the player who has not yet finished his turn. Player class contains
the hand for each player and the value for "mana")


* classes for error checking and statistics (the class for errors contains methods for commands where
errors may appear, and the class for statistics keeps the number of games won by each player, but 
also the total number of games played)


* the "Solve Commands" class handles each action separately

I the given <em>main</em> I created an object <em>play game</em> that calls a method
for resolving all the actions in game. Then, this function identify each particular 
command and call the right method (with suggestive name). 

I went through all the games, respectively all the commands in a game,
creating a new Game object (named currentGame) at each game start and
also two new players. I took the decks with which each player plays
this game and mix them.

For debug commands, I extracted what was necessary to be displayed and wrote
accordingly in json format, using ArrayNode or ObjectNode. 

For action commands, first I checked all the cases of errors, calling a method,
and then I made the attacks or used the skills according to the command.

To make it easier for me to display in json format each result of the commands,
I created a method that receive a card and return an ObjectNode that contains all
the information about that and also a method that receive a list of cards
and returns an ArrayNode which contains all the cards in the desired form.

Every time I had to use the ability of a card, I called the method created in the
class of the type of that card, giving it the right parameters.