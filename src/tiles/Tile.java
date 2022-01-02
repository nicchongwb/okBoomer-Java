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
    public static Tile CornerTile1 = new CornerTile1(0); // CornerTile1 has an id of 0
    public static Tile TopTile = new TopTile(1); // TopTile has an id of 1
    public static Tile CornerTile2 = new CornerTile2(2);
    public static Tile LeftTile = new LeftTile(3);
    public static Tile RightTile = new RightTile(4);
    public static Tile CornerTile3 = new CornerTile3(5);
    public static Tile BottomTile = new BottomTile(6);
    public static Tile CornerTile4 = new CornerTile4(7);
    public static Tile BlueTile = new BlueTile(8);
    public static Tile PathTile = new PathTile(9);
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
