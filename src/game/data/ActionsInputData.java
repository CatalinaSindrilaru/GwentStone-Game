package game.data;

import fileio.ActionsInput;

public class ActionsInputData {
    private String command;
    private int handIdx;
    private CoordinatesData cardAttacker;
    private CoordinatesData cardAttacked;
    private int affectedRow;
    private int playerIdx;
    private int x;
    private int y;

    public ActionsInputData() {

    }
    public ActionsInputData(ActionsInput action) {
        this.command = new String(action.getCommand());
        this.handIdx = action.getHandIdx();
        this.cardAttacker = new CoordinatesData(action.getCardAttacker());
        this.cardAttacked = new CoordinatesData(action.getCardAttacked());
        this.affectedRow = action.getAffectedRow();
        this.playerIdx = action.getPlayerIdx();
        this.x = action.getX();
        this.y = action.getY();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(int handIdx) {
        this.handIdx = handIdx;
    }

    public CoordinatesData getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(CoordinatesData cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public CoordinatesData getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(CoordinatesData cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
