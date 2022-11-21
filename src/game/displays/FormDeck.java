package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CardInputData;

import java.util.ArrayList;

/**
 * Contain method that form a deck (as an ArrayNode) with all the cards that it have
 */
public class FormDeck {

    /**
     * Return an ArrayNode that contains all the cards from the given ArrayList
     * @param deck that need to be displayed
     * @return ArrayNode
     */
    public ArrayNode addDeckInOutput(final ArrayList<CardInputData> deck) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode cardArray = mapper.createArrayNode();

        for (CardInputData card : deck) {
            FormCard displayCard = new FormCard();
            ObjectNode cardDetails = displayCard.addCardInOutput(card);

            cardArray.add(cardDetails);
        }
        return cardArray;
    }
}
