package creativeendlessgrowingceg.allergychecker;

import android.content.Context;

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
}
