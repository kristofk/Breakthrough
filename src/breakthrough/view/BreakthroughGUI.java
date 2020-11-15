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
    JButton[][] buttons;

    // =================================================================================================================
    // Initializers
    // =================================================================================================================

    public BreakthroughGUI() {
        window.add(boardGUI);

        initializeMenu();
//        initializeButtons();
        window.pack();
    }

    private void initializeMenu() {
        GameMenu menuBar = new GameMenu();
        window.setJMenuBar(menuBar);
        int[] boardSizes = new int[]{6, 8, 10};
        for (int boardSize: boardSizes) {
            menuBar.getNewMenu().add(newBoardSizeMenuItem(boardSize));
        }
    }

    JMenuItem newBoardSizeMenuItem(int size) {
        JMenuItem sizeMenuItem = new JMenuItem(size + "x" + size);
        sizeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame(size);
            }
        });
        return sizeMenuItem;
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
