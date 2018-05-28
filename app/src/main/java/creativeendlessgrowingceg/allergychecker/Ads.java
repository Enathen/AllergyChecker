package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import static android.content.ContentValues.TAG;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-28
 */

public class Ads {
    public static String MOBILE_AD_STRING = "ca-app-pub-3607354849437438~1697911164";
    public static String INTERSTITIAL = "ca-app-pub-3607354849437438/2589028805";
    //public static String INTERSTITIAL_TEST = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd mInterstitialAd;
    private Context context;

    public Ads(Context context) {
        this.context = context;
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(INTERSTITIAL);
    }

    public static void startAds(Context context) {
        MobileAds.initialize(context, MOBILE_AD_STRING);
    }

    public void showAds() {


        int anInt = PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.interstitial, 0);
        if (anInt > 6) {
            Log.d(TAG, "showAds: " + anInt);
            loadAds(mInterstitialAd);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();

                PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(APISharedPreference.interstitial, 0).apply();
            }
        } else {
            Log.d(TAG, "showAds1: " + anInt);
            anInt++;
            Log.d(TAG, "showAds2: " + anInt);
            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(APISharedPreference.interstitial, anInt).apply();
        }
    }

    private void loadAds(InterstitialAd mInterstitialAd) {
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("85566EDEF434C46837B6373FFB555990").build());
    }
}
