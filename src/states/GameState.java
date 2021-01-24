package states;

import entities.creatures.Player;
import gfx.Assets;
import okBoomer.Game;
import tiles.Tile;
import worlds.World;

import java.awt.*;

public class GameState extends State {

    // World
    private World world;

    // Players
    private Player player1;
    private Player player2;

    // Constructors
    public GameState(Game game){
        super(game); // This is to look at the same game object
        world = new World("src/res/worlds/world1.txt");
        player1 = new Player(game, 0,0); // spawn player 1 at the start
        player2 = new Player(game, 576, 576); // spawn player 2 at the end

    }


    // Other methods
    @Override
    public void tick() {
        // Insert logic to update all variables related to Game
        world.tick();
        player1.tick();
        player2.tick();
    }

    @Override
    public void render(Graphics g) {
        world.render(g);
        player1.render(g);
        player2.render(g);

    }
}
