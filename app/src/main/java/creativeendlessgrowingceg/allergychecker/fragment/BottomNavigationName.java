package creativeendlessgrowingceg.allergychecker.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import creativeendlessgrowingceg.allergychecker.APISharedPreference;
import creativeendlessgrowingceg.allergychecker.DashboardFragment;
import creativeendlessgrowingceg.allergychecker.DateAndHistory;
import creativeendlessgrowingceg.allergychecker.MyAllergiesNew;
import creativeendlessgrowingceg.allergychecker.R;
import creativeendlessgrowingceg.allergychecker.SettingsFragment;

import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getCurrentTheme;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.setBottomColor;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.setGradient;

public class BottomNavigationName extends AppCompatActivity{

    private static final String TAG = "BottomNavigationName";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            getFragmentManager().popBackStack();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    MyAllergiesNew nextFrag= new MyAllergiesNew();
                    BottomNavigationName.this.getFragmentManager().beginTransaction()
                            .replace(R.id.container, nextFrag,"Home")
                            .addToBackStack("test")
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    DashboardFragment nextFrag2 = new DashboardFragment();

                    BottomNavigationName.this.getFragmentManager().beginTransaction()
                            .replace(R.id.container, nextFrag2,"Dashboard")
                            .addToBackStack("test2")
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    SettingsFragment nextFrag3 = new SettingsFragment();
                    BottomNavigationName.this.getFragmentManager().beginTransaction()
                            .replace(R.id.container, nextFrag3,"Settings")
                            .addToBackStack("test3")
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getCurrentTheme(getBaseContext()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_name);
        if(!PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getBoolean(APISharedPreference.experimental,false)){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle(R.string.experimentalTitle);
            adb.setMessage(R.string.experimentalMessage);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putBoolean(APISharedPreference.experimental,true).apply();
                }
            });
            adb.show();

        }

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        String str=getIntent().getStringExtra(APISharedPreference.getScannedText);
        setGradient(getBaseContext(), (ConstraintLayout) findViewById(R.id.container));
        setBottomColor(getBaseContext(),navigation);
        if(str!= null){
            DateAndHistory dateAndHistory = new DateAndHistory(getBaseContext(), str);
            if(!str.equals("")){
                dateAndHistory.saveArray();
            }
            Bundle b = new Bundle();
            b.putString(APISharedPreference.getScannedText, getIntent().getStringExtra(APISharedPreference.getScannedText));
            fragmentAnalyze(navigation,b);
        }
        if(getIntent().getStringExtra(APISharedPreference.getHistory) != null){
            Bundle b = new Bundle();
            b.putString(APISharedPreference.getHistory, getIntent().getStringExtra(APISharedPreference.getHistory));
            fragmentAnalyze(navigation,b);
        }


    }
    private void fragmentAnalyze(BottomNavigationView navigation,Bundle bundle){
        Fragment fragment = new SettingsFragment();
        fragment.setArguments(bundle);
        navigation.setSelectedItemId(R.id.navigation_notifications);
        BottomNavigationName.this.getFragmentManager().beginTransaction()
                .replace(R.id.container, fragment,"Settings")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
