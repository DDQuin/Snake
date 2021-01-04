package me.ddquin.snake.logic;

import java.util.Collection;

public class SnakeGame {

    private SnakeBoard board;

    public SnakeGame(int width, int height) {
        board = new SnakeBoard(width, height);
    }

    public GameStatus nextStep() {
        return board.nextStep();
    }

    public void setSnakeHeadDirection(Direction dir) {
        board.setSnakeHeadDirection(dir);
    }

    public Cell getSnakeHead() {
        return board.getSnakeHead();
    }

    public Collection<Cell> getSnakeBody() {
        return board.getSnakeBody();
    }

    public Cell getApple() {
        return board.getApple();
    }

    public int getWidth() {
        return board.getWidth();
    }

    public Cell getTail() {
        return board.getTail();
    }

    public int getHeight() {
        return board.getHeight();
    }

    public Direction getSnakeCellDirection(Cell cell) {
        return board.getSnakeCellDirection(cell);
    }

    public int getScore() {
        return board.getScore();
    }
}
