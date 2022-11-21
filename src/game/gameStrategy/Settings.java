package game.gameStrategy;

import game.data.CardInputData;

import java.util.ArrayList;

/**
 * Contains method which initialize values when a turn ends
 */
public class Settings {

    /**
     * Set froze and attack fields of a card back to 0 and also change player
     * @param currentGame current game that the player's have
     * @param hero card of the player
     */
    public void initializingChangeTurn(final Game currentGame, final CardInputData hero) {

        int startingRow = 0;
        if (currentGame.getCurrentPlayer() == 1) {
            startingRow = 2;
        }

        for (int i = startingRow; i <= startingRow + 1; i++) {
            ArrayList<CardInputData> row = currentGame.getTable().get(i);
            for (CardInputData card : row) {
                card.setFrozen(0);
                card.setAttack(0);
                hero.setAttack(0);
            }
        }

        if (currentGame.getCurrentPlayer() == 1) {
            currentGame.changePlayer(2);
        } else {
            currentGame.changePlayer(1);
        }
    }
}
