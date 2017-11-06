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
    private ArrayList<PictureIngredient> arrayListOvoVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListLactoOvoVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListLactoVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListDemiVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListPolloVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListPescoVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListVegetarian= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListVegan= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListCitrus= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListMuslim= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListKosher= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListLegumes= new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListSpice= new ArrayList<>();
    Context context;

    public AllergyList(Context context){
        this.context = context;
        setArrayListNuts();
        setArrayListGluten();
        setArrayListFish();
        setArrayListShellfish();
        setArrayListSeeds();
        setArrayListFruit();
        setArrayListVegetables();
        setArrayListCitrus();
        setArrayListLegumes();
        setArrayListMuslim();
        setArrayListKosher();
        setArrayListVegan();
        setArrayListVegetarian();
        setArrayListSpice();
        setArrayListDemiVegetarian();
        setArrayListLactoOvoVegetarian();
        setArrayListOvoVegetarian();
        setArrayListLactoVegetarian();
        setArrayListPolloVegetarian();
        setArrayListPescoVegetarian();
    }

    private void setArrayListVegetables() {
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.tomato));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.aspargus));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.avocado));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.bellPepper));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.cabbage));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.carrot));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.celery));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.garlic));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.lettuce));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.maize));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.potato));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.pumpkin));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato,R.string.zuchini));
        sort(arrayListVegetables);
    }
    private void setArrayListVegetarian() {
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.fish));
        sort(arrayListVegetarian);
    }
    private void setArrayListLactoOvoVegetarian() {
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.fish));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.diary));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.egg));
        sort(arrayListLactoOvoVegetarian);
    }
    private void setArrayListOvoVegetarian() {
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));

        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.fish));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.diary));
        sort(arrayListLactoOvoVegetarian);
    }
    private void setArrayListDemiVegetarian() {
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));

        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        sort(arrayListDemiVegetarian);
    }
    private void setArrayListLactoVegetarian() {
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));

        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.fish));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.egg));
        sort(arrayListLactoVegetarian);
    }
    private void setArrayListPolloVegetarian() {
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));

        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.fish));
        sort(arrayListPolloVegetarian);
    }

    private void setArrayListPescoVegetarian() {
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.meat));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.vegan,R.string.chicken));
        sort(arrayListPescoVegetarian);
    }

    private void setArrayListVegan() {
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.pork));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.duck));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.meat));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.goat));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.poultry));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.lamb));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.sausage));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.beef));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.veal));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.gelatin));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.shellfish));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.fish));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.diary));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.egg));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan,R.string.honey));
        sort(arrayListVegan);
    }
    private void setArrayListCitrus() {
        arrayListCitrus.add(new PictureIngredient(R.drawable.tomato,R.string.citrus));
        sort(arrayListCitrus);
    }
    private void setArrayListMuslim() {
        arrayListMuslim.add(new PictureIngredient(R.drawable.tomato,R.string.tomato));
        sort(arrayListMuslim);
    }
    private void setArrayListKosher() {
        arrayListKosher.add(new PictureIngredient(R.drawable.tomato,R.string.tomato));
        sort(arrayListKosher);
    }
    private void setArrayListLegumes() {
        arrayListLegumes.add(new PictureIngredient(R.drawable.tomato,R.string.legumes));
        sort(arrayListLegumes);
    }
    private void setArrayListSpice() {
        arrayListSpice.add(new PictureIngredient(R.drawable.tomato,R.string.legumes));
        sort(arrayListSpice);
    }

    public ArrayList<PictureIngredient> getArrayListOvoVegetarian() {
        return arrayListOvoVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListLactoOvoVegetarian() {
        return arrayListLactoOvoVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListLactoVegetarian() {
        return arrayListLactoVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListDemiVegetarian() {
        return arrayListDemiVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListPolloVegetarian() {
        return arrayListPolloVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListPescoVegetarian() {
        return arrayListPescoVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListKosher() {
        return arrayListKosher;
    }

    public ArrayList<PictureIngredient> getArrayListVegetarian() {
        return arrayListVegetarian;
    }

    public ArrayList<PictureIngredient> getArrayListVegan() {
        return arrayListVegan;
    }

    public ArrayList<PictureIngredient> getArrayListCitrus() {
        return arrayListCitrus;
    }

    public ArrayList<PictureIngredient> getArrayListMuslim() {
        return arrayListMuslim;
    }

    public ArrayList<PictureIngredient> getArrayListLegumes() {
        return arrayListLegumes;
    }
    public ArrayList<PictureIngredient> getArrayListSpice() {
        return arrayListSpice;
    }


    public ArrayList<PictureIngredient> getArrayListSeeds() {
        return arrayListSeeds;
    }

    public void setArrayListSeeds() {
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds,R.string.sesame));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds,R.string.buckwheat));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds,R.string.mustardSeed));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds,R.string.poppySeed));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds,R.string.pumpkinSeed));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds,R.string.sunflowerSeed));
        sort(arrayListSeeds);
    }

    public ArrayList<PictureIngredient> getArrayListFish() {
        return arrayListFish;
    }

    public void setArrayListFish() {
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.pike));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.tuna));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.salmon));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.caviar));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.anchovies));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.bass));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.catfish));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.cod));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.flounder));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.haddock));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.hake));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.halibut));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.herring));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.mahi));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.perch));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.pollock));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.swordfish));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.sole));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.snapper));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.surimi));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.tilapia));
        arrayListFish.add(new PictureIngredient(R.drawable.fish,R.string.trout));

        sort(arrayListFish);
    }

    public ArrayList<PictureIngredient> getArrayListShellfish() {
        return arrayListShellfish;
    }

    public void setArrayListShellfish() {
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.barnacle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.crab));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.crawfish));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.krill));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.lobster));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.prawns));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.shrimp));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.scampi));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.abalone));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.clams));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.cockle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.limpet));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.mussels));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.octopus));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.oysters));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.periwinkle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.urchin));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.scallops));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.snails));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.squid));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish,R.string.whelk));

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
    public ArrayList<PictureIngredient> getArrayListFruit(){
        return arrayListFruit;
    }
    public ArrayList<PictureIngredient> getArrayListVegetables(){
        return arrayListVegetables;
    }

    public void setArrayListNuts() {
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.almond));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.brazil));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.cocao));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.hazelnut));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.macadamia));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.peanuts));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.pecans));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.pili));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.pine));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.pistachios));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.soy));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.tiger));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts,R.string.walnuts));


        sort(arrayListNuts);
    }

    public void setArrayListGluten() {

        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.durum));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.emmer));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.rye));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.barley));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.triticale));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.malt));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.semolina));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.spelt));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.farina));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.farro));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.graham));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.kamut));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.einkorn));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat,R.string.wheat));

        sort(arrayListGluten);
    }
    public void setArrayListFruit() {

        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.apple));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.pear));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.tomato));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.strawberry));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.prune));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.pomegranate));
        arrayListFruit.add(new PictureIngredient(R.drawable.pinapple,R.string.pinapple));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.persimmon));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach,R.string.peach));
        arrayListFruit.add(new PictureIngredient(R.drawable.orange,R.string.orange));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.melon));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.mango));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.lychee));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.kiwi));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.grape));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.fig));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.date));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.coconut));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.cherry));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.banana));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.apricot));
        arrayListFruit.add(new PictureIngredient(R.drawable.fruit,R.string.acerola));

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
        boolean notAllergy;
        PictureIngredient(int picture, int ingredient){
            this.picture = picture;
            this.id = ingredient;
            this.ingredient = context.getString(ingredient);
        }
        PictureIngredient(int picture, int ingredient,boolean notAllergy){
            this.picture = picture;
            this.id = ingredient;
            this.ingredient = context.getString(ingredient);
            this.notAllergy = notAllergy;
        }
    }
}
