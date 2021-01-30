package entities.creatures;

import gfx.Assets;
import okBoomer.Game;
import states.GameState;
import worlds.World;

import java.awt.*;

/* Usage: GameState class -> private Player player;
GameState constructor -> player = new Player(x,y);
GameState tick() -> player.tick();
GameState render() -> player.render(g);

 */

public class Player extends Creature{
    // Access current board information about player1/2 currX, currY from GameState static variables
    private World world = GameState.getWorld();

    private static int playerCount = 0;
    private static int pixToMove = 32; // Amount of pixels to move

    // Player characteristics/attributes
    private String name;
    private final int pid; // Player ID
    private Game game;

    // declare variables to check if key is already pressed
    private static boolean alrPressedp1 = false;
    private static boolean alrPressedp2 = false;

    // temporary variables to hold next player coordinates
    private static int newX;
    private static int newY;

    public Player(Game game, int x, int y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game; // Help us access KeyManager
        pid = playerCount;
        playerCount++;
    }

    public String getName(){
        return name;
    }

    private void setName(String name){
        this.name = name;
    }

    private void getInput() {
        // Everytime we call getInput method, we have to reset xMove and yMove
        xMove = 0;
        yMove = 0;

        newX = x;
        newY = y;

        if (pid == 0){ // If pid == 0 for player 1
            if (game.getKeyManager().p1Up) { // if keyPressed is true

                if(!alrPressedp1){ // check if alrPressed is true

                    // for player collision purposes, check if the next tile the player will be moving to
                    // collides with the edges of the map.
                    newY = y - speed;

                    // if they don't collide, allow them to move
                    // and update board array
                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        yMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true; // set alrPressed to true so our player won't move themselves continuously
                                        // Note: alrPressed is set to false inside KeyManager.java on keyRelease
                }
            }
            if (game.getKeyManager().p1Down) {

                if(!alrPressedp1){

                    newY = y + speed;
                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        yMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true;
                }

            }
            if (game.getKeyManager().p1Left) {

                if(!alrPressedp1){

                    newX = x - speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        xMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true;
                }

            }
            if (game.getKeyManager().p1Right) {

                if(!alrPressedp1){

                    newX = x + speed;
                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        xMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true;
                }

            }
        }
        else if (pid == 1){ // It pid == 1 for player 2

            if (game.getKeyManager().p2Up) {

                if(!alrPressedp2){
                    newY = y - speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        yMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }

                    alrPressedp2 = true;
                }

            }
            if (game.getKeyManager().p2Down) {

                if(!alrPressedp2){

                    newY = y + speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        yMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }

                    alrPressedp2 = true;
                }

            }
            if (game.getKeyManager().p2Left) {

                if(!alrPressedp2){

                    newX = x - speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        xMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp2 = true;
                }

            }
            if (game.getKeyManager().p2Right) {

                if(!alrPressedp2){

                    newX = x + speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY)){ // coords are passed in as pixels
                        xMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp2 = true;
                }
            }
        }

    }

    public static void setIfPressed1(boolean bool){
        alrPressedp1 = bool;
    }

    public static void setIfPressed2(boolean bool){
        alrPressedp2 = bool;
    }

    public static boolean getIfPressed1(){
        return alrPressedp1;
    }

    public static boolean getIfPressed2(){
        return alrPressedp2;
    }

    @Override
    public void tick() {
        // Print out player position if key is not pressed
        /*if (alrPressedp1){
            System.out.printf("P%d: X: %d\tY: %d%n", getPid()+1, x, y);
        }
        */
        getInput();
        move();
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out player
        g.drawImage(Assets.player, x, y, width, height,null);
    }

    // Getter and Setter
    public int getPid() {
        return pid;
    }
}
