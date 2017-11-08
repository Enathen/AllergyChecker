package creativeendlessgrowingceg.allergychecker;

import java.util.HashSet;

/**
 * Created by Enathen on 2017-11-01.
 */

public class LangString {
    String language;

    boolean on;
    int id;
    int found;
    HashSet<String> allPossibleDerivationsOfAllergen;
    public LangString(String language, boolean on, int id) {
        this.language = language;

        this.on = on;
        this.id = id;

    }
    public LangString(String language, boolean on, int id,HashSet<String> allPossibleDerivationsOfAllergen) {
        this.language = language;

        this.on = on;
        this.id = id;

        this.allPossibleDerivationsOfAllergen = allPossibleDerivationsOfAllergen;
    }
    public void addallPossibleDerivationsOfAllergen(HashSet<String> allPossibleDerivationsOfAllergen){
        this.allPossibleDerivationsOfAllergen = allPossibleDerivationsOfAllergen;
    }
}