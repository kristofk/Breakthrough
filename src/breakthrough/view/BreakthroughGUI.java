package breakthrough.view;

import breakthrough.model.Board;
import breakthrough.model.BoardState;

import javax.swing.*;
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
        window.add(boardGUI);
        window.setJMenuBar(menu);
        connectNewMenuItems();

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

    // =================================================================================================================
    // Methods
    // =================================================================================================================

    private void newGame(int size) {

        // Model
        board = new Board(size);

        // View
        clearGUI();
        boardGUI = new BoardGUI(board);
        window.add(boardGUI);
        window.pack();
    }

    private void clearGUI() {
        window.getContentPane().remove(boardGUI);
    }

}
