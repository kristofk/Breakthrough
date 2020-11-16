package breakthrough.model;

import java.awt.*;
import java.util.ArrayList;

public class Board {

    private int size;
    private Cell[][] cells;
    private BoardState state = BoardState.xSelectSource;
    private Cell selectedCell;

    // =================================================================================================================
    // Constructors
    // =================================================================================================================

    public Board(int size) {
        this.size = size;
        cells = new Cell[size][size];
        loadDefaultSetup();
        setState(BoardState.xSelectSource);
    }

    private void loadDefaultSetup() {
        fillLine(0, CellOccupancy.o);
        fillLine(1, CellOccupancy.o);
        fillLine(size - 1, CellOccupancy.x);
        fillLine(size - 2, CellOccupancy.x);
        for (int i = 2; i < size - 2; i++) {
            fillLine(i, CellOccupancy.empty);
        }
    }

    private void fillLine(int lineIndex, CellOccupancy occupancy) {
        for (int i = 0; i < size; i++) {
            Point coords = new Point(lineIndex, i);
            cells[lineIndex][i] = new Cell(occupancy, coords);
        }
    }

    // =================================================================================================================
    // Methods - Board updates
    // =================================================================================================================

    private void enablePossibleXSources() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (possibleDestinationsFor(cell).size() == 0) continue;
                if (cell.getOccupancy() == CellOccupancy.x) cell.setCurrentState(CellState.enabled);
                else cell.setCurrentState(CellState.disabled);
            }
        }
    }

    private void enablePossibleOSources() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (possibleDestinationsFor(cell).size() == 0) continue;
                if (cell.getOccupancy() == CellOccupancy.o) cell.setCurrentState(CellState.enabled);
                else cell.setCurrentState(CellState.disabled);
            }
        }
    }

    private void enablePossibleDestinations() {
        disableAll(false);
        ArrayList<Cell> possibleDestinations = possibleDestinationsFor(selectedCell);
        for (Cell cell : possibleDestinations) {
            cell.setCurrentState(CellState.enabled);
        }
    }

    private void move(Point source, Point destination) {
        System.out.println(source + " -> " + destination);
        Cell sourceCell = cells[source.x][source.y];
        Cell destinationCell = cells[destination.x][destination.y];
        destinationCell.setOccupancy(sourceCell.getOccupancy());
        sourceCell.setOccupancy(CellOccupancy.empty);
    }

    private void clearSelection() {
        selectedCell.setCurrentState(CellState.disabled);
        selectedCell = null;
    }

    private void disableAll(boolean disableSelected) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getCurrentState() == CellState.selectedSource) {
                    if (disableSelected) cell.setCurrentState(CellState.disabled);
                } else {
                    cell.setCurrentState(CellState.disabled);
                }
            }
        }
    }

    // =================================================================================================================
    // Methods - Helpers
    // =================================================================================================================

    private void setState(BoardState newState) {
        state = newState;
        switch (state) {
            case xSelectSource:
                enablePossibleXSources();
                return;
            case oSelectSource:
                enablePossibleOSources();
                return;
            case xSelectDestination, oSelectDestination:
                enablePossibleDestinations();
                return;
        }
    }

    public ArrayList<Cell> possibleDestinationsFor(Cell sourceCell) {
        ArrayList<Cell> possibleDestinations = new ArrayList<>();

        Point sourceCellCoords = sourceCell.getCoords();
        int sourceCellRow = sourceCellCoords.x;
        int sourceCellColumn = sourceCellCoords.y;
        CellOccupancy sourceCellOccupancy = sourceCell.getOccupancy();

        int row = (sourceCellOccupancy == CellOccupancy.x) ?
                sourceCellRow - 1 :
                (sourceCellOccupancy == CellOccupancy.o) ?
                        sourceCellRow + 1 :
                        sourceCellRow;

        int columnLowerBound = sourceCellColumn - 1;
        int columnUpperBound = sourceCellColumn + 1;

        for (Cell cell : cells[row]) {
            if (cell.getOccupancy() == sourceCellOccupancy) continue;

            int cellColumn = cell.getCoords().y;
            if (columnLowerBound > cellColumn && cellColumn > columnUpperBound) continue;
            if (cellColumn == sourceCellColumn && cell.getOccupancy() != CellOccupancy.empty) continue;
            possibleDestinations.add(cell);
        }

        return possibleDestinations;
    }

    // =================================================================================================================
    // Getters & setters
    // =================================================================================================================

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void cellSelectedAt(int row, int column) {
        Cell selection = cells[row][column];
        switch (state) {
            case xSelectSource:
                selectedCell = cells[row][column];
                selectedCell.setCurrentState(CellState.selectedSource);
                setState(BoardState.xSelectDestination);
                return;
            case oSelectSource:
                selectedCell = cells[row][column];
                selectedCell.setCurrentState(CellState.selectedSource);
                setState(BoardState.oSelectDestination);
                return;
            case xSelectDestination:
                move(selectedCell.getCoords(), new Point(row, column));
                setState(BoardState.oSelectSource);
                clearSelection();
                return;
            case oSelectDestination:
                move(selectedCell.getCoords(), new Point(row, column));
                setState(BoardState.xSelectSource);
                return;
        }
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
