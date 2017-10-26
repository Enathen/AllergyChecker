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
        arrayListSeeds.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.sesame),R.string.sesame));

        sort(arrayListSeeds);
    }

    public ArrayList<PictureIngredient> getArrayListFish() {
        return arrayListFish;
    }

    public void setArrayListFish() {
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.pike),R.string.pike));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.tuna),R.string.tuna));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.salmon),R.string.salmon));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.caviar),R.string.caviar));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.anchovies),R.string.anchovies));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.bass),R.string.bass));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.catfish),R.string.catfish));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.cod),R.string.cod));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.flounder),R.string.flounder));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.haddock),R.string.haddock));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.hake),R.string.hake));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.halibut),R.string.halibut));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.herring),R.string.herring));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.mahi),R.string.mahi));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.perch),R.string.perch));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.pollock),R.string.pollock));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.pike),R.string.pike));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.swordfish),R.string.swordfish));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.sole),R.string.sole));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.snapper),R.string.snapper));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.surimi),R.string.surimi));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.tilapia),R.string.tilapia));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.trout),R.string.trout));

        sort(arrayListFish);
    }

    public ArrayList<PictureIngredient> getArrayListShellfish() {
        return arrayListShellfish;
    }

    public void setArrayListShellfish() {
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.barnacle),R.string.barnacle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.crab),R.string.crab));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.crawfish),R.string.crawfish));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.krill),R.string.krill));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.lobster),R.string.lobster));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.prawns),R.string.prawns));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.shrimp),R.string.shrimp));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.scampi),R.string.scampi));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.abalone),R.string.abalone));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.clams),R.string.clams));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.cockle),R.string.cockle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.limpet),R.string.limpet));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.mussels),R.string.mussels));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.octopus),R.string.octopus));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.oysters),R.string.oysters));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.periwinkle),R.string.periwinkle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.urchin),R.string.urchin));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.scallops),R.string.scallops));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.snails),R.string.snails));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.squid),R.string.squid));
        arrayListShellfish.add(new PictureIngredient(R.drawable.fish,context.getString(R.string.whelk),R.string.whelk));

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
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.almond),R.string.almond));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.brazil),R.string.brazil));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.cocao),R.string.cocao));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.hazelnut),R.string.hazelnut));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.macadamia),R.string.macadamia));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.peanuts),R.string.peanuts));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pecans),R.string.pecans));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pili),R.string.pili));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pine),R.string.pine));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.pistachios),R.string.pistachios));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.soy),R.string.soy));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.tiger),R.string.tiger));
        arrayListNuts.add(new PictureIngredient(R.drawable.peanut,context.getString(R.string.walnuts),R.string.walnuts));


        sort(arrayListNuts);
    }

    public void setArrayListGluten() {

        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.durum),R.string.durum));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.emmer),R.string.emmer));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.rye),R.string.rye));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.barley),R.string.barley));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.triticale),R.string.triticale));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.malt),R.string.malt));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.semolina),R.string.semolina));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.spelt),R.string.spelt));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.farina),R.string.farina));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.farro),R.string.farro));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.graham),R.string.graham));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.kamut),R.string.kamut));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.einkorn),R.string.einkorn));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.wheat),R.string.wheat));

        sort(arrayListGluten);
    }
    public void setArrayListFruit() {

        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.apple),R.string.apple));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.pear),R.string.pear));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.tomato),R.string.tomato));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.strawberry),R.string.strawberry));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.prune),R.string.prune));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.pomegranate),R.string.pomegranate));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.pinapple),R.string.pinapple));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.persimmon),R.string.persimmon));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.peach),R.string.peach));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.orange),R.string.orange));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.melon),R.string.melon));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.mango),R.string.mango));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.lychee),R.string.lychee));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.kiwi),R.string.kiwi));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.grape),R.string.grape));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.fig),R.string.fig));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.date),R.string.date));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.coconut),R.string.coconut));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.cherry),R.string.cherry));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.banana),R.string.banana));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.apricot),R.string.apricot));
        arrayListFruit.add(new PictureIngredient(R.drawable.wheat,context.getString(R.string.acerola),R.string.acerola));

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
        int id;
        PictureIngredient(int picture, String ingredient,int id){
            this.picture = picture;
            this.id = id;
            this.ingredient = ingredient;
        }
    }
}
