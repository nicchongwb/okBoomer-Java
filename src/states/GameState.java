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
    //public static boolean getBombed = false;

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
            5 = bomb (planted) + player 1 occupying (bomb is planted by p1)
            6 = bomb (planted) + player 2 occupying (bomb is planted by p2)
        */
        for (int[] columns: board){
            Arrays.fill(columns, 0); // Fills all element in board with 0
        }

        player1 = new Player(handler, 0,0); // spawn player 1 at the start
        player2 = new Player(handler, 576, 576); // spawn player 2 at the end
        bomb = new Bomb(handler, 128,256); // spawn bomb (planted) in middle

        // initialise bombPart to get bombList array, it's not added into the arrayList, so it will not be counted in the map
        bombPart = new BombCollectable(handler, 0, 0);
        bombList = bombPart.getBombsSpawnedList();

        // Set static variables for collision logic in Player class getInput()

        // Update board with player(s) and bomb coordinate
        board[player1.getY()/64][player1.getX()/64] = 1; // set player 1 in board [0][0] = 0,0
        board[player2.getY()/64][player2.getX()/64] = 2; // set player 2 in board [9][9] = 576,576
        board[bomb.getY()/64][bomb.getX()/64] = 3; // set bomb in board[4][4] = 256,256

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

        // render the current bombs stored in the object
        for(int i = 0; i < bombList.size(); i++){
            bombList.get(i).render(g);
        }
        player1.render(g);
        player2.render(g);

    }

    /* Method to determine if player can move */
    public static boolean canPlayerMove(int pid, int prevX, int prevY, int newX, int newY, Player targetPlayer){

        /* Logic flow:
                1. Check if TID new is collidable or non-collidable tile.
                2. If it is a non-collidable tile, update actions accordingly, and check previous tile
                2.1 If previous tile is 0, 3, 4, then let player move and update board accordingly
                2.2 If previous tile is 5, 6, then also let player move and update board accordingly (change prev tile to bomb)
                3. If it is a collidable tile or world border, do not let player move.
        */

        /* 2D array Board usage:
            0 = empty, no entity occupying
            1 = player1 occupying
            2 = player2 occupying
            3 = bomb (planted) occupying
            4 = bomb (collectable) occupying
            5 = bomb (planted) + player 1 occupying (bomb is planted by p1)
            6 = bomb (planted) + player 2 occupying (bomb is planted by p2)
        */

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

            // sometimes, when both players move TOO fast (press keyboard too fast), there may
            // be latency issues with the coordinates. This piece of if-else code is to
            // prevent the coordinates from updating wrongly ;)
            if(newX - prevX == 0){
                if(newY - prevY == 1 || newY - prevY == -1);
                else{
                    return false;
                }
            } else if(newY - prevY == 0){
                if(newX - prevX == 1 || newX - prevX == -1);
                else{
                    return false;
                }
            } else {
                return false;
            }

            // get the tile id of the next tile the player is going to step on
            // and get the tild id of the previous tile the player was stepping on
            int tidPrev = getTileId(prevX,prevY);
            int tidNew = getTileId(newX, newY);

            switch (tidNew){

                // if next tile is empty [non-collidable tile]
                case 0:
                    checkPrevandUpdateBoard(tidPrev, pid, prevX, prevY, newX, newY);
                    return true; // let player move

                // if next tile is bomb [non-collidable tile]
                case 3:
                    checkPrevandUpdateBoard(tidPrev, pid, prevX, prevY, newX, newY);

                    // Remove bombPart from ArrayList bombList so that it does not render
                    bombPlayer(targetPlayer);
                    targetPlayer.setBombed();
                    System.out.println("bomb");
                    return true;

                // once player picks up bomb [non-collidable tile]
                case 4:

                    /* Remove bombCollectable from bombList */
                    for (BombCollectable bombCol : bombList){
                        if (bombCol.getX() == newX*64 && bombCol.getY() == newY*64) {
                            bombList.remove(bombCol);
                            break;
                        }
                    }

                    checkPrevandUpdateBoard(tidPrev, pid, prevX, prevY, newX, newY);
                    collectBombPart(targetPlayer);
                    return true;

                // if next tile is player + bomb [collidable tile]
                // if next tile is player (and any other collidable tiles)
                default:
                    return false;

            }

        }

    }

    /* Method to check if the previous tile is collidable so that we can decide how to update the 2d board array*/
    public static void checkPrevandUpdateBoard(int tidPrev, int pid, int prevX, int prevY, int newX, int newY){

        /*  1. Target index(s) of 2D board array where player resides, make it empty
            2. Target index(x) of 2D board array based on player's current X, Y and update it
        */

        switch (tidPrev){

            // for cases 5 and 6, change previous tile to 3 (planted bomb).
            case 5:
            case 6:
                board[prevY][prevX] = 3; // Step 1
                board[newY][newX] = pid+1; // Step 2

                System.out.printf("Board: P%d: PrevXY: [%d][%d] = %d%n" +
                                "Board: P%d: CurrXY: [%d][%d] = %d%n%n",
                        pid+1, prevY, prevX, board[prevY][prevX], pid+1, newY, newX, board[newY][newX]);
                break;

            // for cases 0 to 4, change previous tile to 0 (empty tile)
            default:
                board[prevY][prevX] = 0; // Step 1
                board[newY][newX] = pid+1; // Step 2

                System.out.printf("Board: P%d: PrevXY: [%d][%d] = %d%n" +
                                "Board: P%d: CurrXY: [%d][%d] = %d%n%n",
                        pid+1, prevY, prevX, board[prevY][prevX], pid+1, newY, newX, board[newY][newX]);

                break;
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
                                    board[randy][randx] = 4; // update board array
                                    timer.setRdyToSpawn(false); // reset the item spawn rate
                                    timer.sethasRunStarted(false); // reset hasStarted variable
                                    break;
                                }

                            }

                    }
                }
            }
        }

    }

    /* Method to bomb player */
    public static void bombPlayer(Player targetPlayer){ targetPlayer.setHealth(targetPlayer.getHealth() - 1); }

    /* Method to plant the collected bomb */
    public static void plantBomb(Player targetPlayer){ targetPlayer.setBomb(targetPlayer.getBomb() - 1); }

    public static void collectBombPart(Player targetPlayer){ targetPlayer.addBombPart(); }


    // Getters and Setters
    public int[][] getBoard() {
        return board;
    }

    public static int getTileId(int newX, int newY){

        int tid = board[newY][newX];
        return tid;

    }

    public static void setTileId(int tid, int newX, int newY){
        board[newY][newX] = tid;
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

    @Override
    // Update Inventory Methods
    public int getP1BombHeld(){
        return player1.getBomb();
    }

    @Override
    public int getP2BombHeld(){
        return player2.getBomb();
    }

    @Override
    public int getP1BombPart(){
        return player1.getBombCollectable();
    }

    @Override
    public int getP2BombPart(){
        return player2.getBombCollectable();
    }

}
