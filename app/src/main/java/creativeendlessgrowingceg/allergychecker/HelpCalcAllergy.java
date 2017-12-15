package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.app.Activity;
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
    Metric<String> hammingDistance = new Metric<String>() {
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
            if (allergies.containsKey(localeString.length())) {
                allergies.put((localeString).length(), allergies.get((localeString).length())).put(
                        localeString, new AllAllergiesForEachInteger(locale.getLanguage(),
                                localeString, id, startPage.getString(id)));
            } else {
                HashMap<String, AllAllergiesForEachInteger> hashSetn = new HashMap<>();
                hashSetn.put(localeString, new AllAllergiesForEachInteger(locale.getLanguage(), localeString, id, startPage.getString(id)));
                allergies.put((localeString).length(), hashSetn);
            }
        }
        return length;
    }

    public void bkTree(int length, HashSet<String> alreadyContainedAllergies, TreeMap<Integer, HashSet<String>> hashSetAllStrings, HashMap<Integer, HashMap<String, AllAllergiesForEachInteger>> allergies, StartPage calcAllergy, ArrayList<AllAllergiesForEachInteger> allFoundAllergies) {
        long start = System.currentTimeMillis();
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
                        //alreadyContainedAllergies.add(allAllergiesForEachInteger.getNameOfIngredient());

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
                        //alreadyContainedAllergies.add(allAllergiesForEachInteger.getNameOfIngredient());

                    }

                }
            }

        }
        long stop = System.currentTimeMillis();
        Log.d(TAG, "TIME: " + (stop - start));
    }


    public void checkFullString(String s, HashMap<Integer, HashMap<String, AllAllergiesForEachInteger>> allergies, StartPage startPage, HashSet<String> alreadyContainedAllergies, ArrayList<AllAllergiesForEachInteger> allFoundAllergies) {
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

    public int getFlag(String langCode) {
        if (langCode.equals("sv")) {
            return R.drawable.se;
        }
        if (langCode.equals("en")) {
            return R.drawable.gb;
        }
        if (langCode.equals("es")) {
            return R.drawable.es;
        }
        return 0;

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
}

// HashMap<String, LangString> allergies = spellCheckAllergy.permuteStringi(mContext,arrayListAllergies);
            /*if(allergies != null) {

                boolean b;
                counter = hashSetAllStrings.size()/2;
                i = 0;
                for (String string : hashSetAllStrings) {

                    if(i % 2 == 0){
                        counter++;
                    }
                    i++;
                    b = false;
                    publishProgress(hashSetAllStrings.size(), counter);

                    for (String extraKey : allergies.keySet()){

                        if(extraKey.replaceAll("\\s+","").equals(string)){

                            allergies.get(extraKey).found++;
                            if(allergies.get(extraKey).found == 1){
                                if(!definitelyContained.contains(allergies.get(extraKey).id)){
                                    outputString = outputString.concat(getString(R.string.definitelyContained)+" "+ getString(allergies.get(extraKey).id) + "\n");
                                    definitelyContained.add(allergies.get(extraKey).id);

                                }
                            }
                            b = true;
                            break;
                        }
                    }
                    if(!b){
                        for (String key : allergies.keySet() ) {

                            if(string.contains(key)){

                                allergies.get(key).found++;
                                if(allergies.get(key).found == 1){
                                    outputString = outputString.concat(getString(R.string.probablyContained) + " "+
                                            getString(allergies.get(key).id) + " " + getString(R.string.fromWord)
                                            + " " + string + "\n");


                                }
                            }
                            if (allergies.get(key).allPossibleDerivationsOfAllergen.contains(string)) {
                                allergies.get(key).found++;
                                if(allergies.get(key).found == 1) {
                                    outputString = outputString.concat(getString(R.string.probablyContained) + " "+
                                            getString(allergies.get(key).id) + " " + getString(R.string.fromWord)
                                            + " "  + string + "\n");

                                }
                            }
                        }
                    }

                }
                for (String key : allergies.keySet()) {
                    if(allergies.get(key).found>0) {
                        if(!Locale.getDefault().getLanguage().equals(allergies.get(key).language)) {
                            if(allergies.containsKey(getString(allergies.get(key).id).toLowerCase())){
                                allergies.get( getString(allergies.get(key).id).toLowerCase()).found += allergies.get(key).found;
                                allergies.get(key).found = 0;
                            }
                        }
                    }
                }
                outputString = outputString.concat("\n");
                for (String key : allergies.keySet()){

                    if(allergies.get(key).found>0){
                        dontEat = true;
                    }
                }

            }*/




                                /*HashSet<String> string = spellCheckAllergy.permuteString(locale.getLanguage(),
                            getStringByLocal(StartPage.this, id,
                                    locale.getLanguage()));*/
//LangString langString = new LangString(locale.getLanguage(),true,id);
//langString.allPossibleDerivationsOfAllergen = string;