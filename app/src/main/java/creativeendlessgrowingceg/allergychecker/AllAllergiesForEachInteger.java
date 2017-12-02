package creativeendlessgrowingceg.allergychecker;

/**
 * Created by Enathen on 2017-12-01.
 */

public class AllAllergiesForEachInteger {
    private final String language;
    private final String nameOfIngredient;
    private final int id;
    private String nameOfWordFound;

    public AllAllergiesForEachInteger(String language, String nameOfIngredient, int ID){

        this.language = language;
        this.nameOfIngredient = nameOfIngredient;

        id = ID;
    }

    public String getNameOfWordFound() {
        return nameOfWordFound;
    }

    public void setNameOfWordFound(String nameOfWordFound) {
        this.nameOfWordFound = nameOfWordFound;
    }

    public String getLanguage() {
        return language;
    }

    public String getNameOfIngredient() {
        return nameOfIngredient;
    }

    public int getId() {
        return id;
    }
}
