package game.gameStrategy;

import game.data.CardInputData;

import java.util.ArrayList;

/**
 * Class for one game, that contains the table for the current game,
 * the player turn and also the tour and round
 */
public class Game {
    public static final int MAX_NUMBER_ROWS = 4;
    public static final int MAX_NUMBER_COLS = 5;
    private ArrayList<ArrayList<CardInputData>> table;
    private int currentPlayer;

    private int round;
    private int turn;

    /**
     * Creates a new table (4 x 5)
     */
    public void createGameTable() {
        table = new ArrayList<>(MAX_NUMBER_ROWS);
        for (int i = 0; i < MAX_NUMBER_ROWS; i++) {
            ArrayList<CardInputData> row = new ArrayList<>(MAX_NUMBER_COLS);
            table.add(row);
        }
    }

    /**
     * Creates a new table and initialize tour and round with 1
     */
    public void newGame() {
        createGameTable();
        round = 1;
        turn = 1;
    }

    /**
     * Change the current player with another
     * @param player index of wanted player
     */
    public void changePlayer(final int player) {
        currentPlayer = player;
    }

    /**
     * Increase number of tours
     */
    public void increaseTurn() {
        turn += 1;
    }

    /**
     * Increase number of rounds
     */
    public void increaseRound() {
        round += 1;
    }

    /**
     * @return table
     */
    public ArrayList<ArrayList<CardInputData>> getTable() {
        return table;
    }

    /**
     * @return currentPlayer
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return round
     */
    public int getRound() {
        return round;
    }

    /**
     * @return turn
     */
    public int getTurn() {
        return turn;
    }

}
