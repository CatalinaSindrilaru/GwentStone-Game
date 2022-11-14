package game.gameStrategy;

import fileio.CardInput;
import game.data.CardInputData;

import java.util.ArrayList;

public class Player {

    private ArrayList<CardInputData> hand = new ArrayList<>();
    private int mana;
    private int totalGames;
    private int WinnedGames;

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

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWinnedGames() {
        return WinnedGames;
    }

    public void setWinnedGames(int winnedGames) {
        WinnedGames = winnedGames;
    }
}
