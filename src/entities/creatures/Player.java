package entities.creatures;

import gfx.Assets;
import sfx.AudioPlayer;
import interfaces.Board;
import okBoomer.Handler;
import states.GameState;
import gfx.Animation;
import entities.items.Bomb;
import java.awt.*;
import java.awt.image.BufferedImage;


/* Usage: GameState class -> private Player player;
GameState constructor -> player = new Player(x,y);
GameState tick() -> player.tick();
GameState render() -> player.render(g);

 */

public class Player extends Creature implements Board {
    private static final int DEFAULT_BOMB = 0;
    private static final int MAX_BOMB = 3;

    public static int playerCount = 0;
    private static int pixToMove = 32; // Amount of pixels to move
    private Animation p1animDown, p1animUp, p1animLeft, p1animRight, p2animDown, p2animUp, p2animLeft, p2animRight;
    private Animation p1animDownbombed, p1animUpbombed, p1animLeftbombed, p1animRightbombed, p2animDownbombed, p2animUpbombed, p2animLeftbombed, p2animRightbombed;
    private static int p1facing = 1; // 0: face up, 1: down, 2: left, 3:right
    private static int p2facing = 0;

    // Player characteristics/attributes
    private String name;
    private final int pid; // Player ID
    private Handler handler;
    private Bomb bomb;      // Initialise bomb object with coordinates
    private Player player;
    private int bombHeld; // Keep track of how many bombs player is holding
    private  boolean checkBombed = false; // Check whether player got bombed
    private  boolean bombTimer = false;
    private long lastTrueTime;

    private int bombCollectable; // Keep track of how many bombCollectable player is holding

    // Declare variables to check if key is already pressed
    private static boolean alrPressedp1 = false;
    private static boolean alrPressedp2 = false;

    // Temporary variables to hold next player coordinates
    private int newX;
    private int newY;

    // Variables for playing sounds
    private AudioPlayer collectsound, dropsound;

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

        p1animDownbombed = new Animation(245, Assets.player1_downbombed);
        p1animUpbombed = new Animation(245, Assets.player1_upbombed);
        p1animLeftbombed = new Animation(245, Assets.player1_leftbombed);
        p1animRightbombed = new Animation(245, Assets.player1_rightbombed);

        p2animDownbombed = new Animation(245, Assets.player2_downbombed);
        p2animUpbombed = new Animation(245, Assets.player2_upbombed);
        p2animLeftbombed = new Animation(245, Assets.player2_leftbombed);
        p2animRightbombed = new Animation(245, Assets.player2_rightbombed);
    }

    // Getter and Setter
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
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

    public int getNewX() {
        return newX;
    }

    private void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    private void setNewY(int newY) {
        this.newY = newY;
    }

    public int getPid() {
        return pid;
    }

    public int getBomb() {
        return bombHeld;
    }

    public void setBomb(int bomb) {
        this.bombHeld = bomb;
    }

    public int getBombCollectable(){
        return bombCollectable;
    }

    public boolean setBombed(){
        return this.checkBombed = true;
    }

    private boolean setBombedTimer(){
        return this.bombTimer = false;
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

        p1animDownbombed.tick();
        p1animUpbombed.tick();
        p1animLeftbombed.tick();
        p1animRightbombed.tick();
        p2animDownbombed.tick();
        p2animUpbombed.tick();
        p2animLeftbombed.tick();
        p2animRightbombed.tick();

        getInput();
        move();
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out player
        if (checkBombed) {
            bombTimer();
            if (bombTimer) {
                g.drawImage(getBombedAnimationFrame(), x, y, width, height, null);
            }
            else{
                g.drawImage(getCurrentAnimationFrame(), x, y, width, height, null);
            }
        }
        else {
            g.drawImage(getCurrentAnimationFrame(), x, y, width, height, null);
        }
    }

    // Buffer Image and Timer
    private BufferedImage getCurrentAnimationFrame(){
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

    private BufferedImage getBombedAnimationFrame() {
        if (checkBombed) {
            if (pid == 0) {
                if (p1facing == 0) {
                    return p1animUpbombed.getCurrentFrame();
                } else if (p1facing == 1) {
                    return p1animDownbombed.getCurrentFrame();
                } else if (p1facing == 2) {
                    return p1animLeftbombed.getCurrentFrame();
                } else {
                    return p1animRightbombed.getCurrentFrame();
                }
            } else {
                if (p2facing == 0) {
                    return p2animUpbombed.getCurrentFrame();
                } else if (p2facing == 1) {
                    return p2animDownbombed.getCurrentFrame();
                } else if (p2facing == 2) {
                    return p2animLeftbombed.getCurrentFrame();
                } else {
                    return p2animRightbombed.getCurrentFrame();
                }
            }
        } else {
            return getCurrentAnimationFrame();
        }
    }

    private boolean bombTimer() {
        long now = System.currentTimeMillis();
        if (checkBombed & !bombTimer) {
            lastTrueTime = now;
            bombTimer = true;
        }
        if (lastTrueTime + 1000 < now) { //to run player bombed animations for 1 second
            checkBombed = false;
            bombTimer = false;
        }
        return bombTimer;
    }

    // Other methods
    private void getInput() {
        // Everytime we call getInput method, we have to reset xMove and yMove
        xMove = 0;
        yMove = 0;

        newX = x;
        newY = y;

        if (pid == 0){ // If pid == 0 for player 1
            if (handler.getKeyManager().p1Up) { // If keyPressed is true

                if(!alrPressedp1){ // Check if alrPressed is true

                    // For player collision purposes, check if the next tile the player will be moving to
                    // collides with the edges of the map.
                    newY = y - speed;

                    // If they don't collide, allow them to move
                    // and update board array
                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // The coords are passed in as pixels
                        yMove = -speed;
                    }

                    alrPressedp1 = true; // To set alrPressed to true so our player won't move themselves continuously
                    // Note: alrPressed is set to false inside KeyManager.java on keyRelease
                    p1facing = 0 ;
                }
            }
            if (handler.getKeyManager().p1Down) {

                if(!alrPressedp1){

                    newY = y + speed;
                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = speed;
                    }

                    alrPressedp1 = true;
                    p1facing = 1;
                }

            }
            if (handler.getKeyManager().p1Left) {

                if(!alrPressedp1){

                    newX = x - speed;

                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = -speed;
                    }

                    alrPressedp1 = true;
                    p1facing = 2;
                }

            }
            if (handler.getKeyManager().p1Right) {

                if(!alrPressedp1){

                    newX = x + speed;
                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = speed;
                    }

                    alrPressedp1 = true;
                    p1facing = 3;
                }

            }
            if (handler.getKeyManager().p1Bomb) {
                // Ensure that player collected at least 1 bomb
                if (getBomb() > 0) {
                    // Check if the tile have a bomb planted or not
                    if (GameState.getTileId(prevX / 64, prevY / 64) != 5) {
                        GameState.setTileId(5, prevX / 64, prevY / 64);
                        // Add new bomb object
                        bomb = new Bomb(handler, prevX, prevY);
                        GameState.plantBomb(this, bomb);

                        dropsound = new AudioPlayer("/res/audio/drop.wav"); //sound effect for dropping bomb
                        dropsound.playonce();

                    }

                }

                alrPressedp1 = true;
            }
        }

        else if (pid == 1){ // It pid == 1 for player 2

            if (handler.getKeyManager().p2Up) {

                if(!alrPressedp2){
                    newY = y - speed;

                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = -speed;
                    }

                    alrPressedp2 = true;
                    p2facing = 0;
                }

            }
            if (handler.getKeyManager().p2Down) {

                if(!alrPressedp2){

                    newY = y + speed;

                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        yMove = speed;
                    }

                    alrPressedp2 = true;
                    p2facing = 1;
                }

            }
            if (handler.getKeyManager().p2Left) {

                if(!alrPressedp2){

                    newX = x - speed;

                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)) { // coords are passed in as pixels
                        xMove = -speed;
                    }

                    alrPressedp2 = true;
                    p2facing = 2;
                }

            }
            if (handler.getKeyManager().p2Right) {

                if(!alrPressedp2){

                    newX = x + speed;

                    if(Board.canPlayerMove(pid, prevX, prevY, newX, newY, this)){ // coords are passed in as pixels
                        xMove = speed;
                    }

                    alrPressedp2 = true;
                    p2facing = 3;
                }
            }
            if (handler.getKeyManager().p2Bomb) {
                // need to fix bug
                if(!alrPressedp2){
                    // Ensure that player collected at least 1 bomb
                    if (getBomb() > 0) {
                        // Check if the tile have a bomb planted or not
                        if (GameState.getTileId(prevX / 64, prevY / 64) != 6) {
                            GameState.setTileId(6, prevX / 64, prevY / 64);
                            // Add new bomb object
                            bomb = new Bomb(handler, prevX, prevY);
                            GameState.plantBomb(this, bomb);
                            dropsound = new AudioPlayer("/res/audio/drop.wav"); //sound effect for dropping bomb
                            dropsound.playonce();
                        }

                    }

                    alrPressedp2 = true;
                }
            }
        }

    }

    // Method to convert bombCollectable to bombHeld
    public void addBombPart(){
        // We convert 2 bombCollectable to 1 bomb
        // Each player only can hold up to 2 bomb, if max bomb held, the player still can collect
        // up to 2 bombCollectable
        collectsound = new AudioPlayer("/res/audio/collect.wav"); //sound effect for collecting bomb
        collectsound.playonce();
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