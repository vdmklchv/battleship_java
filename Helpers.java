package battleship;

import java.util.Scanner;

class Helpers {

    static Scanner sc = new Scanner(System.in);
    static int[][] getShipInitialCoordinates() {
        String startCoordinates = sc.next();
        String endCoordinates = sc.next();

        if (startCoordinates == null || endCoordinates == null) {
            throw new IllegalArgumentException("Wrong input");
        }

        String[] coordinateArray1 = {String.valueOf(startCoordinates.charAt(0)), startCoordinates.substring(1)};
        String[] coordinateArray2 = {String.valueOf(endCoordinates.charAt(0)), endCoordinates.substring(1)};

        try {
            int startCoordinate1 = convertLetterCoordinateToInteger(coordinateArray1[0].toUpperCase());
            int startCoordinate2 = Integer.parseInt(coordinateArray1[1]) - 1;
            int endCoordinate1 = convertLetterCoordinateToInteger(coordinateArray2[0].toUpperCase());
            int endCoordinate2 = Integer.parseInt(coordinateArray2[1]) - 1;

            if (startCoordinate1 > endCoordinate1) {
                int intermediary = startCoordinate1;
                startCoordinate1 = endCoordinate1;
                endCoordinate1 = intermediary;
            }

            if (startCoordinate2 > endCoordinate2) {
                int intermediary = startCoordinate2;
                startCoordinate2 = endCoordinate2;
                endCoordinate2 = intermediary;
            }

            return new int[][]{{startCoordinate1, startCoordinate2}, {endCoordinate1, endCoordinate2}};
        } catch (IllegalArgumentException e) {
            return new int[][]{{-1, -1}, {-1, -1}};
        }
    }

    static int convertLetterCoordinateToInteger(String coordinate) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return alphabet.indexOf(coordinate);
    }

    static int[] getHitCoordinates() {
        String coordinatesAsString = sc.next();

        try {
            int coordinate1 = convertLetterCoordinateToInteger(String.valueOf(coordinatesAsString.charAt(0)).toUpperCase());
            int coordinate2 = Integer.parseInt(coordinatesAsString.substring(1)) - 1;
            return new int[]{coordinate1, coordinate2};
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument provided");
        }

        return new int[]{-1, -1};
    }

    static int[][] calculateOccupiedCells(int[] startCoordinates, int[] endCoordinates, int numOfCells) {
        final int NUMBER_OF_COORDINATES = 2;
        int[][] occupiedCells = new int[numOfCells][NUMBER_OF_COORDINATES];
        int count = 0;

        if ((endCoordinates[1] - startCoordinates[1]) > 0) {
            int start = startCoordinates[1];
            while (start <= endCoordinates[1]) {
                occupiedCells[count] = new int[]{startCoordinates[0], start};
                if (count < numOfCells - 1) count++;
                start++;
            }
        }
        if ((endCoordinates[0] - startCoordinates[0]) > 0) {
            int start = startCoordinates[0];
            while (start <= endCoordinates[0]) {
                occupiedCells[count] = new int[]{start, startCoordinates[1]};
                if (count < numOfCells - 1) count++;
                start++;
            }
        }
        return occupiedCells;
    }

    static void checkInitialCoordinatesValidity(String name, int numOfCells, int[] startCoordinates, int[] endCoordinates, BattleField field, int[][] occupiedCells) throws WrongCoordinatesException {
        int fieldSize = field.getSize();

        if (Math.abs(startCoordinates[0] - endCoordinates[0]) != numOfCells - 1 &&
                Math.abs(startCoordinates[1] - endCoordinates[1]) != numOfCells - 1) {
            throw new WrongCoordinatesException("Error! Wrong length of the " + name + "! Try again:");
        }

        if (Math.abs(startCoordinates[0] - endCoordinates[0]) != 0 &&
                Math.abs(startCoordinates[1] - endCoordinates[1]) != 0) {
            throw new WrongCoordinatesException("Error! Wrong ship location! Try again:");
        }

        if (startCoordinates[0] < 0 || startCoordinates[1] < 0 || endCoordinates[0] < 0
                || endCoordinates[1] < 0 || startCoordinates[0] > fieldSize || startCoordinates[1] > fieldSize
                || endCoordinates[0] > fieldSize || endCoordinates[1] > fieldSize) {
            throw new WrongCoordinatesException("Error! Coordinates out of bounds!");
        }

        if (isShipCollided(occupiedCells, field, fieldSize)) {
            throw new WrongCoordinatesException("Error! You placed it too close to another one. Try again:");
        }

    }

    static boolean isShipCollided(int[][] occupiedCells, BattleField field, int fieldSize) {
        /* check each occupied cell for correct positions against already placed ships */
        for (int[] cell: occupiedCells) {
            if ("O".equals(field.getCellData(cell[0], cell[1]))) {
                return true;
            }

            if (cell[0] - 1 >= 0 && "O".equals(field.getCellData(cell[0] - 1, cell[1]))) {
                return true;
            }

            if (cell[0] - 1 >= 0 && cell[1] + 1 < fieldSize && "O".equals(field.getCellData(cell[0] - 1, cell[1] + 1))) {
                return true;
            }

            if (cell[1] + 1 < fieldSize && "O".equals(field.getCellData(cell[0], cell[1] + 1))) {
                return true;
            }

            if (cell[0] + 1 < fieldSize && cell[1] + 1 < fieldSize && "O".equals(field.getCellData(cell[0] + 1, cell[1] + 1))) {
                return true;
            }

            if (cell[0] + 1 < fieldSize && "O".equals(field.getCellData(cell[0] + 1, cell[1]))) {
                return true;
            }

            if (cell[0] + 1 < fieldSize && cell[1] - 1 >= 0 && "O".equals(field.getCellData(cell[0] + 1, cell[1] - 1))) {
                return true;
            }

            if (cell[1] - 1 >= 0 && "O".equals(field.getCellData(cell[0], cell[1] - 1))) {
                return true;
            }

            if (cell[0] - 1 >= 0 && cell[1] - 1 >= 0 && "O".equals(field.getCellData(cell[0] - 1, cell[1] - 1))) {
                return true;
            }
        }
        return false;
    }

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static String[] createLetterCoordinateArray(int size) {
        String[] letterCoordinateArray = new String[size];

        char start = 'A';
        letterCoordinateArray[0] = String.valueOf(start);

        for (int i = 1; i < size; i++) {
            letterCoordinateArray[i] = String.valueOf((char) (start + i));
        }

        return letterCoordinateArray;
    }

    static void printField(BattleField.FIELD_TYPES type, Player player) {
        // create letter coordinate array
        String[] letterCoordinateArray = Helpers.createLetterCoordinateArray(player.getBattleField().getSize());

        // print header
        System.out.print("  ");
        for (int i = 1; i <= player.getBattleField().getSize(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        String[][] field = type == BattleField.FIELD_TYPES.PRIVATE_FIELD ? player.getBattleField().getPrivateField() : player.getBattleField().getCurrentField();

        // print field and append letter coordinate in front
        for (int i = 0; i < player.getBattleField().getSize(); i++) {
            String[] row = field[i];
            String letterCoordinate = letterCoordinateArray[i];
            System.out.printf("%s %s", letterCoordinate, String.join(" ", row));
            System.out.println();
        }
    }

    static void printBothFields(Player activePlayer, Player inactivePlayer) {
        printField(BattleField.FIELD_TYPES.PUBLIC_FIELD, inactivePlayer);
        System.out.println("------------------");
        printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, activePlayer);
    }

    static void passActionToAnotherPlayer() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Press Enter and pass the move to another player: ");
        sc.nextLine();
        clearConsole();
    }
}
