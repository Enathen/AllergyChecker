package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;


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
    private StartPage startPage;
    boolean saveButton = false;
    private int rand;

    public HistoryFragment(StartPage startPage) {

        this.startPage = startPage;
    }
    public HistoryFragment() {
        // Required empty public constructor
    }

    public HistoryFragment(HistoryFragment historyFragment) {
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
    public  HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment(this);
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
        /*Locale locale = new Locale(new SettingsFragment(getContext()).getLanguageFromLFragment(getContext()));
        final Locale newLocale = new Locale(locale.getLanguage());
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = getContext().getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"history" + Locale.getDefault().getLanguage());
        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_history, container, false);

        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.lineaLayoutFragHistory);

        ArrayList<String> arrayList = new StartPage(getActivity()).getArrayFromHistory();
        Collections.sort(arrayList,new stringComparator());
        Collections.reverse(arrayList);

        for (String s : arrayList) {
            Log.d(TAG,s.substring(19));
        }
        insertNew(inflater,container,arrayList);
        Log.d(TAG,"history" + Locale.getDefault().getLanguage());
        return parentFrameLayout;
    }
    public static class stringComparator implements Comparator<String> {
        @Override
        public int compare(String string1, String string2) {
            String year1 = string1.substring(0,4);
            String year2 = string2.substring(0,4);
            String month1 = string1.substring(5,7);
            String month2 = string2.substring(5,7);
            String day1 = string1.substring(8,10);
            String day2 = string2.substring(8,10);

            String time1 = string1.substring(11,19);
            String time2 = string2.substring(11,19);
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
        ArrayList<Integer> color = ColorRandom.getRandomColor();
        rand = new Random().nextInt(color.size());
        if(!arrayList.isEmpty()) {

            int baseIncrease = (255 - 26) / arrayList.size() - 1;
            if(baseIncrease == 0){
                baseIncrease = 1;
            }
            Log.d(TAG, String.valueOf(baseIncrease));
            for (final String s : arrayList) {

                final LinearLayout topLinLayOut = (LinearLayout) inflater.inflate(R.layout.historyrow, container, false);
                final LinearLayout newLinearLayout = (LinearLayout) topLinLayOut.findViewById(R.id.linlay);
                TextView side = (TextView) topLinLayOut.findViewById(R.id.sideBorder);
                TextView side2 = (TextView) topLinLayOut.findViewById(R.id.sideBorder1);
                TextView textview = (TextView) newLinearLayout.findViewById(R.id.textViewHistoryRow);
                final TextView tv = new TextView(getContext());
                tv.setText(s.substring(20));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(22);
                tv.setBackgroundColor(getContext().getColor(R.color.colorPrimaryDark));
                tv.setTextColor(getContext().getColor(R.color.fontColorTextWhite));
                tv.setVisibility(View.INVISIBLE);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.INVISIBLE);
                        topLinLayOut.removeView(tv);
                    }
                });
                final Button button = new Button(getContext());
                button.setBackgroundColor(getContext().getColor(R.color.colorPrimaryDark));
                button.setTextColor(getContext().getColor(R.color.fontColorTextWhite));
                button.setTextSize(26);
                button.setText(R.string.delete);
                button.setGravity(View.TEXT_ALIGNMENT_CENTER);
                button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new StartPage(getActivity()).deleteOneItemHistory(s,startPage);
                        topLinLayOut.removeView(button);
                        topLinLayOut.removeView(tv);
                        topLinLayOut.removeView(newLinearLayout);
                        parentLinearLayout.findViewById(R.id.buttonHistoryFragment).setVisibility(View.VISIBLE);
                        parentLinearLayout.findViewById(R.id.buttonHistoryFragment).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), StartPage.class);
                                startActivity(intent);
                            }
                        });


                    }
                });
                String correctString = s.substring(0, 20);


                rand++;

                side.setBackgroundColor(ColorRandom.getRandomColorFromArray(color,rand));
                side2.setBackgroundColor(ColorRandom.getRandomColorFromArray(color,rand));
                colorGreenToRed += baseIncrease;
                if (colorGreenToRed >= 255) {
                    baseIncrease=-1;
                }
                textview.setText(correctString);
                newLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG,"TIMEHISTORY");
                        Locale.setDefault(loadLocale());
                        Configuration config = new Configuration();
                        config.setLocale(loadLocale());
                        getActivity().getApplicationContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
                        Intent intent = new Intent(getActivity(), StartPage.class);
                        intent.putExtra("HistoryFragment", s.substring(20));
                        startActivity(intent);

                    }
                });
                newLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        if(tv.getVisibility() == View.VISIBLE){
                            tv.setVisibility(View.INVISIBLE);
                            topLinLayOut.removeView(tv);
                            topLinLayOut.removeView(button);

                        }else{
                            topLinLayOut.addView(tv);
                            topLinLayOut.addView(button);
                            tv.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });
                parentLinearLayout.addView(topLinLayOut);
            }

            if(!arrayList.isEmpty()){
                LinearLayout linearLayoutDeleteAll = (LinearLayout) inflater.inflate(R.layout.historyrow,container,false);
                ((TextView) linearLayoutDeleteAll.findViewById(R.id.textViewHistoryRow)).setText(getString(R.string.deleteHistory));
                linearLayoutDeleteAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        new StartPage(getActivity()).deleteHistory();
                                        Intent intent = new Intent(getActivity(), StartPage.class);
                                        startActivity(intent);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(R.string.areYouSure).setPositiveButton(R.string.yes, dialogClickListener)
                                .setNegativeButton(R.string.no, dialogClickListener).show();


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

    private Locale loadLocale() {
        Locale locale = new Locale(new SettingsFragment(getContext()).getLanguageFromLFragment(getContext()));
        Log.d(TAG,locale.getLanguage());
        return locale;
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
