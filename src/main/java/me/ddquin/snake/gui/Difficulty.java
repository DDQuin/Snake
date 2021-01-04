package me.ddquin.snake.gui;

public enum Difficulty {

    EASY(5),
    MEDIUM(10),
    HARD(20);

    public int fps;

    Difficulty(int fps) {
        this.fps = fps;
    }
}
