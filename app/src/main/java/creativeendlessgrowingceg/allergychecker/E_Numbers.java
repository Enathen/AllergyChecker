package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link E_Numbers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link E_Numbers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class E_Numbers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String URL = "URL";
    private static final String INFO_ENUMBER = "ENUMBER";
    private static final String CHILD = "CHILD";
    private static final String US = "US";
    private static final String EU = "EU";
    private static final String NAME = "NAME";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String url;
    private String infoEnumber;
    private String name;
    private int eu;
    private int us;
    private boolean child;


    public E_Numbers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment E_Numbers.
     */
    // TODO: Rename and change types and number of parameters
    public static E_Numbers newInstance(String param1, String param2) {
        E_Numbers fragment = new E_Numbers();
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
            url = getArguments().getString(URL);
            name = getArguments().getString(NAME);
            infoEnumber = getArguments().getString(INFO_ENUMBER);
            eu = getArguments().getInt(EU);
            us = getArguments().getInt(US);
            child = getArguments().getBoolean(CHILD);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout inflate = (FrameLayout) inflater.inflate(R.layout.fragment_e__numbers, container, false);
        LinearLayout linearLayout = inflate.findViewById(R.id.linLayE_Numbers);
        ((TextView)linearLayout.findViewById(R.id.textViewLink)).setText(Html.fromHtml("<u>" + name + "</u>"));
        ((TextView)linearLayout.findViewById(R.id.textViewLink)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        ((TextView)linearLayout.findViewById(R.id.textViewEnumbers)).setText(infoEnumber);
        ImageView imageViewUS = (ImageView) linearLayout.findViewById(R.id.imageViewUS);
        ImageView imageViewEU = (ImageView) linearLayout.findViewById(R.id.ImageViewEU);
        ImageView imageViewCHILD = (ImageView) linearLayout.findViewById(R.id.imageViewChild);

        if(eu == 0){
            imageViewEU.setVisibility(View.INVISIBLE);
        } else if( eu == 2){
            imageViewEU.setImageDrawable(getActivity().getDrawable(R.drawable.da));
        }
        if(us == 0){
            imageViewUS.setVisibility(View.INVISIBLE);
        } else if( us == 2){
            imageViewUS.setImageDrawable(getActivity().getDrawable(R.drawable.da));
        }
        if(!child){
            imageViewCHILD.setVisibility(View.INVISIBLE);
        }
        return inflate;
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
