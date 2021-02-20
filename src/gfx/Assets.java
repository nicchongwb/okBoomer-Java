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
    public static BufferedImage[] btn_start;
    public static BufferedImage[] btn_quit;
    public static BufferedImage RightTile, LeftTile, TopTile, BottomTile, CornerTile1, CornerTile2, CornerTile3, CornerTile4, BlueTile, PathTile;
    public static BufferedImage BombTile, BombPart;
    public static BufferedImage[] player1_down, player1_left, player1_right, player1_up, player2_down, player2_left, player2_right, player2_up ;
    public static BufferedImage[] player1_downbombed, player1_leftbombed, player1_rightbombed, player1_upbombed, player2_downbombed, player2_leftbombed, player2_rightbombed, player2_upbombed ;


    public static void init(){
        // Test spritesheet
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/res/sprites/sheet.png"));
        SpriteSheet bgtiles = new SpriteSheet(ImageLoader.loadImage("/res/sprites/bombermantilesnew.png"));
        SpriteSheet character = new SpriteSheet(ImageLoader.loadImage("/res/sprites/characternew.png"));
        SpriteSheet bombItem = new SpriteSheet(ImageLoader.loadImage("/res/sprites/bombs2.bmp"));


        // Set BufferedImage objects to be targets sprites in SpriteSheet

        //start button
        btn_start = new BufferedImage[2];
        btn_start[0] = sheet.crop(0, height*2, width*2, height);
        btn_start[1] = sheet.crop(0, height*3, width*2, height);

        //quit button
        btn_quit = new BufferedImage[1];
        btn_quit[0] = sheet.crop(0, height*1, width*2, height);

        //background
        RightTile = bgtiles.crop(width * 3, height, width, height);
        LeftTile = bgtiles.crop(width, height, width, height) ;
        TopTile = bgtiles.crop(width * 2, 0, width, height);
        BottomTile = bgtiles.crop(width * 2 , height * 2, width, height);
        CornerTile1 = bgtiles.crop(width, 0, width, height);
        CornerTile2 = bgtiles.crop(width * 3, 0, width, height);
        CornerTile3 = bgtiles.crop(width , height * 2, width, height);
        CornerTile4 = bgtiles.crop(width * 3, height *2, width, height);
        BlueTile = bgtiles.crop(width * 2 , height, width, height);
        PathTile = bgtiles.crop(width * 5 , height, width, height);
        //bomb
        BombTile = bombItem.crop(width * 2, 0, width, height);
        BombPart = bombItem.crop(width * 2, 0, width, height);
        //players
        player1_down = new BufferedImage[3];
        player1_left = new BufferedImage[3];
        player1_right = new BufferedImage[3];
        player1_up = new BufferedImage[3];

        player1_downbombed = new BufferedImage[3];
        player1_leftbombed = new BufferedImage[3];
        player1_rightbombed = new BufferedImage[3];
        player1_upbombed = new BufferedImage[3];

        player2_down = new BufferedImage[3];
        player2_left = new BufferedImage[3];
        player2_right = new BufferedImage[3];
        player2_up = new BufferedImage[3];

        player2_downbombed = new BufferedImage[3];
        player2_leftbombed = new BufferedImage[3];
        player2_rightbombed = new BufferedImage[3];
        player2_upbombed = new BufferedImage[3];

        player1_down[0] = character.crop(width * 3,height * 4, width, height);
        player1_down[1] = character.crop(width * 4,height * 4, width, height);
        player1_down[2] = character.crop(width * 5,height * 4, width, height);
        player1_left[0] = character.crop(width * 3,height * 5, width, height);
        player1_left[1] = character.crop(width * 4,height * 5, width, height);
        player1_left[2] = character.crop(width * 5,height * 5, width, height);
        player1_right[0] = character.crop(width * 3,height * 6, width, height);
        player1_right[1] = character.crop(width * 4,height * 6, width, height);
        player1_right[2] = character.crop(width * 5,height * 6, width, height);
        player1_up[0] = character.crop(width * 3,height * 7, width, height);
        player1_up[1] = character.crop(width * 4,height * 7, width, height);
        player1_up[2] = character.crop(width * 5,height * 7, width, height);

        player1_downbombed[0] = character.crop(width * 12,height * 4, width, height);
        player1_downbombed[1] = character.crop(width * 13,height * 4, width, height);
        player1_downbombed[2] = character.crop(width * 14,height * 4, width, height);
        player1_leftbombed[0] = character.crop(width * 12,height * 5, width, height);
        player1_leftbombed[1] = character.crop(width * 13,height * 5, width, height);
        player1_leftbombed[2] = character.crop(width * 14,height * 5, width, height);
        player1_rightbombed[0] = character.crop(width * 12,height * 6, width, height);
        player1_rightbombed[1] = character.crop(width * 13,height * 6, width, height);
        player1_rightbombed[2] = character.crop(width * 14,height * 6, width, height);
        player1_upbombed[0] = character.crop(width * 12,height * 7, width, height);
        player1_upbombed[1] = character.crop(width * 13,height * 7, width, height);
        player1_upbombed[2] = character.crop(width * 14,height * 7, width, height);

        player2_down[0] = character.crop(0,0, width, height);
        player2_down[1] = character.crop(width,0, width, height);
        player2_down[2] = character.crop(width * 2,0, width, height);
        player2_left[0] = character.crop(0, height , width, height);
        player2_left[1] = character.crop(width, height , width, height);
        player2_left[2] = character.crop(width * 2, height , width, height);
        player2_right[0] = character.crop(0,height * 2, width, height);
        player2_right[1] = character.crop(width,height * 2, width, height);
        player2_right[2] = character.crop(width * 2,height * 2, width, height);
        player2_up[0] = character.crop(0,height * 3, width, height);
        player2_up[1] = character.crop(width,height * 3, width, height);
        player2_up[2] = character.crop(width * 2,height * 3, width, height);

        player2_downbombed[0] = character.crop(width * 12,0, width, height);
        player2_downbombed[1] = character.crop(width * 13,0, width, height);
        player2_downbombed[2] = character.crop(width * 14,0, width, height);
        player2_leftbombed[0] = character.crop(width * 12, height , width, height);
        player2_leftbombed[1] = character.crop(width * 13, height , width, height);
        player2_leftbombed[2] = character.crop(width * 14, height , width, height);
        player2_rightbombed[0] = character.crop(width * 12,height * 2, width, height);
        player2_rightbombed[1] = character.crop(width * 13,height * 2, width, height);
        player2_rightbombed[2] = character.crop(width * 14,height * 2, width, height);
        player2_upbombed[0] = character.crop(width * 12,height * 3, width, height);
        player2_upbombed[1] = character.crop(width * 13,height * 3, width, height);
        player2_upbombed[2] = character.crop(width * 14,height * 3, width, height);

        //start button
        //btn_start = new BufferedImage[2];
        //btn_start[0] = sheet.crop(width*6, height*4, width*2, height);
        //btn_start[1] = sheet.crop(width*6, height*4, width*2, height);

    }
}