package display;

import gfx.Assets;
import states.EndState;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

// Display class to create window display/etc in Launcher
public class Display{
    // Declare local variables
    private JFrame frame; // JFrame for window display of application
    private Canvas canvas; // Canvas object let us draw graphics(sprite,rect,etc) on our window/JFrame in this case
    private InventoryDisplay inventory; // JPanel for Inventory
    private ScoreboardDisplay scoreboard; // JPanel for Scoreboard

    private String title;
    private int width, height;

    private int counter = 0;

    //static JTextField player1_input;
    //static JTextField player2_input;
    private static String p1Name_input, p2Name_input;



    // Variables for Polygon drawing
    int LeftBgX[] = {0, 352, 288, 0};
    int LeftBgY[] = {0, 0, 60, 60};
    int RightBgX[] = {352, 640, 640, 288};
    int RightBgY[] = {0, 0, 60, 60};
    Color cyanColor = new Color(0x0da595);
    Color brownColor = new Color(0xe1c672);

    // Constructor
    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        // create inventory panel
        inventory = new InventoryDisplay();
        inventory.setPreferredSize(new Dimension(640, 60));
        JLabel invText = new JLabel("Inventory");
        inventory.add(invText);

        // create scoreboard
        scoreboard = new ScoreboardDisplay();
        //scoreboard.setLayout(new BorderLayout()); //uncomment to use borderlayout
        scoreboard.setPreferredSize(new Dimension(640,60));
        JLabel scoreText = new JLabel("Scoreboard");
        scoreboard.add(scoreText);

        //background
        Image img = Toolkit.getDefaultToolkit().getImage("E:\\rahul.jpg");

        createDisplay(); // Create Display/ Initialise JFrame
    }

    // Display Static Getter and Setters
    public static void setP1Name(String name){
        p1Name_input = name;
    }

    public static void setP2Name(String name){
        p2Name_input = name;
    }

    public static String getP1Name(){
        return p1Name_input;
    }

    public static String getP2Name(){
        return p2Name_input;
    }

    // Other methods
    private void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure process is killed upon closing
        frame.setResizable(false); // Disable window resize
        frame.setLocationRelativeTo(null); // Window will start in middle
        frame.setVisible(true); // Window to appear on screen

        // Initialise canvas object
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height)); // Set canvas size according to this.width,height
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height)); // Ensure that canvas always stay within the width and height

        canvas.setBackground(Color.BLACK);

        // KeyManager Input
        canvas.setFocusable(false); // So that JFrame will focus on itself

        // Final Step to Add canvas,etc onto our JFrame
        frame.add(canvas);
        frame.pack(); // Resize frame so that we can see all of the canvas without issues/ cutoffs

    }

    // Method to add inventory and scoreboard to the canvas
    public void createInvScore(){
        frame.add(this.scoreboard, BorderLayout.NORTH);
        frame.add(this.inventory, BorderLayout.SOUTH);
        frame.pack();
    }

    public void clearDisplay(){
        scoreboard.setVisible(false);
        inventory.setVisible(false);
        frame.pack();

    }


    public void returnDisplay(){
        scoreboard.setVisible(true);
        inventory.setVisible(true);
    }

    // Jpanel for inventory display
    class InventoryDisplay extends JPanel{
        // Variables for Inventory
        private int p1BombHeld, p2BombHeld;
        private int p1BombPart, p2BombPart;

        // Constructor
        public InventoryDisplay() {
            p1BombHeld = 0;
            p2BombHeld = 0;
            p1BombPart = 0;
            p2BombPart = 0;
            setFocusable(false);  // so that this can receive key-events
            requestFocus();
        }

        // Override paintComponent to do custom drawing.
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;  // if using Java 2D
            super.paintComponent(g2d);       // paint background
            setBackground(Color.LIGHT_GRAY);      // may use an image for background
        }

        // Paint function to update scoreboard. This is called via repaint()
        @Override
        public void paint(Graphics g){
            g.clearRect(0, 0, 640, 60);

            Graphics2D g2d = (Graphics2D)g;  // if using Java 2D
            super.paintComponent(g2d);       // paint background
            setBackground(Color.ORANGE);      // may use an image for background

            g.setColor(cyanColor);
            g.fillPolygon(LeftBgX, LeftBgY, 4);
            g.setColor(brownColor);
            g.fillPolygon(RightBgX, RightBgY, 4);

            // Bomb(s) icon:
            g.drawImage(Assets.BombPart, 32,15 ,null);
            g.drawImage(Assets.BombPart, 576,15 ,null);

            g.setColor(Color.WHITE);
            ((Graphics2D) g).setStroke(new BasicStroke(1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g.drawLine(352, 0, 288, 60);

            // Player(s) information includes: playerID/name, health
            g.setFont(new Font("SansSerif ", Font.BOLD, 13));
            g.drawString("Bombs: " + String.valueOf(p1BombHeld) + "/3", 76, 30);
            g.drawString("Bomb Parts: " + String.valueOf(p1BombPart) + "/2", 76, 45);


            g.drawString("Bombs: " + String.valueOf(p2BombHeld) + "/3", 464, 30);
            g.drawString("Bombs Parts: " + String.valueOf(p2BombPart) + "/2", 464, 45);


            // To see if inventory is updating
            //g.drawString(String.valueOf(counter), 300, 45);
            counter += 1;

        }

        // Getter and Setter
        public int getP1BombHeld() {
            return p1BombHeld;
        }

        public void setP1BombHeld(int p1BombHeld) {
            this.p1BombHeld = p1BombHeld;
        }

        public int getP2BombHeld() {
            return p2BombHeld;
        }

        public void setP2BombHeld(int p2BombHeld) {
            this.p2BombHeld = p2BombHeld;
        }

        public int getP1BombPart() {
            return p1BombPart;
        }

        public void setP1BombPart(int p1BombPart) {
            this.p1BombPart = p1BombPart;
        }

        public int getP2BombPart() {
            return p2BombPart;
        }

        public void setP2BombPart(int p2BombPart) {
            this.p2BombPart = p2BombPart;
        }

    }


    // Jpanel for scoreboard display
    class ScoreboardDisplay extends JPanel{
        // Variables for Scoreboard
        private int p1Health, p2Health;

        // Constructor
        public ScoreboardDisplay() {
            p1Health = 0;
            p2Health = 0;
        }

        // Override paintComponent to do custom drawing.
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D)g;  // if using Java 2D
            super.paintComponent(g2d);       // paint background
            setBackground(Color.ORANGE);      // may use an image for background

        }

        // Paint function to update scoreboard. This is called via repaint()
        @Override
        public void paint(Graphics g){
            g.clearRect(0, 0, 640, 60);

            Graphics2D g2d = (Graphics2D)g;  // if using Java 2D
            super.paintComponent(g2d);       // paint background
            setBackground(Color.ORANGE);      // may use an image for background

            // Background for scoreboard

            g.setColor(cyanColor);
            g.fillPolygon(LeftBgX, LeftBgY, 4);
            g.setColor(brownColor);
            g.fillPolygon(RightBgX, RightBgY, 4);

            g.setColor(Color.WHITE);
            ((Graphics2D) g).setStroke(new BasicStroke(1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            g.drawLine(352, 0, 288, 60);

            // Player(s) Avatar:
            g.drawImage(Assets.player1_down[1], 32,15 ,null);
            g.drawImage(Assets.player2_down[1], 576,15 ,null);

            // Player(s) information includes: playerID/name, health
            g.setFont(new Font("SansSerif ", Font.BOLD, 13));
            g.drawString(getP1Name(), 76, 30);
            g.drawString("Health: " + String.valueOf(p1Health) + "/10", 76, 45);

            g.drawString(getP2Name(), 480, 30);
            g.drawString("Health: " + String.valueOf(p2Health) + "/10", 480, 45);

            // To see if scoreboard is updating
            //g.drawString(String.valueOf(counter), 300, 45);
            counter += 1;

        }

        // Getter and Setter
        public int getP1Health() {
            return p1Health;
        }

        public void setP1Health(int p1Health) {
            this.p1Health = p1Health;
        }

        public int getP2Health() {
            return p2Health;
        }

        public void setP2Health(int p2Health) {
            this.p2Health = p2Health;
        }

    }

    // Getters
    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }

    public ScoreboardDisplay getScoreboard() {
        return scoreboard;
    }

    public InventoryDisplay getInventory(){
        return inventory;
    }

    public void updateScoreboard(int p1Health, int p2Health) {
        this.scoreboard.setP1Health(p1Health);
        this.scoreboard.setP2Health(p2Health);
        this.scoreboard.repaint(); // calls the paint() in scoreboard
    }

    public void updateInventory(int p1BombHeld, int p2BombHeld, int p1BombPart, int p2BombPart){
        this.inventory.setP1BombPart(p1BombPart);
        this.inventory.setP2BombPart(p2BombPart);
        this.inventory.setP1BombHeld(p1BombHeld);
        this.inventory.setP2BombHeld(p2BombHeld);
        this.inventory.repaint(); // calls the paint() in inventory
    }
}
