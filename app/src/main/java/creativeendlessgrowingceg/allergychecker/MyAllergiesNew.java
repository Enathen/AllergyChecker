package creativeendlessgrowingceg.allergychecker;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.ContentValues.TAG;
import static creativeendlessgrowingceg.allergychecker.AllergyList.checkAvailablePicture;


public class MyAllergiesNew extends Fragment implements SearchView.OnQueryTextListener {
    ListViewAdapter adapter;
    ListView listView;

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

        new SetupAllergyView((LinearLayout) parentFrame.findViewById(R.id.linearLayoutMyAllergies), new AllergyList(getContext()).getMyAllergies(), ValidateAllergiesPreferences.setupAllergy()).execute();

        SearchView searchView = (SearchView) parentFrame.findViewById(R.id.searchBarAllergies);
        TreeMap<Integer, ArrayList<Integer>> keys = new AllergyList(getContext()).getMyAllergies();
        ArrayList<Integer> keysString = new ArrayList<>();
        for (ArrayList<Integer> key : keys.values()) {
            keysString.addAll(key);

        }
        adapter = new ListViewAdapter(getContext(), keysString, ValidateAllergiesPreferences.setupAllergy());

        listView = (ListView) parentFrame.findViewById(R.id.listViewTest);
        listView.setAdapter(adapter);
        adapter.filter("ABCDEFGHJI");
        searchView.setOnQueryTextListener(this);
        return parentFrame;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        int filter;
        if (s.equals("")) {
            filter = adapter.filter("ABCDEFGHJI");
        } else {
            filter = adapter.filter(s);
        }
        setListViewHeightBasedOnChildren(listView, filter, listView.getSelectedView());
        return true;
    }

    private void setListViewHeightBasedOnChildren(ListView listView, int amount, View view) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (amount));
        listView.setLayoutParams(params);
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
                        if (isAdded() && getActivity() != null) {

                            final CardClassLayout cardClassLayout = new CardClassLayout.CardClassLayoutBuilder(getContext(), getString(integer), checkAvailablePicture(integer), colorGradientPicker.
                                    ColorGradientPickerPick(myAllergyPreference.size(), atomicInteger.addAndGet(1), getContext())).
                                    optionalLinearSizeHorizontalHeight(75).optionalBorder(getContext().getColor(R.color.colorCheckBoxColor)).buildCardClassLayout();

                            cardClassLayout.getLinearLayoutHorizontal().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new SetupAllergies(myAllergyPreference, hashMap, cardClassLayout, linearCardClass, integer).execute();

                                }
                            });
                            cardClassLayout.getLinearLayoutVertical().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            parentLinearLayout.addView(cardClassLayout.getParentLinearLayout());
                        }
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
                    final ArrayList<CheckBox> arrayList = new ArrayList<>();
                    for (int integers : myAllergies.get(integer)) {
                        Log.d(TAG, "run: " + hashMap.get(integers));
                        CheckBoxLayout checkBoxLayout = new CheckBoxLayout.CheckBoxBuilder(getContext(), getString(integers)).optionalCheckBox(hashMap.get(integers)).buildCheckBoxLayout();
                        arrayList.add(checkBoxLayout.getCheckBox());
                        linearCardClass.addView(cardClassLayout, checkBoxLayout.getView());
                    }
                    linearCardClass.addView(cardClassLayout,new ButtonLayout.ButtonLayoutBuilder(getContext(), getString(R.string.checkAll), new View.OnClickListener() {
                        public Boolean checkAll =true;

                        @Override
                        public void onClick(View view) {

                            if(checkAll){
                                for (CheckBox checkBox : arrayList) {
                                    checkBox.setChecked(true);
                                }
                                ((TextView)view).setText(getString(R.string.uncheckAll));
                                checkAll = !checkAll;
                            }
                            else{
                                for (CheckBox checkBox : arrayList) {
                                    checkBox.setChecked(false);
                                }
                                ((TextView)view).setText(getString(R.string.checkAll));
                                checkAll = !checkAll;
                            }
                        }
                    }).buildButtonLayout().getView());

                    linearCardClass.CardDefaultTransition(cardClassLayout, CardClassSetup.explode(), getContext());
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

