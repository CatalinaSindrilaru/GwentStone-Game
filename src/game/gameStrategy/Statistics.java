package game.gameStrategy;

/**
 * Class that keeps records of the total games played and number of games that each player wined
 */
public class Statistics {

    private int playerOneWins;
    private int playerTwoWins;

    private int totalGamesPlayed;

    /**
     * Initialize values at the beginning of the first game
     */
    public Statistics() {
        playerOneWins = 0;
        playerTwoWins = 0;
        totalGamesPlayed = 0;
    }

    /**
     * Increase player one wins with 1
     */
    public void increasePlayerOneWins() {
        playerOneWins++;
    }

    /**
     * Increase player two wins with 1
     */
    public void increasePlayerTwoWins() {
        playerTwoWins++;
    }

    /**
     * Increase number of total games played with 1
     */
    public void increaseTotalGamesPlayed() {
        totalGamesPlayed++;
    }

    /**
     * @return playerOneWins
     */
    public int getPlayerOneWins() {
        return playerOneWins;
    }

    /**
     * @return playerTwoWins
     */
    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    /**
     * @return totalGamesPlayed
     */
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
}
