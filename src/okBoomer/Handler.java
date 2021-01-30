package okBoomer;

import Input.KeyManager;
import gfx.MouseManager;

public class Handler {
    private Game game;
    public Handler(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }
    public MouseManager getMouseManager(){
        return game.getMouseManager();
    }
}
