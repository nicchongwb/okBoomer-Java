package interfaces;

import states.GameState;
import states.MenuState;
import states.State;
import okBoomer.Game;

public interface Jukebox {

    static void playMusic(){
        if (State.getState() != null){
            if (Game.toPlay){
                if (State.getState() instanceof MenuState){
                    State.stateMusic.stop();
                    State.setMusic("/res/audio/bomberman1_menu.wav");
                }
                else if (State.getState() instanceof GameState){
                    State.stateMusic.stop();
                    State.setMusic("/res/audio/Invincible2.wav");
                }
            }
            else{
                State.stateMusic.stop();
                State.setMusic("/res/audio/bomberman1_menu.wav");
            }
        }

    }
}
