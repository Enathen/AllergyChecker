package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getCheckBoxColor;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getFontColor;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class SliderLayout {

    private SeekBar seekBar;
    private LinearLayout view;
    private TextView timeSleep;
    private TextView seconds;


    public SliderLayout(SliderLayoutBuilder SliderLayoutBuilder) {
        this.view = SliderLayoutBuilder.view;
        this.seekBar = SliderLayoutBuilder.seekBar;
        this.timeSleep = SliderLayoutBuilder.timeSleep;
        this.seconds = SliderLayoutBuilder.seconds;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(int current) {
        seekBar.setProgress(current);
    }

    public TextView getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(TextView timeSleep) {
        this.timeSleep = timeSleep;
    }

    public TextView getSeconds() {
        return seconds;
    }

    public void setSeconds(TextView seconds) {
        this.seconds = seconds;
    }

    public LinearLayout getView() {
        return view;
    }

    public void setView(LinearLayout view) {
        this.view = view;
    }



    public static class SliderLayoutBuilder {
        private static final String TAG = "SLIDERLAYOUT";
        private SeekBar seekBar;
        private LinearLayout view;
        private TextView timeSleep;
        private TextView seconds;
        private Context context;

        public SliderLayoutBuilder(Context context, String sliderText, int high) {
            final SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);
            int current = sharedPreferences.getInt(APISharedPreference.getTimeSleep(),5);
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.slider_layout, null);
            view.findViewById(R.id.linearLayoutSliderLayout).setOnClickListener(null);
            view.findViewById(R.id.linearLayoutSliderLayoutSlider).setOnClickListener(null);
            seekBar = view.findViewById(R.id.seekBarSlider);
            timeSleep = view.findViewById(R.id.textViewSliderLayoutStart);
            timeSleep.setText(sliderText);
            timeSleep.setTextColor(getFontColor(context));
            seconds = view.findViewById(R.id.textViewSliderLayoutEnd);
            float converted = current/100;
            seconds.setText(String.valueOf(converted).concat("s"));
            seconds.setVisibility(View.VISIBLE);
            seekBar.setOnSeekBarChangeListener(listener());
            seekBar.setMax(high);
            seekBar.setProgress(current);

            setColor();

        }

        private void setColor() {
            int checkBoxColor = getCheckBoxColor(context);
            seconds.setTextColor(checkBoxColor);
            seekBar.setProgressTintList(ColorStateList.valueOf(checkBoxColor));
            seekBar.setBackgroundTintList(ColorStateList.valueOf(checkBoxColor));
            seekBar.setProgressBackgroundTintList(ColorStateList.valueOf(checkBoxColor));
            seekBar.setThumbTintList(ColorStateList.valueOf(checkBoxColor));
        }

        private SeekBar.OnSeekBarChangeListener listener(){
            return new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    float converted = i;
                    converted = converted/10;
                    seconds.setText(String.valueOf(converted).concat("s"));
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(APISharedPreference.getTimeSleep(),i).apply();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };
        }
        public SliderLayout buildSliderLayout() {

            return new SliderLayout(this);
        }



    }
}
