package me.ddquin.snake.gui;

import me.ddquin.snake.logic.Cell;
import me.ddquin.snake.logic.Direction;
import me.ddquin.snake.logic.GameStatus;
import me.ddquin.snake.logic.SnakeGame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class SnakeGameGUI extends JFrame {

    private SnakeGame game;
    private Timer timer;
    private SnakePanel snakePanel;
    private Difficulty difficulty;

    public static final int SF = 50; // Scaling factor
    public static final int OFFSET_X = 43;
    public static final int OFFSET_Y = 30;
    public static final int OFFSET_WINDOW = 100;

    public SnakeGameGUI(int width, int height, Difficulty difficulty)  {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snake Game");
        this.setSize(width * SF + OFFSET_WINDOW, height * SF + OFFSET_WINDOW );
        this.difficulty = difficulty;
        game = new SnakeGame(width, height);
        createSnakeBoard();
        this.setLocation(0, 0);
        this.setVisible(true);
    }

    private void createSnakeBoard() {
        snakePanel = new SnakePanel(game);
        this.add(snakePanel);
        ActionListener taskPerformer = evt -> {
            snakePanel.updateGUI();
            GameStatus status = game.nextStep();
            playCorrectSound(status);
            if (status == GameStatus.LOST || status == GameStatus.WON) {
                JOptionPane.showMessageDialog(this, "Your final score was " + game.getScore());
                closeAndOpenMenu();
            }
        };
        timer = new Timer(1000 / difficulty.fps, taskPerformer);
        timer.start();
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                            if (e.getID() == KeyEvent.KEY_PRESSED) {
                                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                                    game.setSnakeHeadDirection(Direction.RIGHT);
                                } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                                    game.setSnakeHeadDirection(Direction.LEFT);
                                } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                                    game.setSnakeHeadDirection(Direction.UP);
                                } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                                    game.setSnakeHeadDirection(Direction.DOWN);
                                }
                            }
                        return false;
                    }
                });
    }

    public void playSound(final String url) {
        try {
            URL ostFile = getClass().getResource(url);
            if (ostFile == null) {
                System.out.println("OST File not found.");
                return;
            }
            AudioInputStream aIn = AudioSystem.getAudioInputStream(ostFile);
            Clip clip = AudioSystem.getClip();
            clip.open(aIn);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void playCorrectSound(GameStatus status) {
        switch(status) {
            case GROWN:
                playSound("/grow.wav");
                break;
            case LOST:
                playSound("/lost.wav");
                break;
            case WON:
                playSound("/won.wav");
                break;
        }
    }


    private void closeAndOpenMenu() {
        timer.stop();
        new SnakeMenuGUI(game.getWidth(), game.getHeight());
        this.dispose();
    }

    public class SnakePanel extends JPanel {

        private SnakeGame game;

        public SnakePanel(SnakeGame game) {
            super();
            this.setSize(game.getWidth() * SF, game.getHeight() * SF);
            this.game = game;
        }

        public void updateGUI() {
            repaint();
        }


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Random rand = new Random();
            g.setColor(new Color(0x6C2E06));
            g.fillRect(0 ,0, game.getWidth() * SF + OFFSET_WINDOW, game.getHeight() * SF +OFFSET_WINDOW);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Default", Font.BOLD, 20));
            g.drawString("Score: " + game.getScore(), 30, 25);
            g.setColor(Color.BLACK);
            drawCheckerBoard(g, new Color(0xFFE7961C, true), new Color(0x875F06));
            g.setColor(new Color(0x9CE70D));
            for (Cell cell: game.getSnakeBody()) {
                    drawSnakeCell(g, cell);
            }
            drawSnakeEnd(g, game.getSnakeHead());
            g.setColor(Color.RED);
            drawCircle(g, game.getApple().getX() * SF + OFFSET_X, game.getApple().getY() * SF + OFFSET_Y, SF);
        }

        private void drawCheckerBoard(Graphics g, Color color1, Color color2) {
            Color currentColor = color1;
            for (int y = 0; y < game.getHeight(); y++) {
                int newY = (y * SF) + OFFSET_Y;
                for (int x = 0; x < game.getWidth(); x++) {
                    int newX = (x * SF) + OFFSET_X;
                    g.setColor(currentColor);
                    g.fillRect(newX, newY, SF, SF);
                    System.out.println(x);
                    //alternate between colors but if even number of width then don't alternate at last column of each row
                    if (!(game.getWidth() % 2 == 0 && x == game.getWidth() - 1)) {
                        currentColor = (currentColor == color1 ? color2 : color1);
                    }
                }
            }
        }

        private void drawSnakeEnd(Graphics g, Cell cell) {
            Direction endDir = game.getSnakeCellDirection(cell);
            drawArc(g, cell, endDir);
        }

        private void drawArc(Graphics g, Cell cell, Direction d) {
            int scaledX = cell.getX() * SF + OFFSET_X;
            int scaledY = cell.getY() * SF + OFFSET_Y;
            if (d == Direction.UP) {
                g.fillArc(scaledX, scaledY + SF/2, SF, SF, 0, 180 );
            } else if (d == Direction.DOWN) {
                g.fillArc(scaledX, scaledY - SF/2, SF, SF, 0, -180 );
            } else if (d == Direction.LEFT) {
                g.fillArc(scaledX + SF/2, scaledY, SF, SF, 90, 180);
            } else if (d == Direction.RIGHT) {
                g.fillArc(scaledX - SF/2, scaledY, SF, SF, -90, 180);
            }

        }

        private void drawSnakeCell(Graphics g, Cell cell) {
            int scaledX = cell.getX() * SF + OFFSET_X;
            int scaledY = cell.getY() * SF + OFFSET_Y;
            g.fillRect(scaledX, scaledY, SF , SF);
        }

        private void drawCircle(Graphics g, int x, int y, int r) {
            g.fillOval(x,y,r,r);
        }
    }
}
