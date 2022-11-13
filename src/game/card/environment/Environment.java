package game.card.environment;

import game.data.CardInputData;

import java.util.ArrayList;

public class Environment extends CardInputData {
    private ArrayList<String> environmentCards = new ArrayList<>();

    public Environment() {
        environmentCards.add("Firestorm");
        environmentCards.add("Heart Hound");
        environmentCards.add("Winterfell");
    }

    public ArrayList<String> getEnvironmentCards() {
        return environmentCards;
    }
}
