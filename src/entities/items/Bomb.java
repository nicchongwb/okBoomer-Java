package entities.items;

import gfx.Assets;
import okBoomer.Game;
import okBoomer.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/* Bomb.java is called when a player places down a bomb!
*  This is different from the BombCollectable.java, where
*  we use it to spawn Bomb Parts (bombs that are collectable by the player)
* */
public class Bomb extends Item{
    private static final int DEFAULT_DAMAGE = 1;
    private static int bombCount = 0;
    private BufferedImage bombImg;
    // ArrayList to hold the planted bombs objects
    private ArrayList<Bomb> plantBombList  = new ArrayList<Bomb>();

    // Player characteristics/attributes
    private String name;
    private final int bombID; // Player ID
    private Game game;
    private int damage;

    public Bomb(Handler handler, int x, int y){
        super(handler, x, y, Item.DEFAULT_ITEM_WIDTH, Item.DEFAULT_ITEM_HEIGHT);
        this.handler = handler; // Help us access KeyManager
        this.damage = DEFAULT_DAMAGE;
        bombID = bombCount;
        bombCount++;
        bombImg = Assets.BombTile;
    }

    // Getter and Setter
    public String getName(){
        return name;
    }

    public int getBombID(){
        return bombID;
    }

    private void setName(String name){
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public ArrayList<Bomb> getPlantedBombList(){
        return this.plantBombList;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out bomb
        g.drawImage(bombImg, x, y, width, height,null);
    }
}
