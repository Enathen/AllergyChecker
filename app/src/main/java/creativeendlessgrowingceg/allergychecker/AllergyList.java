package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.support.annotation.NonNull;
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
    public ArrayList<PictureIngredient> getSpecifiedKeyAllergy(int key){
        if(key == R.string.fish){
            setArrayListFish();
            return getArrayListFish();
        }
        if(key == R.string.shellfish){
            setArrayListShellfish();
            return getArrayListShellfish();
        }
        if(key == R.string.dairy){
            setArrayListDairy();
            return getArrayListDairy();
        }
        return null;
    }
    public boolean checkAvailablePicture(int picture){
        if(picture == R.drawable.fish)
            return true;
        if(picture == R.drawable.shellfish)
            return true;
        if(picture == R.drawable.peach)
            return true;
        if(picture == R.drawable.milk)
            return true;
        if(picture == R.drawable.nuts)
            return true;
        if(picture == R.drawable.legumes)
            return true;
        if(picture == R.drawable.tomato)
            return true;
        if(picture == R.drawable.spice)
            return true;
        if(picture == R.drawable.orange)
            return true;
        if(picture == R.drawable.seeds)
            return true;
        if(picture == R.drawable.wheat)
            return true;
        if(picture == R.drawable.halal)
            return true;
        if(picture == R.drawable.vegetarian)
            return true;
        if(picture == R.drawable.vegan)
            return true;
        if(picture == R.drawable.lactovegitarian)
            return true;
        if(picture == R.drawable.ovoveg)
            return true;
        if(picture == R.drawable.pescoveg)
            return true;
        if(picture == R.drawable.polloveg)
            return true;
        if(picture == R.drawable.lactoovoveg)
            return true;
        if(picture == R.drawable.demiveg)
            return true;
        return false;


    }
    public ArrayList<PictureIngredient> getSpecifiedKeyPreference(int key){
        if(key == R.string.vegan){
            setArrayListVegan();
            return getArrayListVegan();
        }
        if(key == R.string.vegetarian){
            setArrayListVegetarian();
            return getArrayListVegetarian();
        }
        if(key == R.string.demiVegetarian){
            setArrayListDemiVegetarian();
            return getArrayListDemiVegetarian();
        }
        if(key == R.string.ovoVegetarian){
            setArrayListOvoVegetarian();
            return getArrayListOvoVegetarian();
        }
        if(key == R.string.lactoVegetarian){
            setArrayListLactoVegetarian();
            return getArrayListLactoVegetarian();
        }
        if(key == R.string.lactoOvoVegetarian){
            setArrayListLactoOvoVegetarian();
            return getArrayListLactoOvoVegetarian();
        }
        if(key == R.string.pescoVegetarian){
            setArrayListPescoVegetarian();
            return getArrayListPescoVegetarian();
        }
        if(key == R.string.polloVegetarian){
            setArrayListPolloVegetarian();
            return getArrayListPolloVegetarian();
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
        //arrayLists.put(setArrayListMuslim(),getArrayListMuslim());
        arrayLists.put(setArrayListLactoVegetarian(),getArrayListLactoVegetarian());
        arrayLists.put(setArrayListOvoVegetarian(),getArrayListOvoVegetarian());
        arrayLists.put(setArrayListLactoOvoVegetarian(),getArrayListLactoOvoVegetarian());
        arrayLists.put(setArrayListPescoVegetarian(),getArrayListPescoVegetarian());
        arrayLists.put(setArrayListPolloVegetarian(),getArrayListPolloVegetarian());
        arrayLists.put(setArrayListVegan(),getArrayListVegan());

        arrayLists.put(setArrayListVegetarian(),getArrayListVegetarian());

        return arrayLists;
    }

    private Integer setArrayListVegetables() {
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.tomato));
        arrayListVegetables.add(new PictureIngredient(R.drawable.tomato, R.string.aspargus));

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
      /*  for (PictureIngredient arrayListVegetable : arrayListVegetables) {
            Log.d(TAG, "veget: " + arrayListVegetable.getIngredient());
        }*/

        sort(arrayListVegetables);
        /*for (PictureIngredient arrayListVegetable : arrayListVegetables) {
            Log.d(TAG, "setArrayListVegetables: " + arrayListVegetable.getIngredient());
        }*/
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
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.pig));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.duck));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.meat));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.goat));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.poultry));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.lamb));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.sausage));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.beef));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.veal));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.gelatin));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.shellfish));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.fish));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.dairy));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.egg));
        arrayListLactoOvoVegetarian.add(new PictureIngredient(R.drawable.lactoovoveg, R.string.chicken));
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

        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.apple));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.avocado));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.pear));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.tomato));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.strawberry));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.prune));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.pomegranate));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.pinapple));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.persimmon));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.peach));
        arrayListFruit.add(new PictureIngredient(R.drawable.peach, R.string.orange));
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
    public ArrayList<E_Numbers> getArrayListE_Numbers(){
        ArrayList<E_Numbers> arrayListE_Numbers = new ArrayList<>();
        arrayListE_Numbers.add(new E_Numbers("Curcumin",R.string.E100,"E100",1,1,"https://en.wikipedia.org/wiki/Curcumin"));
        arrayListE_Numbers.add(new E_Numbers("Riboflavin",R.string.E101,"E101",1,1,"https://en.wikipedia.org/wiki/Riboflavin"));
        arrayListE_Numbers.add(new E_Numbers("Tartrazine",R.string.E102,"E102",0,1,"https://en.wikipedia.org/wiki/Tartrazine"));
        arrayListE_Numbers.add(new E_Numbers("Alkannin",R.string.E103,"E103",1,0,"https://en.wikipedia.org/wiki/Alkannin"));
        arrayListE_Numbers.add(new E_Numbers("Quinoline Yellow WS",R.string.E104,"E104",0,1,"https://en.wikipedia.org/wiki/Quinoline_Yellow_WS"));
        arrayListE_Numbers.add(new E_Numbers("Fast Yellow AB",R.string.E105,"E105",0,0,"https://en.wikipedia.org/wiki/Fast_Yellow_AB"));
        arrayListE_Numbers.add(new E_Numbers("Riboflavin-5-Sodium Phosphate",R.string.E106,"E106",0,0,"https://en.wikipedia.org/wiki/Flavin_mononucleotide"));
        arrayListE_Numbers.add(new E_Numbers("Yellow 2G",R.string.E107,"E107",0,0,"https://en.wikipedia.org/wiki/Yellow_2G"));

        arrayListE_Numbers.add(new E_Numbers("Sunset Yellow FCF ",R.string.E110,"E110",1,1,"https://en.wikipedia.org/wiki/Sunset_Yellow_FCF"));
        arrayListE_Numbers.add(new E_Numbers("Orange GGN",R.string.E111,"E111",1,0,"https://en.wikipedia.org/wiki/Orange_GGN"));
        arrayListE_Numbers.add(new E_Numbers("Cochineal",R.string.E120,"E120",1,1,"https://en.wikipedia.org/wiki/Cochineal"));
        arrayListE_Numbers.add(new E_Numbers("Citrus Red 2",R.string.E121,"E121",1,0,"https://en.wikipedia.org/wiki/Citrus_Red_2"));
        arrayListE_Numbers.add(new E_Numbers("Carmoisine",R.string.E122,"E122",2,1,"https://en.wikipedia.org/wiki/Azorubine"));
        arrayListE_Numbers.add(new E_Numbers("Amaranth",R.string.E123,"E123",2,1,"https://en.wikipedia.org/wiki/Amaranth_(dye)"));
        arrayListE_Numbers.add(new E_Numbers("Ponceau 4R",R.string.E124,"E124",0,1,"https://en.wikipedia.org/wiki/Ponceau_4R"));
        arrayListE_Numbers.add(new E_Numbers("Ponceau SX",R.string.E125,"E125",1,0,"https://en.wikipedia.org/wiki/Scarlet_GN"));
        arrayListE_Numbers.add(new E_Numbers("Ponceau 6R",R.string.E126,"E126",0,0,"https://en.wikipedia.org/wiki/Ponceau_6R"));
        arrayListE_Numbers.add(new E_Numbers("Erythrosine",R.string.E127,"E127",1,1,"https://en.wikipedia.org/wiki/Erythrosine"));
        arrayListE_Numbers.add(new E_Numbers("Red 2G",R.string.E128,"E128",0,0,"https://en.wikipedia.org/wiki/Red_2G"));
        arrayListE_Numbers.add(new E_Numbers("Allura Red AC",R.string.E129,"E129",1,1,"https://en.wikipedia.org/wiki/Allura_Red_AC"));

        arrayListE_Numbers.add(new E_Numbers("Indanthrene blue RS",R.string.E130,"E130",0,0,"https://en.wikipedia.org/wiki/Indanthrone_blue"));
        arrayListE_Numbers.add(new E_Numbers("Patent Blue V",R.string.E131,"E131",0,1,"https://en.wikipedia.org/wiki/Patent_Blue_V"));
        arrayListE_Numbers.add(new E_Numbers("Indigo carmine",R.string.E132,"E132",1,1,"https://en.wikipedia.org/wiki/Indigo_carmine"));
        arrayListE_Numbers.add(new E_Numbers("Brilliant Blue FCF",R.string.E133,"E133",1,1,"https://en.wikipedia.org/wiki/Brilliant_Blue_FCF"));

        arrayListE_Numbers.add(new E_Numbers("Chlorophylls",R.string.E140,"E140",0,1,"https://en.wikipedia.org/wiki/Chlorophyll"));
        arrayListE_Numbers.add(new E_Numbers("Copper complexes of chlorophylls and chlorophyllins",R.string.E141,"E141",1,1,""));
        arrayListE_Numbers.add(new E_Numbers("Green S",R.string.E142,"E142",0,1,"https://en.wikipedia.org/wiki/Green_S"));
        arrayListE_Numbers.add(new E_Numbers("Fast Green FCF",R.string.E143,"E143",1,2,"https://en.wikipedia.org/wiki/Fast_Green_FCF"));

        arrayListE_Numbers.add(new E_Numbers("Plain caramel",R.string.E150,"E150",1,1,"https://en.wikipedia.org/wiki/Caramel_color"));
        arrayListE_Numbers.add(new E_Numbers("Brilliant Black BN",R.string.E151,"E151",0,1,"https://en.wikipedia.org/wiki/Brilliant_Black_BN"));
        arrayListE_Numbers.add(new E_Numbers("Carbon black",R.string.E152,"E152",0,0,"https://en.wikipedia.org/wiki/Carbon_black"));
        arrayListE_Numbers.add(new E_Numbers("Vegetable carbon",R.string.E153,"E153",0,1,"https://en.wikipedia.org/wiki/Carbon_black"));
        arrayListE_Numbers.add(new E_Numbers("Brown FK",R.string.E154,"E154",0,0,"https://en.wikipedia.org/wiki/Brown_FK"));
        arrayListE_Numbers.add(new E_Numbers("Brown HT",R.string.E155,"E155",0,1,"https://en.wikipedia.org/wiki/Brown_HT"));

        arrayListE_Numbers.add(new E_Numbers("Alpha-carotene",R.string.E160a,"E160a",0,1,"https://en.wikipedia.org/wiki/Alpha-Carotene"));
        arrayListE_Numbers.add(new E_Numbers("Annatto",R.string.E160b,"E160b",0,1,"https://en.wikipedia.org/wiki/Annatto"));
        arrayListE_Numbers.add(new E_Numbers("Paprika oleoresin",R.string.E160c,"E160c",1,1,"https://en.wikipedia.org/wiki/Paprika_oleoresin"));
        arrayListE_Numbers.add(new E_Numbers("Lycopene",R.string.E160d,"E160d",1,1,"https://en.wikipedia.org/wiki/Lycopene"));
        arrayListE_Numbers.add(new E_Numbers("Beta-apo-8'-carotenal",R.string.E160e,"E160e",1,1,"https://en.wikipedia.org/wiki/Apocarotenal"));
        arrayListE_Numbers.add(new E_Numbers("Ethyl ester of beta-apo-8'-carotenic acid",R.string.E160f,"E160f",0,0,"https://en.wikipedia.org/wiki/Food_orange_7"));
        arrayListE_Numbers.add(new E_Numbers("Flavoxanthin",R.string.E161a,"E161a",0,0,"https://en.wikipedia.org/wiki/Flavoxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Lutein",R.string.E161b,"E161b",0,1,"https://en.wikipedia.org/wiki/Lutein"));
        arrayListE_Numbers.add(new E_Numbers("Cryptoxanthin",R.string.E161c,"E161c",0,0,"https://en.wikipedia.org/wiki/Cryptoxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Rubixanthin",R.string.E161d,"E161d",0,0,"https://en.wikipedia.org/wiki/Rubixanthin"));
        arrayListE_Numbers.add(new E_Numbers("Violaxanthin",R.string.E161e,"E161e",0,0,"https://en.wikipedia.org/wiki/Violaxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Rhodoxanthin",R.string.E161f,"E161f",0,0,"https://en.wikipedia.org/wiki/Rhodoxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Canthaxanthin",R.string.E161g,"E161g",1,1,"https://en.wikipedia.org/wiki/Canthaxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Zeaxanthin",R.string.E161h,"E161h",0,0,"https://en.wikipedia.org/wiki/Zeaxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Citranaxanthin",R.string.E161i,"E161i",0,0,"https://en.wikipedia.org/wiki/Citranaxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Astaxanthin",R.string.E161j,"E161j",0,0,"https://en.wikipedia.org/wiki/Astaxanthin"));
        arrayListE_Numbers.add(new E_Numbers("Beetroot Red",R.string.E162,"E162",1,1,"https://en.wikipedia.org/wiki/Betanin"));
        arrayListE_Numbers.add(new E_Numbers("Anthocyanins",R.string.E163,"E163",0,1,"https://en.wikipedia.org/wiki/Anthocyanin"));
        arrayListE_Numbers.add(new E_Numbers("Saffron",R.string.E164,"E164",1,0,"https://en.wikipedia.org/wiki/Saffron"));

        arrayListE_Numbers.add(new E_Numbers("Calcium carbonate",R.string.E170,"E170",0,1,"https://en.wikipedia.org/wiki/Calcium_carbonate"));
        arrayListE_Numbers.add(new E_Numbers("Titanium dioxide",R.string.E171,"E171",1,1,"https://en.wikipedia.org/wiki/Titanium_dioxide"));
        arrayListE_Numbers.add(new E_Numbers("Iron oxides",R.string.E172,"E172",1,1,"https://en.wikipedia.org/wiki/Iron_oxide"));
        arrayListE_Numbers.add(new E_Numbers("Aluminium",R.string.E173,"E173",0,1,"https://en.wikipedia.org/wiki/Aluminium"));
        arrayListE_Numbers.add(new E_Numbers("Silver",R.string.E174,"E174",0,1,"https://en.wikipedia.org/wiki/Silver"));
        arrayListE_Numbers.add(new E_Numbers("Gold",R.string.E175,"E175",0,1,"https://en.wikipedia.org/wiki/Gold"));

        arrayListE_Numbers.add(new E_Numbers("Pigment Rubine",R.string.E180,"E180",0,1,"https://en.wikipedia.org/wiki/Lithol_Rubine_BK"));
        arrayListE_Numbers.add(new E_Numbers("Tannin",R.string.E181,"E181",0,0,"https://en.wikipedia.org/wiki/Health_effects_of_natural_phenols_and_polyphenols"));
        arrayListE_Numbers.add(new E_Numbers("Orcein",R.string.E182,"E182",0,2,"https://en.wikipedia.org/wiki/Orcein"));


        arrayListE_Numbers.add(new E_Numbers("Sorbic acid",R.string.E200,"E200",0,1,"https://en.wikipedia.org/wiki/Sorbic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium sorbate",R.string.E201,"E201",0,0,"https://en.wikipedia.org/wiki/Sodium_sorbate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium sorbate",R.string.E202,"E202",0,1,"https://en.wikipedia.org/wiki/Potassium_sorbate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium sorbate",R.string.E203,"E203",0,1,"https://en.wikipedia.org/wiki/Calcium_sorbate"));
        arrayListE_Numbers.add(new E_Numbers("Heptyl p-hydroxybenzoate",R.string.E209,"E209",0,0,"https://en.wikipedia.org/wiki/Heptylparaben"));

        arrayListE_Numbers.add(new E_Numbers("Benzoic acid",R.string.E210,"E210",0,1,"https://en.wikipedia.org/wiki/Benzoic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium benzoate",R.string.E211,"E211",0,1,"https://en.wikipedia.org/wiki/Sodium_benzoate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium benzoate",R.string.E212,"E212",0,1,"https://en.wikipedia.org/wiki/Potassium_benzoate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium benzoate",R.string.E213,"E213",0,1,"https://en.wikipedia.org/wiki/Calcium_benzoate"));
        arrayListE_Numbers.add(new E_Numbers("Ethylparaben",R.string.E214,"E214",0,1,"https://en.wikipedia.org/wiki/Ethylparaben"));
        arrayListE_Numbers.add(new E_Numbers("Sodium ethyl para-hydroxybenzoate",R.string.E215,"E215",0,1,"https://en.wikipedia.org/wiki/Ethylparaben"));
        arrayListE_Numbers.add(new E_Numbers("Propylparaben",R.string.E216,"E216",0,0,"https://en.wikipedia.org/wiki/Propylparaben"));
        arrayListE_Numbers.add(new E_Numbers("Sodium propyl para-hydroxybenzoate",R.string.E217,"E217",0,0,"https://en.wikipedia.org/wiki/Propylparaben"));
        arrayListE_Numbers.add(new E_Numbers("Methylparaben",R.string.E218,"E218",0,1,"https://en.wikipedia.org/wiki/Methylparaben"));
        arrayListE_Numbers.add(new E_Numbers("Sodium methyl para-hydroxybenzoate",R.string.E219,"E219",0,1,"https://en.wikipedia.org/wiki/Sodium_methylparaben"));

        arrayListE_Numbers.add(new E_Numbers("Sulphur dioxide",R.string.E220,"E220",0,1,"https://en.wikipedia.org/wiki/Sulfur_dioxide"));
        arrayListE_Numbers.add(new E_Numbers("Sodium sulphite",R.string.E221,"E221",0,1,"https://en.wikipedia.org/wiki/Sodium_sulfite"));
        arrayListE_Numbers.add(new E_Numbers("Sodium bisulphite",R.string.E222,"E222",0,1,"https://en.wikipedia.org/wiki/Sodium_bisulfite"));
        arrayListE_Numbers.add(new E_Numbers("Sodium metabisulphite",R.string.E223,"E223",0,1,"https://en.wikipedia.org/wiki/Sodium_metabisulfite"));
        arrayListE_Numbers.add(new E_Numbers("Potassium metabisulphite",R.string.E224,"E224",0,1,"https://en.wikipedia.org/wiki/Potassium_metabisulfite"));
        arrayListE_Numbers.add(new E_Numbers("Potassium sulphite",R.string.E225,"E225",0,0,"https://en.wikipedia.org/wiki/Potassium_sulfite"));
        arrayListE_Numbers.add(new E_Numbers("Calcium sulphite",R.string.E226,"E226",0,1,"https://en.wikipedia.org/wiki/Calcium_sulfite"));
        arrayListE_Numbers.add(new E_Numbers("Calcium hydrogen sulphite",R.string.E227,"E227",0,1,"https://en.wikipedia.org/wiki/Calcium_bisulfite"));
        arrayListE_Numbers.add(new E_Numbers("Potassium hydrogen sulphite",R.string.E228,"E228",0,1,"https://en.wikipedia.org/wiki/Potassium_bisulfite"));

        arrayListE_Numbers.add(new E_Numbers("Biphenyl",R.string.E230,"E230",0,0,"https://en.wikipedia.org/wiki/Biphenyl"));
        arrayListE_Numbers.add(new E_Numbers("Orthophenyl phenol",R.string.E231,"E231",0,0,"https://en.wikipedia.org/wiki/2-Phenylphenol"));
        arrayListE_Numbers.add(new E_Numbers("Sodium orthophenyl phenol",R.string.E232,"E232",0,0,"https://en.wikipedia.org/wiki/Sodium_orthophenyl_phenol"));
        arrayListE_Numbers.add(new E_Numbers("Thiabendazole",R.string.E233,"E233",0,0,"https://en.wikipedia.org/wiki/Tiabendazole"));
        arrayListE_Numbers.add(new E_Numbers("Nisin",R.string.E234,"E234",0,1,"https://en.wikipedia.org/wiki/Nisin"));
        arrayListE_Numbers.add(new E_Numbers("Natamycin, Pimaracin",R.string.E235,"E235",0,1,"https://en.wikipedia.org/wiki/Natamycin"));
        arrayListE_Numbers.add(new E_Numbers("Formic acid",R.string.E236,"E236",0,0,"https://en.wikipedia.org/wiki/Formic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium formate",R.string.E237,"E237",0,0,"https://en.wikipedia.org/wiki/Sodium_formate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium formate",R.string.E238,"E238",0,0,"https://en.wikipedia.org/wiki/Calcium_formate"));
        arrayListE_Numbers.add(new E_Numbers("Hexamine",R.string.E239,"E239",0,1,"https://en.wikipedia.org/wiki/Hexamethylenetetramine"));

        arrayListE_Numbers.add(new E_Numbers("Formaldehyde",R.string.E240,"E240",0,0,"https://en.wikipedia.org/wiki/Formaldehyde"));
        arrayListE_Numbers.add(new E_Numbers("Dimethyl dicarbonate",R.string.E242,"E242",0,1,"https://en.wikipedia.org/wiki/Dimethyl_dicarbonate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium nitrite",R.string.E249,"E249",0,1,"https://en.wikipedia.org/wiki/Potassium_nitrite"));

        arrayListE_Numbers.add(new E_Numbers("Sodium nitrite",R.string.E250,"E250",0,1,"https://en.wikipedia.org/wiki/Sodium_nitrite"));
        arrayListE_Numbers.add(new E_Numbers("Sodium nitrate ",R.string.E251,"E251",0,1,"https://en.wikipedia.org/wiki/Sodium_nitrate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium nitrate",R.string.E252,"E252",0,1,"https://en.wikipedia.org/wiki/Potassium_nitrate"));

        arrayListE_Numbers.add(new E_Numbers("Acetic acid",R.string.E260,"E260",0,1,"https://en.wikipedia.org/wiki/Acetic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Potassium acetate",R.string.E261,"E261",0,1,"https://en.wikipedia.org/wiki/Potassium_acetate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium acetates",R.string.E262,"E262",0,1,"https://en.wikipedia.org/wiki/Sodium_acetate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium acetate",R.string.E263,"E263",0,1,"https://en.wikipedia.org/wiki/Calcium_acetate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium acetate",R.string.E264,"E264",0,0,"https://en.wikipedia.org/wiki/Ammonium_acetate"));
        arrayListE_Numbers.add(new E_Numbers("Dehydroacetic acid",R.string.E265,"E265",0,0,"https://en.wikipedia.org/wiki/Dehydroacetic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium dehydroacetate",R.string.E266,"E266",0,0,"https://en.wikipedia.org/wiki/Sodium_dehydroacetate"));

        arrayListE_Numbers.add(new E_Numbers("Lactic acid",R.string.E270,"E270",1,1,"https://en.wikipedia.org/wiki/Lactic_acid"));

        arrayListE_Numbers.add(new E_Numbers("Propionic acid",R.string.E280,"E280",1,1,"https://en.wikipedia.org/wiki/Propionic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium propionate",R.string.E281,"E281",1,1,"https://en.wikipedia.org/wiki/Sodium_propionate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium propionate",R.string.E282,"E282",0,1,"https://en.wikipedia.org/wiki/Calcium_propanoate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium propionate",R.string.E283,"E283",0,1,"https://en.wikipedia.org/wiki/Potassium_propanoate"));
        arrayListE_Numbers.add(new E_Numbers("Boric acid",R.string.E284,"E284",0,1,"https://en.wikipedia.org/wiki/Boric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium tetraborate",R.string.E285,"E285",0,1,"https://en.wikipedia.org/wiki/Borax"));

        arrayListE_Numbers.add(new E_Numbers("Carbon dioxide",R.string.E290,"E290",0,1,"https://en.wikipedia.org/wiki/Carbon_dioxide"));
        arrayListE_Numbers.add(new E_Numbers("Malic acid",R.string.E296,"E296",1,1,"https://en.wikipedia.org/wiki/Malic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Fumaric acid",R.string.E297,"E297",0,1,"https://en.wikipedia.org/wiki/Fumaric_acid"));




        arrayListE_Numbers.add(new E_Numbers("Ascorbic acid (Vitamin C)",R.string.E300,"E300",0,1,"https://en.wikipedia.org/wiki/Vitamin_C"));
        arrayListE_Numbers.add(new E_Numbers("Sodium ascorbate",R.string.E301,"E301",1,1,"https://en.wikipedia.org/wiki/Sodium_ascorbate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium ascorbate",R.string.E302,"E302",1,1,"https://en.wikipedia.org/wiki/Calcium_ascorbate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium ascorbate",R.string.E303,"E303",0,0,"https://en.wikipedia.org/wiki/Potassium_ascorbate"));
        arrayListE_Numbers.add(new E_Numbers("Fatty acid esters of ascorbic acid",R.string.E304,"E304",1,1,"https://en.wikipedia.org/wiki/Ascorbyl_palmitate"));
        arrayListE_Numbers.add(new E_Numbers("Ascorbyl stearate",R.string.E305,"E305",0,0,"https://en.wikipedia.org/wiki/Ascorbyl_stearate"));
        arrayListE_Numbers.add(new E_Numbers("Tocopherols",R.string.E306,"E306",1,1,"https://en.wikipedia.org/wiki/Tocopherol"));
        arrayListE_Numbers.add(new E_Numbers("Alpha-Tocopherol",R.string.E307,"E307",0,1,"https://en.wikipedia.org/wiki/Alpha-Tocopherol"));
        arrayListE_Numbers.add(new E_Numbers("Gamma-Tocopherol",R.string.E308,"E308",0,1,"https://en.wikipedia.org/wiki/Gamma-Tocopherol"));
        arrayListE_Numbers.add(new E_Numbers("Delta-Tocopherol",R.string.E309,"E309",0,1,"https://en.wikipedia.org/wiki/Delta-Tocopherol"));

        arrayListE_Numbers.add(new E_Numbers("Propyl gallate",R.string.E310,"E310",0,1,"https://en.wikipedia.org/wiki/Propyl_gallate"));
        arrayListE_Numbers.add(new E_Numbers("Octyl gallate",R.string.E311,"E311",0,1,"https://en.wikipedia.org/wiki/Octyl_gallate"));
        arrayListE_Numbers.add(new E_Numbers("Dodecyl gallate",R.string.E312,"E312",0,1,"https://en.wikipedia.org/wiki/Dodecyl_gallate"));
        arrayListE_Numbers.add(new E_Numbers("Ethyl gallate",R.string.E313,"E313",0,0,"https://en.wikipedia.org/wiki/Ethyl_gallate"));
        arrayListE_Numbers.add(new E_Numbers("Guaiac resin",R.string.E314,"E314",0,0,"https://en.wikipedia.org/wiki/Gum_guaicum"));
        arrayListE_Numbers.add(new E_Numbers("Erythorbic acid",R.string.E315,"E315",0,1,"https://en.wikipedia.org/wiki/Erythorbic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium erythorbate",R.string.E316,"E316",0,1,"https://en.wikipedia.org/wiki/Sodium_erythorbate"));
        arrayListE_Numbers.add(new E_Numbers("Erythorbin acid",R.string.E317,"E317",0,0,"https://en.wikipedia.org/wiki/Erythorbic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium erythorbin",R.string.E318,"E318",0,0,"https://en.wikipedia.org/wiki/Calcium_erythorbate"));
        arrayListE_Numbers.add(new E_Numbers("tert-Butylhydroquinone",R.string.E319,"E319",0,1,"https://en.wikipedia.org/wiki/Tert-Butylhydroquinone"));

        arrayListE_Numbers.add(new E_Numbers("Butylated hydroxyanisole",R.string.E320,"E320",0,1,"https://en.wikipedia.org/wiki/Butylated_hydroxyanisole"));
        arrayListE_Numbers.add(new E_Numbers("Butylated hydroxytoluene",R.string.E321,"E321",0,1,"https://en.wikipedia.org/wiki/Butylated_hydroxytoluene"));
        arrayListE_Numbers.add(new E_Numbers("Lecithin",R.string.E322,"E322",0,1,"https://en.wikipedia.org/wiki/Lecithin"));
        arrayListE_Numbers.add(new E_Numbers("Anoxomer",R.string.E323,"E323",0,0,"https://en.wikipedia.org/wiki/Anoxomer"));
        arrayListE_Numbers.add(new E_Numbers("Ethoxyquin",R.string.E324,"E324",0,0,"https://en.wikipedia.org/wiki/Ethoxyquin"));
        arrayListE_Numbers.add(new E_Numbers("Sodium lactate",R.string.E325,"E325",0,1,"https://en.wikipedia.org/wiki/Sodium_lactate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium lactate",R.string.E326,"E326",0,1,"https://en.wikipedia.org/wiki/Potassium_lactate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium lactate",R.string.E327,"E327",0,1,"https://en.wikipedia.org/wiki/Calcium_lactate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium lactate",R.string.E328,"E328",0,0,"https://en.wikipedia.org/wiki/Ammonium_lactate"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium lactate",R.string.E329,"E329",0,0,"https://en.wikipedia.org/wiki/Magnesium_lactate"));

        arrayListE_Numbers.add(new E_Numbers("Citric acid",R.string.E330,"E330",0,1,"https://en.wikipedia.org/wiki/Citric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium citrates",R.string.E331,"E331",0,1,"https://en.wikipedia.org/wiki/Sodium_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium citrates",R.string.E332,"E332",0,1,"https://en.wikipedia.org/wiki/Potassium_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium citrates",R.string.E333,"E333",0,1,"https://en.wikipedia.org/wiki/Calcium_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Tartaric acid",R.string.E334,"E334",0,1,"https://en.wikipedia.org/wiki/Tartaric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium tartrates",R.string.E335,"E335",0,1,"https://en.wikipedia.org/wiki/Sodium_tartrate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium tartrates",R.string.E336,"E336",0,1,"https://en.wikipedia.org/wiki/Potassium_tartrate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium potassium tartrate",R.string.E337,"E337",0,1,"https://en.wikipedia.org/wiki/Potassium_sodium_tartrate"));
        arrayListE_Numbers.add(new E_Numbers("Orthophosphoric acid",R.string.E338,"E338",0,1,"https://en.wikipedia.org/wiki/Phosphoric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium phosphates",R.string.E339,"E339",0,1,"https://en.wikipedia.org/wiki/Sodium_phosphates"));

        arrayListE_Numbers.add(new E_Numbers("Potassium phosphates",R.string.E340,"E340",0,1,"https://en.wikipedia.org/wiki/Dipotassium_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium phosphates",R.string.E341,"E341",0,1,"https://en.wikipedia.org/wiki/Calcium_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium phosphates",R.string.E342,"E342",0,0,"https://en.wikipedia.org/wiki/Ammonium_dihydrogen_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium phosphates",R.string.E343,"E343",0,1,"https://en.wikipedia.org/wiki/Magnesium_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Lecithin citrate",R.string.E344,"E344",0,0,"https://en.wikipedia.org/wiki/Lecithin"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium citrate",R.string.E345,"E345",0,0,"https://en.wikipedia.org/wiki/Magnesium_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium malate",R.string.E349,"E349",0,0,"https://en.wikipedia.org/wiki/Ammonium_malate"));

        arrayListE_Numbers.add(new E_Numbers("Sodium malates",R.string.E350,"E350",0,1,"https://en.wikipedia.org/wiki/Sodium_malate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium malate",R.string.E351,"E351",0,1,"https://en.wikipedia.org/wiki/Potassium_malate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium malates",R.string.E352,"E352",0,1,"https://en.wikipedia.org/wiki/Calcium_malate"));
        arrayListE_Numbers.add(new E_Numbers("Metatartaric acid",R.string.E353,"E353",0,1,"https://en.wikipedia.org/wiki/Metatartaric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Calcium tartrate",R.string.E354,"E354",0,1,"https://en.wikipedia.org/wiki/Calcium_tartrate"));
        arrayListE_Numbers.add(new E_Numbers("Adipic acid",R.string.E355,"E355",0,1,"https://en.wikipedia.org/wiki/Adipic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium adipate",R.string.E356,"E356",0,1,"https://en.wikipedia.org/wiki/Sodium_adipate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium adipate",R.string.E357,"E357",0,1,"https://en.wikipedia.org/wiki/Potassium_adipate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium adipate",R.string.E359,"E359",0,0,"https://en.wikipedia.org/wiki/Ammonium_adipate"));

        arrayListE_Numbers.add(new E_Numbers("Succinic acid",R.string.E363,"E363",0,1,"https://en.wikipedia.org/wiki/Succinic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium fumarate",R.string.E365,"E365",0,0,"https://en.wikipedia.org/wiki/Sodium_fumarate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium fumarate",R.string.E366,"E366",0,0,"https://en.wikipedia.org/wiki/Potassium_fumarate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium fumarate",R.string.E367,"E367",0,0,"https://en.wikipedia.org/wiki/Calcium_fumarate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium fumarate",R.string.E368,"E368",0,0,"https://en.wikipedia.org/wiki/Ammonium_fumarate"));

        arrayListE_Numbers.add(new E_Numbers("1,4-Heptonolactone",R.string.E370,"E370",0,0,"https://en.wikipedia.org/wiki/1,4-Heptonolactone"));

        arrayListE_Numbers.add(new E_Numbers("Triammonium citrate",R.string.E380,"E380",0,1,"https://en.wikipedia.org/wiki/Triammonium_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium ferric citrate",R.string.E381,"E381",0,0,"https://en.wikipedia.org/wiki/Ammonium_ferric_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium glycerylphosphate",R.string.E383,"E383",0,0,"https://en.wikipedia.org/wiki/Calcium_glycerylphosphate"));
        arrayListE_Numbers.add(new E_Numbers("Isopropyl citrate",R.string.E384,"E384",0,0,"https://en.wikipedia.org/wiki/Isopropyl_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium disodium ethylene diamine tetraacetate",R.string.E385,"E385",0,1,"https://en.wikipedia.org/wiki/Ethylenediaminetetraacetic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Disodium ethylene diamine tetraacetate",R.string.E386,"E386",0,0,"https://en.wikipedia.org/wiki/Ethylenediaminetetraacetic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Oxystearin",R.string.E387,"E387",0,0,"https://en.wikipedia.org/w/index.php?title=Oxystearin&action=edit&redlink=1"));
        arrayListE_Numbers.add(new E_Numbers("Thiodipropionic acid",R.string.E388,"E388",0,0,"https://en.wikipedia.org/wiki/Thiodipropionic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Dilauryl thiodipropionate",R.string.E389,"E389",0,0,"https://en.wikipedia.org/wiki/Dilauryl_thiodipropionate"));

        arrayListE_Numbers.add(new E_Numbers("Distearyl thiodipropionate",R.string.E390,"E390",0,0,"https://en.wikipedia.org/wiki/Distearyl_thiodipropionate"));
        arrayListE_Numbers.add(new E_Numbers("Phytic acid",R.string.E391,"E391",0,0,"https://en.wikipedia.org/wiki/Phytic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Extracts of rosemary",R.string.E392,"E392",0,1,"https://en.wikipedia.org/wiki/Rosemary"));
        arrayListE_Numbers.add(new E_Numbers("Calcium lactobionate",R.string.E399,"E399",0,0,"https://en.wikipedia.org/wiki/Lactobionic_acid"));


        arrayListE_Numbers.add(new E_Numbers("Alginic acid",R.string.E400,"E400",0,1,"https://en.wikipedia.org/wiki/Alginic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium alginate",R.string.E401,"E401",0,1,"https://en.wikipedia.org/wiki/Alginic_acid#Sodium_alginate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium alginate",R.string.E402,"E402",0,1,"https://en.wikipedia.org/wiki/Alginic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium alginate",R.string.E403,"E403",0,1,"https://en.wikipedia.org/wiki/Ammonium_alginate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium alginate",R.string.E404,"E404",0,1,"https://en.wikipedia.org/wiki/Calcium_alginate"));
        arrayListE_Numbers.add(new E_Numbers("Propane-1,2-diol alginate",R.string.E405,"E405",0,1,"https://en.wikipedia.org/wiki/Propylene_glycol_alginate"));
        arrayListE_Numbers.add(new E_Numbers("Agar",R.string.E406,"E406",0,1,"https://en.wikipedia.org/wiki/Agar"));
        arrayListE_Numbers.add(new E_Numbers("Carrageenan",R.string.E407,"E407",0,1,"https://en.wikipedia.org/wiki/Carrageenan"));
        arrayListE_Numbers.add(new E_Numbers("Processed eucheuma seaweed",R.string.E407a,"E407a",0,1,"https://en.wikipedia.org/wiki/Carrageenan"));
        arrayListE_Numbers.add(new E_Numbers("Bakers yeast glycan",R.string.E408,"E408",0,0,"https://en.wikipedia.org/wiki/Bakers_yeast_glycan"));
        arrayListE_Numbers.add(new E_Numbers("Arabinogalactan",R.string.E409,"E409",0,0,"https://en.wikipedia.org/wiki/Arabinogalactan"));

        arrayListE_Numbers.add(new E_Numbers("Locust bean gum",R.string.E410,"E410",0,1,"https://en.wikipedia.org/wiki/Locust_bean_gum"));
        arrayListE_Numbers.add(new E_Numbers("Oat gum",R.string.E411,"E411",0,0,"https://en.wikipedia.org/wiki/Oat_gum"));
        arrayListE_Numbers.add(new E_Numbers("Guar gum",R.string.E412,"E412",0,1,"https://en.wikipedia.org/wiki/Guar_gum"));
        arrayListE_Numbers.add(new E_Numbers("Tragacanth",R.string.E413,"E413",0,1,"https://en.wikipedia.org/wiki/Tragacanth"));
        arrayListE_Numbers.add(new E_Numbers("Acacia gum",R.string.E414,"E414",0,1,"https://en.wikipedia.org/wiki/Gum_arabic"));
        arrayListE_Numbers.add(new E_Numbers("Xanthan gum",R.string.E415,"E415",0,1,"https://en.wikipedia.org/wiki/Xanthan_gum"));
        arrayListE_Numbers.add(new E_Numbers("Karaya gum",R.string.E416,"E416",0,1,"https://en.wikipedia.org/wiki/Gum_karaya"));
        arrayListE_Numbers.add(new E_Numbers("Tara gum",R.string.E417,"E417",0,1,"https://en.wikipedia.org/wiki/Tara_spinosa"));
        arrayListE_Numbers.add(new E_Numbers("Gellan gum",R.string.E418,"E418",0,1,"https://en.wikipedia.org/wiki/Gellan_gum"));
        arrayListE_Numbers.add(new E_Numbers("Gum ghatti",R.string.E419,"E419",0,0,"https://en.wikipedia.org/wiki/Anogeissus_latifolia"));

        arrayListE_Numbers.add(new E_Numbers("Sorbitol",R.string.E420,"E420",0,1,"https://en.wikipedia.org/wiki/Sorbitol"));
        arrayListE_Numbers.add(new E_Numbers("Mannitol",R.string.E421,"E421",0,1,"https://en.wikipedia.org/wiki/Mannitol"));
        arrayListE_Numbers.add(new E_Numbers("Glycerol",R.string.E422,"E422",0,1,"https://en.wikipedia.org/wiki/Glycerol"));
        arrayListE_Numbers.add(new E_Numbers("Curdlan",R.string.E424,"E424",0,0,"https://en.wikipedia.org/wiki/Curdlan"));
        arrayListE_Numbers.add(new E_Numbers("Konjac",R.string.E425,"E425",0,1,"https://en.wikipedia.org/wiki/Konjac"));
        arrayListE_Numbers.add(new E_Numbers("Soybean hemicellulose",R.string.E426,"E426",0,1,"https://en.wikipedia.org/wiki/Soybean_hemicellulose"));
        arrayListE_Numbers.add(new E_Numbers("Cassia gum",R.string.E427,"E427",0,1,"https://en.wikipedia.org/wiki/Cassia_gum"));
        arrayListE_Numbers.add(new E_Numbers("Peptones",R.string.E429,"E429",0,0,"https://en.wikipedia.org/wiki/Peptide#Peptide_classes"));

        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (8) stearate",R.string.E430,"E430",0,0,"https://en.wikipedia.org/wiki/Polyoxyethene_(8)_stearate"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (40) stearate",R.string.E431,"E431",0,1,"https://en.wikipedia.org/wiki/Polyoxyethene_(40)_stearate"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (20) sorbitan monolaurate",R.string.E432,"E432",0,1,"https://en.wikipedia.org/wiki/Polysorbate_20"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (20) sorbitan monooleate",R.string.E433,"E433",0,1,"https://en.wikipedia.org/wiki/Polysorbate_80"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (20) sorbitan monopalmitate",R.string.E434,"E434",0,1,"https://en.wikipedia.org/wiki/Polyoxyethene_(20)_sorbitan_monopalmitate"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (20) sorbitan monostearate",R.string.E435,"E435",0,1,"https://en.wikipedia.org/wiki/Polyoxyethene_(20)_sorbitan_monostearate"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxyethene (20) sorbitan tristearate",R.string.E436,"E436",0,1,"https://en.wikipedia.org/wiki/Polyoxyethene_(20)_sorbitan_tristearate"));

        arrayListE_Numbers.add(new E_Numbers("Pectins",R.string.E440,"E440",0,1,"https://en.wikipedia.org/wiki/Pectin"));
        arrayListE_Numbers.add(new E_Numbers("Gelatine",R.string.E441,"E441",0,0,"https://en.wikipedia.org/wiki/Gelatin"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium phosphatides",R.string.E442,"E442",0,1,"https://en.wikipedia.org/wiki/Mixed_ammonium_salts_of_phosphorylated_glycerides"));
        arrayListE_Numbers.add(new E_Numbers("Brominated vegetable oil",R.string.E443,"E443",0,2,"https://en.wikipedia.org/wiki/Brominated_vegetable_oil"));
        arrayListE_Numbers.add(new E_Numbers("Sucrose acetate isobutyrate",R.string.E444,"E444",0,1,"https://en.wikipedia.org/wiki/Sucrose_acetate_isobutyrate"));
        arrayListE_Numbers.add(new E_Numbers("Glycerol esters of wood rosins",R.string.E445,"E445",0,1,"https://en.wikipedia.org/wiki/Glycerol_ester_of_wood_rosin"));
        arrayListE_Numbers.add(new E_Numbers("Succistearin",R.string.E446,"E446",0,0,"https://en.wikipedia.org/wiki/Succistearin"));

        arrayListE_Numbers.add(new E_Numbers("Diphosphates",R.string.E450,"E450",0,1,"https://en.wikipedia.org/wiki/Pyrophosphate"));
        arrayListE_Numbers.add(new E_Numbers("Triphosphates",R.string.E451,"E451",0,1,"https://en.wikipedia.org/wiki/Polyphosphate"));
        arrayListE_Numbers.add(new E_Numbers("Polyphosphates",R.string.E452,"E452",0,1,"https://en.wikipedia.org/wiki/Polyphosphate"));
        arrayListE_Numbers.add(new E_Numbers("Beta-cyclodextrin",R.string.E459,"E459",0,1,"https://en.wikipedia.org/wiki/Cyclodextrin"));

        arrayListE_Numbers.add(new E_Numbers("Cellulose",R.string.E460,"E460",0,1,"https://en.wikipedia.org/wiki/Cellulose"));
        arrayListE_Numbers.add(new E_Numbers("Methyl cellulose",R.string.E461,"E461",0,1,"https://en.wikipedia.org/wiki/Methyl_cellulose"));
        arrayListE_Numbers.add(new E_Numbers("Ethyl cellulose",R.string.E462,"E462",0,1,"https://en.wikipedia.org/wiki/Ethyl_cellulose"));
        arrayListE_Numbers.add(new E_Numbers("Hydroxypropyl cellulose",R.string.E463,"E463",0,1,"https://en.wikipedia.org/wiki/Hydroxypropyl_cellulose"));
        arrayListE_Numbers.add(new E_Numbers("Hypromellose",R.string.E464,"E464",0,1,"https://en.wikipedia.org/wiki/Hypromellose"));
        arrayListE_Numbers.add(new E_Numbers("Ethyl methyl cellulose",R.string.E465,"E465",0,1,"https://en.wikipedia.org/wiki/Ethyl_methyl_cellulose"));
        arrayListE_Numbers.add(new E_Numbers("Carboxymethyl cellulose",R.string.E466,"E466",0,1,"https://en.wikipedia.org/wiki/Carboxymethyl_cellulose"));
        arrayListE_Numbers.add(new E_Numbers("Ethyl hydroxyethyl cellulose",R.string.E467,"E467",0,0,"https://en.wikipedia.org/wiki/Ethulose"));
        arrayListE_Numbers.add(new E_Numbers("Crosslinked sodium carboxymethyl cellulose",R.string.E468,"E468",0,1,"https://en.wikipedia.org/wiki/Croscarmellose_sodium"));
        arrayListE_Numbers.add(new E_Numbers("Enzymically hydrolysed carboxymethylcellulose",R.string.E469,"E469",0,1,"https://en.wikipedia.org/wiki/Enzymically_hydrolysed_carboxymethylcellulose"));

        arrayListE_Numbers.add(new E_Numbers("Sodium",R.string.E470a,"E470a",0,1,"https://en.wikipedia.org/wiki/E470a"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium salts of fatty acids",R.string.E470b,"E470b",0,1,"https://en.wikipedia.org/wiki/Magnesium_stearate"));
        arrayListE_Numbers.add(new E_Numbers("Mono- and diglycerides of fatty acids",R.string.E471,"E471",0,1,"https://en.wikipedia.org/wiki/Mono-_and_diglycerides_of_fatty_acids"));
        arrayListE_Numbers.add(new E_Numbers("Acetic acid esters of mono- and diglycerides of fatty acids",R.string.E472a,"E472a",0,1,"https://en.wikipedia.org/wiki/E472a"));
        arrayListE_Numbers.add(new E_Numbers("Lactic acid esters of mono- and diglycerides of fatty acids",R.string.E472b,"E472b",0,1,"https://en.wikipedia.org/wiki/E472b"));
        arrayListE_Numbers.add(new E_Numbers("Citric acid esters of mono- and diglycerides of fatty acids",R.string.E472c,"E472c",0,1,"https://en.wikipedia.org/wiki/E472c"));
        arrayListE_Numbers.add(new E_Numbers("Tartaric acid esters of mono- and diglycerides of fatty acids",R.string.E472d,"E472d",0,1,"https://en.wikipedia.org/wiki/E472d"));
        arrayListE_Numbers.add(new E_Numbers("Mono- and diacetyl tartaric acid esters of mono- and diglycerides of fatty acids",R.string.E472e,"E472e",0,1,"https://en.wikipedia.org/wiki/DATEM"));
        arrayListE_Numbers.add(new E_Numbers("Mixed acetic and tartaric acid esters of mono- and diglycerides of fatty acids",R.string.E472f,"E472f",0,1,"https://en.wikipedia.org/wiki/E472f"));
        arrayListE_Numbers.add(new E_Numbers("Succinylated monoglycerides",R.string.E472g,"E472g",0,0,"https://en.wikipedia.org/wiki/Succinylated_monoglycerides"));
        arrayListE_Numbers.add(new E_Numbers("Sucrose esters of fatty acids",R.string.E473,"E473",0,1,"https://en.wikipedia.org/wiki/E473"));
        arrayListE_Numbers.add(new E_Numbers("Sucroglycerides",R.string.E474,"E474",0,1,"https://en.wikipedia.org/wiki/Sucroglyceride"));
        arrayListE_Numbers.add(new E_Numbers("Polyglycerol esters of fatty acids",R.string.E475,"E475",0,1,"https://en.wikipedia.org/wiki/E475"));
        arrayListE_Numbers.add(new E_Numbers("Polyglycerol polyricinoleate",R.string.E476,"E476",0,1,"https://en.wikipedia.org/wiki/Polyglycerol_polyricinoleate"));
        arrayListE_Numbers.add(new E_Numbers("Propane-1,2-diol esters of fatty acids, propylene glycol esters of fatty acids",R.string.E477,"E477",0,1,"https://en.wikipedia.org/wiki/E477"));
        arrayListE_Numbers.add(new E_Numbers("Lactylated fatty acid esters of glycerol and propane-1",R.string.E478,"E478",0,0,"https://en.wikipedia.org/wiki/E478"));
        arrayListE_Numbers.add(new E_Numbers("Thermally oxidized soya bean oil interacted with mono- and diglycerides of fatty acids",R.string.E479b,"E479b",0,1,"https://en.wikipedia.org/wiki/E479b"));

        arrayListE_Numbers.add(new E_Numbers("Dioctyl sodium sulphosuccinate",R.string.E480,"E480",0,0,"https://en.wikipedia.org/wiki/Docusate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium stearoyl-2-lactylate",R.string.E481,"E481",0,1,"https://en.wikipedia.org/wiki/Sodium_stearoyl_lactylate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium stearoyl-2-lactylate",R.string.E482,"E482",0,1,"https://en.wikipedia.org/wiki/Calcium_stearoyl-2-lactylate"));
        arrayListE_Numbers.add(new E_Numbers("Stearyl tartrate",R.string.E483,"E483",0,1,"https://en.wikipedia.org/wiki/Stearyl_palmityl_tartrate"));
        arrayListE_Numbers.add(new E_Numbers("Stearyl citrate",R.string.E484,"E484",0,0,"https://en.wikipedia.org/wiki/Stearyl_citrate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium stearoyl fumarate",R.string.E485,"E485",0,0,"https://en.wikipedia.org/wiki/Sodium_stearoyl_fumarate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium stearoyl fumarate",R.string.E486,"E486",0,0,"https://en.wikipedia.org/wiki/Calcium_stearoyl_fumarate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium laurylsulphate",R.string.E487,"E487",0,0,"https://en.wikipedia.org/wiki/Sodium_dodecyl_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Ethoxylated Mono- and Di-Glycerides",R.string.E488,"E488",0,0,"https://en.wikipedia.org/wiki/E488"));
        arrayListE_Numbers.add(new E_Numbers("Methyl glucoside-coconut oil ester",R.string.E489,"E489",0,0,"https://en.wikipedia.org/wiki/E489"));

        arrayListE_Numbers.add(new E_Numbers("Propane-1,2-diol",R.string.E490,"E490",0,0,"https://en.wikipedia.org/wiki/Propylene_glycol"));
        arrayListE_Numbers.add(new E_Numbers("Sorbitan monostearate",R.string.E491,"E491",0,1,"https://en.wikipedia.org/wiki/Sorbitan_monostearate"));
        arrayListE_Numbers.add(new E_Numbers("Sorbitan tristearate",R.string.E492,"E492",0,1,"https://en.wikipedia.org/wiki/Sorbitan_tristearate"));
        arrayListE_Numbers.add(new E_Numbers("Sorbitan monolaurate",R.string.E493,"E493",0,1,"https://en.wikipedia.org/wiki/Sorbitan_monolaurate"));
        arrayListE_Numbers.add(new E_Numbers("Sorbitan monooleate",R.string.E494,"E494",0,1,"https://en.wikipedia.org/wiki/Sorbitan_monooleate"));
        arrayListE_Numbers.add(new E_Numbers("Sorbitan monopalmitate",R.string.E495,"E495",0,1,"https://en.wikipedia.org/wiki/Sorbitan_monopalmitate"));
        arrayListE_Numbers.add(new E_Numbers("Sorbitan trioleate",R.string.E496,"E496",0,0,"https://en.wikipedia.org/wiki/Sorbitan_trioleate"));
        arrayListE_Numbers.add(new E_Numbers("Polyoxypropylene-polyoxyethylene polymers",R.string.E497,"E497",0,0,"https://en.wikipedia.org/wiki/E497"));
        arrayListE_Numbers.add(new E_Numbers("Partial polyglycerol esters of polycondensed fatty acids of castor oil",R.string.E498,"E498",0,0,"https://en.wikipedia.org/wiki/E498"));




        arrayListE_Numbers.add(new E_Numbers("Sodium carbonates",R.string.E500,"E500",0,1,"https://en.wikipedia.org/wiki/Carbonate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium carbonates",R.string.E501,"E501",0,1,"https://en.wikipedia.org/wiki/Potassium_carbonate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium carbonates",R.string.E503,"E503",0,1,"https://en.wikipedia.org/wiki/Ammonium_carbonate"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium carbonates",R.string.E504,"E504",0,1,"https://en.wikipedia.org/wiki/Magnesium_carbonate"));
        arrayListE_Numbers.add(new E_Numbers("Ferrous carbonate",R.string.E505,"E505",0,0,"https://en.wikipedia.org/wiki/Siderite"));
        arrayListE_Numbers.add(new E_Numbers("Hydrochloric acid",R.string.E507,"E507",0,1,"https://en.wikipedia.org/wiki/Hydrochloric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Potassium chloride",R.string.E508,"E508",0,1,"https://en.wikipedia.org/wiki/Potassium_chloride"));
        arrayListE_Numbers.add(new E_Numbers("Calcium chloride",R.string.E509,"E509",0,1,"https://en.wikipedia.org/wiki/Calcium_chloride"));

        arrayListE_Numbers.add(new E_Numbers("Ammonium chloride",R.string.E510,"E510",0,0,"https://en.wikipedia.org/wiki/Ammonium_chloride"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium chloride",R.string.E511,"E511",0,1,"https://en.wikipedia.org/wiki/Magnesium_chloride"));
        arrayListE_Numbers.add(new E_Numbers("Stannous chloride",R.string.E512,"E512",0,1,"https://en.wikipedia.org/wiki/Tin(II)_chloride"));
        arrayListE_Numbers.add(new E_Numbers("Sulphuric acid",R.string.E513,"E513",0,1,"https://en.wikipedia.org/wiki/Sulfuric_acid"));
        arrayListE_Numbers.add(new E_Numbers("Sodium sulphates",R.string.E514,"E514",0,1,"https://en.wikipedia.org/wiki/Sodium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium Sulphates",R.string.E515,"E515",0,1,"https://en.wikipedia.org/wiki/Potassium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium sulphate",R.string.E516,"E516",0,1,"https://en.wikipedia.org/wiki/Calcium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium sulphate",R.string.E517,"E517",0,1,"https://en.wikipedia.org/wiki/Ammonium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium sulphate",R.string.E518,"E518",0,0,"https://en.wikipedia.org/wiki/Magnesium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Copper(II) sulphate",R.string.E519,"E519",0,0,"https://en.wikipedia.org/wiki/Copper(II)_sulfate"));

        arrayListE_Numbers.add(new E_Numbers("Aluminium sulphate",R.string.E520,"E520",0,1,"https://en.wikipedia.org/wiki/Aluminium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Aluminium sodium sulphate",R.string.E521,"E521",0,1,"https://en.wikipedia.org/wiki/Sodium_aluminium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Aluminium potassium sulphate",R.string.E522,"E522",0,1,"https://en.wikipedia.org/wiki/Potassium_alum"));
        arrayListE_Numbers.add(new E_Numbers("Aluminium ammonium sulphate",R.string.E523,"E523",0,1,"https://en.wikipedia.org/wiki/Ammonium_aluminium_sulfate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium hydroxide",R.string.E524,"E524",0,1,"https://en.wikipedia.org/wiki/Sodium_hydroxide"));
        arrayListE_Numbers.add(new E_Numbers("Potassium hydroxide",R.string.E525,"E525",0,1,"https://en.wikipedia.org/wiki/Potassium_hydroxide"));
        arrayListE_Numbers.add(new E_Numbers("Calcium hydroxide",R.string.E526,"E526",0,1,"https://en.wikipedia.org/wiki/Calcium_hydroxide"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium hydroxide",R.string.E527,"E527",0,1,"https://en.wikipedia.org/wiki/Ammonia_solution"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium hydroxide",R.string.E528,"E528",0,1,"https://en.wikipedia.org/wiki/Magnesium_hydroxide"));
        arrayListE_Numbers.add(new E_Numbers("Calcium oxide",R.string.E529,"E529",0,1,"https://en.wikipedia.org/wiki/Calcium_oxide"));

        arrayListE_Numbers.add(new E_Numbers("Magnesium oxide",R.string.E530,"E530",0,1,"https://en.wikipedia.org/wiki/Magnesium_oxide"));
        arrayListE_Numbers.add(new E_Numbers("Sodium ferrocyanide",R.string.E535,"E535",0,1,"https://en.wikipedia.org/wiki/Sodium_ferrocyanide"));
        arrayListE_Numbers.add(new E_Numbers("Potassium ferrocyanide",R.string.E536,"E536",0,1,"https://en.wikipedia.org/wiki/Potassium_ferrocyanide"));
        arrayListE_Numbers.add(new E_Numbers("Ferrous hexacyanomanganate",R.string.E537,"E537",0,0,"https://en.wikipedia.org/wiki/Ferrous_hexacyanomanganate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium ferrocyanide",R.string.E538,"E538",0,1,"https://en.wikipedia.org/wiki/Calcium_ferrocyanide"));
        arrayListE_Numbers.add(new E_Numbers("Sodium thiosulphate",R.string.E539,"E539",0,0,"https://en.wikipedia.org/wiki/Sodium_thiosulfate"));

        arrayListE_Numbers.add(new E_Numbers("Dicalcium diphosphate",R.string.E540,"E540",0,0,"https://en.wikipedia.org/wiki/Calcium_pyrophosphate"));
        arrayListE_Numbers.add(new E_Numbers("Sodium aluminium phosphate",R.string.E541,"E541",0,1,"https://en.wikipedia.org/wiki/Sodium_aluminium_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Bone phosphate",R.string.E542,"E542",0,0,"https://en.wikipedia.org/wiki/Bone_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium sodium polyphosphate",R.string.E543,"E543",0,0,"https://en.wikipedia.org/wiki/Calcium_sodium_polyphosphate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium polyphosphate",R.string.E544,"E544",0,0,"https://en.wikipedia.org/wiki/Calcium_polyphosphate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium polyphosphate",R.string.E545,"E545",0,0,"https://en.wikipedia.org/wiki/Ammonium_polyphosphate"));

        arrayListE_Numbers.add(new E_Numbers("Sodium Silicates",R.string.E550,"E550",0,0,"https://en.wikipedia.org/wiki/Sodium_silicate"));
        arrayListE_Numbers.add(new E_Numbers("Silicon dioxide",R.string.E551,"E551",0,1,"https://en.wikipedia.org/wiki/Silicon_dioxide"));
        arrayListE_Numbers.add(new E_Numbers("Calcium silicate",R.string.E552,"E552",0,1,"https://en.wikipedia.org/wiki/Calcium_silicate"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium silicate",R.string.E553a,"E553a",0,1,"https://en.wikipedia.org/wiki/Talc"));
        arrayListE_Numbers.add(new E_Numbers("Talc",R.string.E553b,"E553b",0,1,"https://en.wikipedia.org/wiki/Talc"));
        arrayListE_Numbers.add(new E_Numbers("Sodium aluminosilicate",R.string.E554,"E554",0,1,"https://en.wikipedia.org/wiki/Sodium_aluminosilicate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium aluminium silicate",R.string.E555,"E555",0,1,"https://en.wikipedia.org/wiki/Microcline"));
        arrayListE_Numbers.add(new E_Numbers("Calcium aluminosilicate",R.string.E556,"E556",0,1,"https://en.wikipedia.org/wiki/Calcium_aluminosilicate"));
        arrayListE_Numbers.add(new E_Numbers("Zinc silicate",R.string.E557,"E557",0,0,"https://en.wikipedia.org/wiki/Hemimorphite"));
        arrayListE_Numbers.add(new E_Numbers("Bentonite",R.string.E558,"E558",0,0,"https://en.wikipedia.org/wiki/Bentonite"));
        arrayListE_Numbers.add(new E_Numbers("Aluminium silicate",R.string.E559,"E559",0,1,"https://en.wikipedia.org/wiki/Aluminium_silicate"));

        arrayListE_Numbers.add(new E_Numbers("Potassium silicate",R.string.E560,"E560",0,0,"https://en.wikipedia.org/wiki/Potassium_silicate"));
        arrayListE_Numbers.add(new E_Numbers("Vermiculite",R.string.E561,"E561",0,0,"https://en.wikipedia.org/wiki/Vermiculite"));
        arrayListE_Numbers.add(new E_Numbers("Sepiolite",R.string.E562,"E562",0,0,"https://en.wikipedia.org/wiki/Sepiolite"));
        arrayListE_Numbers.add(new E_Numbers("Sepiolitic clay",R.string.E563,"E563",0,0,"https://en.wikipedia.org/wiki/Sepiolitic_clay"));
        arrayListE_Numbers.add(new E_Numbers("Lignosulphonates",R.string.E565,"E565",0,0,"https://en.wikipedia.org/wiki/Lignosulfonates"));
        arrayListE_Numbers.add(new E_Numbers("Natrolite-phonolite",R.string.E566,"E566",0,0,"https://en.wikipedia.org/wiki/Natrolite-phonolite"));

        arrayListE_Numbers.add(new E_Numbers("Fatty acids",R.string.E570,"E570",0,1,"https://en.wikipedia.org/wiki/Fatty_acid"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium stearate",R.string.E572,"E572",0,0,"https://en.wikipedia.org/wiki/Magnesium_stearate"));
        arrayListE_Numbers.add(new E_Numbers("Gluconic acid",R.string.E574,"E574",0,1,"https://en.wikipedia.org/wiki/Gluconic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Glucono delta-lactone",R.string.E575,"E575",0,1,"https://en.wikipedia.org/wiki/Glucono_delta-lactone"));
        arrayListE_Numbers.add(new E_Numbers("Sodium gluconate",R.string.E576,"E576",0,1,"https://en.wikipedia.org/wiki/Sodium_gluconate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium gluconate",R.string.E577,"E577",0,1,"https://en.wikipedia.org/wiki/Potassium_gluconate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium gluconate",R.string.E578,"E578",0,1,"https://en.wikipedia.org/wiki/Calcium_gluconate"));
        arrayListE_Numbers.add(new E_Numbers("Ferrous gluconate",R.string.E579,"E579",0,1,"https://en.wikipedia.org/wiki/Iron(II)_gluconate"));

        arrayListE_Numbers.add(new E_Numbers("Magnesium gluconate",R.string.E580,"E580",0,0,"https://en.wikipedia.org/wiki/Magnesium_gluconate"));
        arrayListE_Numbers.add(new E_Numbers("Ferrous lactate",R.string.E585,"E585",0,1,"https://en.wikipedia.org/wiki/Iron(II)_lactate"));
        arrayListE_Numbers.add(new E_Numbers("4-Hexylresorcinol",R.string.E586,"E586",0,1,"https://en.wikipedia.org/wiki/Hexylresorcinol"));

        arrayListE_Numbers.add(new E_Numbers("Synthetic calcium aluminates",R.string.E598,"E598",0,0,"https://en.wikipedia.org/wiki/Calcium_aluminates"));
        arrayListE_Numbers.add(new E_Numbers("Perlite",R.string.E599,"E599",0,0,"https://en.wikipedia.org/wiki/Perlite"));


        arrayListE_Numbers.add(new E_Numbers("Glutamic acid",R.string.E620,"E620",0,1,"https://en.wikipedia.org/wiki/Glutamic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Monosodium glutamate (MSG)",R.string.E621,"E621",0,1,"https://en.wikipedia.org/wiki/Monosodium_glutamate"));
        arrayListE_Numbers.add(new E_Numbers("Monopotassium glutamate",R.string.E622,"E622",0,1,"https://en.wikipedia.org/wiki/Monopotassium_glutamate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium diglutamate",R.string.E623,"E623",0,1,"https://en.wikipedia.org/wiki/Calcium_diglutamate"));
        arrayListE_Numbers.add(new E_Numbers("Monoammonium glutamate",R.string.E624,"E624",0,1,"https://en.wikipedia.org/wiki/Monoammonium_glutamate"));
        arrayListE_Numbers.add(new E_Numbers("Magnesium diglutamate",R.string.E625,"E625",0,1,"https://en.wikipedia.org/wiki/Magnesium_diglutamate"));
        arrayListE_Numbers.add(new E_Numbers("Guanylic acid",R.string.E626,"E626",0,1,"https://en.wikipedia.org/wiki/Guanosine_monophosphate"));
        arrayListE_Numbers.add(new E_Numbers("Disodium guanylate",R.string.E627,"E627",0,1,"https://en.wikipedia.org/wiki/Disodium_guanylate"));
        arrayListE_Numbers.add(new E_Numbers("Dipotassium guanylate",R.string.E628,"E628",0,1,"https://en.wikipedia.org/wiki/Dipotassium_guanylate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium guanylate",R.string.E629,"E629",0,1,"https://en.wikipedia.org/wiki/Calcium_guanylate"));

        arrayListE_Numbers.add(new E_Numbers("Inosinic acid",R.string.E630,"E630",0,1,"https://en.wikipedia.org/wiki/Inosinic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Disodium inosinate",R.string.E631,"E631",0,1,"https://en.wikipedia.org/wiki/Disodium_inosinate"));
        arrayListE_Numbers.add(new E_Numbers("Dipotassium inosinate",R.string.E632,"E632",0,1,"https://en.wikipedia.org/wiki/Dipotassium_inosinate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium inosinate",R.string.E633,"E633",0,1,"https://en.wikipedia.org/wiki/Calcium_inosinate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium 5'-ribonucleotides",R.string.E634,"E634",0,1,"https://en.wikipedia.org/wiki/Calcium_5%27-ribonucleotide"));
        arrayListE_Numbers.add(new E_Numbers("Disodium 5'-ribonucleotides",R.string.E635,"E635",0,1,"https://en.wikipedia.org/wiki/Disodium_ribonucleotides"));

        arrayListE_Numbers.add(new E_Numbers("Glycine",R.string.E640,"E640",0,1,"https://en.wikipedia.org/wiki/Glycine"));

        arrayListE_Numbers.add(new E_Numbers("Zinc acetate",R.string.E650,"E650",0,1,"https://en.wikipedia.org/wiki/Zinc_acetate"));





        arrayListE_Numbers.add(new E_Numbers("Tetracyclines",R.string.E701,"E701",0,0,"https://en.wikipedia.org/wiki/Tetracycline_antibiotics"));
        arrayListE_Numbers.add(new E_Numbers("Chlortetracycline",R.string.E702,"E702",0,0,"https://en.wikipedia.org/wiki/Chlortetracycline"));
        arrayListE_Numbers.add(new E_Numbers("Oxytetracycline",R.string.E703,"E703",0,0,"https://en.wikipedia.org/wiki/Oxytetracycline"));
        arrayListE_Numbers.add(new E_Numbers("Oleandomycin",R.string.E704,"E704",0,0,"https://en.wikipedia.org/wiki/Oleandomycin"));
        arrayListE_Numbers.add(new E_Numbers("Penicillin G potassium",R.string.E705,"E705",0,0,"https://en.wikipedia.org/wiki/Penicillin_G_potassium"));
        arrayListE_Numbers.add(new E_Numbers("Penicillin G sodium",R.string.E706,"E706",0,0,"https://en.wikipedia.org/wiki/Benzylpenicillin"));
        arrayListE_Numbers.add(new E_Numbers("Penicillin G procaine",R.string.E707,"E707",0,0,"https://en.wikipedia.org/wiki/Penicillin_G_procaine"));
        arrayListE_Numbers.add(new E_Numbers("Penicillin G benzathine",R.string.E708,"E708",0,0,"https://en.wikipedia.org/wiki/Benzathine_benzylpenicillin"));

        arrayListE_Numbers.add(new E_Numbers("Spiramycins",R.string.E710,"E710",0,0,"https://en.wikipedia.org/wiki/Spiramycin"));
        arrayListE_Numbers.add(new E_Numbers("Virginiamycins",R.string.E711,"E711",0,0,"https://en.wikipedia.org/wiki/Virginiamycin"));
        arrayListE_Numbers.add(new E_Numbers("Flavomycin",R.string.E712,"E712",0,0,"https://en.wikipedia.org/wiki/Bambermycin"));
        arrayListE_Numbers.add(new E_Numbers("Tylosin",R.string.E713,"E713",0,0,"https://en.wikipedia.org/wiki/Tylosin"));
        arrayListE_Numbers.add(new E_Numbers("Monensin A",R.string.E714,"E714",0,0,"https://en.wikipedia.org/wiki/Monensin"));
        arrayListE_Numbers.add(new E_Numbers("Avoparcin",R.string.E715,"E715",0,0,"https://en.wikipedia.org/wiki/Avoparcin"));
        arrayListE_Numbers.add(new E_Numbers("Salinomycin",R.string.E716,"E716",0,0,"https://en.wikipedia.org/wiki/Salinomycin"));
        arrayListE_Numbers.add(new E_Numbers("Avilamycin",R.string.E717,"E717",0,0,"https://en.wikipedia.org/wiki/Avilamycin"));




        arrayListE_Numbers.add(new E_Numbers("Dimethyl polysiloxane",R.string.E900,"E900",0,1,"https://en.wikipedia.org/wiki/Polydimethylsiloxane"));
        arrayListE_Numbers.add(new E_Numbers("Beeswax",R.string.E901,"E901",0,1,"https://en.wikipedia.org/wiki/Beeswax"));
        arrayListE_Numbers.add(new E_Numbers("Candelilla wax",R.string.E902,"E902",0,1,"https://en.wikipedia.org/wiki/Candelilla_wax"));
        arrayListE_Numbers.add(new E_Numbers("Carnauba wax",R.string.E903,"E903",0,1,"https://en.wikipedia.org/wiki/Carnauba_wax"));
        arrayListE_Numbers.add(new E_Numbers("Shellac",R.string.E904,"E904",0,1,"https://en.wikipedia.org/wiki/Shellac"));
        arrayListE_Numbers.add(new E_Numbers("Paraffins",R.string.E905,"E905",0,1,"https://en.wikipedia.org/wiki/Paraffin_wax"));
        arrayListE_Numbers.add(new E_Numbers("Mineral oil",R.string.E905a,"E905a",0,0,"https://en.wikipedia.org/wiki/Mineral_oil"));
        arrayListE_Numbers.add(new E_Numbers("Petrolatum",R.string.E905b,"E905b",0,0,"https://en.wikipedia.org/wiki/Petroleum_jelly"));
        arrayListE_Numbers.add(new E_Numbers("Petroleum wax",R.string.E905c,"E905c",0,0,"https://en.wikipedia.org/wiki/Paraffin_wax"));
        arrayListE_Numbers.add(new E_Numbers("Gum benzoic",R.string.E906,"E906",0,0,"https://en.wikipedia.org/wiki/Benzoin_(resin)"));
        arrayListE_Numbers.add(new E_Numbers("Crystalline wax",R.string.E907,"E907",0,1,"https://en.wikipedia.org/wiki/Paraffin_wax"));
        arrayListE_Numbers.add(new E_Numbers("Rice bran wax",R.string.E908,"E908",0,0,"https://en.wikipedia.org/wiki/Rice_bran_wax"));
        arrayListE_Numbers.add(new E_Numbers("Spermaceti wax",R.string.E909,"E909",0,0,"https://en.wikipedia.org/wiki/Spermaceti"));

        arrayListE_Numbers.add(new E_Numbers("Wax esters",R.string.E910,"E910",0,0,"https://en.wikipedia.org/wiki/Wax_ester"));
        arrayListE_Numbers.add(new E_Numbers("Methyl esters of fatty acids",R.string.E911,"E911",0,0,"https://en.wikipedia.org/wiki/Fatty_acid_methyl_ester"));
        arrayListE_Numbers.add(new E_Numbers("Montanic acid esters",R.string.E912,"E912",0,1,"https://en.wikipedia.org/wiki/Montanic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Lanolin",R.string.E913,"E913",0,0,"https://en.wikipedia.org/wiki/Lanolin"));
        arrayListE_Numbers.add(new E_Numbers("Oxidized polyethylene wax",R.string.E914,"E914",0,1,"https://en.wikipedia.org/wiki/Oxidized_polyethylene_wax"));
        arrayListE_Numbers.add(new E_Numbers("Esters of colophony",R.string.E915,"E915",0,0,"https://en.wikipedia.org/wiki/Rosin"));
        arrayListE_Numbers.add(new E_Numbers("Calcium iodate",R.string.E916,"E916",0,0,"https://en.wikipedia.org/wiki/Calcium_iodate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium iodate",R.string.E917,"E917",0,0,"https://en.wikipedia.org/wiki/Potassium_iodate"));
        arrayListE_Numbers.add(new E_Numbers("Nitrogen oxides",R.string.E918,"E918",0,0,"https://en.wikipedia.org/wiki/Nitrogen_oxide"));
        arrayListE_Numbers.add(new E_Numbers("Nitrosyl chloride",R.string.E919,"E919",0,0,"https://en.wikipedia.org/wiki/Nitrosyl_chloride"));

        arrayListE_Numbers.add(new E_Numbers("L-cysteine",R.string.E920,"E920",0,1,"https://en.wikipedia.org/wiki/Cysteine"));
        arrayListE_Numbers.add(new E_Numbers("L-cystine",R.string.E921,"E921",0,0,"https://en.wikipedia.org/wiki/Cystine"));
        arrayListE_Numbers.add(new E_Numbers("Potassium persulphate",R.string.E922,"E922",0,0,"https://en.wikipedia.org/wiki/Potassium_persulfate"));
        arrayListE_Numbers.add(new E_Numbers("Ammonium persulphate",R.string.E923,"E923",0,0,"https://en.wikipedia.org/wiki/Ammonium_persulfate"));
        arrayListE_Numbers.add(new E_Numbers("Potassium bromate",R.string.E924,"E924",0,2,"https://en.wikipedia.org/wiki/Potassium_bromate"));
        arrayListE_Numbers.add(new E_Numbers("Calcium bromate",R.string.E924b,"E924b",0,0,"https://en.wikipedia.org/wiki/Calcium_bromate"));
        arrayListE_Numbers.add(new E_Numbers("Chlorine",R.string.E925,"E925",0,0,"https://en.wikipedia.org/wiki/Chlorine"));
        arrayListE_Numbers.add(new E_Numbers("Chlorine dioxide",R.string.E926,"E926",0,0,"https://en.wikipedia.org/wiki/Chlorine_dioxide"));
        arrayListE_Numbers.add(new E_Numbers("Azodicarbonamide",R.string.E927a,"E927a",0,0,"https://en.wikipedia.org/wiki/Azodicarbonamide"));
        arrayListE_Numbers.add(new E_Numbers("Carbamide (Urea)",R.string.E927b,"E927b",0,1,"https://en.wikipedia.org/wiki/Urea"));
        arrayListE_Numbers.add(new E_Numbers("Benzoyl peroxide",R.string.E928,"E928",0,0,"https://en.wikipedia.org/wiki/Benzoyl_peroxide"));
        arrayListE_Numbers.add(new E_Numbers("Acetone peroxide",R.string.E929,"E929",0,0,"https://en.wikipedia.org/wiki/Acetone_peroxide"));

        arrayListE_Numbers.add(new E_Numbers("Calcium peroxide",R.string.E930,"E930",0,0,"https://en.wikipedia.org/wiki/Calcium_peroxide"));
        arrayListE_Numbers.add(new E_Numbers("Argon",R.string.E938,"E938",0,1,"https://en.wikipedia.org/wiki/Argon"));
        arrayListE_Numbers.add(new E_Numbers("Helium",R.string.E939,"E939",0,1,"https://en.wikipedia.org/wiki/Helium"));

        arrayListE_Numbers.add(new E_Numbers("Dichlorodifluoromethane",R.string.E940,"E940",0,0,"https://en.wikipedia.org/wiki/Dichlorodifluoromethane"));
        arrayListE_Numbers.add(new E_Numbers("Nitrogen",R.string.E941,"E941",0,1,"https://en.wikipedia.org/wiki/Nitrogen"));
        arrayListE_Numbers.add(new E_Numbers("Nitrous oxide",R.string.E942,"E942",0,1,"https://en.wikipedia.org/wiki/Nitrous_oxide"));
        arrayListE_Numbers.add(new E_Numbers("Butane",R.string.E943a,"E943a",0,1,"https://en.wikipedia.org/wiki/Butane"));
        arrayListE_Numbers.add(new E_Numbers("Isobutane",R.string.E943b,"E943b",0,1,"https://en.wikipedia.org/wiki/Isobutane"));
        arrayListE_Numbers.add(new E_Numbers("Propane",R.string.E944,"E944",0,1,"https://en.wikipedia.org/wiki/Propane"));
        arrayListE_Numbers.add(new E_Numbers("Chloropentafluoroethane",R.string.E945,"E945",0,0,"https://en.wikipedia.org/wiki/Chloropentafluoroethane"));
        arrayListE_Numbers.add(new E_Numbers("Octafluorocyclobutane",R.string.E946,"E946",0,0,"https://en.wikipedia.org/wiki/Octafluorocyclobutane"));
        arrayListE_Numbers.add(new E_Numbers("Oxygen",R.string.E948,"E948",0,1,"https://en.wikipedia.org/wiki/Oxygen"));
        arrayListE_Numbers.add(new E_Numbers("Hydrogen",R.string.E949,"E949",0,1,"https://en.wikipedia.org/wiki/Hydrogen"));

        arrayListE_Numbers.add(new E_Numbers("Acesulfame potassium",R.string.E950,"E950",0,1,"https://en.wikipedia.org/wiki/Acesulfame_potassium"));
        arrayListE_Numbers.add(new E_Numbers("Aspartame",R.string.E951,"E951",0,1,"https://en.wikipedia.org/wiki/Aspartame"));
        arrayListE_Numbers.add(new E_Numbers("Cyclamate",R.string.E952,"E952",0,1,"https://en.wikipedia.org/wiki/Sodium_cyclamate"));
        arrayListE_Numbers.add(new E_Numbers("Isomalt, Isomaltitol",R.string.E953,"E953",0,1,"https://en.wikipedia.org/wiki/Isomalt"));
        arrayListE_Numbers.add(new E_Numbers("Saccharin",R.string.E954,"E954",0,1,"https://en.wikipedia.org/wiki/Saccharin"));
        arrayListE_Numbers.add(new E_Numbers("Sucralose",R.string.E955,"E955",0,1,"https://en.wikipedia.org/wiki/Sucralose"));
        arrayListE_Numbers.add(new E_Numbers("Alitame",R.string.E956,"E956",0,0,"https://en.wikipedia.org/wiki/Alitame"));
        arrayListE_Numbers.add(new E_Numbers("Thaumatin",R.string.E957,"E957",0,1,"https://en.wikipedia.org/wiki/Thaumatin"));
        arrayListE_Numbers.add(new E_Numbers("Glycyrrhizin",R.string.E958,"E958",0,0,"https://en.wikipedia.org/wiki/Glycyrrhizin"));
        arrayListE_Numbers.add(new E_Numbers("Neohesperidine dihydrochalcone",R.string.E959,"E959",0,1,"https://en.wikipedia.org/wiki/Neohesperidin_dihydrochalcone"));

        arrayListE_Numbers.add(new E_Numbers("Steviol glycosides",R.string.E960,"E960",0,1,"https://en.wikipedia.org/wiki/Steviol_glycoside"));
        arrayListE_Numbers.add(new E_Numbers("Neotame",R.string.E961,"E961",0,1,"https://en.wikipedia.org/wiki/Neotame"));
        arrayListE_Numbers.add(new E_Numbers("Aspartame-acesulfame salt",R.string.E962,"E962",0,1,"https://en.wikipedia.org/wiki/Aspartame-acesulfame_salt"));
        arrayListE_Numbers.add(new E_Numbers("Maltitol",R.string.E965,"E965",0,1,"https://en.wikipedia.org/wiki/Maltitol"));
        arrayListE_Numbers.add(new E_Numbers("Lactitol",R.string.E966,"E966",0,1,"https://en.wikipedia.org/wiki/Lactitol"));
        arrayListE_Numbers.add(new E_Numbers("Xylitol",R.string.E967,"E967",0,1,"https://en.wikipedia.org/wiki/Xylitol"));
        arrayListE_Numbers.add(new E_Numbers("Erythritol",R.string.E968,"E968",0,1,"https://en.wikipedia.org/wiki/Erythritol"));

        arrayListE_Numbers.add(new E_Numbers("Quillaia extract",R.string.E999,"E999",0,1,"https://en.wikipedia.org/wiki/Quillaia"));



        arrayListE_Numbers.add(new E_Numbers("Cholic acid",R.string.E1000,"E1000",1,0,"https://en.wikipedia.org/wiki/Cholic_acid"));
        arrayListE_Numbers.add(new E_Numbers("Choline salts",R.string.E1001,"E1001",1,0,"https://en.wikipedia.org/wiki/Choline"));

        arrayListE_Numbers.add(new E_Numbers("Amylase",R.string.E1100,"E1100",0,0,"https://en.wikipedia.org/wiki/Amylase"));
        arrayListE_Numbers.add(new E_Numbers("Proteases",R.string.E1101,"E1101",1,0,"https://en.wikipedia.org/wiki/Protease"));
        arrayListE_Numbers.add(new E_Numbers("Glucose oxidase",R.string.E1102,"E1102",0,0,"https://en.wikipedia.org/wiki/Glucose_oxidase"));
        arrayListE_Numbers.add(new E_Numbers("Invertase",R.string.E1103,"E1103",0,1,"https://en.wikipedia.org/wiki/Invertase"));
        arrayListE_Numbers.add(new E_Numbers("Lipases",R.string.E1104,"E1104",0,0,"https://en.wikipedia.org/wiki/Lipase"));
        arrayListE_Numbers.add(new E_Numbers("Lysozyme",R.string.E1105,"E1105",0,0,"https://en.wikipedia.org/wiki/Lysozyme"));

        arrayListE_Numbers.add(new E_Numbers("Polydextrose",R.string.E1200,"E1200",0,1,"https://en.wikipedia.org/wiki/Polydextrose"));
        arrayListE_Numbers.add(new E_Numbers("Polyvinylpyrrolidone",R.string.E1201,"E1201",0,1,"https://en.wikipedia.org/wiki/Polyvinylpyrrolidone"));
        arrayListE_Numbers.add(new E_Numbers("Polyvinylpolypyrrolidone",R.string.E1202,"E1202",0,1,"https://en.wikipedia.org/wiki/Polyvinylpolypyrrolidone"));
        arrayListE_Numbers.add(new E_Numbers("Polyvinyl alcohol",R.string.E1203,"E1203",0,1,"https://en.wikipedia.org/wiki/Polyvinyl_alcohol"));
        arrayListE_Numbers.add(new E_Numbers("Pullulan",R.string.E1204,"E1204",0,1,"https://en.wikipedia.org/wiki/Pullulan"));

        arrayListE_Numbers.add(new E_Numbers("Dextrin",R.string.E1400,"E1400",0,0,"https://en.wikipedia.org/wiki/Dextrin"));
        arrayListE_Numbers.add(new E_Numbers("Modified starch",R.string.E1401,"E1401",0,0,"https://en.wikipedia.org/wiki/Modified_starch"));
        arrayListE_Numbers.add(new E_Numbers("Alkaline modified starch",R.string.E1402,"E1402",0,0,"https://en.wikipedia.org/wiki/Alkaline_modified_starch"));
        arrayListE_Numbers.add(new E_Numbers("Bleached starch",R.string.E1403,"E1403",0,0,"https://en.wikipedia.org/wiki/Bleached_starch"));
        arrayListE_Numbers.add(new E_Numbers("Oxidized starch",R.string.E1404,"E1404",0,1,"https://en.wikipedia.org/wiki/Oxidized_starch"));
        arrayListE_Numbers.add(new E_Numbers("Enzyme treated starch",R.string.E1405,"E1405",0,0,"https://en.wikipedia.org/wiki/Enzyme_treated_starch"));

        arrayListE_Numbers.add(new E_Numbers("Monostarch phosphate",R.string.E1410,"E1410",0,1,"https://en.wikipedia.org/wiki/Monostarch_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Distarch glycerol",R.string.E1411,"E1411",0,0,"https://en.wikipedia.org/wiki/Distarch_glycerol"));
        arrayListE_Numbers.add(new E_Numbers("Distarch phosphate",R.string.E1412,"E1412",0,1,"https://en.wikipedia.org/wiki/Distarch_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Phosphated distarch phosphate",R.string.E1413,"E1413",0,1,"https://en.wikipedia.org/wiki/Phosphated_distarch_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Acetylated distarch phosphate",R.string.E1414,"E1414",0,1,"https://en.wikipedia.org/wiki/Acetylated_distarch_phosphate"));

        arrayListE_Numbers.add(new E_Numbers("Starch acetate esterified with acetic anhydride ",R.string.E1420,"E1420",0,1,"https://en.wikipedia.org/wiki/E1420"));
        arrayListE_Numbers.add(new E_Numbers("Starch acetate esterified with vinyl acetate ",R.string.E1421,"E1421",0,0,"https://en.wikipedia.org/wiki/E1421"));
        arrayListE_Numbers.add(new E_Numbers("Acetylated distarch adipate",R.string.E1422,"E1422",0,1,"https://en.wikipedia.org/wiki/Acetylated_distarch_adipate"));
        arrayListE_Numbers.add(new E_Numbers("Acetylated distarch glycerol",R.string.E1423,"E1423",0,0,"https://en.wikipedia.org/wiki/Acetylated_distarch_glycerol"));

        arrayListE_Numbers.add(new E_Numbers("Distarch glycerine",R.string.E1430,"E1430",0,0,"https://en.wikipedia.org/wiki/Distarch_glycerine"));

        arrayListE_Numbers.add(new E_Numbers("Hydroxy propyl starch",R.string.E1440,"E1440",0,1,"https://en.wikipedia.org/wiki/Hydroxy_propyl_starch"));
        arrayListE_Numbers.add(new E_Numbers("Hydroxy propyl distarch glycerine",R.string.E1441,"E1441",0,0,"https://en.wikipedia.org/wiki/Hydroxy_propyl_distarch_glycerine"));
        arrayListE_Numbers.add(new E_Numbers("Hydroxy propyl distarch phosphate",R.string.E1442,"E1442",0,1,"https://en.wikipedia.org/wiki/Hydroxypropyl_distarch_phosphate"));
        arrayListE_Numbers.add(new E_Numbers("Hydroxy propyl distarch glycerol",R.string.E1443,"E1443",0,0,"https://en.wikipedia.org/wiki/Hydroxy_propyl_distarch_glycerol"));

        arrayListE_Numbers.add(new E_Numbers("Starch sodium octenyl succinate",R.string.E1450,"E1450",0,1,"https://en.wikipedia.org/wiki/Starch_sodium_octenyl_succinate"));
        arrayListE_Numbers.add(new E_Numbers("Acetylated oxidised starch",R.string.E1451,"E1451",0,1,"https://en.wikipedia.org/wiki/Acetylated_oxidised_starch"));
        arrayListE_Numbers.add(new E_Numbers("Starch aluminium octenyl succinate",R.string.E1452,"E1452",0,1,"https://en.wikipedia.org/wiki/Starch_aluminium_octenyl_succinate"));

        arrayListE_Numbers.add(new E_Numbers("Benzylated hydrocarbons",R.string.E1501,"E1501",0,0,"https://en.wikipedia.org/wiki/Benzylated_hydrocarbons"));
        arrayListE_Numbers.add(new E_Numbers("Butane-1, 3-diol",R.string.E1502,"E1502",0,0,"https://en.wikipedia.org/wiki/1,3-Butanediol"));
        arrayListE_Numbers.add(new E_Numbers("Castor oil",R.string.E1503,"E1503",0,0,"https://en.wikipedia.org/wiki/Castor_oil"));
        arrayListE_Numbers.add(new E_Numbers("Ethyl acetate",R.string.E1504,"E1504",0,0,"https://en.wikipedia.org/wiki/Ethyl_acetate"));
        arrayListE_Numbers.add(new E_Numbers("Triethyl citrate",R.string.E1505,"E1505",0,1,"https://en.wikipedia.org/wiki/Triethyl_citrate"));

        arrayListE_Numbers.add(new E_Numbers("Ethanol",R.string.E1510,"E1510",0,0,"https://en.wikipedia.org/wiki/Ethanol"));
        arrayListE_Numbers.add(new E_Numbers("Glyceryl monoacetate",R.string.E1516,"E1516",0,0,"https://en.wikipedia.org/wiki/Glyceryl_monoacetate"));
        arrayListE_Numbers.add(new E_Numbers("Glyceryl diacetate or diacetin",R.string.E1517,"E1517",0,1,"https://en.wikipedia.org/wiki/Glyceryl_diacetate"));
        arrayListE_Numbers.add(new E_Numbers("Glyceryl triacetate or triacetin",R.string.E1518,"E1518",0,1,"https://en.wikipedia.org/wiki/Triacetin"));
        arrayListE_Numbers.add(new E_Numbers("Benzyl alcohol",R.string.E1519,"E1519",0,1,"https://en.wikipedia.org/wiki/Benzyl_alcohol"));

        arrayListE_Numbers.add(new E_Numbers("Propylene glycol",R.string.E1520,"E1520",0,1,"https://en.wikipedia.org/wiki/Propylene_glycol"));
        arrayListE_Numbers.add(new E_Numbers("Polyethylene glycol 8000",R.string.E1521,"E1521",0,1,"https://en.wikipedia.org/wiki/Polyethylene_glycol"));
        arrayListE_Numbers.add(new E_Numbers("Hydroxyethyl cellulose",R.string.E1525,"E1525",0,0,"https://en.wikipedia.org/wiki/Hydroxyethyl_cellulose"));
        return arrayListE_Numbers;
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
    public class E_Numbers implements Comparable{
        private String id;
        private String name;
        private final int US;
        private final int EU;

        private final String url;
        private String information;

        E_Numbers(String Name, int information, String id, int US, int EU, String url) {
            this.id = id;
            name = Name;
            this.US = US;
            this.EU = EU;

            this.information = context.getString(information);

            this.url = url;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getUS() {
            return US;
        }

        public int getEU() {
            return EU;
        }

        public String getUrl() {
            return url;
        }

        public String getInformation() {
            return information;
        }

        @Override
        public int compareTo(@NonNull Object o) {
            E_Numbers o1 = (E_Numbers) o;
            String number = getId().replaceAll("\\D+","");
            String number2 = o1.getId().replaceAll("\\D+","");
            if(number.length()!=number2.length()){
                return number.length()-number2.length();

            }
            return getId().compareToIgnoreCase(o1.getId());
        }
    }
}
