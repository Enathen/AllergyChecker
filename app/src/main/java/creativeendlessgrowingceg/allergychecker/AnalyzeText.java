package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-19
 */

public class AnalyzeText extends AsyncTask<String, Integer, ArrayList<AllergiesClass>> {
    private boolean languageIsSelected = false;
    private Context context;

    public AnalyzeText( Context context){

        this.context = context;
    }

    @Override
    protected ArrayList<AllergiesClass> doInBackground(String... strings) {
        String[] fullTextArray = strings[0].split("\\s+");
        AlgorithmAllergies algorithmAllergies = new AlgorithmAllergies();
        ArrayList<Locale> languages = LanguagesAccepted.getActiveLanguages(context);
        if(!languages.isEmpty()){
            languageIsSelected = true;
        }
        TreeMap<Integer, TreeSet<String>> treeMapSplittedFullText = algorithmAllergies.FixStringAllStrings(fullTextArray);

        return null;
    }
}
