package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by Enathen on 2017-10-13.
 */

public class AllergyList {
    private static final String TAG = "ArrayListAllergy";
    Context context;
    private ArrayList<String> arrayListIngredients = new ArrayList<>();
    private ArrayList<ImageView> arrayListPictures = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListNuts = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListGluten = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListFish = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListShellfish = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListSeeds = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListFruit = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListVegetables = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListOvoVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListLactoOvoVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListLactoVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListDemiVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListPolloVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListPescoVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListVegetarian = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListVegan = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListCitrus = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListMuslim = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListKosher = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListLegumes = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListSpice = new ArrayList<>();
    private ArrayList<PictureIngredient> arrayListDairy = new ArrayList<>();

    public AllergyList(Context context) {
        this.context = context;
    }
    public static ArrayList<Integer> getParentKeys(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(R.string.citrus);
        arrayList.add(R.string.dairy);
        arrayList.add(R.string.spice);
        arrayList.add(R.string.legumes);
        arrayList.add(R.string.nuts);
        arrayList.add(R.string.fruit);
        arrayList.add(R.string.seeds);
        arrayList.add(R.string.shellfish);
        arrayList.add(R.string.fish);
        arrayList.add(R.string.gluten);
        return arrayList;
    }
    public ArrayList<PictureIngredient> getSpecifiedKey(int key){
        if(key == R.string.citrus){
            setArrayListCitrus();
            return getArrayListCitrus();
        }
        if(key == R.string.fish){
            setArrayListFish();
            return getArrayListFish();
        }
        if(key == R.string.shellfish){
            setArrayListShellfish();
            return getArrayListShellfish();
        }
        if(key == R.string.fruit){
            setArrayListFruit();
            return getArrayListFruit();
        }
        if(key == R.string.seeds){
            setArrayListSeeds();
            return getArrayListSeeds();
        }
        if(key == R.string.gluten){
            setArrayListGluten();
            return getArrayListGluten();
        }
        if(key == R.string.nuts){
            setArrayListNuts();
            return getArrayListNuts();
        }
        if(key == R.string.spice){
            setArrayListSpice();
            return getArrayListSpice();
        }
        if(key == R.string.dairy){
            setArrayListDairy();
            return getArrayListDairy();
        }
        if(key == R.string.legumes){
            setArrayListLegumes();
            return getArrayListLegumes();
        }
        return null;
    }

    public TreeMap<Integer,ArrayList<AllergyList.PictureIngredient>> getMyAllergies() {
        TreeMap<Integer,ArrayList<PictureIngredient>> arrayLists = new TreeMap<>();
        arrayLists.put(setArrayListCitrus(),getArrayListCitrus());
        arrayLists.put(setArrayListDairy(),getArrayListDairy());
        arrayLists.put(setArrayListFish(),getArrayListFish());
        arrayLists.put(setArrayListFruit(),getArrayListFruit());
        arrayLists.put(setArrayListGluten(),getArrayListGluten());
        arrayLists.put(setArrayListLegumes(),getArrayListLegumes());
        arrayLists.put(setArrayListNuts(),getArrayListNuts());
        arrayLists.put(setArrayListSeeds(),getArrayListSeeds());
        arrayLists.put(setArrayListShellfish(),getArrayListShellfish());
        arrayLists.put(setArrayListSpice(),getArrayListSpice());
        arrayLists.put(setArrayListVegetables(),getArrayListVegetables());
        for (ArrayList<PictureIngredient> pictureIngredients : arrayLists.values()) {
            sort(pictureIngredients);
        }
        return arrayLists;
    }


    public TreeMap<Integer,ArrayList<AllergyList.PictureIngredient>> getMyPreference(){

        TreeMap<Integer,ArrayList<AllergyList.PictureIngredient>> arrayLists = new TreeMap<>();
        arrayLists.put(setArrayListDemiVegetarian(),getArrayListDemiVegetarian());
        arrayLists.put(setArrayListOvoVegetarian(),getArrayListLactoOvoVegetarian());
        arrayLists.put(setArrayListLactoVegetarian(),getArrayListLactoVegetarian());
        arrayLists.put(setArrayListMuslim(),getArrayListMuslim());
        arrayLists.put(setArrayListOvoVegetarian(),getArrayListOvoVegetarian());
        arrayLists.put(setArrayListPescoVegetarian(),getArrayListPescoVegetarian());
        arrayLists.put(setArrayListPolloVegetarian(),getArrayListPolloVegetarian());
        arrayLists.put(setArrayListLactoOvoVegetarian(),getArrayListLactoOvoVegetarian());
        arrayLists.put(setArrayListVegan(),getArrayListVegan());

        arrayLists.put(setArrayListVegetarian(),getArrayListVegetarian());

        return arrayLists;
    }

    private Integer setArrayListVegetables() {
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.tomato));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.aspargus));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.avocado));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.bellPepper));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.cabbage));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.carrot));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.celery));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.garlic));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.lettuce));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.maize));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.potato));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.pumpkin));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.zucchini));
        for (PictureIngredient arrayListVegetable : arrayListVegetables) {
            Log.d(TAG, "veget: " + arrayListVegetable.getIngredient());
        }

        sort(arrayListVegetables);
        for (PictureIngredient arrayListVegetable : arrayListVegetables) {
            Log.d(TAG, "setArrayListVegetables: " + arrayListVegetable.getIngredient());
        }
        return R.string.vegetables;
    }

    private Integer setArrayListVegetarian() {
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.pig));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.duck));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.meat));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.goat));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.poultry));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.lamb));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.sausage));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.beef));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.veal));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.gelatin));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.shellfish));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.fish));
        arrayListVegetarian.add(new PictureIngredient(R.drawable.vegetarian, R.string.chicken));
        sort(arrayListVegetarian);
        return R.string.vegetarian;
    }

    private Integer setArrayListLactoOvoVegetarian() {
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.pig));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.duck));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.meat));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.goat));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.poultry));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.lamb));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.sausage));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.beef));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.veal));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.gelatin));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.shellfish));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.fish));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.dairy));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.egg));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.chicken));
        sort(arrayListLactoOvoVegetarian);
        return R.string.lactoOvoVegetarian;
    }

    private Integer setArrayListOvoVegetarian() {
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.pig));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.duck));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.meat));

        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.goat));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.poultry));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.lamb));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.sausage));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.beef));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.veal));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.gelatin));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.shellfish));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.fish));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.dairy));
        arrayListOvoVegetarian.add(new PictureIngredient(R.drawable.ovoveg, R.string.chicken));
        sort(arrayListLactoOvoVegetarian);
        return R.string.ovoVegetarian;
    }

    private Integer setArrayListDemiVegetarian() {
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.pig));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.duck));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.meat));

        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.goat));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.poultry));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.lamb));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.sausage));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.beef));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.veal));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.gelatin));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.shellfish));
        arrayListDemiVegetarian.add(new PictureIngredient(R.drawable.demiveg, R.string.chicken));
        sort(arrayListDemiVegetarian);
        return R.string.demiVegetarian;
    }

    private Integer setArrayListLactoVegetarian() {
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.pig));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.duck));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.meat));

        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.goat));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.poultry));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.lamb));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.sausage));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.beef));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.veal));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.gelatin));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.shellfish));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.fish));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.egg));
        arrayListLactoVegetarian.add(new PictureIngredient(R.drawable.lactovegitarian, R.string.chicken));
        sort(arrayListLactoVegetarian);
        return R.string.lactoVegetarian;
    }

    private Integer setArrayListPolloVegetarian() {
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.pig));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.duck));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.meat));

        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.goat));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.lamb));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.sausage));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.beef));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.veal));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.gelatin));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.shellfish));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.fish));
        arrayListPolloVegetarian.add(new PictureIngredient(R.drawable.polloveg, R.string.chicken));
        sort(arrayListPolloVegetarian);
        return R.string.polloVegetarian;
    }

    private Integer setArrayListPescoVegetarian() {
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.pig));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.duck));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.meat));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.goat));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.poultry));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.lamb));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.sausage));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.beef));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.veal));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.gelatin));
        arrayListPescoVegetarian.add(new PictureIngredient(R.drawable.pescoveg, R.string.shellfish));

        sort(arrayListPescoVegetarian);
        return R.string.pescoVegetarian;
    }

    private Integer setArrayListVegan() {
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.pig));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.duck));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.meat));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.goat));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.poultry));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.lamb));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.sausage));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.beef));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.veal));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.gelatin));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.shellfish));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.fish));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.dairy));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.egg));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.honey));
        arrayListVegan.add(new PictureIngredient(R.drawable.vegan, R.string.chicken));
        sort(arrayListVegan);
        return R.string.vegan;
    }

    private Integer setArrayListCitrus() {
        arrayListCitrus.add(new PictureIngredient(R.drawable.orange, R.string.lemon));
        arrayListCitrus.add(new PictureIngredient(R.drawable.orange, R.string.lime));
        arrayListCitrus.add(new PictureIngredient(R.drawable.orange, R.string.orange));
        arrayListCitrus.add(new PictureIngredient(R.drawable.orange, R.string.grapefruit));
        sort(arrayListCitrus);
        return R.string.citrus;
    }

    private Integer setArrayListMuslim() {
        arrayListMuslim.add(new PictureIngredient(R.drawable.halal, R.string.pig));
        arrayListMuslim.add(new PictureIngredient(R.drawable.halal, R.string.bacon));
        arrayListMuslim.add(new PictureIngredient(R.drawable.halal, R.string.gelatin));
        sort(arrayListMuslim);
        return R.string.halal;
    }


    private Integer setArrayListLegumes() {
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.chickpea));
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.lentil));
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.lupin));
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.peanut));
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.pea));
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.soy));
        arrayListLegumes.add(new PictureIngredient(R.drawable.legumes, R.string.soybean));
        sort(arrayListLegumes);
        return R.string.legumes;
    }

    private Integer setArrayListSpice() {
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.anis));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.coriander));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.cumin));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.fennel));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.parsley));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.ragweed));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.echinacea));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.artichoke));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.dandelions));
        arrayListSpice.add(new PictureIngredient(R.drawable.spice, R.string.hibiscus));
        sort(arrayListSpice);
        return R.string.spice;
    }

    private Integer setArrayListDairy() {
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.milk));
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.butter));
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.casein));
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.yoghurt));
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.cream));
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.custard));
        arrayListDairy.add(new PictureIngredient(R.drawable.milk, R.string.lactose));
        return R.string.dairy;

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

    public Integer setArrayListSeeds() {
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds, R.string.sesame));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds, R.string.buckwheat));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds, R.string.mustardSeed));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds, R.string.poppySeed));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds, R.string.pumpkinSeed));
        arrayListSeeds.add(new PictureIngredient(R.drawable.seeds, R.string.sunflowerSeed));
        sort(arrayListSeeds);
        return R.string.seeds;
    }

    public ArrayList<PictureIngredient> getArrayListFish() {
        return arrayListFish;
    }

    public Integer setArrayListFish() {
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.pike));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.tuna));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.salmon));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.caviar));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.anchovies));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.bass));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.catfish));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.cod));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.flounder));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.haddock));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.hake));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.halibut));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.herring));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.mahi));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.perch));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.pollock));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.swordfish));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.sole));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.snapper));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.surimi));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.tilapia));
        arrayListFish.add(new PictureIngredient(R.drawable.fish, R.string.trout));

        sort(arrayListFish);
        return R.string.fish;
    }

    public ArrayList<PictureIngredient> getArrayListShellfish() {
        return arrayListShellfish;
    }

    public Integer setArrayListShellfish() {
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.barnacle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.crab));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.crawfish));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.krill));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.lobster));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.prawns));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.shrimp));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.scampi));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.abalone));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.clams));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.cockle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.limpet));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.mussels));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.octopus));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.oysters));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.periwinkle));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.urchin));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.scallops));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.snails));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.squid));
        arrayListShellfish.add(new PictureIngredient(R.drawable.shellfish, R.string.whelk));

        sort(arrayListShellfish);
        return R.string.shellfish;
    }

    public ArrayList<String> getArrayListIngredients() {
        return arrayListIngredients;
    }

    public ArrayList<ImageView> getArrayListPictures() {
        return arrayListPictures;
    }

    public void setArrayListPictures(String arrayListPictures) {
        this.arrayListIngredients.add(arrayListPictures);
    }

    public ArrayList<PictureIngredient> getArrayListNuts() {
        return arrayListNuts;
    }

    public ArrayList<PictureIngredient> getArrayListGluten() {
        return arrayListGluten;
    }

    public ArrayList<PictureIngredient> getArrayListFruit() {
        return arrayListFruit;
    }

    public ArrayList<PictureIngredient> getArrayListVegetables() {
        return arrayListVegetables;
    }

    public Integer setArrayListNuts() {
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.almond));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.brazil));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.cocoa));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.hazelnut));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.macadamia));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.peanut));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.pecans));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.pili));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.pine));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.pistachios));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.soy));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.tiger));
        arrayListNuts.add(new PictureIngredient(R.drawable.nuts, R.string.walnuts));


        sort(arrayListNuts);
        return R.string.nuts;
    }

    public Integer setArrayListGluten() {

        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.durum));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.emmer));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.rye));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.barley));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.triticale));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.malt));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.semolina));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.spelt));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.farina));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.farro));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.graham));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.kamut));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.einkorn));
        arrayListGluten.add(new PictureIngredient(R.drawable.wheat, R.string.wheat));

        sort(arrayListGluten);
        return R.string.gluten;
    }

    public Integer setArrayListFruit() {

        arrayListFruit.add(new PictureIngredient(R.drawable.fruit, R.string.apple));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.pear));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.tomato));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.strawberry));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.prune));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.pomegranate));
        arrayListFruit.add(new PictureIngredient(R.drawable.pinapple, R.string.pinapple));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.persimmon));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.peach));
        arrayListFruit.add(new PictureIngredient(R.drawable.orange, R.string.orange));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.melon));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.mango));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.lychee));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.kiwi));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.grape));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.fig));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.date));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.coconut));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.cherry));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.banana));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.apricot));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.acerola));

        sort(arrayListFruit);
        return R.string.fruit;
    }

    private void sort(ArrayList<PictureIngredient> arrayList) {
        Collections.sort(arrayList, new Comparator<PictureIngredient>() {
            @Override
            public int compare(PictureIngredient pic1, PictureIngredient pic2) {
                return pic1.getIngredient().compareToIgnoreCase(pic2.getIngredient());
            }
        });
    }


    public ArrayList<PictureIngredient> getArrayListDairy() {
        return arrayListDairy;
    }

    public class PictureIngredient {
        private int picture;
        private String ingredient;
        private int id;

        PictureIngredient(int picture, int ingredient) {
            this.picture = picture;
            this.id = ingredient;
            this.ingredient = context.getString(ingredient);
        }


        public int getPicture() {
            return picture;
        }

        public String getIngredient() {
            return ingredient;
        }

        public int getId() {
            return id;
        }

    }
}
