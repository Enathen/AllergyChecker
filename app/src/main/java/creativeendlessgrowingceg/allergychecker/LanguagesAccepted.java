package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * handles all languages and flags.
 *
 * @author Jonathan Alexander Norberg
 * @version 2018-01-12
 */

class LanguagesAccepted {
    private static final LanguagesAccepted ourInstance = new LanguagesAccepted();

    public static LanguagesAccepted getInstance() {
        return ourInstance;
    }

    /**
     * @param context to help sort
     * @return array list with allergies
     */
    public static ArrayList<Locale> getLanguages(Context context) {
        ArrayList<Locale> arrayList = new ArrayList<>();
        arrayList.add(new Locale("sv"));
        arrayList.add(new Locale("en"));
        arrayList.add(new Locale("es"));
        arrayList.add(new Locale("da"));
        arrayList.add(new Locale("nb"));
        arrayList.add(new Locale("fi"));
        arrayList.add(new Locale("de"));
        arrayList.add(new Locale("fr"));
        arrayList.add(new Locale("nl"));
        arrayList.add(new Locale("it"));
        arrayList.add(new Locale("pt"));
        arrayList.add(new Locale("pl"));

        sort(arrayList, context);
        return arrayList;
    }

    /**
     * get flags desired
     *
     * @param langCode to check
     * @return desired flag if exist
     */
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
        if (langCode.equals("nb")) {
            return R.drawable.no;
        }
        if (langCode.equals("da")) {
            return R.drawable.da;
        }
        if (langCode.equals("fi")) {
            return R.drawable.fi;
        }
        if (langCode.equals("de")) {
            return R.drawable.de;
        }
        if (langCode.equals("fr")) {
            return R.drawable.fr;
        }
        if (langCode.equals("nl")) {
            return R.drawable.nl;
        }
        if (langCode.equals("it")) {
            return R.drawable.it;
        }
        if (langCode.equals("pl")) {
            return R.drawable.pl;
        }
        if (langCode.equals("pt")) {
            return R.drawable.pt;
        }
        return 0;
    }

    /**
     * get name of country
     *
     * @param langCode to check
     * @return desired country name
     */
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
        if (langCode.equals("nb")) {
            return R.string.norway;
        }
        if (langCode.equals("da")) {
            return R.string.denmark;
        }
        if (langCode.equals("fi")) {
            return R.string.finnish;
        }
        if (langCode.equals("de")) {
            return R.string.german;
        }
        if (langCode.equals("fr")) {
            return R.string.french;
        }
        if (langCode.equals("nl")) {
            return R.string.netherlands;
        }
        if (langCode.equals("it")) {
            return R.string.italian;
        }
        if (langCode.equals("pt")) {
            return R.string.portuguese;
        }
        if (langCode.equals("pl")) {
            return R.string.polish;
        }
        return 0;
    }

    /**
     * get static name.
     *
     * @param langCode to get
     * @return static name
     */
    public static int getCountryNameStatic(String langCode) {
        if (langCode.equals("sv")) {
            return R.string.staticSwedish;
        }
        if (langCode.equals("en")) {
            return R.string.staticEnglish;
        }
        if (langCode.equals("es")) {
            return R.string.staticSpanish;
        }
        if (langCode.equals("nb")) {
            return R.string.staticNorway;
        }
        if (langCode.equals("da")) {
            return R.string.staticDenmark;
        }
        if (langCode.equals("fi")) {
            return R.string.staticFinnish;
        }
        if (langCode.equals("de")) {
            return R.string.staticGerman;
        }
        if (langCode.equals("fr")) {
            return R.string.staticFrench;
        }
        if (langCode.equals("nl")) {
            return R.string.staticNetherland;
        }
        if (langCode.equals("it")) {
            return R.string.staticItalian;
        }
        if (langCode.equals("pt")) {
            return R.string.staticPortuguese;
        }
        if (langCode.equals("pl")) {
            return R.string.staticPolish;
        }
        return 0;
    }


    private static void sort(ArrayList<Locale> arrayList, final Context context) {
        Collections.sort(arrayList, new Comparator<Locale>() {
            @Override
            public int compare(Locale locale, Locale locale2) {
                return context.getString(getCountryName(locale.getLanguage())).compareToIgnoreCase(context.getString(getCountryName(locale2.getLanguage())));
            }
        });
    }



    public static String getStringByLocalNoTakeAwaySpace(Activity context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));

        return context.createConfigurationContext(configuration).getResources().getString(id);
    }

}
