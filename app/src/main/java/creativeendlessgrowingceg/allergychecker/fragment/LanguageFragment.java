package creativeendlessgrowingceg.allergychecker.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import creativeendlessgrowingceg.allergychecker.LanguagesAccepted;
import creativeendlessgrowingceg.allergychecker.R;
import creativeendlessgrowingceg.allergychecker.TextHandler;
import creativeendlessgrowingceg.allergychecker.billingmodule.billing.BillingManager;
import creativeendlessgrowingceg.allergychecker.billingmodule.billing.BillingProvider;
import creativeendlessgrowingceg.allergychecker.billingmodule.skulist.AcquireFragment;
import creativeendlessgrowingceg.allergychecker.subscription.SubscriptionsViewController;

/**
 * language class inflate view.
 */
public class LanguageFragment extends Fragment implements BillingProvider {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "LanguageFragment";
    private static final String DIALOG_TAG = "dialog";
    private static final int BILLING_MANAGER_NOT_INITIALIZED = -1;
    String language;
    SharedPreferences preference;
    private String mParam1;
    private String mParam2;
    private HashSet<ImageView> imageViewHashSetToDestroy = new HashSet<>();
    private OnFragmentInteractionListener mListener;
    private ArrayList<CheckBoxes> checkBoxes = new ArrayList<>();
    private BillingManager mBillingManager;
    private AcquireFragment mAcquireFragment;
    private SubscriptionsViewController mViewController;


    public LanguageFragment() {
    }


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
    }

    /**
     * create view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewController = new SubscriptionsViewController(this);

        if (savedInstanceState != null) {
            mAcquireFragment = (AcquireFragment) getFragmentManager()
                    .findFragmentByTag(DIALOG_TAG);
        }
        mBillingManager = new BillingManager(this, mViewController.getUpdateListener());

        preference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = getLanguageFromLFragment(getContext());


        FrameLayout parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_settings, container, false);

        //insert everything to this linear layout
        LinearLayout parentLinearLayout = parentFrameLayout.findViewById(R.id.linearLayoutLanguage);
        /*Button button = new Button(getContext());
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
        parentLinearLayout.addView(button);*/
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        parentLinearLayout.addView(addLanguages(inflater));
        if(!sharedPreferences.getBoolean("FirstTimerLanguageFrag", false)){
            for (CheckBoxes checkBox : checkBoxes) {
                if (checkBox.locale.getLanguage().equals(language)) {
                    checkBox.checkBox.setChecked(true);
                }
            }
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            sharedPreferencesEditor.putBoolean(
                    "FirstTimerLanguageFrag", true);
            sharedPreferencesEditor.apply();
        }



        return parentFrameLayout;
    }

    /**
     * add languages from languages accepted
     */
    private LinearLayout addLanguages(LayoutInflater inflater) {
        final ArrayList<LinearLayout> arrayListLinearLayout = new ArrayList<>();
        final LinearLayout topLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
        final LinearLayout parentLinearLayout = topLinearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        ((ImageView) parentLinearLayout.findViewById(R.id.imageViewRowCategory)).setVisibility(View.INVISIBLE);
        parentLinearLayout.findViewById(R.id.dropDownList).setVisibility(View.INVISIBLE);
        ((TextView) parentLinearLayout.findViewById(R.id.textViewCategory)).setText(TextHandler.capitalLetter(R.string.checkAll, getContext()));
        SharedPreferences settings = getActivity().getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (final Locale locale : LanguagesAccepted.getLanguages(getContext())) {
            Log.d(TAG, "addLanguages: " + locale.getLanguage());
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
            ((ImageView) newLinearLayout.findViewById(R.id.imageViewRowCategory)).setImageResource(LanguagesAccepted.getFlag(locale.getLanguage()));
            imageViewHashSetToDestroy.add((ImageView) newLinearLayout.findViewById(R.id.imageViewRowCategory));
            newLinearLayout.findViewById(R.id.dropDownList).setVisibility(View.INVISIBLE);
            ((TextView) newLinearLayout.findViewById(R.id.textViewCategory)).setText(TextHandler.capitalLetter(LanguagesAccepted.getCountryName(locale.getLanguage()), getContext()));
            final CheckBox checkBox = newLinearLayout.findViewById(R.id.checkBoxRowCategory);
            checkBoxes.add(new CheckBoxes(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), checkBox, locale));
            checkBox.setChecked(settings.getBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), false));

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d(TAG, "isPremiumPurchased: "+ isPremiumPurchased());
                    //if (isPremiumPurchased() || locale.getLanguage().equals("en") || locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                        editor.putBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), isChecked);
                        editor.apply();
                        checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
               /*         if (locale.getLanguage().equals(language)) {
                            checkBox.setChecked(true);
                            editor.putBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), true);
                            editor.apply();
                            checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
                        }*/
                    //}
                    /*else {
                        buttonView.setChecked(!isChecked);
                        onPurchaseButtonClicked();

                    }*/

                }


            });
            arrayListLinearLayout.add(newLinearLayout);
        }

        checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);


        ((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)).
                setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        /*if (!isPremiumPurchased()) {
                            onPurchaseButtonClicked();
                            buttonView.setChecked(!isChecked);
                        } else {*/
                            for (LinearLayout linearLayout : arrayListLinearLayout) {
                                ((CheckBox) linearLayout.findViewById(R.id.checkBoxRowCategory)).setChecked(isChecked);
                            }
                            editor.putBoolean(String.valueOf(R.string.languageFrom), isChecked);
                            editor.apply();
                        //}
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

    /**
     * if purchase pressed begin flow
     */
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

    /**
     * check if all boxes is checked and parent should change
     *
     * @param parentCheckBox        to change
     * @param editor                to update status
     * @param arrayListLinearLayout to check if parent is checked
     */
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

                        /*if (!isPremiumPurchased()) {
                            onPurchaseButtonClicked();
                            buttonView.setChecked(!isChecked);
                        } else {*/
                            for (LinearLayout linearLayout : arrayListLinearLayout) {
                                ((CheckBox) linearLayout.findViewById(R.id.checkBoxRowCategory)).setChecked(isChecked);
                            }
                            editor.putBoolean(String.valueOf(R.string.languageFrom), isChecked);
                            editor.apply();
                        //}
                    }
                });
                return;
            }
        }
        parentCheckBox.setChecked(true);
    }

    /**
     * save categories
     */
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

    /**
     * get languages
     *
     * @param context context
     * @return locales
     */
    public ArrayList<Locale> getCategories(Context context) {

        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("languageSet", new HashSet<String>());
        ArrayList<Locale> arrayList = new ArrayList<>();
        for (String s : set) {
            arrayList.add(new Locale(s));
        }
        return arrayList;
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

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        for (ImageView imageView : imageViewHashSetToDestroy) {
            imageView.setImageDrawable(null);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }

    /**
     * save when pause
     */
    @Override
    public void onPause() {
        super.onPause();
        saveCategories();
        mListener = null;
    }


    /**
     * get language from language fragment
     *
     * @param context context
     * @return languages
     */
    public String getLanguageFromLFragment(Context context) {
        //Log.d(TAG,preference.getString("getLanguage",null));
        ArrayList<Locale> languageAccepted = LanguagesAccepted.getLanguages(context);
        preference = PreferenceManager.getDefaultSharedPreferences(context);
        if (preference.contains("getLanguage")) {
            return preference.getString("getLanguage", "en");
        }
        for (Locale locale : languageAccepted) {
            if (locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                sharedPreferencesEditor.putString("getLanguage", locale.getLanguage());
                sharedPreferencesEditor.apply();
                return Locale.getDefault().getLanguage();
            }

        }

        return "en";
    }

    /**
     * set languages from startpage
     *
     * @param startPage activity
     * @param language  to save
     */
    public void setGetLanguage(StartPage startPage, String language) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(startPage).edit();
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
        if (mAcquireFragment != null) {
            mAcquireFragment.refreshUI();
        }
    }


    /**
     * set selected languages from startpage
     *
     * @param context context
     * @param set     to save
     * @param locales to save
     */
    public void setCategories(StartPage context, Set<String> set, Set<Locale> locales) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.putStringSet("languageSet", set);
        mEdit1.apply();
        SharedPreferences settings = context.getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (Locale locale : locales) {
            editor.putBoolean(context.getString(LanguagesAccepted.getCountryName(locale.getLanguage())), false);
            editor.apply();
        }
    }

    public void saveDefaultLanguage(Activity activity) {
        for (Locale locale : LanguagesAccepted.getLanguages(activity.getBaseContext())) {
            if (locale.getLanguage().equals(Locale.getDefault().getLanguage())) {
                Log.d(TAG, "saveDefaultLanguage: ");
                SharedPreferences sp = activity.getSharedPreferences(TAG, Context.MODE_PRIVATE);
                SharedPreferences.Editor mEdit1 = sp.edit();


                Set<String> set = new ArraySet<>();

                set.add(String.valueOf(Locale.getDefault().getCountry()));
                mEdit1.putStringSet("languageSet", set);
                mEdit1.apply();
                return;
            }

        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * checkbox class
     */
    private class CheckBoxes {
        String id;
        CheckBox checkBox;
        Locale locale;

        CheckBoxes(String id, CheckBox checkBox, Locale locale) {
            this.id = id;
            this.checkBox = checkBox;
            this.locale = locale;

        }
    }

}
