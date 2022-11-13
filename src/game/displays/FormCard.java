package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.card.environment.Environment;
import game.card.hero.Hero;
import game.data.CardInputData;

public class FormCard {

    public ObjectNode addCardInOutput(CardInputData card) {
        Environment environmentCards = new Environment();
        Hero heroCards = new Hero();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode cardDetails = mapper.createObjectNode();

        cardDetails.put("mana", card.getMana());

        if (heroCards.getHeroCards().contains(card.getName())) {
            cardDetails.put("health", card.getHealth());
        } else if (!environmentCards.getEnvironmentCards().contains(card.getName())) {
            cardDetails.put("attackDamage", card.getAttackDamage());
            cardDetails.put("health", card.getHealth());
        }

        cardDetails.put("description", card.getDescription());

        // parcugere colors
        ArrayNode colors = mapper.createArrayNode();
        for (String color : card.getColors()) {
            colors.add(color);
        }

        cardDetails.set("colors", colors);
        cardDetails.put("name", card.getName());

        return cardDetails;
    }
}
