package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-18
 */

public class CollectAllAllergies {
    public static HashSet<Integer> getAllergies(Context context){
        HashSet<Integer> integers = new HashSet<>();
        HashMap<Integer, Integer> hashMap = ValidateAllergiesPreferences.setupAllergy();
        TreeMap<Integer, ArrayList<Integer>> myAllergies = new AllergyList(context).getMyAllergies();
        for (int key : hashMap.keySet()) {
            if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(String.valueOf(hashMap.get(key)),false)){
                integers.add(key);
            }
        }
        HashSet<Integer> integerToRemove = new HashSet<>();

        for (ArrayList<Integer> arrayList : myAllergies.values()) {
            integerToRemove.addAll(arrayList);
        }
        HashSet<Integer> integerToRemove2 = new HashSet<>();
        for (Integer integer : integers) {
            if(!integerToRemove.contains(integer)){
                integerToRemove2.add(integer);
            }
        }
        integers.removeAll(integerToRemove2);
        return integers;
    }
}
