package breakthrough.view;

import breakthrough.model.Cell;

import javax.swing.*;
import java.awt.*;

public class CellGUI extends JButton {

    public CellGUI(Cell cell) {
        super();
        Dimension size = new Dimension(50, 50);
        setPreferredSize(size);
        setText(cell.toString());
        setEnabled(cell.isEnabled());
    }
}
