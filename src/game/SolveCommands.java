package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Input;
import game.card.Environment;
import game.card.Hero;
import game.card.Minion;
import game.data.ActionsInputData;
import game.data.CardInputData;
import game.data.GameInputData;
import game.data.InputData;
import game.data.StartGameInputData;
import game.displays.Display;
import game.displays.FormCard;
import game.displays.FormDeck;
import game.gameStrategy.Game;
import game.gameStrategy.Player;
import game.gameStrategy.Settings;
import game.gameStrategy.Statistics;
import game.gameStrategy.VerifyErrors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SolveCommands {
    /* In table, first player has third and fourth rows */
    public static final int FIRST_ROW_PLAYER1 = 2;
    /* In table, second player has first and second  rows */
    public static final int FIRST_ROW_PLAYER2 = 0;

    public static final int MAX_NUMBER_ROWS = 4;

    /**
     * Receive all the commands and call the appropriate methods
     * @param input copy of the initial data
     * @param output the final output in which it is written
     * @param theirData Initial data
     */
    public void display(final InputData input, final ArrayNode output, final Input theirData) {

        /* Extract all the games */
        ArrayList<GameInputData> games = input.getGames();
        Statistics statistics = new Statistics();

        /* Go through all the games */
        for (GameInputData game : games) {
            statistics.increaseTotalGamesPlayed();

            InputData inputData = new InputData(theirData);
            /* Extract the actions for the actual game */
            ArrayList<ActionsInputData> actions = game.getActions();
            /* Extract which deck each player plays with */
            int playerOneDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();

            /* Extract the deck for each player */
            ArrayList<CardInputData> playerOneDeck;
            ArrayList<CardInputData> playerTwoDeck;
            playerOneDeck = inputData.getPlayerOneDecks().getDecks().get(playerOneDeckIdx);
            playerTwoDeck = inputData.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx);

            /* Shuffle the decks */
            Collections.shuffle(playerOneDeck, new Random(game.getStartGame().getShuffleSeed()));
            Collections.shuffle(playerTwoDeck, new Random(game.getStartGame().getShuffleSeed()));

            Player player1 = new Player();
            Player player2 = new Player();

            /* Extract the hero for each player*/
            CardInputData playerOneHero = game.getStartGame().getPlayerOneHero();
            CardInputData playerTwoHero = game.getStartGame().getPlayerTwoHero();

            /* Delete the first card in the range and put it in hand */
            player1.getHand().add(playerOneDeck.remove(0));
            player2.getHand().add(playerTwoDeck.remove(0));

            /* Create a new table for each new game and  initialize turn and round with 1 */
            Game currentGame = new Game();
            currentGame.newGame();
            /* Change the player to the starting player */
            currentGame.changePlayer(game.getStartGame().getStartingPlayer());
            /* Set mana for each player at 1 */
            player1.setMana(1);
            player2.setMana(1);

            /* Go through all the actions for a game */
            for (ActionsInputData action : actions) {

                String command = new String(action.getCommand());

                if (command.compareTo("getCardsInHand") == 0) {
                    getCardsInHand(action.getPlayerIdx(), player1, player2, output);

                } else if (command.compareTo("getPlayerDeck") == 0) {
                    getPlayerDeck(inputData, game, action, output);

                } else if (command.compareTo("getPlayerTurn") == 0) {
                    getPlayerTurn(currentGame, output);

                } else if (command.compareTo("getPlayerHero") == 0) {
                    getPlayerHero(game, action, output);

                } else if (command.compareTo("endPlayerTurn") == 0) {
                    endPlayerTurn(currentGame, player1, player2, playerOneDeck,
                                  playerTwoDeck, playerOneHero, playerTwoHero);

                } else if  (command.compareTo(("placeCard")) == 0) {
                    placeCard(currentGame, action.getHandIdx(), player1, player2, output);

                } else if (command.compareTo("getPlayerMana") == 0) {
                    getPlayerMana(action.getPlayerIdx(), player1, player2, output);

                } else if (command.compareTo("getCardsOnTable") == 0) {
                    getCardsOnTable(currentGame, output);

                } else if (command.compareTo("getEnvironmentCardsInHand") == 0) {
                    getEnvironmentCardsInHand(action.getPlayerIdx(), player1, player2, output);

                } else if (command.compareTo("getCardAtPosition") == 0) {
                    getCardAtPosition(currentGame, action.getX(), action.getY(), output);

                } else if (command.compareTo("useEnvironmentCard") == 0) {
                    useEnvironmentCard(currentGame, player1, player2, action.getHandIdx(),
                                       action.getAffectedRow(), output);

                } else if (command.compareTo("getFrozenCardsOnTable") == 0) {
                    getFrozenCardsOnTable(currentGame, output);

                } else if (command.compareTo("cardUsesAttack") == 0) {
                    cardUsesAttack(currentGame, action, output);

                } else if (command.compareTo("cardUsesAbility") == 0) {
                    cardUsesAbility(currentGame, action, output);

                } else if (command.compareTo("useAttackHero") == 0) {
                    useAttackHero(game.getStartGame(), currentGame, action, output, statistics);

                } else if (command.compareTo("useHeroAbility") == 0) {
                    useHeroAbility(game.getStartGame(), currentGame, player1, player2,
                                   action.getAffectedRow(), output);

                } else if (command.compareTo("getTotalGamesPlayed") == 0) {
                    getTotalGamesPlayed(statistics, output);

                } else if (command.compareTo("getPlayerOneWins") == 0) {
                    getPlayerWins(1, statistics, output);

                } else if (command.compareTo("getPlayerTwoWins") == 0) {
                    getPlayerWins(2, statistics, output);
                }
            }
        }

    }

    /**
     * Display the deck the player is playing with
     * @param inputData information for all the games
     * @param game information for the game
     * @param action contains all the commands for the game
     * @param output the final output in which it is written
     */
    public void getPlayerDeck(final InputData inputData, final GameInputData game,
                              final ActionsInputData action, final ArrayNode output) {

        /* Find the deck for the player given as an argument */
        ArrayList<ArrayList<CardInputData>> decks = inputData.getPlayerOneDecks().getDecks();
        int playerDeckIdx = game.getStartGame().getPlayerOneDeckIdx();

        if (action.getPlayerIdx() == 2) {
            decks = inputData.getPlayerTwoDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();
        }

        Display displayGetPlayerDeck = new Display();
        displayGetPlayerDeck.displayDeck(decks.get(playerDeckIdx), action.getPlayerIdx(), output,
                                "getPlayerDeck");
    }

    /**
     * Display the hero of the player
     * @param game information for the game
     * @param action contains all the commands for the game
     * @param output the final output in which it is written
     */
    public void getPlayerHero(final GameInputData game, final ActionsInputData action,
                              final ArrayNode output) {

        /* Extract the hero for the player given as an argument */
        CardInputData hero = game.getStartGame().getPlayerOneHero();

        if (action.getPlayerIdx() == 2) {
            hero = game.getStartGame().getPlayerTwoHero();
        }

        Display displayGetPlayerHero = new Display();
        displayGetPlayerHero.displayGetPlayerHero(hero, action.getPlayerIdx(), output);

    }

    /**
     *
     * @param currentGame additional information about the current game as table, current player
     * @param output the final output in which it is written
     */
    public void getPlayerTurn(final Game currentGame, final ArrayNode output) {

        int playerTurn = currentGame.getCurrentPlayer();

        Display displayGetPlayerTurn = new Display();
        displayGetPlayerTurn.displayGetPlayerTurn(playerTurn, output);
    }

    /**
     * End the current turn and change the necessary information
     * @param currentGame additional information about the current game as table, current player
     * @param player1 information about player1
     * @param player2 information about player2
     * @param playerOneDeck the first player's deck
     * @param playerTwoDeck the second player's deck
     * @param playerOneHero the first player's hero
     * @param playerTwoHero the second player's hero
     */
    public void endPlayerTurn(final Game currentGame, final Player player1, final Player player2,
                              final ArrayList<CardInputData> playerOneDeck,
                              final ArrayList<CardInputData> playerTwoDeck,
                              final CardInputData playerOneHero,
                              final CardInputData playerTwoHero) {

        /* Increase the turn */
        currentGame.increaseTurn();

        /* Check if the two turns have ended */
        if (currentGame.getTurn() % 2 == 1) {

            /* Increase the round */
            currentGame.increaseRound();

            /* Increase mana to each player */
            player1.changeManaPlayer(currentGame.getRound());
            player2.changeManaPlayer(currentGame.getRound());

            /* Delete the first card in the range and put it in hand */
            if (playerOneDeck.size() > 0) {
                player1.getHand().add(playerOneDeck.remove(0));
            }

            if (playerTwoDeck.size() > 0) {
                player2.getHand().add(playerTwoDeck.remove(0));
            }
        }

        CardInputData hero = playerOneHero;
        if (currentGame.getCurrentPlayer() == 2) {
            hero = playerTwoHero;
        }

        /* Unset freeze and attack for cards */
        Settings settings = new Settings();
        settings.initializingChangeTurn(currentGame, hero);
    }

    /**
     * Display the cards from the player's hand
     * @param playerIdx index of the player for whom I want to apply the actions
     * @param player1 information about player1
     * @param player2 information about player2
     * @param output the final output in which it is written
     */
    public void getCardsInHand(final int playerIdx, final Player player1,
                               final Player player2, final ArrayNode output) {

        Display displayGetPlayerDeck = new Display();
        if (playerIdx == 1) {
            displayGetPlayerDeck.displayDeck(player1.getHand(), playerIdx, output,
                                    "getCardsInHand");

        } else {
            displayGetPlayerDeck.displayDeck(player2.getHand(), playerIdx, output,
                                   "getCardsInHand");
        }
    }

    /**
     * Put a card from hand on the table depending on the specifications of each one
     * @param currentGame additional information about the current game as table, current player
     * @param handIdx index of the card in hand
     * @param player1 information about player1
     * @param player2 information about player2
     * @param output the final output in which it is written
     */
    public void placeCard(final Game currentGame, final int handIdx, final Player player1,
                          final Player player2, final ArrayNode output) {

        Minion minion = new Minion();

        Player player = player1;
        if (currentGame.getCurrentPlayer() == 2) {
            player = player2;
        }

        /* Take the card from handIdx position out of hand */
        ArrayList<CardInputData> hand = player.getHand();

        if (hand.size() > 0 && handIdx < hand.size()) {

            CardInputData card = hand.get(handIdx);
            ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

            VerifyErrors verifyErrors = new VerifyErrors();
            int done = verifyErrors.errorsPlaceCard(currentGame, player, card, output);
            if (done == 1) {
                return;
            }

            /* Place the card on the table */
            ArrayList<CardInputData> row;
            if (player == player1) {
                row = table.get(FIRST_ROW_PLAYER1);
                if (minion.getBackCards().contains(card.getName())) {
                    row = table.get(FIRST_ROW_PLAYER1 + 1);
                }
            } else {
                row = table.get(FIRST_ROW_PLAYER2 + 1);
                if (minion.getBackCards().contains(card.getName())) {
                    row = table.get(FIRST_ROW_PLAYER2);
                }
            }

            row.add(hand.remove(handIdx));
            player.changeManaPlayer(-card.getMana());
        }
    }

    /**
     * Display the mana of the player
     * @param playerIdx index of the player for whom I want to apply the actions
     * @param player1 information about player1
     * @param player2 information about player2
     * @param output the final output in which it is written
     */
    public void getPlayerMana(final int playerIdx, final Player player1,
                              final Player player2, final ArrayNode output) {

        int playerMana = player1.getMana();
        if (playerIdx == 2) {
            playerMana = player2.getMana();
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getPlayerMana");
        outputCommand.put("playerIdx", playerIdx);
        outputCommand.put("output", playerMana);

        output.add(outputCommand);
    }

    /**
     * Display all the cards from the table
     * @param currentGame additional information about the current game as table, current player
     * @param output the final output in which it is written
     */
    public void getCardsOnTable(final Game currentGame, final ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        /* Creates an ArrayNode to which I add all the cards on the table */
        ArrayNode cardsOnTable = mapper.createArrayNode();

        /* Go through all the rows on the table */
        for (int i = 0; i < MAX_NUMBER_ROWS; i++) {
            ArrayList<CardInputData> row = currentGame.getTable().get(i);

            FormDeck formDeck = new FormDeck();
            ArrayNode rowFormed  = formDeck.addDeckInOutput(row);

            cardsOnTable.add(rowFormed);
        }

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getCardsOnTable");
        outputCommand.set("output", cardsOnTable);

        /* Add all to the final output */
        output.add(outputCommand);
    }

    /**
     * Display the environment cards from the player's hand
     * @param playerIdx index of the player for whom I want to apply the actions
     * @param player1 information about player1
     * @param player2 information about player2
     * @param output the final output in which it is written
     */
    public void getEnvironmentCardsInHand(final int playerIdx, final Player player1,
                                          final Player player2, final ArrayNode output) {

        Player player = player1;

        if (playerIdx == 2) {
            player = player2;
        }

        Environment environment = new Environment();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode environmentCards = mapper.createArrayNode();

        /* Go through all the cards in hand and format the environment one */
        for (CardInputData card : player.getHand()) {
            if (environment.getEnvironmentCards().contains(card.getName())) {
                FormCard displayCard = new FormCard();
                ObjectNode environmentCard = displayCard.addCardInOutput(card);
                /* Add the card to the ArrayNode */
                environmentCards.add(environmentCard);
            }
        }

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getEnvironmentCardsInHand");
        outputCommand.put("playerIdx", playerIdx);
        outputCommand.set("output", environmentCards);

        /* Add all to the final output */
        output.add(outputCommand);
    }

    /**
     * Display the card for the given position in table
     * @param currentGame additional information about the current game as table, current player
     * @param x row for the card
     * @param y column for the card
     * @param output the final output in which it is written
     */
    public void getCardAtPosition(final Game currentGame, final int x, final int y,
                                  final ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getCardAtPosition");
        outputCommand.put("x", x);
        outputCommand.put("y", y);

        ArrayList<CardInputData> row = currentGame.getTable().get(x);

        if (y >= row.size()) {
            outputCommand.put("output", "No card available at that position.");

        } else {
            FormCard displayCard = new FormCard();
            ObjectNode card = displayCard.addCardInOutput(row.get(y));
            outputCommand.set("output", card);
        }

        output.add(outputCommand);
    }

    /**
     * Apply one of the environment cards from hand on a row
     * @param currentGame additional information about the current game as table, current player
     * @param player1 information about player1
     * @param player2 information about player2
     * @param handIdx index of the environment card in hand
     * @param affectedRow row that receives the changes
     * @param output the final output in which it is written
     */
    public void useEnvironmentCard(final Game currentGame, final Player player1,
                                   final Player player2, final int handIdx,
                                   final int affectedRow, final ArrayNode output) {

        Player player = player1;
        if (currentGame.getCurrentPlayer() == 2) {
            player = player2;
        }

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();
        CardInputData card = player.getHand().get(handIdx);
        VerifyErrors verifyErrors = new VerifyErrors();

        int done = verifyErrors.errorsUseEnvironmentCard(currentGame, player, card, handIdx,
                affectedRow, output);
        if (done == 1) {
            return;
        }

        Environment environment = new Environment();

        if (card.getName().compareTo("Firestorm") == 0) {
            environment.applyFirestorm(table, affectedRow);

        } else if (card.getName().compareTo("Winterfell") == 0) {
            environment.applyWinterfell(table, affectedRow);

        } else if (card.getName().compareTo("Heart Hound") == 0) {
            environment.applyHeartHound(table, affectedRow);
        }

        /* Modify the player's mana */
        player.changeManaPlayer(-player.getHand().get(handIdx).getMana());
        /* Remove the environment card from the hand */
        player.getHand().remove(handIdx);
    }

    /**
     * Display frozen card on the table
     * @param currentGame additional information about the current game as table, current player
     * @param output the final output in which it is written
     */
    public void getFrozenCardsOnTable(final Game currentGame, final ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode cardArray = mapper.createArrayNode();

        /* Go through the cards on the table */
        for (ArrayList<CardInputData> row : currentGame.getTable()) {
            for (CardInputData card : row) {
                /* Find the frozen cards and add them to the ArrayNode */
                if (card.getFrozen() == 1) {
                    FormCard displayCard = new FormCard();
                    ObjectNode formedCard = displayCard.addCardInOutput(card);

                    cardArray.add(formedCard);
                }
            }
        }

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getFrozenCardsOnTable");
        outputCommand.set("output", cardArray);

        output.add(outputCommand);
    }

    /**
     * Attack the given card of the enemy
     * @param currentGame additional information about the current game as table, current player
     * @param action contains all the commands for the game
     * @param output the final output in which it is written
     */
    public void cardUsesAttack(final Game currentGame, final ActionsInputData action,
                               final ArrayNode output) {

        /* Position of the attacker card on table */
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();

        /* Position of the attacked card on table */
        int attackedX = action.getCardAttacked().getX();
        int attackedY = action.getCardAttacked().getY();

        VerifyErrors verifyErrors = new VerifyErrors();

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        /* First check if the positions are appropriate for the dimension of the table */
        if ((attackerX < table.size() && attackedX < table.size())
                && (attackerY < table.get(attackerX).size()
                && attackedY < table.get(attackedX).size())) {

            /* Find the attacker and attacked card on the table */
            CardInputData attackerCard = table.get(attackerX).get(attackerY);
            CardInputData attackedCard = table.get(attackedX).get(attackedY);

            int done = verifyErrors.errorsCardUsesAttack(currentGame, attackerCard, attackedCard,
                    action.getCardAttacker(), action.getCardAttacked(), output);
            if (done == 1) {
                return;
            }

            /* The attack */
            int health = attackedCard.getHealth();
            int attackDamage = attackerCard.getAttackDamage();
            attackedCard.setHealth(health - attackDamage);

            if (attackedCard.getHealth() <= 0) {
                table.get(attackedX).remove(attackedY);
            }

            /* The card already attacked this turn */
            attackerCard.setAttack(1);
        }
    }

    /**
     * Apply the ability of the attacker card on the attacked card
     * @param currentGame additional information about the current game as table, current player
     * @param action contains all the commands for the game
     * @param output the final output in which it is written
     */
    public void cardUsesAbility(final Game currentGame, final ActionsInputData action,
                                final ArrayNode output) {

        /* Position of the attacker card on table */
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();

        /* Position of the attacked card on table */
        int attackedX = action.getCardAttacked().getX();
        int attackedY = action.getCardAttacked().getY();

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        /* First check if the positions are appropriate for the dimension of the table */
        if ((attackerX < table.size() && attackedX < table.size())
                && (attackerY < table.get(attackerX).size()
                && attackedY < table.get(attackedX).size())) {

            /* Find the attacker and attacked card on the table */
            CardInputData attackerCard = table.get(attackerX).get(attackerY);
            CardInputData attackedCard = table.get(attackedX).get(attackedY);

            VerifyErrors verifyErrors = new VerifyErrors();
            int done = verifyErrors.errorsCardUsesAbility(currentGame, attackerCard,
                    attackedCard, action.getCardAttacker(), action.getCardAttacked(), output);
            if (done == 1) {
                return;
            }

            Minion minion = new Minion();
            /* Identify the special card and apply his ability */
            if (attackerCard.getName().compareTo("The Ripper") == 0) {
                minion.applyAbilityTheRipper(attackedCard);

            } else if (attackerCard.getName().compareTo("Miraj") == 0) {
                minion.applyAbilityMiraj(attackerCard, attackedCard);

            } else if (attackerCard.getName().compareTo("The Cursed One") == 0) {
                minion.applyAbilityTheCursedOne(currentGame, attackedCard, attackedX, attackedY);

            } else if (attackerCard.getName().compareTo("Disciple") == 0) {
                minion.applyDisciple(attackedCard);
            }

            /* The card already attacked this turn */
            attackerCard.setAttack(1);
        }
    }

    /**
     * The attacker card attack the enemy's hero
     * @param startGameInputData necessary information to start a new game
     * @param currentGame additional information about the current game as table, current player
     * @param action contains all the commands for the game
     * @param output the final output in which it is written
     * @param statistics contains the player1 and player2 wins and also tne number of all
     *                  the games played
     */
    public void  useAttackHero(final StartGameInputData startGameInputData, final Game currentGame,
                               final ActionsInputData action, final ArrayNode output,
                               final Statistics statistics) {

        /* Position of the attacker */
        int attackerX = action.getCardAttacker().getX();
        int attackerY = action.getCardAttacker().getY();

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        if (attackerX < table.size()  && attackerY < table.get(attackerX).size()) {

            CardInputData attackerCard = table.get(attackerX).get(attackerY);

            VerifyErrors verifyErrors = new VerifyErrors();
            int done = verifyErrors.errorsUseAttackHero(currentGame, attackerCard,
                    action.getCardAttacker(), output);
            if (done == 1) {
                return;
            }

            String message = "Player one killed the enemy hero.";
            if (currentGame.getCurrentPlayer() == 2) {
                message = "Player two killed the enemy hero.";
            }

            Hero hero = new Hero();
            /* Attack the hero and eventually kill it */
            hero.killHero(currentGame, startGameInputData, message, attackerCard, statistics,
                    output);

            /* The card already attacked this turn */
            attackerCard.setAttack(1);
        }
    }

    /**
     * The player's hero uses his ability on a row
     * @param startGameInputData necessary information to start a new game
     * @param currentGame additional information about the current game as table, current player
     * @param player1 information about player1
     * @param player2 information about player2
     * @param affectedRow row that receives the changes
     * @param output the final output in which it is written
     */
    public void useHeroAbility(final StartGameInputData startGameInputData, final Game currentGame,
                               final Player player1, final Player player2, final int affectedRow,
                               final ArrayNode output) {

        Player player = player1;
        /* Find the current player's hero */
        CardInputData heroCard = startGameInputData.getPlayerOneHero();

        if (currentGame.getCurrentPlayer() == 2) {
            heroCard = startGameInputData.getPlayerTwoHero();
            player = player2;
        }

        VerifyErrors verifyErrors = new VerifyErrors();
        int done = verifyErrors.useHeroAbility(currentGame, player, heroCard, affectedRow, output);
        if (done == 1) {
            return;
        }

        ArrayList<CardInputData> row =  currentGame.getTable().get(affectedRow);

        Hero hero = new Hero();
        /* Identify the hero and apply his ability */
        if (heroCard.getName().compareTo("Lord Royce") == 0) {
            hero.applyAbilityLord(row);

        } else if (heroCard.getName().compareTo("Empress Thorina") == 0) {
            hero.applyAbilityThorina(row);

        } else if (heroCard.getName().compareTo("King Mudface") == 0) {
            hero.applyAbilityKing(row);

        } else if (heroCard.getName().compareTo("General Kocioraw") == 0) {
            hero.applyAbilityKocioraw(row);
        }

        heroCard.setAttack(1);
        /* Decrease the player's mana by hero mana */
        player.setMana(player.getMana() - heroCard.getMana());
    }

    /**
     * Display total games played so far
     * @param statistics contains the player1 and player2 wins and also tne number of all
     *                   the games played
     * @param output the final output in which it is written
     */
    public void getTotalGamesPlayed(final Statistics statistics, final ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getTotalGamesPlayed");
        outputCommand.put("output", statistics.getTotalGamesPlayed());

        output.add(outputCommand);
    }

    /**
     * Display the number of wined games for a player
     * @param playerIdx the index of the player for which I want to display
     * @param statistics contains the player1 and player2 wins and also tne number of all
     *                 the games played
     * @param output the final output in which it is written
     */
    public void getPlayerWins(final int playerIdx, final Statistics statistics,
                              final ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        if (playerIdx == 1) {
            outputCommand.put("command", "getPlayerOneWins");
            outputCommand.put("output", statistics.getPlayerOneWins());
        } else {
            outputCommand.put("command", "getPlayerTwoWins");
            outputCommand.put("output", statistics.getPlayerTwoWins());
        }

        output.add(outputCommand);
    }
}
