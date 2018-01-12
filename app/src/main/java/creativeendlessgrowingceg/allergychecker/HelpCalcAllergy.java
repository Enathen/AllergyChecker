package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by Enathen on 2017-12-01.
 */

public class HelpCalcAllergy {
    private static final String TAG = "HelpCalcAllergy";
    private Metric<String> hammingDistance = new Metric<String>() {
        @Override
        public int distance(String x, String y) {
            if (!(x.length() != y.length() || x.length() - 1 != y.length() || x.length() + 1 != y.length())) {
                throw new IllegalArgumentException();
            }
            int distance = 0;
            if (y.length() > x.length()) {
                for (int i = 0; i < y.length() - 1; i++)
                    if (x.charAt(i) != y.charAt(i)) {
                        distance++;
                    }
                distance++;
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
            return distance;
        }
    };
    private boolean dontEat;

    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Activity context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));


        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase().replaceAll("\\s+", "");
    }

    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Context context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));


        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase().replaceAll("\\s+", "");
    }

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
                if (splitStr[i].equals("de") && splitStr.length > 0) {
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

    public int setLocaleString(int length, int id, HashMap<Integer, HashMap<String, AllAllergiesForEachInteger>> allergies, ArrayList<Locale> listOfLanguages, StartPage startPage) {

        for (Locale locale : listOfLanguages) {

            String localeString = getStringByLocal(startPage, id, locale.getLanguage());
            if (length < localeString.length()) {
                length = localeString.length() + 2;
            }
            List<String> list = split(localeString);
            for (int i = 0; i < list.size(); i++) {

                if (allergies.containsKey(localeString.length())) {

                    allergies.put((list.get(i)).length(), allergies.get((list.get(i)).length())).put(
                            list.get(i), new AllAllergiesForEachInteger(locale.getLanguage(),
                                    list.get(i), id, startPage.getString(id)));


                } else {
                    HashMap<String, AllAllergiesForEachInteger> hashSetn = new HashMap<>();
                    hashSetn.put(list.get(i), new AllAllergiesForEachInteger(locale.getLanguage(), list.get(i), id, startPage.getString(id)));
                    allergies.put((list.get(i)).length(), hashSetn);

                }
            }
        }

        return length;
    }

    public void bkTree(int length, TreeMap<Integer, HashSet<String>> hashSetAllStrings,
                       HashMap<Integer, HashMap<String, AllAllergiesForEachInteger>> allergies,
                       ArrayList<AllAllergiesForEachInteger> allFoundAllergies) {
        for (HashMap<String, AllAllergiesForEachInteger> hashSet : allergies.values()) {
            for (AllAllergiesForEachInteger s : hashSet.values()) {
                Log.d(TAG, "getCategoriesFromOtherClass: " + s.getNameOfIngredient());
            }
        }
        for (Integer s : hashSetAllStrings.keySet()) {
            if (length < s) {
                continue;
            }
            MutableBkTree<String> bkTree = new MutableBkTree<>(hammingDistance);
            bkTree.addAll(hashSetAllStrings.get(s));
            BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);
            if (allergies.containsKey(s) && s > 4) {
                HashMap<String, AllAllergiesForEachInteger> allAllergies = allergies.get(s);
                for (AllAllergiesForEachInteger allAllergiesForEachInteger : allAllergies.values()) {
                    Set<BkTreeSearcher.Match<? extends String>> matches;

                    if (s > 4 && s < 10) {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 1);

                    } else {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 2);
                    }
                    for (BkTreeSearcher.Match<? extends String> match : matches) {
                        AllAllergiesForEachInteger all = new AllAllergiesForEachInteger(
                                allAllergiesForEachInteger.getLanguage(),
                                allAllergiesForEachInteger.getNameOfIngredient(),
                                allAllergiesForEachInteger.getId(),
                                allAllergiesForEachInteger.getMotherLanguage());
                        all.setNameOfWordFound(match.getMatch());
                        allFoundAllergies.add(all);

                    }

                }
            }
            //allergier en mer
            if (allergies.containsKey(s + 1) && s + 1 > 4) {
                HashMap<String, AllAllergiesForEachInteger> allAllergies = allergies.get(s + 1);
                for (AllAllergiesForEachInteger allAllergiesForEachInteger : allAllergies.values()) {
                    Set<BkTreeSearcher.Match<? extends String>> matches;
                    if (s + 1 < 7) {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 1);
                    } else if (s + 1 < 10) {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 2);
                    } else {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 3);
                    }
                    for (BkTreeSearcher.Match<? extends String> match : matches) {
                        AllAllergiesForEachInteger all = new AllAllergiesForEachInteger(
                                allAllergiesForEachInteger.getLanguage(),
                                allAllergiesForEachInteger.getNameOfIngredient(),
                                allAllergiesForEachInteger.getId(),
                                allAllergiesForEachInteger.getMotherLanguage());
                        all.setNameOfWordFound(match.getMatch());
                        allFoundAllergies.add(all);
                        //alreadyContainedAllergies.add(allAllergiesForEachInteger.getNameOfIngredient());

                    }

                }
            }
            //allergier mindre
            if (allergies.containsKey(s - 1) && s - 1 > 5) {
                HashMap<String, AllAllergiesForEachInteger> allAllergies = allergies.get(s - 1);
                for (AllAllergiesForEachInteger allAllergiesForEachInteger : allAllergies.values()) {
                    Set<BkTreeSearcher.Match<? extends String>> matches;
                    if (s - 1 < 14) {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 2);

                    } else {
                        matches = searcher.search(allAllergiesForEachInteger.getNameOfIngredient(), 3);


                    }
                    for (BkTreeSearcher.Match<? extends String> match : matches) {
                        AllAllergiesForEachInteger all = new AllAllergiesForEachInteger(
                                allAllergiesForEachInteger.getLanguage(),
                                allAllergiesForEachInteger.getNameOfIngredient(),
                                allAllergiesForEachInteger.getId(),
                                allAllergiesForEachInteger.getMotherLanguage());
                        all.setNameOfWordFound(match.getMatch());
                        allFoundAllergies.add(all);

                    }

                }
            }

        }
    }


    public void checkFullString(String s, HashMap<Integer, HashMap<String, AllAllergiesForEachInteger>> allergies, ArrayList<AllAllergiesForEachInteger> allFoundAllergies) {
        for (int key : allergies.keySet()) {

            HashMap<String, AllAllergiesForEachInteger> allAllergies = allergies.get(key);
            for (AllAllergiesForEachInteger allAllergiesForEachInteger : allAllergies.values()) {
                /*if(alreadyContainedAllergies.contains(allAllergiesForEachInteger.getNameOfIngredient())){
                    continue;
                }*/
                if (s.contains(allAllergiesForEachInteger.getNameOfIngredient())) {
                    AllAllergiesForEachInteger all = new AllAllergiesForEachInteger(
                            allAllergiesForEachInteger.getLanguage(),
                            allAllergiesForEachInteger.getNameOfIngredient(),
                            allAllergiesForEachInteger.getId(),
                            allAllergiesForEachInteger.getMotherLanguage());
                    all.setNameOfWordFound(s);
                    allFoundAllergies.add(all);
                    //alreadyContainedAllergies.add(allAllergiesForEachInteger.getNameOfIngredient());
                }
            }
        }

    }


    public boolean checkIfAlreadyShown(View v) {
        if (v.getRotation() == 0) {
            v.setRotation(180);
            return true;
        } else {
            v.setRotation(0);
            return false;


        }

    }

    public String cutFirstWord(String string) {
        List<String> list = null;
        if (string.contains(",")) {
            list = Arrays.asList(string.split(","));

        }
        if (list == null) {
            return string;
        }
        return list.get(0);
    }

    public List<String> split(String string) {
        List<String> list = null;
        if (string.contains(",")) {
            list = Arrays.asList(string.split(","));

        }
        if (list == null) {
            list = new ArrayList<>();
            list.add(string);
            return list;
        }
        return list;
    }
}
