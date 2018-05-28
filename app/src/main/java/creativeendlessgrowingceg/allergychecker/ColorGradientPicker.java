package creativeendlessgrowingceg.allergychecker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;

import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.*;
import static java.lang.Math.abs;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-07
 */

public class ColorGradientPicker {

    @SuppressLint("ResourceType")
    public int ColorGradientPickerPick(int amount, int currentPos, Context context) {
        @SuppressLint("ResourceType")
        String color = context.getResources().getString(getPrimaryColorNotColorInt(context));
        color = color.substring(3);
        int primaryR = Integer.valueOf(color.substring(0, 2), 16);
        int primaryG = Integer.valueOf(color.substring(2, 4), 16);
        int primaryB = Integer.valueOf(color.substring(4, 6), 16);
        color = context.getResources().getString(getPrimaryLightColorNotColorInt(context));
        color = color.substring(3);
        int primaryLightR = Integer.valueOf(color.substring(0, 2), 16);
        int primaryLightG = Integer.valueOf(color.substring(2, 4), 16);
        int primaryLightB = Integer.valueOf(color.substring(4, 6), 16);



        return Color.rgb(calcColor(primaryLightR, primaryR, amount , currentPos),
                    calcColor(primaryLightG, primaryG, amount, currentPos),
                    calcColor(primaryLightB, primaryB, amount, currentPos));



    }

    private int calcColor(int color1, int color2, int amount, int currentPos) {
        int newColorAbs = abs((color1 - color2)/ amount);

        if (color1 > color2) {
            color1 = color1-(newColorAbs*currentPos);
        } else {
            color1 = color1+(newColorAbs*currentPos);
        }
        return color1;
    }
}
