package battleship;

public class Player {
    // FIELDS
    private final BattleField battleField;

    // GETTERS
    public BattleField getBattleField() {
        return battleField;
    }

    // CONSTRUCTOR
    public Player() {
        this.battleField = new BattleField();
    }

    private void placeShips()  {
        Ship aircraftCarrier = createShip(App.SHIP_TYPES.AIRCRAFT_CARRIER, battleField);
        battleField.placeShip(aircraftCarrier);
        Helpers.printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, this);
        Ship battleShip = createShip(App.SHIP_TYPES.BATTLESHIP, battleField);
        battleField.placeShip(battleShip);
        Helpers.printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, this);
        Ship submarine = createShip(App.SHIP_TYPES.SUBMARINE, battleField);
        battleField.placeShip(submarine);
        Helpers.printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, this);
        Ship cruiser = createShip(App.SHIP_TYPES.CRUISER, battleField);
        battleField.placeShip(cruiser);
        Helpers.printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, this);
        Ship destroyer = createShip(App.SHIP_TYPES.DESTROYER, battleField);
        battleField.placeShip(destroyer);
        Helpers.printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, this);
    }

    private Ship createShip(App.SHIP_TYPES type, BattleField field) {
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
                int[][] coordinates = Helpers.getShipInitialCoordinates();
                int[][] occupiedCells = Helpers.calculateOccupiedCells(coordinates[0], coordinates[1], numberOfCells);
                Helpers.checkInitialCoordinatesValidity(name, numberOfCells, coordinates[0], coordinates[1], field, occupiedCells);
                return new Ship(name, numberOfCells, coordinates[0], coordinates[1], occupiedCells);
            } catch (WrongCoordinatesException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    void hit(BattleField field, Player inactivePlayer) {
        int[] coordinates = Helpers.getHitCoordinates();
        while (coordinates[0] < 0 || coordinates[1] < 0 || coordinates[0] > field.getSize() - 1
                || coordinates[1] > field.getSize() - 1) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            coordinates = Helpers.getHitCoordinates();
        }
        if (field.isHit(coordinates)) {
            field.updateFields(coordinates, "X");
            Helpers.printBothFields(this, inactivePlayer);
            System.out.println();
            // find a ship
            Ship foundShip = field.findShipAtCoordinates(coordinates);
            // UPDATE SHIP COORDINATES TO REFLECT DAMAGE
            foundShip.takeHit(coordinates);
            // CHECK IF SHIP IS SANK
            if (foundShip.isShipSank()) {
                System.out.println("You sank a ship! Specify a new target:\n");
            } else {
                System.out.println("You hit a ship! Try again:\n");
            }
            // CHECK IF GAME IS OVER
            if (field.isGameOver()) {
                App.setGameState(App.GAME_STATE.FINISHED);
            }
        } else {
            field.updateFields(coordinates, "M");
            Helpers.printBothFields(this, inactivePlayer);
            System.out.println("You missed! Try again:\n");
        }
    }

    void setupField() {
        Helpers.printField(BattleField.FIELD_TYPES.PRIVATE_FIELD, this);
        System.out.println();
        placeShips();
    }
}

