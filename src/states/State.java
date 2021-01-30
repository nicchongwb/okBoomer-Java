package states;

import okBoomer.Game;

import java.awt.Graphics;

/* This class is an abstract class to hold the necessary characteristics of what
any state must have.
States can be utilised in games to separate different states.
eg. Main menu state, game setting stats, actual game state
Having a state class helps us manage the flow of the game application and also
helps us in defining different states of game entities (eg. Run, Walk, Fly).
Helps us to defining rules in the transition of states
    - eg. Main Menu -> Game -> Pause Menu -> exit game -> Main Menu

<------------------This class Contains State Manager logic--------------------->
USAGE:
    1. eg. create GameState class under states package and have it extend State class
    2. in Game class, create private State gameState
    3. in Game class init() -> gameState = new GameState(); // This is polymorphism
                                       // where State object can take the form of
                                       // its subclasses, in this case, gameState.
    4. Game class init() -> State.setState(gameState);
*/



import java.awt.*;

public abstract class State {

    // State Manager logic | utilises polymorphism concept
    private static State currentState = null; // Hold what state we want to hold in our game

    // Game State
    protected Game game; // This is to unify the game instance between different states
    // Constructor to take in game instance
    public State(Game game){
        this.game = game;
    }

    public static void setCurrentState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }
    // End of State Manager

    // Class methods
    public abstract void tick();
    public abstract void render(Graphics g);

    // Methods for scoreboard
    public abstract int getP1Health();
    public abstract int getP2Health();

}
