package worlds;

import tiles.Tile;
import utils.Utils;

import java.awt.Graphics;

/* Usage: GameState class -> private World world;
*
* */
public class World {

    private int width, height;
    private int spawnX, spawnY;

    private int[][] tileArr; // 2d-array for storing coordinates x, y

    public World(String path){
        loadWorld(path);
    }

    public void tick(){

    }

    public void render(Graphics g){
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                getTile(x, y).render(g, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT); // render the tile at coordinates x, y.
            }
        }
    }


    public Tile getTile(int x, int y){
        Tile t = Tile.tiles[tileArr[x][y]]; // obtain tile id at coordinates x, y.
        if(t == null) { // if invalid tile id is called, return a regular path tile (default).
            return Tile.PathTile;
        }
        return t;
    }

    // function for loading worlds from text file.
    private void loadWorld(String path){
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tileArr = new int[width][height];
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                tileArr[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]); // offset is 4 bc the first four variables are the variables
                                                                            // width,height,spawnx,spawny and not actual world data
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
