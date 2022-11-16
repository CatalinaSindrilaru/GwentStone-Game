package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import game.data.CoordinatesData;

public class DisplayError {

    public void displayErrorPlaceCard(ArrayNode output, String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "placeCard");
        outputCommand.put("handIdx", 0);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

    public void displayErrorUseEnvironmentCard(ArrayNode output, int handIdx, int row, String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "useEnvironmentCard");
        outputCommand.put("handIdx", handIdx);
        outputCommand.put("affectedRow", row);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }

    public void displayErrorcardUsesAttack(ArrayNode output, CoordinatesData attacker, CoordinatesData attacked, String errorMessage) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode formAttacker = mapper.createObjectNode();
        formAttacker.put("x", attacker.getX());
        formAttacker.put("y", attacker.getY());

        ObjectNode formAttacked = mapper.createObjectNode();
        formAttacked.put("x", attacked.getX());
        formAttacked.put("y", attacked.getY());

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "cardUsesAttack");
        outputCommand.set("cardAttacker", formAttacker);
        outputCommand.set("cardAttacked", formAttacked);
        outputCommand.put("error", errorMessage);

        output.add(outputCommand);
    }
}
