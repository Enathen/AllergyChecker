package creativeendlessgrowingceg.allergychecker;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * class to help checkbox in my allergies and my preference
 * since you must know if same category or if you have same
 * allergy in another category.
 *
 * @author Jonathan Alexander Norberg
 * @version 2018-01-14
 */

public class AllergyCheckBoxClass {

    private int parentPicture;
    private int parentKey;

    private int childKey;
    private CheckBox parentCheckBox;

    private CheckBox childCheckBox;
    private String childIngredient;
    private ArrayList<AllergyCheckBoxClass> neighbourClasses = new ArrayList<>();
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


    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
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


    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public ArrayList<AllergyCheckBoxClass> getNeighbourClasses() {
        return neighbourClasses;
    }

    public void setNeighbourClasses(ArrayList<AllergyCheckBoxClass> neighbourClasses) {
        this.neighbourClasses = neighbourClasses;
    }
}
