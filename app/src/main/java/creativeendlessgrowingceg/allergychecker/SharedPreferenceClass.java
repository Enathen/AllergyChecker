package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
}
