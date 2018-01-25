package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-01-24
 */

public class Billing {
    private static final String TAG = "BILLING";
    private String mPremiumUpgradePrice;
    private BillingClient mBillingClient;
    private Activity activity;

    public Billing(Activity activity){

        this.activity = activity;
    }
    public void connection(Context context){
        PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
            /**
             * Implement this method to get notifications for purchases updates. Both purchases initiated by
             * your app and the ones initiated by Play Store will be reported here.
             *
             * @param responseCode Response code of the update.
             * @param purchases    List of updated purchases if present.
             */
            @Override
            public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
                if (responseCode == BillingClient.BillingResponse.OK
                        && purchases != null) {
                    for (Purchase purchase : purchases) {
                        handlePurchase(purchase);
                    }
                    Log.d(TAG, "onPurchasesUpdated: billingresponse OK");
                } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
                    Log.d(TAG, "onPurchasesUpdated: Handle an error caused by a user cancelling the purchase flow.");
                    // Handle an error caused by a user cancelling the purchase flow.
                } else {
                    // Handle any other error codes.
                    Log.d(TAG, "onPurchasesUpdated: OtherError");
                }


            }
        };
        mBillingClient = BillingClient.newBuilder(context).setListener(purchasesUpdatedListener).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                Log.d(TAG, "onBillingSetupFinished: " + billingResponseCode);
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d(TAG, "onBillingServiceDisconnected: Try to restart the connection on the next request to\n" +
                        "                // Google Play by calling the startConnection() method.");
            }
        });
        SKUList();
    }

    public void handlePurchase(Purchase purchase) {
        Log.d(TAG, "handlePurchase: "+ purchase);
    }
    public void buyProduct(String skuId){
        Log.d(TAG, "buyProduct: " + mBillingClient.isFeatureSupported(skuId));
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSku(skuId)
                .setType(BillingClient.SkuType.SUBS)
                .build();
        int responseCode = mBillingClient.launchBillingFlow(activity,flowParams);
        Log.d(TAG, "buyProduct: " + responseCode);
    }
    public void setmBillingClientOverride(){
        mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS,
                new PurchaseHistoryResponseListener() {
                    @Override
                    public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                          List<Purchase> purchasesList) {
                        if (responseCode == BillingClient.BillingResponse.OK
                                && purchasesList != null) {
                            for (Purchase purchase : purchasesList) {
                                // Process the result.
                            }
                        }
                    }
                });
    }
    public void SubConsume(String skuId){
        BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
                .setSku(skuId).setType(BillingClient.SkuType.SUBS);
        int responseCode = mBillingClient.launchBillingFlow(activity,builder.build());
        Log.d(TAG, "consumeResponseLister: " + responseCode);
        // Make sure to implement a retry policy to handle interrupted connections.
    }
    public void queryPurchase(){
        Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.SUBS);
    }
    public void SKUList(){
        List<String> skuList = new ArrayList<>();
        skuList.add("premium_upgrade");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        mBillingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
            /**
             * Called to notify that a fetch SKU details operation has finished.
             *
             * @param responseCode   Response code of the update.
             * @param skuDetailsList List of SKU details.
             */
            @Override
            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                Log.d(TAG, "onSkuDetailsResponse: "+ skuDetailsList + responseCode);
                if (responseCode == BillingClient.BillingResponse.OK
                        && skuDetailsList != null) {
                    for (SkuDetails skuDetails : skuDetailsList) {
                        String sku = skuDetails.getSku();
                        String price = skuDetails.getPrice();
                        if ("premium_upgrade".equals(sku)) {
                            mPremiumUpgradePrice = price;
                        }
                    }
                }
            }
        });
    }
}
