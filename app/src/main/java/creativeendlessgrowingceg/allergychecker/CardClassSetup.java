package creativeendlessgrowingceg.allergychecker;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.transitionseverywhere.Explode;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class CardClassSetup {
    private HashMap<LinearLayout, ArrayList<View>> objectToRender = new HashMap<>();

    public static TransitionSet explode() {
        return new TransitionSet()
                .addTransition(new Explode()).setDuration(250);
    }

    public static TransitionSet fade() {
        return new TransitionSet()
                .addTransition(new Fade()).setDuration(1000);
    }

    public static TransitionSet end() {
        return new TransitionSet()
                .addTransition(new Slide(Gravity.END)).setDuration(1000);
    }

    public void CardDefaultTransition(CardClassLayout cardClassLayout, final TransitionSet set) {

        final LinearLayout linearLayoutVertical = cardClassLayout.getLinearLayoutVertical();
        final LinearLayout linearLayoutHorizontal = cardClassLayout.getLinearLayoutHorizontal();
        linearLayoutHorizontal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutHorizontal.findViewById(R.id.imageViewCardHorizontal).setVisibility(View.GONE);
                linearLayoutHorizontal.findViewById(R.id.textViewCardHorizontal).setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition((ViewGroup) linearLayoutVertical.findViewById(R.id.linearLayoutCardVertical), set);

                if (objectToRender.containsKey(linearLayoutVertical)) {
                    for (View layout : objectToRender.get(linearLayoutVertical)) {
                        layout.setVisibility(View.VISIBLE);

                    }
                }


                linearLayoutVertical.setVisibility(View.VISIBLE);
                linearLayoutVertical.findViewById(R.id.imageViewCardVertical).setVisibility(View.VISIBLE);
                linearLayoutVertical.findViewById(R.id.textViewCardVertical).setVisibility(View.VISIBLE);

            }

        });

        linearLayoutVertical.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionSet setFast = new TransitionSet()
                        .addTransition(new Slide(Gravity.END)).setDuration(500);
                linearLayoutVertical.findViewById(R.id.imageViewCardVertical).setVisibility(View.GONE);
                linearLayoutVertical.findViewById(R.id.textViewCardVertical).setVisibility(View.GONE);
                if (objectToRender.containsKey(linearLayoutVertical)) {
                    for (View layout : objectToRender.get(linearLayoutVertical)) {
                        layout.setVisibility(View.GONE);
                    }

                }
                TransitionManager.beginDelayedTransition((ViewGroup) linearLayoutHorizontal.findViewById(R.id.linearLayoutCardHorizontal), setFast);
                linearLayoutHorizontal.setVisibility(View.VISIBLE);
                linearLayoutHorizontal.findViewById(R.id.imageViewCardHorizontal).setVisibility(View.VISIBLE);
                linearLayoutHorizontal.findViewById(R.id.textViewCardHorizontal).setVisibility(View.VISIBLE);


            }

        });
    }

    public void addView(CardClassLayout viewById, View toAdd) {
        LinearLayout linearLayoutVertical = viewById.getLinearLayoutVertical();
        if (objectToRender.containsKey(linearLayoutVertical)) {
            objectToRender.get(linearLayoutVertical).add(toAdd);
        } else {
            ArrayList<View> linearLayouts = new ArrayList<>();
            linearLayouts.add(toAdd);
            objectToRender.put(linearLayoutVertical, linearLayouts);
        }
        linearLayoutVertical.addView(toAdd);
    }

    public void removeView(CardClassLayout viewById, View toRemove) {
        LinearLayout linearLayoutVertical = viewById.getLinearLayoutVertical();
        linearLayoutVertical.removeView(toRemove);
        objectToRender.get(linearLayoutVertical).remove(toRemove);
        toRemove.setVisibility(View.GONE);
    }


}
