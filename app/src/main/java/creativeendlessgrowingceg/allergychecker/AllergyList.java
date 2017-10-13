package creativeendlessgrowingceg.allergychecker;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Enathen on 2017-10-13.
 */

public class AllergyList {
    ArrayList<String> arrayListIngredients = new ArrayList<>();
    ArrayList<ImageView> arrayListPictures = new ArrayList<>();
    public AllergyList(){

    }

    public ArrayList<String> getArrayListIngredients() {
        return arrayListIngredients;
    }

    public ArrayList<ImageView> getArrayListPictures() {
        return arrayListPictures;
    }

    public void setArrayListIngredients(String arrayListIngredients) {
        this.arrayListIngredients.add(arrayListIngredients);
    }

    public void setArrayListPictures(String arrayListPictures) {
        this.arrayListIngredients.add(arrayListPictures);
    }
}
