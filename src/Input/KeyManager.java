package Input;

import entities.creatures.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* Purpose of this class is to manage user key inputs to drive the game
KeyListener API is a class for Java to access the keyboard
This class is used in the following classes:
Display, Game, Player
*/

public class KeyManager implements KeyListener {
    // Declare variables
    private boolean[] keys;

    // Player 1 up, down, left, right
    public boolean p1Up, p1Down, p1Left, p1Right;
    // Player 2 up, down, left, right
    public boolean p2Up, p2Down, p2Left, p2Right;


    // Constructor
    public KeyManager(){
        keys = new boolean[256]; // Eg. A is ID123, then when it is pressed, we
                                 // set keys[123] = true;
    }

    // Update key variables method, always run during game loop
    public void tick(){
        // Update player 1 keys
        p1Up = keys[KeyEvent.VK_W];
        p1Down = keys[KeyEvent.VK_S];
        p1Left = keys[KeyEvent.VK_A];
        p1Right = keys[KeyEvent.VK_D];

        // Update player 2 keys
        p2Up = keys[KeyEvent.VK_UP];
        p2Down = keys[KeyEvent.VK_DOWN];
        p2Left = keys[KeyEvent.VK_LEFT];
        p2Right = keys[KeyEvent.VK_RIGHT];

    }

    // KeyPressed is called whenever user presses a key
    @Override
    public void keyPressed(KeyEvent e) {

        // Check if key is previously pressed
        if (keys[e.getKeyCode()] != true){
            keys[e.getKeyCode()] = true; // Update boolean Array
            // Debug print statement to see if key is pressed
            //System.out.println("Key is pressed");
        }
        else {
            keys[e.getKeyCode()] = false;
        }
    }

    // KeyReleased is called whenever user releases a key
    @Override
    public void keyReleased(KeyEvent e) {

        //System.out.println("Key is released");

        // check if the key released was player1's key or player2's key

        // player 1's keys
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_A ||
            e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_D){

            // set variable for checking if key is already pressed to false.
            if(Player.getIfPressed1()){
                Player.setIfPressed1(false);
            }
        }

        // player 2's keys
        if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN ||
                e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT){
            
            // set variable for checking if key is already pressed to false.
            if(Player.getIfPressed2()){
                Player.setIfPressed2(false);
            }
        }

        keys[e.getKeyCode()] = false; // Update boolean Array

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
