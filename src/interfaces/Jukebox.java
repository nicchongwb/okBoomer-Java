package interfaces;

import states.GameState;
import states.MenuState;
import states.State;
import okBoomer.Game;

// Interface to control Music change
public interface Jukebox {

    static void playMusic(String filepath){
        try{
            State.setMusic(filepath);
        } catch(Exception e){
            // error is thrown when file path names are wrong.
            System.out.println("Error loading audio file resources. Please double-check your file names before" +
                    "re-running the game!");
            System.exit(0);
        }

    }

    static void stopMusic(){
        try{
            State.stateMusic.stop();
        } catch(NullPointerException e){
            // when stopping the music, error might be thrown if there is no music currently playing.
            // hence, we catch NullPointerException
            System.out.println("Error loading stopping game music. Please double-check your file names before" +
                    "re-running the game!");
            System.exit(0);
        }

    }

}
