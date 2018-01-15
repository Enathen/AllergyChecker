package creativeendlessgrowingceg.allergychecker;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * load an UI.
 * Created by Enathen on 2018-01-12.
 */

public class LoadUIAllergies {
    private static final String TAG = "LoadUIAllergies";
    private boolean preference;
    private LayoutInflater inflater;
    protected final StartPage context;
    protected final FrameLayout parentFrameLayout;
    protected final LinearLayout parentLinearLayout;


    private TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> myAllergies;
    protected TreeMap<Integer, TreeMap<Integer, LinearLayout>> linearLayoutToInsertLater = new TreeMap<>();
    protected TreeMap<Integer, Integer> parentKeyToParentImage = new TreeMap<>();
    protected TreeMap<Integer,LinearLayout> parentLinearLayoutHashMap = new TreeMap<>();
    private TreeMap<String,AllergyCheckBoxClass> allergyInfo = new TreeMap<>();

    public LoadUIAllergies(boolean preference,LayoutInflater inflater, StartPage context, FrameLayout parentFrameLayout, LinearLayout parentLinearLayout, TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> myAllergies) {
        this.preference = preference;
        this.inflater = inflater;
        this.context = context;

        this.parentFrameLayout = parentFrameLayout;
        this.parentLinearLayout = parentLinearLayout;
        this.myAllergies = myAllergies;
        new AddCategories().execute();
    }

    /**
     * add all categories
     * @param parentKey to set.
     * @param parentKeyToPicture
     */
    private void addCategory(int parentKey, CheckBox parentCheckBox, int parentKeyToPicture){
        TreeMap<Integer,LinearLayout> categoriesLinearLayout = new TreeMap<>();

        ArrayList<AllergyCheckBoxClass> allergyCheckBoxClasses = new ArrayList<>();
        for (AllergyList.PictureIngredient pictureIngredient : myAllergies.get(parentKey)) {

            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout, null);
            TextView textOfAllergy = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);
            ImageView imageOfAllergy = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);
            CheckBox checkBoxOfAllergy = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);

            textOfAllergy.setText(pictureIngredient.getIngredient());
            imageOfAllergy.setImageResource(pictureIngredient.getPicture());
            categoriesLinearLayout.put(pictureIngredient.getId(),newLinearLayout);
            if(allergyInfo.containsKey(pictureIngredient.getIngredient())){
                AllergyCheckBoxClass allergyCheckBoxClass = new AllergyCheckBoxClass(checkBoxOfAllergy, parentCheckBox,
                        parentKeyToPicture, pictureIngredient.getId(), parentKey, pictureIngredient.getIngredient(),
                        allergyInfo.get(pictureIngredient.getIngredient()).getSameItemDifferentCategories());
                allergyInfo.get(pictureIngredient.getIngredient()).getSameItemDifferentCategories().add(allergyCheckBoxClass);
                allergyCheckBoxClasses.add(allergyCheckBoxClass);

            }else{
                allergyInfo.put(pictureIngredient.getIngredient(),new AllergyCheckBoxClass(checkBoxOfAllergy,
                        parentCheckBox,parentKeyToPicture,pictureIngredient.getId(),parentKey,
                        pictureIngredient.getIngredient()));
                allergyInfo.get(pictureIngredient.getIngredient()).getSameItemDifferentCategories().add(allergyInfo.get(pictureIngredient.getIngredient()));
                allergyCheckBoxClasses.add(allergyInfo.get(pictureIngredient.getIngredient()));
            }



        }
        String allergy = "";
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyCheckBoxClasses) {
            allergyCheckBoxClass.setNeigbhourClasses(allergyCheckBoxClasses);
            allergy = allergyCheckBoxClass.getChildIngredient();
        }
        linearLayoutToInsertLater.put(parentKey,categoriesLinearLayout);



    }

    private CheckBox createParentLinearLayout(final int key, int pictureID){
        final LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
        LinearLayout linearLayoutRow = (LinearLayout) linearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        ((TextView) linearLayout.findViewById(R.id.textViewCategory)).setText(context.getString(key));

        final CheckBox checkboxRowCategory = (CheckBox) linearLayoutRow.findViewById(R.id.checkBoxRowCategory);


        ImageView imageIcon = (ImageView) linearLayoutRow.findViewById(R.id.imageViewRowCategory);
        imageIcon.setImageResource(pictureID);
        final ImageView dropDownImage = (ImageView) linearLayoutRow.findViewById(R.id.dropDownList);

        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLinearLayout.onclickDropDownList(dropDownImage,linearLayoutToInsertLater.get(key),linearLayout);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLinearLayout.onclickDropDownList(dropDownImage,linearLayoutToInsertLater.get(key),linearLayout);
            }
        });

        parentLinearLayoutHashMap.put(key,linearLayout);

        return checkboxRowCategory;

    }



    private class AddCategories extends AsyncTask<String, Integer, String> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... params) {
            for (int key : myAllergies.keySet()) {
                parentKeyToParentImage.put(key,myAllergies.get(key).get(0).getPicture());
            }
            for (int key : myAllergies.keySet()) {

                addCategory(key,createParentLinearLayout(key,parentKeyToParentImage.get(key)),parentKeyToParentImage.get(key));
            }
            return null;
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String s) {
            for (int key : linearLayoutToInsertLater.keySet()) {

                parentLinearLayout.addView(parentLinearLayoutHashMap.get(key));
            }

            for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                    final CheckBox checkBox = checkBoxClass.getChildCheckBox();
                    checkBox.setChecked(SharedPreferenceClass.checkBoolean(checkBoxClass.getChildIngredient(),context));

                    if(checkBox.isChecked()){
                        checkBoxClass.getParentCheckBox().setChecked(true);
                        checkBoxClass.setOn(true);
                    }
                    CreateLinearLayout.checkBoxChildOnCheckedListener(checkBoxClass);
                }
            }
            HashSet<Integer> hashSet = new HashSet<>();

            for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                if(!hashSet.contains(allergyCheckBoxClass.getParentKey())){
                    hashSet.add(allergyCheckBoxClass.getParentKey());
                    CreateLinearLayout.parentCheckedChanged(allergyCheckBoxClass);

                }
            }
            if(!preference){
                Log.d(TAG, "onPostExecute: " + SharedPreferenceClass.getSetFromPreference(context).size());
                HashSet<String> hashSetCheckedAllergies = (HashSet<String>) SharedPreferenceClass.getSetFromPreference(context);
                HashSet<String> hashSetCheckedNotAllergies = (HashSet<String>) SharedPreferenceClass.getSetFromNotPreference(context);
                for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                    if(hashSetCheckedAllergies.contains(context.getString(allergyCheckBoxClass.getParentKey()))){
                        allergyCheckBoxClass.getParentCheckBox().setChecked(true);
                    }
                    if(hashSetCheckedNotAllergies.contains(context.getString(allergyCheckBoxClass.getParentKey()))){
                        allergyCheckBoxClass.getParentCheckBox().setChecked(false);
                    }
                }
                hashSetCheckedAllergies.clear();
                SharedPreferenceClass.setSetFromPreference(context,hashSetCheckedAllergies);
            }else{
                HashSet<Integer> hashSetCheckedAllergies =  SharedPreferenceClass.getSetFromAllergy(context);

                for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                    if(hashSetCheckedAllergies.contains(allergyCheckBoxClass.getChildKey())){
                        allergyCheckBoxClass.getChildCheckBox().setChecked(true);
                    }

                }
            }
            parentLinearLayout.addView(new TextView(context));
            parentLinearLayout.addView(new TextView(context));
            parentLinearLayout.addView(new TextView(context));
            parentLinearLayout.addView(new TextView(context));


            parentLinearLayout.removeView(parentLinearLayout.findViewById(R.id.progressBarAllergy));

            super.onPostExecute(s);
        }
    }

    public HashSet<String> getCurrentlyActiveAllergies() {
        HashSet<String> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                if(checkBoxClass.isOn()){
                    hashSet.add(checkBoxClass.getChildIngredient());
                }
            }
        }
        return hashSet;
    }
    public HashSet<String> getCurrentlyNotActiveAllergies() {
        HashSet<String> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                if(!checkBoxClass.isOn()){
                    hashSet.add(checkBoxClass.getChildIngredient());
                }
            }
        }
        return hashSet;
    }

    public HashSet<Integer> getCurrentlyActiveParentAllergies() {
        HashSet<Integer> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            if(allergyCheckBoxClass.getParentCheckBox().isChecked()){
                hashSet.add(allergyCheckBoxClass.getParentKey());


            }
        }
        return hashSet;
    }

    public HashMap<String, CheckBox> getCheckBoxToRemove() {
        HashMap<String, CheckBox> hashMap = new HashMap<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            if(allergyCheckBoxClass.isRemove()){
                hashMap.put(allergyCheckBoxClass.getChildIngredient(),allergyCheckBoxClass.getChildCheckBox());
            }
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {

                if(checkBoxClass.isRemove()){
                    hashMap.put(checkBoxClass.getChildIngredient(),checkBoxClass.getChildCheckBox());
                }

            }
        }
        return hashMap;
    }
    public void saveCurrentlyActive(boolean b){
        for (String key : getCheckBoxToRemove().keySet()) {
            SharedPreferenceClass.setBoolean(key,context,false);
        }

        for (String key : getCurrentlyActiveAllergies()) {
            SharedPreferenceClass.setBoolean(key, context,true);
        }
        if(b){
            SharedPreferenceClass.setSetFromPreference(context,getCurrentlyActiveAllergies());
            SharedPreferenceClass.setSetFromNotPreference(context,getCurrentlyNotActiveAllergies());

        }
        if(!b){
            SharedPreferenceClass.setSetFromAllergy(context,getCurrentlyActiveParentAllergies());
        }
        saveAllergies();

    }
    private void saveAllergies(){
        HashSet<Integer> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                if(checkBoxClass.isOn()){
                    hashSet.add(checkBoxClass.getChildKey());
                }
            }
        }
        if(!preference){
            for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                    if(checkBoxClass.getParentCheckBox().isChecked()){
                        hashSet.add(checkBoxClass.getParentKey());
                    }
                }
            }
            SharedPreferenceClass.setSet(context,hashSet);
        }else{
            SharedPreferenceClass.setSetPreference(context,hashSet);
            for (Integer integer : SharedPreferenceClass.getSetPreference(context)) {
                Log.d(TAG, "getSetPreference: " + context.getString(integer));
            }
        }

    }


}
