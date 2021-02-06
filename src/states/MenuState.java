package states;

import gfx.Assets;
import gfx.UIImageButton;
import gfx.UIManager;

import okBoomer.ClickListener;
import okBoomer.Game;

import okBoomer.Handler;
import worlds.World;


import java.awt.*;


public class MenuState extends State {
    private UIManager uiManager;

    public MenuState(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(200,200,256,128, Assets.btn_start, new ClickListener(){
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                State.setCurrentState(handler.getGame().gameState);
            }
        }));

        }



//    public MenuState(Handler handler){
//        super(handler);
//        uiManager = new UIManager(handler);
//        uiManager.addObject(new UIImageButton(200,200,128,64, Assets.btn_start, new ClickListener(){
//            @Override
//            public void onClick() {
//                game.start();
//            }
//        }));
//
//    }

    @Override
    public void tick() {
        // Insert logic to update all variables related to Menu
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw Methods to draw out menu

        uiManager.render(g);
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
