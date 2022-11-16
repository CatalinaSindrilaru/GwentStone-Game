package game.card.minion;

import game.data.CardInputData;

import java.util.ArrayList;

public class Minion extends CardInputData {
    //TODO
    private ArrayList<String> minionNormalCards = new ArrayList<>();
    private ArrayList<String> minionSpecialCards = new ArrayList<>();

    private ArrayList<String> frontCards = new ArrayList<>();

    private ArrayList<String> backCards = new ArrayList<>();

    private ArrayList<String> tankCards = new ArrayList<>();

    public Minion(){
        minionNormalCards.add("Sentinel");
        minionNormalCards.add("Berserker");
        minionNormalCards.add("Goliath");
        minionNormalCards.add("Warden");

        minionSpecialCards.add("The Ripper");
        minionSpecialCards.add("Miraj");
        minionSpecialCards.add("The Cursed One");
        minionSpecialCards.add("Disciple");

        frontCards.add("The Ripper");
        frontCards.add("Miraj");
        frontCards.add("Goliath");
        frontCards.add("Warden");

        backCards.add("Sentinel");
        backCards.add("Berserker");
        backCards.add("The Cursed One");
        backCards.add("Disciple");

        tankCards.add("Goliath");
        tankCards.add("Warden");

    }

    public ArrayList<String> getMinionNormalCards() {
        return minionNormalCards;
    }
    public ArrayList<String> getMinionSpecialCards() {
        return minionSpecialCards;
    }

    public ArrayList<String> getFrontCards() {
        return frontCards;
    }

    public ArrayList<String> getBackCards() {
        return backCards;
    }

    public ArrayList<String> getTankCards() {
        return tankCards;
    }
}
