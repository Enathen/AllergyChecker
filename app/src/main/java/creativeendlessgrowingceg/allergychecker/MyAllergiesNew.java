package creativeendlessgrowingceg.allergychecker;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MyAllergiesNew extends Fragment {


    public MyAllergiesNew() {
        // Required empty public constructor
    }

    public static MyAllergiesNew newInstance() {
        MyAllergiesNew fragment = new MyAllergiesNew();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentFrame = inflater.inflate(R.layout.fragment_my_allergies, container, false);
        new SetupAllergyView((LinearLayout) parentFrame.findViewById(R.id.linearLayoutMyAllergies),new AllergyList(getContext()).getMyAllergies(),ValidateAllergiesPreferences.setupAllergy()).execute();
        return parentFrame;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class SetupAllergyView extends AsyncTask<Object, Object, Object> {

        private LinearLayout parentLinearLayout;
        private TreeMap<Integer, ArrayList<Integer>> myAllergyPreference;
        private HashMap<Integer, Integer> hashMap;

        public SetupAllergyView(LinearLayout parentLinearLayout, TreeMap<Integer, ArrayList<Integer>> myAllergyPreference, HashMap<Integer, Integer> hashMap) {

            this.parentLinearLayout = parentLinearLayout;
            this.myAllergyPreference = myAllergyPreference;
            this.hashMap = hashMap;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            final CardClassSetup linearCardClass = new CardClassSetup();
            final AtomicInteger atomicInteger = new AtomicInteger(0);
            final ColorGradientPicker colorGradientPicker = new ColorGradientPicker();

            for (final Integer integer : myAllergyPreference.keySet()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final CardClassLayout cardClassLayout = new CardClassLayout.CardClassLayoutBuilder(getContext(), TextHandler.capitalLetter(TextHandler.cutFirstWord(getString(integer))), R.drawable.history, colorGradientPicker.
                                ColorGradientPickerPick(myAllergyPreference.size(), atomicInteger.addAndGet(1))).
                                optionalLinearSizeHorizontalHeight(300).buildCardClassLayout();

                        cardClassLayout.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new SetupAllergies(myAllergyPreference, hashMap, cardClassLayout, linearCardClass,integer).execute();

                            }
                        });
                        cardClassLayout.getLinearLayoutVertical().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        parentLinearLayout.addView(cardClassLayout.getParentLinearLayout());

                    }
                });

            }
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            parentLinearLayout.findViewById(R.id.progressBarAllergies).setVisibility(View.GONE);
        }

    }

    private class SetupAllergies extends AsyncTask<Object, Object, Object> {
        private final HashMap<Integer, Integer> hashMap;
        private final CardClassLayout cardClassLayout;
        private final CardClassSetup linearCardClass;
        private final ProgressBar progressBar;
        private Integer integer;
        private TreeMap<Integer, ArrayList<Integer>> myAllergies;

        public SetupAllergies(TreeMap<Integer, ArrayList<Integer>> myAllergies, HashMap<Integer, Integer> hashMap, CardClassLayout cardClassLayout, CardClassSetup linearCardClass, Integer integer) {
            progressBar = new ProgressBar(getContext());
            cardClassLayout.getParentLinearLayout().addView(progressBar);
            this.myAllergies = myAllergies;
            this.hashMap = hashMap;
            this.cardClassLayout = cardClassLayout;
            this.linearCardClass = linearCardClass;
            this.integer = integer;
        }

        @Override
        protected Object doInBackground(Object... objects) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int integers : myAllergies.get(integer)) {
                        linearCardClass.addView(cardClassLayout, new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(integers)).optionalCheckBox(hashMap.get(integers)).buildCheckBoxLayout().getView());
                    }
                    linearCardClass.CardDefaultTransition(cardClassLayout, CardClassSetup.explode());
                    cardClassLayout.getLinearLayoutHorizontal().performClick();
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
        }
    }

}

