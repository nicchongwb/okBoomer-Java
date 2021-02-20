package states;

import interfaces.Jukebox;
import okBoomer.Game;

import okBoomer.Handler;


import java.awt.*;

public class EndState extends State {

    public EndState(Handler handler){
        super(handler);
        handler.getMouseManager().setUIManager(Game.uiManager);
        Jukebox.stopMusic();
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
