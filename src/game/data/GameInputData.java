package game.data;

import fileio.ActionsInput;
import fileio.GameInput;

import java.util.ArrayList;

public class GameInputData {
    private StartGameInputData startGame;
    private ArrayList<ActionsInputData> actions = new ArrayList<>();

    public GameInputData() {

    }
    public GameInputData(GameInput game) {
        this.startGame = new StartGameInputData(game.getStartGame());
        for (ActionsInput action : game.getActions()) {
            ActionsInputData action2 = new ActionsInputData((action));
            this.actions.add(action2);
        }
    }

    public StartGameInputData getStartGame() {
        return startGame;
    }

    public void setStartGame(StartGameInputData startGame) {
        this.startGame = startGame;
    }

    public ArrayList<ActionsInputData> getActions() {
        return actions;
    }

    public void setActions(ArrayList<ActionsInputData> actions) {
        this.actions = actions;
    }
}
