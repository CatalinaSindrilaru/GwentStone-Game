package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CardInputData;

import java.text.Normalizer;
import java.util.ArrayList;

public class DisplayGetPlayerDeck {

    public void displayGetPlayerDeck(ArrayList<CardInputData> deck, int playerIdx, ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getPlayerDeck");
        outputCommand.put("playerIdx", playerIdx);

        FormDeck formDeck = new FormDeck();
        ArrayNode deckFormed  = formDeck.addDeckInOutput(deck);

        outputCommand.set("output", deckFormed);

       output.add(outputCommand);
    }
}
