package me.ddquin.snake.logic;

public class Cell {

    private int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean equals(Cell c) {
        if (c.getX() == x && c.getY() == y) {
            return true;
        }
        return false;
    }
}
