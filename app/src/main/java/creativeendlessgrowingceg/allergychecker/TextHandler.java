package creativeendlessgrowingceg.allergychecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-01-23
 */

class TextHandler {
    private static final TextHandler ourInstance = new TextHandler();

    public static TextHandler getInstance() {
        return ourInstance;
    }

    private TextHandler() {
    }
    static String capitalLetter(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
    static String capitalLetter(int integer, Context context){
        String string = context.getString(integer);
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
    static String cutFirstWord(String string) {
        List<String> list = null;
        if (string.contains(",")) {
            list = Arrays.asList(string.split(","));

        }
        if (list == null) {
            return string;
        }
        return list.get(0);
    }

    static List<String> split(String string) {
        List<String> list = null;
        if (string.contains(",")) {
            list = Arrays.asList(string.split(","));

        }
        if (list == null) {
            list = new ArrayList<>();
            list.add(string);
            return list;
        }
        return list;
    }
}
