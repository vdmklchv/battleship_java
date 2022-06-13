package battleship;

import java.util.Arrays;

class BattleField {
    // CONSTANTS
    static final int DEFAULT_FIELD_SIZE = 10;
    // FIELDS
    private final int size;

    int getSize() {
        return size;
    }

    private final String[][] startingField;

    // CONSTRUCTORS

    public BattleField() {
        this(DEFAULT_FIELD_SIZE);
    }

    public BattleField(int size) {
        this.size = size;
        this.startingField = createEmptyField(this.size);
    }

    private String[][] createEmptyField(int size) {
        String[][] startingFieldArray = new String[size][size];
        for (String[] row: startingFieldArray) {
            Arrays.fill(row, "~");
        }
        return startingFieldArray;
    }

    String getCellData(int x, int y) {
        return this.startingField[x][y];
    }

    void printCurrentField() {
        // create letter coordinate array
        String[] letterCoordinateArray = createLetterCoordinateArray(this.size);

        // print header
        System.out.print("  ");
        for (int i = 1; i <= this.size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // print field and append letter coordinate in front
        for (int i = 0; i < this.size; i++) {
            String[] row = this.startingField[i];
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

    public void placeShip(Ship ship) {
        int[][] occupiedCells = ship.getOccupiedCells();

        for (int[] cellCoordinate: occupiedCells) {
            startingField[cellCoordinate[0]][cellCoordinate[1]] = "O";
        }
    }

}
