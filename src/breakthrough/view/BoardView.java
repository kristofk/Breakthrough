package breakthrough.view;

import breakthrough.model.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical representation of the Board.
 */
public class BoardView extends JPanel {

    /**
     * Cells on the board.
     */
    private CellView[][] buttons;

    /**
     * Constructor of the BoardView class.
     * @param board The board to represent.
     */
    public BoardView(Board board) {
        super();
        int size = board.getSize();
        buttons = new CellView[size][size];

        GridLayout gridLayout = new GridLayout(size, size);
        setLayout(gridLayout);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CellView button = new CellView(board.getCells()[i][j]);
                buttons[i][j] = button;
                add(button);
            }
        }

    }

    /**
     *
     * @return Cells on the board.
     */
    public CellView[][] getButtons() {
        return buttons;
    }

}
