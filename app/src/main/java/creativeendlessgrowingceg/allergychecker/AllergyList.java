package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
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
    private int addInt= 0;
    Context context;

    public AllergyList(Context context){
        this.context = context;
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
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.sesame),addInt++));

        sort(arrayListSeeds);
    }

    public ArrayList<PictureIngredient> getArrayListFish() {
        return arrayListFish;
    }

    public void setArrayListFish() {
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.pike),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.tuna),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.salmon),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.caviar),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.anchovies),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.bass),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.catfish),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.cod),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.flounder),addInt++));

        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.haddock),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.hake),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.halibut),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.herring),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.mahi),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.perch),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.pollock),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.pike),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.swordfish),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.sole),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.snapper),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.surimi),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.tilapia),addInt++));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.trout),addInt++));
        addInt += 1000;
        sort(arrayListFish);
    }

    public ArrayList<PictureIngredient> getArrayListShellfish() {
        return arrayListShellfish;
    }

    public void setArrayListShellfish() {
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.barnacle),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.crab),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.crawfish),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.krill),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.lobster),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.prawns),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.shrimp),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.scampi),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.abalone),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.clams),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.cockle),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.limpet),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.mussels),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.octopus),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.oysters),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.periwinkle),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.urchin),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.scallops),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.snails),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.squid),addInt++));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.whelk),addInt++));
        addInt += 1000;
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
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.almond),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.brazil),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.cocao),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.hazelnut),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.macadamia),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.peanuts),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pecans),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pili),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pine),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pistachios),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.soy),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.tiger),addInt++));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.walnuts),addInt++));
        addInt += 1000;

        sort(arrayListNuts);
    }

    public void setArrayListGluten() {

        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.durum),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.emmer),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.rye),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.barley),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.triticale),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.malt),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.semolina),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.spelt),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.farina),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.farro),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.graham),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.kamut),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.einkorn),addInt++));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.wheat),addInt++));
        addInt += 1000;
        sort(arrayListGluten);
    }
    public void setArrayListFruit() {
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.apple),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.pear),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.tomato),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.strawberry),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.prune),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.pomegranate),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.pinapple),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.persimmon),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.peach),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.orange),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.melon),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.mango),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.lychee),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.kiwi),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.grape),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.fig),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.date),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.coconut),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.cherry),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.banana),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.apricot),addInt++));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.acerola),addInt++));
        addInt += 1000;
        sort(arrayListFruit);
    }


    public void setArrayListPictures(String arrayListPictures) {
        this.arrayListIngredients.add(arrayListPictures);
    }
    private void sort(ArrayList<PictureIngredient> arrayList){
        Collections.sort(arrayList, new Comparator<PictureIngredient>() {
            @Override
            public int compare(PictureIngredient pic1, PictureIngredient pic2) {
                return pic1.ingredient.compareToIgnoreCase(pic2.ingredient);
            }
        });
    }
    public class PictureIngredient{
        int picture;
        String ingredient;
        int number;
        PictureIngredient(int picture, String ingredient,int number){
            this.picture = picture;
            this.number = number;
            this.ingredient = ingredient;
        }
    }
}
