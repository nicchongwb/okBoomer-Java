package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* Utils class is a utility class consisting of multiple helper functions
*  that can help us with our game
*
*  Usage: called by World.java to load world information (tiles) from text file.
* */

public class Utils {

    public static String loadFileAsString(String path){
        StringBuilder builder = new StringBuilder();

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while((line = br.readLine()) != null)
                builder.append(line + "\n");

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return builder.toString();
    }

    // converts String into Integer
    public static int parseInt(String number){
        try{
            return Integer.parseInt(number);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

}
