package okBoomer;

import display.Display;
import gfx.Assets;
import gfx.ImageLoader;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/* Game class, not to be mistaken with Launcher class
This class will be the main class of our game that holds all of our
base code of our game.
- Start everything, Close everything and close our game
- Game class will implement Runnable to make it run on a thread
*/

public class Game implements Runnable{
    // Declare local variables
    private Display display; // To give our Game class a Display

    // Variables for Display
    public int width, height; // Easier to access width,height used
    public String title;

    // Variables for Thread
    private boolean running = false; // Running variable for game loop run()
    private Thread thread; // Thread object for us to run the game on

    // Variables for rendering
    private BufferStrategy bs; // Buffer = hidden screen, to buffer number images before rendering them out to screen
    private Graphics g; // Allows us to draw things(shapes,lines,images) to canvas, basically our paintbrush

    int x = 0; // Test variable for game tick/update refresh rate


    // Constructors
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    // Other methods

    // init method to initialise assets in our game before we process to run our game
    private void init(){
        display = new Display(title, width, height); // Initialise Display object for Game instance
        Assets.init(); // Load in all of our assets
    }

    // tick method a.k.a update all game variables, positions of objects, etc
    // during game loop.
    private void tick(){
        x += 1;
    }

    // render method to render during game loop
    private void render(){
        bs = display.getCanvas().getBufferStrategy(); // Set buffer strategy for canvas | to give a buffer
                                                      // for canvas before displaying out to screen

        // Check if bs is empty before getting buffer strategy
        if (bs == null){
            display.getCanvas().createBufferStrategy(3); // Create bs of 3 buffers
            return; // Return out of render method
        }

        g = bs.getDrawGraphics(); // Create the paintbrush

        // Before we drawing anything to screen, we must clear the screen
        g.clearRect(0, 0, width, height);

        /*-----------------------------Draw to canvas below:-----------------------------*/

        g.drawImage(Assets.grass, x, 10, null);

        /*--------------------------------End of drawing---------------------------------*/

        bs.show(); // After drawing, tell Java to show all the buffered drawing to screen
        g.dispose(); // Get rids of graphics object to save resources | since there is a
                     // set of buffered renders constantly queueing up to be sent do display
                     // on screen
    }

    // Must have a run method if we implement runnable
    // Run method is where majority of our game codes will go
    @Override
    public void run() {
        init(); // Initialise game

        /*Code below controls the refresh rate (fps) and to ensure that
            the refresh rate is constant rate regardless of computational
            hardware resources. */
        int fps = 60; // Indicates how many times under a second do we want out tick() method to run
        double timePerTick = 1000000000 / fps; // 1 bil nanoSeconds(1 sec) / fps = number of times we want to run tick()
                                               // method under a second/ 1 bil nanoSeconds.
        double delta = 0; // delta is the rate of change
        long now;
        long lastTime = System.nanoTime(); // Get current time of computer in nanoSeconds
        // A. [optional] fps counter in System.out to see refresh rate
        long timer = 0; // A.
        int ticks = 0; // A.

        /* Game loop: 1 -> 2 -> 1 and repeat
            1. Update all variables, positions of objects ,etc -> tick();
            2. Render(draw) everything to screen -> render(); */
        while (running){
            now = System.nanoTime(); // Get current time of computer in nanoSeconds
            delta += (now - lastTime) / timePerTick; // amount of time passed since we last called this line of code
                                                     // divided by timePerTick =  How much time we have until we call
                                                     // the tick() and render() methods again.

            timer += now - lastTime; // [Optional] this is to see refresh rate in System.out

            lastTime = now; // Update lastTime to latest time

            // Now we use the delta as a counter to control the refresh rate to be 60 fps
            if (delta >= 1){
                tick(); // Step 1.
                render(); // Step 2.
                ticks++; // [Optional] this is to see refresh rate in System.out
                delta--; // After tick() and render(), we have to subtract delta by 1
            }

            // [Optional] Check if timer has been running for 1 sec (1 bil nanoSecond)
            if (timer >= 1000000000){
                System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }

        }

        stop(); // Stop threading once game loop terminates
    }


    // Synchronized method to ensure that only one thread can access
    // the resource at a given point of time
    public synchronized void start(){
        // If game is already running then we return out of method
        if (running)
            return;
        // Else we proceed to run the game
        running = true; // To enable game loop to run
        thread = new Thread(this); // Pass in this==Game instance to initialize thread
        thread.start(); // Start thread, thread will call the run() method
    }

    public synchronized void stop(){
        // If game is not running then we return out of method
        if (!running)
            return;
        // Else we process to stop the game
        running = false; // To enable game loop to stop
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
