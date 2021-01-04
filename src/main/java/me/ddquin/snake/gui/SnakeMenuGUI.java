package me.ddquin.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeMenuGUI {
    private JFrame frame;
    private JLabel startLabel;
    private JTextField widthText, heightText;
    private JPanel startMenuPanel;
    private JComboBox<Difficulty> difficultySelector;
    private boolean inMenu;

    public SnakeMenuGUI(int startWidth, int startHeight) {
        inMenu = true;
        makeFrame(startWidth, startHeight);
    }

    public SnakeMenuGUI() {
        this(17, 17);
    }

    private void makeFrame(int startWidth, int startHeight) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 300));

        createStartMenu(startWidth, startHeight);

        frame.setLocation(200, 200);
        frame.setVisible(true);
    }

    private void createStartMenu(int startWidth, int startHeight) {
        Container contentPane = frame.getContentPane();

        //First row for setting width
        widthText = new JTextField();
        widthText.setText(startWidth + "");
        JLabel widthLabel = new JLabel("Enter in the width of the board.");
        widthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel widthPanel = new JPanel(new GridLayout(1, 2));
        widthPanel.add(widthLabel);
        widthPanel.add(widthText);

        //Second row for setting height
        heightText = new JTextField();
        heightText.setText(startHeight + "");
        JLabel heightLabel = new JLabel("Enter in the height of the board.");
        heightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel heightPanel = new JPanel(new GridLayout(1, 2));
        heightPanel.add(heightLabel);
        heightPanel.add(heightText);

        //Third row for setting difficulty
        difficultySelector = new JComboBox<>();
        difficultySelector.addItem(Difficulty.EASY);
        difficultySelector.addItem(Difficulty.MEDIUM);
        difficultySelector.addItem(Difficulty.HARD);
        difficultySelector.setSelectedIndex(1);
        JLabel difficultyLabel = new JLabel("Choose the difficulty of the game");
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel difficultyPanel = new JPanel(new GridLayout(1, 2));
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(difficultySelector);

        //Start button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> checkIfCanStartGame());
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (inMenu) {
                            if (e.getID() == KeyEvent.KEY_PRESSED) {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    checkIfCanStartGame();
                                }
                            }
                        }
                        return false;
                    }
                });
        startLabel = new JLabel("Enter in values and press start");
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel startPanel = new JPanel(new GridLayout(1, 2));
        startPanel.add(startLabel);
        startPanel.add(startButton);

        //Connect all four rows
        startMenuPanel = new JPanel();
        startMenuPanel.setLayout(new BoxLayout(startMenuPanel, BoxLayout.Y_AXIS));
        startMenuPanel.add(widthPanel);
        startMenuPanel.add(heightPanel);
        startMenuPanel.add(difficultyPanel);
        startMenuPanel.add(startPanel);
        contentPane.add(startMenuPanel, BorderLayout.CENTER);

    }

    private void checkIfCanStartGame() {
        int width;
        int height;
        try {
            width = Integer.parseInt(widthText.getText());
            height = Integer.parseInt(heightText.getText());

            if (width < 3 || height < 3 ) {
                startLabel.setText("All fields must be greater than 2");
                startLabel.setForeground(Color.RED);
                return;
            }
            startLabel.setText("Enter in values and press start");
            startLabel.setForeground(Color.BLACK);
            new SnakeGameGUI(width, height, (Difficulty) difficultySelector.getSelectedItem());
            inMenu = false;
            frame.dispose();
        } catch (NumberFormatException e) {
            startLabel.setText("Please make sure all fields are numbers");
            startLabel.setForeground(Color.RED);
        }
    }
}
