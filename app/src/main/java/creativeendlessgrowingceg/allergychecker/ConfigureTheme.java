package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-26
 */

public class ConfigureTheme {
    final static int defaultTheme = 0;
    final static int fireTheme = 1;
    final static int plainTheme = 2;

    public static int getCurrentTheme(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return R.style.AppThemeFire;
        } else if (anInt == plainTheme) {
            return R.style.AppThemePlain;
        }

        return R.style.AppTheme;

    }

    public static int getCheckBoxColor(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return context.getColor(R.color.colorCheckBoxColorFire);
        }else if (anInt == plainTheme) {
            return context.getColor(R.color.colorCheckBoxColorPlain);
        }

        return context.getColor(R.color.colorCheckBoxColor);
    }

    public static int getPrimaryColor(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return context.getColor(R.color.colorPrimaryFire);
        }else if (anInt == plainTheme) {
            return context.getColor(R.color.colorPrimaryPlain);
        }
        return context.getColor(R.color.colorPrimary);
    }
    public static int getPrimaryColorNotColorInt(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return R.color.colorPrimaryFire;
        }else if (anInt == plainTheme) {
            return R.color.colorPrimaryPlain;
        }
        return R.color.colorPrimary;
    }
    public static int getPrimaryLightColorNotColorInt(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return R.color.colorPrimaryLightFire;
        }else if (anInt == plainTheme) {
            return R.color.colorPrimaryLightPlain;
        }
        return R.color.colorPrimaryLight;
    }
    public static int getPrimaryLightColor(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return context.getColor(R.color.colorPrimaryLightFire);
        }else if (anInt == plainTheme) {
            return context.getColor(R.color.colorPrimaryLightPlain);
        }
        return context.getColor(R.color.colorPrimaryLight);
    }

    public static int getAccentColor(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return context.getColor(R.color.colorAccentFire);
        }else if (anInt == plainTheme) {
            return context.getColor(R.color.colorAccentPlain);
        }

        return context.getColor(R.color.colorAccent);
    }

    public static int getFontColor(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return context.getColor(R.color.colorFontFire);
        }else if (anInt == plainTheme) {
            return context.getColor(R.color.colorFontPlain);
        }
        return context.getColor(R.color.colorFont);
    }

    public static void setGradient(Context context, ConstraintLayout view) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            view.setBackground(context.getDrawable(R.drawable.gradient_fire));
            return;
        }else if (anInt == plainTheme) {
            view.setBackground(context.getDrawable(R.drawable.gradient_plain));
            return ;
        }
        view.setBackground(context.getDrawable(R.drawable.gradient));
    }

    public static Drawable getGradient(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            return context.getDrawable(R.drawable.gradient_fire);
        }else if (anInt == plainTheme) {
            return context.getDrawable(R.drawable.gradient_plain);
        }
        return context.getDrawable(R.drawable.gradient);
    }

    public static Drawable getSpecificGradient(Context context, int i) {
        if (i == fireTheme) {
            return context.getDrawable(R.drawable.gradient_fire);
        }else if (i == plainTheme) {
            return context.getDrawable(R.drawable.gradient_plain);
        }
        return context.getDrawable(R.drawable.gradient);
    }

    public static void setBottomColor(Context context, BottomNavigationView view) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {
            ColorStateList colorStateList = context.getColorStateList(R.color.bottom_fire);
            view.setItemIconTintList(colorStateList);
            view.setItemTextColor(colorStateList);
            return;
        }else if (anInt == plainTheme) {
            ColorStateList colorStateList = context.getColorStateList(R.color.bottom_plain);
            view.setItemIconTintList(colorStateList);
            view.setItemTextColor(colorStateList);
            return;
        }
        ColorStateList colorStateList = context.getColorStateList(R.color.bottom);
        view.setItemIconTintList(colorStateList);
        view.setItemTextColor(colorStateList);
    }

    public static int getSpinnerDropDown(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {

            return R.layout.spinner_dropdown_items_fire;
        }else if (anInt == plainTheme) {

            return R.layout.spinner_dropdown_items_plain;
        }

        return R.layout.spinner_dropdown_item;
    }

    public static int getSpinnerLayout(Context context) {
        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.theme, 1);
        if (anInt == fireTheme) {

            return R.layout.spinner_item;
        }else if (anInt == plainTheme) {

            return R.layout.spinner_item_plain;
        }

        return R.layout.spinner_item;
    }
}
