package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
}
