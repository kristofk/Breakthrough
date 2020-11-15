package breakthrough.model;

import java.awt.*;

public class Board {

    private int size;
    private Cell[][] cells;
    BoardState state = BoardState.xSelectSource;

    // =================================================================================================================
    // Constructors
    // =================================================================================================================

    public Board(int size) {
        this.size = size;
        cells = new Cell[size][size];
        loadDefaultSetup();
        newState(BoardState.xSelectSource);
    }

    // =================================================================================================================
    // Methods
    // =================================================================================================================

    private void loadDefaultSetup() {
        fillLineWith(0, CellOccupancy.o);
        fillLineWith(1, CellOccupancy.o);
        fillLineWith(size - 1, CellOccupancy.x);
        fillLineWith(size - 2, CellOccupancy.x);
        for (int i = 2; i < size - 2; i++) {
            fillLineWith(i, CellOccupancy.empty);
        }
    }

    private void fillLineWith(int lineIndex, CellOccupancy occupancy) {
        for (int i = 0; i < size; i++) {
            Point coords = new Point(lineIndex, i);
            cells[lineIndex][i] = new Cell(occupancy, coords);
        }
    }

    public void newState(BoardState state) {
        switch (state) {
            case xSelectSource:
                enableOnlyX();
            case xSelectDestination: return;
            case oSelectSource: return;
            case oSelectDestination: return;
        }
    }

    private void enableOnlyX() {
        for (Cell[] cellRow : cells) {
            for (Cell cell : cellRow) {
                if (cell.getOccupancy() == CellOccupancy.x) {
                    cell.setEnabled(true);
                } else {
                    cell.setEnabled(false);
                }
            }
        }
    }

    // =================================================================================================================
    // Getters & setters
    // =================================================================================================================

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    // =================================================================================================================
    // Overrides
    // =================================================================================================================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(cells[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
