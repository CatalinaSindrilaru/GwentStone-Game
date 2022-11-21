package game.gameStrategy;

import game.data.CardInputData;

import java.util.ArrayList;

/**
 * Class for a player that contains the hand and mana.
 */
public class Player {

    private ArrayList<CardInputData> hand = new ArrayList<>();
    private int mana;

    /**
     * Change the value of the player's mana
     * @param value that decrease/increase actual mana
     */
    public void changeManaPlayer(final int value) {
        mana += value;
    }

    /**
     * @return hand
     */
    public ArrayList<CardInputData> getHand() {
        return hand;
    }

    /**
     * @return mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param mana new value
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

}
