package breakthrough.view;

import breakthrough.model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardGUI extends JPanel {

    private GridLayout gridLayout;
    private CellGUI[][] buttons;

    // =================================================================================================================
    // Initializers
    // =================================================================================================================

    public BoardGUI(Board board) {
        super();
        System.out.println(board);
        int size = board.getSize();
        buttons = new CellGUI[size][size];

        GridLayout gridLayout = new GridLayout(size, size);
        setLayout(gridLayout);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CellGUI button = new CellGUI(board.getCells()[i][j]);
                buttons[i][j] = button;
                add(button);
            }
        }

    }

    // =================================================================================================================
    // Functions
    // =================================================================================================================

    // =================================================================================================================
    // Getters & Setters
    // =================================================================================================================

    public CellGUI[][] getButtons() {
        return buttons;
    }

}
