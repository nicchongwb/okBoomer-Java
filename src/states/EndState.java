package states;

import gfx.Assets;
import gfx.UIImageButton;
import gfx.UIManager;

import interfaces.Jukebox;
import okBoomer.ClickListener;
import okBoomer.Game;

import okBoomer.Handler;
import worlds.World;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EndState extends State {

    public EndState(Handler handler){
        super(handler);
        handler.getMouseManager().setUIManager(Game.uiManager);
        Jukebox.stopMusic();
        Jukebox.playMusic("/res/audio/bomberman1_menu.wav");
    }

    @Override
    public void tick() {
        // Insert logic to update all variables related to Menu
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw Methods to draw out menu
        Game.uiManager.render(g);
    }

    /*--------------------------Ignore this function, this serves no purpose in this class----------------------------*/
    @Override
    public int getP1Health(){
        return 0;
    }

    @Override
    public int getP2Health(){
        return 0;
    }

    @Override
    // Update Inventory Methods
    public int getP1BombHeld(){
        return 0;
    }

    @Override
    public int getP2BombHeld(){
        return 0;
    }

    @Override
    public int getP1BombPart(){
        return 0;
    }

    @Override
    public int getP2BombPart(){
        return 0;
    }
    /*----------------------------------------------End of ignored----------------------------------------------------*/
}
