package interfaces;

import states.GameState;
import states.MenuState;
import states.State;
import okBoomer.Game;

// Interface to control Music change
public interface Jukebox {

    static void playMusic(String filepath){
        State.setMusic(filepath);
    }

    static void stopMusic(){
        State.stateMusic.stop();
    }

}
