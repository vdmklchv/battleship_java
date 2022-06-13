package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship {

    final int CELL_IS_HIT = -1;

    private final String name;
    private final int numOfCells;
    private final int[] startCoordinates;
    private final int[] endCoordinates;

    private final int[][] occupiedCells;
    private List<int[]> hitCells;

    int[][] getOccupiedCells() {
        return this.occupiedCells;
    }

    public Ship(String name, int numOfCells, int[] startCoordinates, int[] endCoordinates, int[][] occupiedCells) {
        this.name = name;
        this.numOfCells = numOfCells;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
        this.occupiedCells = occupiedCells;
        this.hitCells = new ArrayList<>();
    }

    void takeHit(int[] coordinates) {
        for (int[] cell: hitCells) {
            if (Arrays.equals(cell, coordinates)) {
                return;
            }
        }
        hitCells.add(coordinates);
    }

    boolean isShipSank() {
        int countOfDamagedCells = 0;
        for (int[] occupiedCell: occupiedCells) {
            for (int[] hitCell: hitCells) {
                if (Arrays.equals(occupiedCell, hitCell)) {
                    countOfDamagedCells++;
                }
            }
        }

        return countOfDamagedCells == numOfCells;
    }
}
