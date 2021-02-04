package states;

import entities.creatures.Player;
import entities.items.Bomb;
import entities.items.BombCollectable;
import okBoomer.Handler;
import utils.ItemTimer;
import worlds.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GameState extends State {

    // World
    private static World world;
    private static int maxWorldX;
    private static int maxWorldY;
    private static int minWorldX = -1;
    private static int minWorldY = -1;

    // 2D Array to keep track of entities if player touch bomb | for game logic (process damage, etc)
    private static int[][] board;

    // Players
    private Player player1;
    private Player player2;

    // Bombs
    private Bomb bomb; // Explore using arrayList to store list of placed bombs
    private BombCollectable bombPart; // bomb item for player to collect bomb parts to fill up bomb pouch
    private static ArrayList<BombCollectable> bombList; // get the list of currently spawned bomb items

    private ItemTimer timer = new ItemTimer();

    // Constructors
    public GameState(Handler handler){
        super(handler); // This is to look at the same game object
        world = new World("src/res/worlds/world1.txt");
        maxWorldX = (world.getWidth()-1);
        maxWorldY = (world.getWidth()-1);

        board = new int[world.getWidth()][world.getHeight()];

        /* 2D array Board usage:
            0 = empty, no entity occupying
            1 = player1 occupying
            2 = player2 occupying
            3 = bomb (planted) occupying
            4 = bomb (collectable) occupying
            */
        for (int[] columns: board){
            Arrays.fill(columns, 0); // Fills all element in board with 0
        }

        player1 = new Player(handler, 0,0); // spawn player 1 at the start
        player2 = new Player(handler, 576, 576); // spawn player 2 at the end
        bomb = new Bomb(handler, 256,256); // spawn bomb (planted) in middle

        // initialise bombPart to get bombList array, it's not added into the arrayList, so it will not be counted in the map
        bombPart = new BombCollectable(handler, 0, 0);
        bombList = bombPart.getBombsSpawnedList();

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
        spawnItem(0); // only spawn bombs as it is the only item in the game

    }

    @Override
    public void render(Graphics g) {
        // Render is layered approach (whatever runs first will be base layer)
        world.render(g);

        bomb.render(g);
        player1.render(g);
        player2.render(g);

        // render the current bombs stored in the object
        for(int i = 0; i < bombList.size(); i++){
            bombList.get(i).render(g);
        }
    }

    public static boolean canPlayerMove(int pid, int prevX, int prevY, int newX, int newY, Player targetPlayer){

        prevX = prevX/64;
        prevY = prevY/64;
        newX = newX/64;
        newY = newY/64;

        // if newX and newY is more than world edges, do not let player move
        if((newY > (maxWorldY)) || newY <= minWorldY){
            return false;
        }
        else if ((newX > (maxWorldX)) || newX <= minWorldX){
            return false;
        }
        else{

            // get the tileid of the next tile the player is stepping on
            int tid = getTileId(newX, newY);
            /* 2D array Board usage:
            0 = empty, no entity occupying
            1 = player1 occupying
            2 = player2 occupying
            3 = bomb (planted) occupying
            4 = bomb (collectable) occupying
            */
            switch (tid){

                // if next tile is empty
                case 0:
                    updateBoard(pid, prevX, prevY, newX, newY);
                    return true; // let player move

                // if next tile is bomb
                case 3:
                    updateBoard(pid, prevX, prevY, newX, newY);

                    // Remove bombPart from ArrayList bombList so that it does not render


                    bombPlayer(targetPlayer);
                    System.out.println("bomb");
                    return true;

                // once player picks up bomb <nich0las ch0ng>
                case 4:
                    updateBoard(pid, prevX, prevY, newX, newY);

                    /* Remove bombCollectable from bombList */
                    for (BombCollectable bombCol : bombList){
                        if (bombCol.getX() == newX*64 && bombCol.getY() == newY*64){
                            bombList.remove(bombCol);
                        }
                        break;
                    }

                    collectBombPart(targetPlayer);
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

        // get tid of the previous tile
        int tidPrev = getTileId(prevX,prevY);

        // player 1
        if (pid == 0){

            // check if the previous tile is a bomb or empty
            if (tidPrev == 3){
                board[prevX][prevY] = 3; // Step 1
            }
            else{
                board[prevX][prevY] = 0; // Step 1
            }

            board[newX][newY] = 1; // Step 2

            System.out.printf("Board: P1: PrevXY: [%d][%d] = %d%n" +
                            "Board: P1: CurrXY: [%d][%d] = %d%n%n",
                    prevX, prevY, board[prevX][prevY], newX, newY, board[newX][newY]);
        }
        // player 2
        else if (pid == 1){

            if (tidPrev == 3){
                board[prevX][prevY] = 3; // Step 1
            }
            else{
                board[prevX][prevY] = 0; // Step 1
            }

            board[newX][newY] = 2; // Step 2

            System.out.printf("Board: P2: PrevXY: [%d][%d] = %d%n" +
                            "Board: P2: CurrXY: [%d][%d] = %d%n%n",
                    prevX, prevY, board[prevX][prevY], newX, newY, board[newX][newY]);

        }

    }

    /* Method to spawn items */
    public void spawnItem(int itemid){

        // if there are more than 3 bomb parts on the map, do not spawn anymore.
        if(bombList.size()<3){
            // check if countdown timer is running, if it is not, run it
            if(!timer.hasRunStarted()){
                timer.startTimer();
            }

            else {
                // if item ready to spawn
                if(timer.readyToSpawn()){
                /* itemid usage:
                    0 = bomb (collectable)
                    1 = shield ???
                 */
                    switch (itemid){

                        // code to spawn bomb (collectable)
                        case 0:
                            while(true){
                                // generate random x and y from 0 to 9
                                Random rand = new Random();
                                int randx = rand.nextInt(10);
                                int randy = rand.nextInt(10);

                                // check if anything is occupying this tile
                                // if nothing is occupying it, spawn a bomb part!
                                int tid = getTileId(randx, randy);
                                if(tid == 0){
                                    /* spawn bomb */
                                    // add new bomb item spawned into our bomb array
                                    bombList.add(new BombCollectable(handler,randx*64, randy*64));
                                    board[randx][randy] = 4; // update board array
                                    timer.setRdyToSpawn(false); // reset the item spawn rate
                                    break;
                                }

                            }

                    }
                }
            }
        }

    }
    /* Method to bomb player */
    public static void bombPlayer(Player targetPlayer){
        targetPlayer.setHealth(targetPlayer.getHealth() - 1);
    }

    /* Method to plant the collected bomb */
    public static void plantBomb(Player targetPlayer){
        targetPlayer.setBomb(targetPlayer.getBomb() - 1);
    }

    public static void collectBombPart(Player targetPlayer){
        targetPlayer.addBombPart();
    }

    // Getters and Setters
    public int[][] getBoard() {
        return board;
    }

    public static int getTileId(int newX, int newY){

        int tid = board[newX][newY];
        return tid;

    }

    public static void setBombTileId(int newX, int newY){
        int tid = 3;
        board[newX][newY] = tid;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public static World getWorld(){ return world; }

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

    /*
    Maybe for Inventory Display
    @Override
    public int getP1Bomb(){
        return player1.getBomb();
    }
    @Override
    public int getP2Bomb(){
        return player2.getBomb();
    }
     */
}
