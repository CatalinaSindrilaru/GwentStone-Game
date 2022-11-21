package game.card;

import game.data.CardInputData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains names of the environment cards and also the methods for applying the ability
 * of these cards.
 */
public class Environment {
    public static final int MAX_NUMBER_ROWS = 4;
    private final ArrayList<String> environmentCards =
            new ArrayList<>(Arrays.asList("Firestorm", "Heart Hound", "Winterfell"));

    /**
     * Get names of the environment cards
     * @return environmentCards
     */
    public ArrayList<String> getEnvironmentCards() {
        return environmentCards;
    }

    /**
     * Decrease the health to all the minions in the affected row with 1
     * @param table table (4 x 5) for the current game
     * @param affectedRow row that receives the changes
     */
    public void applyFirestorm(final ArrayList<ArrayList<CardInputData>> table,
                               final int affectedRow) {

        ArrayList<CardInputData> row = table.get(affectedRow);

        for (int i = 0; i < row.size(); i++) {
            CardInputData minionCard = row.get(i);
            /* Decrease the life of the card */
            int health = minionCard.getHealth();
            minionCard.setHealth(health - 1);

            /* Verifies if the card is out of life and destroy it in consequence */
            if (minionCard.getHealth() == 0) {
                row.remove(i);
                i--;
            }
        }
    }

    /**
     * The opponent's minion with the highest life (health) in the row is stolen and placed on
     * the player's "mirrored" row
     * @param table table (4 x 5) for the current game
     * @param affectedRow row that receives the changes
     */
    public void applyHeartHound(final ArrayList<ArrayList<CardInputData>> table,
                                final int affectedRow) {

        ArrayList<CardInputData> row = table.get(affectedRow);

        int position = 0;
        int maxHealth = Integer.MIN_VALUE;
        /* Finds the minion with the highest health */
        for (int i = 0; i < row.size(); i++) {
            if (maxHealth < row.get(i).getHealth()) {
                maxHealth = row.get(i).getHealth();
                position = i;
            }
        }

        int myRow = MAX_NUMBER_ROWS - 1 - affectedRow; /* player's "mirrored" row */
        /* Delete the minion from the enemy row and place it on the player's row */
        table.get(myRow).add(row.remove(position));
    }

    /**
     * All cards in a row stand for one turn
     * @param table table (4 x 5) for the current game
     * @param affectedRow row that receives the changes
     */
    public void applyWinterfell(final ArrayList<ArrayList<CardInputData>> table,
                                final int affectedRow) {

        ArrayList<CardInputData> row = table.get(affectedRow);
        /* Set each card as being frozen on that row */
        for (CardInputData minionCard : row) {
            minionCard.setFrozen(1);
        }
    }
}
