package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Locale;


public class Splashscreen extends Activity {

    private static final String TAG = "Splashscreen";
    Thread splashTread;
    ImageView imageView;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
       final ImageView iv = (ImageView) findViewById(R.id.splash);
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        iv.startAnimation(animation);




        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                setLocale();
                Intent i = new Intent(Splashscreen.this,StartPage.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(i);
                overridePendingTransition(0,0);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void setLocale() {

      new SettingsFragment(this).setGetLanguage(Splashscreen.this,Locale.getDefault().getLanguage());

    }


}