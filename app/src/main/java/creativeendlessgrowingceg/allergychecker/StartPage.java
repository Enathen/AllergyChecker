package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import creativeendlessgrowingceg.allergychecker.camera.OcrCaptureActivity;

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
    ArrayList<String> ingredientsAllergy = new ArrayList<>();
    private InterstitialAd interstitialAd;
    private String Language = "";
    public int clickAmount = 0;
    public StartPage(FragmentActivity activity) {
        prefs = activity.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }
    public StartPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        newString = getString(R.string.startPageHeader);
        loadInterstitial();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View drawer = (View) findViewById(R.id.language);



                PopupMenu popup = new PopupMenu(StartPage.this, drawer);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.use_flash, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                                StartPage.this,
                                "Now using: " + item.getTitle(),
                                Toast.LENGTH_SHORT
                        ).show();
                        boolean flash = false;
                        Log.d(TAG, item.getTitle().toString());
                        if(item.getTitle().toString().equals("Flash"))
                            flash = true;
                        else
                            flash = false;

                        Intent intent = new Intent(getBaseContext(), OcrCaptureActivity.class);
                        intent.putExtra("EXTRA_SESSION_ID", flash);
                        startActivity(intent);
                        return true;
                    }
                });




                popup.show(); //showing popup menu



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
            Calendar calendar = Calendar.getInstance();
            DateString dateString = new DateString(calendar.getTime(),str);
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
    }

    private void displayInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().build());
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
    private void checkStringAgainstAllergies(String str) {

        displayInterstitial();

        String[] splitStr = str.split("\\s+");
        HashMap<String,LanguageString> arrayListAllergies = null;
        ArrayList<Locale> listOfLanguages = new SettingsFragment(this).getCategories();
        for (Locale listOfLanguage : listOfLanguages) {
            Log.d(TAG,listOfLanguage.getLanguage());
        }
        arrayListAllergies = new AllergyFragment(this).getArrayListFromAllCheckedAllergies(listOfLanguages,StartPage.this);
        SpellCheckAllergy spellCheckAllergy = new SpellCheckAllergy();
        if(arrayListAllergies != null) {
            HashMap<String, LanguageString> allergies = spellCheckAllergy.permuteString(arrayListAllergies);

            String outputString = "";
            boolean b = false;
            for (String string : splitStr) {

                for (String extraKey : allergies.keySet()){
                    if(extraKey.equals(string)){

                        allergies.get(extraKey).found++;
                        if(allergies.get(extraKey).found == 1){

                            outputString = outputString.concat(getString(R.string.definitelyContained)+ getString(allergies.get(extraKey).id) + "\n");
                            Log.d(TAG, "ALLERGI: " + outputString);
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
                                outputString = outputString.concat("Probably contained: "+ getString(allergies.get(key).id) + " from Word: " + string + "\n");
                                Log.d(TAG, "ALLERGI: " + outputString);
                                break;

                            }
                        }
                        if (allergies.get(key).allPossibleDerivationsOfAllergen.contains(string)) {
                            allergies.get(key).found++;
                            if(allergies.get(key).found == 1) {
                                outputString = outputString.concat("Probably contained: " + getString(allergies.get(key).id) + " from Word: " + string + "\n");
                                Log.d(TAG, "ALLERGI: " + outputString);
                                break;
                            }
                        }
                    }
                }

            }
            boolean dontEat = false;
            boolean incorrectLanguage = false;
            for (String key : allergies.keySet()) {
                if(allergies.get(key).found>0) {
                    if(!Locale.getDefault().getLanguage().equals(allergies.get(key).language)) {
                        if(allergies.containsKey(getString(allergies.get(key).id).toLowerCase())){
                            allergies.get( getString(allergies.get(key).id).toLowerCase()).found += allergies.get(key).found;

                        }else{
                            incorrectLanguage = true;
                        }
                    }
                }
            }
            outputString = outputString.concat("\n");
            for (String key : allergies.keySet()){

                if(allergies.get(key).found>0){
                    if(!incorrectLanguage){
                        if(Locale.getDefault().getLanguage().equals(allergies.get(key).language)){
                            outputString = outputString.concat("Allergy: "+ getString(allergies.get(key).id)+ " Contained " +
                                    allergies.get(key).found + " times.\n");
                            allergies.get(key).found = 0;
                            dontEat = true;

                        }

                    }else{
                        outputString = outputString.concat("Allergy: " + allergies.get(key).language + " "+ getString(allergies.get(key).id)+ " Contained " +
                                allergies.get(key).found + " times.\n");
                        allergies.get(key).found = 0;
                        dontEat = true;
                    }
                }
            }
            if(dontEat){
                outputString = outputString.concat("\nDon't use!\n");
                outputString = outputString.concat("\nScanned text below:\n");
                ((TextView) findViewById(R.id.textViewFoundAllergies)).setTextColor(Color.RED);
                ((TextView) findViewById(R.id.textViewFoundAllergies)).setText(outputString);
            }else{
                if(allergies.isEmpty()){
                    outputString = "\nYou have no allergies selected! But ";
                }
                outputString = outputString.concat("You can use it!\n");
                outputString = outputString.concat("\nScanned text below:\n");
                ((TextView) findViewById(R.id.textViewFoundAllergies)).setTextColor(getColor(R.color.colorAccent));
                ((TextView) findViewById(R.id.textViewFoundAllergies)).setText(outputString);
            }
            allergicString = outputString;
            allergic.setText(outputString);
        }
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
            View drawer = (View) findViewById(R.id.language);
            PopupMenu popup = new PopupMenu(StartPage.this, drawer);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    Language = item.getTitle().toString();
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
            setTitle("AllergyChecker");



        } else if (id == R.id.history) {
            fragment = new HistoryFragment(); setTitle("History");


        } else if (id == R.id.statistics) {
            fragment = new StatisticsFragment(); setTitle("Statistics");
        } else if (id == R.id.languageMenu) {
            fragment = new SettingsFragment(); setTitle(R.string.language);
        }else if (id == R.id.allergies) {
            fragment = new AllergyFragment(this); setTitle("Allergy");
        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

    public void profilOnClick(View view) {
        Log.d(TAG, "HEEEj");
    }

    public class DateString{
        String string;

        DateString(Date date, String string){
            String newString = date.toString();
            newString = newString.concat(string);
            this.string = newString;
        }
    }
}
