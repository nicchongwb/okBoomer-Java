package entities.creatures;

import entities.items.BombCollectable;
import gfx.Assets;
import okBoomer.Game;
import okBoomer.Handler;
import states.GameState;
import worlds.World;
import gfx.Animation;
import entities.items.Bomb;

import java.awt.*;
import java.awt.image.BufferedImage;

/* Usage: GameState class -> private Player player;
GameState constructor -> player = new Player(x,y);
GameState tick() -> player.tick();
GameState render() -> player.render(g);

 */

public class Player extends Creature{
    public static final int DEFAULT_BOMB = 0;
    public static final int MAX_BOMB = 3;

    private static int playerCount = 0;
    private static int pixToMove = 32; // Amount of pixels to move
    private Animation p1animDown, p1animUp, p1animLeft, p1animRight, p2animDown, p2animUp, p2animLeft, p2animRight;
    private static int p1facing = 1; // 0: face up, 1: down, 2: left, 3:right
    private static int p2facing = 0;

    // Player characteristics/attributes
    private String name;
    private final int pid; // Player ID
    private Handler handler;
    private Bomb bomb;
    private int bombHeld; // keep track of how many bombs player is holding

    private int bombCollectable; // keep track of how many bombCollectable player is holding

    // declare variables to check if key is already pressed
    private static boolean alrPressedp1 = false;
    private static boolean alrPressedp2 = false;

    // temporary variables to hold next player coordinates
    private int newX;
    private int newY;

    public Player(Handler handler, int x, int y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.handler = handler; // Help us access KeyManager
        pid = playerCount;
        playerCount++;
        this.bombHeld = DEFAULT_BOMB;
        this.bombCollectable = 0; // Start off with 0 bomb collectable

        //Animations
        p1animDown = new Animation(500, Assets.player1_down);
        p1animUp = new Animation(500, Assets.player1_up);
        p1animLeft = new Animation(500, Assets.player1_left);
        p1animRight = new Animation(500, Assets.player1_right);

        p2animDown = new Animation(500, Assets.player2_down);
        p2animUp = new Animation(500, Assets.player2_up);
        p2animLeft = new Animation(500, Assets.player2_left);
        p2animRight = new Animation(500, Assets.player2_right);
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
            if (handler.getKeyManager().p1Up) { // if keyPressed is true

                if(!alrPressedp1){ // check if alrPressed is true

                    // for player collision purposes, check if the next tile the player will be moving to
                    // collides with the edges of the map.
                    newY = y - speed;

                    // if they don't collide, allow them to move
                    // and update board array
                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true; // set alrPressed to true so our player won't move themselves continuously
                                        // Note: alrPressed is set to false inside KeyManager.java on keyRelease
                    p1facing = 0 ;
                }
            }
            if (handler.getKeyManager().p1Down) {

                if(!alrPressedp1){

                    newY = y + speed;
                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true;
                    p1facing = 1;
                }

            }
            if (handler.getKeyManager().p1Left) {

                if(!alrPressedp1){

                    newX = x - speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true;
                    p1facing = 2;
                }

            }
            if (handler.getKeyManager().p1Right) {

                if(!alrPressedp1){

                    newX = x + speed;
                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp1 = true;
                    p1facing = 3;
                }

            }
            if (handler.getKeyManager().p1Bomb) {

                if (!alrPressedp1) {
                    if (getBomb() > 0) {
                        if (GameState.getTileId(prevX / 64, prevY / 64) != 5) {
                            GameState.setTileId(5, prevX / 64, prevY / 64);
                            GameState.plantBomb(this);
                            System.out.println("p1 bomb pouch: " + getBomb());
                            // add new bomb object
                        } else {
                            System.out.println("p1 bomb planted");
                        }
                    } else {
                        System.out.println("p1 bomb pouch: empty");
                    }
                    alrPressedp1 = true;
                }
            }
        }
        else if (pid == 1){ // It pid == 1 for player 2

            if (handler.getKeyManager().p2Up) {

                if(!alrPressedp2){
                    newY = y - speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }

                    alrPressedp2 = true;
                    p2facing = 0;
                }

            }
            if (handler.getKeyManager().p2Down) {

                if(!alrPressedp2){

                    newY = y + speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }

                    alrPressedp2 = true;
                    p2facing = 1;
                }

            }
            if (handler.getKeyManager().p2Left) {

                if(!alrPressedp2){

                    newX = x - speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = -speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp2 = true;
                    p2facing = 2;
                }

            }
            if (handler.getKeyManager().p2Right) {

                if(!alrPressedp2){

                    newX = x + speed;

                    if(GameState.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = speed;
                    }
                    else{
                        System.out.println("No move pls");
                    }
                    alrPressedp2 = true;
                    p2facing = 3;
                }
            }
            if (handler.getKeyManager().p2Bomb) {

                if(!alrPressedp2){
                    // Ensure that player collected at least 1 bomb
                    if(getBomb() > 0) {
                        // Player can only plant 1 bomb at a time
                        if (GameState.getTileId(prevX/64, prevY/64) != 6) {
                            // Get player position and plant the bomb
                            GameState.setTileId(6, prevX / 64, prevY / 64);
                            GameState.plantBomb(this);
                            System.out.println("p2 bomb pouch: " + getBomb());
                        }
                        else{
                            System.out.println("p2 bomb planted");
                        }
                    }
                    else{
                        System.out.println("p2 bomb pouch: empty");
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
        //Animations
        p1animDown.tick();
        p1animUp.tick();
        p1animLeft.tick();
        p1animRight.tick();
        p2animDown.tick();
        p2animUp.tick();
        p2animLeft.tick();
        p2animRight.tick();

        getInput();
        move();
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out player
        g.drawImage(getCurrentAnimationFrame(), x, y, width, height,null);
    }

    private BufferedImage getCurrentAnimationFrame(){
        if (pid ==0){
            if (handler.getKeyManager().p1Left){
                return p1animLeft.getCurrentFrame();
            }else if (handler.getKeyManager().p1Right){
                return p1animRight.getCurrentFrame();
            }else if (handler.getKeyManager().p1Up){
                return p1animUp.getCurrentFrame();
            } else if (handler.getKeyManager().p1Down){
                return p1animDown.getCurrentFrame();
            }else{
                return getFacing();
            }
        }
        else {
            if (handler.getKeyManager().p2Left){
                return p2animLeft.getCurrentFrame();
            }else if (handler.getKeyManager().p2Right){
                return p2animRight.getCurrentFrame();
            }else if (handler.getKeyManager().p2Down){
                return p2animDown.getCurrentFrame();
            } else {
                return getFacing();
            }
        }
    }

    private BufferedImage getFacing(){
        if (pid ==0) {
            if (p1facing == 0) {
                return p1animUp.getCurrentFrame();
            } else if (p1facing == 1) {
                return p1animDown.getCurrentFrame();
            } else if (p1facing == 2) {
                return p1animLeft.getCurrentFrame();
            } else {
                return p1animRight.getCurrentFrame();
            }
        }
        else{
            if (p2facing == 0) {
                return p2animUp.getCurrentFrame();
            } else if (p2facing == 1) {
                return p2animDown.getCurrentFrame();
            } else if (p2facing == 2) {
                return p2animLeft.getCurrentFrame();
            } else {
                return p2animRight.getCurrentFrame();
            }
        }
    }

    // Getter and Setter
    public int getPid() {
        return pid;
    }

    // Other methods
    public int getBomb() {
        return bombHeld;
    }

    public void setBomb(int bomb) {
        this.bombHeld = bomb;
    }

    public int getBombCollectable(){
        return bombCollectable;
    }


    // Method to convert bombCollectable to bombHeld
    public void addBombPart(){
        // We convert 2 bombCollectable to 1 bomb
        // Each player only can hold up to 2 bomb, if max bomb held, the player still can collect
        // up to 2 bombCollectable
        if (bombCollectable < 2){
            this.bombCollectable += 1;
        }

        if (bombHeld < MAX_BOMB) {
            if (this.bombCollectable >= 2) {
                this.bombCollectable -= 2;
                this.bombHeld += 1;
            }
        }
    }

}
