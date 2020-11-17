package breakthrough.model;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Board {

    /**
     * The property that keeps track of the changes and listeners.
     */
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * The size of the square board.
     */
    private int size;

    /**
     * The cells on the board.
     */
    private Cell[][] cells;

    /**
     * The state of the board.
     */
    private BoardState state = BoardState.xSelectSource;

    /**
     * The cell that is marked as the source of the move.
     */
    private Cell selectedCell;

    /**
     * Property to keep track whether the game is over.
     */
    boolean gameOver = false;

    // =================================================================================================================
    // Constructors
    // =================================================================================================================

    /**
     * Constructor for `Board`.
     * @param size The size of the board to construct.
     */
    public Board(int size) {
        this.size = size;
        cells = new Cell[size][size];
        loadDefaultSetup();
        setState(BoardState.xSelectSource);
    }

    /**
     * Loads the initial empty board.
     */
    private void loadDefaultSetup() {
        fillLine(0, CellOccupancy.o);
        fillLine(1, CellOccupancy.o);
        fillLine(size - 1, CellOccupancy.x);
        fillLine(size - 2, CellOccupancy.x);
        for (int i = 2; i < size - 2; i++) {
            fillLine(i, CellOccupancy.empty);
        }
    }

    /**
     * Fills all data points in the specified line with the specified data.
     * @param lineIndex The line to fill.
     * @param occupancy The data to fill the line with.
     */
    private void fillLine(int lineIndex, CellOccupancy occupancy) {
        for (int i = 0; i < size; i++) {
            Point coords = new Point(lineIndex, i);
            cells[lineIndex][i] = new Cell(occupancy, coords);
        }
    }

    // =================================================================================================================
    // Methods - Board updates
    // =================================================================================================================

    /**
     * Make the cellState enabled for every cell from where X can initiate a move.
     */
    private void enablePossibleXSources() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (possibleDestinationsFor(cell).size() == 0) continue;
                if (cell.getOccupancy() == CellOccupancy.x) cell.setCurrentState(CellState.enabled);
                else cell.setCurrentState(CellState.disabled);
            }
        }
    }

    /**
     * Make the cellState enabled for every cell from where O can initiate a move.
     */
    private void enablePossibleOSources() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (possibleDestinationsFor(cell).size() == 0) continue;
                if (cell.getOccupancy() == CellOccupancy.o) cell.setCurrentState(CellState.enabled);
                else cell.setCurrentState(CellState.disabled);
            }
        }
    }

    /**
     * Make the cellState enabled for every cell to where the player can move from the selectedSource.
     */
    private void enablePossibleDestinations() {
        disableAll(false);
        ArrayList<Cell> possibleDestinations = possibleDestinationsFor(selectedCell);
        for (Cell cell : possibleDestinations) {
            cell.setCurrentState(CellState.enabled);
        }
    }

    /**
     * Move the selected piece at the source to the destination.
     * @param source The piece to move.
     * @param destination The location where the piece will move to.
     */
    private void move(Point source, Point destination) {
        Cell sourceCell = cells[source.x][source.y];
        Cell destinationCell = cells[destination.x][destination.y];
        destinationCell.setOccupancy(sourceCell.getOccupancy());
        sourceCell.setOccupancy(CellOccupancy.empty);
    }

    /**
     * Properly clear the selectedCell property.
     */
    private void clearSelection() {
        selectedCell.setCurrentState(CellState.disabled);
        selectedCell = null;
    }

    /**
     * Disable all enabled cells.
     * @param disableSelected If true then the selected cell will also be disabled, otherwise it will stay.
     */
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

    /**
     * Changes the state property and updates the cells accordingly.
     * @param newState The new value of the state property.
     */
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

    /**
     * Find all the possible destinations where the piece can move to.
     * @param sourceCell The piece to move.
     * @return All possible destinations where the piece can move.
     */
    private ArrayList<Cell> possibleDestinationsFor(Cell sourceCell) {
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

        if (row < 0 || size <= row) return possibleDestinations;

        int columnLowerBound = sourceCellColumn - 1;
        int columnUpperBound = sourceCellColumn + 1;

        for (Cell cell : cells[row]) {
            if (cell.getOccupancy() == sourceCellOccupancy) continue;

            int cellColumn = cell.getCoords().y;
            if (!(columnLowerBound <= cellColumn && cellColumn <= columnUpperBound)) continue;
            if (cellColumn == sourceCellColumn && cell.getOccupancy() != CellOccupancy.empty) continue;
            possibleDestinations.add(cell);
        }
        return possibleDestinations;
    }

    /**
     * Detects and handles the end of the game.
     */
    private void checkGameOver() {
        if (didXWin()) {
            JOptionPane.showMessageDialog(null, "X won!");
            setGameOver(true);
        } else if (didOWin()) {
            JOptionPane.showMessageDialog(null, "O won!");
            setGameOver(true);
        }
    }

    /**
     * Checks if X won the game.
     * @return Boolean whether X won the game.
     */
    private boolean didXWin() {
        Cell[] topRow = cells[0];
        for (Cell cell : topRow) {
            if (cell.getOccupancy() == CellOccupancy.x) return true;
        }

        return false;
    }

    /**
     * Checks if O won the game.
     * @return Boolean whether O won the game.
     */
    private boolean didOWin() {
        Cell[] topRow = cells[size - 1];
        for (Cell cell : topRow) {
            if (cell.getOccupancy() == CellOccupancy.o) return true;
        }

        return false;
    }

    // =================================================================================================================
    // Getters & setters
    // =================================================================================================================

    /**
     *
     * @return Value of the size property.
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return Value of the cells property.
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Sets the gameOver property to the specified value.
     * @param gameOver New value of the gameOver property.
     */
    public void setGameOver(boolean gameOver) {
        boolean oldValue = this.gameOver;
        this.gameOver = gameOver;
        changes.firePropertyChange("gameOver", oldValue, gameOver);
    }

    /**
     * Signal which cell has been selected on the UI.
     * @param row The row part of the coordinate.
     * @param column The columns part of the coordinate.
     */
    public void cellSelectedAt(int row, int column) {
        Cell selection = cells[row][column];
        switch (state) {
            case xSelectSource:
                selectedCell = cells[row][column];
                selectedCell.setCurrentState(CellState.selectedSource);
                setState(BoardState.xSelectDestination);
                break;
            case oSelectSource:
                selectedCell = cells[row][column];
                selectedCell.setCurrentState(CellState.selectedSource);
                setState(BoardState.oSelectDestination);
                break;
            case xSelectDestination:
                move(selectedCell.getCoords(), new Point(row, column));
                setState(BoardState.oSelectSource);
                clearSelection();
                break;
            case oSelectDestination:
                move(selectedCell.getCoords(), new Point(row, column));
                setState(BoardState.xSelectSource);
                clearSelection();
                break;
        }

        checkGameOver();
    }

    // =================================================================================================================
    // Overrides
    // =================================================================================================================

    /**
     *
     * @return String representation of the Boar class.
     */
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

    // =================================================================================================================
    // Property listeners
    // =================================================================================================================

    /**
     * Add the class to the listeners to listen for changes in teh Board class.
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
