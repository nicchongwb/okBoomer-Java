package entities.items;

import gfx.Assets;
import okBoomer.Handler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bomb extends Item implements ActionListener{
    private int damage;  // Default bomb damage
    private static final int DEFAULT_DAMAGE = 1;
    private static int bombID = 0;  // To identify the respective planted bomb
    private BufferedImage bombImg;  // Image of the bomb

    private ArrayList<Bomb> plantBombList  = new ArrayList<Bomb>();  // Arraylist to store all the planted bombs currently on the board

    // Timer
    private float alpha = 1f;
    private Timer timer;
    private final int DELAY = 30;
    private final int INITIAL_DELAY = 300;

    public Bomb(Handler handler, int x, int y){
        super(handler, x, y, Item.DEFAULT_ITEM_WIDTH, Item.DEFAULT_ITEM_HEIGHT);
        this.handler = handler; // Help us access KeyManager
        this.damage = DEFAULT_DAMAGE;
        bombID += 1;
        bombImg = Assets.BombTile;
        initTimer();
    }

    // Getter and Setter
    public int getDamage() {
        return damage;
    }

    public void setBombID(int bombID){
        this.bombID = bombID;
    }

    private void setDamage(int damage) {
        this.damage = damage;
    }

    public int getBombID(){
        return bombID;
    }

    // Get the planted bomb objects on the board
    public ArrayList<Bomb> getPlantedBombList(){
        return this.plantBombList;
    }

    // Method for timer when a bomb is planted
    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

    private void endTimer(){
        alpha += -0.01f;

        if (alpha <= 0) {

            alpha = 0;
            timer.stop();
        }
    }

    @Override
    public void tick() {
        endTimer();
    }

    @Override
    public void render(Graphics g) {
        drawImage(g);
    }

    // To draw the bomb
    private void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        // set transparency/ fading effect
        AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(acomp);
        g2d.drawImage(bombImg, x, y, width, height, null);

        g2d.dispose();
    }

    // Listener to update the graphics
    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
        repaint();
    }
}