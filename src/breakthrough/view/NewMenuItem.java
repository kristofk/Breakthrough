package breakthrough.view;

import javax.swing.*;

/**
 * Menu item to start new game.
 */
public class NewMenuItem extends JMenuItem {

    /**
     * The board size this button holds.
     */
    private int boardSize;

    /**
     * Constructor for the NewMenuItem object.
     * @param boardSize The size of board to represent.
     */
    public NewMenuItem(int boardSize) {
        super(boardSize + "x" + boardSize);
        this.boardSize = boardSize;
    }

    /**
     *
     * @return Value of the boardSize.
     */
    public int getBoardSize() {
        return boardSize;
    }

}
