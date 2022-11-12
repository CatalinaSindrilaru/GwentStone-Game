package game.data;

import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public class InputData {
    private DecksInputData playerOneDecks;
    private DecksInputData playerTwoDecks;
    private ArrayList<GameInputData> games = new ArrayList<>();

    public InputData() {

    }
    public InputData(Input input) {
        this.playerOneDecks = new DecksInputData(input.getPlayerOneDecks());
        this.playerTwoDecks = new DecksInputData(input.getPlayerTwoDecks());

        for (GameInput game : input.getGames()) {
            GameInputData new_game = new GameInputData(game);
            this.games.add(new_game);
        }
    }

    public DecksInputData getPlayerOneDecks() {
        return playerOneDecks;
    }

    public void setPlayerOneDecks(DecksInputData playerOneDecks) {
        this.playerOneDecks = playerOneDecks;
    }

    public DecksInputData getPlayerTwoDecks() {
        return playerTwoDecks;
    }

    public void setPlayerTwoDecks(DecksInputData playerTwoDecks) {
        this.playerTwoDecks = playerTwoDecks;
    }

    public ArrayList<GameInputData> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameInputData> games) {
        this.games = games;
    }
}
