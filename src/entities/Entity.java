package entities;

import java.awt.*;

// Entity aka Game Actors, this can be used to implement creatures/items
public abstract class Entity {
    // Entity characteristics/attributes
    protected int x, y; // Position of entity, We use int since our x,y are
                        // whole numbers, float can be used too but depends
                        // on game logic/design.

    // Constructors
    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void tick();

    public abstract void render(Graphics g);
}
