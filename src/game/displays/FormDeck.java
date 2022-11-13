package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CardInputData;

import java.util.ArrayList;

public class FormDeck {

    public ArrayNode addDeckInOutput(ArrayList<CardInputData> deck) {
        ObjectMapper mapper = new ObjectMapper();
        //ObjectNode deckDetails = mapper.createObjectNode();

        ArrayNode cardArray = mapper.createArrayNode();

        for (CardInputData card : deck) {
            FormCard displayCard = new FormCard();
            ObjectNode cardDetails = displayCard.addCardInOutput(card);

            cardArray.add(cardDetails);
        }

        return cardArray;
    }
}
