package creativeendlessgrowingceg.allergychecker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static java.lang.Math.abs;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-07
 */

public class ColorGradientPicker {
    @SuppressLint("ResourceType")
    public int ColorGradientPickerPick(int amount, int currentPos) {
        amount = amount+1;
        String color = "#594168";
        Integer primaryR = Integer.valueOf(color.substring(1, 3), 16);
        Integer primaryG = Integer.valueOf(color.substring(3, 5), 16);
        Integer primaryB = Integer.valueOf(color.substring(5, 7), 16);
        Log.d(TAG, "Primary: "+ primaryR + " : " + primaryG + " : " + primaryB);
        color = "#3ccac8";
        Integer primaryLightR = Integer.valueOf(color.substring(1, 3), 16);
        Integer primaryLightG = Integer.valueOf(color.substring(3, 5), 16);
        Integer primaryLightB = Integer.valueOf(color.substring(5, 7), 16);
        Log.d(TAG, "PrimaryLight: "+ primaryLightR + " : " + primaryLightG + " : " + primaryLightB);
        color = "#5293cc";
        Integer accentR = Integer.valueOf(color.substring(1, 3), 16);
        Integer accentG = Integer.valueOf(color.substring(3, 5), 16);
        Integer accentB = Integer.valueOf(color.substring(5, 7), 16);
        Log.d(TAG, "accent: "+ accentR + " : " + accentG + " : " + accentB);
        if (amount / 2 >= currentPos) {
            return Color.rgb(calcColor(primaryLightR, accentR, amount / 2, currentPos),
                    calcColor(primaryLightG, accentG, amount / 2, currentPos),
                    calcColor(primaryLightB, accentB, amount / 2, currentPos));

        }else{
            Log.d(TAG, "ColorGradientPickerPick: "+ " HÄÄÄR");
            return Color.rgb(calcColor(accentR, primaryR, amount, currentPos),
                    calcColor(accentG, primaryG, amount, currentPos),
                    calcColor(accentB, primaryB, amount, currentPos));
        }

    }

    private int calcColor(Integer primaryLightR, Integer accentR, int i, int currentPos) {
        int newColorR = primaryLightR;
        int colorR = abs((newColorR - accentR)/ (i-1));
        if (primaryLightR > accentR) {
            Log.d(TAG, "calcColor: "+newColorR);
            newColorR = primaryLightR-(colorR*currentPos-1);
            Log.d(TAG, "calcColor: "+newColorR);
        } else {
            Log.d(TAG, "calcColor: "+newColorR);
            Log.d(TAG, "calcColor: "+ (currentPos-1));
            newColorR = primaryLightR+(colorR*currentPos-1);
            Log.d(TAG, "calcColor: "+newColorR);
        }
        Log.d(TAG, "calcColor: swag");
        return newColorR;
    }
}
