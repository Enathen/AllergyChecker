package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-18
 */

public class CollectAllAllergies {
    public static HashSet<Integer> getAllergies(Context context){
        HashSet<Integer> integers = new HashSet<>();
        HashMap<Integer, Integer> hashMap = ValidateAllergiesPreferences.setupAllergy();
        for (int key : hashMap.keySet()) {
            if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(String.valueOf(hashMap.get(key)),false)){
                integers.add(key);
            }
        }
        return integers;
    }
}
