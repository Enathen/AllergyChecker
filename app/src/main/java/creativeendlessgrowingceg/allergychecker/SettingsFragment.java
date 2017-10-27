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
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "LanguageFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String language;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;
    private OnFragmentInteractionListener mListener;
    private ArrayList<RadioButton> radioButtons = new ArrayList<>();

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        Locale locale = getResources().getConfiguration().getLocales().get(0);
        language = locale.getLanguage();

        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_settings, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linearLayoutLanguage);
        Languages languages = new Languages(getContext());

        parentLinearLayout.addView(addLanguages(inflater,languages.getstaticArrayListLanguage(),"from"));




        return parentFrameLayout;
    }

    private LinearLayout addLanguages(LayoutInflater inflater, ArrayList<Languages.LanguagesClass> languageFrom, String from) {
        final ArrayList<LinearLayout> arrayListLinearLayout = new ArrayList<>();
        final LinearLayout topLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowlanguage,null);
        final LinearLayout parentLinearLayout = (LinearLayout) topLinearLayout.findViewById(R.id.linHorRowLang);

        for (final Languages.LanguagesClass languagesClass : languageFrom) {

            LinearLayout newLinearLayout = (LinearLayout) inflater.inflate(R.layout.rowlayoutlanguageradio,null);
            ((ImageView)newLinearLayout.findViewById(R.id.imageViewRadio)).setImageResource(languagesClass.picture);
            ((TextView)newLinearLayout.findViewById(R.id.textViewRadio)).setText(languagesClass.language);
            RadioButton radioButton = (RadioButton) newLinearLayout.findViewById(R.id.radioButtonLanguage);
            radioButtons.add(radioButton);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,languagesClass.language);
                    onRadioButtonClicked(v,languagesClass.id);
                }
            });

            arrayListLinearLayout.add(newLinearLayout);
        }
        ((ImageView)parentLinearLayout.findViewById(R.id.imageViewDropDownLanguage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(v,arrayListLinearLayout,topLinearLayout);
            }
        });
        parentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickDropDownList(parentLinearLayout.findViewById(R.id.imageViewDropDownLanguage),
                        arrayListLinearLayout,topLinearLayout);
            }
        });
        return topLinearLayout;
    }
    public void onRadioButtonClicked(View view, int id) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton rbutton = ((RadioButton) view);
        for (RadioButton radioButton : radioButtons) {
            if (!radioButton.equals(rbutton)){
                radioButton.setChecked(false);
            }
        }
    }
    public void onclickDropDownList(View v,ArrayList<LinearLayout> arrayList,LinearLayout linearLayoutPar) {
        if(v.getRotation() == 180){
            v.setRotation(0);
            for (LinearLayout linearLayout : arrayList) {
                linearLayoutPar.removeView(linearLayout);

            }
        }else{
            v.setRotation(180);

            for (LinearLayout linearLayout : arrayList) {
                linearLayoutPar.addView(linearLayout);

            }


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
