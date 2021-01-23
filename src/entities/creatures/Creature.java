package entities.creatures;

import entities.Entity;

public abstract class Creature extends Entity {
    // Creature characteristics/attributes
    protected int health;

    public Creature(int x, int y) {
        super(x, y);
        health = 10; // Set health
    }

}
