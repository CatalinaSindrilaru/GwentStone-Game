package game.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.data.CardInputData;
import game.data.StartGameInputData;
import game.gameStrategy.Game;
import game.gameStrategy.Statistics;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains all the hero names and methods for applying their abilities and also
 * a method in case the hero dies
 */
public class Hero {
    //todo
    private final ArrayList<String> heroCards =
            new ArrayList<>(Arrays.asList("Empress Thorina",
                    "General Kocioraw", "King Mudface", "Lord Royce"));

    /**
     * Get names of the hero cards
     * @return heroCards
     */
    public ArrayList<String> getHeroCards() {
        return heroCards;
    }

    /**
     * Decreases the hero life of the enemy and verifies if his hero died,
     * meaning that the current player wins the game
     * @param currentGame current game that the players have
     * @param startGameInputData initial data for each game
     * @param message different for player1 or player2
     * @param attackerCard card that attack
     * @param statistics contains the player1 and player2 wins and also tne number
     *                  of all the games played
     * @param output the final output in which it is written
     */
    public void killHero(final Game currentGame, final StartGameInputData startGameInputData,
                         final String message, final CardInputData attackerCard,
                         final Statistics statistics, final ArrayNode output) {

        CardInputData heroEnemy = startGameInputData.getPlayerTwoHero();
        if (currentGame.getCurrentPlayer() == 2) {
            heroEnemy = startGameInputData.getPlayerOneHero();
        }

        /* Decrease the hero enemy life */
        int healthHero = heroEnemy.getHealth();
        int attackDamage = attackerCard.getAttackDamage();
        heroEnemy.setHealth(healthHero - attackDamage);

        /* Verifies if the hero died */
        if (heroEnemy.getHealth() <= 0) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode outputCommand = mapper.createObjectNode();
            outputCommand.put("gameEnded", message);

            output.add(outputCommand);

            if (currentGame.getCurrentPlayer() == 1) {
                statistics.increasePlayerOneWins();
            } else {
                statistics.increasePlayerTwoWins();
            }
        }

    }

    /**
     * Freeze the card with the highest attack of the row
     * @param row row that receives the changes
     */
    public void applyAbilityLord(final ArrayList<CardInputData> row) {

        int maxAttackDamage = Integer.MIN_VALUE;
        int position = 0;
        /* Finds the card with the highest attack */
        for (int i = 0; i < row.size(); i++) {
            CardInputData card = row.get(i);
            if (card.getAttackDamage() > maxAttackDamage) {
                maxAttackDamage = card.getAttackDamage();
                position = i;
            }
        }
        CardInputData card = row.get(position);
        /* Freeze that card */
        card.setFrozen(1);
    }

    /**
     * Destroy the card with the highest life in a row.
     * @param row row that receives the changes
     */
    public void applyAbilityThorina(final ArrayList<CardInputData> row) {

        int maxHealth = Integer.MIN_VALUE;
        int position = 0;
        /* Finds the card with the highest life on the row */
        for (int i = 0; i < row.size(); i++) {
            CardInputData card = row.get(i);
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                position = i;
            }
        }

        /* Destroy that card */
        row.remove(position);
    }

    /**
     * Increase the attack with 1 for all the cards in row
     * @param row row that receives the changes
     */
    public void applyAbilityKocioraw(final ArrayList<CardInputData> row) {

        for (CardInputData card : row) {
            int attackDamage = card.getAttackDamage();
            card.setAttackDamage(attackDamage + 1);
        }
    }

    /**
     * Increase the health  with 1 for all the cards in row
     * @param row row that receives the changes
     */
    public void applyAbilityKing(final ArrayList<CardInputData> row) {

        for (CardInputData card : row) {
            int health = card.getHealth();
            card.setHealth(health + 1);
        }
    }
}
