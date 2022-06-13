package battleship;

public class Ship {

    private final String name;
    private final int numOfCells;
    private final int[] startCoordinates;
    private final int[] endCoordinates;

    private final int[][] occupiedCells;

    int[][] getOccupiedCells() {
        return this.occupiedCells;
    }

    public Ship(String name, int numOfCells, int[] startCoordinates, int[] endCoordinates, int[][] occupitedCells) {
        this.name = name;
        this.numOfCells = numOfCells;
        this.startCoordinates = startCoordinates;
        this.endCoordinates = endCoordinates;
        this.occupiedCells = occupitedCells;
    }

}
