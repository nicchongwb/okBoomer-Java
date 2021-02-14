package states;

import gfx.AudioPlayer;
import interfaces.Board;
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


public class GameState extends State implements Board{

    // World
    private static World world;
    private static int maxWorldX;
    private static int maxWorldY;
    private static int minWorldX = -1;
    private static int minWorldY = -1;
    //public static boolean getBombed = false;

    // 2D Array to keep track of entities if player touch bomb | for game logic (process damage, etc)
    public static int[][] board;

    // Players
    private Player player1;
    private Player player2;

    // Bombs
    private Bomb bomb; // Explore using arrayList to store list of placed bombs
    private BombCollectable bombPart; // bomb item for player to collect bomb parts to fill up bomb pouch
    public static ArrayList<BombCollectable> bombList; // get the list of currently spawned bomb items
    public static ArrayList<Bomb> plantedBombList; // Arraylist to keep track of the number of planted bombs

    private ItemTimer timer = new ItemTimer();
    private static boolean alrSetBomb = false;

    // variable for playing sound

    private static AudioPlayer bombsound;

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

        plantedBombList = bomb.getPlantedBombList();
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
        // render planted bomb
        //if (alrSetBomb){
            // render the current bombs stored in the object
            for(int i = 0; i < plantedBombList.size(); i++){
                plantedBombList.get(i).render(g);
            }
        //}
        player1.render(g);
        player2.render(g);

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
    public static void bombPlayer(Player targetPlayer){
        bombsound = new AudioPlayer("/res/audio/bomb2.wav"); //sound effect for bombing player
        bombsound.playonce();
        targetPlayer.setHealth(targetPlayer.getHealth() - 1);
    }

    /* Method to plant the collected bomb */
    public static void plantBomb(Player targetPlayer, Bomb bomb){
        targetPlayer.setBomb(targetPlayer.getBomb() - 1);
        // remove oldest bomb and add the latest bomb into the array when total bomb planted on the map exceed 8
        if(plantedBombList.size() == 2){
            setTileId(0, plantedBombList.get(0).getX()/64, plantedBombList.get(0).getY()/64);
            plantedBombList.remove(0);
        }
        plantedBombList.add(bomb); // add bomb object to ArrayList
        alrSetBomb = true;
        System.out.println("Planted bomb arraylist size: " + plantedBombList.size());
    }

    public static void collectBombPart(Player targetPlayer){ targetPlayer.addBombPart(); }


    // Getters and Setters
    public static int getMaxWorldX() {
        return maxWorldX;
    }

    public static void setMaxWorldX(int maxWorldX) {
        GameState.maxWorldX = maxWorldX;
    }

    public static int getMaxWorldY() {
        return maxWorldY;
    }

    public static void setMaxWorldY(int maxWorldY) {
        GameState.maxWorldY = maxWorldY;
    }

    public static int getMinWorldX() {
        return minWorldX;
    }

    public static void setMinWorldX(int minWorldX) {
        GameState.minWorldX = minWorldX;
    }

    public static int getMinWorldY() {
        return minWorldY;
    }

    public static void setMinWorldY(int minWorldY) {
        GameState.minWorldY = minWorldY;
    }

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
