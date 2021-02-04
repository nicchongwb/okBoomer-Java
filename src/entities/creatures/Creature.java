package entities.creatures;

import entities.Entity;
import okBoomer.Handler;

public abstract class Creature extends Entity {

    // DEFAULT_BOMB need to edit
    //public static final int DEFAULT_BOMB = 10;
    public static final int DEFAULT_HEALTH = 10;
    public static final int DEFAULT_SPEED = 64; // Change to 64 after Alicia's map
    public static final int DEFAULT_CREATURE_WIDTH = 64,
                            DEFAULT_CREATURE_HEIGHT = 64;

    // Creature characteristics/attributes
    //protected int bomb;
    protected int health;
    protected int speed;
    protected int xMove, yMove; // Movement in pixels to add to x, y


    public Creature(Handler handler, int x, int y, int width, int height) {
        super(handler, x, y, width, height);
        //bomb = DEFAULT_BOMB; // Set default bomb
        health = DEFAULT_HEALTH; // Set default health
        speed = DEFAULT_SPEED; // Set default speed
        xMove = 0;
        yMove = 0;
    }

    // Move method
    public void move() {
        prevX = x; // Set prevX before updating x
        prevY = y; // Set prevY before updating y
        x += xMove;
        y += yMove;
    }

    // Getters and Setters
    /*public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        this.bomb = bomb;
    }*/

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getxMove() {
        return xMove;
    }

    public void setxMove(int xMove) {
        this.xMove = xMove;
    }

    public int getyMove() {
        return yMove;
    }

    public void setyMove(int yMove) {
        this.yMove = yMove;
    }

}
