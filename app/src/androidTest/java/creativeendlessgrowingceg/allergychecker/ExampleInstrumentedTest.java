package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;
import java.util.TreeSet;

import static android.content.ContentValues.TAG;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private Context appContext;
    private AlgorithmAllergies algorithmAllergies;
    private TreeMap<Integer, ArrayList<Integer>> myAllergies;
    private int i;
    HashMap<String, AllergiesClass> allergies = new HashMap<>();

    @Before
    public void setUp(){
        appContext = InstrumentationRegistry.getTargetContext();
        algorithmAllergies = new AlgorithmAllergies();
        AllergyList allergyList = new AllergyList(appContext);
        myAllergies = allergyList.getMyAllergies();

        ArrayList<Locale> languages = new ArrayList<>();
        languages.add(new Locale("en"));
        i = 0;
        for (ArrayList<Integer> ingredients : myAllergies.values()) {
            for (Integer ingredient : ingredients) {
                i = algorithmAllergies.translateAllAllergies(ingredient.getId(), allergies, languages, appContext);
            }
        }
        assertNotNull(allergies);
    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.


        //assertEquals("creativeendlessgrowingceg.allergychecker", appContext.getPackageName());
    }
    @Test
    public void allergyListShouldNotFind(){

        TreeSet<String> split = new TreeSet<>();
        String grapefruit = "lmn grp gra lem pomegr ancho gar ca3";
        split.addAll(Arrays.asList(grapefruit.split("\\s")));
        for (String s : split) {
            //Log.d(TAG, "allergyList: "+ s+ " : " + split.size());

        }
       // Log.d(TAG, "allergyList: "+ allergies);
        for (String string : split) {
            String s = TextHandler.FixText(string);
            TreeMap<Integer, TreeSet<String>> integerTreeSetTreeMap = algorithmAllergies.FixStringAllStrings(s.split("\\s+"));

            ArrayList<AllergiesClass> allergiesClasses = algorithmAllergies.bkTree(i, integerTreeSetTreeMap);
            printAllergiesFound(allergiesClasses);

        }
        //assertEquals(1,allergiesClasses.size());


    }
    private void printAllergiesFound(ArrayList<AllergiesClass> allergiesClasses){
        for (AllergiesClass allergiesClass : allergiesClasses) {
            Log.d(TAG, "allergyList: "+ allergiesClass.getNameOfWordFound() + " : " + allergiesClass.getNameOfIngredient()+ " DISTANCE: " + allergiesClass.getDistance());
        }
    }
}
