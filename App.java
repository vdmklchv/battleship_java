package battleship;

class App {
    // ENUMS
    enum GAME_STATE {
        PREPARATION, IN_PROGRESS, FINISHED
    }

    enum ACTIVE_PLAYER {
        PLAYER_1, PLAYER_2
    }

    enum SHIP_TYPES {
        AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER
    }


    // STATIC FIELDS
    static GAME_STATE currentGameState;
    static ACTIVE_PLAYER currentActivePlayer;


    // STATIC SETTERS
    static void setGameState(GAME_STATE gameState) {
        currentGameState = gameState;
    }

    static void setActivePlayer(ACTIVE_PLAYER activePlayer) {
        currentActivePlayer = activePlayer;
    }

    void start() {
        // Initialize players
        setGameState(GAME_STATE.PREPARATION);
        Player player_1 = new Player();
        Player player_2 = new Player();
        // Prepare game fields
        System.out.println("Player 1, place your ships to the game field\n");
        player_1.setupField();

        Helpers.passActionToAnotherPlayer();

        System.out.println("Player 2, place your ships to the game field\n");
        player_2.setupField();

        Helpers.passActionToAnotherPlayer();

        // GAME LOGIC
        setGameState(GAME_STATE.IN_PROGRESS);
        setActivePlayer(ACTIVE_PLAYER.PLAYER_1);

        System.out.println("The game starts!\n");

        while (currentGameState == GAME_STATE.IN_PROGRESS) {
            advanceRound(player_1, player_2);
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private void advanceRound(Player player1, Player player2) {

        String player;

        if(currentActivePlayer == ACTIVE_PLAYER.PLAYER_1) {
            player = "Player 1";
        } else {
            player = "Player 2";
        }

        System.out.println(player + ", it's your turn:");

        if (currentActivePlayer == ACTIVE_PLAYER.PLAYER_1) {
            Helpers.printBothFields(player1, player2);
            player1.hit(player2.getBattleField(), player2);
            setActivePlayer(ACTIVE_PLAYER.PLAYER_2);
        } else {
            Helpers.printBothFields(player2, player1);
            player2.hit(player1.getBattleField(), player1);
            setActivePlayer(ACTIVE_PLAYER.PLAYER_1);
        }

        if (currentGameState == GAME_STATE.FINISHED) {
            return;
        }

        Helpers.passActionToAnotherPlayer();
    }
}



