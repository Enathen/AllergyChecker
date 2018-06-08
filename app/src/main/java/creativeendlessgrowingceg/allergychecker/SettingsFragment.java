package creativeendlessgrowingceg.allergychecker;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.TextView;
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
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.timesScanned;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getFontColor;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getPrimaryColor;
import static creativeendlessgrowingceg.allergychecker.LanguagesAccepted.getFlag;


public class SettingsFragment extends Fragment {


    private String string;
    private SharedPreferences defaultSharedPreferences;
    private SharedPreferences.Editor edit;
    private Boolean openToSaveStatistics = true;
    private Context mContext;

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
        Bundle arguments = getArguments();
        if (getContext() != null) {
            mContext = getContext();
        }
        if (arguments != null) {
            string = arguments.getString(APISharedPreference.getScannedText);

            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            edit = defaultSharedPreferences.edit();
            if (string == null) {
                openToSaveStatistics = false;
                string = arguments.getString(APISharedPreference.getHistory);
            }
            if (openToSaveStatistics) {

                int i = defaultSharedPreferences.getInt(timesScanned, 0) + 1;
                edit.putInt(timesScanned, i);
                edit.putInt(getWordCount(), defaultSharedPreferences.getInt(getWordCount(), 0) + string.split("\\s+").length);
                edit.apply();
            }
        } else {
            openToSaveStatistics = false;
            string = new DateAndHistory(mContext).getTop();
            if (string != null) {
                Toast.makeText(mContext, R.string.lastScannedText, Toast.LENGTH_LONG).show();
                string = string.substring(20);
            } else {
                Toast.makeText(mContext, R.string.noScanned, Toast.LENGTH_LONG).show();
            }

        }
        if (string != null) {
            Log.d(TAG, "swag: " + isAdded() + getActivity());
            if (isAdded() && getActivity() != null) {
                new AnalyzeText(inflate).execute(string);
            }

        } else {
            inflate.findViewById(R.id.progressBarSettings).setVisibility(View.GONE);
        }
        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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

        public AnalyzeText(FrameLayout parentFrameLayout) {
            this.parentFrameLayout = parentFrameLayout;
        }

        public ArrayList<AllergyList.E_Numbers> getAllFoundENumbers() {
            return allFoundENumbers;
        }

        public TreeMap<String, AllergiesClass> getAllFoundAllergies() {
            return allFoundAllergies;
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
            ArrayList<Locale> languages = LanguagesAccepted.getActiveLanguages(mContext);
            if (languages.isEmpty()) {
                languageIsSelected = false;
            }
            treeMapSplittedFullText = algorithmAllergies.FixStringAllStrings(fullTextArray);
            int counter = 0;
            int i = 0;
            allAllergies = new HashMap<>();
            allergies = CollectAllAllergies.getAllergies(mContext);

            for (int id : allergies) {

                algorithmAllergies.translateAllAllergies(id, allAllergies, languages, mContext);
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
            ArrayList<AllergyList.E_Numbers> eNumbersArrayList = new AllergyList(mContext).getArrayListE_Numbers();

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
            if (openToSaveStatistics) {
                int j = 0;
                for (AllergiesClass allergiesClass : allFoundAllergies.values()) {
                    j += allergiesClass.getAllergiesClasses().size();
                }
                j += defaultSharedPreferences.getInt(getFoundCount(), 0);
                edit.putInt(getFoundCount(), j);
                j = defaultSharedPreferences.getInt(getFoundENumbers(), 0) + allFoundENumbers.size();
                edit.putInt(getFoundENumbers(), j);
                edit.apply();
            }


            return null;
        }

        private void setupView() {
            final CardClassSetup linearCardClass = new CardClassSetup();
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            final ColorGradientPicker colorGradientPicker = new ColorGradientPicker();
            final CardClassLayout warning = new CardClassLayout.CardClassLayoutBuilder(mContext, getString(R.string.warning), R.drawable.warning, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), mContext), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsWarnings)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
            setupWarning(warning, linearCardClass);
            final CardClassLayout foundAllergies = new CardClassLayout.CardClassLayoutBuilder(mContext, getString(R.string.foundAllergies), R.drawable.wheatcircle, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), mContext), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsFoundAllergies)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
            final CardClassLayout foundENumbers = new CardClassLayout.CardClassLayoutBuilder(mContext, getString(R.string.foundEnumbers), R.drawable.enumber, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), mContext), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsFoundENumbers)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
            final CardClassLayout textScanned = new CardClassLayout.CardClassLayoutBuilder(mContext, getString(R.string.textScanned), R.drawable.focuson, colorGradientPicker.ColorGradientPickerPick(4, atomicInteger.addAndGet(1), mContext), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsScannedText)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
            setupFoundAllergies(foundAllergies, linearCardClass);
            setupFoundENumbers(foundENumbers, linearCardClass);
            setupTextScanned(textScanned, linearCardClass);

        }

        private void setupWarning(CardClassLayout warning, CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(warning, CardClassSetup.explode(), mContext);
            if (languageIsSelected && allergiesSelected) {
                warning.getParentLinearLayout().setVisibility(View.GONE);
            }
            if (!languageIsSelected) {
                linearCardClass.addView(warning, new CheckBoxLayout.CheckBoxBuilder(mContext, getString(R.string.noLanguageSelected)).buildCheckBoxLayout().getView());
            }
            if (!allergiesSelected) {
                linearCardClass.addView(warning, new CheckBoxLayout.CheckBoxBuilder(mContext, getString(R.string.noAllergies)).buildCheckBoxLayout().getView());
            }
        }

        private void setupFoundAllergies(final CardClassLayout foundAllergies, final CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(foundAllergies, CardClassSetup.explode(), mContext);
            if (allFoundAllergies.isEmpty()) {
                foundAllergies.getTextViewHorizontal().setText(getString(R.string.noAllergiesFound));
                foundAllergies.getTextViewVertical().setText(getString(R.string.noAllergiesFound));
            }
            foundAllergies.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (AllergiesClass allFoundAllergie : allFoundAllergies.values()) {
                        ArrayList<AllergiesClass> allergiesClasses = allFoundAllergie.getAllergiesClasses();

                        if (allergiesClasses.size() > 0) {
                            CardClassLayout newFoundAllergies = new CardClassLayout.CardClassLayoutBuilder(mContext, allFoundAllergie.getMotherAllergy().concat(" " + String.valueOf(allFoundAllergie.getFoundAllergies())), R.drawable.wheatcircle, getPrimaryColor(getContext())).optionalLinearSizeHorizontalHeight(56).buildCardClassLayout();
                            linearCardClass.addView(foundAllergies, newFoundAllergies.getParentLinearLayout());
                            final CardClassSetup linearCardClass = new CardClassSetup();
                            linearCardClass.CardDefaultTransition(newFoundAllergies, CardClassSetup.explode(), mContext);
                            for (AllergiesClass allergiesClass : allergiesClasses) {
                                linearCardClass.addView(newFoundAllergies, new CheckBoxLayout.CheckBoxBuilder(mContext, allergiesClass.getMotherAllergy()).optionalBiggerLeftString().optionalImage(getFlag(allergiesClass.getLanguage())).optionalLastString(allergiesClass.getNameOfWordFound()).buildCheckBoxLayout().getView());
                            }
                        } else {

                            linearCardClass.addView(foundAllergies, new CheckBoxLayout.CheckBoxBuilder(mContext, allFoundAllergie.getMotherAllergy()).optionalLastString(String.valueOf(allFoundAllergie.getFoundAllergies() + 1)).buildCheckBoxLayout().getView());
                        }
                    }
                    linearCardClass.CardDefaultTransition(foundAllergies, CardClassSetup.explode(), mContext);
                    foundAllergies.getLinearLayoutHorizontal().performClick();
                }
            });

        }

        private void setupFoundENumbers(final CardClassLayout foundENumbers, final CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(foundENumbers, CardClassSetup.explode(), mContext);
            if (allFoundENumbers.isEmpty()) {
                foundENumbers.getParentLinearLayout().setVisibility(View.GONE);
            }

            foundENumbers.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (AllergyList.E_Numbers allFoundENumber : allFoundENumbers) {
                        CardClassLayout newFoundAllergies = new CardClassLayout.CardClassLayoutBuilder(mContext, allFoundENumber.getId().concat(" " + allFoundENumber.getName()), R.drawable.enumber, getPrimaryColor(getContext())).optionalLinearSizeHorizontalHeight(56).buildCardClassLayout();
                        linearCardClass.addView(foundENumbers, newFoundAllergies.getParentLinearLayout());
                        final CardClassSetup linearCardClass = new CardClassSetup();
                        linearCardClass.CardDefaultTransition(newFoundAllergies, CardClassSetup.explode(), mContext);
                        linearCardClass.addView(newFoundAllergies, new CheckBoxLayout.CheckBoxBuilder(mContext, allFoundENumber.getInformation()).optionalOnlyFirstString().optionalRemoveImage().buildCheckBoxLayout().getView());
                        linearCardClass.addView(newFoundAllergies, new CheckBoxLayout.CheckBoxBuilder(mContext, allFoundENumber.getUrl()).optionalOnlyFirstString().optionalRemoveImage().optionalAddAutoLink().buildCheckBoxLayout().getView());

                    }
                    linearCardClass.CardDefaultTransition(foundENumbers, CardClassSetup.explode(), mContext);
                    foundENumbers.getLinearLayoutHorizontal().performClick();
                }
            });
        }

        private void setupTextScanned(final CardClassLayout textScanned, final CardClassSetup linearCardClass) {
            linearCardClass.CardDefaultTransition(textScanned, CardClassSetup.explode(), mContext);
            if (allStringsOrdered.isEmpty()) {
                textScanned.getParentLinearLayout().setVisibility(View.GONE);
            }
            textScanned.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (String s : allStringsOrdered) {
                        if (s.equals("")) {
                            continue;
                        }
                        TextView textView = new TextView(mContext);
                        textView.setText(s);
                        textView.setTextColor(getFontColor(mContext));
                        textView.setVisibility(View.GONE);
                        textView.setTextSize(22);
                        textView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "yatra.ttf"));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        layoutParams.leftMargin = 110;
                        textView.setLayoutParams(layoutParams);
                        linearCardClass.addView(textScanned, textView);
                    }
                    linearCardClass.CardDefaultTransition(textScanned, CardClassSetup.explode(), mContext);
                    textScanned.getLinearLayoutHorizontal().performClick();
                }
            });
        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);
            if (isAdded() && getActivity() != null) {
                setupView();
            }
            parentFrameLayout.findViewById(R.id.progressBarSettings).setVisibility(View.GONE);
        }
    }
}
