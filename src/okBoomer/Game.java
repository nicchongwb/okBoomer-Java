package okBoomer;

import Input.KeyManager;
import Input.MouseManager;
import display.Display;
import entities.creatures.Player;
import gfx.*;
import interfaces.Jukebox;
import interfaces.StateManager;
import sfx.AudioPlayer;
import states.*;
import states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static display.Display.getP1Name;
import static display.Display.getP2Name;

/* Game class, not to be mistaken with Launcher class
This class will be the main class of our game that holds all of our
base code of our game.
- Start everything, Close everything and close our game
- Game class will implement Runnable to make it run on a thread
*/

public class Game implements Runnable, StateManager {
    // Declare local variables
    private Display display; // To give our Game class a Display
    public static UIManager uiManager;

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

    // States
    public State gameState;
    public State menuState;
    public State endState;

    // Input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    public boolean playAgain = false;
    public String whoDied;

    //Handler
    private Handler handler;

    // Play bomb sound
    private static AudioPlayer bombsound;

    // Jukebox control variables
    public static boolean toPlay = true;

    // Constructors
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    //
    public String player1_win_lose = "";
    public String player2_win_lose = "";
    public boolean player1_winORlose;
    public boolean player2_winORlose;


    // Other methods

    // The init method to initialise assets in our game before we process to run our game
    private void init(){
        display = new Display(title, width, height); // Initialise Display object for Game instance
        display.getFrame().addKeyListener(keyManager); // Add keyListener to JFrame so that GUI updates based on key input
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        Assets.init(); // Load in all of our assets

        handler = new Handler(this);
        uiManager = new UIManager(handler);

        // States | Initialise states and set desired current state
        gameState = new GameState(handler); // Pass in this current instance of game class
        menuState = new MenuState(handler); // Pass in this current instance of game class
        endState = new EndState(handler);
        State.setCurrentState(menuState); // Set State to MenuState for the first launch
        Jukebox.stopMusic(); // Stop any music if playing in the case of replay game
        Jukebox.playMusic("/res/audio/bomberman1_menu.wav");
    }

    // The tick method a.k.a update all game variables, positions of objects, etc
    // during game loop.
    private void tick(){
        // Update KeyManager
        keyManager.tick();


        // State | if any state exist then we call the currentState's tick function
        if (State.getState() != null){
            State.getState().tick();
            // Debuggin block
            if (State.getState() instanceof GameState){
                display.returnDisplay();
                //System.out.println("Game.tick() GameState");
            }
            else if (State.getState() instanceof MenuState){
                //System.out.println("Game.tick() MenuState");
            }
            else if (State.getState() instanceof EndState){

                //System.out.println("Game.tick() EndState");
            }
        }
    }

    // render method to render during game loop
    private void render(){
        // Initialise inventory and scoreboard display if State is GameState
        if (State.getState() instanceof GameState){
            this.display.createInvScore();
        }


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

        // State | if any state exist then we pass in g to state to render
        // respective state
        if (State.getState() != null) {
            if (State.getState() instanceof MenuState) {

                g.drawImage(Toolkit.getDefaultToolkit().getImage("src/res/sprites/background.png"), 0, 0, null);
                g.drawImage(Toolkit.getDefaultToolkit().getImage("src/res/sprites/title.png"), 80, -60, null);


                State.getState().render(g);


            }if (State.getState() instanceof EndState) {

                display.clearDisplay();
                g.drawImage(Toolkit.getDefaultToolkit().getImage("src/res/sprites/background.png"), 0, 0, null);
                g.drawImage(Toolkit.getDefaultToolkit().getImage("src/res/sprites/gameover.png"), 80, -100, null);

                State.getState().render(g);

                Font fnt0 = new Font("arial", Font.BOLD, 50);
                g.setFont(fnt0);
                g.setColor(Color.green);

                //Print out winner's name
                String player1_wins = getP1Name() + " wins";
                String player2_wins = getP2Name() + " wins";

                //Centralise text
                FontMetrics metrics = g.getFontMetrics(fnt0);
                int x1 = 0 + (this.width - metrics.stringWidth(player1_wins)) / 2;
                int x2 = 0 + (this.width - metrics.stringWidth(player2_wins)) / 2;

                //Display on screen
                if(whoDied == "p1"){
                    g.drawString(player2_wins, x2, 190);



                }else if(whoDied == "p2"){
                    g.drawString(player1_wins, x1, 190);

                }



                //When replay button is clicked, initialise new game
                if (playAgain == true) {
                    playAgain = false;
                    Assets.init();
                    handler = new Handler(this);
                    gameState = new GameState(handler); // Pass in this current instance of game class
                    menuState = new MenuState(handler); // Pass in this current instance of menu class
                    menuState = new EndState(handler); // Pass in this current instance of end class

                }
            }
            else if (State.getState() instanceof GameState) {
                display.returnDisplay();
                State.getState().render(g);


                // If we are in GameState, then we update scoreboard
                //if (State.getState() instanceof GameState) {
                int p1Health = gameState.getP1Health();
                int p2Health = gameState.getP2Health();

                int p1BombHeld = gameState.getP1BombHeld();
                int p2BombHeld = gameState.getP2BombHeld();

                int p1BombPart = gameState.getP1BombPart();
                int p2BombPart = gameState.getP2BombPart();

                display.updateScoreboard(p1Health, p2Health);
                display.updateInventory(p1BombHeld, p2BombHeld, p1BombPart, p2BombPart);

                int max_length = 35;
                //int total_length = getP1Name().length() + getP2Name().length();
                String spaces = "";
                String spaces_right = "";
                String spaces_left = "";

                if(getP1Name().length() > getP2Name().length()){
                    int diff = getP1Name().length() - getP2Name().length();
                    for (int i =0; i<=diff; i++){
                        spaces_left +=" ";
                    }
                }else if (getP1Name().length() < getP2Name().length()) {
                    int diff = getP2Name().length() - getP1Name().length();
                    for (int i = 0; i <= diff; i++) {
                        spaces_right += " ";
                    }
                }

                for(int i = 0; i<max_length; i++){
                    spaces += " ";

                }

                if (p2Health == 0){
                    System.out.println("player2 die");

                    //Append to leaderboard.txt
                    String player1_win_player2_lose = getP1Name()  +spaces_left+spaces+ getP2Name() + spaces_right+ "\n";
                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter("src/states/leaderboard.txt", true));
                        out.write(player1_win_player2_lose);
                        out.close();


                    }catch(IOException e) {
                        System.out.println("Something went wrong" + e);
                    }

                    //Switch to end state
                    State.setCurrentState(endState);
                    StateManager.switchState(handler, "EndState");
                    StateManager.initUIManager(handler, uiManager);
                    playAgain = true;
                    Player.playerCount = 0;
                    whoDied = "p2";
                }else if (p1Health ==0){
                    System.out.println("player1 die");
                    String player1_lose_player2_win = getP2Name() +spaces_left+ spaces+ getP1Name()+ spaces_right+"\n";
                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter("src/states/leaderboard.txt", true));
                        out.write(player1_lose_player2_win);
                        out.close();


                    }catch(IOException e) {
                        System.out.println("Something went wrong" + e);
                    }
                    //Switch to end state
                    State.setCurrentState(endState);
                    StateManager.switchState(handler, "EndState");
                    StateManager.initUIManager(handler, uiManager);
                    playAgain = true;
                    Player.playerCount = 0;
                    whoDied = "p1";
                }
            }
        }

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
        /* ---------- MAIN CONTROL OF StateManager interface tie with UIManager Class --------*/
            /* This section is to control the music, necessary UIWindows and UIButton interactions
             for the respective state the game is in */
        StateManager.initUIManager(handler, uiManager);


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
                //System.out.println("FPS: " + ticks);
                ticks = 0;
                timer = 0;
            }

        }

        stop(); // Stop threading once game loop terminates
    }

    // KeyManager Method for players in the game
    public KeyManager getKeyManager(){
        return keyManager;
    }

    public MouseManager getMouseManager(){
        return mouseManager;
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