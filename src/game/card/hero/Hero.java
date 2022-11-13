package game.card.hero;

import game.data.CardInputData;

import java.util.ArrayList;

public class Hero extends CardInputData {
    //todo
    private ArrayList<String> heroCards = new ArrayList<>();

    public Hero() {
        heroCards.add("Empress Thorina");
        heroCards.add("General Kocioraw");
        heroCards.add("King Mudface");
        heroCards.add("Lord Royce");
    }

    public ArrayList<String> getHeroCards() {
        return heroCards;
    }
}
