package creativeendlessgrowingceg.allergychecker;

import android.support.v7.widget.RecyclerView;
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

    private HashMap<RecyclerView, ArrayList<LinearLayout>> objectToRender = new HashMap<>();
    public static TransitionSet explode(){
        return new TransitionSet()
                .addTransition(new Explode()).setDuration(500);
    }
    public static TransitionSet fade(){
        return new TransitionSet()
                .addTransition(new Fade()).setDuration(1000);
    }

    public void CardDefaultTransition(CardClassLayout cardClassLayout, final TransitionSet set) {

        final LinearLayout linearLayoutVertical = cardClassLayout.getLinearLayoutVertical();
        final LinearLayout linearLayoutHorizontal = cardClassLayout.getLinearLayoutHorizontal();
        final RecyclerView recycleViewVertical = cardClassLayout.getRecycleViewVertical();
        linearLayoutHorizontal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TransitionManager.endTransitions(linearLayoutHorizontal);

                //TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.linearLayoutCardHorizontal));
                linearLayoutHorizontal.setVisibility(View.GONE);
                linearLayoutHorizontal.findViewById(R.id.imageViewCardHorizontal).setVisibility(View.GONE);

                linearLayoutHorizontal.findViewById(R.id.textViewCardHorizontal).setVisibility(View.GONE);
                TransitionManager.beginDelayedTransition((ViewGroup) linearLayoutVertical.findViewById(R.id.linearLayoutCardVertical), set);
                linearLayoutHorizontal.post(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        if(objectToRender.containsKey(recycleViewVertical)){
                            for (LinearLayout layout : objectToRender.get(recycleViewVertical)) {
                                layout.setVisibility(View.VISIBLE);

                            }
                        }
                    }
                });

                linearLayoutVertical.setVisibility(View.VISIBLE);
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
                linearLayoutVertical.setVisibility(View.GONE);
                if(objectToRender.containsKey(recycleViewVertical)) {
                    for (LinearLayout layout : objectToRender.get(recycleViewVertical)) {
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

    public void addCheckBox(CardClassLayout viewById, LinearLayout toAdd) {
        RecyclerView recycleViewVertical = viewById.getRecycleViewVertical();
        if (objectToRender.containsKey(recycleViewVertical)) {
            objectToRender.get(recycleViewVertical).add(toAdd);
        } else {
            ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
            linearLayouts.add(toAdd);
            objectToRender.put(recycleViewVertical, linearLayouts);
        }
        viewById.getLinearLayoutVertical().addView(toAdd);
    }
}
