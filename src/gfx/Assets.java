package gfx;

import java.awt.image.BufferedImage;

/* Purpose of this class is to load all of our assets in the Game class so that
we do not need to keep on using ImageLoader/SpriteSheet class in the Game class's
render() method. FOR PERFORMANCE OPTIMIZATION.
Assets can contain Images, Sounds, Sprites, etc (any other resources).
 */

/* Usage:
1. add Assets.init() in Game class init() method
2. under draw section in render() in Game class, use g to draw:
   eg. g.drawImage(Assets.grass, 10, 10, null);
 */

public class Assets {
    // Declare the game assets objects here
    // We can utilise ArrayList to manage collections of BufferedImages
    // eg. Character animation
    private static final int width = 32, height = 32; // This is the size of each sprite(grid space) in sheet.png
    public static BufferedImage player, dirt, grass, stone, tree; // Eg. of game asset

    public static void init(){
        // Test spritesheet
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/sprites/sheet.png"));
        // Set BufferedImage objects to be targets sprites in SpriteSheet
        player = sheet.crop(0, 0, width, height);
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width * 2, 0 , width, height);
        stone = sheet.crop(width * 3, 0 , width, height);
        tree = sheet.crop(0, height, width, height);
    }
}
