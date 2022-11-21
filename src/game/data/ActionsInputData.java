package game.data;

import fileio.ActionsInput;

/**
 * Contains information for the actions in each game
 */
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

    /**
     * Copy constructor
     * @param action contains all the commands for the game
     */
    public ActionsInputData(final ActionsInput action) {
        this.command = new String(action.getCommand());
        this.handIdx = action.getHandIdx();
        if (action.getCardAttacker() != null) {
            this.cardAttacker = new CoordinatesData(action.getCardAttacker());
        }

        if (action.getCardAttacked() != null) {
            this.cardAttacked = new CoordinatesData(action.getCardAttacked());
        }

        this.affectedRow = action.getAffectedRow();
        this.playerIdx = action.getPlayerIdx();
        this.x = action.getX();
        this.y = action.getY();
    }

    /**
     * @return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command new value
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     * @return handIdx
     */
    public int getHandIdx() {
        return handIdx;
    }

    /**
     *
     * @param handIdx new value
     */
    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    /**
     * @return cardAttacker
     */
    public CoordinatesData getCardAttacker() {
        return cardAttacker;
    }

    /**
     * @param cardAttacker new value
     */
    public void setCardAttacker(final CoordinatesData cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    /**
     * @return cardAttacked
     */
    public CoordinatesData getCardAttacked() {
        return cardAttacked;
    }

    /**
     * @param cardAttacked new value
     */
    public void setCardAttacked(final CoordinatesData cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    /**
     * @return affectedRow
     */
    public int getAffectedRow() {
        return affectedRow;
    }

    /**
     * @param affectedRow new value
     */
    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    /**
     * @return playerIdx
     */
    public int getPlayerIdx() {
        return playerIdx;
    }

    /**
     * @param playerIdx new value
     */
    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    /**
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x new value
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y new value
     */
    public void setY(final int y) {
        this.y = y;
    }
}
