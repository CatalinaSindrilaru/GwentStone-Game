package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CardInputData;

public class DisplayGetPlayerHero {

    public void displayGetPlayerHero(CardInputData hero, int playerIdx, ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getPlayerHero");
        outputCommand.put("playerIdx", playerIdx);

        FormCard formCard = new FormCard();
        ObjectNode cardFormed = formCard.addCardInOutput(hero);

        outputCommand.set("output", cardFormed);

        output.add(outputCommand);
    }
}
