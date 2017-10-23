package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.TextView;
import android.widget.Toast;

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
        ,SpellCheckerSession.SpellCheckerSessionListener
        ,HistoryFragment.OnFragmentInteractionListener
        ,StatisticsFragment.OnFragmentInteractionListener
        ,SettingsFragment.OnFragmentInteractionListener
        ,AllergyFragment.OnFragmentInteractionListener{
    private static final String TAG = "StartPage";
    private static final String SHARED_PREFS_NAME = "StartPage";
    private TextView suggestions;
    String newString= "Ingredients";
    ArrayList<String> dateStrings = new ArrayList<>();
    SharedPreferences prefs;
    ArrayList<String> ingredientsAllergy = new ArrayList<>();
    private String Language = "";
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

    private void checkStringAgainstAllergies(String str) {
        String[] splitStr = str.split("\\s+");

        HashMap<String,ArrayList<String>> arrayListAllergies = new AllergyFragment(this).getArrayListFromAllCheckedAllergies();
        SpellCheckAllergy spellCheckAllergy = new SpellCheckAllergy();
        if(!arrayListAllergies.isEmpty()) {
            HashMap<String, ArrayList<String>> allergies = spellCheckAllergy.permuteString(arrayListAllergies);
            String outputString = "";
            for (String string : splitStr) {
                for (String key : allergies.keySet()) {
                    boolean shortcut = false;
                    for (String extraKey : allergies.keySet()){
                        if(extraKey.equals(string)){
                            outputString = outputString.concat("Allergies Contained: "+ extraKey + "\n");
                            Log.d(TAG, "ALLERGI: " + outputString);
                            shortcut = true;
                            break;
                        }
                    }
                    if(string.contains(key) && !shortcut){
                        outputString = outputString.concat("Allergies highly certainly contained Inside: "+ key + " from Word: " + string + "\n");
                        Log.d(TAG, "ALLERGI: " + outputString);
                        break;
                    }
                    if(!shortcut){
                        if (allergies.get(key).contains(string)) {
                            outputString = outputString.concat("Allergies certainly contained: "+ key + " from Word: " + string + "\n");
                            Log.d(TAG, "ALLERGI: " + outputString);
                            break;
                        }
                    }else{
                        break;
                    }
                }
            }
            ((TextView) findViewById(R.id.textViewFoundAllergies)).setText(outputString);
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
    private void fetchSuggestionsFor(final String input){
        Locale locale = new Locale("sv","SE"); //whatever language
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        /*Locale[] test = Locale.getAvailableLocales();
        for (Locale locale1 : test) {
            Log.d(TAG,locale1.getLanguage());
            Log.d(TAG,locale1.getCountry());
        }*/
        config.setLocale(locale);
        Log.d(TAG,locale.getLanguage()+ " "+ locale.getCountry());
        TextServicesManager tsm = (TextServicesManager) getSystemService(TEXT_SERVICES_MANAGER_SERVICE);
        SpellCheckerSession spellCheckerSession = null;
        if(locale.getLanguage().equals("sv")){
            spellCheckerSession = tsm.newSpellCheckerSession(null, locale, this, false);

        }else{
            spellCheckerSession = tsm.newSpellCheckerSession(null, Locale.ENGLISH, this, true);
        }

        if(spellCheckerSession == null){
            Log.d(TAG,"SPELL CHECKER NULL");
        }
        if (spellCheckerSession != null) {
            spellCheckerSession.getSentenceSuggestions(
                    new TextInfo[]{ new TextInfo(input) },
                    5
            );
        }
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
        ((TextView) findViewById(R.id.textViewFoundAllergies)).setText("");
        if (id == R.id.nav_camera) {
            suggestions.setText(newString);
            Log.d(TAG, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
            while (getSupportFragmentManager().getBackStackEntryCount() != 0)
                getSupportFragmentManager().popBackStackImmediate();
            setTitle("AllergyChecker");
        } else if (id == R.id.history) {
            fragment = new HistoryFragment(); setTitle("History");


        } else if (id == R.id.statistics) {
            fragment = new StatisticsFragment(); setTitle("Statistics");
        } else if (id == R.id.nav_manage) {
            fragment = new SettingsFragment(); setTitle("Settings");
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
    public void onGetSuggestions(SuggestionsInfo[] results) {

    }

    @Override
    public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] results) {
        final StringBuffer sb = new StringBuffer("");


        for(SentenceSuggestionsInfo result:results){
            int n = result.getSuggestionsCount();

            //Log.d(TAG,"N="+ String.valueOf(n));
            for(int i=0; i < n; i++){
                int m = result.getSuggestionsInfoAt(i).getSuggestionsCount();
                //Log.d(TAG, "M="+String.valueOf(m));
                for(int k=0; k < m; k++) {
                    ingredientsAllergy.add(result.getSuggestionsInfoAt(i).getSuggestionAt(k));
                    Log.d(TAG,"TextScannedSuggestions: " + result.getSuggestionsInfoAt(i).getSuggestionAt(k));

                }
            }
        }


        Log.d(TAG,"String From Photo: "+sb.toString());
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                suggestions.append(sb.toString());
            }
        });*/
    }
    private void onGetSentence(){

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
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
