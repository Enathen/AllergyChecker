package creativeendlessgrowingceg.allergychecker;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Enathen on 2017-10-13.
 */

public class AllergyList {
    private static final String TAG = "ArrayListAllergy";
    private ArrayList<String> arrayListIngredients = new ArrayList<>();
    private ArrayList<ImageView> arrayListPictures = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListNuts = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListGluten = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListFish = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListShellfish = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListSeeds= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListFruit= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListVegetables= new ArrayList<>();

    public AllergyList(){
        setArrayListNuts();
        setArrayListGluten();
        setArrayListFish();
        setArrayListShellfish();
        setArrayListSeeds();
        setArrayListFruit();
        //setArrayListVegetables();
    }

    public ArrayList<PictureIngredient> getArrayListSeeds() {
        return arrayListSeeds;
    }

    public void setArrayListSeeds() {
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Sesame"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Benne"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Gingelly"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Gomasio"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Halvah"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Sim"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Tahini"));
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,"Til"));
        sort(arrayListSeeds);
    }

    public ArrayList<PictureIngredient> getArrayListFish() {
        return arrayListFish;
    }

    public void setArrayListFish() {
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Pike"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Tuna"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Salmon"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Caviar"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Anchovies"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Bass"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Catfish"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Cod"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Flounder"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Grouper"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Haddock"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Hake"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Halibut"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Herring"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Mahi"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Perch"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Pollock"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Pike"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Swordfish"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Sole"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Snapper"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Surimi"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Tilapia"));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,"Trout"));
        sort(arrayListFish);
    }

    public ArrayList<PictureIngredient> getArrayListShellfish() {
        return arrayListShellfish;
    }

    public void setArrayListShellfish() {
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Barnacle"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Crab"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Crawfish"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Crayfish"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Krill"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Lobster"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Prawns"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Shrimp"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"scampi"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Abalone"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Clams"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Cockle"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Limpet"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Mussels"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Octopus"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Oysters"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Periwinkle"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Urchin"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Scallops"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Snails"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Squid"));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,"Whelk"));
        sort(arrayListShellfish);
    }

    public ArrayList<String> getArrayListIngredients() {
        return arrayListIngredients;
    }

    public ArrayList<ImageView> getArrayListPictures() {
        return arrayListPictures;
    }

    public ArrayList<PictureIngredient> getArrayListNuts() {
        return arrayListNuts;
    }

    public ArrayList<PictureIngredient> getArrayListGluten() {
        return arrayListGluten;
    }

    public void setArrayListNuts() {
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Almond"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Brazil"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Cocao"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Hazelnut"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Macadamia"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Peanuts"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Pecans"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Pili"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Pine"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Pistachios"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Pistachios"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Soy"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Tiger"));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,"Walnuts"));
        sort(arrayListNuts);
    }

    public void setArrayListGluten() {
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Wheat"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Durum"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Emmer"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Rye"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Barley"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Triticale"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Malt"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Semolina"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Spelt"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Farina"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Farro"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Graham"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"KAMUT"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Einkorn"));
        sort(arrayListGluten);
    }
    public void setArrayListFruit() {
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Apple"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Pear"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Tomato"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Strawberry"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Prune"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Pomegranate"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Pinapple"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Persimmon"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Peach"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Orange"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Melon"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Mango"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Lychee"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Kiwi"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Grape"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Fig"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Date"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Coconut"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Cherry"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Banana"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Apricot"));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,"Acerola"));
        sort(arrayListFruit);
    }
/*    public void setArrayListVegetables() {
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Wheat"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Durum"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Emmer"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Rye"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Barley"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Triticale"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Malt"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Semolina"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Spelt"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Farina"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Farro"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Graham"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"KAMUT"));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,"Einkorn"));
        sort(arrayListGluten);
    }*/

    public void setArrayListPictures(String arrayListPictures) {
        this.arrayListIngredients.add(arrayListPictures);
    }
    private void sort(ArrayList<PictureIngredient> arrayList){
        Collections.sort(arrayList, new Comparator<PictureIngredient>() {
            @Override
            public int compare(PictureIngredient pic1, PictureIngredient pic2) {
                return pic2.ingredient.compareToIgnoreCase(pic1.ingredient);
            }
        });
    }
    public class PictureIngredient{
        int picture;
        String ingredient;

        PictureIngredient(int picture, String ingredient){
            this.picture = picture;

            this.ingredient = ingredient;
        }
    }
}
