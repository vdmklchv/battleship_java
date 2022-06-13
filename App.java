package battleship;

import java.util.Scanner;

class App {

    enum SHIP_TYPES {
        AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER
    }

    void start() {
        BattleField bf1 = new BattleField();
        bf1.printCurrentField();
        System.out.println();
        placeShips(bf1);
        System.out.println("The game starts");
        bf1.printCurrentField();
        hit(bf1);

    }

    private void placeShips(BattleField field)  {
        Ship aircraftCarrier = createShip(SHIP_TYPES.AIRCRAFT_CARRIER, field);
        field.placeShip(aircraftCarrier);
        field.printCurrentField();
        Ship battleShip = createShip(SHIP_TYPES.BATTLESHIP, field);
        field.placeShip(battleShip);
        field.printCurrentField();
        Ship submarine = createShip(SHIP_TYPES.SUBMARINE, field);
        field.placeShip(submarine);
        field.printCurrentField();
        Ship cruiser = createShip(SHIP_TYPES.CRUISER, field);
        field.placeShip(cruiser);
        field.printCurrentField();
        Ship destroyer = createShip(SHIP_TYPES.DESTROYER, field);
        field.placeShip(destroyer);
        field.printCurrentField();
    }

    private void checkInitialCoordinatesValidity(String name, int numOfCells, int[] startCoordinates, int[] endCoordinates, BattleField field, int[][] occupiedCells) throws WrongCoordinatesException {
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

    private boolean isShipCollided(int[][] occupiedCells, BattleField field, int fieldSize) {
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

    private int convertLetterCoordinateToInteger(String coordinate) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return alphabet.indexOf(coordinate);
    }

    private int[][] getShipInitialCoordinates() {
        Scanner sc = new Scanner(System.in);
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

    private int[] getHitCoordinates() {
        Scanner sc = new Scanner(System.in);
        String coordinatesAsString = sc.next();
        try {
            int coordinate1 = convertLetterCoordinateToInteger(String.valueOf(coordinatesAsString.charAt(0)));
            int coordinate2 = Integer.parseInt(coordinatesAsString.substring(1)) - 1;
            return new int[]{coordinate1, coordinate2};
        } catch (IllegalArgumentException e) {
            System.out.println("Wrong argument provided");
        }

        return new int[]{-1, -1};
    }

    private void hit(BattleField field) {
        System.out.println("Take a shot!");
        int[] coordinates = getHitCoordinates();
        while (coordinates[0] < 0 || coordinates[1] < 0 || coordinates[0] > field.getSize() - 1
        || coordinates[1] > field.getSize() - 1) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            coordinates = getHitCoordinates();
        }
        if (field.isHit(coordinates)) {
            field.updateField(coordinates, "X");
            System.out.println("You hit a ship!");
        } else {
            field.updateField(coordinates, "M");
            System.out.println("You missed!");
        }
        field.printCurrentField();
    }

    private Ship createShip(SHIP_TYPES type, BattleField field) {
        String name;
        int numberOfCells;

        switch (type) {
            case AIRCRAFT_CARRIER:
                name = "Aircraft Carrier";
                numberOfCells = 5;
                break;
            case BATTLESHIP:
                name = "Battleship";
                numberOfCells = 4;
                break;
            case SUBMARINE:
                name = "Submarine";
                numberOfCells = 3;
                break;
            case CRUISER:
                name = "Cruiser";
                numberOfCells = 3;
                break;
            case DESTROYER:
                name = "Destroyer";
                numberOfCells = 2;
                break;
            default:
                name = "Unknown";
                numberOfCells = 0;
                break;
        }

        System.out.printf("Enter the coordinates of the %s (%d cells):", name, numberOfCells);
        System.out.println();

        while (true) {
            try {
                int[][] coordinates = getShipInitialCoordinates();
                int[][] occupiedCells = calculateOccupiedCells(coordinates[0], coordinates[1], numberOfCells);
                checkInitialCoordinatesValidity(name, numberOfCells, coordinates[0], coordinates[1], field, occupiedCells);
                return new Ship(name, numberOfCells, coordinates[0], coordinates[1], occupiedCells);
            } catch (WrongCoordinatesException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int[][] calculateOccupiedCells(int[] startCoordinates, int[] endCoordinates, int numOfCells) {
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
}
