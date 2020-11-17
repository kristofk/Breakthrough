package breakthrough.view;

import breakthrough.model.Cell;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CellView extends JButton implements PropertyChangeListener {

    private Cell model;

    public CellView(Cell cell) {
        super();

        model = cell;
        model.addPropertyChangeListener(this);

        Dimension size = new Dimension(50, 50);
        setPreferredSize(size);
        setOpaque(true);

        draw();
    }

    public void draw() {
        setText(model.toString());
        switch (model.getCurrentState()) {
            case enabled:
                setEnabled(true);
                setBackground(null);
                return;
            case disabled:
                setEnabled(false);
                setBackground(null);
                return;
            case selectedSource:
                setEnabled(false);
                setBackground(Color.lightGray);
                setOpaque(true);
                return;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        draw();
    }
}
