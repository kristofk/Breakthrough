package breakthrough.view;

import breakthrough.model.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The base class of the project.
 */
public class BreakthroughGUI implements PropertyChangeListener {

    /**
     * The default size of the initial board.
     */
    private final int defaultBoardSize = 6;

    // =================================================================================================================
    // Properties - Model
    // =================================================================================================================

    /**
     * Model of the board.
     */
    private Board board = new Board(defaultBoardSize);

    // =================================================================================================================
    // Properties - View
    // =================================================================================================================

    /**
     * Window of the app.
     */
    private JFrame window = new GameWindow();

    /**
     * Visual representation of the board.
     */
    private BoardView boardView = new BoardView(board);

    /**
     * Menu on the window.
     */
    private GameMenu menu = new GameMenu();

    // =================================================================================================================
    // Initializers
    // =================================================================================================================

    /**
     * Constructor of the BreakthroughGUI class.
     */
    public BreakthroughGUI() {
        window.setJMenuBar(menu);
        connectNewMenuItems();
        newGame(defaultBoardSize);

        window.pack();
    }

    /**
     * Connect the menu items to the proper actions.
     */
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

    /**
     * Connect the cell buttons to the correct actions.
     */
    private void connectCellButtons() {
        CellView[][] cellButtons = boardView.getButtons();
        for (int i = 0; i < cellButtons.length; i++) {
            for (int j = 0; j < cellButtons[i].length; j++) {
                cellButtons[i][j].addActionListener(new ButtonListener(i, j));
            }
        }
    }

    // =================================================================================================================
    // Methods
    // =================================================================================================================

    /**
     * Proper procedure to start a new game.
     * @param size The size of the new board.
     */
    private void newGame(int size) {
        clearGUI();
        board = new Board(size);
        boardView = new BoardView(board);
        window.add(boardView);
        connectCellButtons();
        window.pack();
        board.addPropertyChangeListener(this);
    }

    /**
     * Reset the visual contents of the app.
     */
    private void clearGUI() {
        window.getContentPane().remove(boardView);
    }

    /**
     * Handles change notifications.
     * @param evt Representation of the change.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("STARTING NEW GAME");
        board.removePropertyChangeListener(this);
        newGame(defaultBoardSize);
    }

    // =================================================================================================================
    // Action Listeners
    // =================================================================================================================

    /**
     * Object responsible for listening to cell presses.
     */
    class ButtonListener implements ActionListener {

        /**
         * The coordinates of the cell for identification.
         */
        private int x, y;

        /**
         * Constructor of the ButtonListener object.
         * @param x X part of the coordinate.
         * @param y Y part of teh coordinate.
         */
        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * The action to execute upon signal.
         * @param e Representation of the event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            board.cellSelectedAt(x, y);
        }
    }

}
