package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HistoryFragment";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    HashMap<LinearLayout,String> StoredString = new HashMap<>();

    private OnFragmentInteractionListener mListener;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;
    private Bundle savedInstanceState;
    public HistoryFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment test.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_history, container, false);
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.lineaLayoutFragHistory);
        ArrayList<String> arrayList = new StartPage(getActivity()).getArrayFromHistory();
        arrayList.isEmpty();
        Collections.sort(arrayList,new stringComparator());
        Collections.reverse(arrayList);

        for (String s : arrayList) {
            Log.d(TAG,s.substring(34));

        }
        insertNew(inflater,container,arrayList);
        return parentFrameLayout;
    }
    private static class stringComparator implements Comparator<String> {
        @Override
        public int compare(String string1, String string2) {

            String month1 = string1.substring(4,7);
            String month2 = string2.substring(4,7);
            String day1 = string1.substring(8,10);
            String day2 = string2.substring(8,10);
            String time1 = string1.substring(11,19);
            String time2 = string2.substring(11,19);
            String year1 = string1.substring(30,34);
            String year2 = string2.substring(30,34);
            if(year1.compareToIgnoreCase(year2) != 0){
                return year1.compareToIgnoreCase(year2);
            }
            if(month1.compareToIgnoreCase(month2) != 0){
                return month1.compareToIgnoreCase(month2);
            }
            if(day1.compareToIgnoreCase(day2) != 0){
                return day1.compareToIgnoreCase(day2);
            }if(time1.compareToIgnoreCase(time2) != 0){
                return time1.compareToIgnoreCase(time2);
            }


            return 0;
        }
    }
    private void insertNew(final LayoutInflater inflater, final ViewGroup container, final ArrayList<String> arrayList){
        int colorGreenToRed = 26;
        if(!arrayList.isEmpty()) {

            int baseIncrease = (255 - 26) / arrayList.size() - 1;
            Log.d(TAG, String.valueOf(baseIncrease));
            for (final String s : arrayList) {

                LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.historyrow, container, false);
                TextView textview = (TextView) newLinearLayout.findViewById(R.id.textViewHistoryRow);
                String correctString = s.substring(0, 20);

                textview.setBackgroundColor(Color.rgb(colorGreenToRed, 178, 172));
                colorGreenToRed += baseIncrease;
                if (colorGreenToRed > 255) {
                    colorGreenToRed = 255;
                }
                correctString = correctString.concat(s.substring(30, 34));
                textview.setText(correctString);
                newLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), StartPage.class);
                        intent.putExtra("HistoryFragment", s.substring(34));
                        startActivity(intent);

                    }
                });

                parentLinearLayout.addView(newLinearLayout);
            }

            if(!arrayList.isEmpty()){
                LinearLayout linearLayoutDeleteAll = (LinearLayout) inflater.inflate(R.layout.historyrowdeleteallbutton,container,false);
                linearLayoutDeleteAll.setBackgroundColor(Color.rgb(colorGreenToRed,178,172));
                linearLayoutDeleteAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new StartPage(getActivity()).deleteHistory();
                        Intent intent = new Intent(getActivity(), StartPage.class);
                        startActivity(intent);

                    }
                });
                parentLinearLayout.addView(linearLayoutDeleteAll);
            }
        }
        else{
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.historyrow,container,false);
            TextView textview = (TextView) linearLayout.findViewById(R.id.textViewHistoryRow);
            textview.setText(R.string.scanPhotos);
            parentLinearLayout.addView(linearLayout);
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
