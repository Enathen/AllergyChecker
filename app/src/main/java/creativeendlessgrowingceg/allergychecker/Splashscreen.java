package creativeendlessgrowingceg.allergychecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class Splashscreen extends Activity {

    Thread splashTread;
    ImageView imageView;
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
                finish();

                Intent i = new Intent(Splashscreen.this,StartPage.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}