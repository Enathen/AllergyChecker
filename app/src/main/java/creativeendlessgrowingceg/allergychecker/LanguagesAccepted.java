package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Enathen on 2018-01-12.
 */

class LanguagesAccepted {
    private static final LanguagesAccepted ourInstance = new LanguagesAccepted();

    public static LanguagesAccepted getInstance() {
        return ourInstance;
    }

    public static ArrayList<Locale> getLanguages() {
        ArrayList<Locale> arrayList = new ArrayList<>();
        arrayList.add(new Locale("sv"));
        arrayList.add(new Locale("en"));
        arrayList.add(new Locale("es"));
        sort(arrayList);
        return arrayList;
    }

    public static int getFlag(String langCode) {
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
    public static int getCountryName(String langCode) {
        if (langCode.equals("sv")) {
            return R.string.swedish;
        }
        if (langCode.equals("en")) {
            return R.string.english;
        }
        if (langCode.equals("es")) {
            return R.string.spanish;
        }
        return 0;
    }
    private static void sort(ArrayList<Locale> arrayList) {
        Collections.sort(arrayList, new Comparator<Locale>() {
            @Override
            public int compare(Locale locale, Locale locale2) {
                return locale.getLanguage().compareToIgnoreCase(locale2.getLanguage());
            }
        });
    }
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Activity context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));


        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase().replaceAll("\\s+", "");
    }

}
