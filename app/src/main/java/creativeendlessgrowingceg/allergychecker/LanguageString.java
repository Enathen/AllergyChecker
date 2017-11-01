package creativeendlessgrowingceg.allergychecker;

import java.io.Serializable;

/**
 * Created by Enathen on 2017-10-24.
 */

public class LanguageString implements Serializable{
    public boolean on = false;
    public int id;
    public LanguageString(boolean on, int id){

        this.on = on;
        this.id = id;
    }

}
