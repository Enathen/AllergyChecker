package creativeendlessgrowingceg.allergychecker;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.transitionseverywhere.Explode;
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

    private HashMap<LinearLayout, ArrayList<LinearLayout>> objectToRender = new HashMap<>();

    public void CardDefaultTransition(CardClassLayout cardClassLayout) {
        final LinearLayout linearLayoutVertical = cardClassLayout.getLinearLayoutVertical();
        final LinearLayout linearLayoutHorizontal = cardClassLayout.getLinearLayoutHorizontal();
        linearLayoutHorizontal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionManager.endTransitions(linearLayoutHorizontal);
                TransitionSet setFast = new TransitionSet()
                        .addTransition(new Explode()).setDuration(500);
                //TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.linearLayoutCardHorizontal));
                linearLayoutHorizontal.findViewById(R.id.imageViewCardHorizontal).setVisibility(View.GONE);

                linearLayoutHorizontal.findViewById(R.id.textViewCardHorizontal).setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition((ViewGroup) linearLayoutVertical.findViewById(R.id.linearLayoutCardVertical), setFast);
                if(objectToRender.containsKey(linearLayoutVertical)){
                    for (LinearLayout layout : objectToRender.get(linearLayoutVertical)) {
                        layout.setVisibility(View.VISIBLE);

                    }
                }

                linearLayoutVertical.findViewById(R.id.imageViewCardVertical).setVisibility(View.VISIBLE);
                linearLayoutVertical.findViewById(R.id.textViewCardVertical).setVisibility(View.VISIBLE);


            }

        });
        linearLayoutVertical.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionManager.endTransitions((ViewGroup) linearLayoutVertical.findViewById(R.id.linearLayoutCardVertical));
                TransitionSet setFast = new TransitionSet()
                        .addTransition(new Slide(Gravity.END)).setDuration(500);
                //TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.linearLayoutCardVertical));
                linearLayoutVertical.findViewById(R.id.imageViewCardVertical).setVisibility(View.GONE);
                linearLayoutVertical.findViewById(R.id.textViewCardVertical).setVisibility(View.GONE);
                if(objectToRender.containsKey(linearLayoutVertical)) {
                    for (LinearLayout layout : objectToRender.get(linearLayoutVertical)) {
                        layout.setVisibility(View.GONE);

                    }
                }
                TransitionManager.beginDelayedTransition((ViewGroup) linearLayoutHorizontal.findViewById(R.id.linearLayoutCardHorizontal), setFast);
                linearLayoutHorizontal.findViewById(R.id.imageViewCardHorizontal).setVisibility(View.VISIBLE);
                linearLayoutHorizontal.findViewById(R.id.textViewCardHorizontal).setVisibility(View.VISIBLE);


            }

        });
    }

    public void addCheckBox(CardClassLayout viewById, LinearLayout toAdd) {
        if (objectToRender.containsKey(viewById.getLinearLayoutVertical())) {
            objectToRender.get(viewById.getLinearLayoutVertical()).add(toAdd);
        } else {
            ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
            linearLayouts.add(toAdd);
            objectToRender.put(viewById.getLinearLayoutVertical(), linearLayouts);
        }
        viewById.getLinearLayoutVertical().addView(toAdd);
    }
}
