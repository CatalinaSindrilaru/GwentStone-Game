package game.data;

import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

/**
 * Contains the deck for each player and also all the games that the
 * player's can play
 */
public class InputData {
    private DecksInputData playerOneDecks;
    private DecksInputData playerTwoDecks;
    private ArrayList<GameInputData> games = new ArrayList<>();

    public InputData() {

    }

    /**
     * Copy Constructor
     * @param input information about games and decks
     */
    public InputData(final Input input) {
        this.playerOneDecks = new DecksInputData(input.getPlayerOneDecks());
        this.playerTwoDecks = new DecksInputData(input.getPlayerTwoDecks());

        for (GameInput game : input.getGames()) {
            GameInputData newGame = new GameInputData(game);
            this.games.add(newGame);
        }
    }

    /**
     * @return playerOneDecks
     */
    public DecksInputData getPlayerOneDecks() {
        return playerOneDecks;
    }

    /**
     * @param playerOneDecks new value
     */
    public void setPlayerOneDecks(final DecksInputData playerOneDecks) {
        this.playerOneDecks = playerOneDecks;
    }

    /**
     * @return playerTwoDecks
     */
    public DecksInputData getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    /**
     * @param playerTwoDecks new value
     */
    public void setPlayerTwoDecks(final DecksInputData playerTwoDecks) {
        this.playerTwoDecks = playerTwoDecks;
    }

    /**
     * @return games
     */
    public ArrayList<GameInputData> getGames() {
        return games;
    }

    /**
     * @param games new value
     */
    public void setGames(final ArrayList<GameInputData> games) {
        this.games = games;
    }
}
