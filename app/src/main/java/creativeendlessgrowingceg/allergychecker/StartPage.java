package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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

import java.util.Locale;

import creativeendlessgrowingceg.allergychecker.camera.OcrCaptureActivity;

public class StartPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,SpellCheckerSession.SpellCheckerSessionListener
        ,HistoryFragment.OnFragmentInteractionListener
        ,StatisticsFragment.OnFragmentInteractionListener
        ,SettingsFragment.OnFragmentInteractionListener
        ,AllergyFragment.OnFragmentInteractionListener{
    private static final String TAG = "StartPage";
    private TextView suggestions;
    String newString= "Ingredients";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean firstTime = false;
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
/*
            String[] parts = str.split("\\s+");
            Arrays.sort(parts);
            StringBuilder sb = new StringBuilder();
            for(String s:parts){
                sb.append(s);
                sb.append(" ");
            }

            str = sb.toString().trim();*/
            suggestions.setText(str);
            newString = str;

        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fetchSuggestionsFor(String input){
        TextServicesManager tsm = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
        SpellCheckerSession spellCheckerSession = tsm.newSpellCheckerSession(null, Locale.US, this, false);
        if(spellCheckerSession == null){
            Log.d(TAG,"SPELL CHECKER NULL");
        }
        if (spellCheckerSession != null) {
            spellCheckerSession.getSentenceSuggestions(
                    new TextInfo[]{ new TextInfo(input) },
                    1
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
        if (id == R.id.nav_camera) {
            suggestions.setText(newString);
            Log.d(TAG, String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
            while (getSupportFragmentManager().getBackStackEntryCount() != 0)
                getSupportFragmentManager().popBackStackImmediate();
            Toast.makeText(StartPage.this, "Pressed Camera" , Toast.LENGTH_SHORT).show();
        } else if (id == R.id.history) {
            fragment = new HistoryFragment(); setTitle("History");


        } else if (id == R.id.statistics) {
            fragment = new StatisticsFragment(); setTitle("Statistics");
        } else if (id == R.id.nav_manage) {
            fragment = new SettingsFragment(); setTitle("Settings");
        }else if (id == R.id.allergies) {
            fragment = new AllergyFragment(); setTitle("Allergy");
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
            for(int i=0; i < n; i++){
                int m = result.getSuggestionsInfoAt(i).getSuggestionsCount();
                String s = String.valueOf(m);
                Log.d(TAG,s);
                for(int k=0; k < m; k++) {
                    sb.append(result.getSuggestionsInfoAt(i).getSuggestionAt(k))
                            .append("\n");
                    Log.d(TAG,result.getSuggestionsInfoAt(i).getSuggestionAt(k));


                }
                sb.append("\n");
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                suggestions.append(sb.toString());
            }
        });
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
}
