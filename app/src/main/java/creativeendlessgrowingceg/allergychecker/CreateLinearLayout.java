package creativeendlessgrowingceg.allergychecker;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
    static void onclickDropDownList(View v, HashSet<LinearLayout> linearLayoutArrayList, LinearLayout parentLinearLayout) {
        if (v.getRotation() == 180) {
            v.setRotation(0);
            for (LinearLayout linearLayout : linearLayoutArrayList) {
                parentLinearLayout.removeView(linearLayout);
            }
        } else {
            v.setRotation(180);
            for (LinearLayout linearLayout : linearLayoutArrayList) {
                parentLinearLayout.addView(linearLayout);
            }
        }

    }

    static void parentCheckedChanged(final ArrayList<CheckBox> checkBoxHashMap, final CheckBox checkboxRowCategory){
        checkboxRowCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (CheckBox checkBox : checkBoxHashMap) {
                    checkBox.setChecked(isChecked);

                }
            }
        });
    }
    public static void checkParentShouldChecked(ArrayList<CheckBox> checkBoxHashMap, CheckBox parentCheckBox) {
        parentCheckBox.setOnCheckedChangeListener(null);
        for (CheckBox checkBox : checkBoxHashMap) {
            if (checkBox.isChecked()){
                parentCheckBox.setChecked(true);
                parentCheckedChanged(checkBoxHashMap,parentCheckBox);
                return;

            }
        }
        parentCheckBox.setChecked(false);
        parentCheckedChanged(checkBoxHashMap,parentCheckBox);
    }
    public static void sameItemDifferentCategories(HashMap<Integer, ArrayList<CheckBox>> sameItemDifferentCategories, int id, CheckBox checkBoxOfAllergy){
        if(sameItemDifferentCategories.containsKey(id)){
            sameItemDifferentCategories.get(id).add(checkBoxOfAllergy);
        }else {
            ArrayList<CheckBox> arrayListCheckBoxes = new ArrayList<>();
            arrayListCheckBoxes.add(checkBoxOfAllergy);
            sameItemDifferentCategories.put(id,arrayListCheckBoxes);
        }
    }
}
