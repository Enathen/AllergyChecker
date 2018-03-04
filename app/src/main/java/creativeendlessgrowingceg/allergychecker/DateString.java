package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-03-04
 */

public class DateString {
    private static final String SHARED_PREFS_NAME = "StartPage";
    private static final String TAG = "DATESTRING";
    private final SharedPreferences prefs;
    String string;
    private Context startPage;

    DateString(String string,Context startPage) {
        this.startPage = startPage;
        prefs = startPage.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("sv"));
        this.string = simpleDateFormat.format(new Date()).concat(" " + string);
        Log.d(TAG, "DateString: " + string+ " : " +this.string);
    }
    DateString(Context startPage) {
        this.startPage = startPage;
        prefs = startPage.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
    }
    public boolean saveArray() {
        ArrayList<String> dateStrings = getArray();
        Collections.sort(getArray());
        for (String dateString : dateStrings) {
            Log.d(TAG, dateString);
        }

        SharedPreferences.Editor mEdit1 = prefs.edit();
        Collections.sort(dateStrings, new HistoryFragment.stringComparator());
        if (dateStrings.size() > 128) {
            int sizeToMuch = dateStrings.size() % 128;
            for (int i = 0; i < sizeToMuch; i++) {
                Log.d(TAG, "Remove History: " + dateStrings.get(i));
                dateStrings.remove(i);
            }
        }

        Set<String> set = new HashSet<>();
        set.addAll(dateStrings);
        mEdit1.putStringSet("list", set);
        return mEdit1.commit();
    }

    public ArrayList<String> getArray() {

        Set<String> set = prefs.getStringSet("list", new HashSet<String>());

        return new ArrayList<>(set);
    }

    public void deleteOneItemHistory(String keys) {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        Set<String> set = prefs.getStringSet("list", new HashSet<String>());
        set.remove(keys);
        mEdit1.remove("list");
        mEdit1.commit();
        mEdit1.putStringSet("list", set);
        mEdit1.commit();


    }

    public void deleteHistory() {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        mEdit1.remove("list");
        mEdit1.apply();

    }

    public ArrayList<String> getArrayFromHistory() {
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = prefs.getStringSet("list", new HashSet<String>());
        for (String s : set) {
            Log.d(TAG, "getArray: " + s);
        }
        return new ArrayList<>(set);
    }
}
