package okBoomer;

import display.Display;

/* Game class, not to be mistaken with Launcher class
This class will be the main class of our game that holds all of our
base code of our game.
- Start everything, Close everything and close our game
- Game class will implement Runnable to make it run on a thread
*/

public class Game implements Runnable{
    // Declare local variables
    private Display display; // To give our Game class a Display

    public int width, height; // Easier to access width,height used
    public String title;

    private boolean running = false; // Running variable for game loop run()
    private Thread thread; // Thread object for us to run the game on

    // Constructos
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;

    }

    // Other methods

    // init method to initialise assets in our game before we process to run our game
    private void init(){
        display = new Display(title, width, height); // Initialise Display object for Game instance
    }

    // tick method a.k.a update all game variables, positions of objects, etc
    // during game loop
    private void tick(){

    }

    // render method to render during game loop
    private void render(){

    }

    // Must have a run method if we implement runnable
    // Run method is where majority of our game codes will go
    @Override
    public void run() {
        init();

        /* Game loop: 1 -> 2 -> 1 and repeat
        1. Update all variables, positions of objects ,etc
        2. Render(draw) everything to screen
         */
        while (running){
            tick(); // Step 1.
            render(); // Step 2.
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
