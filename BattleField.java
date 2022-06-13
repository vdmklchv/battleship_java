package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BattleField {
    enum FIELD_TYPES {
        PUBLIC_FIELD, PRIVATE_FIELD
    }

    // CONSTANTS
    static final int DEFAULT_FIELD_SIZE = 10;
    // FIELDS
    private final int size;

    int getSize() {
        return size;
    }

    private final String[][] privateField;

    private final String[][] currentField;

    private final List<Ship> ships = new ArrayList<>();

    // CONSTRUCTORS

    public BattleField() {
        this(DEFAULT_FIELD_SIZE);
    }

    public BattleField(int size) {
        this.size = size;
        this.privateField = createEmptyField(this.size);
        this.currentField = createEmptyField(this.size);
    }

    private String[][] createEmptyField(int size) {
        String[][] startingFieldArray = new String[size][size];
        for (String[] row: startingFieldArray) {
            Arrays.fill(row, "~");
        }
        return startingFieldArray;
    }

    String getCellData(int x, int y) {
        return this.privateField[x][y];
    }

    void printField(FIELD_TYPES type) {
        // create letter coordinate array
        String[] letterCoordinateArray = createLetterCoordinateArray(this.size);

        // print header
        System.out.print("  ");
        for (int i = 1; i <= this.size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        String[][] field = type == FIELD_TYPES.PRIVATE_FIELD ? this.privateField : this.currentField;

        // print field and append letter coordinate in front
        for (int i = 0; i < this.size; i++) {
            String[] row = field[i];
            String letterCoordinate = letterCoordinateArray[i];
            System.out.printf("%s %s", letterCoordinate, String.join(" ", row));
            System.out.println();
        }
    }

    private String[] createLetterCoordinateArray(int size) {
        String[] letterCoordinateArray = new String[size];

        char start = 'A';
        letterCoordinateArray[0] = String.valueOf(start);

        for (int i = 1; i < this.size; i++) {
            letterCoordinateArray[i] = String.valueOf((char) (start + i));
        }

        return letterCoordinateArray;
    }

    void placeShip(Ship ship) {
        int[][] occupiedCells = ship.getOccupiedCells();

        for (int[] cellCoordinate: occupiedCells) {
            privateField[cellCoordinate[0]][cellCoordinate[1]] = "O";
        }
        saveShip(ship);
    }

    private void saveShip(Ship ship) {
        this.ships.add(ship);
    }

    boolean isHit(int[] coordinates) {
        return "O".equals(privateField[coordinates[0]][coordinates[1]])
                || "X".equals(privateField[coordinates[0]][coordinates[1]]);
    }

    void updateFields(int[] coordinates, String value) {
        privateField[coordinates[0]][coordinates[1]] = value;
        currentField[coordinates[0]][coordinates[1]] = value;
    }

    Ship findShipAtCoordinates(int[] coordinates) {
        for (Ship ship: ships) {
            int[][] occupiedCells = ship.getOccupiedCells();
            for (int[] cell: occupiedCells) {
                if (Arrays.equals(cell, coordinates)) {
                    return ship;
                }
            }
        }
        return null;
    }

    boolean isGameOver() {
        for (Ship ship: ships) {
            if (!ship.isShipSank()) {
                return false;
            }
        }
        return true;
    }
}
