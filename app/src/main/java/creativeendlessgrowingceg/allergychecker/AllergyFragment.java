package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;


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
    private HashSet<Integer> hashMapCategoriesAllergy = new HashSet<>();
    private HashMap<String,LinearLayout> linearLayoutParents = new HashMap<>();
    private HashMap<String,ArrayList<CheckBox>> checkBoxes = new HashMap<>();
    private HashMap<String,CheckBox> parentCheckBox = new HashMap<>();
    private HashMap<Integer,ArrayList<CheckBox>> sameItemDifferentCategories = new HashMap<>();
    private HashMap<Integer,Integer> profileSavePicture = new HashMap<>();

    private File startPageFile;
    private StartPage startPage;
    HashMap<Integer,ImageView> imageViewHashMap;
    private ArrayList<Locale> localeArrayList;
    boolean parentSetOnClick = false;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);


    public AllergyFragment(AllergyFragment allergyFragment) {
        // Required empty public constructor
    }




    public AllergyFragment() {
        // Required empty public constructor
    }

    public AllergyFragment(StartPage startPage, HashMap<Integer,ImageView> imageViewHashMap,ArrayList<Locale> localeArrayList) {
        this.startPage = startPage;
        this.imageViewHashMap = imageViewHashMap;
        startPageFile = new File(startPage.getFilesDir(),"profile.txt");
        if(localeArrayList.isEmpty()){
            localeArrayList.add(Locale.getDefault());
        }
        this.localeArrayList = localeArrayList;
    }

    public AllergyFragment(StartPage startPage) {
        this.startPage = startPage;
        startPageFile = new File(startPage.getFilesDir(),"data.txt");
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
        /*Locale locale = new Locale(new SettingsFragment(getContext()).getLanguageFromLFragment(getContext()));
        final Locale newLocale = new Locale(locale.getLanguage());
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = getContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());*/
    }
    //create the look
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_allergy, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linlayoutFrag);
        AllergyList allergylist = new AllergyList(getContext());
        //getCategories();
        addCategory(inflater, allergylist.getArrayListCitrus(), "Citrus",R.string.citrus,R.drawable.orange);
        addCategory(inflater, allergylist.getArrayListFish(), "Fish",R.string.fish,R.drawable.fish);
        addCategory(inflater, allergylist.getArrayListFruit(), "Fruit", R.string.fruit,R.drawable.fruit);
        addCategory(inflater, allergylist.getArrayListGluten(), "Gluten",R.string.gluten, R.drawable.wheat);
        addCategory(inflater, allergylist.getArrayListLegumes(), "Legumes",R.string.legumes, R.drawable.legumes);
        addCategory(inflater, allergylist.getArrayListNuts(), "Nuts",R.string.nuts, R.drawable.nuts);
        addCategory(inflater, allergylist.getArrayListSeeds(), "Seeds",R.string.seeds, R.drawable.seeds);
        addCategory(inflater, allergylist.getArrayListShellfish(), "Shellfish", R.string.shellfish, R.drawable.shellfish);
        addCategory(inflater, allergylist.getArrayListVegetables(), "Vegetables", R.string.vegetables, R.drawable.tomato);
        addCategory(inflater, allergylist.getArrayListMuslim(), "Halal", R.string.halal, R.drawable.tomato);
        addCategory(inflater, allergylist.getArrayListVegetarian(), "Vegetarian", R.string.vegetarian, R.drawable.vegetarian);
        addCategory(inflater, allergylist.getArrayListVegan(), "Vegan", R.string.vegan, R.drawable.vegan);
        addCategory(inflater, allergylist.getArrayListLactoVegetarian(), "Lacto Vegetarian", R.string.lactoVegetarian, R.drawable.lactovegitarian);
        addCategory(inflater, allergylist.getArrayListOvoVegetarian(), "Ovo Vegetarian", R.string.ovoVegetarian, R.drawable.ovoveg);
        addCategory(inflater, allergylist.getArrayListLactoOvoVegetarian(), "LactoOvo Vegetarian", R.string.lactoOvoVegetarian, R.drawable.lactoovoveg);
        addCategory(inflater, allergylist.getArrayListDemiVegetarian(), "Demi Vegetarian", R.string.demiVegetarian, R.drawable.demiveg);
        addCategory(inflater, allergylist.getArrayListPolloVegetarian(), "Pollo Vegetarian", R.string.polloVegetarian, R.drawable.polloveg);
        addCategory(inflater, allergylist.getArrayListPescoVegetarian(), "Pesco Vegetarian", R.string.pescoVegetarian, R.drawable.pescoveg);

        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Citrus", container, R.string.citrus, R.drawable.orange));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Fish", container, R.string.fish, R.drawable.fish));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Fruit", container, R.string.fruit, R.drawable.fruit));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Gluten", container, R.string.gluten, R.drawable.wheat));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Legumes", container, R.string.legumes, R.drawable.legumes));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Nuts", container, R.string.nuts, R.drawable.nuts));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Seeds", container, R.string.seeds, R.drawable.seeds));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Shellfish", container, R.string.shellfish, R.drawable.shellfish));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Vegetables", container, R.string.vegetables, R.drawable.tomato));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Halal", container, R.string.halal, R.drawable.tomato));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Vegetarian", container, R.string.vegetarian, R.drawable.vegetarian));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Vegan", container, R.string.vegan, R.drawable.vegan));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Lacto Vegetarian", container, R.string.lactoVegetarian, R.drawable.lactovegitarian));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Ovo Vegetarian", container, R.string.ovoVegetarian, R.drawable.ovoveg));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "LactoOvo Vegetarian", container, R.string.lactoOvoVegetarian, R.drawable.lactoovoveg));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Demi Vegetarian", container, R.string.demiVegetarian, R.drawable.demiveg));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Pollo Vegetarian", container, R.string.polloVegetarian, R.drawable.polloveg));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater, "Pesco Vegetarian", container, R.string.pescoVegetarian, R.drawable.pescoveg));

        //saveCategories();
        return parentFrameLayout;
    }



    private LinearLayout insertSingleAllergy(LayoutInflater inflater, final int name, ViewGroup container, final int pictureId) {
        final LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rowlayout, container, false);
        ((TextView) linearLayout.findViewById(R.id.textViewRow)).setText(name);
        ((ImageView) linearLayout.findViewById(R.id.imageViewRow)).setImageResource(pictureId);

        SharedPreferences settings = getContext().getSharedPreferences(getResources().getString(name), 0);
        final SharedPreferences.Editor editor = settings.edit();

        CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.checkBoxRow);
        checkBox.setChecked(settings.getBoolean(getResources().getString(name), false));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                editor.putBoolean(getResources().getString(name), isChecked);
                editor.apply();
                if(isChecked){
                    //getCategories();
                    addItemToHashMap(name,pictureId);
                    //saveCategories();
                }
                else{
                    //getCategories();
                    removeItemToHashMap(name,pictureId);
                    //saveCategories();
                }
            }
        });
        checkIfNotExist(checkBox,name,pictureId);

        return linearLayout;

    }
    public void checkIfNotExist(CheckBox checkBox,int key,int mainCat){
        if(checkBox.isChecked()){

            if(!hashMapCategoriesAllergy.contains(key)){
                profileSavePicture.put(key,mainCat);
                hashMapCategoriesAllergy.add(key);
                Log.d(TAG,"Put in hashMapCategoriesAllergy: " + getStringByLocal(getActivity(),key,Locale.getDefault().getLanguage()));
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

        parentCheckBoxOnClickListener(checkboxRowCategory,key,name);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,key);
            }
        });
        linearLayoutParents.put(key,linearLayout);
        parentCheckBox.put(key,checkboxRowCategory);

        seeIfAllCheckboxIsChecked(checkboxRowCategory,key,name);
        return linearLayout;
    }
    public void parentCheckBoxOnClickListener(CheckBox checkboxRowCategory, final String key, final int parentKey){
        checkboxRowCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG,"TIME");
                //getCategories();
                parentSetOnClick = true;
                if(isChecked) {
                    for (CheckBox checkBox : checkBoxes.get(key)) {

                        checkBox.setChecked(true);
                    }


                }else {
                    for (CheckBox checkBox : checkBoxes.get(key)) {
                        checkBox.setChecked(false);
                    }

                }

                Log.d(TAG,"TIME");
                //saveCategories();
                parentSetOnClick = false;

            }
        });
    }
    public void addItemToHashMap(int id,int pictureId){
        hashMapCategoriesAllergy.add(id);
        profileSavePicture.put(id,pictureId);
    }
    public void removeItemToHashMap(int id,int pictureId){
        if(profileSavePicture.containsKey(id)){
            profileSavePicture.remove(id);

        }
        if(hashMapCategoriesAllergy.contains(id)){
            hashMapCategoriesAllergy.remove(id);

        }

    }
    private void seeIfAllCheckboxIsChecked(CheckBox checkboxRowCategory, String key,int name) {

        for (LinearLayout linearLayout : Categories.get(key)) {
            CheckBox checkBox = (CheckBox) linearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            if(!checkBox.isChecked()){
                checkboxRowCategory.setChecked(false);
                return;
            }

        }
        checkboxRowCategory.setOnCheckedChangeListener(null);
        checkboxRowCategory.setChecked(true);
        parentCheckBoxOnClickListener(checkboxRowCategory,key,name);


    }
    private void checkBoxLeftMarginSaveString(boolean isChecked, int id,int pictureId){
        if(!parentSetOnClick){
            //getCategories();
        }
        if(isChecked){
            addItemToHashMap(id,pictureId);
        }else{

            removeItemToHashMap(id,pictureId);
        }
        if(!parentSetOnClick){
            //
            //saveCategories();
        }

    }
    public String setTextViewCorrect(String ingredient){
        List<String> list = null;
        if(ingredient.contains(",")){
            list = Arrays.asList(ingredient.split(","));

        }
        if(list == null){
            return ingredient;
        }
        return list.get(0);
    }

    private void addCategory(final LayoutInflater inflater, ArrayList<AllergyList.PictureIngredient> arrayListCategory, final String key,final int parentKey,final int parentPicture){
        ArrayList<LinearLayout> arrayList = new ArrayList<>();
        ArrayList<CheckBox> checkBoxList = new ArrayList<>();
        for (final AllergyList.PictureIngredient arrayListCat : arrayListCategory) {

            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout,null);

            final TextView textview = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);

            textview.setText(setTextViewCorrect(arrayListCat.ingredient));
            ImageView imageView = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);

            imageView.setImageResource(arrayListCat.picture);

            SharedPreferences settings = getContext().getSharedPreferences(arrayListCat.ingredient, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            Log.d(TAG, "addCategory: " + arrayListCat.ingredient);
            checkBox.setChecked(settings.getBoolean(arrayListCat.ingredient, false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Log.d(TAG,"NUMBER2:" + arrayListCat.id + "name: " + arrayListCat.ingredient);

                    for (CheckBox box : sameItemDifferentCategories.get(arrayListCat.id)) {
                        if(!box.equals(checkBox)){
                            box.setChecked(isChecked);
                        }
                    }

                    checkBoxLeftMarginSaveString(isChecked,arrayListCat.id,parentPicture);
                    parentCheckBox.get(key).setOnCheckedChangeListener(null);
                    seeIfAllCheckboxIsChecked(parentCheckBox.get(key),key,parentKey);
                    parentCheckBoxOnClickListener(parentCheckBox.get(key),key,parentKey);
                    Log.d(TAG, "addCategory: " + arrayListCat.ingredient);
                    editor.putBoolean(arrayListCat.ingredient, isChecked);
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
            checkIfNotExist(checkBox, arrayListCat.id,parentPicture);
        }
        checkBoxes.put(key,checkBoxList);
        Categories.put(key,arrayList);


    }
    public void saveCategories(){
        final Context context = this.getContext();
        new MyTask().execute("Save");
    }


    public void getCategories(){

        final Context context = this.getContext();



        FileInputStream fileInputStream;
        File file = new File(context.getFilesDir(), "data.txt");
        Log.d(TAG,"size" + String.valueOf(Integer.parseInt(String.valueOf(file.length()/1024))));
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            if(lock.isWriteLocked()){
                lock.readLock().unlock();
                objectInputStream.close();
                return;
            }
            hashMapCategoriesAllergy = (HashSet<Integer>) objectInputStream.readObject();

            objectInputStream.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


    }
    public HashSet<Integer> getCategoriesFromOtherClass(){
        SharedPreferences sp = startPage.getSharedPreferences("AllergyFrag", Context.MODE_PRIVATE);
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("data", new HashSet<String>());
        HashSet<Integer> hashSet = new HashSet<>();
        for (String s : set) {
            hashSet.add(Integer.parseInt(s));
        }


        return hashSet;
    }
    public void setProfilePic(){
        new SavePictureFromOtherClass().execute();

    }

    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Activity context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));


        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase().replaceAll("\\s+","");
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
        profileSavePicture();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveCategories();
        profileSavePicture();
        mListener = null;
    }

    private void profileSavePicture() {
        final Context context = this.getContext();
        new SavePicture(context).execute();
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
    private class MyTask extends AsyncTask<String, Integer, String> {

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array

            SharedPreferences sp = startPage.getSharedPreferences("AllergyFrag", Context.MODE_PRIVATE);
            SharedPreferences.Editor mEdit1 = sp.edit();
            Set<String> set = new HashSet<>();
            for (Integer integer : hashMapCategoriesAllergy) {
                set.add(integer.toString());
            }

            mEdit1.putStringSet("data", set);
            mEdit1.apply();


            return "this string is passed to onPostExecute";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, String.valueOf(values[1]));
            double i = ((double)values[1]/(double)values[0]) * 100;
            Log.d(TAG, String.valueOf(i));
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Do things like hide the progress bar or change a TextView
        }
    }
    private class SavePicture extends AsyncTask<String, Integer, String> {

        private Context context;

        public SavePicture(Context context) {

            this.context = context;
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array

            try {
                FileOutputStream fileOutputStream;
                File file = new File(startPage.getFilesDir(), "profile.txt");
                fileOutputStream = new FileOutputStream(file, false);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(profileSavePicture);
                objectOutputStream.close();
                Log.d(TAG,"PROFILE THREAD FINITO"+ Thread.currentThread().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "this string is passed to onPostExecute";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String alreadyString = "00000000";
            ArrayList<Integer> alreadySelectedImages = new ArrayList<>();
            int i = 0;
            for (ImageView imageView : imageViewHashMap.values()) {
                imageView.setImageResource(R.drawable.emptyborder);
            }
            for (int id: profileSavePicture.values()) {
                if(i>7){
                    break;
                }

                if(alreadyString.charAt(i) == '0'){
                    if (!alreadySelectedImages.contains(id)) {
                        imageViewHashMap.get(i).setImageResource(id);
                        alreadySelectedImages.add(id);
                        StringBuilder myName = new StringBuilder(alreadyString);
                        myName.setCharAt(i, '1');
                        alreadyString = myName.toString();
                        i++;
                    }
                }
            }
        }
    }
    private class SavePictureFromOtherClass extends AsyncTask<String, Integer, String> {

        private Context context;



        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array

            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(startPageFile);

                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                profileSavePicture = (HashMap<Integer, Integer>) objectInputStream.readObject();

                objectInputStream.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            return "this string is passed to onPostExecute";
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String alreadyString = "00000000";
            ArrayList<Integer> alreadySelectedImages = new ArrayList<>();
            int i = 0;
            for (ImageView imageView : imageViewHashMap.values()) {
                imageView.setImageDrawable(startPage.getDrawable(R.drawable.emptyborder));
            }
            for (int id: profileSavePicture.values()) {
                if(i>7){
                    break;
                }

                if(alreadyString.charAt(i) == '0'){
                    if (!alreadySelectedImages.contains(id)) {
                        imageViewHashMap.get(i).setImageResource(id);
                        alreadySelectedImages.add(id);
                        StringBuilder myName = new StringBuilder(alreadyString);
                        myName.setCharAt(i, '1');
                        alreadyString = myName.toString();
                        i++;
                    }
                }
            }
        }
    }
}
