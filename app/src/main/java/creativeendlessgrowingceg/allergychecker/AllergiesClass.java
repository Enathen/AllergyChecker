package creativeendlessgrowingceg.allergychecker;

import android.support.annotation.NonNull;

/**
 *
 * Helper class for keeping all allergies
 *
 * @author Jonathan Alexander Norberg
 * @version 2017-12-01
 */

public class AllergiesClass implements Comparable<AllergiesClass> {
    private final String language;
    private final String nameOfIngredient;
    private final int id;
    private String motherLanguage;
    private String nameOfWordFound;

    public AllergiesClass(String language, String nameOfIngredient, int ID, String motherLanguage) {

        this.language = language;
        this.nameOfIngredient = nameOfIngredient;

        id = ID;
        this.motherLanguage = motherLanguage;
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

    public String getMotherLanguage() {
        return motherLanguage;
    }


    @Override
    public int compareTo(@NonNull AllergiesClass o) {
        return motherLanguage.compareToIgnoreCase(o.motherLanguage);
    }
}
