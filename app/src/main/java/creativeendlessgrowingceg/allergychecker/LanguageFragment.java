package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import creativeendlessgrowingceg.allergychecker.billingmodule.billing.BillingManager;
import creativeendlessgrowingceg.allergychecker.billingmodule.billing.BillingProvider;
import creativeendlessgrowingceg.allergychecker.billingmodule.skulist.AcquireFragment;
import creativeendlessgrowingceg.allergychecker.subscription.SubscriptionsViewController;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LanguageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LanguageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LanguageFragment extends Fragment implements BillingProvider {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "LanguageFragment";
    // Tag for a dialog that allows us to find it when screen was rotated
    private static final String DIALOG_TAG = "dialog";
    private static final int BILLING_MANAGER_NOT_INITIALIZED = -1;
    String language;
    Locale myLocale;
    SharedPreferences preference;
    private File startPageFile;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;
    private OnFragmentInteractionListener mListener;
    private ArrayList<RadioButtons> radioButtons = new ArrayList<>();
    private ArrayList<CheckBoxes> checkBoxes = new ArrayList<>();
    private StartPage startpage;
    private BillingManager mBillingManager;
    private AcquireFragment mAcquireFragment;
    private SubscriptionsViewController mViewController;
    private ProgressBar parentProgressBar;


    public LanguageFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanguageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LanguageFragment newInstance(String param1, String param2) {
        LanguageFragment fragment = new LanguageFragment();
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
        startPageFile = new File(getActivity().getFilesDir(), "language.txt");
        preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Start the controller and load game data
        mViewController = new SubscriptionsViewController(this);

       /* if (get().startsWith(DEFAULT_PACKAGE_PREFIX)) {
            throw new RuntimeException("Please change the sample's package name!");
        }*/

        // Try to restore dialog fragment if we were showing it prior to screen rotation
        if (savedInstanceState != null) {
            mAcquireFragment = (AcquireFragment) getFragmentManager()
                    .findFragmentByTag(DIALOG_TAG);
        }

        // Create and initialize BillingManager which talks to BillingLibrary
        mBillingManager = new BillingManager(this, mViewController.getUpdateListener());

        preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = getLanguageFromLFragment(getContext());
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());


        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_settings, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linearLayoutLanguage);
        Button button = new Button(getContext());
        button.setText("Premium");
        button.getBackground().setColorFilter(0xFF19b3ad, PorterDuff.Mode.MULTIPLY);
        button.setTextSize(30);
        button.setTextColor(Color.WHITE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPurchaseButtonClicked();
            }
        });
        parentLinearLayout.addView(button);
        // parentProgressBar = (ProgressBar) parentLinearLayout.findViewById(R.id.progressBarLanguage);
        //parentLinearLayout.addView(addStaticLanguages(inflater,languages.getstaticArrayListLanguage()));
        parentLinearLayout.addView(addLanguages(inflater));

        for (CheckBoxes checkBox : checkBoxes) {
            if (checkBox.locale.getLanguage().equals(language)) {
                checkBox.checkBox.setChecked(true);
            }
        }


        return parentFrameLayout;
    }

    private LinearLayout addLanguages(LayoutInflater inflater) {
        final ArrayList<LinearLayout> arrayListLinearLayout = new ArrayList<>();
        final LinearLayout topLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
        final LinearLayout parentLinearLayout = (LinearLayout) topLinearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        ((ImageView) parentLinearLayout.findViewById(R.id.imageViewRowCategory)).setImageResource(R.drawable.translate);
        ((ImageView) parentLinearLayout.findViewById(R.id.dropDownList)).setVisibility(View.INVISIBLE);
        ((TextView) parentLinearLayout.findViewById(R.id.textViewCategory)).setText(TextHandler.capitalLetter(R.string.checkAll, getContext()));
        SharedPreferences settings = getActivity().getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (final Locale locale : LanguagesAccepted.getLanguages()) {
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
            ((ImageView) newLinearLayout.findViewById(R.id.imageViewRowCategory)).setImageResource(LanguagesAccepted.getFlag(locale.getLanguage()));
            ((ImageView) newLinearLayout.findViewById(R.id.dropDownList)).setVisibility(View.INVISIBLE);
            ((TextView) newLinearLayout.findViewById(R.id.textViewCategory)).setText(TextHandler.capitalLetter(LanguagesAccepted.getCountryName(locale.getLanguage()), getContext()));
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowCategory);
            checkBoxes.add(new CheckBoxes(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), checkBox, locale));
            checkBox.setChecked(settings.getBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isPremiumPurchased() || locale.getLanguage().equals("en") || locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                        editor.putBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), isChecked);
                        editor.apply();
                        checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
                        if (locale.getLanguage().equals(language)) {
                            checkBox.setChecked(true);
                            editor.putBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), true);
                            editor.apply();
                            checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
                        }
                    } else {
                        buttonView.setChecked(!isChecked);
                        onPurchaseButtonClicked();

                    }

                }


            });
            arrayListLinearLayout.add(newLinearLayout);
        }

        checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
        (parentLinearLayout.findViewById(R.id.dropDownList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v, arrayListLinearLayout, topLinearLayout);
            }
        });

        ((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)).
                setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (!isPremiumPurchased()) {
                            onPurchaseButtonClicked();
                            buttonView.setChecked(!isChecked);
                        } else{
                            for (LinearLayout linearLayout : arrayListLinearLayout) {
                                ((CheckBox) linearLayout.findViewById(R.id.checkBoxRowCategory)).setChecked(isChecked);
                            }
                            editor.putBoolean(String.valueOf(R.string.languageFrom), isChecked);
                            editor.apply();
                        }
                    }
                });
        for (LinearLayout linearLayout : arrayListLinearLayout) {
            topLinearLayout.addView(linearLayout);
        }
        for (int i = 0; i < 4; i++) {
            topLinearLayout.addView(new TextView(getContext()));
        }
        return topLinearLayout;
    }

    private void onPurchaseButtonClicked() {

        mAcquireFragment = new AcquireFragment();

        if (!isAcquireFragmentShown()) {
            mAcquireFragment.show(getFragmentManager(), DIALOG_TAG);

            if (mBillingManager != null && mBillingManager.getBillingClientResponseCode() > BILLING_MANAGER_NOT_INITIALIZED) {
                mAcquireFragment.onManagerReady(this);
            }
        }
    }

    public boolean isAcquireFragmentShown() {
        return mAcquireFragment != null && mAcquireFragment.isVisible();
    }

    private void checkIfParentCheckBoxShouldSwitch(CheckBox parentCheckBox, final SharedPreferences.Editor editor, final ArrayList<LinearLayout> arrayListLinearLayout) {

        for (CheckBoxes checkBox : checkBoxes) {

            if (!checkBox.checkBox.isChecked()) {
                parentCheckBox.setOnCheckedChangeListener(null);
                parentCheckBox.setChecked(false);
                /*editor.putBoolean(String.valueOf(R.string.languageFrom),false);
                editor.apply();*/
                parentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        for (LinearLayout linearLayout : arrayListLinearLayout) {
                            ((CheckBox) linearLayout.findViewById(R.id.checkBoxRowCategory)).setChecked(isChecked);
                        }
                        editor.putBoolean(String.valueOf(R.string.languageFrom), isChecked);
                        editor.apply();
                    }
                });
                return;
            }
        }
        parentCheckBox.setChecked(true);
    }

    public void saveCategories() {
        SharedPreferences sp = getActivity().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();


        Set<String> set = new ArraySet<>();

        for (CheckBoxes checkBox : checkBoxes) {
            if (checkBox.checkBox.isChecked()) {
                set.add(checkBox.locale.getLanguage());
            }
        }
        mEdit1.putStringSet("languageSet", set);
        mEdit1.apply();
    }

    public ArrayList<Locale> getCategories(StartPage startPage) {

        SharedPreferences sp = startPage.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("languageSet", new HashSet<String>());
        ArrayList<Locale> arrayList = new ArrayList<>();
        for (String s : set) {
            arrayList.add(new Locale(s));
        }
        return arrayList;
    }


    public void onclickDropDownList(View v, ArrayList<LinearLayout> arrayList, LinearLayout linearLayoutPar) {
        if (v.getRotation() == 180) {
            v.setRotation(0);
            for (LinearLayout linearLayout : arrayList) {
                linearLayoutPar.removeView(linearLayout);
            }
        } else {
            v.setRotation(180);

            for (LinearLayout linearLayout : arrayList) {
                linearLayoutPar.addView(linearLayout);

            }


        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCategories();
        mListener = null;
    }

    public String getLanguageFromLFragment(StartPage startPage) {

        ArrayList<Locale> languageAccepted = LanguagesAccepted.getLanguages();
        preference = PreferenceManager.getDefaultSharedPreferences(startPage);
        if (preference.contains("getLanguage")) {
            return preference.getString("getLanguage", "en");
        }
        for (Locale locale : languageAccepted) {
            if (locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(startPage).edit();
                sharedPreferencesEditor.putString("getLanguage", locale.getLanguage());
                sharedPreferencesEditor.apply();
                return Locale.getDefault().getLanguage();
            }

        }

        return "en";
    }

    public String getLanguageFromLFragment(Context startPage) {
        //Log.d(TAG,preference.getString("getLanguage",null));
        ArrayList<Locale> languageAccepted = LanguagesAccepted.getLanguages();
        Log.d(TAG, "StartPageLanguageFragment" + Locale.getDefault().getLanguage());
        preference = PreferenceManager.getDefaultSharedPreferences(startPage);
        if (preference.contains("getLanguage")) {
            return preference.getString("getLanguage", "en");
        }
        for (Locale locale : languageAccepted) {
            if (locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(startPage).edit();
                sharedPreferencesEditor.putString("getLanguage", locale.getLanguage());
                sharedPreferencesEditor.apply();
                return Locale.getDefault().getLanguage();
            }

        }

        return "en";
    }

    public void setGetLanguage(StartPage splashscreen, String language) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(splashscreen).edit();
        sharedPreferencesEditor.putString("getLanguage", language);
        sharedPreferencesEditor.apply();
    }

    @Override
    public BillingManager getBillingManager() {
        return mBillingManager;
    }

    @Override
    public boolean isPremiumPurchased() {
        return mViewController.isPremiumPurchased();
    }


    @Override
    public boolean isGoldMonthlySubscribed() {
        return mViewController.isGoldMonthlySubscribed();
    }

    @Override
    public boolean isGoldYearlySubscribed() {
        return mViewController.isGoldYearlySubscribed();
    }

    public void onBillingManagerSetupFinished() {
        if (mAcquireFragment != null) {
            mAcquireFragment.onManagerReady(this);
        }
    }

    /**
     * Remove loading spinner and refresh the UI
     */
    public void showRefreshedUi() {
        waitScreen(false);
        updateUi();
        if (mAcquireFragment != null) {
            mAcquireFragment.refreshUI();
        }
    }

    private void waitScreen(boolean b) {
        // parentProgressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    private void updateUi() {

    }

    public void setCategories(StartPage categories, Set<String> set, Set<Locale> locales) {
        SharedPreferences sp = categories.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putStringSet("languageSet", set);
        mEdit1.apply();
        SharedPreferences settings = categories.getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (Locale locale : locales) {
            editor.putBoolean(categories.getString(LanguagesAccepted.getCountryName(locale.getLanguage())), false);
            editor.apply();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class RadioButtons {
        String id;
        RadioButton radioButton;
        Locale staticLocale;

        public RadioButtons(String id, RadioButton radioButton, Locale staticLocale) {
            this.id = id;
            this.radioButton = radioButton;
            this.staticLocale = staticLocale;

        }
    }

    private class CheckBoxes {
        String id;
        CheckBox checkBox;
        Locale locale;

        public CheckBoxes(String id, CheckBox checkBox, Locale locale) {
            this.id = id;
            this.checkBox = checkBox;
            this.locale = locale;

        }
    }

}
