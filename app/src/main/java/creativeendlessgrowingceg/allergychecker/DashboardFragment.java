package creativeendlessgrowingceg.allergychecker;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import creativeendlessgrowingceg.allergychecker.camera.OcrCaptureActivity;
import creativeendlessgrowingceg.allergychecker.design.activity.OnboardingPagerActivity;
import creativeendlessgrowingceg.allergychecker.fragment.HistoryFragment;
import creativeendlessgrowingceg.allergychecker.fragment.StartPage;

import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getFoundCount;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getScannedCount;
import static creativeendlessgrowingceg.allergychecker.APISharedPreference.getWordCount;


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
        ((ScrollView)inflate.findViewById(R.id.scrollViewDashboardFrag)).setSmoothScrollingEnabled(true);

        CardClassSetup linearCardClass = new CardClassSetup();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ColorGradientPicker colorGradientPicker = new ColorGradientPicker();


        CardClassLayout history = new CardClassLayout.CardClassLayoutBuilder(getContext(), "History", R.drawable.history, colorGradientPicker.ColorGradientPickerPick(5, atomicInteger.addAndGet(1)),(LinearLayout) inflate.findViewById(R.id.linearCardHistory)).optionalLinearSizeHorizontalHeight(300).buildCardClassLayout();
        CardClassLayout statistic = new CardClassLayout.CardClassLayoutBuilder(getContext(), "Statistic", R.drawable.star, colorGradientPicker.ColorGradientPickerPick(5, atomicInteger.addAndGet(1)),(LinearLayout) inflate.findViewById(R.id.linearCardStatistic)).optionalLinearSizeHorizontalHeight(300).buildCardClassLayout();
        CardClassLayout language = new CardClassLayout.CardClassLayoutBuilder(getContext(), "Language", R.drawable.language, colorGradientPicker.ColorGradientPickerPick(5, atomicInteger.addAndGet(1)),(LinearLayout) inflate.findViewById(R.id.linearCardLanguage)).optionalLinearSizeHorizontalHeight(300).buildCardClassLayout();
        CardClassLayout showAllergies = new CardClassLayout.CardClassLayoutBuilder(getContext(), "Allergies", R.drawable.wheatcircle, colorGradientPicker.ColorGradientPickerPick(5, atomicInteger.addAndGet(1)),(LinearLayout) inflate.findViewById(R.id.linearCardShowAllergies)).optionalLinearSizeHorizontalHeight(300).buildCardClassLayout();
        CardClassLayout camera = new CardClassLayout.CardClassLayoutBuilder(getContext(), "Camera", R.drawable.ic_menu_camera, colorGradientPicker.ColorGradientPickerPick(5, atomicInteger.addAndGet(1)),(LinearLayout) inflate.findViewById(R.id.linearCardCamera)).optionalLinearSizeHorizontalHeight(300).buildCardClassLayout();
        historySetup(history,linearCardClass);
        statisticSetup(statistic,linearCardClass);
        languageSetup(language,linearCardClass);
        showAllergiesSetup(showAllergies,linearCardClass);
        cameraSetup(camera,linearCardClass);


        return inflate;

    }




    private void showAllergiesSetup(final CardClassLayout showAllergies, final CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(showAllergies,CardClassSetup.explode());
        final SpinnerLayout spinner = new SpinnerLayout.SpinnerLayoutBuilder(getContext(),setupLanguages()).buildSpinnerLayout();

        linearCardClass.addView(showAllergies,spinner.getView());
        spinner.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public ArrayList<LinearLayout> linearLayoutTreeSet;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = parent.getItemAtPosition(position).toString().trim();
                Locale locale = spinner.getStringLocaleTreeMap().get(key);
                if(linearLayoutTreeSet != null){
                    if(!linearLayoutTreeSet.isEmpty()){
                        for (LinearLayout linearLayout : linearLayoutTreeSet) {
                            linearCardClass.removeView(showAllergies,linearLayout);
                        }
                    }
                }
                linearLayoutTreeSet= new ArrayList<>();
                TreeSet<String> stringTreeSet = new TreeSet<>();
                //TODO load ALL allergies
                /*for (Integer integer : new LoadUIAllergies().getAllergies(getContext())) {

                    stringTreeSet.add(LanguagesAccepted.getStringByLocalNoTakeAwaySpace(getActivity(),integer,locale.getLanguage()));
                }*/
                for (String string : stringTreeSet) {
                    LinearLayout view1 = new CheckBoxLayout.CheckBoxBuilder(getContext(), string).buildCheckBoxLayout().getView();
                    linearLayoutTreeSet.add(view1);

                    view1.setVisibility(View.VISIBLE);
                    linearCardClass.addView(showAllergies,view1);

                }
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
        linearCardClass.CardDefaultTransition(camera,CardClassSetup.explode());
        final CheckBoxLayout flash = new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(R.string.flash)).optionalCheckBox(APISharedPreference.getFlash()).buildCheckBoxLayout();
        final CheckBoxLayout focus = new CheckBoxLayout.CheckBoxBuilder(getContext(),getString(R.string.focus)).optionalCheckBox(APISharedPreference.getFocus()).buildCheckBoxLayout();

        final SliderLayout timeSleep = new SliderLayout.SliderLayoutBuilder(getContext(), getString(R.string.timeSleep),10).buildSliderLayout();
        linearCardClass.addView(camera,new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.camera), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), OcrCaptureActivity.class);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(getContext());
                intent.putExtra(APISharedPreference.getFlash(), sharedPreferences.getBoolean(APISharedPreference.getFlash(),false));
                intent.putExtra(APISharedPreference.getFocus(), sharedPreferences.getBoolean(APISharedPreference.getFocus(),true));
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
        linearCardClass.addView(camera, flash.getView());
        linearCardClass.addView(camera, focus.getView());
        linearCardClass.addView(camera, timeSleep.getView());

    }

    private void languageSetup(CardClassLayout language, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(language,CardClassSetup.explode());
        for (Locale locale : LanguagesAccepted.getLanguages(getContext())) {
            linearCardClass.addView(language, new CheckBoxLayout.CheckBoxBuilder(getContext(),
                    getString(LanguagesAccepted.getCountryName(locale.getLanguage()))).optionalImage(LanguagesAccepted.getFlag(locale.getLanguage())).optionalCheckBox(locale.getLanguage()).buildCheckBoxLayout().getView());
        }
    }

    private void statisticSetup(CardClassLayout statistic, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(statistic,CardClassSetup.explode());
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        linearCardClass.addView(statistic, new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(R.string.wordCount)).optionalLastString(sp.getString(getWordCount(), "0")).buildCheckBoxLayout().getView());
        linearCardClass.addView(statistic, new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(R.string.foundAllergiesCount)).optionalLastString(sp.getString(getFoundCount(), "0")).buildCheckBoxLayout().getView());
        linearCardClass.addView(statistic, new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(R.string.scannedCount)).optionalLastString(sp.getString(getScannedCount(), "0")).buildCheckBoxLayout().getView());


    }
    private void historySetup(CardClassLayout history, CardClassSetup linearCardClass) {
        linearCardClass.CardDefaultTransition(history,CardClassSetup.explode());
        ArrayList<String> arrayList = new DateAndHistory(getActivity()).getArrayFromHistory();
        Collections.sort(arrayList, new HistoryFragment.stringComparator());
        Collections.reverse(arrayList);
        for (String s : arrayList) {
            linearCardClass.addView(history, new CheckBoxLayout.CheckBoxBuilder(getContext(), s.substring(0, 20)).buildListener(historyCheckbox(s.substring(20))).optionalMarginBottom().buildCheckBoxLayout().getView());

        }

    }

    private View.OnClickListener historyCheckbox(final String substring) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(getActivity(), StartPage.class);
                intent.putExtra("HistoryFragment", substring);
                startActivity(intent);
                getActivity().finish();

            }
        };
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
