package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import creativeendlessgrowingceg.allergychecker.FAB.FloatingToolbar;
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

        //first time user enters app.
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display our OnboardingFragment


        if (!sharedPreferences.getBoolean("firstTime", false)) {
            startActivity(new Intent(this, OnboardingPagerActivity.class));
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(this).edit();
            sharedPreferencesEditor.putBoolean(
                    "firstTime", true);
            sharedPreferencesEditor.apply();
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

        Intent intent = getIntent();
        String str = intent.getStringExtra("location");
        suggestions = (TextView) findViewById(R.id.ingredientsTextView);
        allergic = (TextView) findViewById(R.id.textViewFoundAllergies);


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

        /*Locale locale = new Locale(new SettingsFragment(this).getLanguageFromLFragment(this));
        final Locale newLocale = new Locale(locale.getLanguage());
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = this.getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());*/
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
            suggestions.setText(newString);
            allergic.setText(allergicString);

            Log.d(TAG, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
            while (getSupportFragmentManager().getBackStackEntryCount() != 0)
                getSupportFragmentManager().popBackStackImmediate();
            setTitle("Allergy Checker");


            /*Locale locale = new Locale(new SettingsFragment(getApplicationContext()).getLanguageFromLFragment(getApplicationContext()));
            final Locale newLocale = new Locale(locale.getLanguage());
            Locale.setDefault(newLocale);
            final Configuration config = new Configuration();
            config.locale = newLocale;

            final Resources res = getApplicationContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());*/
        } else if (id == R.id.history) {
            fragment = new HistoryFragment(this); setTitle("History");
        } else if (id == R.id.languageMenu) {

            fragment = new SettingsFragment(); setTitle("Language");
        }else if (id == R.id.allergies) {

            fragment = new AllergyFragment(this,getImageViewHashMap(),new SettingsFragment(this).getCategories()); setTitle("Allergies");
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


    public class DateString{
        String string;

        DateString(String string){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 10);
            Date date = calendar.getTime();

            String dateTime = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
            dateTime = dateTime.concat(" " +DateFormat.getTimeInstance(DateFormat.MEDIUM).format(date));

            Log.d(TAG,"DATE:"+ dateTime);
            String newString = dateTime;
            newString = newString.concat(" " + string);
            this.string = newString;
        }
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
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            String str = params[0];
            String[] splitStr = str.split("\\s+");
            HashSet<String> hashSetString = new HashSet<>();
            for (int i = 0; i < splitStr.length; i++) {
                if(splitStr.length-1 != i){
                    hashSetString.add(splitStr[i]+ splitStr[i+1]);

                }
                hashSetString.add(splitStr[i]);


            }
            for (String s : hashSetString) {
                Log.d(TAG, "HASHSET" + s);
            }

            ArrayList<Locale> listOfLanguages = new SettingsFragment(mContext).getCategories();

            //// TODO: 2017-11-08 if language changed after implement new one
            //arrayListAllergies = new AllergyFragment(this).getArrayListFromAllCheckedAllergies(listOfLanguages,StartPage.this,Locale.getDefault());

            SpellCheckAllergy spellCheckAllergy = new SpellCheckAllergy();
            Log.d(TAG,"TIMEReceiveString");
            HashMap<String, LangString> allergies = new AllergyFragment(mContext, listOfLanguages).getCategoriesFromOtherClass();
           // HashMap<String, LangString> allergies = spellCheckAllergy.permuteStringi(mContext,arrayListAllergies);
            Log.d(TAG,"TIMEReceiveString");
            String outputString = "";
            boolean dontEat = false;
            if(allergies != null) {



                boolean b = false;
                int i =0;
                for (String string : hashSetString) {
                    i++;
                    b = false;
                    publishProgress(hashSetString.size(), i);
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
                        /*if(allergies.get(key).found==1){
                            outputString = outputString.concat(getString(allergies.get(key).id)+ " " + getString(R.string.contained) + " "+
                                    allergies.get(key).found +" "+ getString(R.string.time) + ".\n");
                            allergies.get(key).found = 0;

                        }
                        else{

                            //if(Locale.getDefault().getLanguage().equals(allergies.get(key).language)){
                            outputString = outputString.concat(getString(allergies.get(key).id)+ " " + getString(R.string.contained) + " "+
                                    allergies.get(key).found +" "+ getString(R.string.times) + ".\n");
                            allergies.get(key).found = 0;
                            dontEat = true;
                        }*/
                        dontEat = true;

                    }
                }

            }
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
                outputString = outputString.concat("\n" + getString(R.string.mightContainAllergies)+ "\n");
                outputString = outputString.concat(getString(R.string.youCanUse)+"\n");
                outputString = outputString.concat(getString(R.string.scannedTextBelow)+"\n");
                textView.setTextColor(getColor(R.color.colorAccent));
                textView.setText(outputString);
            }
            allergicString = outputString;
            allergic.setText(outputString);
            // Do things like hide the progress bar or change a TextView
        }
    }
}
