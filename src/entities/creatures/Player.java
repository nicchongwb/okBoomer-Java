package entities.creatures;

import gfx.Assets;

import java.awt.*;

/* Usage: GameState class -> private Player player;
GameState constructor -> player = new Player(x,y);
GameState tick() -> player.tick();
GameState render() -> player.render(g);

 */

public class Player extends Creature{
    private static int playerCount = 0;
    // Player characteristics/attributes

    private String name;
    private int pid; // Player ID


    public Player(int x, int y) {
        super(x, y);
        pid = playerCount;
        playerCount++;
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
        g.drawImage(Assets.player, x, y, null);
    }

}
