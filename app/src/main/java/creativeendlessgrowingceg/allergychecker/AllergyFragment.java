package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private HashMap<String,ArrayList<String>> categoriesAllergy = new HashMap<>();
    private HashMap<String,LinearLayout> linearLayoutParents = new HashMap<>();
    private HashMap<String,CheckBox> checkBoxes = new HashMap<>();
    private SharedPreferences prefs;
    private File startPageFile;

    public AllergyFragment(AllergyFragment allergyFragment) {
        // Required empty public constructor
    }
    public AllergyFragment(StartPage startPage) {
        prefs = startPage.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
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
    }
    //create the look
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_allergy, container, false);
        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linlayoutFrag);
        AllergyList allergylist = new AllergyList();
        addCategory(inflater,allergylist.getArrayListFish(),"Fish");
        addCategory(inflater,allergylist.getArrayListGluten(),"Gluten");
        addCategory(inflater,allergylist.getArrayListNuts(),"Nuts");
        addCategory(inflater,allergylist.getArrayListSeeds(),"Seeds");
        addCategory(inflater,allergylist.getArrayListShellfish(),"Shellfish");
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater,"Fish",container,R.string.fish, R.drawable.fish));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater,"Gluten",container,R.string.gluten,R.drawable.wheat));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater,"Nuts",container,R.string.nuts, R.drawable.peanut));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater,"Seeds",container,R.string.seeds, R.drawable.wheat));
        parentLinearLayout.addView(insertCheckboxAndImageView(inflater,"Shellfish",container,R.string.shellfish, R.drawable.wheat));
        parentLinearLayout.addView(insertSingleAllergy(inflater,R.string.milk,container,R.drawable.car));
        return parentFrameLayout;


    }

    private LinearLayout insertSingleAllergy(LayoutInflater inflater, final int name, ViewGroup container, int pictureId) {
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
                    addItemToHashMap(String.valueOf(((TextView)linearLayout.findViewById(R.id.textViewRow)).getText()));
                }
                else{
                    removeItemToHashMap(String.valueOf(((TextView)linearLayout.findViewById(R.id.textViewRow)).getText()));
                }
            }
        });
        return linearLayout;

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
        checkBoxes.put(key,checkboxRowCategory);
        checkboxRowCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    for (LinearLayout linearLayout : Categories.get(key)) {
                        CheckBox newCheck = (CheckBox) linearLayout.findViewById(R.id.checkBoxRowLeftMargin);
                        newCheck.setChecked(true);

                        addItemToHashMap(String.valueOf(((TextView)linearLayout.findViewById(R.id.textViewLeftMargin)).getText()));
                    }
                }else {
                    for (LinearLayout linearLayout : Categories.get(key)) {
                        CheckBox newCheck = (CheckBox) linearLayout.findViewById(R.id.checkBoxRowLeftMargin);
                        newCheck.setChecked(false);
                        removeItemToHashMap( String.valueOf(((TextView)linearLayout.findViewById(R.id.textViewLeftMargin)).getText()));
                    }
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,key);
            }
        });
        linearLayoutParents.put(key,linearLayout);
        seeIfAllCheckboxIsChecked(checkboxRowCategory,key);
        return linearLayout;
    }
    public void addItemToHashMap(String string){
        string = string.toLowerCase();
        getCategories();
        if(!categoriesAllergy.containsKey(string)){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(string);
            categoriesAllergy.put(string,arrayList);
            saveCategories();
            return;
        }
        ArrayList<String> arrayList = categoriesAllergy.get(string);
        arrayList.add(string);
        categoriesAllergy.put(string,arrayList);
        saveCategories();

    }
    public void removeItemToHashMap(String string){
        string = string.toLowerCase();
        getCategories();
        categoriesAllergy.remove(string);
        saveCategories();

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
    private void checkBoxLeftMarginSaveString(boolean isChecked, String text){
        if(isChecked){
            addItemToHashMap(text);
        }else{
            removeItemToHashMap(text);
        }

    }


    private void addCategory(LayoutInflater inflater, ArrayList<AllergyList.PictureIngredient> arrayListCategory, final String key){
        ArrayList<LinearLayout> arrayList = new ArrayList<>();
        for (final AllergyList.PictureIngredient arrayListCat : arrayListCategory) {
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout,null);
            final TextView textview = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);
            textview.setText(arrayListCat.ingredient);
            ImageView imageView = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);
            imageView.setImageResource(arrayListCat.picture);
            SharedPreferences settings = getContext().getSharedPreferences(arrayListCat.ingredient, 0);
            final SharedPreferences.Editor editor = settings.edit();
            final CheckBox checkBox = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);
            checkBox.setChecked(settings.getBoolean(arrayListCat.ingredient, false));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBoxLeftMarginSaveString(isChecked, String.valueOf(textview.getText()));
                    editor.putBoolean(arrayListCat.ingredient, isChecked);
                    editor.apply();
                }
            });
            arrayList.add(newLinearLayout);
        }
        Categories.put(key,arrayList);

    }
    public void saveCategories(){

        FileOutputStream fileOutputStream;

        File file = new File(this.getContext().getFilesDir(), "data.txt");
        try {
            fileOutputStream = new FileOutputStream(file,false);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(categoriesAllergy);
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

            categoriesAllergy = (HashMap<String, ArrayList<String>>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String,ArrayList<String>> getArrayListFromAllCheckedAllergies(){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(startPageFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            categoriesAllergy = (HashMap<String, ArrayList<String>>) objectInputStream.readObject();
            objectInputStream.close();
            return categoriesAllergy;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
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
}
