package game.gameStrategy;

import game.data.CardInputData;

import java.util.ArrayList;

public class Game {
    private Player player1;
    private Player player2;

    private ArrayList<ArrayList<CardInputData>> table;

    public void createGameTable() {
        table = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            ArrayList<CardInputData> row = new ArrayList<>(5);
            table.add(row);
        }
    }

}
