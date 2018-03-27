package creativeendlessgrowingceg.allergychecker.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import java.util.Locale;

import creativeendlessgrowingceg.allergychecker.ColorRandom;
import creativeendlessgrowingceg.allergychecker.DateAndHistory;
import creativeendlessgrowingceg.allergychecker.R;
import creativeendlessgrowingceg.allergychecker.TextHandler;

/**
 * displays scanned allergies to be scanned again
 *
 * @author Jonathan Alexander Norberg
 * @version 2017 Nov
 */
public class HistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HistoryFragment";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;
    private Bundle savedInstanceState;

    public HistoryFragment() {
        // Required empty public constructor
    }


    public HistoryFragment newInstance(String param1, String param2) {
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

    /**
     *  get dateString and inflate views
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_history, container, false);

        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.lineaLayoutFragHistory);

        ArrayList<String> arrayList = new DateAndHistory(getActivity()).getArrayFromHistory();
        Collections.sort(arrayList, new stringComparator());
        Collections.reverse(arrayList);

        insertNew(inflater, container, arrayList);

        return parentFrameLayout;
    }

    /**
     * inflate view and create nice side borders
     * @param arrayList of history strings
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertNew(final LayoutInflater inflater, final ViewGroup container, final ArrayList<String> arrayList) {
        if (!arrayList.isEmpty()) {

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
                tv.setBackgroundColor(Color.rgb(36,49,60));
                tv.setTextColor(Color.WHITE);
                tv.setVisibility(View.INVISIBLE);

                final Button button = new Button(getContext());
                button.setBackgroundColor(Color.rgb(36,49,60));
                button.setTextColor(Color.WHITE);
                button.setTextSize(26);
                button.setText(R.string.delete);
                button.setGravity(View.TEXT_ALIGNMENT_CENTER);
                button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DateAndHistory(getActivity()).deleteOneItemHistory(s);
                        topLinLayOut.removeView(button);
                        topLinLayOut.removeView(tv);
                        topLinLayOut.removeView(newLinearLayout);


                    }
                });
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.INVISIBLE);
                        topLinLayOut.removeView(tv);
                        topLinLayOut.removeView(button);
                    }
                });
                String correctString = s.substring(0, 20);


                int randomColorFromArray = ColorRandom.getRandomColorFromArray();
                side.setBackgroundColor(randomColorFromArray);
                side2.setBackgroundColor(randomColorFromArray);
                textview.setText(TextHandler.capitalLetter(correctString));
                newLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Locale.setDefault(loadLocale());
                        Configuration config = new Configuration();
                        config.setLocale(loadLocale());
                        getActivity().getApplicationContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
                        Intent intent = new Intent(getActivity(), StartPage.class);
                        intent.putExtra("HistoryFragment", s.substring(20));
                        startActivity(intent);
                        getActivity().finish();

                    }
                });
                newLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        if (tv.getVisibility() == View.VISIBLE) {
                            tv.setVisibility(View.INVISIBLE);
                            topLinLayOut.removeView(tv);
                            topLinLayOut.removeView(button);

                        } else {
                            topLinLayOut.addView(tv);
                            topLinLayOut.addView(button);
                            tv.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });
                parentLinearLayout.addView(topLinLayOut);
            }

            if (!arrayList.isEmpty()) {
                LinearLayout linearLayoutDeleteAll = (LinearLayout) inflater.inflate(R.layout.historyrow, container, false);
                ((TextView) linearLayoutDeleteAll.findViewById(R.id.textViewHistoryRow)).setText(getString(R.string.deleteHistory));
                linearLayoutDeleteAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        new DateAndHistory(getActivity()).deleteHistory();
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
        } else {
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.historyrow, container, false);
            TextView textview = (TextView) linearLayout.findViewById(R.id.textViewHistoryRow);
            textview.setText(R.string.scanPhotos);
            parentLinearLayout.addView(linearLayout);
        }
    }
    private Locale loadLocale() {
        return new Locale(new LanguageFragment().getLanguageFromLFragment(getContext()));
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * compare dates to present it nice.
     */
    public static class stringComparator implements Comparator<String> {
        @Override
        public int compare(String string1, String string2) {
            String year1 = string1.substring(0, 4);
            String year2 = string2.substring(0, 4);
            String month1 = string1.substring(5, 7);
            String month2 = string2.substring(5, 7);
            String day1 = string1.substring(8, 10);
            String day2 = string2.substring(8, 10);

            String time1 = string1.substring(11, 19);
            String time2 = string2.substring(11, 19);
            if (year1.compareToIgnoreCase(year2) != 0) {
                return year1.compareToIgnoreCase(year2);
            }
            if (month1.compareToIgnoreCase(month2) != 0) {

                return month1.compareToIgnoreCase(month2);
            }
            if (day1.compareToIgnoreCase(day2) != 0) {
                return day1.compareToIgnoreCase(day2);
            }
            if (time1.compareToIgnoreCase(time2) != 0) {
                return time1.compareToIgnoreCase(time2);
            }


            return 0;
        }
    }
}
