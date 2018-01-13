package creativeendlessgrowingceg.allergychecker;

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
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "LanguageFragment";
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

    public SettingsFragment(StartPage startPage) {
        startPageFile = new File(startPage.getFilesDir(), "language.txt");
        this.startpage = startPage;
        preference = PreferenceManager.getDefaultSharedPreferences(startPage);
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    public SettingsFragment(Context context) {
        startPageFile = new File(context.getFilesDir(), "language.txt");
        preference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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

        preference = PreferenceManager.getDefaultSharedPreferences(getContext());
        language = getLanguageFromLFragment(getContext());
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());


        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_settings, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linearLayoutLanguage);
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
        ((TextView) parentLinearLayout.findViewById(R.id.textViewCategory)).setText(R.string.checkAll);
        SharedPreferences settings = getContext().getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (final Locale locale : LanguagesAccepted.getLanguages()) {
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
            ((ImageView) newLinearLayout.findViewById(R.id.imageViewRowCategory)).setImageResource(LanguagesAccepted.getFlag(locale.getLanguage()));
            ((ImageView) newLinearLayout.findViewById(R.id.dropDownList)).setVisibility(View.INVISIBLE);
            ((TextView) newLinearLayout.findViewById(R.id.textViewCategory)).setText(LanguagesAccepted.getCountryName(locale.getLanguage()));
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowCategory);
            checkBoxes.add(new CheckBoxes(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), checkBox, locale));
            checkBox.setChecked(settings.getBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editor.putBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), isChecked);
                    editor.apply();

                    checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
                    if (locale.getLanguage().equals(language)) {
                        checkBox.setChecked(true);
                        editor.putBoolean(getString(LanguagesAccepted.getCountryName(locale.getLanguage())), true);
                        editor.apply();
                        checkIfParentCheckBoxShouldSwitch(((CheckBox) parentLinearLayout.findViewById(R.id.checkBoxRowCategory)), editor, arrayListLinearLayout);
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
                        for (LinearLayout linearLayout : arrayListLinearLayout) {
                            ((CheckBox) linearLayout.findViewById(R.id.checkBoxRowCategory)).setChecked(isChecked);
                        }
                        editor.putBoolean(String.valueOf(R.string.languageFrom), isChecked);
                        editor.apply();

                    }
                });
        for (LinearLayout linearLayout : arrayListLinearLayout) {
            topLinearLayout.addView(linearLayout);
        }
        return topLinearLayout;
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
        SharedPreferences sp = getActivity().getSharedPreferences("LanguageFragment", Context.MODE_PRIVATE);
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

    public ArrayList<Locale> getCategories() {
        SharedPreferences sp = startpage.getSharedPreferences("LanguageFragment", Context.MODE_PRIVATE);
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("languageSet", new HashSet<String>());
        ArrayList<Locale> arrayList = new ArrayList<>();
        for (String s : set) {
            arrayList.add(new Locale(s));
        }
        return arrayList;
    }

    public void onRadioButtonClicked(View view, SharedPreferences.Editor editor) {
        // Is the button now checked?
        RadioButton rbutton = ((RadioButton) view);
        for (RadioButtons radioButton : radioButtons) {
            if (!radioButton.radioButton.equals(rbutton)) {
                radioButton.radioButton.setChecked(false);
            }

            editor.putBoolean(radioButton.id, radioButton.radioButton.isChecked());
            editor.apply();
        }
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

        ArrayList<Locale> languageAccepted = LanguagesAccepted.getInstance().getLanguages();

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
        ArrayList<Locale> languageAccepted = LanguagesAccepted.getInstance().getLanguages();
        Log.d(TAG, "StartPageLanguageFragment" + Locale.getDefault().getLanguage());
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
