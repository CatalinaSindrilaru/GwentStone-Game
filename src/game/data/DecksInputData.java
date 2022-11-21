package game.data;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

/**
 * Contains the decks for each player
 */
public class DecksInputData {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<CardInputData>> decks = new ArrayList<>();

    public DecksInputData() {

    }

    /**
     * Copy constructor
     * @param decksInput information about all the decks in game
     */
    public DecksInputData(final DecksInput decksInput) {
        this.nrCardsInDeck = decksInput.getNrCardsInDeck();
        this.nrDecks = decksInput.getNrDecks();

        for (ArrayList<CardInput> deck : decksInput.getDecks()) {

            ArrayList<CardInputData> newDeck = new ArrayList<>();
            for (CardInput card : deck) {
                CardInputData newCard = new CardInputData(card);
                newDeck.add(newCard);
            }

            this.decks.add(newDeck);
        }
    }

    /**
     * @return nrCardsInDeck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     * @param nrCardsInDeck new value
     */
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    /**
     * @return nrDecks
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     * @param nrDecks new value
     */
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    /**
     * @return decks
     */
    public ArrayList<ArrayList<CardInputData>> getDecks() {
        return decks;
    }

    /**
     * @param decks new value
     */
    public void setDecks(final ArrayList<ArrayList<CardInputData>> decks) {
        this.decks = decks;
    }
}
