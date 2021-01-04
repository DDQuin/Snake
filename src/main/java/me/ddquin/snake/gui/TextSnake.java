package me.ddquin.snake.gui;

import me.ddquin.snake.logic.Cell;
import me.ddquin.snake.logic.Direction;
import me.ddquin.snake.logic.GameStatus;
import me.ddquin.snake.logic.SnakeGame;

import java.util.Scanner;

public class TextSnake {

    private SnakeGame game;

    public TextSnake() {
        game = new SnakeGame(5, 3);
        Scanner scan = new Scanner(System.in);
        displaySnake();
        while(true) {
            String input = scan.nextLine();
            for (Direction d: Direction.values()) {
                if (input.equalsIgnoreCase(d.name())) {
                    game.setSnakeHeadDirection(d);
                    System.out.println("Snake head set to direction " + d.name().toLowerCase());
                }
            }
            if (input.equalsIgnoreCase("move")) {
                GameStatus status = game.nextStep();
                if (status == GameStatus.LOST) {
                    System.out.println("You lost!");
                    System.exit(0);
                } else if (status == GameStatus.WON) {
                    System.out.println("You won!");
                    System.exit(0);
                }
                displaySnake();
            }
        }
    }

    private void displaySnake() {
        for (int row = 0; row < game.getHeight(); row++) {
            System.out.println();
            for (int column = 0; column < game.getWidth(); column++) {
                Cell cell = new Cell(column, row);
                boolean isSnakeCell = false;
                for (Cell c: game.getSnakeBody()) {
                    if (cell.equals(c)) {
                        isSnakeCell = true;
                    }
                }
                if (isSnakeCell) {
                    System.out.print("S ");
                }
                else if (game.getSnakeHead().equals(cell)) {
                    System.out.print("H ");
                } else if (game.getApple().equals(cell)) {
                    System.out.print("A ");
                }
                else {
                    System.out.print("O ");
                }
            }
        }
        System.out.println();
    }


}
