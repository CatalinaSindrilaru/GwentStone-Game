package game.card;

import game.data.CardInputData;
import game.gameStrategy.Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains names of the cards that can be placed on front/back rows on table,
 * the names of tank cards and also the methods for applying the abilities
 * of the special cards.
 */
public class Minion {
    private final ArrayList<String> frontCards =
            new ArrayList<>(Arrays.asList("The Ripper", "Miraj", "Goliath", "Warden"));

    private final ArrayList<String> backCards =
            new ArrayList<>(Arrays.asList("Sentinel", "Berserker", "The Cursed One", "Disciple"));

    private final ArrayList<String> tankCards =
            new ArrayList<>(Arrays.asList("Goliath", "Warden"));

    /**
     * Get names of the cards that should be placed on the front rows
     * @return frontCards
     */
    public ArrayList<String> getFrontCards() {
        return frontCards;
    }

    /**
     * Get names of the cards that should be placed on the back rows
     * @return backCards
     */
    public ArrayList<String> getBackCards() {
        return backCards;
    }

    /**
     * Get names of the tank cards
     * @return tankCards
     */
    public ArrayList<String> getTankCards() {
        return tankCards;
    }

    /**
     * Decrease the attack of the attacked card with 2
     * @param attackedCard card that use his ability
     */
    public void applyAbilityTheRipper(final CardInputData attackedCard) {

        attackedCard.setAttackDamage(attackedCard.getAttackDamage() - 2);
        /* if the attack is now a negative e number, set it to 0 */
        if (attackedCard.getAttackDamage() < 0) {
            attackedCard.setAttackDamage(0);
        }
    }

    /**
     * swaps his life with the life of an opposing minion
     * @param attackerCard card that use his ability
     * @param attackedCard card that is affected
     */
    public void applyAbilityMiraj(final CardInputData attackerCard,
                                  final CardInputData attackedCard) {

        int healthAttacker = attackerCard.getHealth();
        int healthAttacked = attackedCard.getHealth();

        attackerCard.setHealth(healthAttacked);
        attackedCard.setHealth(healthAttacker);
    }

    /**
     * Swap between the life of an opposing minion and the same minion's attack
     * @param currentGame current game that the player's have
     * @param attackedCard card that is affected
     * @param attackedX row of the attacked card
     * @param attackedY column of the attacked card
     */
    public void applyAbilityTheCursedOne(final Game currentGame, final CardInputData attackedCard,
                                         final int attackedX, final int attackedY) {
        int healthAttacked = attackedCard.getHealth();
        int attackDamageAttacked = attackedCard.getAttackDamage();

        attackedCard.setHealth(attackDamageAttacked);
        attackedCard.setAttackDamage(healthAttacked);

        /* Destroy the card from the table if it has no life */
        if (attackedCard.getHealth() <= 0) {
            currentGame.getTable().get(attackedX).remove(attackedY);
        }
    }

    /**
     * Increase the life of the attacked card with 2
     * @param attackedCard card that is affected
     */
    public void applyDisciple(final CardInputData attackedCard) {

        attackedCard.setHealth(attackedCard.getHealth() + 2);
    }
}
