package game.data;

import fileio.ActionsInput;
import fileio.GameInput;

import java.util.ArrayList;

/**
 * Contains the information for one game
 */
public class GameInputData {
    private StartGameInputData startGame;
    private ArrayList<ActionsInputData> actions = new ArrayList<>();

    public GameInputData() {

    }

    /**
     * Copy constructor
     * @param game information about a game
     */
    public GameInputData(final GameInput game) {
        this.startGame = new StartGameInputData(game.getStartGame());
        for (ActionsInput action : game.getActions()) {
            ActionsInputData newAction = new ActionsInputData((action));
            this.actions.add(newAction);
        }
    }

    /**
     * @return startGame
     */
    public StartGameInputData getStartGame() {
        return startGame;
    }

    /**
     * @param startGame new value
     */
    public void setStartGame(final StartGameInputData startGame) {
        this.startGame = startGame;
    }

    /**
     * @return actions
     */
    public ArrayList<ActionsInputData> getActions() {
        return actions;
    }

    /**
     * @param actions new value
     */
    public void setActions(final ArrayList<ActionsInputData> actions) {
        this.actions = actions;
    }
}
