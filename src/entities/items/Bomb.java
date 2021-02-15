package entities.items;

import entities.creatures.Player;
import gfx.Assets;
import okBoomer.Game;
import okBoomer.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bomb extends Item{
    private static final int DEFAULT_DAMAGE = 1;
    private static int bombCount = 0;
    private static int bombID;
    private BufferedImage bombImg;
    private int damage;
    private ArrayList<Bomb> plantBombList  = new ArrayList<Bomb>();

    public Bomb(Handler handler, int x, int y){
        super(handler, x, y, Item.DEFAULT_ITEM_WIDTH, Item.DEFAULT_ITEM_HEIGHT);
        this.handler = handler; // Help us access KeyManager
        this.damage = DEFAULT_DAMAGE;
        bombID = bombCount;
        bombCount++;
        bombImg = Assets.BombTile;
    }



    // Getter and Setter
    public int getDamage() {
        return damage;
    }

    // reset
    public void setBombID(int bombID){
        this.bombID = bombID;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBombID(){
        return bombID;
    }

    public void setBombCount(int bombCount){
        this.bombCount = bombCount;
    }

    // get the planted bomb objects on the map
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
