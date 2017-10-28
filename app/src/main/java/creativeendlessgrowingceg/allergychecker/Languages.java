package creativeendlessgrowingceg.allergychecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Enathen on 2017-10-27.
 */

public class Languages {
    private final Context context;
    private ArrayList<LanguagesClass> arrayListLanguage = new ArrayList<>();
    private ArrayList<LanguagesClass> staticArrayListLanguage = new ArrayList<>();
    public Languages(Context context){
        this.context = context;
        setArrayListLanguage();
        setStaticArrayListLanguage();
        sort(arrayListLanguage);
        sort(staticArrayListLanguage);
    }
    public ArrayList<LanguagesClass> getArrayListLanguage(){
        return arrayListLanguage;
    }
    public ArrayList<LanguagesClass> getstaticArrayListLanguage(){
        return staticArrayListLanguage;
    }
    public void setArrayListLanguage() {
        arrayListLanguage.add(new LanguagesClass(R.drawable.gb,context.getString(R.string.english),R.string.english,new Locale("en")));
        arrayListLanguage.add(new LanguagesClass(R.drawable.se,context.getString(R.string.swedish),R.string.swedish,new Locale("sv")));
    }
    public void setStaticArrayListLanguage(){
        staticArrayListLanguage.add(new LanguagesClass(R.drawable.gb,context.getString(R.string.staticEnglish),R.string.staticEnglish,new Locale("en")));
        staticArrayListLanguage.add(new LanguagesClass(R.drawable.se,context.getString(R.string.staticSwedish),R.string.staticSwedish,new Locale("sv")));
    }
    private void sort(ArrayList<Languages.LanguagesClass> arrayList){
        Collections.sort(arrayList, new Comparator<Languages.LanguagesClass>() {
            @Override
            public int compare(Languages.LanguagesClass pic1, Languages.LanguagesClass pic2) {
                return pic1.language.compareToIgnoreCase(pic2.language);
            }
        });
    }
    public class LanguagesClass {
        int picture;
        String language;
        int id;
        Locale locale;
        boolean on;
        public LanguagesClass(int picture, String language,int id,Locale locale){
            this.picture = picture;
            this.id = id;
            this.language = language;
            this.locale = locale;
        }
        public void setOn(boolean on){
            this.on = on;
        }
    }
}
