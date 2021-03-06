/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package creativeendlessgrowingceg.allergychecker.camera;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import creativeendlessgrowingceg.allergychecker.APISharedPreference;
import creativeendlessgrowingceg.allergychecker.R;
import creativeendlessgrowingceg.allergychecker.camera.ui.CameraSource;
import creativeendlessgrowingceg.allergychecker.camera.ui.CameraSourcePreview;
import creativeendlessgrowingceg.allergychecker.camera.ui.GraphicOverlay;
import creativeendlessgrowingceg.allergychecker.fragment.BottomNavigationName;

/**
 * Activity for the Ocr Detecting app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
public final class OcrCaptureActivity extends AppCompatActivity {
    private static final String TAG = "OcrCaptureActivity";

    // Intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // Permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // Constants used to pass extra data in the intent
    public static final String AutoFocus = APISharedPreference.getFocus();
    public static final String UseFlash = APISharedPreference.getFlash();
    public static final String SleepTimer = APISharedPreference.getTimeSleep();
    public static final String TextBlockObject = "String";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    // Helper objects for detecting taps and pinches.
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private boolean useFlash = false;
    // A TextToSpeech engine for speaking a String value.
    private String textTapped;
    private OcrDetectorProcessor ocrDetectorProcessor;
    private Toast toast;

    public OcrCaptureActivity() {
    }

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle bundle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        super.onCreate(bundle);
        setContentView(R.layout.ocr_capture);

        Intent intent = getIntent();
        useFlash = intent.getBooleanExtra(UseFlash,false);
        Log.d(TAG, String.valueOf(useFlash));
        mPreview = findViewById(R.id.preview);
        mGraphicOverlay = findViewById(R.id.graphicOverlay);


        // Set good defaults for capturing text.
        boolean autoFocus = intent.getBooleanExtra(AutoFocus,true);


        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {

            createCameraSource(autoFocus, useFlash,intent.getIntExtra(SleepTimer,5));
        } else {
            requestCameraPermission();
        }

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Snackbar.make(mGraphicOverlay, " Pinch/Stretch to zoom. Hold long to remove all text boxes",
                Snackbar.LENGTH_LONG)
                .show();


    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

/*        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };
        Snackbar.make(mGraphicOverlay, " Pinch/Stretch to zoom",
                Snackbar.LENGTH_LONG).setAction(R.string.ok,listener)
                .show();*/
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(thisActivity, permissions, RC_HANDLE_CAMERA_PERM);
                return;
            }
        };
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.varning)
                .setMessage(R.string.thisAppOnlySave)
                .setPositiveButton(R.string.ok, listener)
                .show();*/


    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);

        boolean c = gestureDetector.onTouchEvent(e);

        return b || c || super.onTouchEvent(e);
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the ocr detector to detect small text samples
     * at long distances.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash,int timeSleep) {
        Context context = getApplicationContext();

        // Create the TextRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();


        // Set the TextRecognizer's Processor.
        Log.d(TAG, "createCameraSource: "+ timeSleep+ " : " + timeSleep*100);
        ocrDetectorProcessor = new OcrDetectorProcessor(mGraphicOverlay,this,timeSleep*100);
        textRecognizer.setProcessor(ocrDetectorProcessor);

        // Check if the TextRecognizer is operational.
        if (!textRecognizer.isOperational()) {
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_NEUTRAL:
                            Intent intent = new Intent(OcrCaptureActivity.this, BottomNavigationName.class);
                            intent.putExtra(APISharedPreference.getScannedText, textTapped);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.downloadOfTextScannerNotComplete).setNeutralButton(R.string.ok, dialogClickListener).show();

        }
        CameraManager manager = (CameraManager)this.getSystemService(Context.CAMERA_SERVICE);
        float videoFrameRate= 30f;
        int videoFrameWidth = 1280;
        int videoFrameHeight = 1024;

        try {
            if (manager != null) {
                for (String cameraId : manager.getCameraIdList()) {
                    int id = Integer.valueOf(cameraId);
                    Log.d(TAG, "createCameraSource: "+ id);
                    if (CamcorderProfile.hasProfile(id, CamcorderProfile.QUALITY_HIGH_SPEED_LOW)) {
                        CamcorderProfile profile = CamcorderProfile.get(id, CamcorderProfile.QUALITY_HIGH_SPEED_LOW);
                        videoFrameHeight = profile.videoFrameHeight;
                        videoFrameWidth = profile.videoFrameWidth;
                        videoFrameRate = profile.videoFrameRate;

                    }
                }
            }
        } catch (CameraAccessException e) {
            Log.d(TAG, "createCameraSource: "+ e);

        }

        Log.d(TAG, "VideoFrameRate: " + videoFrameRate);
        Log.d(TAG, "videoFrameWidth: " + videoFrameWidth);
        Log.d(TAG, "videoFrameHeight: " + videoFrameHeight);
        // Create the mCameraSource using the TextRecognizer.
        mCameraSource =
                new CameraSource.Builder(getApplicationContext(), textRecognizer)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setRequestedPreviewSize(videoFrameWidth, videoFrameHeight)
                        .setRequestedFps(videoFrameRate)
                        .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                        .setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null)
                        .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // We have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus,false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            int sleepTimer = getIntent().getIntExtra(SleepTimer, 0);
            createCameraSource(autoFocus, useFlash,sleepTimer);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.cannotUseCamera)
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    /**
     * onTap is called to save text when the tapped TextBlock.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the tap was on a TextBlock
     */
    private boolean onTap(float rawX, float rawY) {
        Set<OcrGraphic> graphic1 = mGraphicOverlay.getGraphic();
        if (!graphic1.isEmpty()) {
            textTapped = "";
            for (OcrGraphic ocrGraphic : graphic1) {


                List<? extends Text> textComponents = ocrGraphic.getComponents();
                Log.d(TAG, "text data is being saved! " + ocrGraphic.getComponents().size());
                for(Text currentText : textComponents) {
                    if (currentText != null && !currentText.getValue().equalsIgnoreCase("null")) {
                        //Log.d(TAG, "text data is being saved! " + currentText.getValue());
                        // Speak the string.
                       // Log.d(TAG, textTapped);
                        if(textTapped.equals("null")){
                            textTapped = currentText.getValue();
                        }
                        else {
                            textTapped += " " + currentText.getValue();
                        }
                    }
                    else {
                        Log.d(TAG, "text data is null");
                    }
                }
            }



            //Toast.makeText(this, textTapped, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(OcrCaptureActivity.this, BottomNavigationName.class);
            intent.putExtra(APISharedPreference.getScannedText, textTapped);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            }


        else {
            Log.d(TAG,"no text detected");
        }
        return textTapped != null;
    }
    public void cancelToast(){
        if(toast != null)
            toast.cancel();
    }
    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mGraphicOverlay.clear();

            ocrDetectorProcessor.setSavedTextIterator(0);
            ocrDetectorProcessor.cancelToast();
            if(toast != null){
                toast.cancel();
            }
            toast = Toast.makeText(getApplicationContext(), 0+ "", Toast.LENGTH_LONG);
            toast.show();
        }
    }



    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (mCameraSource != null) {
                mCameraSource.doZoom(detector.getScaleFactor());
            }
        }
    }
}
