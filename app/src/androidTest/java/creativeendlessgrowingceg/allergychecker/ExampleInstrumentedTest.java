package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "test";
    SpellCheckAllergy spellCheckAllergy = new SpellCheckAllergy();
    HistoryFragment historyFragment = new HistoryFragment();
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("creativeendlessgrowingceg.allergychecker", appContext.getPackageName());
    }
    @Test
    public void DateChecker(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        Date date = calendar.getTime();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(formattedDate);
        formattedDate = "2017-11-03 14:41:12";
        arrayList.add(formattedDate);
        formattedDate = "2015-11-03 14:41:13";
        arrayList.add(formattedDate);
        formattedDate = "2018-11-03 14:41:42";
        arrayList.add(formattedDate);
        formattedDate = "2017-10-17 14:41:10";
        arrayList.add(formattedDate);
        formattedDate = "2017-11-01 14:41:16";
        arrayList.add(formattedDate);
        formattedDate = "2017-12-01 14:41:09";
        arrayList.add(formattedDate);
        formattedDate = "2017-11-03 14:41:21";
        arrayList.add(formattedDate);
        for (String s : arrayList) {
            Log.d(TAG,s.substring(19));
        }
        Collections.sort(arrayList,new HistoryFragment.stringComparator());
        Collections.reverse(arrayList);
        for (String s : arrayList) {
            Log.d(TAG,s.substring(19));
        }
    }
    @Test
    public void DateCHecker(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Log.d(TAG, DateFormat.getDateInstance(DateFormat.SHORT).format(date));
        Log.d(TAG, DateFormat.getTimeInstance(DateFormat.SHORT).format(date));
    }
}
