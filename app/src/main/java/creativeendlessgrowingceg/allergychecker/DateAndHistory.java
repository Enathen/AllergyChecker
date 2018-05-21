package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public DateAndHistory( Context startPage,String string) {
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
        mEdit1.putStringSet(APISharedPreference.dateAndHistory, set);
        mEdit1.commit();
    }
    public void sort(ArrayList<String> arrayList){
        Collections.sort(arrayList, new stringComparator());
        Collections.reverse(arrayList);
    }
    public String getTop(){
        ArrayList<String> strings = new ArrayList<>(prefs.getStringSet(APISharedPreference.dateAndHistory, new HashSet<String>()));
        sort(strings);
        if(strings.isEmpty()){
            return null;
        }
        return strings.get(0);
    }
    /**
     * @return ArrayList of datestrings
     */
    public ArrayList<String> getArray() {

        Set<String> set = prefs.getStringSet(APISharedPreference.dateAndHistory, new HashSet<String>());

        return new ArrayList<>(set);
    }

    /**
     * @param key to get deleted
     */
    public void deleteOneItemHistory(String key) {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        Set<String> set = prefs.getStringSet(APISharedPreference.dateAndHistory, new HashSet<String>());
        set.remove(key);
        mEdit1.remove(APISharedPreference.dateAndHistory);
        mEdit1.commit();
        mEdit1.putStringSet(APISharedPreference.dateAndHistory, set);
        mEdit1.commit();


    }

    /**
     * delete full history
     */
    public void deleteHistory() {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        mEdit1.remove(APISharedPreference.dateAndHistory);
        mEdit1.apply();

    }

    /**
     * @return datestring
     */
    public ArrayList<String> getArrayFromHistory() {

        return new ArrayList<>(prefs.getStringSet(APISharedPreference.dateAndHistory, new HashSet<String>()));
    }
    /**
     * compare dates to present it nice.
     */
    public static class stringComparator implements Comparator<String> {
        @Override
        public int compare(String string1, String string2) {
            String year1 = string1.substring(0, 4);
            String year2 = string2.substring(0, 4);
            String month1 = string1.substring(5, 7);
            String month2 = string2.substring(5, 7);
            String day1 = string1.substring(8, 10);
            String day2 = string2.substring(8, 10);

            String time1 = string1.substring(11, 19);
            String time2 = string2.substring(11, 19);
            if (year1.compareToIgnoreCase(year2) != 0) {
                return year1.compareToIgnoreCase(year2);
            }
            if (month1.compareToIgnoreCase(month2) != 0) {

                return month1.compareToIgnoreCase(month2);
            }
            if (day1.compareToIgnoreCase(day2) != 0) {
                return day1.compareToIgnoreCase(day2);
            }
            if (time1.compareToIgnoreCase(time2) != 0) {
                return time1.compareToIgnoreCase(time2);
            }


            return 0;
        }
    }
}
