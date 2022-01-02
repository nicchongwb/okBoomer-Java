package utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/* ItemTimer class called by GameState to calculate rate of spawning items
*/
public class ItemTimer extends TimerTask{

    private boolean hasStarted = false; // check if timer has started
    private boolean rdyToSpawn = false; // check if timer has ended

    private int spawnRate;
    private int period = 1000;

    public ItemTimer(){
        startTimer();
    }

    // method to start timer
    public void startTimer(){
        this.hasStarted = true;

        // set spawn rate between 3 seconds to 5 seconds
        spawnRate = ThreadLocalRandom.current().nextInt(3, 6);
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            int i = spawnRate;
            public void run() {
                i--;
                if (i < 1) {
                    timer.cancel();
                    rdyToSpawn = true; // once timer has ended, next item is ready to spawn
                }
            }
        }, 0, period);
    }
    @Override
    public void run(){

    }
    public boolean hasRunStarted() {
        return this.hasStarted;
    }

    public boolean readyToSpawn() {
        return this.rdyToSpawn;
    }

    /* Getters and Setters */
    public void setRdyToSpawn(boolean value) {
        this.rdyToSpawn = value;
    }

    public void sethasRunStarted(boolean value) {
        this.hasStarted = value;
    }
}
