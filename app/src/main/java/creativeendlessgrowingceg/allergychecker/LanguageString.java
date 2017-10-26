package creativeendlessgrowingceg.allergychecker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Enathen on 2017-10-24.
 */

public class LanguageString implements Serializable{
    public String language;
    public String allergyName;
    public ArrayList<String> allPossibleDerivationsOfAllergen = new ArrayList<>();
    public int found = 0;
    public boolean on = false;
    public int id;
    public LanguageString(String language, String allergyName,boolean on, int id){
        this.language = language;
        this.allergyName = allergyName;
        this.on = on;
        this.id = id;
    }
    public void addallPossibleDerivationsOfAllergen(ArrayList<String> allPossibleDerivationsOfAllergen){
        this.allPossibleDerivationsOfAllergen = allPossibleDerivationsOfAllergen;
    }

}
