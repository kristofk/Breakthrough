package breakthrough.view;

import breakthrough.model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {

    private CellView[][] buttons;

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

    public CellView[][] getButtons() {
        return buttons;
    }

}
