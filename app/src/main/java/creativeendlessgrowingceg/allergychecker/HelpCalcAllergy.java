package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import creativeendlessgrowingceg.allergychecker.bktree.BkTreeSearcher;
import creativeendlessgrowingceg.allergychecker.bktree.Metric;
import creativeendlessgrowingceg.allergychecker.bktree.MutableBkTree;

/**
 *
 * allergy calculate class
 *
 * @author Jonathan Alexander Norberg
 * @version 2017-12-01
 */

public class HelpCalcAllergy {
    private static final String TAG = "HelpCalcAllergy";

    /**
     * Hamming distance for the word
     *
     * distance from a word to another depending on their length and char differences.
     *
     */
    private Metric<String> hammingDistance = new Metric<String>() {
        @Override
        public int distance(String x, String y) {
            if (!(x.length() != y.length() || x.length() - 1 != y.length() || x.length() + 1 != y.length())) {
                throw new IllegalArgumentException();
            }
            //Log.d(TAG, "Word X: " + x + " Y: " + y);
            int distance = 0;

            if (y.length() > x.length()) {
                int before = 0;
                for (int i = 0; i < y.length() - 1; i++) {
                    //Log.d(TAG, "i+1+before: " + (i-1+before) + " y: "+ y.length());
                    if(i+before >= y.length()){
                        //Log.d(TAG, "BREAK i+1+before: " + (i-1+before) + " y: "+ y.length());
                        //distance++;
                        break;
                    }
                    //Log.d(TAG, "char X: " + x.charAt(i) + " char Y: " + y.charAt(i+before));
                    if (x.charAt(i) != y.charAt(i+before)) {
                        distance++;
                        //Log.d(TAG, "char X: " + x.charAt(i) + " char Y: " + y.charAt(i+before) + " distance: " + distance);
                        before++;
                        i--;
                    }
                }
                //distance++;
                //Log.d(TAG, "Word X: " + x + " Y: " + y + " distance: " + distance);

            } else if (y.length() < x.length()) {
                for (int i = 0; i < x.length() - 1; i++)
                    if (x.charAt(i) != y.charAt(i)) {
                        distance++;
                    }
                distance++;
            } else {
                for (int i = 0; i < x.length(); i++)
                    if (x.charAt(i) != y.charAt(i)) {
                        distance++;
                    }
            }
            //Log.d(TAG, "DISTANCE " + distance);
            return distance;
        }
    };

    /**
     * convert string to locale desired
     * @param context to use
     * @param id to get string from
     * @param locale to change to locale desired
     * @return locale string
     */
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Context context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase().replaceAll("\\s+", "");
    }
    /**
     * takes a string from all strings scanned checks below and above string and concatinate them
     * helps with forinstance if the camera accident cut a string "Pea" "nut"
     * though the string will only be concatinated above and below if there exist a
     * du, de or di in the middle. this because some latin languages contains them for instance
     * nuezu de brazil.
     * @param splitStr splitted string
     * @param hashSetAllStrings to add
     * @param hashSetToCheckLast this to check full strig later
     */
    public void FixString(String[] splitStr, TreeMap<Integer, HashSet<String>> hashSetAllStrings, HashSet<String> hashSetToCheckLast) {
        for (int i = 0; i < splitStr.length; i++) {
            if (splitStr.length - 1 != i) {
                hashSetToCheckLast.add(splitStr[i] + splitStr[i + 1]);
                if (hashSetAllStrings.containsKey((splitStr[i] + splitStr[i + 1]).length())) {
                    hashSetAllStrings.put((splitStr[i] + splitStr[i + 1]).length(), hashSetAllStrings.get((splitStr[i] + splitStr[i + 1]).length())).add(splitStr[i] + splitStr[i + 1]);
                } else {
                    HashSet<String> hashset = new HashSet<>();
                    hashset.add(splitStr[i] + splitStr[i + 1]);
                    hashSetAllStrings.put((splitStr[i] + splitStr[i + 1]).length(), hashset);
                }
                if ((splitStr[i].equals("de") ||splitStr[i].equals("du")||splitStr[i].equals("di")) && splitStr.length > 0) {
                    hashSetToCheckLast.add(splitStr[i - 1] + splitStr[i] + splitStr[i + 1]);
                    if (hashSetAllStrings.containsKey((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length())) {
                        hashSetAllStrings.put((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length(), hashSetAllStrings.get((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length())).add((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]));
                    } else {
                        HashSet<String> hashset = new HashSet<>();
                        hashset.add(splitStr[i - 1] + splitStr[i] + splitStr[i + 1]);
                        hashSetAllStrings.put((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length(), hashset);
                    }
                }
            }
            hashSetToCheckLast.add(splitStr[i]);
            if (hashSetAllStrings.containsKey((splitStr[i]).length())) {
                hashSetAllStrings.put((splitStr[i]).length(), hashSetAllStrings.get((splitStr[i]).length())).add(splitStr[i]);
            } else {
                HashSet<String> hashset = new HashSet<>();
                hashset.add(splitStr[i]);
                hashSetAllStrings.put((splitStr[i]).length(), hashset);
            }
        }
    }

    /**
     * Get all strings from list of language for instance jordnöt peanut peanödd e.t.c.
     * @param length to speed up algorithm
     * @param id to set R.string. ..
     * @param allergies to insert into
     * @param listOfLanguages to convert strings to
     * @param context to getstring
     * @return length
     */
    public int setLocaleString(int length, int id, HashMap<Integer, HashMap<String, AllergiesClass>> allergies, ArrayList<Locale> listOfLanguages, Context context) {

        for (Locale locale : listOfLanguages) {

            String localeString = getStringByLocal(context, id, locale.getLanguage());

            if (length < localeString.length()) {
                length = localeString.length() + 2;
            }
            List<String> list = TextHandler.split(localeString);
            for (int i = 0; i < list.size(); i++) {

                if (allergies.containsKey(list.get(i).length())) {
                    HashMap<String, AllergiesClass> stringAllAllergiesForEachIntegerHashMap = allergies.get((list.get(i)).length());
                    stringAllAllergiesForEachIntegerHashMap.put(list.get(i), new AllergiesClass(locale.getLanguage(),
                                    list.get(i), id, context.getString(id)));
                    allergies.put((list.get(i)).length(),stringAllAllergiesForEachIntegerHashMap);


                } else {
                    HashMap<String, AllergiesClass> hashSetn = new HashMap<>();
                    hashSetn.put(list.get(i), new AllergiesClass(locale.getLanguage(), list.get(i), id, context.getString(id)));
                    allergies.put((list.get(i)).length(), hashSetn);

                }
            }
        }

        return length;
    }

    /**
     * handles the algorithm of nearby allergies.
     *
     * s > 4 means if string length bigger then 4 use this distance from another word
     * for instance: lemon > 4 then max possible distance is 1 if the word matched against is 4 also
     * then leman, lamon and so on is possible allergies
     *
     * if scanned string is smaller than allergy word the words might be lemn, lemo and so on only
     * though if the word is bigger than 4 else small word will pop up all the time like "sej", from
     * scanned text ej se which would make the user annoyed.
     *
     * @param length speed up algorithm if word longer then the longest allergy
     * @param hashSetAllStrings all strings scanned
     * @param allergies all allergies user have
     * @param allFoundAllergies all found allergies
     */
    public void bkTree(int length, TreeMap<Integer, HashSet<String>> hashSetAllStrings,
                       HashMap<Integer, HashMap<String, AllergiesClass>> allergies,
                       ArrayList<AllergiesClass> allFoundAllergies) {
        for (Integer s : hashSetAllStrings.keySet()) {
            if (length < s) {
                continue;
            }
            MutableBkTree<String> bkTree = new MutableBkTree<>(hammingDistance);
            bkTree.addAll(hashSetAllStrings.get(s));
            BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);
            if (allergies.containsKey(s)) {
                HashMap<String, AllergiesClass> allAllergies = allergies.get(s);
                for (AllergiesClass allergiesClass : allAllergies.values()) {
                    Set<BkTreeSearcher.Match<? extends String>> matches;

                    if (s > 4 && s < 10) {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 1);

                    } else if (s<=4) {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 1);
                    } else {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 2);
                    }
                    for (BkTreeSearcher.Match<? extends String> match : matches) {
                        AllergiesClass all = new AllergiesClass(
                                allergiesClass.getLanguage(),
                                allergiesClass.getNameOfIngredient(),
                                allergiesClass.getId(),
                                allergiesClass.getMotherLanguage());
                        all.setNameOfWordFound(match.getMatch());
                        allFoundAllergies.add(all);

                    }

                }
            }
            //allergier en mer
            if (allergies.containsKey(s + 1) && s + 1 > 4) {
                HashMap<String, AllergiesClass> allAllergies = allergies.get(s + 1);
                for (AllergiesClass allergiesClass : allAllergies.values()) {
                    Set<BkTreeSearcher.Match<? extends String>> matches;
                    if (s + 1 < 7) {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 1);
                    } else if (s + 1 < 10) {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 2);
                    } else {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 3);
                    }
                    for (BkTreeSearcher.Match<? extends String> match : matches) {
                        AllergiesClass all = new AllergiesClass(
                                allergiesClass.getLanguage(),
                                allergiesClass.getNameOfIngredient(),
                                allergiesClass.getId(),
                                allergiesClass.getMotherLanguage());
                        all.setNameOfWordFound(match.getMatch());
                        allFoundAllergies.add(all);
                        //alreadyContainedAllergies.add(allergiesClass.getNameOfIngredient());

                    }

                }
            }
            //allergier mindre
            if (allergies.containsKey(s - 1) && s - 1 > 5) {
                HashMap<String, AllergiesClass> allAllergies = allergies.get(s - 1);
                for (AllergiesClass allergiesClass : allAllergies.values()) {
                    Set<BkTreeSearcher.Match<? extends String>> matches;
                    if (s - 1 < 14) {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 2);

                    } else {
                        matches = searcher.search(allergiesClass.getNameOfIngredient(), 3);


                    }
                    for (BkTreeSearcher.Match<? extends String> match : matches) {
                        AllergiesClass all = new AllergiesClass(
                                allergiesClass.getLanguage(),
                                allergiesClass.getNameOfIngredient(),
                                allergiesClass.getId(),
                                allergiesClass.getMotherLanguage());
                        all.setNameOfWordFound(match.getMatch());
                        allFoundAllergies.add(all);
                    }

                }
            }

        }
    }

    /**
     *  check full string if substring is contained if allergy already found break.
     * @param s to check
     * @param allergies user have
     * @param allFoundAllergies that already found
     */
    public void checkFullString(String s, HashMap<Integer, HashMap<String, AllergiesClass>> allergies, ArrayList<AllergiesClass> allFoundAllergies) {
        for (int key : allergies.keySet()) {

            HashMap<String, AllergiesClass> allAllergies = allergies.get(key);
            for (AllergiesClass allergiesClass : allAllergies.values()) {
                if (s.contains(allergiesClass.getNameOfIngredient())) {

                    AllergiesClass all = new AllergiesClass(
                            allergiesClass.getLanguage(),
                            allergiesClass.getNameOfIngredient(),
                            allergiesClass.getId(),
                            allergiesClass.getMotherLanguage());
                    all.setNameOfWordFound(s);
                    Boolean open = true;
                    for (AllergiesClass allFoundAllergy : allFoundAllergies) {
                        if(allFoundAllergy.getNameOfIngredient().equals(all.getNameOfIngredient())){
                            open = false;
                            Log.d(TAG, "checkFullString: " + allFoundAllergy.getNameOfIngredient());
                        }
                    }
                    if(open)
                        allFoundAllergies.add(all);
                    //alreadyContainedAllergies.add(allergiesClass.getNameOfIngredient());
                }
            }
        }

    }


    /**
     * check enumbers
     * @param s to check e number
     * @param eNumbersArrayList all E numbers
     * @param allFoundAllergies all found E numbers
     */
    public void checkFullStringEnumbers(String s, ArrayList<AllergyList.E_Numbers> eNumbersArrayList, ArrayList<AllergyList.E_Numbers> allFoundAllergies) {
        for (AllergyList.E_Numbers key : eNumbersArrayList) {

            if (s.contains(key.getId().toLowerCase() )&& !allFoundAllergies.contains(key)) {
                Log.d(TAG, "checkFullStringEnumbers0: "+ s);
                Log.d(TAG, "checkFullStringEnumbers1: "+ key.getId());
                allFoundAllergies.add(key);

            }
        }
        eNumbersArrayList.removeAll(allFoundAllergies);
    }

}
