package game.data;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class DecksInputData {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<CardInputData>> decks = new ArrayList<>();

    public DecksInputData() {

    }
    public DecksInputData(DecksInput decksInput) {
        this.nrCardsInDeck = decksInput.getNrCardsInDeck();
        this.nrDecks = decksInput.getNrDecks();

        for (ArrayList<CardInput> deck : decksInput.getDecks()) {

            ArrayList<CardInputData> new_deck = new ArrayList<>();
            for (CardInput card : deck) {
                CardInputData new_card = new CardInputData(card);
                new_deck.add(new_card);
            }

            this.decks.add(new_deck);
        }
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<CardInputData>> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<ArrayList<CardInputData>> decks) {
        this.decks = decks;
    }
}
