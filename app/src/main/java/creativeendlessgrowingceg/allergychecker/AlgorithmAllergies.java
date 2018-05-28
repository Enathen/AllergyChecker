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
import java.util.TreeSet;

import creativeendlessgrowingceg.allergychecker.bktree.BkTreeSearcher;
import creativeendlessgrowingceg.allergychecker.bktree.MutableBkTree;

/**
 * allergy calculate class
 *
 * @author Jonathan Alexander Norberg
 * @version 2017-12-01
 */

public class AlgorithmAllergies {
    private static final String TAG = "AlgorithmAllergies";


    /**
     * convert string to locale desired
     *
     * @param context to use
     * @param id      to get string from
     * @param locale  to change to locale desired
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
     *
     * @param splitStr           splitted string
     */
    public  TreeMap<Integer, TreeSet<String>> FixStringAllStrings(String[] splitStr) {
        TreeMap<Integer, TreeSet<String>> hashSetAllStrings = new TreeMap<>();
        for (int i = 0; i < splitStr.length; i++) {
            if (splitStr.length - 1 != i) {
                if (hashSetAllStrings.containsKey((splitStr[i] + splitStr[i + 1]).length())) {
                    hashSetAllStrings.put((splitStr[i] + splitStr[i + 1]).length(), hashSetAllStrings.get((splitStr[i] + splitStr[i + 1]).length())).add(splitStr[i] + splitStr[i + 1]);
                } else {
                    TreeSet<String> hashset = new TreeSet<>();
                    hashset.add(splitStr[i] + splitStr[i + 1]);
                    hashSetAllStrings.put((splitStr[i] + splitStr[i + 1]).length(), hashset);
                }
                if ((splitStr[i].equals("de") || splitStr[i].equals("du") || splitStr[i].equals("di")) && splitStr.length > 0) {
                    if (hashSetAllStrings.containsKey((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length())) {
                        hashSetAllStrings.put((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length(), hashSetAllStrings.get((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length())).add((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]));
                    } else {
                        TreeSet<String> hashset = new TreeSet<>();
                        hashset.add(splitStr[i - 1] + splitStr[i] + splitStr[i + 1]);
                        hashSetAllStrings.put((splitStr[i - 1] + splitStr[i] + splitStr[i + 1]).length(), hashset);
                    }
                }
            }
            if (hashSetAllStrings.containsKey((splitStr[i]).length())) {
                hashSetAllStrings.put((splitStr[i]).length(), hashSetAllStrings.get((splitStr[i]).length())).add(splitStr[i]);
            } else {
                TreeSet<String> hashset = new TreeSet<>();
                hashset.add(splitStr[i]);
                hashSetAllStrings.put((splitStr[i]).length(), hashset);
            }
        }
        return hashSetAllStrings;
    }
    /**
     * takes a string from all strings scanned checks below and above string and concatinate them
     * helps with forinstance if the camera accident cut a string "Pea" "nut"
     * though the string will only be concatinated above and below if there exist a
     * du, de or di in the middle. this because some latin languages contains them for instance
     * nuezu de brazil.
     *
     * @param splitStr           splitted string
     */
    public HashSet<String> FixStringAllStringsToCheckLast(String[] splitStr) {
        HashSet<String> hashSetToCheckLast = new HashSet<>();
        for (int i = 0; i < splitStr.length; i++) {
            if (splitStr.length - 1 != i) {
                hashSetToCheckLast.add(splitStr[i] + splitStr[i + 1]);

                if ((splitStr[i].equals("de") || splitStr[i].equals("du") || splitStr[i].equals("di")) && splitStr.length > 0) {
                    hashSetToCheckLast.add(splitStr[i - 1] + splitStr[i] + splitStr[i + 1]);
                }
            }
            hashSetToCheckLast.add(splitStr[i]);
        }
        return hashSetToCheckLast;
    }
   /*/**
     * Get all strings from list of language for instance jordnöt peanut peanödd e.t.c.
     *
     * @param length          to speed up algorithm
     * @param id              to set R.string. ..
     * @param allergies       to insert into
     * @param listOfLanguages to convert strings to
     * @param context         to getstring
     * @return length

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
                    allergies.put((list.get(i)).length(), stringAllAllergiesForEachIntegerHashMap);


                } else {
                    HashMap<String, AllergiesClass> hashSetn = new HashMap<>();
                    hashSetn.put(list.get(i), new AllergiesClass(locale.getLanguage(), list.get(i), id, context.getString(id)));
                    allergies.put((list.get(i)).length(), hashSetn);

                }
            }
        }

        return length;
    }*/

    public int translateAllAllergies(int id, HashMap<String, AllergiesClass> allergies, ArrayList<Locale> listOfLanguages, SettingsFragment context) {
        int length = 0;
        for (Locale locale : listOfLanguages) {
            String localeString = getStringByLocal(context.getContext(), id, locale.getLanguage());
            List<String> list = TextHandler.split(localeString);
            for (int i = 0; i < list.size(); i++) {
                allergies.put(list.get(i), new AllergiesClass(locale.getLanguage(),
                        list.get(i), id, context.getString(id)));
                if (length < list.get(i).length()) {
                    length = list.get(i).length() + 2;
                }
            }
        }
        return length;
    }

    /**
     * handles the algorithm of nearby allergies.
     * <p>
     * s > 4 means if string length bigger then 4 use this distance from another word
     * for instance: lemon > 4 then max possible distance is 1 if the word matched against is 4 also
     * then leman, lamon and so on is possible allergies
     * <p>
     * if scanned string is smaller than allergy word the words might be lemn, lemo and so on only
     * though if the word is bigger than 4 else small word will pop up all the time like "sej", from
     * scanned text ej se which would make the user annoyed.
     * @param hashSetAllStrings all strings scanned
     * @param allergies         all allergies user have
     */
    public TreeMap<String,AllergiesClass> bkTree(TreeMap<Integer, TreeSet<String>> hashSetAllStrings, HashMap<String, AllergiesClass> allergies) {
        TreeMap<String,AllergiesClass> allFoundAllergies = new TreeMap<>();
        MutableBkTree<String> bkTree = new MutableBkTree<>(HammingDistance.hammingDistance);
        for (Integer stringTreeSet : hashSetAllStrings.keySet()) {
            if ( stringTreeSet <= 0) {
                continue;
            }
            bkTree.addAll(hashSetAllStrings.get(stringTreeSet));
        }
        BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);
        for (AllergiesClass allergiesClass : allergies.values()) {
            Set<BkTreeSearcher.Match<? extends String>> matches;
            if (allergiesClass.getNameOfIngredient().length() < 5) {
                continue;
            }
            if (allergiesClass.getNameOfIngredient().length() < 7) {
                matches = searcher.search(allergiesClass.getNameOfIngredient(), 1);
            } else if (allergiesClass.getNameOfIngredient().length() < 8) {
                matches = searcher.search(allergiesClass.getNameOfIngredient(), 2);
            } else if (allergiesClass.getNameOfIngredient().length() < 11) {
                matches = searcher.search(allergiesClass.getNameOfIngredient(), 3);
            } else {
                matches = searcher.search(allergiesClass.getNameOfIngredient(), 4);
            }
            for (BkTreeSearcher.Match<? extends String> match : matches) {
                AllergiesClass all = new AllergiesClass(
                        allergiesClass.getLanguage(),
                        allergiesClass.getNameOfIngredient(),
                        allergiesClass.getId(),
                        allergiesClass.getMotherAllergy(),match.getDistance());
                all.setNameOfWordFound(match.getMatch());
                if(allFoundAllergies.containsKey(allergiesClass.getMotherAllergy())){
                    allFoundAllergies.get(allergiesClass.getMotherAllergy()).increaseFoundAllergies(all);
                }else{

                    allFoundAllergies.put(allergiesClass.getMotherAllergy(),all);
                }


                //Log.d(TAG, "bkTree: "+ all.getNameOfIngredient() + " : " + all.getNameOfWordFound() +" : " +all.getDistance());
            }
        }
        return allFoundAllergies;
    }

    /**
     * check full string if substring is contained if allergy already found break.
     *  @param s                 to check
     * @param allergies         user have
     * @param allFoundAllergies that already found
     */
    public void checkFullString(String s, HashMap<String, AllergiesClass> allergies, TreeMap<String, AllergiesClass> allFoundAllergies) {

        for (AllergiesClass allergiesClass : allergies.values()) {
            if (s.contains(allergiesClass.getNameOfIngredient())) {
                AllergiesClass all = new AllergiesClass(
                        allergiesClass.getLanguage(),
                        allergiesClass.getNameOfIngredient(),
                        allergiesClass.getId(),
                        allergiesClass.getMotherAllergy());
                all.setNameOfWordFound(s);
                if(allFoundAllergies.containsKey(allergiesClass.getMotherAllergy())){
                    allFoundAllergies.get(allergiesClass.getMotherAllergy()).increaseFoundAllergies(all);
                }else{

                    allFoundAllergies.put(all.getMotherAllergy(),all);
                }

            }
        }


    }


    /**
     * check enumbers
     *
     * @param s                 to check e number
     * @param eNumbersArrayList all E numbers
     * @param allFoundAllergies all found E numbers
     */
    public void checkFullStringEnumbers(String s, ArrayList<AllergyList.E_Numbers> eNumbersArrayList, ArrayList<AllergyList.E_Numbers> allFoundAllergies) {
        for (AllergyList.E_Numbers key : eNumbersArrayList) {

            if (s.contains(key.getId().toLowerCase()) && !allFoundAllergies.contains(key)) {
                Log.d(TAG, "checkFullStringEnumbers0: " + s);
                Log.d(TAG, "checkFullStringEnumbers1: " + key.getId());
                allFoundAllergies.add(key);

            }
        }
        eNumbersArrayList.removeAll(allFoundAllergies);
    }

}
