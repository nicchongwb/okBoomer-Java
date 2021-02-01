package entities;

import okBoomer.Handler;

import java.awt.*;

// Entity aka Game Actors, this can be used to implement creatures/items
public abstract class Entity {
    protected Handler handler;
    // Entity characteristics/attributes
    protected int x, y; // Position of entity, We use int since our x,y are
                        // whole numbers, float can be used too but depends
                        // on game logic/design.

    protected int width, height; // Size of entity

    protected int prevX, prevY; // Previous position of entity, these variables help
    // to update the 2D board array

    // Constructors
    public Entity(Handler handler, int x, int y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.prevX = this.x;
        this.prevY = this.y;

    }

    public abstract void tick();

    public abstract void render(Graphics g);

    // Getters and Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }
}
