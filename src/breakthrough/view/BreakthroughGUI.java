package breakthrough.view;

import breakthrough.model.Board;
import breakthrough.model.BoardState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

public class BreakthroughGUI {

    private final int defaultBoardSize = 6;

    // =================================================================================================================
    // Model properties
    // =================================================================================================================

    Board board = new Board(defaultBoardSize);
    BoardState state = BoardState.xSelectSource;

    // =================================================================================================================
    // View properties
    // =================================================================================================================

    JFrame window = new GameWindow();
    BoardGUI boardGUI = new BoardGUI(board);
    GameMenu menu = new GameMenu();
    JButton[][] buttons;

    // =================================================================================================================
    // Initializers
    // =================================================================================================================

    public BreakthroughGUI() {
        window.setJMenuBar(menu);
        connectNewMenuItems();
        newGame(defaultBoardSize);

        window.pack();
    }

    private void connectNewMenuItems() {
        NewMenuItem[] items = menu.getNewMenuItems();
        for (NewMenuItem item : items) {
            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newGame(item.getBoardSize());
                }
            });
        }
    }

    private void connectCellButtons() {
        CellGUI[][] cellButtons = boardGUI.getButtons();
        for (int i = 0; i < cellButtons.length; i++) {
            for (int j = 0; j < cellButtons[i].length; j++) {
                cellButtons[i][j].addActionListener(new ButtonListener(i, j));
            }
        }
    }

    // =================================================================================================================
    // Methods
    // =================================================================================================================

    private void newGame(int size) {
        clearGUI();
        board = new Board(size);
        boardGUI = new BoardGUI(board);
        window.add(boardGUI);
        connectCellButtons();
        window.pack();
    }

    private void clearGUI() {
        window.getContentPane().remove(boardGUI);
    }

    // =================================================================================================================
    // Action Listeners
    // =================================================================================================================

    class ButtonListener implements ActionListener {

        private int x, y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button " + x + ", " + y);
//            if (board.get(x, y).getColor() == null) {
//                Color color = new Color(random.nextInt(256),
//                        random.nextInt(256), random.nextInt(256));
//                board.get(x, y).setColor(color);
//                board.get(x, y).setNumber(++clickNum);
//                for (int i = 0; i < NUM_COLORED_FIELDS;) {
//                    Point point = points.remove(points.size()-1);
//                    if (board.get(point).getColor() == null) {
//                        board.get(point).setColor(color);
//                        board.get(point).setNumber(clickNum);
//                        i++;
//                    }
//                }
//                refresh();
//            }
        }
    }

}
