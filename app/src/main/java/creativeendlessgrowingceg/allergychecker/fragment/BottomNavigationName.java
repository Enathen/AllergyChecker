package creativeendlessgrowingceg.allergychecker.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import creativeendlessgrowingceg.allergychecker.DashboardFragment;
import creativeendlessgrowingceg.allergychecker.R;

import static creativeendlessgrowingceg.allergychecker.Gradient.setGradient;

public class BottomNavigationName extends AppCompatActivity {

    private static final String TAG = "BottomNavigationName";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            getFragmentManager().popBackStack();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    DashboardFragment nextFrag= new DashboardFragment();

                    BottomNavigationName.this.getFragmentManager().beginTransaction()
                            .replace(R.id.container, nextFrag,"Dashboard")
                            .addToBackStack(null)
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_name);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        setGradient(findViewById(R.id.container),
                getColor(R.color.colorPrimaryLight),
                getColor(R.color.colorAccent),
                getColor(R.color.colorPrimary), 0f, 0.5f, 1f);


    }

}
