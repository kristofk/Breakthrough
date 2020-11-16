package breakthrough.model;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Board {

    private int size;
    private Cell[][] cells;
    BoardState state = BoardState.xSelectSource;
    Cell selectedCell;

    // =================================================================================================================
    // Constructors
    // =================================================================================================================

    public Board(int size) {
        this.size = size;
        cells = new Cell[size][size];
        loadDefaultSetup();
        setState(BoardState.xSelectSource);
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

    // todo: Only cells that have possible destinations!
    private void enablePossibleXSources() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (possibleDestinationsFor(cell).size() == 0) continue;
                if (cell.getOccupancy() == CellOccupancy.x) cell.setCurrentState(CellState.enabled);
                else cell.setCurrentState(CellState.disabled);
            }
        }
    }

    // todo: Only possible destinations from selected cell.
    private void enablePossibleDestinations() {
        System.out.println("enablePossibleDestinations");
//        for (Cell[] row : cells) {
//            for (Cell cell : row) {
//                CellOccupancy occupancy = cell.getOccupancy();
//                CellState state = cell.getCurrentState();
//                if (state == CellState.selectedSource) continue;
//                if (occupancy == CellOccupancy.empty) cell.setCurrentState(CellState.enabled);
//                else cell.setCurrentState(CellState.disabled);
//            }
//        }
        disableAll(false);
        ArrayList<Cell> possibleDestinations = possibleDestinationsFor(selectedCell);
        for (Cell cell : possibleDestinations) {
            cell.setCurrentState(CellState.enabled);
        }
    }

    private void enablePossibleOSources() {
        // todo
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (possibleDestinationsFor(cell).size() == 0) continue;
                if (cell.getOccupancy() == CellOccupancy.o) cell.setCurrentState(CellState.enabled);
                else cell.setCurrentState(CellState.disabled);
            }
        }
    }

    // todo: implement
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

    public ArrayList<Cell> possibleDestinationsFor(Cell sourceCell) {
        ArrayList<Cell> possibleDestinations = new ArrayList<>();

        Point sourceCellCoords = sourceCell.getCoords();
        int sourceCellRow = sourceCellCoords.x;
        int sourceCellColumn = sourceCellCoords.y;
        CellOccupancy sourceCellOccupancy = sourceCell.getOccupancy();

        int row = (sourceCellOccupancy == CellOccupancy.x) ? sourceCellRow - 1 : sourceCellRow + 1;
        int columnLowerBound = sourceCellColumn - 1;
        int columnUpperBound = sourceCellColumn + 1;

        for (Cell cell : cells[row]) {
            if (cell.getOccupancy() == sourceCell.getOccupancy()) continue;
            int cellColumn = cell.getCoords().y;
            if (columnLowerBound <= cellColumn && cellColumn <= columnUpperBound) {
                possibleDestinations.add(cell);
            }
        }

        return possibleDestinations;
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
    // State setters
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
                move(selectedCell.getCoords(), new Point(row,column));
                setState(BoardState.xSelectSource);
                return;
        }
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

    public void enableCell(int row, int column) {
        cells[row][column].setCurrentState(CellState.enabled);
    }

    public void disableCell(int row, int column) {
        cells[row][column].setCurrentState(CellState.disabled);
    }

    public void setCellState(Point coords, CellState newState) {
        cells[coords.x][coords.y].setCurrentState(newState);
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
