package states;

import gfx.Assets;
import gfx.UIImageButton;
import gfx.UIManager;

import okBoomer.ClickListener;
import okBoomer.Game;

import okBoomer.Handler;
import worlds.World;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;






public class EndState extends State {
    private UIManager uiManager;
    public String player1_name;
    public String player2_name;




    public EndState(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        //Start audio for menu
        //State.setMusic("/res/audio/bomberman1_menu.wav");

        uiManager.addObject(new UIImageButton(200,200,256,128, Assets.btn_start, new ClickListener(){
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);

                if (State.getState() instanceof MenuState) {

                    System.out.println("start button");
                    JTextField P1Field = new JTextField(10);
                    JTextField P2Field = new JTextField(10);
                    JPanel myPanel = new JPanel();
                    myPanel.add(new JLabel("Player 1:"));
                    myPanel.add(P1Field);
                    myPanel.add(Box.createHorizontalStrut(20)); // a spacer
                    myPanel.add(new JLabel("Player 2:"));
                    myPanel.add(P2Field);

                    int result = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter your name", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {

                        System.out.println("Player 1: " + P1Field.getText());
                        System.out.println("Player 2: " + P2Field.getText());
                        player1_name = P1Field.getText();
                        player2_name = P2Field.getText();

//                        State.stateMusic.stop();
//                        State.setMusic("/res/audio/Invincible2.wav");
                        State.setCurrentState(handler.getGame().gameState);


                    }else if(result == JOptionPane.CANCEL_OPTION){
                        System.out.println("Cancel");
                        handler.getMouseManager().setUIManager(uiManager);

                    }


                }else{
//                    State.stateMusic.stop();
//                    State.setMusic("/res/audio/Invincible2.wav");
                    State.setCurrentState(handler.getGame().gameState);
                    System.out.println("play again button");
                }

            }
        }));
        uiManager.addObject(new UIImageButton(200,350,256,128, Assets.btn_quit, new ClickListener(){
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                System.out.println("quit button");

                System.exit(0);
            }
        }));

    }

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
