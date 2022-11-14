package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.card.environment.Environment;
import game.card.minion.Minion;
import game.data.ActionsInputData;
import game.data.CardInputData;
import game.data.GameInputData;
import game.data.InputData;
import game.displays.DisplayDeck;
import game.displays.DisplayError;
import game.displays.DisplayGetPlayerHero;
import game.displays.DisplayGetPlayerTurn;
import game.gameStrategy.Game;
import game.gameStrategy.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SolveCommands {
    public void display(String file, InputData inputData, ArrayNode output) {

        ArrayList<GameInputData> games = inputData.getGames();

        for (GameInputData game : games) {
            ArrayList<ActionsInputData> actions = game.getActions();

            ArrayList<CardInputData> playerOneDeck, playerTwoDeck;
            int playerOneDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();

            playerOneDeck = inputData.getPlayerOneDecks().getDecks().get(playerOneDeckIdx);
            playerTwoDeck = inputData.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx);

            Collections.shuffle(playerOneDeck, new Random(game.getStartGame().getShuffleSeed()));
            Collections.shuffle(playerTwoDeck, new Random(game.getStartGame().getShuffleSeed()));

            Player player1 = new Player();
            Player player2 = new Player();

            // scot carte

            // sterg prima carte din game si o oun in mana

            player1.getHand().add(playerOneDeck.remove(0));
            player2.getHand().add(playerTwoDeck.remove(0));

            // creez o masa noua la fiecare joc nou. incep o noua tura/runda si schimb jucatorul la starting player
            Game currentGame = new Game();
            currentGame.newGame();
            currentGame.changePlayer(game.getStartGame().getStartingPlayer());
            player1.setMana(1);  // mana pt jucator = 1
            player2.setMana(1);


            for (ActionsInputData action : actions) {
                String command = new String(action.getCommand());

                if (command.compareTo("getCardsInHand") == 0) {
                    getCardsInHand(action.getPlayerIdx(), player1, player2, output);
                }

                if (command.compareTo("getPlayerDeck") == 0) {
                    getPlayerDeck(inputData, game, action, output);
                }

                if (command.compareTo("getPlayerTurn") == 0) {
                    getPlayerTurn(currentGame, output);
                }

                if (command.compareTo("getPlayerHero") == 0) {
                    getPlayerHero(game, action, output);
                }

                if (command.compareTo("endPlayerTurn") == 0) {
                    endPlayerTurn(currentGame, player1, player2, playerOneDeck, playerTwoDeck);
                }

                if  (command.compareTo(("placeCard")) == 0) {
                    int handIdx = action.getHandIdx();
                    placeCard(currentGame, handIdx, player1, player2, output);
                }

                if (command.compareTo("getPlayerMana") == 0) {
                    int playerIdx = action.getPlayerIdx();
                    getPlayerMana(playerIdx, player1, player2, output);
                }
            }
        }

    }

    public void getPlayerDeck(InputData inputData, GameInputData game, ActionsInputData action, ArrayNode output) {
        // am nevoie de playerIdx care se gaseste la actions
        // "playerOneDeckIdx": 0
        // lista deck uri pt acel jucator

        // scot pachetele

        int playerIdx = action.getPlayerIdx();
        ArrayList<ArrayList<CardInputData>> decks;
        int playerDeckIdx;

        if (playerIdx == 1) {
            decks = inputData.getPlayerOneDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(decks.get(playerDeckIdx), playerIdx, output, "getPlayerDeck");

        } else if (playerIdx == 2) {
            decks = inputData.getPlayerTwoDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(decks.get(playerDeckIdx), playerIdx, output, "getPlayerDeck");
        }
    }

    public void getPlayerHero(GameInputData game, ActionsInputData action, ArrayNode output) {
        int playerIdx = action.getPlayerIdx();

        CardInputData hero;
        if (playerIdx == 1) {
            hero = game.getStartGame().getPlayerOneHero();
            DisplayGetPlayerHero displayGetPlayerHero = new DisplayGetPlayerHero();
            displayGetPlayerHero.displayGetPlayerHero(hero, playerIdx, output);

        } else if (playerIdx == 2) {
            hero = game.getStartGame().getPlayerTwoHero();
            DisplayGetPlayerHero displayGetPlayerHero = new DisplayGetPlayerHero();
            displayGetPlayerHero.displayGetPlayerHero(hero, playerIdx, output);
        }

    }

    //merge doar pt test1
    public void getPlayerTurn(Game currentGame, ArrayNode output) {
        int playerTurn;
//        playerTurn = game.getStartGame().getStartingPlayer();
        playerTurn = currentGame.getCurrentPlayer();

        DisplayGetPlayerTurn displayGetPlayerTurn = new DisplayGetPlayerTurn();
        displayGetPlayerTurn.displayGetPlayerTurn(playerTurn, output);
    }

    public void endPlayerTurn(Game currentGame, Player player1, Player player2,  ArrayList<CardInputData> playerOneDeck, ArrayList<CardInputData> playerTwoDeck) {
        currentGame.increaseTour();
        if (currentGame.getTour() % 2 == 1) {
            currentGame.increaseRound();

            player1.changeManaPlayer(currentGame.getRound());
            player2.changeManaPlayer(currentGame.getRound());

            if (playerOneDeck.size() > 0)
                player1.getHand().add(playerOneDeck.remove(0));
            if (playerTwoDeck.size() > 0)
                player2.getHand().add(playerTwoDeck.remove(0));
        }

        if (currentGame.getCurrentPlayer() == 1) {
            currentGame.changePlayer(2);
        } else {
            currentGame.changePlayer(1);
        }
    }

    public void getCardsInHand(int playerIdx, Player player1, Player player2, ArrayNode output) {

        if (playerIdx == 1) {
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(player1.getHand(), playerIdx, output, "getCardsInHand");
        } else {
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(player2.getHand(), playerIdx, output, "getCardsInHand");
        }
    }

    public void placeCard(Game currentGame, int handIdx, Player player1, Player player2, ArrayNode output) {

        Environment environment = new Environment();
        Minion minion = new Minion();

        Player player = player1;
        if (currentGame.getCurrentPlayer() == 2) {
            player = player2;
        }

        // scoate cartea de la pozitia handIdx din mana
        ArrayList<CardInputData> hand = player.getHand();

        if (hand.size() > 0 && handIdx < hand.size()) {
            CardInputData card = hand.get(handIdx);
            boolean done = false;

            // scot tabla de jos
            ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();


            // verifica daca cartea este de tipul environment
            if (environment.getEnvironmentCards().contains(card.getName())) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorPlaceCard(output, "Cannot place environment card on table.");
                done = true;

            }
            if (!done && card.getMana() > player.getMana()) {  // verifica daca cartea are costul mai mic decat mana jucatorului

                DisplayError displayError = new DisplayError();
                displayError.displayErrorPlaceCard(output, "Not enough mana to place card on table.");
                done = true;

            }

            if (!done && player == player1) {      // se verifica daca randul pe care trebuie plasata cartea este plin
                if ((minion.getMinionNormalCards().contains(card.getName()) && table.get(3).size() == 5) ||
                        (minion.getMinionSpecialCards().contains(card.getName()) && table.get(2).size() == 5)) {
                    DisplayError displayError = new DisplayError();
                    displayError.displayErrorPlaceCard(output, "Cannot place card on table since row is full.");
                    done = true;
                }
            }
            if (!done && player == player2) {
                if ((minion.getMinionNormalCards().contains(card.getName()) && table.get(0).size() == 5) ||
                        (minion.getMinionSpecialCards().contains(card.getName()) && table.get(1).size() == 5)) {
                    DisplayError displayError = new DisplayError();
                    displayError.displayErrorPlaceCard(output, "Cannot place card on table since row is full.");
                    done = true;
                }
            }

            // altfel plaseaza cartea
            //PLACE CARD PROPRIU-ZIS
            // STERG CARTEA DIN HAND-UL playerului si o pun pe randul potrivit
            if (!done) {
                if (player == player1) {
                    if (minion.getMinionNormalCards().contains(card.getName())) {
                        ArrayList<CardInputData> row = table.get(3);
                        row.add(hand.remove(handIdx));
                    } else {
                        ArrayList<CardInputData> row = table.get(2);
                        row.add(hand.remove(handIdx));
                    }
                    player1.changeManaPlayer(-card.getMana());
                } else {
                    if (minion.getMinionNormalCards().contains(card.getName())) {
                        ArrayList<CardInputData> row = table.get(0);
                        row.add(hand.remove(handIdx));
                    } else {
                        ArrayList<CardInputData> row = table.get(1);
                        row.add(hand.remove(handIdx));
                    }
                    player2.changeManaPlayer(-card.getMana());
                }
            }
        }


    }

    public void getPlayerMana(int playerIdx, Player player1, Player player2, ArrayNode output) {
        int playerMana = player1.getMana();

        if (playerIdx == 2) {
            playerMana = player2.getMana();
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getPlayerMana");
        outputCommand.put("playerIdx", playerIdx);
        outputCommand.put("output", playerMana);

        output.add(outputCommand);
    }

}
