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

import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import creativeendlessgrowingceg.allergychecker.camera.ui.GraphicOverlay;

import static android.content.ContentValues.TAG;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 * TODO: Make this implement Detector.Processor<TextBlock> and add text to the GraphicOverlay
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private static OcrCaptureActivity parent;
    private int savedTextIterator = 0;
    private Toast toast;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, OcrCaptureActivity activity) {
        mGraphicOverlay = ocrGraphicOverlay;
        parent = activity;
    }

    // TODO:  Once this implements Detector.Processor<TextBlock>, implement the abstract methods.
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {

        SparseArray<TextBlock> items = detections.getDetectedItems();
        if(items.size()>0){
            savedTextIterator++;
            Log.d(TAG, "receiveDetections: "+savedTextIterator);
            parent.runOnUiThread(new Runnable() {
                public void run() {
                    parent.cancelToast();
                    if(toast != null){
                        toast.cancel();
                    }
                    toast = Toast.makeText(parent.getBaseContext(), savedTextIterator + "", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
            mGraphicOverlay.removeToSeparate();




            for (int i = 0; i < items.size(); ++i) {
                TextBlock item = items.valueAt(i);
            /*if (item != null && item.getValue() != null) {
                Log.d("Processor", "Text detected! " + item.getValue());
            }*/
                OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
                mGraphicOverlay.add(graphic);

            }
        }

    }
    public void cancelToast(){
        if (toast != null)
            toast.cancel();
    }
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    public int getSavedTextIterator() {
        return savedTextIterator;
    }

    public void setSavedTextIterator(int savedTextIterator) {
        this.savedTextIterator = savedTextIterator;
    }
}
