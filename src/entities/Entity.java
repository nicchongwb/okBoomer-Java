package entities;

import java.awt.*;

// Entity aka Game Actors, this can be used to implement creatures/items
public abstract class Entity {
    // Entity characteristics/attributes
    protected int x, y; // Position of entity, We use int since our x,y are
                        // whole numbers, float can be used too but depends
                        // on game logic/design.

    protected int width, height; // Size of entity

    // Constructors
    public Entity(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

}
