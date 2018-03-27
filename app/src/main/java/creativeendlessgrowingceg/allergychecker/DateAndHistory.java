package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import creativeendlessgrowingceg.allergychecker.fragment.HistoryFragment;

/**
 * helper with date and strings
 *
 * @author Jonathan Alexander Norberg
 * @version 2018-03-04
 */

public class DateAndHistory {
    private static final String SHARED_PREFS_NAME = "StartPage";
    private static final String TAG = "DATESTRING";
    private final SharedPreferences prefs;
    ArrayList<String> dateStrings = new ArrayList<>();

    public DateAndHistory(String string, Context startPage) {
        prefs = startPage.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("sv"));
        dateStrings.add(simpleDateFormat.format(new Date()).concat(" " + string));
    }

    public DateAndHistory(Context startPage) {
        prefs = startPage.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * Save max 128 datestrings
     */
    public void saveArray() {
        dateStrings.addAll(getArray());
        Collections.sort(getArray());

        SharedPreferences.Editor mEdit1 = prefs.edit();
        Collections.sort(dateStrings, new HistoryFragment.stringComparator());
        Collections.reverse(dateStrings);
        if (dateStrings.size() > 64) {
            int sizeToMuch = dateStrings.size() - 64;
            dateStrings.subList(dateStrings.size() - sizeToMuch, dateStrings.size()).clear();

        }


        Set<String> set = new HashSet<>();
        set.addAll(dateStrings);
        mEdit1.putStringSet("list", set);
        mEdit1.commit();
    }

    /**
     * @return ArrayList of datestrings
     */
    public ArrayList<String> getArray() {

        Set<String> set = prefs.getStringSet("list", new HashSet<String>());

        return new ArrayList<>(set);
    }

    /**
     * @param key to get deleted
     */
    public void deleteOneItemHistory(String key) {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        Set<String> set = prefs.getStringSet("list", new HashSet<String>());
        set.remove(key);
        mEdit1.remove("list");
        mEdit1.commit();
        mEdit1.putStringSet("list", set);
        mEdit1.commit();


    }

    /**
     * delete full history
     */
    public void deleteHistory() {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        mEdit1.remove("list");
        mEdit1.apply();

    }

    /**
     * @return datestring
     */
    public ArrayList<String> getArrayFromHistory() {

        return new ArrayList<>(prefs.getStringSet("list", new HashSet<String>()));
    }
}
