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
    private static World world;
    private static int maxWorldX;
    private static int maxWorldY;
    private static int minWorldX = -1;
    private static int minWorldY = -1;

    // 2D Array to keep track of entities if player touch bomb | for game logic (process damage, etc)
    private static int[][] board;
    private boolean checkIfCollide1 = false;
    private boolean checkIfCollide2 = false;

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
        maxWorldX = (world.getWidth()-1) * 64;
        maxWorldY = (world.getWidth()-1) * 64;

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

        // Set static variables for collision logic in Player class getInput()

        // Update board with player(s) and bomb coordinate
        board[player1.getX()/64][player1.getY()/64] = 1; // set player 1 in board [0][0] = 0,0
        board[player2.getX()/64][player2.getY()/64] = 2; // set player 2 in board [9][9] = 576,576
        board[bomb.getX()/64][bomb.getY()/64] = 3; // set bomb in board[4][4] = 256,256

    }


    // Other methods
    @Override
    public void tick() {
        // Insert logic to update all variables related to Game
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

    public static boolean canPlayerMove(int pid, int prevX, int prevY, int newX, int newY, Player targetPlayer){

        // if newX and newY is more than world edges, do not let player move
        if((newY > (maxWorldY)) || newY <= minWorldY){
            return false;
        }
        else if ((newX > (maxWorldX)) || newX <= minWorldX){
            return false;
        }
        else{

            prevX = prevX/64;
            prevY = prevY/64;
            newX = newX/64;
            newY = newY/64;

            // get the tileid of the next tile the player is stepping on
            int tid = getTileId(newX, newY);
            /* 2D array Board usage:
            0 = empty, no entity occupying
            1 = player1 occupying
            2 = player2 occupying
            3 = bomb occupying
            */
            switch (tid){

                // if next tile is empty
                case 0:
                    updateBoard(pid, prevX, prevY, newX, newY);

                    /* Testing section for bomb | this will make unlimited bomb in the center to test bomb damage */
                    board[prevX][prevY] = 3;

                    return true; // let player move

                // if next tile is bomb
                case 3:
                    updateBoard(pid, prevX, prevY, newX, newY);
                    bombPlayer(targetPlayer);
                    System.out.println("bomb");
                    return true;

                // if next tile is player
                default:
                    return false;

            }

        }

    }

    /* Method to update board 2d array and call updateEntity method inside */
    public static void updateBoard(int pid, int prevX, int prevY, int newX, int newY){

        // the coordinates are passed into this method as pixels.
        // divide them by 64 to get the coordinates in rows and cols

        /*  1. Target index(s) of 2D board array where player resides, make it empty
            2. Target index(x) of 2D board array based on player's current X, Y and update it
        */

        // player 1
        if (pid == 0){
            board[prevX][prevY] = 0; // Step 1
            board[newX][newY] = 1; // Step 2

            System.out.printf("Board: P1: PrevXY: [%d][%d] = %d%n" +
                            "Board: P1: CurrXY: [%d][%d] = %d%n%n",
                    prevX, prevY, board[prevX][prevY], newX, newY, board[newX][newY]);
        }
        // player 2
        else if (pid == 1){

            board[prevX][prevY] = 0; // Step 1
            board[newX][newY] = 2; // Step 2

            System.out.printf("Board: P2: PrevXY: [%d][%d] = %d%n" +
                            "Board: P2: CurrXY: [%d][%d] = %d%n%n",
                    prevX, prevY, board[prevX][prevY], newX, newY, board[newX][newY]);

        }

    }

    /* Method to bomb player */
    public static void bombPlayer(Player targetPlayer){
        targetPlayer.setHealth(targetPlayer.getHealth() - 1);
    }


    // Getters and Setters

    public int[][] getBoard() {
        return board;
    }

    public static int getTileId(int newX, int newY){

        int tid = board[newX][newY];
        return tid;

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public static World getWorld(){ return world; }

    public boolean checkIfCollide1() {
        return checkIfCollide1;
    }

    public void checkIfCollide1(boolean checkIfCollide1) {
        this.checkIfCollide1 = checkIfCollide1;
    }

    public boolean checkIfCollide2() {
        return checkIfCollide2;
    }

    public void checkIfCollide2(boolean checkIfCollide2) {
        this.checkIfCollide2 = checkIfCollide2;
    }

    public Bomb getBomb() {
        return bomb;
    }

    // Update Scoreboard Methods
    @Override
    public int getP1Health(){
        return player1.getHealth();
    }

    @Override
    public int getP2Health(){
        return player2.getHealth();
    }
}
