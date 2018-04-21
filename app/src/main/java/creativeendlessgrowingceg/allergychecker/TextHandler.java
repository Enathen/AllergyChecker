package creativeendlessgrowingceg.allergychecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * takes care of text.
 * @author Jonathan Alexander Norberg
 * @version 2018-01-23
 */

public class TextHandler {
    private static final TextHandler ourInstance = new TextHandler();
    private static final String TAG = "TextHandler";

    public static TextHandler getInstance() {
        return ourInstance;
    }

    private TextHandler() {
    }
    public static String capitalLetter(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
    public static String capitalLetter(int integer, Context context){
        String string = context.getString(integer);
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
    public static String cutFirstWord(String string) {
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

    public static String FixText(String str) {
        str = str.replaceAll("\\("," ");
        str = str.replaceAll("\\)"," ");
        str = str.replaceAll("[^\\p{L}\\p{Nd}\\s]+", "");
        //suggestions.setText(str);
        str = str.toLowerCase();
        return str;
    }
    public static String splitUpString(String string, int amountOfCharToRemove){
        amountOfCharToRemove--;
        String strings = string;
        for (int i = 0; i < string.length(); i++) {
            StringBuilder sb = new StringBuilder(string);
            String s = String.valueOf(sb.deleteCharAt(i));
            if(strings.isEmpty()){
                strings = strings.concat(s);
            }else{
                strings = strings.concat(" " + s);

            }
            //Log.d(TAG, "SPLUT: "+ strings);
            if(amountOfCharToRemove!=0){

                strings = strings.concat(" " + splitUpString(s, amountOfCharToRemove));

            }

        }
        return strings;
    }
}
