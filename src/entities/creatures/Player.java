package entities.creatures;

import gfx.Assets;
import okBoomer.Game;

import java.awt.*;

/* Usage: GameState class -> private Player player;
GameState constructor -> player = new Player(x,y);
GameState tick() -> player.tick();
GameState render() -> player.render(g);

 */

public class Player extends Creature{
    private static int playerCount = 0;
    private static int pixToMove = 32; // Amount of pixels to move

    // Player characteristics/attributes
    private String name;
    private final int pid; // Player ID
    private Game game;

    public Player(Game game, int x, int y) {
        super(x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.game = game; // Help us access KeyManager
        pid = playerCount;
        playerCount++;
    }

    public String getName(){
        return name;
    }

    private void setName(String name){
        this.name = name;
    }

    private void getInput() {
        // Everytime we call getInput method, we have to reset xMove and yMove
        xMove = 0;
        yMove = 0;

        if (pid == 0){ // If pid == 0 for player 1
            if (game.getKeyManager().p1Up) {
                yMove = -speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
            if (game.getKeyManager().p1Down) {
                yMove = speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
            if (game.getKeyManager().p1Left) {
                xMove = -speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
            if (game.getKeyManager().p1Right) {
                xMove = speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
        }
        else if (pid == 1){ // It pid == 1 for player 2
            if (game.getKeyManager().p2Up) {
                yMove = -speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
            if (game.getKeyManager().p2Down) {
                yMove = speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
            if (game.getKeyManager().p2Left) {
                xMove = -speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
            if (game.getKeyManager().p2Right) {
                xMove = -speed;
                System.out.printf("X: %d\tY: %d%n", x, y);
            }
        }

    }

    @Override
    public void tick() {
        getInput();
        move();
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw method to draw out player
        g.drawImage(Assets.player, x, y, width, height,null);
    }

}
