package breakthrough.model;

import java.awt.*;

public class Cell {

    private CellOccupancy occupancy;
    private Point coords;
    private CellState currentState;

    // =================================================================================================================
    // Constructors
    // =================================================================================================================

    public Cell(CellOccupancy occupancy, Point coords) {
        this.occupancy = occupancy;
        this.coords = coords;
        this.currentState = CellState.disabled;
    }

    public Cell(Point coords) {
        this(CellOccupancy.empty, coords);
    }

    // =================================================================================================================
    // Methods
    // =================================================================================================================

    public boolean canInitiateMove(Player player) {
        switch (player) {
            case o:
                return occupancy == CellOccupancy.o;
            case x:
                return occupancy == CellOccupancy.x;
            default:
                return false;
        }
    }

    // =================================================================================================================
    // Getters and setters
    // =================================================================================================================

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }

    public CellOccupancy getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(CellOccupancy occupancy) {
        this.occupancy = occupancy;
    }

    public CellState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CellState currentState) {
        this.currentState = currentState;
    }
// =================================================================================================================
    // Overrides
    // =================================================================================================================

    @Override
    public String toString() {
        switch (occupancy) {
            case empty:
                return "-";
            case x:
                return "X";
            case o:
                return "O";
            default:
                return "E";
        }
    }
}
