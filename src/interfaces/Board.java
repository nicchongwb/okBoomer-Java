package interfaces;

import entities.creatures.Player;
import entities.items.BombCollectable;

import static states.GameState.*;

/* This is board interface. This will give us the needed methods to control the entire board[][] in GameState class
This will be implemented by GameState and Player class
Player will call Board.canPlayerMove() to update the board
Board will call Board.checkPrevandUpdateBoard() inside the canPlayerMove() */

public interface Board {
    /* Method to determine if player can move */
    static boolean canPlayerMove(int pid, int prevX, int prevY, int newX, int newY, Player targetPlayer){

        /* Logic flow:
                1. Check if TID new is collidable or non-collidable tile.
                2. If it is a non-collidable tile, update actions accordingly, and check previous tile
                2.1 If previous tile is 0, 3, 4, then let player move and update board accordingly
                2.2 If previous tile is 5, 6, then also let player move and update board accordingly (change prev tile to bomb)
                3. If it is a collidable tile or world border, do not let player move.
        */

        /* 2D array Board usage:
            0 = empty, no entity occupying
            1 = player1 occupying
            2 = player2 occupying
            3 = bomb (planted) occupying
            4 = bomb (collectable) occupying
            5 = bomb (planted) + player 1 occupying (bomb is planted by p1)
            6 = bomb (planted) + player 2 occupying (bomb is planted by p2)
        */

        // Get static variables from GameState
        int maxWorldY = getMaxWorldY();
        int minWorldY = getMinWorldY();
        int maxWorldX = getMaxWorldX();
        int minWorldX = getMinWorldX();

        prevX = prevX/64;
        prevY = prevY/64;
        newX = newX/64;
        newY = newY/64;


        // if newX and newY is more than world edges, do not let player move
        if((newY > (maxWorldY)) || newY <= minWorldY){
            return false;
        }
        else if ((newX > (maxWorldX)) || newX <= minWorldX){
            return false;
        }
        else{

            // sometimes, when both players move TOO fast (press keyboard too fast), there may
            // be latency issues with the coordinates. This piece of if-else code is to
            // prevent the coordinates from updating wrongly ;)
            if(newX - prevX == 0){
                if(newY - prevY == 1 || newY - prevY == -1);
                else{
                    return false;
                }
            } else if(newY - prevY == 0){
                if(newX - prevX == 1 || newX - prevX == -1);
                else{
                    return false;
                }
            } else {
                return false;
            }

            // get the tile id of the next tile the player is going to step on
            // and get the tild id of the previous tile the player was stepping on
            int tidPrev = getTileId(prevX,prevY);
            int tidNew = getTileId(newX, newY);

            switch (tidNew){

                // if next tile is empty [non-collidable tile]
                case 0:
                    Board.checkPrevandUpdateBoard(tidPrev, pid, prevX, prevY, newX, newY);
                    return true; // let player move

                // if next tile is bomb [non-collidable tile]
                case 3:
                    Board.checkPrevandUpdateBoard(tidPrev, pid, prevX, prevY, newX, newY);

                    // Remove bombPart from ArrayList bombList so that it does not render
                    bombPlayer(targetPlayer);
                    targetPlayer.setBombed();
                    return true;

                // once player picks up bomb [non-collidable tile]
                case 4:

                    /* Remove bombCollectable from bombList */
                    for (BombCollectable bombCol : bombList){
                        if (bombCol.getX() == newX*64 && bombCol.getY() == newY*64) {
                            bombList.remove(bombCol);
                            break;
                        }
                    }

                    Board.checkPrevandUpdateBoard(tidPrev, pid, prevX, prevY, newX, newY);
                    collectBombPart(targetPlayer);
                    return true;

                // if next tile is player + bomb [collidable tile]
                // if next tile is player (and any other collidable tiles)
                default:
                    return false;

            }

        }
    }

    /* Method to check if the previous tile is collidable so that we can decide how to update the 2d board array*/
    static void checkPrevandUpdateBoard(int tidPrev, int pid, int prevX, int prevY, int newX, int newY){

        /*  1. Target index(s) of 2D board array where player resides, make it empty
            2. Target index(x) of 2D board array based on player's current X, Y and update it
        */

        switch (tidPrev){

            // for cases 5 and 6, change previous tile to 3 (planted bomb).
            case 5:
            case 6:
                board[prevY][prevX] = 3; // Step 1
                board[newY][newX] = pid+1; // Step 2

                System.out.printf("Board: P%d: PrevXY: [%d][%d] = %d%n" +
                                "Board: P%d: CurrXY: [%d][%d] = %d%n%n",
                        pid+1, prevY, prevX, board[prevY][prevX], pid+1, newY, newX, board[newY][newX]);
                break;

            // for cases 0 to 4, change previous tile to 0 (empty tile)
            default:
                board[prevY][prevX] = 0; // Step 1
                board[newY][newX] = pid+1; // Step 2

                System.out.printf("Board: P%d: PrevXY: [%d][%d] = %d%n" +
                                "Board: P%d: CurrXY: [%d][%d] = %d%n%n",
                        pid+1, prevY, prevX, board[prevY][prevX], pid+1, newY, newX, board[newY][newX]);

                break;
        }
    }
}