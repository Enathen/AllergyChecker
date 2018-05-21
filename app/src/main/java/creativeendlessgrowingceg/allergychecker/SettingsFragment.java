package creativeendlessgrowingceg.allergychecker;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getFoundCount;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getFoundENumbers;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getWordCount;


public class SettingsFragment extends Fragment {


    private String string;
    private SharedPreferences defaultSharedPreferences;
    private SharedPreferences.Editor edit;
    private Boolean openToSaveStatistics = true;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FrameLayout inflate = (FrameLayout) inflater.inflate(R.layout.fragment_settings2, container, false);
        Log.d(TAG, "onCreate: "+ savedInstanceState);
        Bundle arguments = getArguments();
        Log.d(TAG, "onCreate: "+ arguments);
        Log.d(TAG, "onCreate: "+ (arguments != null));
        if (arguments != null) {
            string = arguments.getString(APISharedPreference.getScannedText);

            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            edit = defaultSharedPreferences.edit();
            if(string == null){
                openToSaveStatistics = false;
                string = arguments.getString(APISharedPreference.getHistory);
            }
            if(openToSaveStatistics){
                int i = defaultSharedPreferences.getInt(getWordCount(), 0) + string.split("\\s+").length;
                edit.putInt(getWordCount(),i).apply();
            }
        }else{
            openToSaveStatistics = false;
            string = new DateAndHistory(getContext()).getTop();
            if(string!=null){
                Toast.makeText(getContext(),R.string.lastScannedText,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getContext(),R.string.noScanned,Toast.LENGTH_LONG).show();
            }

        }
        if(string != null){

            new AnalyzeText(inflate).execute(string);

        }
        return inflate;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class AnalyzeText extends AsyncTask<String, Integer, Object> {
        private boolean languageIsSelected = true;
        private boolean allergiesSelected = true;
        private ArrayList<AllergyList.E_Numbers> allFoundENumbers = new ArrayList<>();
        private FrameLayout parentFrameLayout;
        private TreeMap<String, AllergiesClass> allFoundAllergies;
        private TreeSet<String> allStringsOrdered = new TreeSet<>();
        private AlgorithmAllergies algorithmAllergies;
        private HashSet<Integer> allergies;
        private HashMap<String, AllergiesClass> allAllergies;
        private TreeMap<Integer, TreeSet<String>> treeMapSplittedFullText;

        public ArrayList<AllergyList.E_Numbers> getAllFoundENumbers() {
            return allFoundENumbers;
        }

        public TreeMap<String, AllergiesClass> getAllFoundAllergies() {
            return allFoundAllergies;
        }

        public AnalyzeText(FrameLayout parentFrameLayout) {
            this.parentFrameLayout = parentFrameLayout;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            double i = ((double) values[1] / (double) values[0]) * 100;

            ((ProgressBar) parentFrameLayout.findViewById(R.id.progressBarSettings)).setProgress((int) i);
            // Do things like update the progress bar
        }

        private void setup(String[] fullTextArray) {
            algorithmAllergies = new AlgorithmAllergies();
            ArrayList<Locale> languages = LanguagesAccepted.getActiveLanguages(getContext());
            if (languages.isEmpty()) {
                languageIsSelected = false;
            }
            treeMapSplittedFullText = algorithmAllergies.FixStringAllStrings(fullTextArray);
            int counter = 0;
            int i = 0;
            allAllergies = new HashMap<>();
            allergies = CollectAllAllergies.getAllergies(getContext());
            for (int id : allergies) {

                algorithmAllergies.translateAllAllergies(id, allAllergies, languages, SettingsFragment.this);
                counter = publishProgressHelper(counter, i, allergies.size());
                i++;

            }
            if (allAllergies.isEmpty()) {
                allergiesSelected = false;
            }
            allFoundAllergies = algorithmAllergies.bkTree(treeMapSplittedFullText, allAllergies);
        }

        private int publishProgressHelper(int current, int looper, int size) {
            publishProgress(size, current);
            if ((looper % 3) == 0) {
                current++;
            }
            return current;
        }

        @Override
        protected ArrayList<AllergiesClass> doInBackground(String... strings) {
            String[] fullTextArray = strings[0].split("\\s+");
            setup(fullTextArray);

            int counter = fullTextArray.length / 3;
            int i = 0;
            ArrayList<AllergyList.E_Numbers> eNumbersArrayList = new AllergyList(getContext()).getArrayListE_Numbers();

            for (int j = 0; j < fullTextArray.length; j++) {
                counter = publishProgressHelper(counter, i, fullTextArray.length);
                i++;
                if (j + 1 < fullTextArray.length && fullTextArray.length != 1) {
                    String number = fullTextArray[j] + fullTextArray[j + 1].replaceAll("\\D+", "");
                    if (number.length() > 2 && fullTextArray[j].compareToIgnoreCase("e") == 0) {

                        algorithmAllergies.checkFullStringEnumbers(fullTextArray[j] + fullTextArray[j + 1], eNumbersArrayList, allFoundENumbers);
                    }
                }
                String number = fullTextArray[j].replaceAll("\\D+", "");
                if (number.length() > 2) {
                    algorithmAllergies.checkFullStringEnumbers(fullTextArray[j], eNumbersArrayList, allFoundENumbers);
                }


            }
            counter = (fullTextArray.length / 3) * 2;
            i = 0;
            for (String s : algorithmAllergies.FixStringAllStringsToCheckLast(fullTextArray)) {
                algorithmAllergies.checkFullString(s, allAllergies, allFoundAllergies);
                counter = publishProgressHelper(counter, i, fullTextArray.length);
                i++;
            }

            Collections.sort(allFoundENumbers);
            for (TreeSet<String> stringTreeSet : treeMapSplittedFullText.values()) {

                allStringsOrdered.addAll(stringTreeSet);
            }
            if(openToSaveStatistics){
                int j = defaultSharedPreferences.getInt(getFoundCount(), 0) + allFoundAllergies.size();
                edit.putInt(getFoundCount(),j).apply();
                j = defaultSharedPreferences.getInt(getFoundENumbers(), 0) + allFoundENumbers.size();
                edit.putInt(getFoundENumbers(),j).apply();

            }
            setupView();
            return null;
        }

        private void setupView() {
            final CardClassSetup linearCardClass = new CardClassSetup();
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            final ColorGradientPicker colorGradientPicker = new ColorGradientPicker();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final CardClassLayout warning = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.warning), R.drawable.history, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsWarnings)).optionalLinearSizeHorizontalHeight(200).buildCardClassLayout();
                    setupWarning(warning, linearCardClass);
                    final CardClassLayout foundAllergies = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.foundAllergies), R.drawable.wheatcircle, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsFoundAllergies)).optionalLinearSizeHorizontalHeight(200).buildCardClassLayout();
                    final CardClassLayout foundENumbers = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.foundEnumbers), R.drawable.crustaceans, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsFoundENumbers)).optionalLinearSizeHorizontalHeight(200).buildCardClassLayout();
                    final CardClassLayout textScanned = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.textScanned), R.drawable.focuson, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsScannedText)).optionalLinearSizeHorizontalHeight(200).buildCardClassLayout();
                    setupFoundAllergies(foundAllergies, linearCardClass);
                    setupFoundENumbers(foundENumbers, linearCardClass);
                    setupTextScanned(textScanned, linearCardClass);
                }
            });

        }

        private void setupWarning(CardClassLayout warning, CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(warning, CardClassSetup.explode());
            if (languageIsSelected && allergiesSelected) {
                warning.getParentLinearLayout().setVisibility(View.GONE);
            }
            if (!languageIsSelected) {
                linearCardClass.addView(warning, new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(R.string.noLanguageSelected)).buildCheckBoxLayout().getView());
            }
            if (!allergiesSelected) {
                linearCardClass.addView(warning, new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(R.string.noAllergies)).buildCheckBoxLayout().getView());
            }
        }

        private void setupFoundAllergies(final CardClassLayout foundAllergies, final CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(foundAllergies, CardClassSetup.explode());
            if (allFoundAllergies.isEmpty()) {
                foundAllergies.getParentLinearLayout().setVisibility(View.GONE);
            }
            foundAllergies.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (AllergiesClass allFoundAllergie : allFoundAllergies.values()) {
                        linearCardClass.addView(foundAllergies, new CheckBoxLayout.CheckBoxBuilder(getContext(), allFoundAllergie.getMotherAllergy()).optionalLastString(String.valueOf(allFoundAllergie.getFoundAllergies()+1)).buildCheckBoxLayout().getView());
                    }
                    linearCardClass.CardDefaultTransition(foundAllergies, CardClassSetup.explode());
                    foundAllergies.getLinearLayoutHorizontal().performClick();
                }
            });

        }

        private void setupFoundENumbers(final CardClassLayout foundENumbers, final CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(foundENumbers, CardClassSetup.explode());
            if (allFoundENumbers.isEmpty()) {
                foundENumbers.getParentLinearLayout().setVisibility(View.GONE);
            }
            foundENumbers.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (AllergyList.E_Numbers allFoundENumber : allFoundENumbers) {
                        linearCardClass.addView(foundENumbers, new CheckBoxLayout.CheckBoxBuilder(getContext(), allFoundENumber.getName()).buildCheckBoxLayout().getView());
                    }
                    linearCardClass.CardDefaultTransition(foundENumbers, CardClassSetup.explode());
                    foundENumbers.getLinearLayoutHorizontal().performClick();
                }
            });
        }

        private void setupTextScanned(final CardClassLayout textScanned, final CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(textScanned, CardClassSetup.explode());
            if (allStringsOrdered.isEmpty()) {
                textScanned.getParentLinearLayout().setVisibility(View.GONE);
            }
            textScanned.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (String s : allStringsOrdered) {
                        if(s.equals("")){
                            continue;
                        }
                        Log.d(TAG, "onClick: "+ s);
                        linearCardClass.addView(textScanned, new CheckBoxLayout.CheckBoxBuilder(getContext(), s).buildCheckBoxLayout().getView());
                    }
                    linearCardClass.CardDefaultTransition(textScanned, CardClassSetup.explode());
                    textScanned.getLinearLayoutHorizontal().performClick();
                }
            });

        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);
            parentFrameLayout.findViewById(R.id.progressBarSettings).setVisibility(View.GONE);
        }
    }
}
