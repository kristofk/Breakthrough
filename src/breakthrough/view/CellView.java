package breakthrough.view;

import breakthrough.model.Cell;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Visual representation of the Cell.
 */
public class CellView extends JButton implements PropertyChangeListener {

    /**
     * The model to represent.
     */
    private Cell model;

    /**
     * Constructor for the CellView class.
     * @param cell The model to represent.
     */
    public CellView(Cell cell) {
        super();

        model = cell;
        model.addPropertyChangeListener(this);

        Dimension size = new Dimension(50, 50);
        setPreferredSize(size);
        setOpaque(true);

        draw();
    }

    /**
     * Draws the visuals based on the model.
     */
    private void draw() {
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

    /**
     * Handles changes to the model.
     * @param evt Representation of the event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        draw();
    }
}
