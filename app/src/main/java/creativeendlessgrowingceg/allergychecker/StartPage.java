package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import creativeendlessgrowingceg.allergychecker.camera.OcrCaptureActivity;
import creativeendlessgrowingceg.allergychecker.design.activity.OnboardingPagerActivity;

public class StartPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , HistoryFragment.OnFragmentInteractionListener
        , StatisticsFragment.OnFragmentInteractionListener
        , SettingsFragment.OnFragmentInteractionListener
        , MyAllergies.OnFragmentInteractionListener
        , AboutFragment.OnFragmentInteractionListener
        , TranslateHelp.OnFragmentInteractionListener
        , MyPreference.OnFragmentInteractionListener{
    private static final String TAG = "StartPage";
    private static final String SHARED_PREFS_NAME = "StartPage";
    FloatingActionButton flash;
    FloatingActionButton write;
    FloatingActionMenu camera;
    ArrayList<String> dateStrings = new ArrayList<>();
    SharedPreferences prefs;

    private TextView suggestions;
    private TextView allergic;
    private InterstitialAd interstitialAd;
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
        new SettingsFragment(this).setGetLanguage(StartPage.this, Locale.getDefault().getLanguage());
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
        suggestions = (TextView) findViewById(R.id.ingredientsTextView);
        allergic = (TextView) findViewById(R.id.textViewFoundAllergies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadInterstitial();

        String newString = getString(R.string.startPageHeader);
        write = (FloatingActionButton) findViewById(R.id.write);
        camera = (FloatingActionMenu) findViewById(R.id.menu);
        final StartPage startPage = this;
        flash = (FloatingActionButton) findViewById(R.id.flashon);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startPage, OcrCaptureActivity.class);
                intent.putExtra("EXTRA_SESSION_ID", true);
                startPage.startActivity(intent);
            }
        });
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(startPage);
                builder.setTitle(R.string.inputIngredients);
                final EditText input = new EditText(startPage);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(startPage, StartPage.class);
                        intent.putExtra("location", input.getText().toString());
                        startPage.startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                camera.close(true);
                builder.show();

            }
        });

        camera.setOnMenuButtonLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (write.getVisibility() != View.VISIBLE) {
                    write.setVisibility(View.VISIBLE);
                    flash.setVisibility(View.VISIBLE);
                    camera.open(true);

                } else {
                    write.setVisibility(View.GONE);
                    flash.setVisibility(View.GONE);

                    camera.close(true);
                }
                return true;


            }

        });
        camera.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startPage, OcrCaptureActivity.class);
                intent.putExtra("EXTRA_SESSION_ID", false);
                startPage.startActivity(intent);
            }
        });


        intent = getIntent();
        String str = intent.getStringExtra("location");


        if (str != null) {

            str = str.replaceAll("[^\\p{L}\\p{Nd}\\s]+", "");
            String allergyString = str;
            String[] parts = allergyString.split("\\s+");
            Arrays.sort(parts);
            StringBuilder sb = new StringBuilder();
            for (String s : parts) {
                sb.append(s);
                sb.append(" ");
            }

            allergyString = sb.toString().trim();
            Log.d(TAG, allergyString);
            Log.d(TAG, str);

            suggestions.setText(str);
            str = str.toLowerCase();
            if (!str.equals("")) {
                DateString dateString = new DateString(str);
                dateStrings = getArray();
                Log.d(TAG, String.valueOf(dateStrings.size()));

                dateStrings.add(dateString.string);

                Log.d(TAG, String.valueOf(dateStrings.size()));
            } else {
                dateStrings = getArray();
            }
            //setDateStrings(dateStrings);

            saveArray();
            checkStringAgainstAllergies(str);


        } else {
            str = intent.getStringExtra("HistoryFragment");
            if (str != null) {
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
        View parentView = navigationView.getHeaderView(0);
        parentView.findViewById(R.id.LinLayHorNavHeadStartPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });

    }


    private void setProfilePicture() {
        new LoadUIAllergies().savePicture(this,getImageViewHashMap(this));
    }

    private void displayInterstitial() {
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("79C8186833AA41CD2C967FE87614751A").build());
    }

    public void loadInterstitial() {
        interstitialAd = new InterstitialAd(StartPage.this);
        interstitialAd.setAdUnitId("ca-app-pub-3607354849437438/9852745111");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
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
        deleteConstrained();

        (findViewById(R.id.progressBar3)).setVisibility(View.VISIBLE);

        Locale locale = new Locale(new SettingsFragment(this).getLanguageFromLFragment(this));
        final Locale newLocale = new Locale(locale.getLanguage());
        Locale.setDefault(newLocale);
        final Configuration config = new Configuration();
        config.locale = newLocale;

        final Resources res = this.getResources();
        res.updateConfiguration(config, res.getDisplayMetrics());
        new CalcAllergy(this, allergic, (ProgressBar) findViewById(R.id.progressBar3)).execute(str);

    }

    private void deleteConstrained() {
        if (findViewById(R.id.lin_lay) != null) {
            ((ConstraintLayout) findViewById(R.id.lin_lay)).removeView(findViewById(R.id.splash));
            ((ConstraintLayout) findViewById(R.id.lin_lay)).removeView(findViewById(R.id.allergycheck));
            ((LinearLayout) findViewById(R.id.linLayStartPage)).removeView(findViewById(R.id.lin_lay));

        }
    }

    public ArrayList<String> getDateString() {
        for (String dateString : dateStrings) {
            Log.d(TAG, dateString);
        }

        return dateStrings;
    }

    public void setDateStrings(ArrayList<String> datastring) {
        Log.d(TAG, String.valueOf(dateStrings.size()));
        for (String string : datastring) {
            if (!dateStrings.contains(string))
                dateStrings.add(string);
        }
        Log.d(TAG, String.valueOf(dateStrings.size()));
    }

    public boolean saveArray() {
        Collections.sort(dateStrings);
        for (String dateString : dateStrings) {
            Log.d(TAG, dateString);
        }
        prefs = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = prefs.edit();
        Collections.sort(dateStrings, new HistoryFragment.stringComparator());
        if (dateStrings.size() > 128) {
            int sizeToMuch = dateStrings.size() % 128;
            for (int i = 0; i < sizeToMuch; i++) {
                Log.d(TAG, "Remove History: " + dateStrings.get(i));
                dateStrings.remove(i);
            }
        }

        Set<String> set = new HashSet<>();
        set.addAll(dateStrings);
        mEdit1.putStringSet("list", set);
        return mEdit1.commit();
    }

    public ArrayList<String> getArray() {

        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> set = sp.getStringSet("list", new HashSet<String>());

        return new ArrayList<>(set);
    }

    public void deleteOneItemHistory(String keys) {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        Set<String> set = prefs.getStringSet("list", new HashSet<String>());
        set.remove(keys);
        mEdit1.remove("list");
        mEdit1.commit();
        mEdit1.putStringSet("list", set);
        mEdit1.commit();


    }

    public void deleteHistory() {

        SharedPreferences.Editor mEdit1 = prefs.edit();
        mEdit1.remove("list");
        mEdit1.apply();

    }

    public ArrayList<String> getArrayFromHistory() {
        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = prefs.getStringSet("list", new HashSet<String>());
        for (String s : set) {
            Log.d(TAG, "getArray: " + s);
        }
        return new ArrayList<>(set);
    }

    @Override
    public void onBackPressed() {
        int popStack = getFragmentManager().getBackStackEntryCount();

        if (popStack == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
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
            View drawer = findViewById(R.id.language);
            PopupMenu popup = new PopupMenu(StartPage.this, drawer);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    Language = item.getTitle().toString();
                    Log.d(TAG, Language);
                    if (Language.equals("English")) {
                        Locale locale = new Locale("en");
                        Locale.setDefault(locale);

                        Configuration config = new Configuration();
                        config.setLocale(locale);
                        getApplicationContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    } else {
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
        deleteConstrained();
        if (findViewById(R.id.linlayallergyFromWord) != null) {
            findViewById(R.id.linlayallergyFromWord).setVisibility(View.INVISIBLE);

        }
        findViewById(R.id.linLayHorizontalStartPage).setVisibility(View.INVISIBLE);


        ((TextView) findViewById(R.id.textViewFoundAllergies)).setText("");
        if (id == R.id.history) {
            fragment = new HistoryFragment(this);
            setTitle("History");
        } else if (id == R.id.languageMenu) {

            fragment = new SettingsFragment();
            setTitle("Language");
        } else if (id == R.id.allergies) {
//this, getImageViewHashMap(), new SettingsFragment(this).getCategories()
            fragment = new MyAllergies(this,getImageViewHashMap());
            setTitle("Allergies");
        } else if(id == R.id.preference){
            fragment = new MyPreference(this,getImageViewHashMap());
            setTitle("Preferences");
        }
        else if (id == R.id.tutorial) {
            startActivity(new Intent(this, OnboardingPagerActivity.class));
        } else if (id == R.id.about) {
            fragment = new AboutFragment();
            setTitle("About");
        } else if (id == R.id.translate) {
            fragment = new TranslateHelp();
            setTitle("Translate");
        } else if (id == R.id.nav_rate) {
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
        } else if (id == R.id.nav_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = "\n" + getString(R.string.welcomeMessageInvite) + "\n\n";
            sAux = sAux + "//play.google.com/store/apps/details?id=" + this.getPackageName() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } else if (id == R.id.nav_send) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "AllergyCheckerCEG@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getText(R.string.mustBeInEnglish));
            startActivity(Intent.createChooser(emailIntent, getResources().getText(R.string.sendTipsFrom)));

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

    private HashMap<Integer, ImageView> getImageViewHashMap(StartPage startPage) {
        HashMap<Integer, ImageView> imageViewHashMap = new HashMap<>();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View parentView = navigationView.getHeaderView(0);
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

    public HashMap<Integer, ImageView> getImageViewHashMap() {
        HashMap<Integer, ImageView> imageViewHashMap = new HashMap<>();


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
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public Context startPage() {
        return this;
    }

    public class DateString {
        String string;

        DateString(String string) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss", new Locale("sv"));

            this.string = simpleDateFormat.format(new Date()).concat(" " + string);
        }
    }

    private class CalcAllergy extends AsyncTask<String, Integer, ArrayList<AllAllergiesForEachInteger>> {
        private final HelpCalcAllergy helpCalcAllergy;
        private StartPage mContext;
        private TextView textView;
        private ProgressBar viewById;


        public CalcAllergy(StartPage context, TextView textView, ProgressBar viewById) {
            mContext = context;

            this.textView = textView;
            this.viewById = viewById;
            helpCalcAllergy = new HelpCalcAllergy();
        }

        // Runs in UI before background thread is called
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Do something like display a progress bar
        }

        // This is run in a background thread

        @Override
        protected ArrayList<AllAllergiesForEachInteger> doInBackground(String... params) {
            // get the string from params, which is an array
            TreeMap<Integer, HashSet<String>> hashSetAllStrings = new TreeMap<>();

            HashSet<String> hashSetToCheckLast = new HashSet<>();
            helpCalcAllergy.FixString(params[0].split("\\s+"), hashSetAllStrings, hashSetToCheckLast);

            ArrayList<Locale> listOfLanguages = new SettingsFragment(mContext).getCategories();
            if (listOfLanguages.isEmpty()) {
                listOfLanguages.add(Locale.getDefault());
            }
            HashSet<Integer> hashSetFromOtherClass = new MyAllergies(mContext).getAllergies();
            for (Integer hashSetFromOtherClas : hashSetFromOtherClass) {
                Log.d(TAG, "doInBackground: " + getString(hashSetFromOtherClas));
            }
            HashMap<Integer, HashMap<String, AllAllergiesForEachInteger>> allergies = new HashMap<>();

            int length = 0;
            int counter = 0;
            int i = 0;
            ArrayList<AllAllergiesForEachInteger> allFoundAllergies = new ArrayList<>();
            for (int id : hashSetFromOtherClass) {
                publishProgress(hashSetFromOtherClass.size(), counter);
                length = helpCalcAllergy.setLocaleString(length, id, allergies, listOfLanguages, StartPage.this);

                if (i % 2 == 0) {
                    counter++;
                }
                i++;

            }
            for (HashMap<String, AllAllergiesForEachInteger> stringAllAllergiesForEachIntegerHashMap : allergies.values()) {
                for (AllAllergiesForEachInteger allAllergiesForEachInteger : stringAllAllergiesForEachIntegerHashMap.values()) {
                    Log.d(TAG, "STARTPAGE: "+ allAllergiesForEachInteger.getNameOfIngredient());
                }
            }
            helpCalcAllergy.bkTree(length, hashSetAllStrings, allergies, allFoundAllergies);

            long start = System.currentTimeMillis();
            counter = hashSetToCheckLast.size() / 2;
            i = 0;
            for (String s : hashSetToCheckLast) {
                if (i % 2 == 0) {
                    publishProgress(hashSetToCheckLast.size() / 2, counter);
                    counter++;
                }
                i++;
                helpCalcAllergy.checkFullString(s, allergies, allFoundAllergies);
            }
            long stop = System.currentTimeMillis();
            Log.d(TAG, "TIME: " + (stop - start));
            Collections.sort(allFoundAllergies);
            return allFoundAllergies;
        }

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            double i = ((double) values[1] / (double) values[0]) * 100;

            viewById.setProgress((int) i);
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(ArrayList<AllAllergiesForEachInteger> allAllergiesForEachInteger) {
            viewById.setVisibility(View.INVISIBLE);
            super.onPostExecute(allAllergiesForEachInteger);
            String outputString = "";
            final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayHorizontalStartPage);
            final HashMap<String, Lin> linearLayoutHashMap = new HashMap<>();
            for (final AllAllergiesForEachInteger allergiesForEachInteger : allAllergiesForEachInteger) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout newlinearLayout = (LinearLayout) inflater.inflate(R.layout.linlayoutstartpagevertical, null);
                ((ImageView) newlinearLayout.findViewById(R.id.imageViewHorStartPage)).setImageResource(LanguagesAccepted.getInstance().getFlag(allergiesForEachInteger.getLanguage()));
                ((TextView) newlinearLayout.findViewById(R.id.textViewAllergy)).setText(helpCalcAllergy.cutFirstWord(getString(allergiesForEachInteger.getId())).concat(": " + allergiesForEachInteger.getNameOfIngredient()));
                ((TextView) newlinearLayout.findViewById(R.id.textViewFoundFromWord)).setText(allergiesForEachInteger.getNameOfWordFound());
                if (!linearLayoutHashMap.containsKey(allergiesForEachInteger.getMotherLanguage())) {
                    LinearLayout parentLin = (LinearLayout) inflater.inflate(R.layout.linlayoutstartpageverticaltrue, null);
                    parentLin.addView(newlinearLayout);
                    linearLayout.addView(parentLin);
                    ArrayList<LinearLayout> l = new ArrayList<>();
                    linearLayoutHashMap.put(allergiesForEachInteger.getMotherLanguage(), new Lin(newlinearLayout, l, parentLin));
                    Log.d(TAG, "onPostExecute: size" + linearLayoutHashMap.get(allergiesForEachInteger.getMotherLanguage()).linearLayoutArrayList.size());


                } else {
                    newlinearLayout.findViewById(R.id.arrowLeft).setVisibility(View.INVISIBLE);
                    linearLayoutHashMap.get(allergiesForEachInteger.getMotherLanguage()).linearLayoutArrayList.add(newlinearLayout);
                    Log.d(TAG, "onPostExecute: size" + linearLayoutHashMap.get(allergiesForEachInteger.getMotherLanguage()).linearLayoutArrayList.size());
                }
            }
            for (final String string : linearLayoutHashMap.keySet()) {
                if (linearLayoutHashMap.get(string).linearLayoutArrayList.isEmpty()) {
                    Log.d(TAG, "onClick: " + string + "INV");
                    linearLayoutHashMap.get(string).parentLin.findViewById(R.id.arrowLeft).setVisibility(View.INVISIBLE);
                }
                ((TextView) linearLayoutHashMap.get(string).parentLin.findViewById(R.id.textViewAllergy)).
                        append(" " + (linearLayoutHashMap.get(string).linearLayoutArrayList.size() + 1));

                linearLayoutHashMap.get(string).linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (helpCalcAllergy.checkIfAlreadyShown(linearLayoutHashMap.get(string).parentLin.findViewById(R.id.arrowLeft))) {
                            if (!linearLayoutHashMap.get(string).linearLayoutArrayList.isEmpty()) {
                                for (LinearLayout newLin : linearLayoutHashMap.get(string).linearLayoutArrayList) {
                                    linearLayoutHashMap.get(string).parentLin.addView(newLin);
                                }
                            }
                        } else {
                            if (!linearLayoutHashMap.get(string).linearLayoutArrayList.isEmpty()) {
                                for (LinearLayout newLin : linearLayoutHashMap.get(string).linearLayoutArrayList) {
                                    if (linearLayoutHashMap.get(string).parentLin != newLin) {
                                        linearLayoutHashMap.get(string).parentLin.removeView(newLin);
                                    }
                                }
                            }
                        }

                    }

                });
            }
            if (!allAllergiesForEachInteger.isEmpty()) {

                outputString = outputString.concat("\n" + getString(R.string.dontUse) + "\n");
                outputString = outputString.concat("\n" + getString(R.string.mightContainOther) + "\n");
                outputString = outputString.concat(getString(R.string.scannedTextBelow) + "\n");
                textView.setTextColor(Color.RED);
                textView.setText(outputString);
                findViewById(R.id.linlayallergyFromWord).setVisibility(View.VISIBLE);
            } else {
                outputString = outputString.concat("\n" + getString(R.string.youCanUse) + "\n");
                outputString = outputString.concat("\n" + getString(R.string.mightContainAllergies) + "\n");
                outputString = outputString.concat(getString(R.string.scannedTextBelow) + "\n");
                textView.setTextColor(getColor(R.color.colorAccent));
                textView.setText(outputString);
                ((LinearLayout) findViewById(R.id.linLayStartPage)).removeView(findViewById(R.id.linlayallergyFromWord));

            }
            allergic.setText(outputString);
            // Do things like hide the progress bar or change a TextView
        }

        private class Lin {
            private LinearLayout linearLayout;
            private ArrayList<LinearLayout> linearLayoutArrayList;
            private LinearLayout parentLin;

            public Lin(LinearLayout linearLayout, ArrayList<LinearLayout> linearLayoutArrayList, LinearLayout parentLin) {

                this.linearLayout = linearLayout;
                this.linearLayoutArrayList = linearLayoutArrayList;
                this.parentLin = parentLin;
            }


        }
    }

}
