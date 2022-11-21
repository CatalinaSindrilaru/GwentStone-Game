package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CoordinatesData;

/**
 * Contains methods that display errors for different commands in a game
 */
public class DisplayError {

    /**
     * Display appropriate information for error cases - command "Place Card"
     * @param output the final output in which it is written
     * @param errorMessage error message that need to be displayed
     */
    public void displayErrorPlaceCard(final ArrayNode output, final String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "placeCard");
        outputCommand.put("handIdx", 0);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

    /**
     * Display appropriate information for error cases - command "Use Environment Card"
     * @param output the final output in which it is written
     * @param handIdx index of the environment card in hand
     * @param row affected row
     * @param errorMessage error message that need to be displayed
     */
    public void displayErrorUseEnvironmentCard(final ArrayNode output, final int handIdx,
                                               final int row, final String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "useEnvironmentCard");
        outputCommand.put("handIdx", handIdx);
        outputCommand.put("affectedRow", row);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

    /**
     * Display appropriate information for error cases - command "Card Uses Attack / Ability"
     * @param command command that needs to display (Card Uses Attack / Card Uses Ability)
     * @param output the final output in which it is written
     * @param attacker coordinates (row and column) for the attacker card
     * @param attacked coordinates (row and column) for the attacked card
     * @param errorMessage error message that need to be displayed
     */
    public void displayErrorCardUsesAttackAbility(final String command, final ArrayNode output,
                                                  final CoordinatesData attacker,
                                                  final CoordinatesData attacked,
                                                  final String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode formAttacker = mapper.createObjectNode();
        formAttacker.put("x", attacker.getX());
        formAttacker.put("y", attacker.getY());

        ObjectNode formAttacked = mapper.createObjectNode();
        formAttacked.put("x", attacked.getX());
        formAttacked.put("y", attacked.getY());

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", command);
        outputCommand.set("cardAttacker", formAttacker);
        outputCommand.set("cardAttacked", formAttacked);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

    /**
     * Display appropriate information for error cases - command "Attack Hero"
     * @param output the final output in which it is written
     * @param attacker coordinates (row and column) for the attacker card
     * @param errorMessage error message that need to be displayed
     */
    public void displayErrorAttackHero(final ArrayNode output, final CoordinatesData attacker,
                                       final String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode formAttacker = mapper.createObjectNode();
        formAttacker.put("x", attacker.getX());
        formAttacker.put("y", attacker.getY());

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "useAttackHero");
        outputCommand.set("cardAttacker", formAttacker);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

    /**
     * Display appropriate information for error cases - command "Use Ability Hero"
     * @param output the final output in which it is written
     * @param row affected row
     * @param errorMessage error message that need to be displayed
     */
    public void displayErrorUseAbilityHero(final ArrayNode output, final int row,
                                           final String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "useHeroAbility");
        outputCommand.put("affectedRow", row);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

}
