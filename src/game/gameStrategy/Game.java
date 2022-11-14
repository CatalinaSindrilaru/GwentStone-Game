package game.gameStrategy;

import game.data.CardInputData;

import java.util.ArrayList;

public class Game {
//    private Player player1;
//    private Player player2;

    private ArrayList<ArrayList<CardInputData>> table;
    private int currentPlayer;

    private int round;
    private int tour;

    public void createGameTable() {
        table = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            ArrayList<CardInputData> row = new ArrayList<>(5);
            table.add(row);
        }
    }

    public void newGame() {
        createGameTable();
        round = 1;
        tour = 1;
    }

    public void changePlayer(int player) {
        currentPlayer = player;
    }

    public void increaseTour() {
        tour += 1;
    }

    public void increaseRound() {
        round += 1;
    }

    public ArrayList<ArrayList<CardInputData>> getTable() {
        return table;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }
}
