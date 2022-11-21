package game.data;

import fileio.StartGameInput;

/**
 * Contains the necessary information to start a new game
 */
public class StartGameInputData {
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;
    private CardInputData playerOneHero;
    private CardInputData playerTwoHero;
    private int startingPlayer;

    public StartGameInputData() {

    }

    /**
     * Copy constructor
     * @param startGame necessary information to start a new game
     */
    public StartGameInputData(final StartGameInput startGame) {
        this.playerOneDeckIdx = startGame.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = startGame.getPlayerTwoDeckIdx();
        this.shuffleSeed = startGame.getShuffleSeed();
        this.playerOneHero = new CardInputData(startGame.getPlayerOneHero());
        this.playerTwoHero = new CardInputData(startGame.getPlayerTwoHero());
        this.startingPlayer = startGame.getStartingPlayer();
    }

    /**
     * @return playerOneDeckIdx
     */
    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    /**
     * @param playerOneDeckIdx new value
     */
    public void setPlayerOneDeckIdx(final int playerOneDeckIdx) {
        this.playerOneDeckIdx = playerOneDeckIdx;
    }

    /**
     * @return playerTwoDeckIdx
     */
    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    /**
     * @param playerTwoDeckIdx new value
     */
    public void setPlayerTwoDeckIdx(final int playerTwoDeckIdx) {
        this.playerTwoDeckIdx = playerTwoDeckIdx;
    }

    /**
     * @return shuffleSeed
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * @param shuffleSeed new value
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     * @return playerOneHero
     */
    public CardInputData getPlayerOneHero() {
        return playerOneHero;
    }

    /**
     * @param playerOneHero new hero
     */
    public void setPlayerOneHero(final CardInputData playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    /**
     * @return playerTwoHero
     */
    public CardInputData getPlayerTwoHero() {
        return playerTwoHero;
    }

    /**
     * @param playerTwoHero new value
     */
    public void setPlayerTwoHero(final CardInputData playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    /**
     * @return startingPlayer
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }

    /**
     * @param startingPlayer new value
     */
    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
