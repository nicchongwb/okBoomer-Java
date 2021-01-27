package entities.creatures;

import entities.Entity;

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final int DEFAULT_SPEED = 64; // Change to 64 after Alicia's map
    public static final int DEFAULT_CREATURE_WIDTH = 64,
                            DEFAULT_CREATURE_HEIGHT = 64;

    // Creature characteristics/attributes
    protected int health;
    protected int speed;
    protected int xMove, yMove; // Movement in pixels to add to x, y


    public Creature(int x, int y, int width, int height) {
        super(x, y, width, height);
        health = DEFAULT_HEALTH; // Set default health
        speed = DEFAULT_SPEED; // Set default speed
        xMove = 0;
        yMove = 0;
    }

    // Move method
    public void move() {
        x += xMove;
        y += yMove;
    }

    // Getters and Setters
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
