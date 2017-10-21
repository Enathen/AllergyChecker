package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    SpellCheckAllergy spellCheckAllergy = new SpellCheckAllergy();
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("creativeendlessgrowingceg.allergychecker", appContext.getPackageName());
    }
    @Test
    public void Spellchecker(){
        ArrayList<String> arrayList = spellCheckAllergy.permuteAString("dog");
        Collections.sort(arrayList);
        System.out.println(arrayList.get(0));
    }
}
