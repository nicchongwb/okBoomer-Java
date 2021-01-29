package entities.items;

import gfx.Assets;
import okBoomer.Game;

import java.awt.*;

public class Bomb extends Item{
    private static final int DEFAULT_DAMAGE = 1;
    private static int bombCount = 0;

    // Player characteristics/attributes
    private String name;
    private final int bombID; // Player ID
    private Game game;
    private int damage;

    public Bomb(Game game, int x, int y){
        super(x, y, Item.DEFAULT_ITEM_WIDTH, Item.DEFAULT_ITEM_HEIGHT);
        this.game = game; // Help us access KeyManager
        this.damage = DEFAULT_DAMAGE;
        bombID = bombCount;
        bombCount++;
    }

    public String getName(){
        return name;
    }

    private void setName(String name){
        this.name = name;
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
