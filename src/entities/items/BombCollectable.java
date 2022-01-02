package entities.items;

import gfx.Assets;
import okBoomer.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/* BombCollectable.java is called when spawning
*  Bomb Parts (bombs that are collectable by the player).
*  Not to be confused with Bomb.java, which is called when a player
*  places down a bomb. ;)
* */

public class BombCollectable extends Item{

    private ArrayList<BombCollectable> bombsSpawnedList  = new ArrayList<BombCollectable>();
    private int numBombsSpawned = 0; // count the number of bombs spawned in the game
    private int bombID = 0; // give each bomb(collectable) spawned an ID.
    private BufferedImage bombPart;

    public BombCollectable(Handler handler, int x, int y) {
        super(handler, x, y, Item.DEFAULT_ITEM_WIDTH, Item.DEFAULT_ITEM_HEIGHT);
        this.handler = handler; // Help us access KeyManager
        bombPart = Assets.BombPart;
        bombID++;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out bomb (collectable)
        g.drawImage(bombPart, x, y, width, height,null);

    }

    /* Getters and Setters */
    public int getBombsSpawned(){
        return this.numBombsSpawned;
    }

    // nicholas i think you will need to call this after the player picks up the bomb: OK NOTED WITH THANKS
    private void minusBombsSpawned(){
        this.numBombsSpawned--;
    }

    // get the current bomb objects on the map
    public ArrayList<BombCollectable> getBombsSpawnedList(){
        return this.bombsSpawnedList;
    }
}
