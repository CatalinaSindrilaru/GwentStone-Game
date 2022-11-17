package game.gameStrategy;

public class Statistics {

    private int playerOneWins;
    private int playerTwoWins;

    private int totalGamesPlayed;

    public Statistics() {
        playerOneWins = 0;
        playerTwoWins = 0;
        totalGamesPlayed = 0;
    }

    public void increasePlayerOneWins() {
        playerOneWins++;
    }

    public void increasePlayerTwoWins() {
        playerTwoWins++;
    }

    public void increaseTotalGamesPlayed() {
        totalGamesPlayed++;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }
}
