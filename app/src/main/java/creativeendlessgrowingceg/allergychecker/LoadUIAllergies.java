package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * load an UI.
 * Created by Enathen on 2018-01-12.
 */

public class LoadUIAllergies {
    private LayoutInflater inflater;
    protected final Context context;
    protected Activity activity;
    protected final FrameLayout parentFrameLayout;
    protected final LinearLayout parentLinearLayout;
    protected HashSet<Integer> currentlyActiveAllergies = new HashSet<>();
    protected HashMap<Integer, CheckBox> checkBoxToRemove = new HashMap<>();
    private HashMap<CheckBox,ArrayList<CheckBox>> checkBoxHashMap = new HashMap<>();
    private HashMap<Integer, ArrayList<AllergyList.PictureIngredient>> myAllergies;
    protected HashMap<Integer,HashSet<LinearLayout>> linearLayoutToInsertLater = new HashMap<>();
    protected HashMap<Integer, Integer> parentKeyToParentImage = new HashMap<>();
    protected HashMap<Integer,LinearLayout> parentLinearLayoutHashMap = new HashMap<>();
    private HashMap<CheckBox, AllergyList.PictureIngredient> myallergiesCheckBox = new HashMap<>();
    private HashMap<LinearLayout, Boolean> boolLinearLayout = new HashMap<>();
    private HashMap<Integer, ArrayList<CheckBox>> sameItemDifferentCategories= new HashMap<>();

    public LoadUIAllergies(LayoutInflater inflater, Context context, Activity activity, FrameLayout parentFrameLayout, LinearLayout parentLinearLayout, HashMap<Integer, ArrayList<AllergyList.PictureIngredient>> myAllergies) {
        this.inflater = inflater;
        this.context = context;
        this.activity = activity;

        this.parentFrameLayout = parentFrameLayout;
        this.parentLinearLayout = parentLinearLayout;
        this.myAllergies = myAllergies;
        new AddCategories().execute();
    }

    /**
     * add all categories
     * @param key to set.
     */
    private void addCategory(Integer key,CheckBox parentCheckBox){
        HashSet<LinearLayout> categoriesLinearLayout = new HashSet<>();
        ArrayList<CheckBox> categoriesCheckbox = new ArrayList<>();
        for (AllergyList.PictureIngredient pictureIngredient : myAllergies.get(key)) {
            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout, null);
            TextView textOfAllergy = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);
            ImageView imageOfAllergy = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);
            CheckBox checkBoxOfAllergy = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);

            textOfAllergy.setText(pictureIngredient.getIngredient());
            imageOfAllergy.setImageResource(pictureIngredient.getPicture());

            myallergiesCheckBox.put(checkBoxOfAllergy,pictureIngredient);
            categoriesCheckbox.add(checkBoxOfAllergy);
            categoriesLinearLayout.add(newLinearLayout);
            CreateLinearLayout.sameItemDifferentCategories(sameItemDifferentCategories,pictureIngredient.getId(),checkBoxOfAllergy);
        }
        checkBoxHashMap.put(parentCheckBox,categoriesCheckbox);
        linearLayoutToInsertLater.put(key,categoriesLinearLayout);


    }

    private CheckBox createParentLinearLayout(final int key, int pictureID){
        final LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
        LinearLayout linearLayoutRow = (LinearLayout) linearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        ((TextView) linearLayout.findViewById(R.id.textViewCategory)).setText(context.getString(key));

        final CheckBox checkboxRowCategory = (CheckBox) linearLayoutRow.findViewById(R.id.checkBoxRowCategory);


        ImageView imageIcon = (ImageView) linearLayoutRow.findViewById(R.id.imageViewRowCategory);
        imageIcon.setImageResource(pictureID);
        final ImageView dropDownImage = (ImageView) linearLayoutRow.findViewById(R.id.dropDownList);
        if(!boolLinearLayout.containsKey(parentLinearLayout)){
            boolLinearLayout.put(parentLinearLayout,false);
        }
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


        CreateLinearLayout.parentCheckedChanged(checkBoxHashMap.get(key),checkboxRowCategory);
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

                addCategory(key,createParentLinearLayout(key,parentKeyToParentImage.get(key)));
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

            for (final CheckBox parentCheckBox : checkBoxHashMap.keySet()) {
                for (final CheckBox checkBox : checkBoxHashMap.get(parentCheckBox)) {
                    if(checkBoxHashMap.containsKey(checkBox)){
                        for (CheckBox checkBoxes : checkBoxHashMap.get(checkBox)) {
                            if(checkBox.isChecked()){
                                checkBoxes.setChecked(true);
                            }
                        }
                    }
                    checkBox.setChecked(SharedPreferenceClass.checkBoolean(myallergiesCheckBox.get(checkBox).getIngredient(),context));
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            CreateLinearLayout.checkParentShouldChecked(checkBoxHashMap.get(parentCheckBox),parentCheckBox);
                            if(isChecked){
                                currentlyActiveAllergies.add(myallergiesCheckBox.get(checkBox).getId());
                            }else{
                                currentlyActiveAllergies.remove(myallergiesCheckBox.get(checkBox).getId());
                                checkBoxToRemove.put(myallergiesCheckBox.get(checkBox).getId(),checkBox);
                            }
                            for (CheckBox box: sameItemDifferentCategories.get(myallergiesCheckBox.get(checkBox).getId())) {
                                box.setChecked(isChecked);
                            }

                        }
                    });

                }

                CreateLinearLayout.checkParentShouldChecked(checkBoxHashMap.get(parentCheckBox),parentCheckBox);
            }

            parentLinearLayout.removeView(parentLinearLayout.findViewById(R.id.progressBarAllergy));
            super.onPostExecute(s);
        }
    }

    public HashSet<Integer> getCurrentlyActiveAllergies() {
        return currentlyActiveAllergies;
    }

    public HashMap<Integer, CheckBox> getCheckBoxToRemove() {
        return checkBoxToRemove;
    }
}
