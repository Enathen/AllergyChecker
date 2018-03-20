package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * handles shared preferences easy
 * @author Jonathan Alexander Norberg
 * @version 2018-01-12
 */

public class SharedPreferenceClass {
    private static final String TAG = "SharedPreferenceClass";
    private static final SharedPreferenceClass ourInstance = new SharedPreferenceClass();

    public static SharedPreferenceClass getInstance() {
        return ourInstance;
    }

    public static boolean checkBoolean(String string, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(string, false);
    }

    public static void setBoolean(String string, Context context, Boolean bool) {
        SharedPreferences.Editor sharedPreferencesEditor =
                PreferenceManager.getDefaultSharedPreferences(context).edit();
        sharedPreferencesEditor.putBoolean(string, bool);
        sharedPreferencesEditor.apply();
    }

    public static void setSharedPreference(StartPage context, HashSet<Integer> hashMapCategoriesAllergy, String getSharedPreference, String Mother) {
        SharedPreferences sp = context.getSharedPreferences(Mother, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Set<String> set = new TreeSet<>();
        for (Integer integer : hashMapCategoriesAllergy) {
            set.add(integer.toString());
        }

        mEdit1.putStringSet(getSharedPreference, set);
        mEdit1.apply();
    }

    public static HashSet<Integer> getSharedPreference(Context context, String getSharedPreference, String Mother) {
        SharedPreferences sp = context.getSharedPreferences(Mother, Context.MODE_PRIVATE);
        Set<String> set = sp.getStringSet(getSharedPreference, new HashSet<String>());
        HashSet<Integer> hashSet = new HashSet<>();
        for (String s : set) {
            hashSet.add(Integer.parseInt(s));

        }
        return hashSet;
    }

    public static void setSharedPreference(Context context, HashSet<String> hashMapCategoriesAllergy, String getSharedPreference, String Mother) {
        SharedPreferences.Editor mEdit1 = context.getSharedPreferences(Mother, Context.MODE_PRIVATE).edit();
        Set<String> set = new TreeSet<>();
        set.addAll(hashMapCategoriesAllergy);
        mEdit1.putStringSet(getSharedPreference, set);
        mEdit1.apply();
    }

    public static Set<String> getSharedPreferenceString(Context context, String getSharedPreference, String Mother) {
        return context.getSharedPreferences(Mother, Context.MODE_PRIVATE).getStringSet(getSharedPreference, new HashSet<String>());
    }

    public static void addSharedPreference(StartPage context, HashSet<Integer> hashSet, String allergySave, String tag) {
        SharedPreferences sp = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Set<String> set = context.getSharedPreferences(tag, Context.MODE_PRIVATE).getStringSet(allergySave, new HashSet<String>());
        for (Integer integer : hashSet) {
            set.add(integer.toString());
        }

        mEdit1.putStringSet(allergySave, set);
        mEdit1.apply();
    }
}