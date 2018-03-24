package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.res.Resources;
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
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * load my preference and my allergies.
 * <p>
 * Created by Enathen on 2018-01-12.
 */

public class LoadUIAllergies {
    private static final String TAG = "LoadUIAllergies";
    protected StartPage context;
    protected FrameLayout parentFrameLayout;
    protected LinearLayout parentLinearLayout;
    protected TreeMap<Integer, LinkedHashMap<Integer, LinearLayout>> linearLayoutToInsertLater = new TreeMap<>();
    protected TreeMap<Integer, Integer> parentKeyToParentImage = new TreeMap<>();
    protected TreeMap<Integer, LinearLayout> parentLinearLayoutHashMap = new TreeMap<>();
    private boolean preference;
    private LayoutInflater inflater;
    private TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> myAllergies;
    private ConcurrentHashMap<String, AllergyCheckBoxClass> allergyInfo = new ConcurrentHashMap<>();


    public LoadUIAllergies(boolean preference, LayoutInflater inflater, StartPage context, FrameLayout parentFrameLayout, LinearLayout parentLinearLayout, TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> myAllergies) {
        this.preference = preference;
        this.inflater = inflater;
        this.context = context;

        this.parentFrameLayout = parentFrameLayout;
        this.parentLinearLayout = parentLinearLayout;
        this.myAllergies = myAllergies;
        new AddCategories().execute();
    }

    public LoadUIAllergies() {

    }

    /**
     * add categories
     *
     * @param parentKey          int of parent
     * @param parentCheckBox     checkbox of parent
     * @param parentKeyToPicture key to picture of parent
     */
    private void addCategory(int parentKey, CheckBox parentCheckBox, int parentKeyToPicture) {
        LinkedHashMap<Integer, LinearLayout> categoriesLinearLayout = new LinkedHashMap<>();

        ArrayList<AllergyCheckBoxClass> allergyCheckBoxClasses = new ArrayList<>();
        for (AllergyList.PictureIngredient pictureIngredient : myAllergies.get(parentKey)) {

            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.leftmarginrowlayout, null);
            TextView textOfAllergy = (TextView) newLinearLayout.findViewById(R.id.textViewLeftMargin);
            ImageView imageOfAllergy = (ImageView) newLinearLayout.findViewById(R.id.imageViewLeftMargin);
            CheckBox checkBoxOfAllergy = (CheckBox) newLinearLayout.findViewById(R.id.checkBoxRowLeftMargin);

            textOfAllergy.setText(TextHandler.capitalLetter(pictureIngredient.getIngredient()));
            imageOfAllergy.setImageResource(pictureIngredient.getPicture());
            categoriesLinearLayout.put(pictureIngredient.getId(), newLinearLayout);
            if (allergyInfo.containsKey(pictureIngredient.getIngredient())) {
                AllergyCheckBoxClass allergyCheckBoxClass = new AllergyCheckBoxClass(checkBoxOfAllergy, parentCheckBox,
                        parentKeyToPicture, pictureIngredient.getId(), parentKey, pictureIngredient.getIngredient(),
                        allergyInfo.get(pictureIngredient.getIngredient()).getSameItemDifferentCategories());
                allergyInfo.get(pictureIngredient.getIngredient()).getSameItemDifferentCategories().add(allergyCheckBoxClass);
                allergyCheckBoxClasses.add(allergyCheckBoxClass);

            } else {
                allergyInfo.put(pictureIngredient.getIngredient(), new AllergyCheckBoxClass(checkBoxOfAllergy,
                        parentCheckBox, parentKeyToPicture, pictureIngredient.getId(), parentKey,
                        pictureIngredient.getIngredient()));
                allergyInfo.get(pictureIngredient.getIngredient()).getSameItemDifferentCategories().add(allergyInfo.get(pictureIngredient.getIngredient()));
                allergyCheckBoxClasses.add(allergyInfo.get(pictureIngredient.getIngredient()));
            }


        }
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyCheckBoxClasses) {
            allergyCheckBoxClass.setNeighbourClasses(allergyCheckBoxClasses);

        }
        linearLayoutToInsertLater.put(parentKey, categoriesLinearLayout);


    }

    /**
     * create parents linear layout
     * @param key to parent
     * @param pictureID to parent
     * @return checkbox of parent
     */
    private CheckBox createParentLinearLayout(final int key, int pictureID) {
        final LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.rowcategorylayout, null);
        LinearLayout linearLayoutRow = (LinearLayout) linearLayout.findViewById(R.id.linearLayoutRowCategoryHorizontal);
        ((TextView) linearLayout.findViewById(R.id.textViewCategory)).setText(TextHandler.capitalLetter(context.getString(key)));

        final CheckBox checkboxRowCategory = (CheckBox) linearLayoutRow.findViewById(R.id.checkBoxRowCategory);


        ImageView imageIcon = (ImageView) linearLayoutRow.findViewById(R.id.imageViewRowCategory);
        imageIcon.setImageResource(pictureID);
        final ImageView dropDownImage = (ImageView) linearLayoutRow.findViewById(R.id.dropDownList);

        dropDownImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLinearLayout.onclickDropDownList(dropDownImage, linearLayoutToInsertLater.get(key), linearLayout);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateLinearLayout.onclickDropDownList(dropDownImage, linearLayoutToInsertLater.get(key), linearLayout);
            }
        });

        parentLinearLayoutHashMap.put(key, linearLayout);

        return checkboxRowCategory;

    }

    /**
     * save pictures
     * @param context to get drawables
     * @param imageViewHashMap of positions to set
     */
    synchronized void savePicture(StartPage context, HashMap<Integer, ImageView> imageViewHashMap) {
        String alreadyString = "00000000";
        Log.d(TAG, "savePicture: ");

        for (ImageView imageView : imageViewHashMap.values()) {
            imageView.setImageDrawable(context.getDrawable(R.drawable.emptyborder));
        }
        HashSet<Integer> allergySavePicture = SharedPreferenceClass.getSharedPreference(context, "allergySavePicture", TAG);
        HashSet<Integer> allergySavePreference = (SharedPreferenceClass.getSharedPreference(context, "preferenceSavePicture", TAG));



        addPicture(allergySavePreference, alreadyString, imageViewHashMap, addPicture(allergySavePicture, alreadyString, imageViewHashMap, 0));


    }

    /**
     * add pictures. set imageresources of nav drawer icon
     * @param allergySavePicture piasters
     * @param alreadyString of used allergies
     * @param imageViewHashMap pos to set
     * @param i already set
     * @return i
     */
    private int addPicture(HashSet<Integer> allergySavePicture, String alreadyString, HashMap<Integer, ImageView> imageViewHashMap, int i) {
        ArrayList<Integer> alreadySelectedImages = new ArrayList<>();
        HashSet<Integer> allergySavePictureNew = new HashSet<>();
        HashMap<Integer, Integer> integerIntegerHashMap = ValidateAllergiesPreferences.setupIDToValuePicture();
        Log.d(TAG, "addPicture: "+ allergySavePicture);
        for (Integer integer : allergySavePicture) {
            Log.d(TAG, "addPicture: "+ integer + " : "+integerIntegerHashMap.get(integer));
            allergySavePictureNew.add(integerIntegerHashMap.get(integer));
        }
        Log.d(TAG, "addPicture: "+ allergySavePictureNew);
        allergySavePictureNew.remove(null);
        for (int id : allergySavePictureNew) {
            if (i > 7) {
                break;
            }

            if (alreadyString.charAt(i) == '0') {
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
        return i;
    }

    /**
     * delete harmful picture
     * @param context context
     * @param allergySavePicture pictures
     * @param allergySavePictureName name of pictures
     * @param savePicture pictures to save tag
     * @param savePictureName pictures name save tag
     */
    private void checkDeleteAllergySavePicture(StartPage context, HashSet<Integer> allergySavePicture, Set<String> allergySavePictureName, String savePicture, String savePictureName) {
        HashSet<Integer> allergyToRemove = new HashSet<>();

        for (Integer integer : allergySavePicture) {
            try {
                int drawable = context.getResources().getIdentifier(String.valueOf(integer), "drawable", context.getPackageName());
                context.getDrawable(integer);
                if (!allergySavePictureName.contains(context.getResources().getResourceEntryName(drawable))) {
                    allergyToRemove.add(drawable);
                    allergySavePictureName.remove(context.getResources().getResourceEntryName(drawable));
                    Log.d(TAG, "PICTUREREMOVE1: ");
                }
                if (!new AllergyList(context).checkAvailablePicture(integer)) {
                    allergyToRemove.add(drawable);
                    Log.d(TAG, "PICTUREREMOVE2: ");
                }
            } catch (Resources.NotFoundException e) {
                allergyToRemove.add(context.getResources().getIdentifier(String.valueOf(integer), "drawable", context.getPackageName()));
                Log.d(TAG, "PICTUREREMOVE3: ");

            }
        }

        allergySavePicture.removeAll(allergyToRemove);
        SharedPreferenceClass.setSharedPreference(context, allergySavePicture, savePicture, TAG);
        SharedPreferenceClass.setSharedPreference(context, (HashSet<String>) allergySavePictureName, savePictureName, TAG);
    }


    private synchronized HashSet<String> getCurrentlyActiveAllergies() {
        HashSet<String> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                if (checkBoxClass.isOn()) {
                    hashSet.add(checkBoxClass.getChildIngredient());
                }
            }
        }
        return hashSet;
    }

    private synchronized HashSet<String> getCurrentlyNotActiveAllergies() {
        HashSet<String> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                if (!checkBoxClass.isOn()) {
                    hashSet.add(checkBoxClass.getChildIngredient());
                }
            }
        }
        return hashSet;
    }

    private synchronized HashSet<Integer> getCurrentlyActiveParentAllergies() {
        HashSet<Integer> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            if (allergyCheckBoxClass.getParentCheckBox().isChecked()) {
                hashSet.add(allergyCheckBoxClass.getParentKey());


            }
        }
        return hashSet;
    }

    private synchronized HashSet<Integer> getCurrentlyNotActiveParentAllergies() {
        HashSet<Integer> hashSet = new HashSet<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            if (!allergyCheckBoxClass.getParentCheckBox().isChecked()) {
                hashSet.add(allergyCheckBoxClass.getParentKey());
            }
        }
        return hashSet;
    }

    private synchronized HashMap<String, CheckBox> getCheckBoxToRemove() {
        HashMap<String, CheckBox> hashMap = new HashMap<>();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            if (allergyCheckBoxClass.isRemove()) {
                hashMap.put(allergyCheckBoxClass.getChildIngredient(), allergyCheckBoxClass.getChildCheckBox());
            }
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {

                if (checkBoxClass.isRemove()) {
                    hashMap.put(checkBoxClass.getChildIngredient(), checkBoxClass.getChildCheckBox());
                }

            }
        }
        return hashMap;
    }

    synchronized void saveCurrentlyActive(boolean preference) {
        Log.d(TAG, "saveCurrentlyActive: ");
        for (String key : getCheckBoxToRemove().keySet()) {
            SharedPreferenceClass.setBoolean(key, context, false);
        }

        for (String key : getCurrentlyActiveAllergies()) {
            SharedPreferenceClass.setBoolean(key, context, true);
        }

        if (!preference) {
            SharedPreferenceClass.setSharedPreference(context, getCurrentlyActiveParentAllergies(), "allergyToSendToPreference", TAG);
            SharedPreferenceClass.setSharedPreference(context, getCurrentlyNotActiveParentAllergies(), "notAllergyToSendToPreference", TAG);
            saveAllergies();
        }
        if (preference) {

            SharedPreferenceClass.setSharedPreference(context, getCurrentlyActiveAllergies(), "preferenceToSendToAllergy", TAG);
            SharedPreferenceClass.setSharedPreference(context, getCurrentlyNotActiveAllergies(), "notPreferenceToSendToAllergy", TAG);
            savePreference();
        }


    }

    private void saveAllergies() {
        HashSet<Integer> hashSet = new HashSet<>();
        HashSet<Integer> hashSetPicture = new HashSet<>();
        HashSet<Integer> hashSetNot = new HashSet<>();
        HashSet<Integer> hashSetPictureNot = new HashSet<>();
        HashSet<Integer> hashSetPreference = SharedPreferenceClass.getSharedPreference(context, "preferenceSave", TAG);
        HashSet<Integer> hashSetPicturePreference = SharedPreferenceClass.getSharedPreference(context, "preferenceSavePicture", TAG);
        getCheckedCheckBoxes(hashSet, hashSetPicture);
        getCheckedCheckBoxesNot(hashSetNot, hashSetPictureNot);

        saveHashSet(hashSet, "allergySave");
        saveHashSet(hashSetPicture, "allergySavePicture");

        ValidateAllergiesPreferences.setupPictureToValueID();
        addPreferences(hashSet, hashSetPreference, hashSetPicturePreference);
        removePreferences(hashSetNot, hashSetPreference, hashSetPicturePreference);

        saveHashSet(hashSetPreference, "preferenceSave");
        saveHashSet(hashSetPicturePreference, "preferenceSavePicture");
    }

    private void removePreferences(HashSet<Integer> hashSetNot, HashSet<Integer> hashSetPreference, HashSet<Integer> hashSetPicturePreference) {
        TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> myPreference = new AllergyList(context).getMyPreference();
        HashMap<Integer, Integer> allergyKey = ValidateAllergiesPreferences.setupAllergy();
        HashMap<Integer, Integer> keyAllergy = ValidateAllergiesPreferences.setupKey();
        for (Integer integer : hashSetNot) {
            Integer integer1 = keyAllergy.get(integer);

            ArrayList<AllergyList.PictureIngredient> specifiedKey = new AllergyList(context).getSpecifiedKeyAllergy(integer1);
            if (specifiedKey != null) {
                hashSetPreference.remove(allergyKey.get(integer));
                for (Integer pictureIngredients : myPreference.keySet()) {
                    for (AllergyList.PictureIngredient pictureIngredient : myPreference.get(pictureIngredients)) {
                        if (pictureIngredient.getId() == integer1) {
                            hashSetPicturePreference.remove(ValidateAllergiesPreferences.pictureToId.get(pictureIngredient.getPicture()));
                        }
                    }

                }
            }
        }
    }

    private void addPreferences(HashSet<Integer> hashSet, HashSet<Integer> hashSetPreference, HashSet<Integer> hashSetPicturePreference) {
        TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> myPreference = new AllergyList(context).getMyPreference();
        HashMap<Integer, Integer> keyAllergy = ValidateAllergiesPreferences.setupKey();
        HashMap<Integer, Integer> allergyKey = ValidateAllergiesPreferences.setupAllergy();
        for (Integer integer : hashSet) {
            Integer integer1 = keyAllergy.get(integer);
            ArrayList<AllergyList.PictureIngredient> specifiedKey = new AllergyList(context).getSpecifiedKeyAllergy(integer1);
            if (specifiedKey != null) {
                hashSetPreference.add(integer);
                for (Integer pictureIngredients : myPreference.keySet()) {
                    boolean addPreference = true;
                    for (AllergyList.PictureIngredient pictureIngredient : myPreference.get(pictureIngredients)) {
                        if (pictureIngredient.getId() == integer1) {

                            //Log.d(TAG, "addPreferences: " + context.getString(pictureIngredient.getId()) + " Parent:" + context.getString(pictureIngredients));
                        } else {
                            if (!hashSetPreference.contains(allergyKey.get(pictureIngredient.getId()))) {
                                addPreference = false;
                            }
                        }
                    }
                    if (addPreference) {
                        hashSetPicturePreference.add(ValidateAllergiesPreferences.pictureToId.get(myPreference.get(pictureIngredients).get(0).getPicture()));
                    }
                }
            }


        }
    }

    public void printHashSet(HashSet<Integer> hashSet, String name) {
        for (Integer integer : hashSet) {
            Log.d(TAG, "Name: " + name + " : String: " + context.getString(integer));
        }
    }


    synchronized private void savePreference() {
        HashSet<Integer> hashSet = new HashSet<>();
        HashSet<Integer> hashSetPicture = new HashSet<>();
        HashSet<Integer> hashSetNot = new HashSet<>();
        HashSet<Integer> hashSetPictureNot = new HashSet<>();
        HashSet<Integer> hashSetAllergies = SharedPreferenceClass.getSharedPreference(context, "allergySave", TAG);
        HashSet<Integer> hashSetPictureAllergies = SharedPreferenceClass.getSharedPreference(context, "allergySavePicture", TAG);
        getCheckedCheckBoxes(hashSet, hashSetPicture);
        getCheckedCheckBoxesNot(hashSetNot, hashSetPictureNot);

        //debug(hashSet,hashSetPicture,hashSetNot,hashSetPictureNot,hashSetAllergies,hashSetPictureAllergies);


        saveHashSet(hashSet, "preferenceSave");
        saveHashSet(hashSetPicture, "preferenceSavePicture");
        ValidateAllergiesPreferences.setupIDToValuePicture();
        ValidateAllergiesPreferences.setupPictureToValueID();
        Log.d(TAG, "savePreference: ");
        removeAllergies(hashSetNot, hashSetAllergies, hashSetPictureAllergies);
        addAllergies(hashSet, hashSetAllergies, hashSetPictureAllergies);
        saveHashSet(hashSetAllergies, "allergySave");
        saveHashSet(hashSetPictureAllergies, "allergySavePicture");



    }

    private void savePictureName(HashSet<Integer> hashSetPicture, String name) {
        HashSet<String> hashSetPictureName = new HashSet<>();
        for (Integer integer : hashSetPicture) {
            hashSetPictureName.add(context.getResources().getResourceEntryName(integer));
        }
        SharedPreferenceClass.setSharedPreference(context, hashSetPictureName, name, TAG);

    }

    private void debug(HashSet<Integer> hashSet, HashSet<Integer> hashSetPicture, HashSet<Integer> hashSetNot, HashSet<Integer> hashSetPictureNot, HashSet<Integer> hashSetAllergies, HashSet<Integer> hashSetPictureAllergies) {
        printHashSet(hashSet, "hashSet");
        printHashSet(hashSetPicture, "hashSetPicture");
        printHashSet(hashSetNot, "hashSetNot");
        printHashSet(hashSetPictureNot, "hashSetPictureNot");
        printHashSet(hashSetAllergies, "hashSetOPOSITE");
        printHashSet(hashSetPictureAllergies, "hashSetPictureOPOSITE");
    }


    private void addAllergies(HashSet<Integer> hashSet, HashSet<Integer> hashSetAllergies, HashSet<Integer> hashSetPictureAllergies) {
        HashMap<Integer, Integer> keyAllergy = ValidateAllergiesPreferences.setupKey();
        for (Integer integer : hashSet) {
            Integer integer1 = keyAllergy.get(integer);
            ArrayList<AllergyList.PictureIngredient> specifiedKey = new AllergyList(context).getSpecifiedKeyAllergy(integer1);
            if (specifiedKey != null) {
                for (AllergyList.PictureIngredient pictureIngredient : specifiedKey) {
                    hashSetAllergies.add(pictureIngredient.getId());

                }
                hashSetAllergies.add(integer);
                hashSetPictureAllergies.add(ValidateAllergiesPreferences.pictureToId.get(specifiedKey.get(0).getPicture()));
            }
        }
    }

    private void removeAllergies(HashSet<Integer> hashSetNot, HashSet<Integer> hashSetAllergies, HashSet<Integer> hashSetPictureAllergies) {
        HashMap<Integer, Integer> allergyKey = ValidateAllergiesPreferences.setupKey();
        for (Integer integer : hashSetNot) {
            Integer integer1 = allergyKey.get(integer);
            Log.d(TAG, "removeAllergies: " + integer1);
            if (hashSetAllergies.contains(integer)) {
                ArrayList<AllergyList.PictureIngredient> specifiedKey = new AllergyList(context).getSpecifiedKeyAllergy(integer1);
                HashSet<Integer> remove = new HashSet<>();
                for (AllergyList.PictureIngredient pictureIngredient : specifiedKey) {
                    remove.add(allergyKey.get(pictureIngredient.getId()));
                }
                hashSetAllergies.removeAll(remove);
                hashSetAllergies.remove(integer);
                hashSetPictureAllergies.remove(ValidateAllergiesPreferences.pictureToId.get(specifiedKey.get(0).getPicture()));
            }
        }
    }

    private void saveHashSet(HashSet<Integer> hashSet, String name) {
        SharedPreferenceClass.setSharedPreference(context, hashSet, name, TAG);
    }

    private void getCheckedCheckBoxesNot(HashSet<Integer> hashSetNot, HashSet<Integer> hashSetPictureNot) {
        HashMap<Integer, Integer> integerIntegerHashMap = ValidateAllergiesPreferences.setupAllergy();
        HashMap<Integer, Integer> pictureToValueID = ValidateAllergiesPreferences.setupPictureToValueID();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                //if all checked
                if (!checkBoxClass.getParentCheckBox().isChecked()) {
                    hashSetPictureNot.add(pictureToValueID.get(checkBoxClass.getParentPicture()));
                }
                if (!checkBoxClass.getChildCheckBox().isChecked()) {
                    hashSetNot.add(integerIntegerHashMap.get(checkBoxClass.getChildKey()));
                    //Log.d(TAG, "getCheckedCheckBoxesNot: " + integerIntegerHashMap.get(checkBoxClass.getChildKey()) + " : " + context.getString(checkBoxClass.getChildKey()));
                    if (!preference) {
                        hashSetPictureNot.add(pictureToValueID.get(checkBoxClass.getParentPicture()));
                        hashSetNot.add(integerIntegerHashMap.get(checkBoxClass.getParentKey()));

                    }
                }
            }
        }
    }

    private void getCheckedCheckBoxes(HashSet<Integer> hashSet, HashSet<Integer> hashSetPicture) {
        HashMap<Integer, Integer> integerIntegerHashMap = ValidateAllergiesPreferences.setupAllergy();
        HashMap<Integer, Integer> pictureToValueID = ValidateAllergiesPreferences.setupPictureToValueID();
        for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
            for (AllergyCheckBoxClass checkBoxClass : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                //if all checked
                if (checkBoxClass.getParentCheckBox().isChecked()) {
                    hashSetPicture.add(pictureToValueID.get(checkBoxClass.getParentPicture()));
                }
                if (checkBoxClass.getChildCheckBox().isChecked()) {
                    hashSet.add(integerIntegerHashMap.get(checkBoxClass.getChildKey()));
                    if (!preference) {
                        hashSetPicture.add(pictureToValueID.get(checkBoxClass.getParentPicture()));
                        hashSet.add(integerIntegerHashMap.get(checkBoxClass.getParentKey()));
                    }
                }
            }
        }
    }

    synchronized public HashSet<Integer> getAllergies(Context startPage) {
        HashSet<Integer> sharedPreference = SharedPreferenceClass.getSharedPreference(startPage, "allergySave", "LoadUIAllergies");
        sharedPreference.addAll(SharedPreferenceClass.getSharedPreference(startPage, "preferenceSave", "LoadUIAllergies"));
        HashMap<Integer, Integer> allergy = ValidateAllergiesPreferences.setupKey();
        HashSet<Integer> integers = new HashSet<>();
        for (Integer integer : sharedPreference) {
            integers.add(allergy.get(integer));
            //Log.d(TAG, "getAllergies: "+ allergy.get(integer) + " : " + integer);
        }
        integers.remove(null);

        return integers;
    }


    public void checkStringDelete(StartPage startPage) {
        TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> allergies = new AllergyList(startPage.getBaseContext()).getMyAllergies();
        TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> preference = new AllergyList(startPage.getBaseContext()).getMyPreference();
        HashSet<Integer> myAllergies = SharedPreferenceClass.getSharedPreference(startPage, "allergySave", TAG);
        HashSet<Integer> myPreference = SharedPreferenceClass.getSharedPreference(startPage, "preferenceSave", TAG);
        removeStringAllergies(startPage, allergies, myAllergies, "allergySave");
        removeStringAllergies(startPage, preference, myPreference, "preferenceSave");


    }

    private void removeStringAllergies(StartPage startPage, TreeMap<Integer, ArrayList<AllergyList.PictureIngredient>> allergies, HashSet<Integer> myAllergies, String name) {
        HashSet<Integer> allergiesToRemove = new HashSet<>();
        for (Integer myAllergy : myAllergies) {
            try {
                startPage.getString(myAllergy);
                boolean destroy = true;
                for (Integer integer : allergies.keySet()) {
                    if (integer.equals(myAllergy)) {
                        destroy = false;
                        break;
                    }
                    for (AllergyList.PictureIngredient pictureIngredients : allergies.get(integer)) {
                        if (pictureIngredients.getId() == myAllergy) {
                            destroy = false;
                            break;
                        }
                    }
                    if (!destroy) {
                        break;
                    }
                }
                if (destroy) {
                    allergiesToRemove.add(myAllergy);
                }
            } catch (Resources.NotFoundException e) {
                allergiesToRemove.add(myAllergy);
            }
        }
        myAllergies.removeAll(allergiesToRemove);
        SharedPreferenceClass.setSharedPreference(startPage, myAllergies, name, TAG);
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
                parentKeyToParentImage.put(key, myAllergies.get(key).get(0).getPicture());
            }
            for (int key : myAllergies.keySet()) {

                addCategory(key, createParentLinearLayout(key, parentKeyToParentImage.get(key)), parentKeyToParentImage.get(key));
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
                    checkBox.setChecked(SharedPreferenceClass.checkBoolean(checkBoxClass.getChildIngredient(), context));
                    checkBoxClass.setOn(SharedPreferenceClass.checkBoolean(checkBoxClass.getChildIngredient(), context));
                    if (!preference) {
                        if (checkBox.isChecked()) {
                            checkBoxClass.getParentCheckBox().setChecked(true);
                            checkBoxClass.setOn(true);
                        }
                        CreateLinearLayout.checkBoxChildOnCheckedListener(checkBoxClass);
                    } else {
                        CreateLinearLayout.checkBoxChildOnCheckedListenerPreference(checkBoxClass);

                    }

                }
            }
            HashSet<Integer> hashSet = new HashSet<>();

            for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                if (!hashSet.contains(allergyCheckBoxClass.getParentKey())) {
                    hashSet.add(allergyCheckBoxClass.getParentKey());
                    CreateLinearLayout.parentCheckedChanged(allergyCheckBoxClass);
                    if (preference) {
                        for (AllergyCheckBoxClass allergyCheckBoxClass1 : allergyCheckBoxClass.getSameItemDifferentCategories()) {
                            CreateLinearLayout.checkParentShouldChecked(allergyCheckBoxClass1, true);
                        }

                    }

                }
            }
            if (!preference) {
                HashSet<String> hashSetCheckedAllergies = (HashSet<String>) SharedPreferenceClass.getSharedPreferenceString(context, "preferenceToSendToAllergy", TAG);
                HashSet<String> hashSetCheckedNotAllergies = (HashSet<String>) SharedPreferenceClass.getSharedPreferenceString(context, "notPreferenceToSendToAllergy", TAG);
                HashSet<Integer> alreadyChecked = new HashSet<>();
                for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                    if (!alreadyChecked.contains(allergyCheckBoxClass.getParentKey())) {
                        if (hashSetCheckedAllergies.contains(context.getString(allergyCheckBoxClass.getParentKey()))) {
                            alreadyChecked.add(allergyCheckBoxClass.getParentKey());
                            Log.d(TAG, "lägger till från preference: " + context.getString(allergyCheckBoxClass.getParentKey()));
                            allergyCheckBoxClass.getParentCheckBox().setChecked(true);
                        }
                        if (hashSetCheckedNotAllergies.contains(context.getString(allergyCheckBoxClass.getParentKey()))) {
                            alreadyChecked.add(allergyCheckBoxClass.getParentKey());
                            Log.d(TAG, "TAR bort från preference: " + context.getString(allergyCheckBoxClass.getParentKey()));
                            allergyCheckBoxClass.getParentCheckBox().setChecked(false);
                        }
                    }

                }

                hashSetCheckedAllergies.clear();
                hashSetCheckedNotAllergies.clear();
                SharedPreferenceClass.setSharedPreference(context, hashSetCheckedAllergies, "preferenceToSendToAllergy", TAG);
                SharedPreferenceClass.setSharedPreference(context, hashSetCheckedAllergies, "notPreferenceToSendToAllergy", TAG);
            } else {
                HashSet<Integer> hashSetCheckedAllergies = SharedPreferenceClass.getSharedPreference(context, "allergyToSendToPreference", TAG);
                HashSet<Integer> hashSetCheckedNotAllergies = SharedPreferenceClass.getSharedPreference(context, "notAllergyToSendToPreference", TAG);

                for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.values()) {
                    if (hashSetCheckedAllergies.contains(allergyCheckBoxClass.getChildKey())) {
                        Log.d(TAG, "lägger till från allergy: " + context.getString(allergyCheckBoxClass.getParentKey()));
                        allergyCheckBoxClass.getChildCheckBox().setChecked(true);
                    }
                    if (hashSetCheckedNotAllergies.contains(allergyCheckBoxClass.getChildKey())) {
                        Log.d(TAG, "TAR bort från allergy: " + context.getString(allergyCheckBoxClass.getParentKey()));
                        allergyCheckBoxClass.getChildCheckBox().setChecked(false);
                    }

                }
                hashSetCheckedAllergies.clear();
                hashSetCheckedNotAllergies.clear();
                SharedPreferenceClass.setSharedPreference(context, hashSetCheckedAllergies, "allergyToSendToPreference", TAG);
                SharedPreferenceClass.setSharedPreference(context, hashSetCheckedAllergies, "notAllergyToSendToPreference", TAG);
            }
            parentLinearLayout.addView(new TextView(context));
            parentLinearLayout.addView(new TextView(context));
            parentLinearLayout.addView(new TextView(context));
            parentLinearLayout.addView(new TextView(context));


            parentLinearLayout.removeView(parentLinearLayout.findViewById(R.id.progressBarAllergy));

            super.onPostExecute(s);
        }
    }


}