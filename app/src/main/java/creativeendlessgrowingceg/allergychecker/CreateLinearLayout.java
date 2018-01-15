package creativeendlessgrowingceg.allergychecker;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.TreeMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Enathen on 2018-01-13.
 */

public class CreateLinearLayout {
    private static final CreateLinearLayout ourInstance = new CreateLinearLayout();
    public static CreateLinearLayout getInstance() {
        return ourInstance;
    }

    private CreateLinearLayout() {
    }
    static void onclickDropDownList(View v, TreeMap<Integer, LinearLayout> linearLayoutArrayList, LinearLayout parentLinearLayout) {
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

    static void parentCheckedChanged(final AllergyCheckBoxClass allergyInfo){
        allergyInfo.getParentCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (AllergyCheckBoxClass allergyCheckBoxClass : allergyInfo.getNeigbhourClasses()) {

                    for (AllergyCheckBoxClass allergyCheckBoxClass2: allergyCheckBoxClass.getSameItemDifferentCategories()) {
                        allergyCheckBoxClass2.getChildCheckBox().setChecked(isChecked);

                        setOnRemove(allergyCheckBoxClass2, isChecked);

                    }

                }

            }
        });
    }
    public static void checkParentShouldChecked(final AllergyCheckBoxClass allergyInfo) {
        allergyInfo.getParentCheckBox().setOnCheckedChangeListener(null);
        for (AllergyCheckBoxClass allergyCheckBoxClass: allergyInfo.getNeigbhourClasses()) {
            if (allergyCheckBoxClass.getChildCheckBox().isChecked()){
                allergyInfo.getParentCheckBox().setChecked(true);
                setOnRemove(allergyCheckBoxClass,true);
                parentCheckedChanged(allergyInfo);
                return;

            }
        }
        allergyInfo.getParentCheckBox().setChecked(false);
        parentCheckedChanged(allergyInfo);
    }

    public static void checkIfParentAndChildEqual(int parentKey, int childKey, AllergyCheckBoxClass checkBoxes) {
        if(parentKey == childKey){
            if(checkBoxes.getChildCheckBox().isChecked()){
                for (AllergyCheckBoxClass box : checkBoxes.getNeigbhourClasses()) {
                    Log.d(TAG, "checkIfParentAndChildEqual: equal");
                    box.getChildCheckBox().setChecked(true);
                    setOnRemove(box,true);
                }
            }
        }
    }
    public static void sameItemDifferentCategories(ArrayList<AllergyCheckBoxClass> sameItemDifferentCategories, boolean isChecked){
        for (AllergyCheckBoxClass sameItemDifferentCategory : sameItemDifferentCategories) {
            sameItemDifferentCategory.getChildCheckBox().setChecked(isChecked);
        }
    }
    public static void checkBoxChildOnCheckedListener(final AllergyCheckBoxClass allergyInfo) {

        allergyInfo.getChildCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                allergyInfo.getChildCheckBox().setChecked(isChecked);
                if(isChecked){
                    allergyInfo.setOn(true);
                    checkIfParentAndChildEqual(allergyInfo.getParentKey(),allergyInfo.getChildKey(),allergyInfo);
                }else{
                    allergyInfo.setOn(false);
                    allergyInfo.setRemove(true);
                }
                checkParentShouldChecked(allergyInfo);
                sameItemDifferentCategories(allergyInfo.getSameItemDifferentCategories(),isChecked);
            }
        });

    }
    public static void setOnRemove( AllergyCheckBoxClass allergyInfo,boolean isChecked){
        if(isChecked){
            allergyInfo.setOn(true);
        }else{
            allergyInfo.setOn(false);
            allergyInfo.setRemove(true);

        }
    }



}