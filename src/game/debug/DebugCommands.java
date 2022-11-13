package game.debug;

import game.data.*;

import java.util.ArrayList;

public class DebugCommands {
    public ArrayList<CardInputData> getPlayerDeck(InputData inputData, GameInputData game, ActionsInputData action) {
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
            return decks.get(playerDeckIdx);

        } else if (playerIdx == 2) {
            decks = inputData.getPlayerTwoDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();
            return decks.get(playerDeckIdx);
        }

        return null;
    }
}
