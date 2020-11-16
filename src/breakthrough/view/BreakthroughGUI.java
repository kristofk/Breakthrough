package breakthrough.view;

import breakthrough.model.Board;
import breakthrough.model.BoardState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BreakthroughGUI implements PropertyChangeListener {

    private final int defaultBoardSize = 6;

    // =================================================================================================================
    // Properties - Model
    // =================================================================================================================

    Board board = new Board(defaultBoardSize);
    BoardState state = BoardState.xSelectSource;

    // =================================================================================================================
    // Properties - View
    // =================================================================================================================

    JFrame window = new GameWindow();
    BoardGUI boardGUI = new BoardGUI(board);
    GameMenu menu = new GameMenu();

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
        board.addPropertyChangeListener(this);
    }

    private void clearGUI() {
        window.getContentPane().remove(boardGUI);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("STARTING NEW GAME");
        board.removePropertyChangeListener(this);
        newGame(defaultBoardSize);
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
            board.cellSelectedAt(x, y);
        }
    }

}
