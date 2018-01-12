package creativeendlessgrowingceg.allergychecker;

import java.util.Locale;

/**
 * Created by Enathen on 2017-10-27.
 */



public class LanguagesClass {
    int picture;
    String language;
    int id;
    Locale locale;
    boolean on;
    public LanguagesClass(int picture, String language, int id, Locale locale) {
        this.picture = picture;
        this.id = id;
        this.language = language;
        this.locale = locale;
    }


    public void setOn(boolean on) {
        this.on = on;
    }
}

