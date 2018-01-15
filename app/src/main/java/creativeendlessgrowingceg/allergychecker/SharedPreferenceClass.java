package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Enathen on 2018-01-12.
 */

public class SharedPreferenceClass {
    private static final SharedPreferenceClass ourInstance = new SharedPreferenceClass();

    public static SharedPreferenceClass getInstance() {
        return ourInstance;
    }
    public static boolean checkBoolean(String string,Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(string,false);
    }
    public static void setBoolean(String string, Context context, Boolean bool){
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(context).edit();
            sharedPreferencesEditor.putBoolean(string, bool);
            sharedPreferencesEditor.apply();
    }
    public static void setSet(Context context, HashSet<Integer> hashMapCategoriesAllergy){
        SharedPreferences sp = context.getSharedPreferences("LoadUI", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Set<String> set = new TreeSet<>();
        for (Integer integer : hashMapCategoriesAllergy) {
            set.add(integer.toString());
        }

        mEdit1.putStringSet("data", set);
        mEdit1.apply();
    }
    public static HashSet<Integer> getSet(StartPage context){
        SharedPreferences sp = context.getSharedPreferences("LoadUI", Context.MODE_PRIVATE);
        Set<String> set = sp.getStringSet("data", new HashSet<String>());
        HashSet<Integer> hashSet = new HashSet<>();
        for (String s : set) {
            hashSet.add(Integer.parseInt(s));
        }
        return hashSet;
    }
    public static void setSetFromAllergy(Context context, HashSet<String> hashMapCategoriesAllergy){
        SharedPreferences.Editor mEdit1 = context.getSharedPreferences("LoadUI", Context.MODE_PRIVATE).edit();
        Set<String> set = new TreeSet<>();
        set.addAll(hashMapCategoriesAllergy);
        mEdit1.putStringSet("allergy", set);
        mEdit1.apply();
    }
    public static Set<String> getSetFromAllergy(Context context){
        return  context.getSharedPreferences("LoadUI", Context.MODE_PRIVATE).getStringSet("allergy", new HashSet<String>());
    }
    public static void setSetFromPreference(Context context, HashSet<String> hashMapCategoriesAllergy){
        SharedPreferences.Editor mEdit1 = context.getSharedPreferences("LoadUI", Context.MODE_PRIVATE).edit();
        Set<String> set = new TreeSet<>();
        set.addAll(hashMapCategoriesAllergy);
        mEdit1.putStringSet("preference", set);
        mEdit1.apply();
    }
    public static Set<String> getSetFromPreference(Context context){
        return  context.getSharedPreferences("LoadUI", Context.MODE_PRIVATE).getStringSet("preference", new HashSet<String>());
    }
}
