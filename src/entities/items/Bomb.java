package entities.items;

import gfx.Assets;
import okBoomer.Game;
import okBoomer.Handler;

import java.awt.*;

public class Bomb extends Item{
    private static final int DEFAULT_DAMAGE = 1;
    private static int bombCount = 0;

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
    }

    // Getter and Setter
    public String getName(){
        return name;
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

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out player
        g.setColor(Color.darkGray);
        g.fillOval(x, y, width, height);
    }
}
