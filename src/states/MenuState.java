package states;

import okBoomer.Game;

import java.awt.*;

public class MenuState extends State {
    public MenuState(Game game){
        super(game);
    }

    @Override
    public void tick() {
        // Insert logic to update all variables related to Menu
    }

    @Override
    public void render(Graphics g) {
        // Insert g.draw Methods to draw out menu
    }

    // Ignore this function, this serves no purpose in this class
    @Override
    public int getP1Health(){
        return 0;
    }

    @Override
    public int getP2Health(){
        return 0;
    }
}
