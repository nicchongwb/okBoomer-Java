package states;

import entities.creatures.Player;
import entities.items.Bomb;
import gfx.Assets;
import okBoomer.Game;
import tiles.Tile;
import worlds.World;

import java.awt.*;
import java.util.Arrays;

public class GameState extends State {

    // World
    private World world;

    // 2D Array to keep track of entities if player touch bomb | for game logic (process damage, etc)
    private int[][] board;

    // Players
    private Player player1;
    private Player player2;

    // Bombs
    private Bomb bomb; // Explore using arrayList to store list of placed bombs
    // Need an item for player to collect bomb parts to fill up bomb pouch


    // Constructors
    public GameState(Game game){
        super(game); // This is to look at the same game object
        world = new World("src/res/worlds/world1.txt");
        board = new int[world.getWidth()][world.getHeight()];

        /* 2D array Board usage:
            0 = empty, no entity occupying
            1 = player1 occupying
            2 = player2 occupying
            3 = bomb occupying
         */
        for (int[] columns: board){
            Arrays.fill(columns, 0); // Fills all element in board with 0
        }

        player1 = new Player(game, 0,0); // spawn player 1 at the start
        player2 = new Player(game, 576, 576); // spawn player 2 at the end
        bomb = new Bomb(game, 256,256); // spawn bomb in middle

        // Update board with player(s) and bomb coordinate
        board[player1.getX()/64][player1.getY()/64] = 1; // set player 1 in board [0][0] = 0,0
        board[player2.getX()/64][player2.getY()/64] = 2; // set player 2 in board [9][9] = 576,576
        board[bomb.getX()/64][bomb.getY()/64] = 3; // set bomb in board[4][4] = 256,256

    }


    // Other methods
    @Override
    public void tick() {
        // Insert logic to update all variables related to Game
        updateBoard();
        world.tick();
        player1.tick();
        player2.tick();
    }

    @Override
    public void render(Graphics g) {
        // Render is layered approach (whatever runs first will be base layer)
        world.render(g);

        bomb.render(g);
        player1.render(g);
        player2.render(g);

    }

    /* Method to update board 2d array and call updateEntity method inside */
    public void updateBoard(){
        // Update 2D Board array
        // Note that debug section print statements will churn out the first set of correct value and then subsequent
        // sets of duplicated/not update output. This is due to the way GameState.tick() is run. So just ignore the
        // Subsequent sets

        if (player1.getIfPressed1()){
            // Variables of Current/Previous -> X,Y divided by 64 | to help us access board indices
            int p1PrevX = player1.getPrevX()/64;
            int p1PrevY = player1.getPrevY()/64;
            int p1CurrX = player1.getX()/64;
            int p1CurrY = player1.getY()/64;
            // As long as current player's X,Y >= 0, proceed to update the 2D board array
            if (p1CurrX >= 0 | p1CurrX >= 0) {
                /*  1. Target index(s) of 2D board array where player resides, make it empty
                    2. Target index(x) of 2D board array based on player's current X, Y and update it
                */
                board[p1PrevX][p1PrevY] = 0; // Step 1
                board[p1CurrX][p1CurrY] = 1; // Step 2

                // Debug section
                System.out.printf("Graphics: P1: X: %d\tY: %d%n", player1.getX(), player1.getY());
                System.out.printf("Board: P1: PrevXY: [%d][%d] = %d%n" +
                                "Board: P1: CurrXY: [%d][%d] = %d%n%n",
                        p1PrevX, p1PrevY, board[p1PrevX][p1PrevY], p1CurrX, p1CurrY, board[p1CurrX][p1CurrY]);
            }
        }

        if (player2.getIfPressed2()){
            // Variables of Current/Previous -> X,Y divided by 64 | to help us access board indices
            int p2PrevX = player2.getPrevX()/64;
            int p2PrevY = player2.getPrevY()/64;
            int p2CurrX = player2.getX()/64;
            int p2CurrY = player2.getY()/64;
            // As long as current player's X,Y >= 0, proceed to update the 2D board array
            if (p2CurrX >= 0 | p2CurrX >= 0) {
                /*  1. Target index(s) of 2D board array where player resides, make it empty
                    2. Target index(x) of 2D board array based on player's current X, Y and update it
                */
                board[p2PrevX][p2PrevY] = 0; // Step 1
                board[p2CurrX][p2CurrY] = 2; // Step 2

                // Debug section
                System.out.printf("Graphics: P2: X: %d\tY: %d%n", player2.getX(), player2.getY());
                System.out.printf("Board: P2: PrevXY: [%d][%d] = %d%n" +
                                "Board: P2: CurrXY: [%d][%d] = %d%n%n",
                        p2PrevX, p2PrevY, board[p2PrevX][p2PrevY], p2CurrX, p2CurrY, board[p2CurrX][p2CurrY]);
            }
        }
    }

    /* Method to update entity (eg. player health after touching bomb based on 2d array */
    public void updateEntity(){

    }

    // Getters and Setters

    public int[][] getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

}
