package creativeendlessgrowingceg.allergychecker;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import creativeendlessgrowingceg.allergychecker.camera.OcrCaptureActivity;
import creativeendlessgrowingceg.allergychecker.design.activity.OnboardingPagerActivity;
import creativeendlessgrowingceg.allergychecker.fragment.BottomNavigationName;
import creativeendlessgrowingceg.allergychecker.fragment.HistoryFragment;

import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getFoundCount;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getFoundENumbers;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getSpinnerPosition;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getWordCount;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.timesScanned;
import static creativeendlessgrowingceg.allergychecker.CheckBoxLayout.CheckBoxBuilder;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getFontColor;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getSpecificGradient;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Dashboard";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout inflate = (FrameLayout) inflater.inflate(R.layout.fragment_dashbord, container, false);
        ((ScrollView) inflate.findViewById(R.id.scrollViewDashboardFrag)).setSmoothScrollingEnabled(true);
        new SetupDashboardView(inflate).execute();

        return inflate;

    }


    private void showAllergiesSetup(final CardClassLayout showAllergies, final CardClassSetup linearCardClass) {

        linearCardClass.CardDefaultTransition(showAllergies, CardClassSetup.explode(), getContext());
        final SpinnerLayout spinner = new SpinnerLayout.SpinnerLayoutBuilder(getContext(), setupLanguages()).buildSpinnerLayout();

        linearCardClass.addView(showAllergies, spinner.getView());

        spinner.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public ArrayList<LinearLayout> linearLayoutTreeSet;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = parent.getItemAtPosition(position).toString().trim();
                Locale locale = spinner.getStringLocaleTreeMap().get(key);
                if (linearLayoutTreeSet != null) {
                    if (!linearLayoutTreeSet.isEmpty()) {
                        for (LinearLayout linearLayout : linearLayoutTreeSet) {
                            linearCardClass.removeView(showAllergies, linearLayout);
                        }
                    }
                }
                linearLayoutTreeSet = new ArrayList<>();
                TreeSet<String> stringTreeSet = new TreeSet<>();
                for (Integer integer : CollectAllAllergies.getAllergies(getContext())) {
                    stringTreeSet.add(TextHandler.cutFirstWord(TextHandler.capitalLetter(LanguagesAccepted.getStringByLocalNoTakeAwaySpace(getActivity(), integer, locale.getLanguage()))));
                }
                for (String string : stringTreeSet) {
                    /*LinearLayout view1 = new CheckBoxBuilder(getContext(), string).buildCheckBoxLayout().getView();
                    linearLayoutTreeSet.add(view1);

                    view1.setVisibility(View.VISIBLE);
                    linearCardClass.addView(showAllergies, view1);*/
                    TextView textView = new TextView(getContext());
                    textView.setText(string);
                    textView.setTextColor(getFontColor(getContext()));
                    textView.setTextSize(22);
                    textView.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "yatra.ttf"));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.leftMargin = 110;
                    textView.setLayoutParams(layoutParams);
                    linearCardClass.addView(showAllergies, textView);
                }
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt(getSpinnerPosition(), position).apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private TreeMap<String, Locale> setupLanguages() {
        ArrayList<Locale> languages = LanguagesAccepted.getLanguages(getContext());
        final TreeMap<String, Locale> languageString = new TreeMap<>();
        for (Locale language : languages) {
            languageString.put(getString(LanguagesAccepted.getCountryName(language.getLanguage())), language);
        }
        return languageString;
    }

    private void cameraSetup(CardClassLayout camera, CardClassSetup linearCardClass) {
        //add button
        linearCardClass.CardDefaultTransition(camera, CardClassSetup.explode(), getContext());
        final CheckBoxLayout flash = new CheckBoxBuilder(getContext(), getString(R.string.flash)).optionalCheckBox(APISharedPreference.getFlash()).buildCheckBoxLayout();
        final CheckBoxLayout focus = new CheckBoxBuilder(getContext(), getString(R.string.focus)).optionalCheckBox(APISharedPreference.getFocus()).buildCheckBoxLayout();

        final SliderLayout timeSleep = new SliderLayout.SliderLayoutBuilder(getContext(), getString(R.string.timeSleep), 10).buildSliderLayout();
        linearCardClass.addView(camera, flash.getView());
        linearCardClass.addView(camera, focus.getView());
        linearCardClass.addView(camera, timeSleep.getView());
        linearCardClass.addView(camera, new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.camera), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), OcrCaptureActivity.class);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getContext());
                intent.putExtra(APISharedPreference.getFlash(), sharedPreferences.getBoolean(APISharedPreference.getFlash(), false));
                intent.putExtra(APISharedPreference.getFocus(), sharedPreferences.getBoolean(APISharedPreference.getFocus(), true));
                intent.putExtra(APISharedPreference.getTimeSleep(), sharedPreferences.getInt(APISharedPreference.getTimeSleep(), 5));
                getContext().startActivity(intent);

                if (!sharedPreferences.getBoolean(APISharedPreference.getFirstTime(), false)) {
                    startActivity(new Intent(getContext(), OnboardingPagerActivity.class));
                    SharedPreferences.Editor sharedPreferencesEditor =
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    sharedPreferencesEditor.putBoolean(
                            APISharedPreference.getFirstTime(), true);
                    sharedPreferencesEditor.apply();

                }
            }
        }).buildButtonLayout().getView());
        linearCardClass.addView(camera, new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.write), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.inputIngredients);
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!input.getText().toString().equals("")) {
                            Intent intent = new Intent(getActivity(), BottomNavigationName.class);
                            intent.putExtra(APISharedPreference.getScannedText, input.getText().toString());
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), getString(R.string.emptyString), Toast.LENGTH_LONG).show();
                        }


                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        }).buildButtonLayout().getView());

    }

    private void languageSetup(CardClassLayout language, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(language, CardClassSetup.explode(), getContext());
        for (Locale locale : LanguagesAccepted.getLanguages(getContext())) {
            linearCardClass.addView(language, new CheckBoxBuilder(getContext(),
                    getString(LanguagesAccepted.getCountryName(locale.getLanguage()))).optionalImage(LanguagesAccepted.getFlag(locale.getLanguage())).optionalCheckBox(locale.getLanguage()).buildCheckBoxLayout().getView());
        }
    }

    private void statisticSetup(CardClassLayout statistic, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(statistic, CardClassSetup.explode(), getContext());
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        final CheckBoxLayout checkBoxLayout = new CheckBoxBuilder(getContext(), getString(R.string.wordCount)).optionalLastString(String.valueOf(sp.getInt(getWordCount(), 0))).buildCheckBoxLayout();
        final CheckBoxLayout checkBoxLayout1 = new CheckBoxBuilder(getContext(), getString(R.string.foundAllergies)).optionalLastString(String.valueOf(sp.getInt(getFoundCount(), 0))).buildCheckBoxLayout();
        final CheckBoxLayout checkBoxLayout2 = new CheckBoxBuilder(getContext(), getString(R.string.foundEnumbers)).optionalLastString(String.valueOf(sp.getInt(getFoundENumbers(), 0))).buildCheckBoxLayout();
        final CheckBoxLayout checkBoxLayout3 = new CheckBoxBuilder(getContext(), getString(R.string.timesScanned)).optionalLastString(String.valueOf(sp.getInt(timesScanned, 0))).buildCheckBoxLayout();
        final CheckBoxLayout checkBoxLayout4 = new CheckBoxBuilder(getContext(), getString(R.string.foundAllergiesDividedByTimesScanned)).optionalLastString(getAllergiesFoundDividedByTimesScanned(sp)).buildCheckBoxLayout();
        final CheckBoxLayout checkBoxLayout5 = new CheckBoxBuilder(getContext(), getString(R.string.wordsScannedDividedByTimesScanned)).optionalLastString(getWordsScannedDividedByTimesScanned(sp)).buildCheckBoxLayout();
        final CheckBoxLayout checkBoxLayout6 = new CheckBoxBuilder(getContext(), getString(R.string.wordsScannedDividedByFoundAllergies)).optionalLastString(getWordsScannedDividedByFoundAllergies(sp)).buildCheckBoxLayout();
        linearCardClass.addView(statistic, checkBoxLayout.getView());
        linearCardClass.addView(statistic, checkBoxLayout1.getView());
        linearCardClass.addView(statistic, checkBoxLayout2.getView());
        linearCardClass.addView(statistic, checkBoxLayout3.getView());
        linearCardClass.addView(statistic, checkBoxLayout4.getView());
        linearCardClass.addView(statistic, checkBoxLayout5.getView());
        linearCardClass.addView(statistic, checkBoxLayout6.getView());
        linearCardClass.addView(statistic, new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.delete), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(APISharedPreference.getWordCount(), 0);
                edit.putInt(APISharedPreference.getFoundCount(), 0);
                edit.putInt(APISharedPreference.getFoundENumbers(), 0);
                edit.putInt(timesScanned, 0);
                checkBoxLayout.getLastStringTextView().setText(String.valueOf(0));
                checkBoxLayout1.getLastStringTextView().setText(String.valueOf(0));
                checkBoxLayout2.getLastStringTextView().setText(String.valueOf(0));
                checkBoxLayout3.getLastStringTextView().setText(String.valueOf(0));
                checkBoxLayout4.getLastStringTextView().setText(String.valueOf(0));
                checkBoxLayout5.getLastStringTextView().setText(String.valueOf(0));
                checkBoxLayout6.getLastStringTextView().setText(String.valueOf(0));

                edit.apply();
            }
        }).buildButtonLayout().getView());


    }

    private String getWordsScannedDividedByFoundAllergies(SharedPreferences sp) {
        int anInt = sp.getInt(getFoundCount(), 0);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (anInt == 0)
            return "0";
        return df.format((float) sp.getInt(getWordCount(), 0) / anInt);
    }

    private String getWordsScannedDividedByTimesScanned(SharedPreferences sp) {
        int anInt = sp.getInt(timesScanned, 0);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (anInt == 0)
            return "0";
        return df.format((float)sp.getInt(getWordCount(), 0) / anInt);
    }

    private String getAllergiesFoundDividedByTimesScanned(SharedPreferences sp) {
        int anInt = sp.getInt(timesScanned, 0);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (anInt == 0)
            return "0";
        return df.format((float)sp.getInt(getFoundCount(), 0) / anInt);
    }

    private void historySetup(CardClassLayout history, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(history, CardClassSetup.explode(), getContext());
        ArrayList<String> arrayList = new DateAndHistory(getActivity()).getArrayFromHistory();
        Collections.sort(arrayList, new HistoryFragment.stringComparator());
        Collections.reverse(arrayList);
        for (String s : arrayList) {
            linearCardClass.addView(history, new CheckBoxBuilder(getContext(), s.substring(0, 20)).buildListener(historyCheckbox(s.substring(20))).optionalOnlyFirstString().buildCheckBoxLayout().getView());

        }


    }

    private View.OnClickListener historyCheckbox(final String substring) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(getActivity(), BottomNavigationName.class);
                intent.putExtra(APISharedPreference.getHistory, substring);
                startActivity(intent);
                getActivity().finish();

            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void themeSetup(CardClassLayout theme, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(theme, CardClassSetup.explode(), getContext());
        linearCardClass.addView(theme, new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.defaultTheme), themeClick(ConfigureTheme.defaultTheme)).optionalGradient(getSpecificGradient(getContext(), ConfigureTheme.defaultTheme)).buildButtonLayout().getView());
        linearCardClass.addView(theme, new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.fireTheme), themeClick(ConfigureTheme.fireTheme)).optionalGradient(getSpecificGradient(getContext(), ConfigureTheme.fireTheme)).buildButtonLayout().getView());
        linearCardClass.addView(theme, new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.plainTheme), themeClick(ConfigureTheme.plainTheme)).optionalGradient(getSpecificGradient(getContext(), ConfigureTheme.plainTheme)).optionalButtonTextColor(Color.BLACK).buildButtonLayout().getView());

    }

    private View.OnClickListener themeClick(final int theme) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt(APISharedPreference.theme, theme).apply();
                getActivity().finish();
                Intent intent = new Intent(getContext(), BottomNavigationName.class);
                getContext().startActivity(intent);
            }
        };
    }

    private void socialSetup(CardClassLayout social, CardClassSetup linearCardClass) {

        linearCardClass.CardDefaultTransition(social, CardClassSetup.explode(), getContext());
        linearCardClass.addView(social, new CheckBoxBuilder(getContext(), getString(R.string.allergyInsta)).optionalOnlyFirstString().optionalAddAutoLink().optionalImage(R.drawable.insta).buildCheckBoxLayout().getView());
        linearCardClass.addView(social, new CheckBoxBuilder(getContext(), getString(R.string.crengrInsta)).optionalOnlyFirstString().optionalAddAutoLink().optionalImage(R.drawable.insta).buildCheckBoxLayout().getView());

    }

    private class SetupDashboardView extends AsyncTask<Object, Object, Object> {

        private FrameLayout parentFrameLayout;


        public SetupDashboardView(FrameLayout parentFrameLayout) {

            this.parentFrameLayout = parentFrameLayout;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            final CardClassSetup linearCardClass = new CardClassSetup();
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            final ColorGradientPicker colorGradientPicker = new ColorGradientPicker();

            if (isAdded() && getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdded() && getActivity() != null) {
                            final CardClassLayout history = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.history), R.drawable.history, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardHistory)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
                            final CardClassLayout camera = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.scan), R.drawable.ic_menu_camera, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardCamera)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
                            final CardClassLayout statistic = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.statistics), R.drawable.stats, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardStatistic)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
                            final CardClassLayout language = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.language), R.drawable.language, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardLanguage)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
                            final CardClassLayout showAllergies = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.showAllergies), R.drawable.wheatcircle, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardShowAllergies)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
                            final CardClassLayout theme = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.theme), R.drawable.star, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsTheme)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();
                            final CardClassLayout social = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(R.string.social), R.drawable.group, colorGradientPicker.ColorGradientPickerPick(7, atomicInteger.addAndGet(1), getContext()), (LinearLayout) parentFrameLayout.findViewById(R.id.linearCardSettingsSocial)).optionalLinearSizeHorizontalHeight(75).buildCardClassLayout();

                            historySetup(history, linearCardClass);
                            statisticSetup(statistic, linearCardClass);
                            languageSetup(language, linearCardClass);
                            showAllergiesSetup(showAllergies, linearCardClass);
                            cameraSetup(camera, linearCardClass);
                            themeSetup(theme, linearCardClass);
                            socialSetup(social, linearCardClass);
                        }
                    }

                });
            }


            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            parentFrameLayout.findViewById(R.id.progressBarDashboard).setVisibility(View.GONE);
        }

    }
}

