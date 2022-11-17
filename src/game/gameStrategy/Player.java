package game.gameStrategy;

import fileio.CardInput;
import game.data.CardInputData;

import java.util.ArrayList;

public class Player {

    private ArrayList<CardInputData> hand = new ArrayList<>();
    private int mana;

    public ArrayList<CardInputData> getHand() {
        return hand;
    }

    public void setHand(ArrayList<CardInputData> hand) {
        this.hand = hand;
    }

    public void changeManaPlayer(int value) {
        mana += value;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

}
