package tiles;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/* Tile class for storing information about each tile.
* Usage: Tiles.tile[0].render(g, x, y);
* ^ The above spawns a grass tile at position x, y.
* */

public class Tile {

    //STATIC STUFF HERE
    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(0); // grass tile has an id of 0
    public static Tile dirtTile = new DirtTile(1); // dirt tile has an id of 1
    public static Tile rockTile = new RockTile(2);
    public static Tile treeTile = new TreeTile(3);

    //CLASS

    public static final int TILEWIDTH = 64, TILEHEIGHT = 64;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void tick(){

    }

    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }

    // for collision detection
    public boolean isSolid(){
        return false;
    }

    // get specific id of each tile
    public int getId(){
        return id;
    }

}
