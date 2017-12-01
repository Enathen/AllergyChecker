package creativeendlessgrowingceg.allergychecker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import creativeendlessgrowingceg.allergychecker.FAB.FloatingToolbar;
import creativeendlessgrowingceg.allergychecker.bktree.BkTreeSearcher;
import creativeendlessgrowingceg.allergychecker.bktree.Metric;
import creativeendlessgrowingceg.allergychecker.bktree.MutableBkTree;
import creativeendlessgrowingceg.allergychecker.design.activity.OnboardingPagerActivity;

public class StartPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,HistoryFragment.OnFragmentInteractionListener
        ,StatisticsFragment.OnFragmentInteractionListener
        ,SettingsFragment.OnFragmentInteractionListener
        ,AllergyFragment.OnFragmentInteractionListener{
    private static final String TAG = "StartPage";
    private static final String SHARED_PREFS_NAME = "StartPage";
    private TextView suggestions;
    private TextView allergic;
    String newString = "";
    String allergicString = "";
    ArrayList<String> dateStrings = new ArrayList<>();
    SharedPreferences prefs;

    private InterstitialAd interstitialAd;
    private String Language = "";

    ArrayList<Integer> definitelyContained = new ArrayList<>();
    HashMap<Integer,LanguageString> arrayListAllergies;

    public StartPage(FragmentActivity activity) {
        prefs = activity.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }
    public StartPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean("firstTime", false)) {
            startActivity(new Intent(this, OnboardingPagerActivity.class));
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(this).edit();
            sharedPreferencesEditor.putBoolean(
                    "firstTime", true);
            sharedPreferencesEditor.apply();
        }

        Intent intent = getIntent();
        newString = intent.getStringExtra("newString");
        allergicString = intent.getStringExtra("allergicString");
        suggestions = (TextView) findViewById(R.id.ingredientsTextView);
        allergic = (TextView) findViewById(R.id.textViewFoundAllergies);
        if(allergicString != null){
            suggestions.setText(newString);
            allergic.setText(allergicString);
            if(allergicString.contains(getString(R.string.youCanUse))){
                allergic.setTextColor(getColor(R.color.colorAccent));
            }else{
                allergic.setTextColor(Color.RED);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final FloatingToolbar floatingToolbarMenuBuilder = (FloatingToolbar) findViewById(R.id.floatingToolbar);
        floatingToolbarMenuBuilder.attachFab(fab);
        newString = getString(R.string.startPageHeader);
        loadInterstitial();

        floatingToolbarMenuBuilder.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {
                View drawer =  findViewById(R.id.language);


            }

            @Override
            public void onItemLongClick(MenuItem item) {
                Log.d(TAG,"heasdasdsd");
            }
        });

        intent = getIntent();
        String str = intent.getStringExtra("location");


        if(str != null){

            str = str.replaceAll("[^\\p{L}\\p{Nd}\\s]+", "");
            String allergyString = str;
            String[] parts = allergyString.split("\\s+");
            Arrays.sort(parts);
            StringBuilder sb = new StringBuilder();
            for(String s:parts){
                sb.append(s);
                sb.append(" ");
            }

            allergyString = sb.toString().trim();
            Log.d(TAG,allergyString);
            Log.d(TAG,str);

            suggestions.setText(str);

            newString = str;
            str = str.toLowerCase();

            DateString dateString = new DateString(str);
            dateStrings = getArray();
            Log.d(TAG, String.valueOf(dateStrings.size()));
            dateStrings.add(dateString.string);
            Log.d(TAG, String.valueOf(dateStrings.size()));
            //setDateStrings(dateStrings);

            saveArray();
            checkStringAgainstAllergies(str);


        }else{
            str = intent.getStringExtra("HistoryFragment");
            if(str != null) {
                suggestions.setText(str);
                newString = str;
                checkStringAgainstAllergies(str);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setProfilePicture();

    }


    private void setProfilePicture() {
        new AllergyFragment(this,getImageViewHashMap(this),new SettingsFragment(this).getCategories()).setProfilePic();
    }


    private void displayInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("79C8186833AA41CD2C967FE87614751A").build());
    }

    public  void loadInterstitial(){
        interstitialAd = new InterstitialAd(StartPage.this);
        interstitialAd.setAdUnitId("ca-app-pub-3607354849437438/9852745111");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if(interstitialAd.isLoaded()){
                    interstitialAd.show();
                }else{
                    Log.d(TAG, "The interstitial wasn't loaded yet.");
                }

            }

            @Override
            public void onAdClosed() {
            }
        });

    }


    public void insertHashMapFromAllergyFragment(HashMap<Integer,LanguageString> hashMap){

        this.arrayListAllergies = hashMap;
        Log.d(TAG,"SAVED");
    }
    public HashMap<Integer,LanguageString> receiveHashMapFromAllergyFragment(){
        Log.d(TAG,"received");
        return arrayListAllergies;
    }
    private void checkStringAgainstAllergies(String str) {
        displayInterstitial();
        ( findViewById(R.id.progressBar3)).setVisibility(View.VISIBLE);

        Locale locale = new Locale(new SettingsFragment(this).getLanguageFromLFragment(this));
        final Locale newLocale = new Locale(locale.getLanguage());
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = this.getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        new MyTask(this,allergic, (ProgressBar) findViewById(R.id.progressBar3)).execute(str);

    }

    public ArrayList<String> getDateString(){
        for (String dateString : dateStrings) {
            Log.d(TAG,dateString);
        }

        return dateStrings;
    }
    public void setDateStrings(ArrayList<String> datastring){
        Log.d(TAG, String.valueOf(dateStrings.size()));
        for (String string : datastring) {
            if(!dateStrings.contains(string))
                dateStrings.add(string);
        }
        Log.d(TAG, String.valueOf(dateStrings.size()));
    }
    public boolean saveArray() {
        Collections.sort(dateStrings);
        for (String dateString : dateStrings) {
            Log.d(TAG,dateString);
        }
        prefs = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = prefs.edit();
        Set<String> set = new HashSet<>();
        set.addAll(dateStrings);
        mEdit1.putStringSet("list", set);
        return mEdit1.commit();
    }
    public ArrayList<String> getArray() {

        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("list", new HashSet<String>());

        return new ArrayList<>(set);
    }
    public void deleteHistory() {

        SharedPreferences.Editor mEdit1 = prefs.edit();


        mEdit1.remove("list");
        mEdit1.apply();

    }
    public ArrayList<String> getArrayFromHistory() {


        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = prefs.getStringSet("list", new HashSet<String>());

        return new ArrayList<>(set);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.language) {
            //Creating the instance of PopupMenu
            View drawer =  findViewById(R.id.language);
            PopupMenu popup = new PopupMenu(StartPage.this, drawer);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    Language = item.getTitle().toString();
                    Log.d(TAG, Language);
                    if(Language.equals("English")){
                        Locale locale = new Locale("en");
                        Locale.setDefault(locale);

                        Configuration config = new Configuration();
                        config.setLocale(locale);
                        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }else{
                        Locale locale = new Locale("sv");
                        Locale.setDefault(locale);

                        Configuration config = new Configuration();
                        config.setLocale(locale);
                        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                    Toast.makeText(
                            StartPage.this,
                            "Changed language to: " + item.getTitle(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return true;
                }
            });

            popup.show(); //showing popup menu
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        suggestions.setText("");
        allergic.setText("");

        ((TextView) findViewById(R.id.textViewFoundAllergies)).setText("");
        if (id == R.id.nav_camera) {


            Log.d(TAG, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
            /*while (getSupportFragmentManager().getBackStackEntryCount() != 0)
            getSupportFragmentManager().popBackStackImmediate();*/
            Intent intent = new Intent(this, StartPage.class);
            intent.putExtra("newString", newString);
            intent.putExtra("allergicString", allergicString);
            this.startActivity(intent);
        } else if (id == R.id.history) {
            fragment = new HistoryFragment(this); setTitle("History");
        } else if (id == R.id.languageMenu) {

            fragment = new SettingsFragment(); setTitle("Language");
        }else if (id == R.id.allergies) {

            fragment = new AllergyFragment(this,getImageViewHashMap(),new SettingsFragment(this).getCategories()); setTitle("Allergies");
        }else if (id == R.id.tutorial){
            startActivity(new Intent(this, OnboardingPagerActivity.class));
        }
        else if (id == R.id.nav_rate) {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
        }
        else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = "\n" + getString(R.string.welcomeMessageInvite) + "\n\n";
            sAux = sAux + "//play.google.com/store/apps/details?id="+ this.getPackageName()+"\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "AllergyCheckerCEG@gmail.com" });
            startActivity(Intent.createChooser(intent, getResources().getText(R.string.sendTipsFrom)));

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.startPageFrame, fragment).addToBackStack(null).commit();
            fragmentManager.executePendingTransactions();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private HashMap<Integer,ImageView> getImageViewHashMap(StartPage startPage) {
        HashMap<Integer,ImageView> imageViewHashMap = new HashMap<>();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View parentView =  navigationView.getHeaderView(0);
        imageViewHashMap.put(0, (ImageView) parentView.findViewById(R.id.imageViewNav1));
        imageViewHashMap.put(0, (ImageView) parentView.findViewById(R.id.imageViewNav1));
        imageViewHashMap.put(1, (ImageView) parentView.findViewById(R.id.imageViewNav2));
        imageViewHashMap.put(2, (ImageView) parentView.findViewById(R.id.imageViewNav3));
        imageViewHashMap.put(3, (ImageView) parentView.findViewById(R.id.imageViewNav4));
        imageViewHashMap.put(4, (ImageView) parentView.findViewById(R.id.imageViewNav5));
        imageViewHashMap.put(5, (ImageView) parentView.findViewById(R.id.imageViewNav6));
        imageViewHashMap.put(6, (ImageView) parentView.findViewById(R.id.imageViewNav7));
        imageViewHashMap.put(7, (ImageView) parentView.findViewById(R.id.imageViewNav8));
        return imageViewHashMap;
    }

    public HashMap<Integer,ImageView> getImageViewHashMap(){
        HashMap<Integer,ImageView> imageViewHashMap = new HashMap<>();


        imageViewHashMap.put(0, (ImageView) findViewById(R.id.imageViewNav1));
        imageViewHashMap.put(1, (ImageView) findViewById(R.id.imageViewNav2));
        imageViewHashMap.put(2, (ImageView) findViewById(R.id.imageViewNav3));
        imageViewHashMap.put(3, (ImageView) findViewById(R.id.imageViewNav4));
        imageViewHashMap.put(4, (ImageView) findViewById(R.id.imageViewNav5));
        imageViewHashMap.put(5, (ImageView) findViewById(R.id.imageViewNav6));
        imageViewHashMap.put(6, (ImageView) findViewById(R.id.imageViewNav7));
        imageViewHashMap.put(7, (ImageView) findViewById(R.id.imageViewNav8));
        return imageViewHashMap;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        }
    }


    public class DateString {
        String string;

        DateString(String string) {
            Calendar calendar = Calendar.getInstance();
            calendar.get(Calendar.DAY_OF_MONTH);
            Date date = calendar.getTime();

            String dateTime = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
            dateTime = dateTime.concat(" " + DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date));

            Log.d(TAG, "DATE:" + dateTime);
            String newString = dateTime;
            newString = newString.concat(" " + string);
            this.string = newString;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private class MyTask extends AsyncTask<String, Integer, String> {
        private StartPage mContext;
        private TextView textView;
        private ProgressBar viewById;


        public MyTask(StartPage context, TextView textView, ProgressBar viewById) {
            mContext = context;

            this.textView = textView;
            this.viewById = viewById;
        }
        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread
        Metric<String> hammingDistance = new Metric<String>() {
            @Override
            public int distance(String x, String y) {
                if (!(x.length() != y.length() || x.length()-1 != y.length() || x.length()+1 != y.length())) {
                    throw new IllegalArgumentException();
                }

                int distance = 0;


                if(y.length() > x.length()){
                    for (int i = 0; i < y.length()-1; i++)
                        if (x.charAt(i) != y.charAt(i)) {
                            distance++;
                        }
                    distance++;
                }else if (y.length() < x.length()){
                    for (int i = 0; i < x.length()-1; i++)
                        if (x.charAt(i) != y.charAt(i)) {
                            distance++;
                        }
                    distance++;
                }else{
                    for (int i = 0; i < x.length(); i++)
                        if (x.charAt(i) != y.charAt(i)) {
                            distance++;
                        }
                }
                return distance;
            }
        };
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String str = params[0];
            String[] splitStr = str.split("\\s+");
            TreeMap<Integer,HashSet<String>> hashSetString = new TreeMap<>();
            HashSet<String> hashie = new HashSet<>();
            for (int i = 0; i < splitStr.length; i++) {
                if(splitStr.length-1 != i){
                    hashie.add(splitStr[i]+ splitStr[i+1]);
                    if(hashSetString.containsKey((splitStr[i]+ splitStr[i+1]).length())){
                        hashSetString.put((splitStr[i]+ splitStr[i+1]).length(),hashSetString.get((splitStr[i]+ splitStr[i+1]).length())).add(splitStr[i]+ splitStr[i+1]);
                    }else{
                        HashSet<String> hashset = new HashSet<>();
                        hashset.add(splitStr[i]+ splitStr[i+1]);
                        hashSetString.put((splitStr[i]+ splitStr[i+1]).length(),hashset);
                    }
                    if(splitStr[i].equals("de") && splitStr.length>0){
                        hashie.add(splitStr[i-1]+splitStr[i]+ splitStr[i+1]);
                        if(hashSetString.containsKey((splitStr[i-1]+splitStr[i]+ splitStr[i+1]).length())){
                            hashSetString.put((splitStr[i-1]+splitStr[i]+ splitStr[i+1]).length(),hashSetString.get((splitStr[i-1]+splitStr[i]+ splitStr[i+1]).length())).add((splitStr[i-1]+splitStr[i]+ splitStr[i+1]));
                        }else{
                            HashSet<String> hashset = new HashSet<>();
                            hashset.add(splitStr[i-1]+splitStr[i]+ splitStr[i+1]);
                            hashSetString.put((splitStr[i-1]+splitStr[i]+ splitStr[i+1]).length(),hashset);
                        }
                    }

                }
                hashie.add(splitStr[i]);
                if(hashSetString.containsKey((splitStr[i]).length())){
                    hashSetString.put((splitStr[i]).length(),hashSetString.get((splitStr[i]).length())).add(splitStr[i]);
                }else{
                    HashSet<String> hashset = new HashSet<>();
                    hashset.add(splitStr[i]);
                    hashSetString.put((splitStr[i]).length(),hashset);
                }


            }

            ArrayList<Locale> listOfLanguages = new SettingsFragment(mContext).getCategories();
            Log.d(TAG,"TIMEReceiveString");
            HashSet<Integer> hashSet = new AllergyFragment(mContext).getCategoriesFromOtherClass();

            HashMap<Integer,HashSet<String>> allergies = new HashMap<>();

            int counter = 0;
            int i = 0;
            int length = 0;
            for (int id : hashSet) {
                publishProgress(hashSet.size(), counter);
                for (Locale locale : listOfLanguages) {
                    /*HashSet<String> string = spellCheckAllergy.permuteString(locale.getLanguage(),
                            getStringByLocal(StartPage.this, id,
                                    locale.getLanguage()));*/
                    //LangString langString = new LangString(locale.getLanguage(),true,id);
                    //langString.allPossibleDerivationsOfAllergen = string;
                    String localeString =getStringByLocal(StartPage.this,id,locale.getLanguage());
                    if(length< localeString.length()){
                        length = localeString.length()+2;
                    }
                    if(allergies.containsKey(localeString.length())){
                        allergies.put((localeString).length(),allergies.get((localeString).length())).add(localeString);
                    }else{
                        HashSet<String> hashSetn = new HashSet<>();
                        hashSetn.add(localeString);
                        allergies.put((localeString).length(),hashSetn);
                    }
                }
                if(i % 2 == 0){
                    counter++;
                }
                i++;

            }
            boolean dontEat = false;
            String outputString = "";
            HashSet<String> alreadyContainedAllergies = new HashSet<>();

            long start = System.currentTimeMillis();
            for (Integer s : hashSetString.keySet()) {
                //Log.d(TAG, "swag: "+ s);
                if(length<s){
                    continue;
                }
                MutableBkTree<String> bkTree = new MutableBkTree<>(hammingDistance);
                bkTree.addAll(hashSetString.get(s));
                BkTreeSearcher<String> searcher = new BkTreeSearcher<>(bkTree);
                if(allergies.containsKey(s) && s>4){
                    for (String strings : allergies.get(s)) {
                        Set<BkTreeSearcher.Match<? extends String>> matches;

                        if(s>4 && s<10){
                            matches = searcher.search(strings, 1);

                        }else{
                            matches = searcher.search(strings,2);
                        }
                        for (BkTreeSearcher.Match<? extends String> match : matches){
                            if(!dontEat){
                                outputString = outputString.concat(getString(R.string.probablyContained) + ":\n");
                            }
                            dontEat = true;
                            outputString = outputString.concat(strings + " " + getString(R.string.fromWord)
                                    + " "  + match.getMatch() + "\n");
                            Log.d(TAG, "doInBackground: "+match.getMatch() + " distance:"+ + match.getDistance() + " from " + strings);
                            alreadyContainedAllergies.add(strings);

                        }

                    }
                }
                if(allergies.containsKey(s+1) && s+1>4) {
                    for (String strings : allergies.get(s + 1)) {
                        Set<BkTreeSearcher.Match<? extends String>> matches;
                        if(s<10){
                            matches = searcher.search(strings, 2);
                        }else {
                            matches = searcher.search(strings, 3);
                        }
                        for (BkTreeSearcher.Match<? extends String> match : matches) {
                            if(!dontEat){
                                outputString = outputString.concat(getString(R.string.probablyContained) + ":\n");
                            }
                            dontEat = true;
                            outputString = outputString.concat(strings + " " + getString(R.string.fromWord)
                                    + " " + match.getMatch() + "\n");
                            Log.d(TAG, "spank: " + match.getMatch() + " distance:" + +match.getDistance() + " from " + strings);
                            alreadyContainedAllergies.add(strings);

                        }

                    }
                }
                if(allergies.containsKey(s-1) && s-1>5 ) {
                    for (String strings : allergies.get(s - 1)) {
                        Set<BkTreeSearcher.Match<? extends String>> matches;
                        if(s<14){
                            matches= searcher.search(strings, 2);

                        }else{
                            matches= searcher.search(strings, 3);


                        }
                        for (BkTreeSearcher.Match<? extends String> match : matches) {
                            if(!dontEat){
                                outputString = outputString.concat(getString(R.string.probablyContained) + ":\n");
                            }
                            dontEat = true;
                            outputString = outputString.concat(strings + " " + getString(R.string.fromWord)
                                    + " " + match.getMatch() + "\n");
                            Log.d(TAG, "SWAGx: " + match.getMatch() + " distance:" + +match.getDistance() + " from " + strings);
                            alreadyContainedAllergies.add(strings);

                        }

                    }
                }



            }
            long stop = System.currentTimeMillis();
            Log.d(TAG, "TIME: "+(stop-start));
            start = System.currentTimeMillis();
            for (String s : hashie) {

                for (int key : allergies.keySet() ) {

                    for (String s1 : allergies.get(key)) {
                        if(alreadyContainedAllergies.contains(s1)){
                            continue;
                        }
                        if (s.contains(s1)){
                            if(!dontEat){
                                outputString = outputString.concat(getString(R.string.probablyContained) + ":\n");
                            }
                            dontEat = true;
                            outputString = outputString.concat(s1 + " " + getString(R.string.fromWord)
                                    + " " + s + "\n");
                            alreadyContainedAllergies.add(s1);
                        }
                    }
                }
            }
            stop = System.currentTimeMillis();
            Log.d(TAG, "TIME: "+(stop-start));

            // HashMap<String, LangString> allergies = spellCheckAllergy.permuteStringi(mContext,arrayListAllergies);
            /*if(allergies != null) {

                boolean b;
                counter = hashSetString.size()/2;
                i = 0;
                for (String string : hashSetString) {

                    if(i % 2 == 0){
                        counter++;
                    }
                    i++;
                    b = false;
                    publishProgress(hashSetString.size(), counter);

                    for (String extraKey : allergies.keySet()){

                        if(extraKey.replaceAll("\\s+","").equals(string)){

                            allergies.get(extraKey).found++;
                            if(allergies.get(extraKey).found == 1){
                                if(!definitelyContained.contains(allergies.get(extraKey).id)){
                                    outputString = outputString.concat(getString(R.string.definitelyContained)+" "+ getString(allergies.get(extraKey).id) + "\n");
                                    definitelyContained.add(allergies.get(extraKey).id);

                                }
                            }
                            b = true;
                            break;
                        }
                    }
                    if(!b){
                        for (String key : allergies.keySet() ) {

                            if(string.contains(key)){

                                allergies.get(key).found++;
                                if(allergies.get(key).found == 1){
                                    outputString = outputString.concat(getString(R.string.probablyContained) + " "+
                                            getString(allergies.get(key).id) + " " + getString(R.string.fromWord)
                                            + " " + string + "\n");


                                }
                            }
                            if (allergies.get(key).allPossibleDerivationsOfAllergen.contains(string)) {
                                allergies.get(key).found++;
                                if(allergies.get(key).found == 1) {
                                    outputString = outputString.concat(getString(R.string.probablyContained) + " "+
                                            getString(allergies.get(key).id) + " " + getString(R.string.fromWord)
                                            + " "  + string + "\n");

                                }
                            }
                        }
                    }

                }
                for (String key : allergies.keySet()) {
                    if(allergies.get(key).found>0) {
                        if(!Locale.getDefault().getLanguage().equals(allergies.get(key).language)) {
                            if(allergies.containsKey(getString(allergies.get(key).id).toLowerCase())){
                                allergies.get( getString(allergies.get(key).id).toLowerCase()).found += allergies.get(key).found;
                                allergies.get(key).found = 0;
                            }
                        }
                    }
                }
                outputString = outputString.concat("\n");
                for (String key : allergies.keySet()){

                    if(allergies.get(key).found>0){
                        dontEat = true;
                    }
                }

            }*/
            if(dontEat){
                outputString = outputString.concat("1");
            }else{
                outputString = outputString.concat("0");
            }
            return outputString;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            double i = ((double)values[1]/(double)values[0]) * 100;

            viewById.setProgress((int) i);
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String outputString) {
            viewById.setVisibility(View.INVISIBLE);
            super.onPostExecute(outputString);
            String dontEat = outputString.substring(outputString.length()-1);
            outputString = outputString.substring(0, outputString.length() -1);
            if(dontEat.equals("1")){

                outputString = outputString.concat(getString(R.string.dontUse) + "\n");
                outputString = outputString.concat("\n" +getString(R.string.mightContainOther)+ "\n");
                outputString = outputString.concat(getString(R.string.scannedTextBelow)+ "\n");
                textView.setTextColor(Color.RED);
                textView.setText(outputString);
            }else{
                outputString = outputString.concat(getString(R.string.youCanUse)+"\n");
                outputString = outputString.concat("\n" + getString(R.string.mightContainAllergies)+ "\n");
                outputString = outputString.concat(getString(R.string.scannedTextBelow)+"\n");
                textView.setTextColor(getColor(R.color.colorAccent));
                textView.setText(outputString);
            }
            allergicString = outputString;
            allergic.setText(outputString);
            // Do things like hide the progress bar or change a TextView
        }
    }
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getStringByLocal(Activity context, int id, String locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));


        return context.createConfigurationContext(configuration).getResources().getString(id).toLowerCase().replaceAll("\\s+","");
    }
}
