package game.data;

import fileio.StartGameInput;

public class StartGameInputData {
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;
    private CardInputData playerOneHero;
    private CardInputData playerTwoHero;
    private int startingPlayer;

    public StartGameInputData() {

    }
    public StartGameInputData(StartGameInput start_game) {
        this.playerOneDeckIdx = start_game.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = start_game.getPlayerTwoDeckIdx();
        this.shuffleSeed = start_game.getShuffleSeed();
        this.playerOneHero = new CardInputData(start_game.getPlayerOneHero());
        this.playerTwoHero = new CardInputData(start_game.getPlayerTwoHero());
        this.startingPlayer = start_game.getStartingPlayer();
    }

    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    public void setPlayerOneDeckIdx(int playerOneDeckIdx) {
        this.playerOneDeckIdx = playerOneDeckIdx;
    }

    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    public void setPlayerTwoDeckIdx(int playerTwoDeckIdx) {
        this.playerTwoDeckIdx = playerTwoDeckIdx;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public CardInputData getPlayerOneHero() {
        return playerOneHero;
    }

    public void setPlayerOneHero(CardInputData playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    public CardInputData getPlayerTwoHero() {
        return playerTwoHero;
    }

    public void setPlayerTwoHero(CardInputData playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
