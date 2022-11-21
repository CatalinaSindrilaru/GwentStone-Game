package game.gameStrategy;

import com.fasterxml.jackson.databind.node.ArrayNode;
import game.card.Environment;
import game.card.Minion;
import game.data.CardInputData;
import game.data.CoordinatesData;
import game.displays.DisplayError;

import java.util.ArrayList;

/**
 * Class that contains methods that verify all the errors that a command can meet
 */
public class VerifyErrors {
    /* first player has rows 2 and 3 */
    public static final int FIRST_ROW_PLAYER1 = 2;
    /* second player has rows 0 and 1 */
    public static final int FIRST_ROW_PLAYER2 = 0;
    /* a full row has 5 cards */
    public static final int FULL_ROW = 5;

    /**
     *
     * @param currentGame current game that the players have
     * @param player current player
     * @param card card the player wants to put on the table
     * @param output the final output in which it is written
     * @return int ( 1 if an error occurred, else 0 )
     */
    public int errorsPlaceCard(final Game currentGame, final Player player,
                               final CardInputData card, final ArrayNode output) {

        Environment environment = new Environment();
        Minion minion = new Minion();
        DisplayError displayError = new DisplayError();
        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        /* check if the book is of the environment type */
        if (environment.getEnvironmentCards().contains(card.getName())) {

            displayError.displayErrorPlaceCard(output,
                    "Cannot place environment card on table.");
            return 1;
        }

        /* check if the card has a lower cost than the player's mana */
        if (card.getMana() > player.getMana()) {

            displayError.displayErrorPlaceCard(output,
                    "Not enough mana to place card on table.");
            return 1;
        }

        /* it is checked if the row on which the card must be placed is full */
        if (currentGame.getCurrentPlayer() == 1) {
            if ((minion.getBackCards().contains(card.getName())
                    && table.get(FIRST_ROW_PLAYER1 + 1).size() == FULL_ROW)
                    || (minion.getFrontCards().contains(card.getName())
                    && table.get(FIRST_ROW_PLAYER1).size() == FULL_ROW)) {

                displayError.displayErrorPlaceCard(output,
                        "Cannot place card on table since row is full.");
                return 1;
            }

        } else {
            if ((minion.getBackCards().contains(card.getName())
                    && table.get(FIRST_ROW_PLAYER2).size() == FULL_ROW)
                    || (minion.getFrontCards().contains(card.getName())
                    && table.get(FIRST_ROW_PLAYER2 + 1).size() == FULL_ROW)) {

                displayError.displayErrorPlaceCard(output,
                        "Cannot place card on table since row is full.");
                return 1;
            }
        }
        return 0;
    }

    /**
     *
     * @param currentGame current game that the players have
     * @param player current player
     * @param card the card the player wants to use
     * @param handIdx index of the card from the hand
     * @param affectedRow row that receives the changes
     * @param output the final output in which it is written
     * @return int ( 1 if an error occurred, else 0 )
     */
    public int errorsUseEnvironmentCard(final Game currentGame, final Player player,
                                        final CardInputData card, final int handIdx,
                                        final int affectedRow, final ArrayNode output) {

        Environment environment = new Environment();
        DisplayError displayError = new DisplayError();

        /* Check if the chosen index does NOT correspond to an environment type card
         in the list of cards in the hand*/
        if (!environment.getEnvironmentCards().contains(card.getName())) {

            displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow,
                    "Chosen card is not of type environment.");
            return 1;
        }

        /* check if the card has a lower cost than the player's mana */
        if (card.getMana() > player.getMana()) {

            displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow,
                    "Not enough mana to use environment card.");
            return 1;
        }

        /* Check if the affected row belong to the enemy */
        if (((currentGame.getCurrentPlayer() == 1) && (affectedRow == FIRST_ROW_PLAYER1
                || affectedRow == FIRST_ROW_PLAYER1 + 1)) || ((currentGame.getCurrentPlayer() == 2)
                && (affectedRow == FIRST_ROW_PLAYER2 || affectedRow == FIRST_ROW_PLAYER2 + 1))) {

            displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow,
                    "Chosen row does not belong to the enemy.");
            return 1;
        }


        /* check if I have somewhere to put the stolen player */
        if (card.getName().compareTo("Heart Hound") == 0) {
            int myRow = FIRST_ROW_PLAYER1 + 1 - affectedRow;

            if (currentGame.getTable().get(myRow).size() == FULL_ROW) {
                displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow,
                        "Cannot steal enemy card since the player's row is full.");
                return 1;
            }
        }

        return 0;
    }

    /**
     *
     * @param currentGame current game that the players have
     * @param attackerCard card that attack
     * @param attackedCard card that is attacked
     * @param attacker coordinates (row and column) for card that attack
     * @param attacked coordinates (row and column) for card that is attacked
     * @param output the final output in which it is written
     * @return int ( 1 if an error occurred, else 0 )
     */
    public int errorsCardUsesAttack(final Game currentGame, final CardInputData attackerCard,
                                    final CardInputData attackedCard,
                                    final CoordinatesData attacker,
                                    final CoordinatesData attacked, final ArrayNode output) {

        DisplayError displayError = new DisplayError();

        /* check if the attacked card belong to the enemy */
        if ((currentGame.getCurrentPlayer() == 1 && attacker.getX() >= FIRST_ROW_PLAYER1
                && attacked.getX() >= FIRST_ROW_PLAYER1) || (currentGame.getCurrentPlayer() == 2
                && attacker.getX() <= FIRST_ROW_PLAYER2 + 1
                && attacked.getX() <= FIRST_ROW_PLAYER2 + 1)) {

            displayError.displayErrorCardUsesAttackAbility("cardUsesAttack", output,
                    attacker, attacked, "Attacked card does not belong to the enemy.");
            return 1;
        }

        /* check if the card has already attacked in the current turn */
        if (attackerCard.getAttack() == 1) {

                displayError.displayErrorCardUsesAttackAbility("cardUsesAttack", output,
                        attacker, attacked, "Attacker card has already attacked this turn.");
                return 1;
        }

        /* check if the card is frozen */
        if (attackerCard.getFrozen() == 1) {

            displayError.displayErrorCardUsesAttackAbility("cardUsesAttack", output,
                    attacker, attacked, "Attacker card is frozen.");
            return 1;
        }

        Minion minionCards = new Minion();
        ArrayList<String> tankCards = minionCards.getTankCards();

        /* Check if the attacked card is not tank */
        if (!tankCards.contains(attackedCard.getName())) {
            /* check if there is a Tank card in the opponent rows */
            int startingRow = FIRST_ROW_PLAYER2;
            if (currentGame.getCurrentPlayer() == 2) {
                startingRow = FIRST_ROW_PLAYER1;
            }

            for (int i = startingRow; i <= startingRow + 1; i++) {
                ArrayList<CardInputData> row = currentGame.getTable().get(i);
                for (CardInputData card : row) {
                    if (tankCards.contains(card.getName())) {
                        displayError.displayErrorCardUsesAttackAbility("cardUsesAttack", output,
                                attacker, attacked, "Attacked card is not of type 'Tank'.");
                        return 1;
                    }
                }
            }
        }

        return 0;
    }

    /**
     *
     * @param currentGame current game that the players have
     * @param attackerCard card that attack
     * @param attackedCard card that is attacked
     * @param attacker coordinates (row and column) for card that attack
     * @param attacked coordinates (row and column) for card that is attacked
     * @param output the final output in which it is written
     * @return int ( 1 if an error occurred, else 0 )
     */
    public int errorsCardUsesAbility(final Game currentGame, final CardInputData attackerCard,
                                     final CardInputData attackedCard,
                                     final CoordinatesData attacker,
                                     final CoordinatesData attacked, final ArrayNode output) {

        DisplayError displayError = new DisplayError();

        /* check if the card is frozen */
        if (attackerCard.getFrozen() == 1) {

            displayError.displayErrorCardUsesAttackAbility("cardUsesAbility", output,
                    attacker, attacked, "Attacker card is frozen.");
            return 1;
        }

        /* check if the card has already attacked in the current turn */
        if (attackerCard.getAttack() == 1) {

            displayError.displayErrorCardUsesAttackAbility("cardUsesAbility", output,
                    attacker, attacked, "Attacker card has already attacked this turn.");
            return 1;
        }

        /* If the card is Disciple, check if the attacked card belong to rhe current player */
        if (attackerCard.getName().compareTo("Disciple") == 0) {
            if ((currentGame.getCurrentPlayer() == 1 && !(attacker.getX() >= 2
                    && attacked.getX() >= 2)) || (currentGame.getCurrentPlayer() == 2
                    && !(attacker.getX() <= 1 && attacked.getX() <= 1))) {

                displayError.displayErrorCardUsesAttackAbility("cardUsesAbility", output, attacker,
                        attacked, "Attacked card does not belong to the current player.");
                return 1;
            }
        } else {
            /* Check if the card belong to the enemy */
            if ((currentGame.getCurrentPlayer() == 1 && attacker.getX() >= 2
                    && attacked.getX() >= 2) || (currentGame.getCurrentPlayer() == 2
                    && attacker.getX() <= 1 && attacked.getX() <= 1)) {

                displayError.displayErrorCardUsesAttackAbility("cardUsesAbility", output,
                        attacker, attacked, "Attacked card does not belong to the enemy.");
                return 1;
            }
        }

        Minion minionCards = new Minion();
        ArrayList<String> tankCards = minionCards.getTankCards();

        /* Check if the attacked card is not tank */
        if (!tankCards.contains(attackedCard.getName())
                && attackerCard.getName().compareTo("Disciple") != 0) {
            /* check if there is a Tank card in the opponent rows */
            int startingRow = FIRST_ROW_PLAYER2;
            if (currentGame.getCurrentPlayer() == 2) {
                startingRow = FIRST_ROW_PLAYER1;
            }

            for (int i = startingRow; i <= startingRow + 1; i++) {
                ArrayList<CardInputData> row = currentGame.getTable().get(i);
                for (CardInputData card : row) {
                    if (tankCards.contains(card.getName())) {
                        displayError.displayErrorCardUsesAttackAbility("cardUsesAbility", output,
                                attacker, attacked, "Attacked card is not of type 'Tank'.");
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    /**
     *
     * @param currentGame current game that the players have
     * @param attackerCard card that attack
     * @param attacker coordinates (row and column) for card that attack
     * @param output the final output in which it is written
     * @return int ( 1 if an error occurred, else 0 )
     */
    public int errorsUseAttackHero(final Game currentGame, final CardInputData attackerCard,
                                   final CoordinatesData attacker, final ArrayNode output) {

        DisplayError displayError = new DisplayError();

        /* check if the card is frozen */
        if (attackerCard.getFrozen() == 1) {
            displayError.displayErrorAttackHero(output, attacker,
                    "Attacker card is frozen.");
            return 1;
        }

        /* check if the card has already attacked in the current turn */
        if (attackerCard.getAttack() == 1) {
            displayError.displayErrorAttackHero(output, attacker,
                    "Attacker card has already attacked this turn.");
            return 1;
        }

        Minion minionCards = new Minion();
        ArrayList<String> tankCards = minionCards.getTankCards();

        /* check if there is a Tank card in the opponent rows */
        int startingRow = FIRST_ROW_PLAYER2;
        if (currentGame.getCurrentPlayer() == 2) {
            startingRow = FIRST_ROW_PLAYER1;
        }

        for (int i = startingRow; i <= startingRow + 1; i++) {
            ArrayList<CardInputData> row = currentGame.getTable().get(i);

            for (CardInputData card : row) {
                if (tankCards.contains(card.getName())) {
                    displayError.displayErrorAttackHero(output, attacker,
                            "Attacked card is not of type 'Tank'.");
                    return 1;
                }
            }
        }

        return 0;
    }

    /**
     *
     * @param currentGame current game that the players have
     * @param player current player
     * @param hero hero of the current player
     * @param affectedRow row that receives the changes
     * @param output the final output in which it is written
     * @return int ( 1 if an error occurred, else 0 )
     */
    public int useHeroAbility(final Game currentGame, final Player player,
                              final CardInputData hero, final int affectedRow,
                              final ArrayNode output) {

        DisplayError displayError = new DisplayError();

        /* check if the card has a lower cost than the player's mana */
        if (player.getMana() < hero.getMana()) {
            displayError.displayErrorUseAbilityHero(output, affectedRow,
                    "Not enough mana to use hero's ability.");
            return 1;
        }

        /* check if the card has already attacked in the current turn */
        if (hero.getAttack() == 1) {
            displayError.displayErrorUseAbilityHero(output, affectedRow,
                    "Hero has already attacked this turn.");
            return 1;
        }

        /* if the hero is "Lord Royce" or "Empress Thorina", check if the affected row
         belong to the enemy */
        if (hero.getName().compareTo("Lord Royce") == 0
                || hero.getName().compareTo("Empress Thorina") == 0) {

            if ((currentGame.getCurrentPlayer() == 1 && affectedRow >= FIRST_ROW_PLAYER1)
                    || (currentGame.getCurrentPlayer() == 2
                    && affectedRow <= FIRST_ROW_PLAYER2 + 1)) {

                displayError.displayErrorUseAbilityHero(output, affectedRow,
                        "Selected row does not belong to the enemy.");
                return 1;
            }
        }

        /* if the hero is "General Kocioraw" or "King Mudface", check if the affected row
         belong to the current player */
        if (hero.getName().compareTo("General Kocioraw") == 0
                || hero.getName().compareTo("King Mudface") == 0) {

            if ((currentGame.getCurrentPlayer() == 1 && affectedRow <= FIRST_ROW_PLAYER2 + 1)
                    || (currentGame.getCurrentPlayer() == 2 && affectedRow >= FIRST_ROW_PLAYER1)) {

                displayError.displayErrorUseAbilityHero(output, affectedRow,
                        "Selected row does not belong to the current player.");
                return 1;

            }
        }

        return 0;
    }
}
