package breakthrough.model;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 */
public class Cell {

    /**
     * The property that keeps track of the changes and listeners.
     */
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * Keeps track of who is currently on the cell or empty.
     */
    private CellOccupancy occupancy;

    /**
     * Coordinates of this cell.
     */
    private Point coords;

    /**
     * State of the cell for drawing.
     */
    private CellState currentState;

    // =================================================================================================================
    // Constructors
    // =================================================================================================================

    /**
     * Constructor of the Cell class.
     * @param occupancy The initial tenant of the cell.
     * @param coords The coordinates of the cell.
     */
    public Cell(CellOccupancy occupancy, Point coords) {
        this.occupancy = occupancy;
        this.coords = coords;
        this.currentState = CellState.disabled;
    }

    // =================================================================================================================
    // Getters
    // =================================================================================================================

    /**
     *
     * @return Value of the coords property.
     */
    public Point getCoords() {
        return coords;
    }

    /**
     *
     * @return Value of the occupancy property.
     */
    public CellOccupancy getOccupancy() {
        return occupancy;
    }

    /**
     *
     * @return Value of the currentState property.
     */
    public CellState getCurrentState() {
        return currentState;
    }

    // =================================================================================================================
    // Setters
    // These send notifications!
    // =================================================================================================================

    /**
     * Assigns the specified value to the occupancy property and sends notification about the update.
     * @param occupancy New value of occupancy.
     */
    public void setOccupancy(CellOccupancy occupancy) {
        CellOccupancy oldValue = this.occupancy;
        this.occupancy = occupancy;
        changes.firePropertyChange("occupancy", oldValue, occupancy);
    }

    /**
     * Assigng the specified value to the currentState property and send notification about the update.
     * @param currentState New value of currentState.
     */
    public void setCurrentState(CellState currentState) {
        CellState oldValue = this.currentState;
        this.currentState = currentState;
        changes.firePropertyChange("currentState", oldValue, currentState);
    }

    // =================================================================================================================
    // Overrides
    // =================================================================================================================

    /**
     *
     * @return String representation of this Cell class.
     */
    @Override
    public String toString() {
        switch (occupancy) {
            case empty:
                return "";
            case x:
                return "X";
            case o:
                return "O";
            default:
                return "E";
        }
    }

    // =================================================================================================================
    // Property listeners
    // =================================================================================================================

    /**
     * Add the class to the listeners to listen for changes in the class.
     * @param l The class that wants to listen.
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }

    /**
     * Removes the specified class from the listeners.
     * @param l The class to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }
}
