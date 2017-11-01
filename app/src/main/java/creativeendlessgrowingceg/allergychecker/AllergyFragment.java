package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllergyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllergyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllergyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ALLERGYFRAG";
    private static final String SHARED_PREFS_NAME = "HistoryFragment";
    private static final String SHARED_PREFS_NAME2 = "HistoryFragmentAllergies";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;
    private OnFragmentInteractionListener mListener;
    private HashMap<String,ArrayList<LinearLayout>> Categories = new HashMap<>();
    private HashMap<Integer,LanguageString> hashMapCategoriesAllergy = new HashMap<>();
    private HashMap<String,LinearLayout> linearLayoutParents = new HashMap<>();
    private HashMap<String,ArrayList<CheckBox>> checkBoxes = new HashMap<>();
    private HashMap<String,CheckBox> parentCheckBox = new HashMap<>();
    private HashMap<Integer,ArrayList<CheckBox>> sameItemDifferentCategories = new HashMap<>();
    private SharedPreferences prefs;
    private File startPageFile;
    private String language;

    public AllergyFragment(AllergyFragment allergyFragment) {
        // Required empty public constructor
    }
    public AllergyFragment(StartPage startPage) {
        prefs = startPage.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        startPageFile = new File(startPage.getFilesDir(),"data.txt");
    }


    public AllergyFragment(Context context) {
        startPageFile = new File(context.getFilesDir(),"data.txt");
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllergyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public AllergyFragment newInstance(String param1, String param2) {
        AllergyFragment fragment = new AllergyFragment(this);
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
        Locale locale = new Locale(new SettingsFragment(getContext()).getLanguageFromLFragment(getContext()));
        final Locale newLocale = new Locale(locale.getLanguage());
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = getContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
    //create the look
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Locale locale = getResources().getConfiguration().getLocales().get(0);
        language = locale.getLanguage();

        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_allergy, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linlayoutFrag);
        AllergyList allergylist = new AllergyList(getContext());

        addCategory(inflater, allergylist.getArrayListFish(), "Fish",R.string.fish);
        addCategory(inflater, allergylist.getArrayListGluten(), "Gluten",R.string.gluten);
        addCategory(inflater, allergylist.getArrayListNuts(), "Nuts",R.string.nuts);
        addCategory(inflater, allergylist.getArrayListSeeds(), "Seeds",R.string.seeds);
        addCategory(inflater, allergylist.getArrayListShellfish(), "Shellfish", R.string.shellfish);
        addCategory(inflater, allergylist.getArrayListFruit(), "Fruit", R.string.fruit);

        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Fish", container, R.string.fish, R.drawable.fish));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Gluten", container, R.string.gluten, R.drawable.wheat));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Nuts", container, R.string.nuts, R.drawable.nuts));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Seeds", container, R.string.seeds, R.drawable.seeds));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Shellfish", container, R.string.shellfish, R.drawable.shellfish));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Fruit", container, R.string.fruit, R.drawable.fruit));

        return parentFrameLayout;
    }


    private LinearLayout insertSingleAllergy(LayoutInflater inflater, final int name, ViewGroup container, int pictureId) {
        final LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rowlayout, container, false);
        ((TextView) linearLayout.findViewById(R.id.textViewRow)).setText(name);
        ((ImageView) linearLayout.findViewById(R.id.imageViewRow)).setImageResource(pictureId);

        SharedPreferences settings = getContext().getSharedPreferences(getResources().getString(name), 0);
        final SharedPreferences.Editor editor = settings.edit();
        getCategories();
        CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.checkBoxRow);
        checkBox.setChecked(settings.getBoolean(getResources().getString(name), false));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                editor.putBoolean(getResources().getString(name), isChecked);
                editor.apply();
                if(isChecked){
                    getCategories();
                    addItemToHashMap(name);
                    saveCategories();
                }
                else{
                    getCategories();
                    removeItemToHashMap(name);
                    saveCategories();
                }
            }
        });
        checkIfNotExist(checkBox,name);
        saveCategories();
        return linearLayout;

    }
    public void checkIfNotExist(CheckBox checkBox,int key){
        if(checkBox.isChecked()){

            if(!hashMapCategoriesAllergy.containsKey(key)){

                hashMapCategoriesAllergy.put(key,new LanguageString(true,key));
                Log.d(TAG,"Put in hashMapCategoriesAllergy: " + key);
            }
        }
    }
    private LinearLayout insertCheckboxAndImageView(LayoutInflater inflater, final String key, ViewGroup container, final int name, int pictureId) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, container, false);
        LinearLayout linearLayoutRow = (LinearLayout) linearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        final TextView textView = (TextView) linearLayout.findViewById(R.id.textViewCategory);
        textView.setText(name);
        final CheckBox checkboxRowCategory = (CheckBox) linearLayoutRow.findViewById(R.id.checkBoxRowCategory);
        final ImageView imageView = (ImageView) linearLayoutRow.findViewById(R.id.dropDownList);

        ((ImageView) linearLayoutRow.findViewById(R.id.imageViewRowCategory)).setImageResource(pictureId);
        linearLayoutRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(imageView,key);
            }
        });

        hashMapCategoriesAllergy.put(name,new LanguageString(false,name));
        parentCheckBoxOnClickListener(checkboxRowCategory,key,name);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,key);
            }
        });
        linearLayoutParents.put(key,linearLayout);
        parentCheckBox.put(key,checkboxRowCategory);
        saveCategories();
        seeIfAllCheckboxIsChecked(checkboxRowCategory,key);
        return linearLayout;
    }
    public void parentCheckBoxOnClickListener(CheckBox checkboxRowCategory, final String key, final int parentKey){
        checkboxRowCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    getCategories();
                    hashMapCategoriesAllergy.get(parentKey).on=true;
                    for (CheckBox checkBox : checkBoxes.get(key)) {
                        checkBox.setChecked(true);
                    }
                    saveCategories();
                }else {
                    getCategories();
                    hashMapCategoriesAllergy.get(parentKey).on=false;
                    for (CheckBox checkBox : checkBoxes.get(key)) {
                        checkBox.setChecked(false);
                    }
                    saveCategories();
                }
            }
        });
    }
    public void addItemToHashMap(int id){

        if(!hashMapCategoriesAllergy.containsKey(id)){
            hashMapCategoriesAllergy.put(id,new LanguageString(true,id));
        }
        hashMapCategoriesAllergy.get(id).on=true;

    }
    public void removeItemToHashMap(int string){

        hashMapCategoriesAllergy.get(string).on = false;

    }
    private void seeIfAllCheckboxIsChecked(CheckBox checkboxRowCategory, String key) {
        for (LinearLayout linearLayout : Categories.get(key)) {
            CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            if(!checkBox.isChecked()){
                checkboxRowCategory.setChecked(false);
                return;
            }

        }
        checkboxRowCategory.setChecked(true);

    }
    private void checkBoxLeftMarginSaveString(boolean isChecked, int id){
        if(isChecked){
            addItemToHashMap(id);
        }else{

            removeItemToHashMap(id);
        }

    }


    private void addCategory(final LayoutInflater inflater, ArrayList<AllergyList.PictureIngredient> arrayListCategory, final String key,final int parentKey){
        ArrayList<LinearLayout> arrayList = new ArrayList<>();
        ArrayList<CheckBox> checkBoxList = new ArrayList<>();
        getCategories();
        for (final AllergyList.PictureIngredient arrayListCat : arrayListCategory) {
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout,null);
            final TextView textview = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);
            textview.setText(arrayListCat.ingredient);
            ImageView imageView = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);
            imageView.setImageResource(arrayListCat.picture);
            //Log.d(TAG,"NUMBER:" + arrayListCat.id + "name: " + arrayListCat.ingredient);
            SharedPreferences settings = getContext().getSharedPreferences(String.valueOf(arrayListCat.id), Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            checkBox.setChecked(settings.getBoolean(String.valueOf(arrayListCat.id), false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   // Log.d(TAG,"NUMBER2:" + arrayListCat.id + "name: " + arrayListCat.ingredient);

                        for (CheckBox box : sameItemDifferentCategories.get(arrayListCat.id)) {
                            if(!box.equals(checkBox)){
                                box.setChecked(isChecked);

                            }
                        }

                    checkBoxLeftMarginSaveString(isChecked,arrayListCat.id);
                    parentCheckBox.get(key).setOnCheckedChangeListener(null);
                    seeIfAllCheckboxIsChecked(parentCheckBox.get(key),key);
                    parentCheckBoxOnClickListener(parentCheckBox.get(key),key,parentKey);
                    editor.putBoolean(String.valueOf(arrayListCat.id), isChecked);
                    editor.apply();
                }
            });
            if(sameItemDifferentCategories.containsKey(arrayListCat.id)){
                ArrayList<CheckBox> array = sameItemDifferentCategories.get(arrayListCat.id);
                array.add(checkBox);
                sameItemDifferentCategories.put(arrayListCat.id,array);

            }else{
                ArrayList<CheckBox> array = new ArrayList<>();
                array.add(checkBox);
                sameItemDifferentCategories.put(arrayListCat.id,array);
            }
            checkBoxList.add(checkBox);
            arrayList.add(newLinearLayout);
            Log.d(TAG, String.valueOf(arrayListCat.id)+ "  " + arrayListCat.ingredient);
            checkIfNotExist(checkBox, arrayListCat.id);
        }

        checkBoxes.put(key,checkBoxList);
        Categories.put(key,arrayList);
        saveCategories();

    }
    public void saveCategories(){

        FileOutputStream fileOutputStream;

        File file = new File(this.getContext().getFilesDir(), "data.txt");
        try {
            fileOutputStream = new FileOutputStream(file,false);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(hashMapCategoriesAllergy);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getCategories(){

        FileInputStream fileInputStream;
        File file = new File(this.getContext().getFilesDir(), "data.txt");
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            hashMapCategoriesAllergy = (HashMap<Integer, LanguageString>) objectInputStream.readObject();
            for (LanguageString languageString : hashMapCategoriesAllergy.values()) {
                Log.d(TAG,getString(languageString.id).toLowerCase());
            }
            objectInputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public void getCategoriesFromOtherClass(){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(startPageFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            hashMapCategoriesAllergy = (HashMap<Integer, LanguageString>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    private void saveCategoriesFromOtherClass() {
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(startPageFile,false);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(hashMapCategoriesAllergy);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * all checked allergies including different languages gets selected
     * @return
     */
    public HashMap<String,LangString> getArrayListFromAllCheckedAllergies(ArrayList<Locale> localeArray, Activity context, Locale lDefault){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(startPageFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            hashMapCategoriesAllergy = (HashMap<Integer, LanguageString>) objectInputStream.readObject();
            objectInputStream.close();
            HashMap<String, LangString> hashMap = new HashMap<>();

            if(localeArray.isEmpty()){

                localeArray.add(context.getResources().getConfiguration().getLocales().get(0));
            }
            for (LanguageString key : hashMapCategoriesAllergy.values()) {
                    if(key.on) {
                        for (Locale locale : localeArray) {
                            Log.d(TAG, "getStringByLocal:" + getStringByLocal(context, key.id, locale.getLanguage()));
                            hashMap.put(getStringByLocal(context, key.id, locale.getLanguage()),
                                    new LangString(locale.getLanguage(), getStringByLocal(context, key.id, locale.getLanguage()), true,
                                            key.id));

                        }
                    }

            }
            return hashMap;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Activity context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase();
    }
    public void onclickDropDownList(View v,final String key) {
        if(v.getRotation() == 180){
            v.setRotation(0);
            for (LinearLayout linearLayout : Categories.get(key)) {
                linearLayoutParents.get(key).removeView(linearLayout);

            }
        }else{
            v.setRotation(180);

            for (LinearLayout linearLayout : Categories.get(key)) {
                linearLayoutParents.get(key).addView(linearLayout);

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

/*    public void insertNewLanguage(SettingsFragment settingsFragment, String locale) {
        getCategoriesFromOtherClass();
        HashMap<String, LanguageString> hashClass = new HashMap<>();
        for (LanguageString string : hashMapCategoriesAllergy.values()) {
            if(!string.language.equals(locale)){
                boolean open = true;
                for (LanguageString languageString : hashMapCategoriesAllergy.values()) {
                    if(string.id == languageString.id && languageString.language.equals(locale)){
                        open = false;
                    }
                }
                if (open){
                    hashClass.put(settingsFragment.getString(string.id),
                            new LanguageString(locale,settingsFragment.getString(string.id), string.on,string.id));



                }
            }
        }
        for (LanguageString languageString : hashClass.values()) {
            Log.d(TAG,settingsFragment.getString(languageString.id).toLowerCase());
            hashMapCategoriesAllergy.put(settingsFragment.getString(languageString.id).toLowerCase(),
                    new LanguageString(locale,settingsFragment.getString(languageString.id).toLowerCase(),
                            languageString.on,languageString.id));
        }
        saveCategoriesFromOtherClass();
    }*/



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
    public class CheckboxInsert {
        public String language;
        public String allergyName;
        public CheckBox checkBox;
        public CheckboxInsert(String language, String allergyName,CheckBox checkBox){
            this.language = language;
            this.allergyName = allergyName;
            this.checkBox = checkBox;
        }


    }


}
