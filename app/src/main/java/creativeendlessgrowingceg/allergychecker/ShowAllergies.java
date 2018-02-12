package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowAllergies.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowAllergies#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowAllergies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private HashSet<Integer> allergies;
    private HashSet<ImageView> imageViewHashSetToDestroy = new HashSet<>();
    private ArrayList<Locale> categories;
    private FrameLayout parentFrame;
    private LinearLayout parentLinearLayout;
    private HashMap<LinearLayout,ArrayList<LinearLayout>> linearLayouts = new HashMap<>();

    public ShowAllergies() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowAllergies.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowAllergies newInstance(String param1, String param2) {
        ShowAllergies fragment = new ShowAllergies();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("HashSet",allergies);
        outState.putSerializable("ArrayList",categories);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            categories = (ArrayList<Locale>) bundle.getSerializable("ArrayList");
        }
        if(savedInstanceState!= null){
            allergies = (HashSet<Integer>) savedInstanceState.getSerializable("HashSet");
            categories = (ArrayList<Locale>) savedInstanceState.getSerializable("ArrayList");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState!= null){
            categories = (ArrayList<Locale>) savedInstanceState.getSerializable("ArrayList");
        }
        parentFrame = (FrameLayout) inflater.inflate(R.layout.fragment_show_allergies, container, false);
        parentLinearLayout =(LinearLayout) parentFrame.findViewById(R.id.showAllergiesLinearLayout);
        allergies = new LoadUIAllergies().getAllergies(this);

        if(!allergies.isEmpty()){
            new ShowAllergies.CalcAllergy(this,inflater,container).execute();
        }else{
            parentLinearLayout.findViewById(R.id.textViewAllergiesFrag).setVisibility(View.VISIBLE);
            parentLinearLayout.findViewById(R.id.pbShowAllergies).setVisibility(View.GONE);
        }

        return parentFrame;
    }
    private class CalcAllergy extends AsyncTask<String, Integer, ArrayList<AllAllergiesForEachInteger>> {

        private final ShowAllergies showAllergies;
        private final LayoutInflater inflater;
        private ViewGroup container;
        public CalcAllergy(ShowAllergies showAllergies, LayoutInflater inflater, ViewGroup container) {

            this.showAllergies = showAllergies;
            this.inflater = inflater;
            this.container = container;
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<AllAllergiesForEachInteger> doInBackground(String... params) {

            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param allAllergiesForEachIntegers The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<AllAllergiesForEachInteger> allAllergiesForEachIntegers) {
            super.onPostExecute(allAllergiesForEachIntegers);

            for (final Locale category : categories) {
                final LinearLayout pLinearLayout = (LinearLayout) inflater.inflate(R.layout.show_allergies_layout, container, false);
                final LinearLayout linearLayout = (LinearLayout) pLinearLayout.findViewById(R.id.showAllergiesLanguage);
                ((ImageView)linearLayout.findViewById(R.id.imageViewFlag)).setImageResource(LanguagesAccepted.getFlag(category.getLanguage()));
                imageViewHashSetToDestroy.add(((ImageView)linearLayout.findViewById(R.id.imageViewFlag)));
                ((TextView)linearLayout.findViewById(R.id.textViewStaticLanguage)).setText(TextHandler.capitalLetter(LanguagesAccepted.getCountryNameStatic(category.getLanguage()),getContext()));
                ((TextView)linearLayout.findViewById(R.id.textViewLocaleLanguage)).setText(TextHandler.capitalLetter(LanguagesAccepted.getCountryName(category.getLanguage()),getContext()));
                ((TextView)pLinearLayout.findViewById(R.id.textViewAllergicAgainst)).setText(TextHandler.capitalLetter(LanguagesAccepted.getStringByLocalNoTakeAwaySpace(getActivity(),R.string.allergyAgianst,category.getLanguage())));
                pLinearLayout.findViewById(R.id.textViewAllergicAgainst).setVisibility(View.INVISIBLE);


                pLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!linearLayouts.containsKey(pLinearLayout)){
                            linearLayouts.put(pLinearLayout,new ArrayList<LinearLayout>());
                            for (Integer allergy : allergies) {
                                LinearLayout linearLayout1 =(LinearLayout) inflater.inflate(R.layout.textviewssplitmiddle, container, false);
                                ((TextView)linearLayout1.findViewById(R.id.tvLeft)).setText(TextHandler.capitalLetter(LanguagesAccepted.getStringByLocalNoTakeAwaySpace(getActivity(),allergy,category.getLanguage())));
                                ((TextView)linearLayout1.findViewById(R.id.tvRight)).setText(TextHandler.capitalLetter(getString(allergy)));
                                linearLayout1.setVisibility(View.INVISIBLE);

                                linearLayouts.get(pLinearLayout).add(linearLayout1);
                            }
                        }

                        for (LinearLayout textViews: linearLayouts.get(v)){
                            if(textViews.getVisibility() == View.INVISIBLE){
                                ((LinearLayout) v).addView(textViews);
                                textViews.setVisibility(View.VISIBLE);
                                pLinearLayout.findViewById(R.id.textViewAllergicAgainst).setVisibility(View.VISIBLE);

                            }else {
                                ((LinearLayout) v).removeView(textViews);
                                textViews.setVisibility(View.INVISIBLE);
                                pLinearLayout.findViewById(R.id.textViewAllergicAgainst).setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
                parentLinearLayout.addView(pLinearLayout);
            }
            parentLinearLayout.findViewById(R.id.pbShowAllergies).setVisibility(View.GONE);
        }

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }
    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        for (ImageView imageView : imageViewHashSetToDestroy) {
            imageView.setImageDrawable(null);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
