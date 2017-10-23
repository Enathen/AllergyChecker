package creativeendlessgrowingceg.allergychecker;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Enathen on 2017-10-19.
 */

public class SpellCheckAllergy {
    private static final String TAG = "SpellCheckAllergy";
    HashMap<String,ArrayList<String>> hashMap = new HashMap<>();
    char[] englishAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public SpellCheckAllergy(){

    }
    public void convertString(){

        for (String key : hashMap.keySet()) {
            Log.d(TAG,"Key:"+key);
            ArrayList<String> arrayListNew = new ArrayList<>();
            for (String s : hashMap.get(key)) {
                Log.d(TAG,"stringToParse"+s);
                arrayListNew = AlgorithmString(s,arrayListNew);
            }
            if(arrayListNew != null){
                hashMap.get(key).clear();
                hashMap.put(key,arrayListNew);
            }
        }

        for (String key : hashMap.keySet()) {
            //Log.d(TAG,"Key: " + key);
            for (String string : hashMap.get(key)) {
                //Log.d(TAG,string);
            }
        }
        Log.d(TAG,"Total walue"+String.valueOf(hashMap.keySet().size()));
    }

    private ArrayList<String> AlgorithmString(String s, ArrayList<String> arrayListNew) {
        s = s.replaceAll("[^\\p{L}\\p{Nd}\\s]+", "");
        s= s.toLowerCase();
        StringBuilder string;
        for (int i = 0; i < s.length()+1; i++) {
            if(i!=s.length()) {
                string = new StringBuilder(s);
                string = string.deleteCharAt(i);
                if(string.length()>1){
                    arrayListNew.add(String.valueOf(string));
                    Log.d(TAG, String.valueOf(string));
                }
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
                //Log.d(TAG,"TEST Replace Char:"+String.valueOf(string));
                if(i!=s.length()){
                    string = new StringBuilder(s);
                    string.setCharAt(i, c);
                    arrayListNew.add(String.valueOf(string));
                    //Log.d(TAG,"TestSetCharAt"+String.valueOf(string));
                }
            }
        }
        arrayListNew.add(s);
        Log.d(TAG, String.valueOf(arrayListNew.size()));
        return arrayListNew;
    }
    public HashMap<String,ArrayList<String>>  permuteString(String string){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(string);
        this.hashMap.put(string,arrayList);
        convertString();
        return this.hashMap;
    }
    public HashMap<String,ArrayList<String>> permuteString(HashMap<String,ArrayList<String>> hashMap){
        this.hashMap = hashMap;
        /*for (String s : hashMap.keySet()) {
            this.hashMap.put(s, hashMap.get(s));
            Log.d(TAG, s);
        }*/
        convertString();
        return this.hashMap;
    }
    public String removeSuffixes(String string){

        return string;
    }



}
