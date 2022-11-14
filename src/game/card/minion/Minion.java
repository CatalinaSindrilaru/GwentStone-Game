package game.card.minion;

import game.data.CardInputData;

import java.util.ArrayList;

public class Minion extends CardInputData {
    //TODO
    private ArrayList<String> minionNormalCards = new ArrayList<>();
    private ArrayList<String> minionSpecialCards = new ArrayList<>();

    public Minion(){
        minionNormalCards.add("Sentinel");
        minionNormalCards.add("Berserker");
        minionNormalCards.add("Goliath");
        minionNormalCards.add("Warden");
        minionSpecialCards.add("The Ripper");
        minionSpecialCards.add("Miraj");
        minionSpecialCards.add("The Cursed One");
        minionSpecialCards.add("Disciple");
    }

    public ArrayList<String> getMinionNormalCards() {
        return minionNormalCards;
    }
    public ArrayList<String> getMinionSpecialCards() {
        return minionSpecialCards;
    }
}
