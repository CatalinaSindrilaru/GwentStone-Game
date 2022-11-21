package game.displays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.card.Environment;
import game.card.Hero;
import game.data.CardInputData;

/**
 * Contains method that form a card (as an ObjectNode) with all his information
 */
public class FormCard {

    /**
     * Return an ObjectNode that contains all the information for a card
     * @param card that needs to be formed as an ObjectNode
     * @return ObjectNode
     */
    public ObjectNode addCardInOutput(final CardInputData card) {

        Environment environmentCards = new Environment();
        Hero heroCards = new Hero();

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode cardDetails = mapper.createObjectNode();

        cardDetails.put("mana", card.getMana());

        /* If the card is a hero add just the health*/
        if (heroCards.getHeroCards().contains(card.getName())) {
            cardDetails.put("health", card.getHealth());

        } else if (!environmentCards.getEnvironmentCards().contains(card.getName())) {
            cardDetails.put("attackDamage", card.getAttackDamage());
            cardDetails.put("health", card.getHealth());
        }

        cardDetails.put("description", card.getDescription());

        /* Creates an ArrayNode and add all the colors that a card has */
        ArrayNode colors = mapper.createArrayNode();
        for (String color : card.getColors()) {
            colors.add(color);
        }

        cardDetails.set("colors", colors);
        cardDetails.put("name", card.getName());

        return cardDetails;
    }
}
