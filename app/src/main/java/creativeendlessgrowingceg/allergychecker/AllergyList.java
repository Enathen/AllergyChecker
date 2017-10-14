package creativeendlessgrowingceg.allergychecker;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Enathen on 2017-10-13.
 */

public class AllergyList {
    private static final String TAG = "ArrayListAllergy";
    private ArrayList<String> arrayListIngredients = new ArrayList<>();
    private ArrayList<ImageView> arrayListPictures = new ArrayList<>();
    private ArrayList<String> arrayListNuts = new ArrayList<>();
    private ArrayList<String> arrayListGluten = new ArrayList<>();
    public AllergyList(){
        setArrayListIngredients();
    }

    public ArrayList<String> getArrayListIngredients() {
        return arrayListIngredients;
    }

    public ArrayList<ImageView> getArrayListPictures() {
        return arrayListPictures;
    }

    public ArrayList<String> getArrayListNuts() {
        return arrayListNuts;
    }

    public ArrayList<String> getArrayListGluten() {
        return arrayListGluten;
    }

    public void setArrayListIngredients() {
        arrayListNuts.add("Almond");
        arrayListNuts.add("Brazil nuts");
        arrayListNuts.add("Cacao");
        arrayListNuts.add("Hazelnut");
        arrayListNuts.add("Mmacadamia nuts");
        arrayListNuts.add("Peanuts");
        arrayListNuts.add("Pecans");
        arrayListNuts.add("Pili nuts");
        arrayListNuts.add("Pine nuts");
        arrayListNuts.add("Pistachios");
        arrayListNuts.add("Pistachios");
        arrayListNuts.add("Soy nuts");
        arrayListNuts.add("Tiger nuts");
        arrayListNuts.add("Walnuts");

    }

    public void setArrayListPictures(String arrayListPictures) {
        this.arrayListIngredients.add(arrayListPictures);
    }
}
