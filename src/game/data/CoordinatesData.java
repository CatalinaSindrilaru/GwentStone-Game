package game.data;

import fileio.Coordinates;

public class CoordinatesData {
    private int x;
    private int y;

    public CoordinatesData() {
    }
    public CoordinatesData(Coordinates coordinates) {
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
