package gfx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// Class to manage the playing of game sounds!

public class AudioPlayer {

    private Clip clip;

    public AudioPlayer(String music) {

        try {

            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(music));

            AudioFormat baseFormat = audio.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream openclip = AudioSystem.getAudioInputStream(decodeFormat, audio);
            clip = AudioSystem.getClip();
            clip.open(openclip);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void play(){
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY); // so audio can keep looping
        //Thread.sleep(10000);
    }

    public void playonce(){ //to play sound effect once only
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();

    }

    public void stop() { //stop audio if it is running
        if(clip.isRunning()) clip.stop();
    }

    public void close() {
        stop();
        clip.close();
    }

}