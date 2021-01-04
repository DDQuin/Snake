package me.ddquin.snake.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class SnakeBoard {

    private Snake snake;
    private Cell apple;
    private int width, height;

    public SnakeBoard(int width, int height) {
        this.width = width;
        this.height = height;
        spawnSnake();
        snake.grow();
        snake.grow();
        spawnApple();
    }

    private void spawnSnake() {
        snake = new Snake(new Cell(2, 0));
    }

    private void spawnApple() {
        Random rand = new Random();
        List<Cell> freeCells = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Cell c = new Cell(column, row);
                // If cell is not a snake cell then add to free cells to spawn apple
                if (!snake.isSnakeCell(c)) {
                    freeCells.add(new Cell(column, row));
                }
            }
        }
        apple = freeCells.get(rand.nextInt(freeCells.size()));
    }

    public Cell getTail() {
        return snake.getTail();
    }

    public Direction getSnakeCellDirection(Cell cell) {
        return snake.getCellDirection(cell);
    }

    public GameStatus nextStep() {
        snake.moveStep();
        if (snakeHasCollided()) {
            return GameStatus.LOST;
        }
        if (snake.getHead().equals(apple)) {
            snake.grow();
            if (snake.getSize() >=  width * height) return GameStatus.WON;
            spawnApple();
            return GameStatus.GROWN;
        }
        return GameStatus.NORMAL;
    }

    public int getScore() {
        return snake.getSize() - 3;
    }

    public boolean snakeHasCollided() {
        //If out of bounds (hit wall)
        if (isOutOfBounds(snake.getHead())) {
            return true;
        }
        //If head has hit body
        for (Cell snakeBody: snake.getBody()) {
            if (snake.getHead().equals(snakeBody)) {
                return true;
            }
        }
        return false;
    }

    public void setSnakeHeadDirection(Direction dir) {
        snake.setHeadDirection(dir);
    }

    private boolean isOutOfBounds(Cell c) {
        int x = c.getX();
        int y = c.getY();
        if (x < 0 || y < 0 || x > width - 1 || y > height - 1) {
            return true;
        }
        return false;
    }

    public Cell getApple() {
        return apple;
    }

    public Cell getSnakeHead() {
        return snake.getHead();
    }

    public Collection<Cell> getSnakeBody() {
        return snake.getBody();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
