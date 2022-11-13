package game.card.minion;

import game.data.CardInputData;

import java.util.ArrayList;

public class Minion extends CardInputData {
    //TODO
    private ArrayList<String> minionCards = new ArrayList<>();

    public Minion(){
        minionCards.add("Sentinel");
        minionCards.add("Berserker");
        minionCards.add("Goliath");
        minionCards.add("Warden");
        minionCards.add("The Ripper");
        minionCards.add("Miraj");
        minionCards.add("The Cursed One");
        minionCards.add("Disciple");
    }

    public ArrayList<String> getMinionCards() {
        return minionCards;
    }
}
