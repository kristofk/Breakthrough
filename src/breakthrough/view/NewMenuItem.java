package breakthrough.view;

import javax.swing.*;

public class NewMenuItem extends JMenuItem {

    private int boardSize;

    public NewMenuItem(int boardSize) {
        super(boardSize + "x" + boardSize);
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

}
