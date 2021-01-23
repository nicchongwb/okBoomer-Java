package gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Purpose of ImageLoader class is to load images for us
// Java stores image in a BufferedImage object

/* Usage of ImageLoader object:
In Assets class:
- make a BufferedImage -> public static BufferedImage player;
 */
public class ImageLoader {
    // Method to load Image
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // If loading image fails, then we exit out of method
        }
        return null;
    }
}
