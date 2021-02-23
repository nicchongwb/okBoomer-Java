package interfaces;


/* StateManager Interface to manage our States in the Game.java */


import display.Display;
import gfx.Assets;
import gfx.UIImageButton;
import gfx.UIManager;
import javafx.util.Pair;
import okBoomer.Handler;
import states.EndState;
import states.MenuState;
import states.State;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface StateManager {
    /* Method to switch the State of game
    *  handler = game instance
    *  state = targetState to switch to: MenuState, GameState, EndState*/
    static void switchState(Handler handler, String state){
        if (state == "MenuState"){
            State.setCurrentState(handler.getGame().menuState);
        }
        if (state == "GameState"){
            State.setCurrentState(handler.getGame().gameState);
        }
        if (state == "EndState"){
            State.setCurrentState(handler.getGame().endState);
        }

    }

    // initMenuUI -> initialise UI elements and click for the Menu State
    static void initMenuUI(Handler handler, UIManager uiManager){
        // Add Start Button object to UIManager's ArrayList
        uiManager.addObject(new UIImageButton(200,200,256,128,Assets.btn_start, new ClickListener(){

            // Override onClick() to perform specific actions upon clicking START button
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                System.out.println("start button");
                JTextField P1Field = new JTextField(10);
                JTextField P2Field = new JTextField(10);
                JPanel myPanel = new JPanel();
                //set background color


                //add Jlabel and field for player 1
                JLabel player1_label = new JLabel("Player 1:");
                player1_label.setForeground(Color.gray);

                myPanel.add(player1_label);
                myPanel.add(P1Field);

                //add Jlabel and field for player 2
                JLabel player2_label = new JLabel("Player 2:");
                player2_label.setForeground(Color.gray);
                myPanel.add(player2_label);
                myPanel.add(P2Field);

                int result = JOptionPane.showConfirmDialog(null, myPanel,
                        "Please enter your name", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {

                    String p1fieldName = P1Field.getText();
                    String p2fieldName = P2Field.getText();
                    // Make input names to max length of 8
                    if (p1fieldName.length() > 8){
                        p1fieldName = p1fieldName.substring(0,8);
                    }
                    if (p2fieldName.length() > 8){
                        p2fieldName = p2fieldName.substring(0,8);
                    }
                    Display.setP1Name(p1fieldName);
                    Display.setP2Name(p2fieldName);
                    Jukebox.stopMusic();
                    Jukebox.playMusic("/res/audio/Invincible2.wav");
                    switchState(handler, "GameState");

                }else if(result == JOptionPane.CANCEL_OPTION){
                    handler.getMouseManager().setUIManager(uiManager);
                }
            }
        }));
        // Add History Button object to UIManager's ArrayList
        uiManager.addObject(new UIImageButton(200,350,256,128,Assets.btn_history, new ClickListener(){

            // Override onClick() to perform specific actions upon clicking START button
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                System.out.println("history button");

                String[] options = {"OK"};
                javax.swing.UIManager UI= new javax.swing.UIManager();
                UI.put("OptionPane.background",Color.black);
                UI.put("Panel.background",Color.black);
                UI.put("OptionPane.minimumSize",new Dimension(500,100));
                JScrollPane scrollpane = new JScrollPane();

                BufferedReader reader;

                String[] categories = {};

                int n = 0;
                try {
                    reader = new BufferedReader(new FileReader("src/states/leaderboard.txt"));
                    String line = reader.readLine();
                    while (line != null) {
                        // read next line
                        line = reader.readLine();

                        String newarr[] = new String[n + 1];

                        // insert the elements from old array to new array
                        for (int i = 0; i < n; i++){
                            newarr[i] = categories[i];

                        }

                        newarr[n] = line;
                        System.arraycopy(categories, 0, newarr, 0, categories.length);
                        categories = newarr;
                        n +=1;

                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String element: categories) {
                    System.out.println(element);
                }



                JList list = new JList(categories);
                list.getClass().getName();
                DefaultListCellRenderer renderer =  (DefaultListCellRenderer)list.getCellRenderer();
                renderer.setHorizontalAlignment(JLabel.CENTER);

                list.setBackground(Color.black);
                list.setForeground(Color.green);


                scrollpane = new JScrollPane(list);

                JPanel panel = new JPanel();

                scrollpane.getViewport().add(list);
                panel.add(scrollpane);
                javax.swing.ImageIcon invisible_icon = new javax.swing.ImageIcon("nothing");


                int result = JOptionPane.showOptionDialog(null, scrollpane, "History", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, invisible_icon, options , options[0]);


                if(result == 0){
                    handler.getMouseManager().setUIManager(uiManager);
                }


            }
        }));
    }

    // initEndUI -> initialise UI elements and click for the End State
    static void initEndUI(Handler handler, UIManager uiManager){

        uiManager.addObject(new UIImageButton(200,200,256,128,Assets.btn_replay, new ClickListener(){
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                switchState(handler, "GameState");
                Jukebox.stopMusic();
                Jukebox.playMusic("/res/audio/Invincible2.wav");
            }
        }));

        // Add History Button object to UIManager's ArrayList
        uiManager.addObject(new UIImageButton(200,270,256,128,Assets.btn_history, new ClickListener(){

            // Override onClick() to perform specific actions upon clicking START button
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                System.out.println("history button");

                String[] options = {"OK"};
                javax.swing.UIManager UI= new javax.swing.UIManager();
                UI.put("OptionPane.background",Color.black);
                UI.put("Panel.background",Color.black);
                UI.put("OptionPane.minimumSize",new Dimension(500,100));
                JScrollPane scrollpane = new JScrollPane();

                BufferedReader reader;

                String[] categories = {};

                int n = 0;
                try {
                    reader = new BufferedReader(new FileReader("src/states/leaderboard.txt"));
                    String line = reader.readLine();
                    while (line != null) {
                        // read next line
                        line = reader.readLine();

                        String newarr[] = new String[n + 1];

                        // insert the elements from old array to new array
                        for (int i = 0; i < n; i++){
                            newarr[i] = categories[i];

                        }

                        newarr[n] = line;
                        System.arraycopy(categories, 0, newarr, 0, categories.length);
                        categories = newarr;
                        n +=1;

                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String element: categories) {
                    System.out.println(element);
                }



                JList list = new JList(categories);
                list.getClass().getName();
                DefaultListCellRenderer renderer =  (DefaultListCellRenderer)list.getCellRenderer();
                renderer.setHorizontalAlignment(JLabel.CENTER);

                list.setBackground(Color.black);
                list.setForeground(Color.green);


                scrollpane = new JScrollPane(list);

                JPanel panel = new JPanel();

                scrollpane.getViewport().add(list);
                panel.add(scrollpane);
                javax.swing.ImageIcon invisible_icon = new javax.swing.ImageIcon("nothing");


                int result = JOptionPane.showOptionDialog(null, scrollpane, "History", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, invisible_icon, options , options[0]);


                if(result == 0){
                    handler.getMouseManager().setUIManager(uiManager);
                }


            }
        }));

        uiManager.addObject(new UIImageButton(200,400,256,128, Assets.btn_quit, new ClickListener(){
            @Override
            public void onClick() {
                handler.getMouseManager().setUIManager(null);
                Jukebox.stopMusic();
                System.exit(0);
            }
        }));
    }

    // This method will control the State of the game based on user's mouse click and keyboard input
    static void initUIManager(Handler handler, UIManager uiManager){
        //clear all buttons from objects array list
        uiManager.objects.clear();

        // Check for Current State of game and issue necessary methods
        if (State.getState() instanceof EndState) {
            initEndUI(handler, uiManager);


        }
        else if (State.getState() instanceof MenuState) {
            initMenuUI(handler, uiManager);
        }
    }


}
