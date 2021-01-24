package states;

import entities.creatures.Player;
import gfx.Assets;
import okBoomer.Game;

import java.awt.*;

public class GameState extends State {
    // Players
    private Player player1;
    private Player player2;


    // Constructors
    public GameState(Game game){
        super(game); // This is to look at the same game object
        player1 = new Player(game, 100,100);
        player2 = new Player(game, 200, 200);

    }


    // Other methods
    @Override
    public void tick() {
        // Insert logic to update all variables related to Game
        player1.tick();
        player2.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.dirt, 0, 0, null); // Test
        player1.render(g);
        player2.render(g);

    }
}
