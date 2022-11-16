package game;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.Coordinates;
import game.card.environment.Environment;
import game.card.minion.Minion;
import game.data.*;
import game.displays.*;
import game.gameStrategy.Game;
import game.gameStrategy.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SolveCommands {
    public void display(String file, InputData inputData, ArrayNode output) {

        ArrayList<GameInputData> games = inputData.getGames();

        for (GameInputData game : games) {
            ArrayList<ActionsInputData> actions = game.getActions();

            ArrayList<CardInputData> playerOneDeck, playerTwoDeck;
            int playerOneDeckIdx = game.getStartGame().getPlayerOneDeckIdx();
            int playerTwoDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();

            playerOneDeck = inputData.getPlayerOneDecks().getDecks().get(playerOneDeckIdx);
            playerTwoDeck = inputData.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx);

            Collections.shuffle(playerOneDeck, new Random(game.getStartGame().getShuffleSeed()));
            Collections.shuffle(playerTwoDeck, new Random(game.getStartGame().getShuffleSeed()));

            Player player1 = new Player();
            Player player2 = new Player();

            // scot carte

            // sterg prima carte din game si o oun in mana

            player1.getHand().add(playerOneDeck.remove(0));
            player2.getHand().add(playerTwoDeck.remove(0));

            // creez o masa noua la fiecare joc nou. incep o noua tura/runda si schimb jucatorul la starting player
            Game currentGame = new Game();
            currentGame.newGame();
            currentGame.changePlayer(game.getStartGame().getStartingPlayer());
            player1.setMana(1);  // mana pt jucator = 1
            player2.setMana(1);


            for (ActionsInputData action : actions) {
                String command = new String(action.getCommand());

                if (command.compareTo("getCardsInHand") == 0) {
                    getCardsInHand(action.getPlayerIdx(), player1, player2, output);
                }

                if (command.compareTo("getPlayerDeck") == 0) {
                    getPlayerDeck(inputData, game, action, output);
                }

                if (command.compareTo("getPlayerTurn") == 0) {
                    getPlayerTurn(currentGame, output);
                }

                if (command.compareTo("getPlayerHero") == 0) {
                    getPlayerHero(game, action, output);
                }

                if (command.compareTo("endPlayerTurn") == 0) {
                    endPlayerTurn(currentGame, player1, player2, playerOneDeck, playerTwoDeck);
                }

                if  (command.compareTo(("placeCard")) == 0) {
                    int handIdx = action.getHandIdx();
                    placeCard(currentGame, handIdx, player1, player2, output);
                }

                if (command.compareTo("getPlayerMana") == 0) {
                    int playerIdx = action.getPlayerIdx();
                    getPlayerMana(playerIdx, player1, player2, output);
                }

                if (command.compareTo("getCardsOnTable") == 0) {
                    getCardsOnTable(currentGame, output);
                }

                if (command.compareTo("getEnvironmentCardsInHand") == 0) {
                    int playerIdx = action.getPlayerIdx();
                    getEnvironmentCardsInHand(playerIdx, player1, player2, output);
                }

                if (command.compareTo("getCardAtPosition") == 0) {
                    int x = action.getX();
                    int y = action.getY();
                    getCardAtPosition(currentGame, x, y, output);
                }

                if (command.compareTo("useEnvironmentCard") == 0) {
                    int handIdx = action.getHandIdx();
                    int affectedRow = action.getAffectedRow();
                    useEnvironmentCard(currentGame, player1, player2, handIdx, affectedRow, output);
                }

                if (command.compareTo("getFrozenCardsOnTable") == 0) {
                    getFrozenCardsOnTable(currentGame, output);
                }

                if (command.compareTo("cardUsesAttack") == 0) {
                    cardUsesAttack(currentGame, action, output);
                }
            }
        }

    }

    public void getPlayerDeck(InputData inputData, GameInputData game, ActionsInputData action, ArrayNode output) {
        // am nevoie de playerIdx care se gaseste la actions
        // "playerOneDeckIdx": 0
        // lista deck uri pt acel jucator

        // scot pachetele

        int playerIdx = action.getPlayerIdx();
        ArrayList<ArrayList<CardInputData>> decks;
        int playerDeckIdx;

        if (playerIdx == 1) {
            decks = inputData.getPlayerOneDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerOneDeckIdx();

            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(decks.get(playerDeckIdx), playerIdx, output, "getPlayerDeck");

        } else if (playerIdx == 2) {
            decks = inputData.getPlayerTwoDecks().getDecks();
            playerDeckIdx = game.getStartGame().getPlayerTwoDeckIdx();
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(decks.get(playerDeckIdx), playerIdx, output, "getPlayerDeck");
        }
    }

    public void getPlayerHero(GameInputData game, ActionsInputData action, ArrayNode output) {
        int playerIdx = action.getPlayerIdx();

        CardInputData hero;
        if (playerIdx == 1) {
            hero = game.getStartGame().getPlayerOneHero();
            DisplayGetPlayerHero displayGetPlayerHero = new DisplayGetPlayerHero();
            displayGetPlayerHero.displayGetPlayerHero(hero, playerIdx, output);

        } else if (playerIdx == 2) {
            hero = game.getStartGame().getPlayerTwoHero();
            DisplayGetPlayerHero displayGetPlayerHero = new DisplayGetPlayerHero();
            displayGetPlayerHero.displayGetPlayerHero(hero, playerIdx, output);
        }

    }

    public void getPlayerTurn(Game currentGame, ArrayNode output) {
        int playerTurn;
//        playerTurn = game.getStartGame().getStartingPlayer();
        playerTurn = currentGame.getCurrentPlayer();

        DisplayGetPlayerTurn displayGetPlayerTurn = new DisplayGetPlayerTurn();
        displayGetPlayerTurn.displayGetPlayerTurn(playerTurn, output);
    }

    public void endPlayerTurn(Game currentGame, Player player1, Player player2,  ArrayList<CardInputData> playerOneDeck, ArrayList<CardInputData> playerTwoDeck) {
        currentGame.increaseTour();
        if (currentGame.getTour() % 2 == 1) {
            currentGame.increaseRound();

            player1.changeManaPlayer(currentGame.getRound());
            player2.changeManaPlayer(currentGame.getRound());

            if (playerOneDeck.size() > 0)
                player1.getHand().add(playerOneDeck.remove(0));
            if (playerTwoDeck.size() > 0)
                player2.getHand().add(playerTwoDeck.remove(0));
        }

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        if (currentGame.getCurrentPlayer() == 1) {
            // sctoate frozen din carti
            // randurile 2 si 3
            for (int i = 2; i < 4; i++) {
                ArrayList<CardInputData> row = table.get(i);
                for (CardInputData card : row) {
                    card.setFrozen(0);
                    card.setAttack(0);
                }
            }
            currentGame.changePlayer(2);
        } else {
            for (int i = 0; i < 2; i++) {
                ArrayList<CardInputData> row = table.get(i);
                for (CardInputData card : row) {
                    card.setFrozen(0);
                    card.setAttack(0);
                }
            }
            currentGame.changePlayer(1);
        }

        //todo la finalul turei unui jucator cartile frozen sunt demarcate, precum si cartile care au atacat deja in tura
    }

    public void getCardsInHand(int playerIdx, Player player1, Player player2, ArrayNode output) {

        if (playerIdx == 1) {
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(player1.getHand(), playerIdx, output, "getCardsInHand");
        } else {
            DisplayDeck displayGetPlayerDeck = new DisplayDeck();
            displayGetPlayerDeck.displayDeck(player2.getHand(), playerIdx, output, "getCardsInHand");
        }
    }

    public void placeCard(Game currentGame, int handIdx, Player player1, Player player2, ArrayNode output) {

        Environment environment = new Environment();
        Minion minion = new Minion();

        Player player = player1;
        if (currentGame.getCurrentPlayer() == 2) {
            player = player2;
        }

        // scoate cartea de la pozitia handIdx din mana
        ArrayList<CardInputData> hand = player.getHand();

        if (hand.size() > 0 && handIdx < hand.size()) {
            CardInputData card = hand.get(handIdx);
            boolean done = false;

            // scot tabla de jos
            ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();


            // verifica daca cartea este de tipul environment
            if (environment.getEnvironmentCards().contains(card.getName())) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorPlaceCard(output, "Cannot place environment card on table.");
                done = true;

            }
            if (!done && card.getMana() > player.getMana()) {  // verifica daca cartea are costul mai mic decat mana jucatorului

                DisplayError displayError = new DisplayError();
                displayError.displayErrorPlaceCard(output, "Not enough mana to place card on table.");
                done = true;

            }

            if (!done && player == player1) {      // se verifica daca randul pe care trebuie plasata cartea este plin

                if ((minion.getBackCards().contains(card.getName()) && table.get(3).size() == 5) ||
                        (minion.getFrontCards().contains(card.getName()) && table.get(2).size() == 5)) {

                    DisplayError displayError = new DisplayError();
                    displayError.displayErrorPlaceCard(output, "Cannot place card on table since row is full.");
                    done = true;
                }
            }
            if (!done && player == player2) {
                if ((minion.getBackCards().contains(card.getName()) && table.get(0).size() == 5) ||
                        (minion.getFrontCards().contains(card.getName()) && table.get(1).size() == 5)) {
                    DisplayError displayError = new DisplayError();
                    displayError.displayErrorPlaceCard(output, "Cannot place card on table since row is full.");
                    done = true;
                }
            }

            // altfel plaseaza cartea
            //PLACE CARD PROPRIU-ZIS
            // STERG CARTEA DIN HAND-UL playerului si o pun pe randul potrivit
            if (!done) {
                if (player == player1) {
                    if (minion.getBackCards().contains(card.getName())) {
                        ArrayList<CardInputData> row = table.get(3);
                        row.add(hand.remove(handIdx));
                    } else {
                        ArrayList<CardInputData> row = table.get(2);
                        row.add(hand.remove(handIdx));
                    }
                    player1.changeManaPlayer(-card.getMana());

                } else {
                    if (minion.getBackCards().contains(card.getName())) {
                        ArrayList<CardInputData> row = table.get(0);
                        row.add(hand.remove(handIdx));
                    } else {
                        ArrayList<CardInputData> row = table.get(1);
                        row.add(hand.remove(handIdx));
                    }
                    player2.changeManaPlayer(-card.getMana());
                }
            }
        }


    }

    public void getPlayerMana(int playerIdx, Player player1, Player player2, ArrayNode output) {
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

    public void getCardsOnTable(Game currentGame, ArrayNode output) {

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode cardsOnTable = mapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            ArrayList<CardInputData> row = table.get(i);

            FormDeck formDeck = new FormDeck();
            ArrayNode rowFormed  = formDeck.addDeckInOutput(row);

            cardsOnTable.add(rowFormed);
        }

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getCardsOnTable");
        outputCommand.set("output", cardsOnTable);

        output.add(outputCommand);
    }

    public void getEnvironmentCardsInHand(int playerIdx, Player player1, Player player2, ArrayNode output) {

        Player player = player1;
        if (playerIdx == 2) {
            player = player2;
        }

        ArrayList<CardInputData> hand = player.getHand();

        Environment environment = new Environment();


        ObjectMapper mapper = new ObjectMapper();
        ArrayNode environmentCards = mapper.createArrayNode();

        for (CardInputData card : hand) {

            if (environment.getEnvironmentCards().contains(card.getName())) {
                FormCard displayCard = new FormCard();
                ObjectNode environmentCard = displayCard.addCardInOutput(card);

                environmentCards.add(environmentCard);
            }
        }

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getEnvironmentCardsInHand");
        outputCommand.put("playerIdx", playerIdx);
        outputCommand.set("output", environmentCards);

        output.add(outputCommand);
    }

    public void getCardAtPosition(Game currentGame, int x, int y, ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getCardAtPosition");
        outputCommand.put("x", x);
        outputCommand.put("y", y);

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();

        ArrayList<CardInputData> row = table.get(x);

        if (y >= row.size()) {
            outputCommand.put("output", "No card available at that position.");

        } else {
            FormCard displayCard = new FormCard();
            ObjectNode card = displayCard.addCardInOutput(row.get(y));

            outputCommand.set("output", card);
        }

        output.add(outputCommand);
    }

    public void useEnvironmentCard(Game currentGame, Player player1, Player player2, int handIdx, int affectedRow, ArrayNode output) {

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();
        Player player = player1;
        if (currentGame.getCurrentPlayer() == 2) {
            player = player2;
        }

        ArrayList<CardInputData> hand = player.getHand();

        CardInputData card = hand.get(handIdx);

        Environment environment = new Environment();
        boolean done = false;

        if (!environment.getEnvironmentCards().contains(card.getName())) {  // daca indexul ales NU corespunde unei cărți de tipul environment în lista de cărți din mână,
            DisplayError displayError = new DisplayError();
            displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow, "Chosen card is not of type environment.");
            done = true;
        }

        if (!done && card.getMana() > player.getMana()) {
            DisplayError displayError = new DisplayError();
            displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow, "Not enough mana to use environment card.");
            done = true;
        }

        // pot combina if-urile acestea doua intr-unul singur
        if (!done && player == player1) {
            if (affectedRow == 2 || affectedRow == 3) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow, "Chosen row does not belong to the enemy.");
                done = true;
            }
        }

        if (!done && player == player2) {
            if (affectedRow == 0 || affectedRow == 1) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow, "Chosen row does not belong to the enemy.");
                done = true;
            }
        }

        if (!done && card.getName().compareTo("Heart Hound") == 0) {  // verific daca am unde sa pun jucatorul furat
            int myRow = 3 - affectedRow;
            if (table.get(myRow).size() == 5) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorUseEnvironmentCard(output, handIdx, affectedRow, "Cannot steal enemy card since the player's row is full.");
                done = true;
            }
        }

        //TODO verificare Heart hound
        if (!done) {
            ArrayList<CardInputData> row = table.get(affectedRow);
            if (card.getName().compareTo("Firestorm") == 0) { // scade cu 1 viata tuturor minionilor de pe rand
                //todo

                for (int i = 0; i < row.size(); i++) {
                    CardInputData minionCard = row.get(i);
                    int health = minionCard.getHealth();
                    minionCard.setHealth(health - 1);
                    // verificare daca cartea nu mai are viata =>>>> sterge cartea din row
                    if (minionCard.getHealth() == 0) {
                        row.remove(i);
                        i--;
                    }
                }

            } else if (card.getName().compareTo("Winterfell") == 0) { // Toate cărțile de pe rând stau o tură.
                //todo
                for (int i = 0; i < row.size(); i++) {
                    CardInputData minionCard = row.get(i);
                    minionCard.setFrozen(1);
                }

            } else if (card.getName().compareTo("Heart Hound") == 0) { // Se fură minionul adversarului cu cea mai mare viață de pe rând și se pune pe rândul “oglindit” aferent jucătorului.
                //todo

                // scot minionul cu cea mai mare viata si il adaug in randul meu
                int position = 0;
                int maxHealth = Integer.MIN_VALUE;
                for (int i = 0; i < row.size(); i++) {
                    if (maxHealth < row.get(i).getHealth()) {
                        maxHealth = row.get(i).getHealth();
                        position = i;
                    }
                }
                int myRow = 3 - affectedRow;
                table.get(myRow).add(row.remove(position));
            }

            // sterge card din mana
            player.changeManaPlayer(-hand.get(handIdx).getMana());
            hand.remove(handIdx);

        }

    }

    public void getFrozenCardsOnTable(Game currentGame, ArrayNode output) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode cardArray = mapper.createArrayNode();

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();
        for (ArrayList<CardInputData> row : table) {
            for (CardInputData card : row) {

                if (card.getFrozen() == 1) {
                    FormCard displayCard = new FormCard();
                    ObjectNode formedCard= displayCard.addCardInOutput(card);

                    cardArray.add(formedCard);
                }
            }
        }

        ObjectNode outputCommand = mapper.createObjectNode();

        outputCommand.put("command", "getFrozenCardsOnTable");
        outputCommand.set("output", cardArray);

        output.add(outputCommand);
    }

    public void cardUsesAttack( Game currentGame, ActionsInputData action, ArrayNode output) {

        CoordinatesData attackerCoordinates = action.getCardAttacker();
        CoordinatesData attackedCoordinates = action.getCardAttacked();

        int attackerX = attackerCoordinates.getX();
        int attackerY = attackerCoordinates.getY();

        int attackedX = attackedCoordinates.getX();
        int attackedY = attackedCoordinates.getY();


        boolean done = false;

        if (currentGame.getCurrentPlayer() == 1) {
            if (attackerX >= 2 && attackedX >= 2) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorcardUsesAttack(output, attackerCoordinates, attackedCoordinates, "Attacked card does not belong to the enemy.");
                done = true;
            }
        }

        if (currentGame.getCurrentPlayer() == 2) {
            if (attackerX <= 1 && attackedX <= 1) {
                DisplayError displayError = new DisplayError();
                displayError.displayErrorcardUsesAttack(output, attackerCoordinates, attackedCoordinates, "Attacked card does not belong to the enemy.");
                done = true;
            }
        }

        ArrayList<ArrayList<CardInputData>> table = currentGame.getTable();
        if ((attackerX < table.size() && attackedX < table.size()) &&
                (attackerY < table.get(attackerX).size() && attackedY < table.get(attackedX).size())) {

            CardInputData attackerCard = table.get(attackerX).get(attackerY);
            CardInputData attackedCard = table.get(attackedX).get(attackedY);

            if (!done && attackerCard.getAttack() == 1) { // daca cartea deja a atatacat in tura curenta
                DisplayError displayError = new DisplayError();
                displayError.displayErrorcardUsesAttack(output, attackerCoordinates, attackedCoordinates, "Attacker card has already attacked this turn.");
                done = true;
            }

            if (!done && attackerCard.getFrozen() == 1) { // daca cartea este frozen
                DisplayError displayError = new DisplayError();
                displayError.displayErrorcardUsesAttack(output, attackerCoordinates, attackedCoordinates, "Attacker card is frozen.");
                done = true;
            }

            // cautam daca pa randurile adversarului se gaseste o carte Tank si daca cartea aleasa nu este Tank
            Minion minionCards = new Minion();
            ArrayList<String> tankCards = minionCards.getTankCards();

            if (!tankCards.contains(attackedCard.getName())) { // am verificat ca aceea carte nu este tank
                // verific daca exista carti tank, si daca da afisez eroare

                if (!done && currentGame.getCurrentPlayer() == 1) {
                    for (int i = 0; i < 2; i++) {
                        ArrayList<CardInputData> row = table.get(i);
                        for (CardInputData card : row) {
                            if (tankCards.contains(card.getName())) {
                                DisplayError displayError = new DisplayError();
                                displayError.displayErrorcardUsesAttack(output, attackerCoordinates, attackedCoordinates, "Attacked card is not of type 'Tank'.");
                                done = true;
                                break;
                            }
                        }
                    }
                }

                if (!done && currentGame.getCurrentPlayer() == 2) {
                    for (int i = 2; i < 4; i++) {
                        ArrayList<CardInputData> row = table.get(i);
                        for (CardInputData card : row) {
                            if (tankCards.contains(card.getName())) {
                                DisplayError displayError = new DisplayError();
                                displayError.displayErrorcardUsesAttack(output, attackerCoordinates, attackedCoordinates, "Attacked card is not of type 'Tank'.");
                                done = true;
                                break;
                            }
                        }
                    }
                }
            }

            // fac atacul propriu zis
            if (!done) {
                int health = attackedCard.getHealth();
                int attackDamage = attackerCard.getAttackDamage();
                attackedCard.setHealth(health - attackDamage);

                if (attackedCard.getHealth() <= 0) {
                    ArrayList<CardInputData> row = table.get(attackedX);
                    row.remove(attackedY);
                }

                attackerCard.setAttack(1);
            }
        }


    }
}
