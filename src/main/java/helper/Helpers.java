package helper;

import java.net.URL;
import java.util.Random;

public class Helpers {

    public static URL getResource(String name){
        return Helpers.class.getClassLoader().getResource(name);
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static int sortDoubleAsc(double el1, double el2){
        if (el1 > el2)
            return 1;
        else if (el1 < el2)
            return -1;
        else {
            return 0;
        }
    }

    public static int sortDoubleDesc(double el1, double el2){
        if (el1 < el2)
            return 1;
        else if (el1 > el2)
            return -1;
        else {
            return 0;
        }
    }
}
