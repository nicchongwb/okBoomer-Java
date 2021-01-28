package states;

import entities.creatures.Player;
import entities.items.Bomb;
import gfx.Assets;
import okBoomer.Game;
import tiles.Tile;
import worlds.World;

import java.awt.*;

public class GameState extends State {

    // World
    private World world;

    // 2D Array to keep track of entities if player touch bomb | for game logic (process damage, etc)
    private static int[][] board;

    // Players
    private Player player1;
    private Player player2;

    // Bombs
    private Bomb bomb; // Explore using arrayList to store list of placed bombs
    // Need an item for player to collect bomb parts to fill up bomb pouch


    // Constructors
    public GameState(Game game){
        super(game); // This is to look at the same game object
        world = new World("src/res/worlds/world1.txt");
        board = new int[world.getWidth()][world.getHeight()];
        player1 = new Player(game, 0,0); // spawn player 1 at the start
        player2 = new Player(game, 576, 576); // spawn player 2 at the end
        bomb = new Bomb(game, 256,256); // spawn bomb in middle

        // Update board with player(s) and bomb coordinate
        board[0][0] = 1; // set player 1 in board [0][0] = 0,0
        board[9][9] = 2; // set player 2 in board [9][9] = 576,576
        board[4][4] = 3; // set bomb in board[4][4] = 256,256

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
        // Render is layered approach (whatever runs first will be base layer)
        world.render(g);

        bomb.render(g);
        player1.render(g);
        player2.render(g);

    }

    /* Method to update board 2d array and call updateEntity method inside */
    public void updateBoard(){

    }

    /* Method to update entity (eg. player health after touching bomb based on 2d array */
    public void updateEntity(){

    }
}
