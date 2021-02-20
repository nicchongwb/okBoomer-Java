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
    private static final int MAX_PLANTED_BOMB = 8;

    // Players
    private Player player1;
    private Player player2;

    // Bombs
    private Bomb bomb; // Explore using arrayList to store list of placed bombs
    private BombCollectable bombPart; // The bomb item for player to collect bomb parts to fill up bomb pouch
    public static ArrayList<BombCollectable> bombList; // To get the list of currently spawned bomb items
    public static ArrayList<Bomb> plantedBombList; // Arraylist to keep track of the number of planted bombs

    private ItemTimer timer = new ItemTimer();

    // Audio
    private static AudioPlayer bombsound;   // variable for playing sound
    private AudioPlayer gamemusic;

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
        bomb = new Bomb(handler, 0,0); // spawn bomb (planted) at the start
        plantedBombList = bomb.getPlantedBombList();

        // To initialise bombPart to get bombList array, it's not added into the arrayList, so it will not be counted in the map
        bombPart = new BombCollectable(handler, 0, 0);
        bombList = bombPart.getBombsSpawnedList();

        // Set static variables for collision logic in Player class getInput()

        // Update board with player(s) and bomb coordinate
        board[player1.getY()/64][player1.getX()/64] = 1; // To set player 1 in board [0][0] = 0,0
        board[player2.getY()/64][player2.getX()/64] = 2; // To set player 2 in board [9][9] = 576,576
    }


    // Other methods
    @Override
    public void tick() {
        // Insert logic to update all variables related to Game
        world.tick();
        player1.tick();
        player2.tick();
        spawnItem(0); // Only spawn bombs as it is the only item in the game

    }

    @Override
    public void render(Graphics g) {
        // Render is layered approach (whatever runs first will be base layer)
        world.render(g);

        bomb.render(g);

        // Render the current bombs stored in the object
        for(int i = 0; i < bombList.size(); i++){
            bombList.get(i).render(g);
        }
        // Render planted bomb
        for(int i = 0; i < plantedBombList.size(); i++){
            plantedBombList.get(i).render(g);
        }
        player1.render(g);
        player2.render(g);

    }


    /* Method to spawn items */
    public void spawnItem(int itemid){

        // If there are more than 3 bomb parts on the map, do not spawn anymore.
        if(bombList.size()<3){
            // Check if countdown timer is running, if it is not, run it
            if(!timer.hasRunStarted()){
                timer.startTimer();
            }

            else {
                // If item ready to spawn
                if(timer.readyToSpawn()){
                /* itemid usage:
                    0 = bomb (collectable)
                 */
                    switch (itemid){

                        // Code to spawn bomb (collectable)
                        case 0:
                            while(true){
                                // Generate random x and y from 0 to 9
                                Random rand = new Random();
                                int randx = rand.nextInt(10);
                                int randy = rand.nextInt(10);

                                // Check if anything is occupying this tile
                                // if nothing is occupying it, spawn a bomb part!
                                int tid = getTileId(randx, randy);
                                if(tid == 0){
                                    /* Spawn bomb */
                                    // Add new bomb item spawned into our bomb array
                                    bombList.add(new BombCollectable(handler,randx*64, randy*64));
                                    board[randy][randx] = 4; // Update board array
                                    timer.setRdyToSpawn(false); // Reset the item spawn rate
                                    timer.sethasRunStarted(false); // Reset hasStarted variable
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
        bombsound = new AudioPlayer("/res/audio/bomb2.wav"); // Sound effect for bombing player
        bombsound.playonce();
        removePlantedBomb(targetPlayer);
        targetPlayer.setHealth(targetPlayer.getHealth() - 1);
    }

    public static void removePlantedBomb(Player targetPlayer){
        for(int i = 0; i < plantedBombList.size(); i++){
            if (plantedBombList.get(i).getX() == targetPlayer.getNewX() && plantedBombList.get(i).getY() == targetPlayer.getNewY()){
                plantedBombList.remove(i);
            }
        }
        resetBombID();
    }

    // Reset bombID - in order to remove by index in the arraylist
    public static void resetBombID(){
        for(int i = 0; i < plantedBombList.size(); i++){
            plantedBombList.get(i).setBombID(i);
        }
    }

    /* Method to plant the collected bomb */
    public static void plantBomb(Player targetPlayer, Bomb bomb){
        targetPlayer.setBomb(targetPlayer.getBomb() - 1);
        // Remove oldest bomb and add the latest bomb into the array
        // when total bomb planted on the map exceed the maximum planted bombs
        if(plantedBombList.size() == MAX_PLANTED_BOMB){
            setTileId(0, plantedBombList.get(0).getX()/64, plantedBombList.get(0).getY()/64);
            plantedBombList.remove(0);
        }
        plantedBombList.add(bomb); // To add bomb object to ArrayList
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