package creativeendlessgrowingceg.allergychecker;

import android.util.Log;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Enathen on 2017-10-19.
 */

public class SpellCheckAllergy {
    private static final String TAG = "SpellCheckAllergy";
    ArrayList<String> arrayList = new ArrayList<>();
    char[] englishAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    ArrayList<String> arrayListNew = new ArrayList<>();
    public SpellCheckAllergy(){

    }
    public void convertString(){
        for (String s : arrayList) {
            Log.d(TAG,s);
            AlgorithmString(s);
        }
        //Collections.sort(arrayListNew);
        arrayList.clear();
        arrayList.addAll(arrayListNew);

        for (String s : arrayList) {
            Log.d(TAG,s);
        }
        Log.d(TAG,"SWAG" + String.valueOf(arrayList.size()));
    }

    private void AlgorithmString(String s) {
        StringBuilder string;
        for (int i = 0; i < s.length()+1; i++) {
            if(i!=s.length()) {
                string = new StringBuilder(s);
                arrayListNew.add(String.valueOf(string.deleteCharAt(i)));
                //Log.d(TAG, String.valueOf(string));
                if(i!=s.length()-1) {
                    string = new StringBuilder(s);
                    char replace = string.charAt(i);

                    string.setCharAt(i,string.charAt(i+1));
                    string.setCharAt(i+1,replace);
                    arrayListNew.add(String.valueOf(string));
                    //Log.d(TAG, String.valueOf(string));
                }
            }
            for (char c : englishAlphabet) {
                string = new StringBuilder(s);
                arrayListNew.add(String.valueOf(string.replace(i,i, String.valueOf(c))));
                //Log.d(TAG,String.valueOf(string));
                if(i!=s.length()){
                    string = new StringBuilder(s);
                    string.setCharAt(i, c);
                    arrayListNew.add(String.valueOf(string));
                    //Log.d(TAG,String.valueOf(string));
                }
            }
        }
        ListIterator<String> iterator = arrayListNew.listIterator();
        while (iterator.hasNext())
        {
            iterator.set(iterator.next().toLowerCase());
        }
        arrayListNew.add(s);
        Log.d(TAG, String.valueOf(arrayListNew.size()));
    }
    public ArrayList<String> permuteString(String string){
        arrayList.add(string);
        convertString();
        return arrayList;
    }
    public ArrayList<String> permuteString(ArrayList<String> arrayList){
        this.arrayList.addAll(arrayList);
        convertString();
        return this.arrayList;
    }



}
