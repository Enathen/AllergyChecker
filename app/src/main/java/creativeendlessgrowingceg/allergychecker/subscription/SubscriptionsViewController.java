package creativeendlessgrowingceg.allergychecker.subscription;

import android.support.annotation.DrawableRes;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;

import java.util.List;

import creativeendlessgrowingceg.allergychecker.LanguageFragment;
import creativeendlessgrowingceg.allergychecker.R;
import creativeendlessgrowingceg.allergychecker.billingmodule.billing.BillingManager;
import creativeendlessgrowingceg.allergychecker.billingmodule.skulist.row.GoldMonthlyDelegate;
import creativeendlessgrowingceg.allergychecker.billingmodule.skulist.row.GoldYearlyDelegate;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-01-29
 */

public class SubscriptionsViewController {
    private static final String TAG = "MainViewController";

    // Graphics for the gas gauge
    private static int[] TANK_RES_IDS = { R.drawable.gas0, R.drawable.gas1, R.drawable.gas2,
            R.drawable.gas3, R.drawable.gas4 };

    // How many units (1/4 tank is our unit) fill in the tank.
    private static final int TANK_MAX = 4;

    private final UpdateListener mUpdateListener;
    private LanguageFragment mActivity;

    // Tracks if we currently own subscriptions SKUs
    private boolean mGoldMonthly;
    private boolean mGoldYearly;

    // Tracks if we currently own a premium car
    private boolean mIsPremium;

    // Current amount of gas in tank, in units
    private int mTank;

    public SubscriptionsViewController(LanguageFragment activity) {
        mUpdateListener = new UpdateListener();
        mActivity = activity;
    }

    public SubscriptionsViewController() {
        mUpdateListener = new UpdateListener();
    }


    public UpdateListener getUpdateListener() {
        return mUpdateListener;
    }

    public boolean isGoldMonthlySubscribed() {
        return mGoldMonthly;
    }

    public boolean isGoldYearlySubscribed() {
        return mGoldYearly;
    }

    public @DrawableRes
    int getTankResId() {
        int index = (mTank >= TANK_RES_IDS.length) ? (TANK_RES_IDS.length - 1) : mTank;
        return TANK_RES_IDS[index];
    }

    public boolean isPremiumPurchased() {
        Log.d(TAG, "isPremiumPurchased: " + mIsPremium);
        return mIsPremium;
    }

    /**
     * Handler to billing updates
     */
    private class UpdateListener implements BillingManager.BillingUpdatesListener {
        @Override
        public void onBillingClientSetupFinished() {
            if(mActivity != null)
            mActivity.onBillingManagerSetupFinished();
        }

        @Override
        public void onConsumeFinished(String token, @BillingClient.BillingResponse int result) {
            Log.d(TAG, "Consumption finished. Purchase token: " + token + ", result: " + result);

            // Note: We know this is the SKU_GAS, because it's the only one we consume, so we don't
            // check if token corresponding to the expected sku was consumed.
            // If you have more than one sku, you probably need to validate that the token matches
            // the SKU you expect.
            // It could be done by maintaining a map (updating it every time you call consumeAsync)
            // of all tokens into SKUs which were scheduled to be consumed and then looking through
            // it here to check which SKU corresponds to a consumed token.

            Log.d(TAG, "End consumption flow.");
        }

        @Override
        public void onPurchasesUpdated(List<Purchase> purchases) {
            Log.d(TAG, "onPurchasesUpdated: ");
            mGoldMonthly = false;
            mGoldYearly = false;

            for (Purchase purchase : purchases) {
                Log.d(TAG, "onPurchasesUpdated: " + purchase.getOrderId());
                switch (purchase.getSku()) {
                    case GoldMonthlyDelegate.SKU_ID:
                        mGoldMonthly = true;
                        mIsPremium = true;
                        break;
                    case GoldYearlyDelegate.SKU_ID:
                        mGoldYearly = true;
                        mIsPremium = true;
                        break;
                }
            }
            if(mActivity != null)
            mActivity.showRefreshedUi();
        }

    }
}
