package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslateHelp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslateHelp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslateHelp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FrameLayout parentFrameLayout;
    private LinearLayout parentLinearLayout;

    public TranslateHelp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TranslateHelp.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslateHelp newInstance(String param1, String param2) {
        TranslateHelp fragment = new TranslateHelp();
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
        parentFrameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_translate_help, container, false);

        //insert everything to this linear layout
        parentLinearLayout = (LinearLayout) parentFrameLayout.findViewById(R.id.linlayoutFragTranslate);

        //getCategories();
        new addAllStringsNecessary(inflater,container,getContext()).execute();
        return parentFrameLayout;

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

    private class addAllStringsNecessary extends AsyncTask<String, Integer, ArrayList<String>> {
        private final LayoutInflater inflater;
        private final ViewGroup container;
        private final Context context;
        private ArrayList<LinearLayout> linearLayouts;
        private HashMap<String,EditText> editTextHashMap = new HashMap<>();

        public addAllStringsNecessary(LayoutInflater inflater, ViewGroup container, Context context) {

            this.inflater = inflater;
            this.container = container;
            this.context = context;
        }
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> strings = getAllStrings();
            linearLayouts = new ArrayList<>();
            for (String string : strings) {
                linearLayouts.add((LinearLayout) inflater.inflate(R.layout.fragmenttranslatehelprelative, container, false));

            }

            return strings;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {

            super.onProgressUpdate(values);
            // Do things like update the progress bar
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            int i =0;
            Collections.sort(result);
            for (String s : result) {
                ((TextView)linearLayouts.get(i).findViewById(R.id.textViewTranslate)).setText(s);
                parentLinearLayout.addView(linearLayouts.get(i));
                editTextHashMap.put(s,(EditText) linearLayouts.get(i).findViewById(R.id.editTextFragmentTranslate));
                i++;
            }
            Button button = new Button(context);
            button.setText(R.string.send);
            button.setBackgroundColor(context.getColor(R.color.colorPrimary));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick();
                }
            });
            parentLinearLayout.findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclick();

                }
            });
            parentLinearLayout.addView(button);
            parentLinearLayout.removeView(parentLinearLayout.findViewById( R.id.progressBarTranslate));




            super.onPostExecute(result);
        }

        private void onclick() {
            String string = "";
            for (String s : editTextHashMap.keySet()) {
                if(!(editTextHashMap.get(s)).getText().toString().equals("")){
                    string = string.concat(s +" : " + editTextHashMap.get(s).getText()+"\n");
                }
            }
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","AllergyCheckerCEGTranslate@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.nameIfyouWantToBeInHelperList)
                    + " : " + "\n" + getString(R.string.translatedFrom) + ": "+ Locale.getDefault().
                    getDisplayLanguage() + "\n" + string);
            startActivity(Intent.createChooser(emailIntent, getResources().getText(R.string.sendTipsFrom)));
        }

        public ArrayList<String> getAllStrings() {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(getString(R.string.permission_camera_rationale));
            arrayList.add(getString(R.string.no_camera_permission));
            arrayList.add(getString(R.string.nuts));
            arrayList.add(getString(R.string.almond));
            arrayList.add(getString(R.string.brazil));
            arrayList.add(getString(R.string.cocoa));
            arrayList.add(getString(R.string.hazelnut));
            arrayList.add(getString(R.string.macadamia));
            arrayList.add(getString(R.string.pecans));
            arrayList.add(getString(R.string.pili));
            arrayList.add(getString(R.string.pine));
            arrayList.add(getString(R.string.pistachios));
            arrayList.add(getString(R.string.soy));
            arrayList.add(getString(R.string.tiger));
            arrayList.add(getString(R.string.walnuts));
            arrayList.add(getString(R.string.sesame));
            arrayList.add(getString(R.string.tuna));
            arrayList.add(getString(R.string.salmon));
            arrayList.add(getString(R.string.caviar));
            arrayList.add(getString(R.string.anchovies));
            arrayList.add(getString(R.string.bass));
            arrayList.add(getString(R.string.catfish));
            arrayList.add(getString(R.string.cod));
            arrayList.add(getString(R.string.flounder));
            arrayList.add(getString(R.string.haddock));
            arrayList.add(getString(R.string.hake));
            arrayList.add(getString(R.string.halibut));
            arrayList.add(getString(R.string.herring));
            arrayList.add(getString(R.string.mahi));
            arrayList.add(getString(R.string.perch));
            arrayList.add(getString(R.string.pollock));
            arrayList.add(getString(R.string.pike));
            arrayList.add(getString(R.string.swordfish));
            arrayList.add(getString(R.string.sole));
            arrayList.add(getString(R.string.snapper));
            arrayList.add(getString(R.string.surimi));
            arrayList.add(getString(R.string.tilapia));
            arrayList.add(getString(R.string.trout));
            arrayList.add(getString(R.string.barnacle));
            arrayList.add(getString(R.string.crab));
            arrayList.add(getString(R.string.crawfish));
            arrayList.add(getString(R.string.krill));
            arrayList.add(getString(R.string.lobster));
            arrayList.add(getString(R.string.prawns));
            arrayList.add(getString(R.string.shrimp));
            arrayList.add(getString(R.string.scampi));
            arrayList.add(getString(R.string.abalone));
            arrayList.add(getString(R.string.clams));
            arrayList.add(getString(R.string.cockle));
            arrayList.add(getString(R.string.limpet));
            arrayList.add(getString(R.string.mussels));
            arrayList.add(getString(R.string.octopus));
            arrayList.add(getString(R.string.oysters));
            arrayList.add(getString(R.string.periwinkle));
            arrayList.add(getString(R.string.urchin));
            arrayList.add(getString(R.string.scallops));
            arrayList.add(getString(R.string.snails));
            arrayList.add(getString(R.string.squid));
            arrayList.add(getString(R.string.whelk));
            arrayList.add(getString(R.string.wheat));
            arrayList.add(getString(R.string.durum));
            arrayList.add(getString(R.string.emmer));
            arrayList.add(getString(R.string.rye));
            arrayList.add(getString(R.string.barley));
            arrayList.add(getString(R.string.triticale));
            arrayList.add(getString(R.string.malt));
            arrayList.add(getString(R.string.semolina));
            arrayList.add(getString(R.string.spelt));
            arrayList.add(getString(R.string.farina));
            arrayList.add(getString(R.string.farro));
            arrayList.add(getString(R.string.graham));
            arrayList.add(getString(R.string.kamut));
            arrayList.add(getString(R.string.einkorn));
            arrayList.add(getString(R.string.pear));
            arrayList.add(getString(R.string.tomato));
            arrayList.add(getString(R.string.strawberry));
            arrayList.add(getString(R.string.prune));
            arrayList.add(getString(R.string.pomegranate));
            arrayList.add(getString(R.string.pinapple));
            arrayList.add(getString(R.string.persimmon));
            arrayList.add(getString(R.string.peach));
            arrayList.add(getString(R.string.orange));
            arrayList.add(getString(R.string.melon));
            arrayList.add(getString(R.string.mango));
            arrayList.add(getString(R.string.lychee));
            arrayList.add(getString(R.string.kiwi));
            arrayList.add(getString(R.string.grape));
            arrayList.add(getString(R.string.date));
            arrayList.add(getString(R.string.fig));
            arrayList.add(getString(R.string.coconut));
            arrayList.add(getString(R.string.cherry));
            arrayList.add(getString(R.string.banana));
            arrayList.add(getString(R.string.apricot));
            arrayList.add(getString(R.string.acerola));
            arrayList.add(getString(R.string.avocado));
            arrayList.add(getString(R.string.shellfish));
            arrayList.add(getString(R.string.gluten));
            arrayList.add(getString(R.string.fish));
            arrayList.add(getString(R.string.milk));
            arrayList.add(getString(R.string.seeds));
            arrayList.add(getString(R.string.deleteHistory));
            arrayList.add(getString(R.string.scanPhotos));
            arrayList.add(getString(R.string.english));
            arrayList.add(getString(R.string.swedish));
            arrayList.add(getString(R.string.spanish));
            arrayList.add(getString(R.string.language));
            arrayList.add(getString(R.string.languageFrom));
            arrayList.add(getString(R.string.languageGeneral));
            arrayList.add(getString(R.string.definitelyContained));
            arrayList.add(getString(R.string.history));
            arrayList.add(getString(R.string.statistics));
            arrayList.add(getString(R.string.allergies));
            arrayList.add(getString(R.string.dontUse));
            arrayList.add(getString(R.string.scannedTextBelow));
            arrayList.add(getString(R.string.youCanUse));
            arrayList.add(getString(R.string.allergy));
            arrayList.add(getString(R.string.contained));
            arrayList.add(getString(R.string.times));
            arrayList.add(getString(R.string.probablyContained));
            arrayList.add(getString(R.string.fromWord));
            arrayList.add(getString(R.string.myAllergies));
            arrayList.add(getString(R.string.share));
            arrayList.add(getString(R.string.send));
            arrayList.add(getString(R.string.mustBeInEnglish));
            arrayList.add(getString(R.string.sendTipsFrom));
            arrayList.add(getString(R.string.welcomeMessageInvite));
            arrayList.add(getString(R.string.time));
            arrayList.add(getString(R.string.buckwheat));
            arrayList.add(getString(R.string.mustardSeed));
            arrayList.add(getString(R.string.poppySeed));
            arrayList.add(getString(R.string.sunflowerSeed));
            arrayList.add(getString(R.string.fruit));
            arrayList.add(getString(R.string.vegetables));
            arrayList.add(getString(R.string.cannotUseCamera));
            arrayList.add(getString(R.string.varning));
            arrayList.add(getString(R.string.thisAppOnlySave));
            arrayList.add(getString(R.string.mightContainOther));
            arrayList.add(getString(R.string.mightContainAllergies));
            arrayList.add(getString(R.string.vegetarian));
            arrayList.add(getString(R.string.vegan));
            arrayList.add(getString(R.string.citrus));
            arrayList.add(getString(R.string.haram));
            arrayList.add(getString(R.string.legumes));
            arrayList.add(getString(R.string.aspargus));
            arrayList.add(getString(R.string.bellPepper));
            arrayList.add(getString(R.string.cabbage));
            arrayList.add(getString(R.string.carrot));
            arrayList.add(getString(R.string.celery));
            arrayList.add(getString(R.string.garlic));
            arrayList.add(getString(R.string.lettuce));
            arrayList.add(getString(R.string.maize));
            arrayList.add(getString(R.string.potato));
            arrayList.add(getString(R.string.pumpkin));
            arrayList.add(getString(R.string.zucchini));
            arrayList.add(getString(R.string.pork));
            arrayList.add(getString(R.string.duck));
            arrayList.add(getString(R.string.goat));
            arrayList.add(getString(R.string.poultry));
            arrayList.add(getString(R.string.lamb));
            arrayList.add(getString(R.string.sausage));
            arrayList.add(getString(R.string.gelatin));
            arrayList.add(getString(R.string.dairy));
            arrayList.add(getString(R.string.egg));
            arrayList.add(getString(R.string.honey));
            arrayList.add(getString(R.string.lactoVegetarian));
            arrayList.add(getString(R.string.ovoVegetarian));
            arrayList.add(getString(R.string.lactoOvoVegetarian));
            arrayList.add(getString(R.string.demiVegetarian));
            arrayList.add(getString(R.string.polloVegetarian));
            arrayList.add(getString(R.string.pescoVegetarian));
            arrayList.add(getString(R.string.chicken));
            arrayList.add(getString(R.string.areYouSure));
            arrayList.add(getString(R.string.yes));
            arrayList.add(getString(R.string.no));
            arrayList.add(getString(R.string.flashon));
            arrayList.add(getString(R.string.cancel));
            arrayList.add(getString(R.string.inputIngredients));
            arrayList.add(getString(R.string.rate));
            arrayList.add(getString(R.string.pressMe));
            arrayList.add(getString(R.string.firstIntro));
            arrayList.add(getString(R.string.secondIntro));
            arrayList.add(getString(R.string.thirdIntro));
            arrayList.add(getString(R.string.tutorial));
            arrayList.add(getString(R.string.anis));
            arrayList.add(getString(R.string.coriander));
            arrayList.add(getString(R.string.cumin));
            arrayList.add(getString(R.string.fennel));
            arrayList.add(getString(R.string.parsley));
            arrayList.add(getString(R.string.ragweed));
            arrayList.add(getString(R.string.echinacea));
            arrayList.add(getString(R.string.artichoke));
            arrayList.add(getString(R.string.dandelions));
            arrayList.add(getString(R.string.hibiscus));
            arrayList.add(getString(R.string.chickpea));
            arrayList.add(getString(R.string.lentil));
            arrayList.add(getString(R.string.lupin));
            arrayList.add(getString(R.string.peanut));
            arrayList.add(getString(R.string.pea));
            arrayList.add(getString(R.string.soybean));
            arrayList.add(getString(R.string.lemon));
            arrayList.add(getString(R.string.lime));
            arrayList.add(getString(R.string.grapefruit));
            arrayList.add(getString(R.string.bacon));
            arrayList.add(getString(R.string.choletsorol));
            arrayList.add(getString(R.string.glycerol));
            arrayList.add(getString(R.string.hormones));
            arrayList.add(getString(R.string.lard));
            arrayList.add(getString(R.string.magnesiumStearate));
            arrayList.add(getString(R.string.monoGlycerides));
            arrayList.add(getString(R.string.pepsin));
            arrayList.add(getString(R.string.halal));
            arrayList.add(getString(R.string.start));
            arrayList.add(getString(R.string.holdFor));
            arrayList.add(getString(R.string.about));
            arrayList.add(getString(R.string.helpTranslate));
            arrayList.add(getString(R.string.onlyFillChanges));
            arrayList.add(getString(R.string.specialThanksTo));
            arrayList.add(getString(R.string.thisappUses));
            arrayList.add(getString(R.string.FAB));
            arrayList.add(getString(R.string.BkTree));
            arrayList.add(getString(R.string.OnBoardMe));
            arrayList.add(getString(R.string.licence));
            arrayList.add(getString(R.string.googleCamera));
            arrayList.add(getString(R.string.translatedFrom));
            arrayList.add(getString(R.string.nameIfyouWantToBeInHelperList));

            return arrayList;
        }
    }
}
