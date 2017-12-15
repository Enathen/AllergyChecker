package creativeendlessgrowingceg.allergychecker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Enathen on 2017-10-24.
 */

public class LanguageString implements Serializable {
    public boolean on = false;
    public int id;
    public int mainAllergyCat;
    public HashMap<String, HashSet<String>> allPossibleWords;

    public LanguageString(boolean on, int id, int mainAllergyCat) {
        this.mainAllergyCat = mainAllergyCat;
        this.on = on;
        this.id = id;
        allPossibleWords = new HashMap<>();
    }


}
