/*package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

*//**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *//*
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "test";
    HistoryFragment historyFragment = new HistoryFragment();
    private Context appContext;
    private AllergyList allergyList;
    private ArrayList<Locale> listOfLanguages;

    @Before
    public void setUp(){
        appContext = InstrumentationRegistry.getTargetContext();
        listOfLanguages = new ArrayList<>();
        listOfLanguages.add(new Locale("es"));
        listOfLanguages.add(new Locale("en"));
        listOfLanguages.add(new Locale("sv"));

        allergyList = new AllergyList(appContext);
    }
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.


        //assertEquals("creativeendlessgrowingceg.allergychecker", appContext.getPackageName());
    }
    @Test
    public void allergyList(){
        ArrayList<ArrayList<AllergyList.PictureIngredient>> arrayListNuts = allergyList.getMyAllergies();
        HelpCalcAllergy helpCalcAllergy = new HelpCalcAllergy();
        HashSet<String> all = new HashSet<>();
        for (ArrayList<AllergyList.PictureIngredient> arrayListNut : arrayListNuts) {
            for (AllergyList.PictureIngredient pictureIngredient : arrayListNut) {
                for (Locale listOfLanguage : listOfLanguages) {
                    all.add(helpCalcAllergy.getStringByLocal(appContext,pictureIngredient.id,listOfLanguage.getLanguage()));

                }

            }
        }
    }
}*/
