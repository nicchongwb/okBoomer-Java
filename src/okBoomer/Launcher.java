package okBoomer;

import display.Display;

// Launcher class with main method
public class Launcher {
    // 16:9 windows ratio
    private static final int width = 640;
    private static final int height = 640;
    private static final String title = "Ok, Boomer!";

    public static void main(String[] args){
        Game game =  new Game(title, width, height);
        game.start();
    }
}
