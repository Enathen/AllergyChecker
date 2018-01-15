package creativeendlessgrowingceg.allergychecker;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Enathen on 2018-01-14.
 */

public class AllergyCheckBoxClass {

    private int parentPicture;
    private int parentKey;

    private int childKey;
    private CheckBox parentCheckBox;

    private CheckBox childCheckBox;
    private String childIngredient;
    private ArrayList<AllergyCheckBoxClass> neigbhourClasses = new ArrayList<>();
    private ArrayList<AllergyCheckBoxClass> sameItemDifferentCategories = new ArrayList<>();
    private boolean on = false;
    private boolean remove = false;

    public AllergyCheckBoxClass(CheckBox checkBox, CheckBox parentCheckBox, int parentPicture, int childKey, int parentKey, String childIngredient) {
        this.childCheckBox = checkBox;

        this.parentCheckBox = parentCheckBox;
        this.parentPicture = parentPicture;
        this.childKey = childKey;
        this.parentKey = parentKey;
        this.childIngredient = childIngredient;
    }

    public AllergyCheckBoxClass(CheckBox checkBoxOfAllergy, CheckBox parentCheckBox, int parentKeyToPicture, int id, int parentKey, String ingredient, ArrayList<AllergyCheckBoxClass> sameItemDifferentCategories) {

        this.childCheckBox = checkBoxOfAllergy;
        this.parentCheckBox = parentCheckBox;
        this.parentPicture = parentKeyToPicture;
        this.childKey = id;
        this.parentKey = parentKey;
        this.childIngredient = ingredient;
        this.sameItemDifferentCategories = sameItemDifferentCategories;
    }

    public ArrayList<AllergyCheckBoxClass> getSameItemDifferentCategories() {
        return sameItemDifferentCategories;
    }

    public void setSameItemDifferentCategories(ArrayList<AllergyCheckBoxClass> sameItemDifferentCategories) {
        this.sameItemDifferentCategories = sameItemDifferentCategories;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public void setParentCheckBox(CheckBox parentCheckBox) {
        this.parentCheckBox = parentCheckBox;
    }

    public void setChildCheckBox(CheckBox childCheckBox) {
        this.childCheckBox = childCheckBox;
    }

    public void setChildIngredient(String childIngredient) {
        this.childIngredient = childIngredient;
    }

    public void setParentPicture(int parentPicture) {
        this.parentPicture = parentPicture;
    }

    public void setParentKey(int parentKey) {
        this.parentKey = parentKey;
    }

    public void setChildKey(int childKey) {
        this.childKey = childKey;
    }

    public int getParentPicture() {
        return parentPicture;
    }

    public int getParentKey() {
        return parentKey;
    }

    public CheckBox getParentCheckBox() {
        return parentCheckBox;
    }

    public int getChildKey() {
        return childKey;
    }

    public CheckBox getChildCheckBox() {
        return childCheckBox;
    }

    public String getChildIngredient() {
        return childIngredient;
    }
    public boolean childEqualParent(){
        return parentKey == childKey;
    }


    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public ArrayList<AllergyCheckBoxClass> getNeigbhourClasses() {
        return neigbhourClasses;
    }

    public void setNeigbhourClasses(ArrayList<AllergyCheckBoxClass> neigbhourClasses) {
        this.neigbhourClasses = neigbhourClasses;
    }
}
