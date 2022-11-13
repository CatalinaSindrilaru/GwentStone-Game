package game.gameStrategy;

import fileio.CardInput;
import game.data.CardInputData;

import java.util.ArrayList;

public class Player {

    private ArrayList<CardInputData> hand = new ArrayList<>();
    private static int mana;
    private static int totalGames;
    private static int WinnedGames;

    public ArrayList<CardInputData> getHand() {
        return hand;
    }

    public void setHand(ArrayList<CardInputData> hand) {
        this.hand = hand;
    }

    public static int getMana() {
        return mana;
    }

    public static void setMana(int mana) {
        Player.mana = mana;
    }

    public static int getTotalGames() {
        return totalGames;
    }

    public static void setTotalGames(int totalGames) {
        Player.totalGames = totalGames;
    }

    public static int getWinnedGames() {
        return WinnedGames;
    }

    public static void setWinnedGames(int winnedGames) {
        WinnedGames = winnedGames;
    }
}
