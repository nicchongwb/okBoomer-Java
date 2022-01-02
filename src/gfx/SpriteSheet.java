package gfx;

import java.awt.image.BufferedImage;

/* Description: SpriteSheet class for us to create a SpriteSheet object
Concept of SpriteSheet is store a sheet of sprites
Then we have a crop function to crop/focus accordingly to the target sprite
in the sheet. */

/* Usage:
To be use together with ImageLoader object.
In Assets class:
- make a private SpriteSheet object.
- init() -> SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/sprites/sheet.png"));
- init() -> player = sheet.crop(0, 0, width, height);
 */


public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(BufferedImage sheet){
        this.sheet = sheet;
    }

    protected BufferedImage crop(int x, int y, int width, int height){
        // Takes in 4 parameters, x|y is the coordinate to start crop
        // width|height is the size of crop
        return sheet.getSubimage(x, y, width, height);
    }
}
