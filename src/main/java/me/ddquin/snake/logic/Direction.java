package me.ddquin.snake.logic;


public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public static Direction getOpposite(Direction d) {
        if (d == UP) return DOWN;
        if (d == DOWN) return UP;
        if (d == RIGHT) return LEFT;
        if (d == LEFT) return RIGHT;
        return null;
    }
}
