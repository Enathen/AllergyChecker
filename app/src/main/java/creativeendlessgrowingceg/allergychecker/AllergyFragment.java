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
import android.widget.Button;
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
    private HashMap<String,Integer> profileSavePicture = new HashMap<>();
    protected ArrayList<CheckBoxClass> setCheckedLater = new ArrayList<>();

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

        //getCategories();
        new AddCategories(inflater,container,getContext()).execute();




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
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                buttonView.post(new Runnable() {
                    @Override
                    public void run() {
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

            }
        });
        checkIfNotExist(checkBox,name,pictureId);

        return linearLayout;

    }
    public void checkIfNotExist(CheckBox checkBox,int key,int mainCat){
        if(checkBox.isChecked()){

            if(!hashMapCategoriesAllergy.contains(key)){
                profileSavePicture.put(getString(key),mainCat);
                hashMapCategoriesAllergy.add(key);
                //Log.d(TAG,"Put in hashMapCategoriesAllergy: " + getStringByLocal(getActivity(),key,Locale.getDefault().getLanguage()));
            }
        }
    }
    private LinearLayout insertCheckboxAndImageView(LayoutInflater inflater, ViewGroup container, final int name, int pictureId) {
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
                onclickDropDownList(imageView,getString(name));
            }
        });

        parentCheckBoxOnClickListener(checkboxRowCategory,getString(name),name);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,getString(name));
            }
        });
        linearLayoutParents.put(getString(name),linearLayout);
        parentCheckBox.put(getString(name),checkboxRowCategory);

        seeIfAllCheckboxIsChecked(checkboxRowCategory,getString(name),name);
        return linearLayout;
    }
    public void parentCheckBoxOnClickListener(CheckBox checkboxRowCategory, final String key, final int parentKey){
        checkboxRowCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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


                //saveCategories();
                parentSetOnClick = false;

            }
        });
    }
    public void addItemToHashMap(int id,int pictureId){
        hashMapCategoriesAllergy.add(id);
        profileSavePicture.put(getString(id),pictureId);
    }
    public void removeItemToHashMap(int id,int pictureId){
        if(profileSavePicture.containsKey(getString(id))){
            profileSavePicture.remove(getString(id));

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
        Log.d(TAG, "seeIfAllCheckboxIsChecked: " + key);
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

    private void addCategory(final LayoutInflater inflater, ArrayList<AllergyList.PictureIngredient> arrayListCategory, final int parentKey, final int parentPicture){
        ArrayList<LinearLayout> arrayList = new ArrayList<>();
        ArrayList<CheckBox> checkBoxList = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (final AllergyList.PictureIngredient arrayListCat : arrayListCategory) {


            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout,null);

            final TextView textview = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);

            textview.setText(setTextViewCorrect(arrayListCat.ingredient));
            ImageView imageView = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);

            imageView.setImageResource(arrayListCat.picture);

            /*SharedPreferences settings = getContext().getSharedPreferences(arrayListCat.ingredient, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = settings.edit();*/
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            setCheckedLater.add(new CheckBoxClass(checkBox,arrayListCat.id,parentPicture,getString(parentKey),parentKey,arrayListCat.ingredient));

            // Log.d(TAG,"NUMBER2:" + arrayListCat.id + "name: " + arrayListCat.ingredient);



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
            //Log.d(TAG, String.valueOf(arrayListCat.id)+ "  " + arrayListCat.ingredient);

        }
        long stop = System.currentTimeMillis();
        Log.d(TAG, "TIMEONE:"+ (stop-start));
        checkBoxes.put(getString(parentKey),checkBoxList);
        Categories.put(getString(parentKey),arrayList);


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
                profileSavePicture = (HashMap<String, Integer>) objectInputStream.readObject();

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
    private class AddCategories extends AsyncTask<String, Integer, String> {

        private Context context;
        private LayoutInflater inflater;
        private ViewGroup container;
        private Context contextHistory;

        public AddCategories(LayoutInflater inflater, ViewGroup container,Context contextHistory) {
            this.inflater = inflater;
            this.container = container;
            this.contextHistory = contextHistory;
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
            AllergyList allergylist = new AllergyList(contextHistory);
            addCategory(inflater, allergylist.getArrayListCitrus(), R.string.citrus,R.drawable.orange);
            addCategory(inflater, allergylist.getArrayListFish(), R.string.fish,R.drawable.fish);
            addCategory(inflater, allergylist.getArrayListFruit(), R.string.fruit,R.drawable.fruit);
            addCategory(inflater, allergylist.getArrayListGluten(), R.string.gluten, R.drawable.wheat);
            addCategory(inflater, allergylist.getArrayListLegumes(), R.string.legumes, R.drawable.legumes);
            addCategory(inflater, allergylist.getArrayListNuts(), R.string.nuts, R.drawable.nuts);
            addCategory(inflater, allergylist.getArrayListSeeds(), R.string.seeds, R.drawable.seeds);
            addCategory(inflater, allergylist.getArrayListShellfish(), R.string.shellfish, R.drawable.shellfish);
            addCategory(inflater, allergylist.getArrayListVegetables(), R.string.vegetables, R.drawable.tomato);
            addCategory(inflater, allergylist.getArrayListMuslim(), R.string.halal, R.drawable.halal);
            addCategory(inflater, allergylist.getArrayListVegetarian(), R.string.vegetarian, R.drawable.vegetarian);
            addCategory(inflater, allergylist.getArrayListVegan(), R.string.vegan, R.drawable.vegan);
            addCategory(inflater, allergylist.getArrayListLactoVegetarian(), R.string.lactoVegetarian, R.drawable.lactovegitarian);
            addCategory(inflater, allergylist.getArrayListOvoVegetarian(), R.string.ovoVegetarian, R.drawable.ovoveg);
            addCategory(inflater, allergylist.getArrayListLactoOvoVegetarian(), R.string.lactoOvoVegetarian, R.drawable.lactoovoveg);
            addCategory(inflater, allergylist.getArrayListDemiVegetarian(), R.string.demiVegetarian, R.drawable.demiveg);
            addCategory(inflater, allergylist.getArrayListPolloVegetarian(), R.string.polloVegetarian, R.drawable.polloveg);
            addCategory(inflater, allergylist.getArrayListPescoVegetarian(), R.string.pescoVegetarian, R.drawable.pescoveg);


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
            long start = System.currentTimeMillis();
            boolean contains = false;
            for (final CheckBoxClass checkBox : setCheckedLater) {
                SharedPreferences settings = contextHistory.getSharedPreferences(checkBox.ingredient, contextHistory.MODE_PRIVATE);
                final SharedPreferences.Editor editor = settings.edit();
                checkBox.checkBox.setChecked(settings.getBoolean(checkBox.ingredient, false));
                if(checkBox.checkBox.isChecked()){
                    contains = true;
                }
                setOnChecked(checkBox.checkBox, editor, checkBox);

                checkIfNotExist(checkBox.checkBox, checkBox.id,checkBox.parentPicture);
            }
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.citrus, R.drawable.orange));

            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.fish, R.drawable.fish));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.fruit, R.drawable.fruit));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.gluten, R.drawable.wheat));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.legumes, R.drawable.legumes));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.nuts, R.drawable.nuts));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.seeds, R.drawable.seeds));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.shellfish, R.drawable.shellfish));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.vegetables, R.drawable.tomato));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.halal, R.drawable.halal));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.vegetarian, R.drawable.vegetarian));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater,  container, R.string.vegan, R.drawable.vegan));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.lactoVegetarian, R.drawable.lactovegitarian));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.ovoVegetarian, R.drawable.ovoveg));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.lactoOvoVegetarian, R.drawable.lactoovoveg));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.demiVegetarian, R.drawable.demiveg));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.polloVegetarian, R.drawable.polloveg));
            parentLinearLayout.addView(insertCheckboxAndImageView(inflater, container, R.string.pescoVegetarian, R.drawable.pescoveg));
            Log.d(TAG, "onPostExecute: " +(System.currentTimeMillis()-start));
            parentLinearLayout.findViewById(R.id.btnUncheckAll).setVisibility(View.VISIBLE);
            if(!contains){
                ((Button)parentLinearLayout.findViewById(R.id.btnUncheckAll)).setText(R.string.checkAll);
            }
            parentLinearLayout.findViewById(R.id.btnUncheckAll).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Button btn = (Button) v;
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            if (btn.getText().equals(getString(R.string.uncheckAll))) {
                                btn.setText(getString(R.string.checkAll));

                                /*for (String boxes : checkBoxes.keySet()) {
                                    for (CheckBox box : checkBoxes.get(boxes)) {
                                        Log.d(TAG, "run: "+boxes);
                                        box.setChecked(false);
                                    }
                                }*/
                                for (String checkBox : parentCheckBox.keySet()) {
                                    Log.d(TAG, "run: " +checkBox);
                                    parentCheckBox.get(checkBox).setChecked(false);
                                }
                            } else {
                                btn.setText(getString(R.string.uncheckAll));
                                /*for (String boxes : checkBoxes.keySet()) {
                                    for (CheckBox box : checkBoxes.get(boxes)) {
                                        Log.d(TAG, "run: "+boxes);
                                        box.setChecked(true);
                                    }
                                }*/
                                for (CheckBox checkBox : parentCheckBox.values()) {
                                    checkBox.setChecked(true);
                                }
                            }
                        }
                    });
                }
            });
            parentLinearLayout.removeView(parentLinearLayout.findViewById(R.id.progressBarAllergy));
        }
    }
    public void setOnChecked(final CheckBox check, final SharedPreferences.Editor editor, final CheckBoxClass checkBox){
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*for (CheckBox box : sameItemDifferentCategories.get(checkBox.id)) {
                    if(!box.equals(check)){

                        box.setOnCheckedChangeListener(null);
                        box.setChecked(isChecked);
                        setOnChecked(box,editor, checkBox);
                    }
                }*/
                for (CheckBox box : sameItemDifferentCategories.get(checkBox.id)) {
                    if(!box.equals(checkBox.checkBox)){

                        box.setChecked(isChecked);

                    }
                }
                if(parentCheckBox.containsKey(checkBox.ingredient)){
                    if(parentCheckBox.get(checkBox.ingredient).isChecked()){
                        parentCheckBox.get(checkBox.ingredient).setChecked(false);
                    }else{
                        parentCheckBox.get(checkBox.ingredient).setChecked(true);
                    }
                }
                checkBoxLeftMarginSaveString(isChecked,checkBox.id,checkBox.parentPicture);
                parentCheckBox.get(checkBox.key).setOnCheckedChangeListener(null);
                seeIfAllCheckboxIsChecked(parentCheckBox.get(checkBox.key),checkBox.key,checkBox.parentKey);
                parentCheckBoxOnClickListener(parentCheckBox.get(checkBox.key),checkBox.key,checkBox.parentKey);
                editor.putBoolean(checkBox.ingredient, isChecked);
                editor.apply();
            }
        });

    }
    private class CheckBoxClass {
        private CheckBox checkBox;
        private final int id;
        private final int parentPicture;
        private final String key;
        private final int parentKey;
        private String ingredient;

        public CheckBoxClass(CheckBox checkBox,int id, int parentPicture, String key, int ParentKey, String ingredient){
            this.checkBox = checkBox;

            this.id = id;
            this.parentPicture = parentPicture;
            this.key = key;
            parentKey = ParentKey;
            this.ingredient = ingredient;
        }
    }
}
