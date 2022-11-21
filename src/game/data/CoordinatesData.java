package game.data;

import fileio.Coordinates;

/**
 * Contains the coordinates of a card in a table
 */
public class CoordinatesData {
    private int x;
    private int y;

    public CoordinatesData() {
    }

    /**
     * Copy Constructor
     * @param coordinates position (row and column)
     */
    public CoordinatesData(final Coordinates coordinates) {
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }

    /**
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x new value
     */
    public void setX(final int x) {
        this.x = x;
    }

    /**
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y new value
     */
    public void setY(final int y) {
        this.y = y;
    }
}
