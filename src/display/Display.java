package display;

import javax.swing.*;
import java.awt.*;

// Display class to create window display/etc in Launcher
public class Display {
    // Declare local variables
    private JFrame frame; // JFrame for window display of application
    private Canvas canvas; // Canvas object let us draw graphics(sprite,rect,etc) on our window/JFrame in this case

    private String title;
    private int width, height;

    // Constructor
    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay(); // Create Display/ Initialise JFrame
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

        // KeyManager Input
        canvas.setFocusable(false); // So that JFrame will focus on itself

        // Final Step to Add canvas,etc onto our JFrame
        frame.add(canvas);
        frame.pack(); // Resize frame so that we can see all of the canvas without issues/ cutoffs

    }

    // Getters
    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }
}
