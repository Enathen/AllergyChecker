package creativeendlessgrowingceg.allergychecker;

import java.util.HashSet;

/**
 * Created by Enathen on 2017-11-01.
 */

public class LangString {
    String language;
    String string;
    boolean on;
    int id;
    int found;
    HashSet<String> allPossibleDerivationsOfAllergen;
    public LangString(String language, String string, boolean on, int id) {
        this.language = language;
        this.string = string;
        this.on = on;
        this.id = id;

    }
    public void addallPossibleDerivationsOfAllergen(HashSet<String> allPossibleDerivationsOfAllergen){
        this.allPossibleDerivationsOfAllergen = allPossibleDerivationsOfAllergen;
    }
}