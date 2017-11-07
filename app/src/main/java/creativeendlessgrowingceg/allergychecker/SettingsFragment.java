package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;


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
    private File startPageFile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String language;
    Locale myLocale;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;
    private OnFragmentInteractionListener mListener;
    private ArrayList<RadioButtons> radioButtons = new ArrayList<>();
    private ArrayList<CheckBoxes> checkBoxes = new ArrayList<>();
    SharedPreferences preference;

    public SettingsFragment(Splashscreen startPage) {
        startPageFile = new File(startPage.getFilesDir(),"language.txt");
        preference = PreferenceManager.getDefaultSharedPreferences(startPage);
    }
    public SettingsFragment(StartPage startPage) {
        startPageFile = new File(startPage.getFilesDir(),"language.txt");
        preference = PreferenceManager.getDefaultSharedPreferences(startPage);
    }
    public SettingsFragment() {
        // Required empty public constructor
    }

    public SettingsFragment(Context context) {
        startPageFile = new File(context.getFilesDir(),"language.txt");
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
        final String lang = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("getLanguage", Locale.getDefault().getLanguage());
        final Locale newLocale = new Locale(lang);
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = getContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        // Check if we need to display our OnboardingFragment
        if (!sharedPreferences.getBoolean(
                "startHistory", false)) {
            language = getResources().getConfiguration().getLocales().get(0).getLanguage();
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
            sharedPreferencesEditor.putBoolean(
                    "startHistory", true);
            sharedPreferencesEditor.apply();
        }else{
            language = sharedPreferences.getString("getLanguage",null);

        }


        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_settings, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linearLayoutLanguage);
        Languages languages = new Languages(getContext());

        parentLinearLayout.addView(addStaticLanguages(inflater,languages.getstaticArrayListLanguage()));
        parentLinearLayout.addView(addLanguages(inflater,languages.getArrayListLanguage()));
        for (RadioButtons radioButton : radioButtons) {
            if(radioButton.staticLocale.getLanguage().equals(language)){
                radioButton.radioButton.setChecked(true);
            }
        }

        for (CheckBoxes checkBox : checkBoxes) {
            if(checkBox.locale.getLanguage().equals(language)){
                checkBox.checkBox.setChecked(true);
            }
        }



        return parentFrameLayout;
    }

    private LinearLayout addLanguages(LayoutInflater inflater, ArrayList<Languages.LanguagesClass> arrayListLanguage) {
        final ArrayList<LinearLayout> arrayListLinearLayout = new ArrayList<>();
        final LinearLayout topLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout,null);
        final LinearLayout parentLinearLayout = (LinearLayout) topLinearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        ((ImageView)parentLinearLayout.findViewById(R.id.imageViewRowCategory)).setImageResource(R.drawable.translate);
        ((TextView)parentLinearLayout.findViewById(R.id.textViewCategory)).setText(R.string.languageFrom);
        SharedPreferences settings = getContext().getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (final Languages.LanguagesClass languagesClass : arrayListLanguage) {
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout,null);
            ((ImageView)newLinearLayout.findViewById(R.id.imageViewLeftMargin)).setImageResource(languagesClass.picture);
            ((TextView)newLinearLayout.findViewById(R.id.textViewLeftMargin)).setText(languagesClass.language);
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            checkBoxes.add(new CheckBoxes(String.valueOf(languagesClass.id),checkBox, languagesClass.locale));
            Log.d(TAG,"SWAG" + String.valueOf(languagesClass.locale));
            checkBox.setChecked(settings.getBoolean(String.valueOf(languagesClass.id), false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    editor.putBoolean(String.valueOf(languagesClass.id),isChecked);
                    editor.apply();

                    checkIfParentCheckBoxShouldSwitch(((CheckBox)parentLinearLayout.findViewById(R.id.checkBoxRowCategory)),editor,arrayListLinearLayout);
                    saveCategories();
                }


            });
            arrayListLinearLayout.add(newLinearLayout);
        }

        checkIfParentCheckBoxShouldSwitch(((CheckBox)parentLinearLayout.findViewById(R.id.checkBoxRowCategory)),editor,arrayListLinearLayout);
        (parentLinearLayout.findViewById(R.id.dropDownList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,arrayListLinearLayout,topLinearLayout);
            }
        });
        parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(parentLinearLayout.findViewById(R.id.dropDownList),
                        arrayListLinearLayout,topLinearLayout);
            }
        });
        ((CheckBox)parentLinearLayout.findViewById(R.id.checkBoxRowCategory)).
                setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (LinearLayout linearLayout : arrayListLinearLayout) {
                    ((CheckBox)linearLayout.findViewById(R.id.checkBoxRowLeftMargin)).setChecked(isChecked);
                }
                editor.putBoolean(String.valueOf(R.string.languageFrom),isChecked);
                editor.apply();
                saveCategories();
            }
        });

        return topLinearLayout;
    }

    private void checkIfParentCheckBoxShouldSwitch(CheckBox parentCheckBox, final SharedPreferences.Editor editor, final ArrayList<LinearLayout> arrayListLinearLayout) {

        for (CheckBoxes checkBox : checkBoxes) {

            if(!checkBox.checkBox.isChecked()){
                parentCheckBox.setOnCheckedChangeListener(null);
                parentCheckBox.setChecked(false);
                /*editor.putBoolean(String.valueOf(R.string.languageFrom),false);
                editor.apply();*/
                parentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        for (LinearLayout linearLayout : arrayListLinearLayout) {
                            ((CheckBox)linearLayout.findViewById(R.id.checkBoxRowLeftMargin)).setChecked(isChecked);
                        }
                        editor.putBoolean(String.valueOf(R.string.languageFrom),isChecked);
                        editor.apply();
                        saveCategories();
                    }
                });
                return;
            }
        }
        parentCheckBox.setChecked(true);
    }


    private LinearLayout addStaticLanguages(LayoutInflater inflater, ArrayList<Languages.LanguagesClass> languageFrom) {
        final ArrayList<LinearLayout> arrayListLinearLayout = new ArrayList<>();
        final LinearLayout topLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowlanguage,null);
        final LinearLayout parentLinearLayout = (LinearLayout) topLinearLayout.findViewById(R.id.linHorRowLang);
        ((ImageView)parentLinearLayout.findViewById(R.id.ImageViewRowLanguage)).setImageResource(R.drawable.langstat);
        ((TextView)parentLinearLayout.findViewById(R.id.textViewRowLanguage)).setText(R.string.languageGeneral);
        SharedPreferences settings = getContext().getSharedPreferences("box", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();
        for (final Languages.LanguagesClass languagesClass : languageFrom) {

            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowlayoutlanguageradio,null);
            ((ImageView)newLinearLayout.findViewById(R.id.imageViewRadio)).setImageResource(languagesClass.picture);
            ((TextView)newLinearLayout.findViewById(R.id.textViewRadio)).setText(languagesClass.language);
            final RadioButton radioButton = (RadioButton) newLinearLayout.findViewById(R.id.radioButtonLanguage);
            radioButtons.add(new RadioButtons(String.valueOf(languagesClass.id),radioButton,languagesClass.locale));
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v,editor);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                        sharedPreferencesEditor.putString("getLanguage", languagesClass.locale.getLanguage());
                        sharedPreferencesEditor.apply();
                    Locale.setDefault(languagesClass.locale);
                    Configuration config = new Configuration();
                    config.setLocale(languagesClass.locale);
                    getActivity().getApplicationContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());

                    Intent intent = getActivity().getIntent();
                    getActivity().finish();
                    startActivity(intent);


                }
            });
            radioButton.setChecked(settings.getBoolean(String.valueOf(languagesClass.id), false));
            arrayListLinearLayout.add(newLinearLayout);
        }

        ((ImageView)parentLinearLayout.findViewById(R.id.imageViewDropDownLanguage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,arrayListLinearLayout,topLinearLayout);
            }
        });
        parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(parentLinearLayout.findViewById(R.id.imageViewDropDownLanguage),
                        arrayListLinearLayout,topLinearLayout);
            }
        });
        return topLinearLayout;
    }

    public void saveCategories(){

        FileOutputStream fileOutputStream;

        File file = new File(this.getContext().getFilesDir(), "language.txt");
        ArrayList<Locale> locales = new ArrayList<>();

        for (CheckBoxes checkBox : checkBoxes) {
            if(checkBox.checkBox.isChecked()){
                locales.add(checkBox.locale);
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file,false);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(locales);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Locale> getCategories(){
        FileInputStream fileInputStream;
        ArrayList<Locale> locales = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(startPageFile);

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            locales = ( ArrayList<Locale>) objectInputStream.readObject();
            objectInputStream.close();
            return locales;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return locales;
    }
    public void onRadioButtonClicked(View view, SharedPreferences.Editor editor) {
        // Is the button now checked?
        RadioButton rbutton = ((RadioButton) view);
        for (RadioButtons radioButton : radioButtons) {
            if (!radioButton.radioButton.equals(rbutton)){
                radioButton.radioButton.setChecked(false);
            }

            editor.putBoolean(radioButton.id,radioButton.radioButton.isChecked());
            editor.apply();
        }
    }

    public void onclickDropDownList(View v,ArrayList<LinearLayout> arrayList,LinearLayout linearLayoutPar) {
        if(v.getRotation() == 180){
            v.setRotation(0);
            for (LinearLayout linearLayout : arrayList) {

                linearLayoutPar.removeView(linearLayout);

            }
        }else{
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

    public String getLanguageFromLFragment(Splashscreen startPage) {
        //Log.d(TAG,preference.getString("getLanguage",null));
        ArrayList<String> languageAccepted = new  ArrayList<String>();
        languageAccepted.add("en");
        languageAccepted.add("sv");

        if (preference.contains("getLanguage")){
            return preference.getString("getLanguage","en");
        }
        for (String s : languageAccepted) {
            if(s.equals(Locale.getDefault().getLanguage())){
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(startPage).edit();
                sharedPreferencesEditor.putString("getLanguage", s);
                sharedPreferencesEditor.apply();
                return Locale.getDefault().getLanguage();
            }

        }

        return "en";
    }
    public String getLanguageFromLFragment(Context startPage) {
        //Log.d(TAG,preference.getString("getLanguage",null));
        ArrayList<String> languageAccepted = new  ArrayList<String>();
        languageAccepted.add("en");
        languageAccepted.add("sv");
        Log.d(TAG,"StartPageLanguageFragment"+Locale.getDefault().getLanguage());
        if (preference.contains("getLanguage")){
            return preference.getString("getLanguage","en");
        }
        for (String s : languageAccepted) {
            if(s.equals(Locale.getDefault().getLanguage())){
                SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(startPage).edit();
                sharedPreferencesEditor.putString("getLanguage", s);
                sharedPreferencesEditor.apply();
                return Locale.getDefault().getLanguage();
            }

        }

        return "en";
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
    public class RadioButtons{
        String id;
        RadioButton radioButton;
        Locale staticLocale;
        public RadioButtons(String id, RadioButton radioButton,Locale staticLocale){
            this.id = id;
            this.radioButton = radioButton;
            this.staticLocale = staticLocale;

        }
    }

    private class CheckBoxes {
        String id;
        CheckBox checkBox;
        Locale locale;
        public CheckBoxes(String id, CheckBox checkBox, Locale locale){
            this.id = id;
            this.checkBox = checkBox;
            this.locale = locale;

        }
    }

}
