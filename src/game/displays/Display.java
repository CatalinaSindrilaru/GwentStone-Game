package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CardInputData;

import java.util.ArrayList;

/**
 * Contains methods for display a deck, a hero and the player turn
 */
public class Display {

    /**
     * Display all the card from an ArrayList of Cards to the output
     * @param deck that needs to be displayed
     * @param playerIdx the current player
     * @param output the final output in which it is written
     * @param command that requested the display
     */
    public void displayDeck(final ArrayList<CardInputData> deck, final int playerIdx,
                            final ArrayNode output, final String command) {

        /* Creates an ArrayNode that contains all the cards with necessary details*/
        FormDeck formDeck = new FormDeck();
        ArrayNode deckFormed = formDeck.addDeckInOutput(deck);

        /* Creates an ObjectNode that contains the command, the current player and the deck */
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", command);
        outputCommand.put("playerIdx", playerIdx);
        outputCommand.set("output", deckFormed);

        /* Add all to the final output */
        output.add(outputCommand);
    }

    /**
     * Display the hero of the current player
     * @param hero card for the current player
     * @param playerIdx current player
     * @param output the final output in which it is written
     */
    public void displayGetPlayerHero(final CardInputData hero, final int playerIdx,
                                     final ArrayNode output) {

        /* Creates an ObjectNode that contains all the details for a card */
        FormCard formCard = new FormCard();
        ObjectNode cardFormed = formCard.addCardInOutput(hero);

        /* Creates an ObjectNode that contains the command, the current player and the card */
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getPlayerHero");
        outputCommand.put("playerIdx", playerIdx);
        outputCommand.set("output", cardFormed);

        /* Add all to the final output */
        output.add(outputCommand);
    }

    /**
     * Displays the player who has not finished his turn yet
     * @param playerTurn current player
     * @param output the final output in which it is written
     */
    public void displayGetPlayerTurn(final int playerTurn, final ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getPlayerTurn");
        outputCommand.put("output", playerTurn);

        output.add(outputCommand);
    }
}
