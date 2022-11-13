package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import game.data.ActionsInputData;
import game.data.CardInputData;
import game.data.GameInputData;
import game.data.InputData;
import game.displays.DisplayGetPlayerDeck;
import game.displays.DisplayGetPlayerHero;
import game.displays.DisplayGetPlayerTurn;
import game.gameStrategy.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SolveCommands {
    public void display(InputData inputData, ArrayNode output) {

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

            // scot carte

            // sterg prima carte din game si o oun in mana
            // runda = 1
            // tura = 1

            // mana pt jucator = 1
            // masa goala la inceput de joc

            Player player1 = new Player();
            Player player2 = new Player();

            player1.getHand().add(playerOneDeck.remove(0));
            player2.getHand().add(playerTwoDeck.remove(0));


            for (ActionsInputData action : actions) {
                String command = new String(action.getCommand());

//                if (command.compareTo("getCardsInHand") == 0) {
//                    //todo
//                } else
                if (command.compareTo("getPlayerDeck") == 0) {

                    getPlayerDeck(inputData, game, action, output);
                    // functie afisare carti
//                    System.out.println(file);
//                    for (CardInputData card : deck) {
//                        System.out.println(card.getDescription());
//                    }
                }
//                } else if (command.compareTo("getCardsOnTable") == 0) {
//                    //todo
//                } else
                if (command.compareTo("getPlayerTurn") == 0) {
                    //todo
                    getPlayerTurn(game, output);
//                    System.out.println(file);
//                    System.out.println(getPlayerTurn(game));
                }
//                } else
                if (command.compareTo("getPlayerHero") == 0) {
                    //todo
                    getPlayerHero(game, action, output);
//                    System.out.println(file);
//                    System.out.println(card.getName());
                }
//                } else if (command.compareTo("getCardAtPosition") == 0) {
//                    //todo
//                } else if (command.compareTo("getPlayerMana") == 0) {
//                    //todo
//                } else if (command.compareTo("getEnvironmentCardsInHand") == 0) {
//                    //todo
//                } else if (command.compareTo("getFrozenCardsOnTable") == 0) {
//                    //todo
//                }
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
//            return decks.get(playerDeckIdx);
            DisplayGetPlayerDeck displayGetPlayerDeck = new DisplayGetPlayerDeck();
            displayGetPlayerDeck.displayGetPlayerDeck(decks.get(playerDeckIdx), playerIdx, output);

        } else if (playerIdx == 2) {
            decks = inputData.getPlayerTwoDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();
//            return decks.get(playerDeckIdx);
            DisplayGetPlayerDeck displayGetPlayerDeck = new DisplayGetPlayerDeck();
            displayGetPlayerDeck.displayGetPlayerDeck(decks.get(playerDeckIdx), playerIdx, output);
        }

        // apelare display -- command, playerIdx, decks.get(playerDeckIdx)
//        DisplayGetPlayerDeck displayGetPlayerDeck = new DisplayGetPlayerDeck();
//        displayGetPlayerDeck.displayGetPlayerDeck(decks.get(playerDeckIdx), playerIdx, output);
        // adaugare neapaart in output
//        return null;
    }

    public void getPlayerHero(GameInputData game, ActionsInputData action, ArrayNode output) {
        int playerIdx = action.getPlayerIdx();

        CardInputData hero;
        if (playerIdx == 1) {
            hero = game.getStartGame().getPlayerOneHero();
            //
            DisplayGetPlayerHero displayGetPlayerHero = new DisplayGetPlayerHero();
            displayGetPlayerHero.displayGetPlayerHero(hero, playerIdx, output);

        } else if (playerIdx == 2) {
            hero = game.getStartGame().getPlayerTwoHero();
            DisplayGetPlayerHero displayGetPlayerHero = new DisplayGetPlayerHero();
            displayGetPlayerHero.displayGetPlayerHero(hero, playerIdx, output);
        }

    }

    //merge doar pt test1
    public void getPlayerTurn(GameInputData game, ArrayNode output) {
        int playerTurn;
        playerTurn = game.getStartGame().getStartingPlayer();

        DisplayGetPlayerTurn displayGetPlayerTurn = new DisplayGetPlayerTurn();
        displayGetPlayerTurn.displayGetPlayerTurn(playerTurn, output);
    }
}
