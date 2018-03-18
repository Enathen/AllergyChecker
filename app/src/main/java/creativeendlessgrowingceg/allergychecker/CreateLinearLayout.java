package creativeendlessgrowingceg.allergychecker;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.content.ContentValues.TAG;

/**
 *
 * @author Jonathan Alexander Norberg
 * @version 2018-01-13
 */

public class CreateLinearLayout {
    private static final CreateLinearLayout ourInstance = new CreateLinearLayout();
    public static CreateLinearLayout getInstance() {
        return ourInstance;
    }

    private CreateLinearLayout() {
    }

    /**
     * switch view to 180 degrees or 0.
     * @param v to switch
     * @param linearLayoutArrayList to be removed
     * @param parentLinearLayout to removed views from
     */
    static void onclickDropDownList(View v, LinkedHashMap<Integer, LinearLayout> linearLayoutArrayList, LinearLayout parentLinearLayout) {
        if (v.getRotation() == 180) {
            v.setRotation(0);
            for (LinearLayout linearLayout : linearLayoutArrayList.values()) {
                parentLinearLayout.removeView(linearLayout);
            }
        } else {
            v.setRotation(180);
            for (LinearLayout linearLayout : linearLayoutArrayList.values()) {
                parentLinearLayout.addView(linearLayout);
            }
        }
    }

    /**
     * set onclick listener on parent.
     * @param allergyInfo to setClickListener on.
     */
    static void parentCheckedChanged(final AllergyCheckBoxClass allergyInfo){
        allergyInfo.getParentCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.getNeighbourClasses()) {

                    for (AllergyCheckBoxClass allergyCheckBoxClass2: allergyCheckBoxClass.getSameItemDifferentCategories()) {
                        allergyCheckBoxClass2.getChildCheckBox().setChecked(isChecked);

                        setOnRemove(allergyCheckBoxClass2, isChecked);
                    }

                }

            }
        });
    }

    /**
     * check if parent should be checked or not
     *
     * @param allergyInfo to get parent
     * @param preference if its preference calling it
     */
    public static void checkParentShouldChecked(final AllergyCheckBoxClass allergyInfo, boolean preference) {
        allergyInfo.getParentCheckBox().setOnCheckedChangeListener(null);
        if(!preference){
            for (AllergyCheckBoxClass allergyCheckBoxClass: allergyInfo.getNeighbourClasses()) {
                if (allergyCheckBoxClass.getChildCheckBox().isChecked()){
                    allergyInfo.getParentCheckBox().setChecked(true);
                    setOnRemove(allergyCheckBoxClass,true);
                    parentCheckedChanged(allergyInfo);
                    return;
                }
            }
            allergyInfo.getParentCheckBox().setChecked(false);
            parentCheckedChanged(allergyInfo);
        }else {
            for (AllergyCheckBoxClass allergyCheckBoxClass: allergyInfo.getNeighbourClasses()) {
                if (!allergyCheckBoxClass.getChildCheckBox().isChecked()){


                    allergyInfo.getParentCheckBox().setChecked(false);
                    setOnRemove(allergyCheckBoxClass,false);
                    parentCheckedChanged(allergyInfo);
                    return;

                }
            }
            allergyInfo.getParentCheckBox().setChecked(true);
            parentCheckedChanged(allergyInfo);


        }
    }

    /**
     * check if parent and child equal
     * @param parentKey to check
     * @param childKey to check
     * @param checkBoxes to set true if equal
     */
    public static void checkIfParentAndChildEqual(int parentKey, int childKey, AllergyCheckBoxClass checkBoxes) {
        if(parentKey == childKey){
            if(checkBoxes.getChildCheckBox().isChecked()){
                for (AllergyCheckBoxClass box : checkBoxes.getNeighbourClasses()) {
                    Log.d(TAG, "checkIfParentAndChildEqual: equal");
                    box.getChildCheckBox().setChecked(true);
                    setOnRemove(box,true);
                }
            }
        }
    }

    /**
     * set isChecked same item from different categorise
     * @param sameItemDifferentCategories to check
     * @param isChecked true or false
     */
    public static void sameItemDifferentCategories(ArrayList<AllergyCheckBoxClass> sameItemDifferentCategories, boolean isChecked){
        for (AllergyCheckBoxClass sameItemDifferentCategory : sameItemDifferentCategories) {
            sameItemDifferentCategory.getChildCheckBox().setChecked(isChecked);
        }
    }

    /**
     * set listener on child
     * @param allergyInfo to be used.
     */
    public static void checkBoxChildOnCheckedListener(final AllergyCheckBoxClass allergyInfo) {

        allergyInfo.getChildCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                allergyInfo.getChildCheckBox().setChecked(isChecked);
                if(isChecked){
                    allergyInfo.setOn(true);
                    //checkIfParentAndChildEqual(allergyInfo.getParentKey(),allergyInfo.getChildKey(),allergyInfo);
                }else{
                    allergyInfo.setOn(false);
                    allergyInfo.setRemove(true);
                }
                checkParentShouldChecked(allergyInfo, false);
                sameItemDifferentCategories(allergyInfo.getSameItemDifferentCategories(),isChecked);
            }
        });

    }

    /**
     * set listener on preference
     * @param allergyInfo
     */
    public static void checkBoxChildOnCheckedListenerPreference(final AllergyCheckBoxClass allergyInfo) {
        allergyInfo.getChildCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                allergyInfo.getChildCheckBox().setChecked(isChecked);
                if(isChecked){
                    allergyInfo.setOn(true);
                }else{
                    allergyInfo.setOn(false);
                    allergyInfo.setRemove(true);
                }
                checkParentShouldChecked(allergyInfo,true);
                sameItemDifferentCategories(allergyInfo.getSameItemDifferentCategories(),isChecked);
            }
        });
    }

    /**
     * set if it should remove or be on for later use when saving
     * @param allergyInfo to set
     * @param isChecked true or false
     */

    public static void setOnRemove( AllergyCheckBoxClass allergyInfo,boolean isChecked){
        if(isChecked){
            allergyInfo.setOn(true);
        }else{
            allergyInfo.setOn(false);
            allergyInfo.setRemove(true);

        }
    }


}
