package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

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
    private ArrayList<Locale> categories;
    private FrameLayout parentFrame;
    private LinearLayout parentLinearLayout;
    private HashMap<LinearLayout,ArrayList<LinearLayout>> linearLayouts = new HashMap<>();

    public ShowAllergies() {
        // Required empty public constructor
    }
    public ShowAllergies(HashSet<Integer> allergies, ArrayList<Locale> categories){

        this.allergies = allergies;
        this.categories = categories;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentFrame = (FrameLayout) inflater.inflate(R.layout.fragment_show_allergies, container, false);
        parentLinearLayout =(LinearLayout) parentFrame.findViewById(R.id.showAllergiesLinearLayout);
        for (Locale category : categories) {
            final LinearLayout pLinearLayout = (LinearLayout) inflater.inflate(R.layout.show_allergies_layout, container, false);
            LinearLayout linearLayout = (LinearLayout) pLinearLayout.findViewById(R.id.showAllergiesLanguage);
            Log.d(TAG, "onCreateView: " + category.getLanguage());
            ((ImageView)linearLayout.findViewById(R.id.imageViewFlag)).setImageResource(LanguagesAccepted.getFlag(category.getLanguage()));
            ((TextView)linearLayout.findViewById(R.id.textViewStaticLanguage)).setText(LanguagesAccepted.getCountryNameStatic(category.getLanguage()));
            ((TextView)linearLayout.findViewById(R.id.textViewLocaleLanguage)).setText(LanguagesAccepted.getCountryName(category.getLanguage()));
            ((TextView)pLinearLayout.findViewById(R.id.textViewAllergicAgainst)).setText(LanguagesAccepted.getStringByLocalNoTakeAwaySpace(getActivity(),R.string.allergyAgianst,category.getLanguage()));
            ((TextView)pLinearLayout.findViewById(R.id.textViewAllergicAgainst)).setVisibility(View.INVISIBLE);

            linearLayouts.put(pLinearLayout,new ArrayList<LinearLayout>());
            for (Integer allergy : allergies) {
                LinearLayout linearLayout1 =(LinearLayout) inflater.inflate(R.layout.textviewssplitmiddle, container, false);
                ((TextView)linearLayout1.findViewById(R.id.tvLeft)).setText(LanguagesAccepted.getStringByLocalNoTakeAwaySpace(getActivity(),allergy,category.getLanguage()));
                ((TextView)linearLayout1.findViewById(R.id.tvRight)).setText(getString(allergy));
                linearLayout1.setVisibility(View.INVISIBLE);

                linearLayouts.get(pLinearLayout).add(linearLayout1);
            }
            pLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (LinearLayout textViews: linearLayouts.get((LinearLayout) v)){
                        if(textViews.getVisibility() == View.INVISIBLE){
                            ((LinearLayout) v).addView(textViews);
                            textViews.setVisibility(View.VISIBLE);
                            ((TextView)pLinearLayout.findViewById(R.id.textViewAllergicAgainst)).setVisibility(View.VISIBLE);

                        }else {
                            ((LinearLayout) v).removeView(textViews);
                            textViews.setVisibility(View.INVISIBLE);
                            ((TextView)pLinearLayout.findViewById(R.id.textViewAllergicAgainst)).setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
            parentLinearLayout.addView(pLinearLayout);
        }
        return parentFrame;
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
