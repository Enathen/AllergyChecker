package creativeendlessgrowingceg.allergychecker;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Enathen on 2018-01-12.
 */

public class LanguagesAccepted {
    private static final LanguagesAccepted ourInstance = new LanguagesAccepted();

    public static LanguagesAccepted getInstance() {
        return ourInstance;
    }
    public ArrayList<Locale> getLanguages(){
        ArrayList<Locale> arrayList = new ArrayList<>();
        arrayList.add(new Locale("sv"));
        arrayList.add(new Locale("en"));
        arrayList.add(new Locale("es"));
        return arrayList;
    }
    public int getFlag(String langCode) {
        if (langCode.equals("sv")) {
            return R.drawable.se;
        }
        if (langCode.equals("en")) {
            return R.drawable.gb;
        }
        if (langCode.equals("es")) {
            return R.drawable.es;
        }
        return 0;

    }
}
