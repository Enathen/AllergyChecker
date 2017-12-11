package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class Splashscreen {

    private static final String TAG = "Splashscreen";
    Thread splashTread;
    ImageView imageView;
    String language;
    public Splashscreen(ImageView iv, Context context, final LinearLayout linearLayout, final ConstraintLayout constraintLayout){
        //final ImageView iv = (ImageView) findViewById(R.id.splash);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.rotate);
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
                linearLayout.removeView(constraintLayout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}