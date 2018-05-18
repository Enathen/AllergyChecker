package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class CheckBoxLayout {

    private ImageView beginImageView;
    private TextView stringTextView;
    private TextView LastStringTextView;
    private CheckBox checkBox;
    private LinearLayout view;

    public CheckBoxLayout(CheckBoxBuilder checkBoxBuilder) {
        this.beginImageView = checkBoxBuilder.beginImageView;
        this.stringTextView = checkBoxBuilder.stringTextView;
        this.LastStringTextView = checkBoxBuilder.lastStringTextView;
        this.view = checkBoxBuilder.view;
        this.checkBox = checkBoxBuilder.checkBox;
    }

    public LinearLayout getView() {
        return view;
    }

    public void setView(LinearLayout view) {
        this.view = view;
    }

    public ImageView getBeginImageView() {
        return beginImageView;
    }

    public void setBeginImageView(ImageView beginImageView) {
        this.beginImageView = beginImageView;
    }

    public TextView getStringTextView() {
        return stringTextView;
    }

    public void setStringTextView(TextView stringTextView) {
        this.stringTextView = stringTextView;
    }

    public TextView getLastStringTextView() {
        return LastStringTextView;
    }

    public void setLastStringTextView(TextView lastStringTextView) {
        LastStringTextView = lastStringTextView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }



    public static class CheckBoxBuilder {

        private ImageView beginImageView;
        private TextView stringTextView;
        private CheckBox checkBox;
        private TextView lastStringTextView;
        private LinearLayout view;
        Context context;

        public CheckBoxBuilder(Context context, String firstText) {
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.checkbox_layout, null);
            ((TextView)view.findViewById(R.id.textViewCheckboxLayoutStart)).setText(firstText);
            stringTextView = ((TextView)view.findViewById(R.id.textViewCheckboxLayoutStart));

        }

        public CheckBoxBuilder optionalImage(int drawable, int color) {
            beginImageView = view.findViewById(R.id.imageViewCheckBoxLayout);

            beginImageView.setImageDrawable(context.getDrawable(drawable));
            beginImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            beginImageView.setVisibility(View.VISIBLE);
            return this;
        }
        public CheckBoxBuilder optionalCheckBox(String checkBoxString) {
            return checkBoxSetuo(checkBoxString);
        }
        public CheckBoxBuilder optionalCheckBox(Integer integer) {
            return checkBoxSetuo(String.valueOf(integer));
        }
        private CheckBoxBuilder checkBoxSetuo(String string){
            view.findViewById(R.id.checkBoxCheckBoxLayout).setVisibility(View.VISIBLE);
            checkBox = view.findViewById(R.id.checkBoxCheckBoxLayout);
            final SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkBox.setChecked(!checkBox.isChecked());
                }
            });
            checkBox.setChecked(sharedPreferences.getBoolean( string,false));
            checkBox.setOnCheckedChangeListener(checkboxOnChecked(sharedPreferences, string));
            return this;
        }
        private CompoundButton.OnCheckedChangeListener checkboxOnChecked(final SharedPreferences sharedPreferences, final String string){
            return new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putBoolean(string,b).apply();
                }
            };
        }

        public CheckBoxBuilder optionalLastString(String lastStringTextView) {
            ((TextView)view.findViewById(R.id.textViewCheckboxLayoutEnd)).setText(lastStringTextView);
            this.lastStringTextView = view.findViewById(R.id.textViewCheckboxLayoutEnd);
            this.lastStringTextView.setVisibility(View.VISIBLE);
            return this;
        }

        public CheckBoxBuilder buildListener(View.OnClickListener onClickListener) {
            view.setOnClickListener(onClickListener);
            return this;
        }
        public CheckBoxLayout buildCheckBoxLayout() {

            return new CheckBoxLayout(this);
        }


        public CheckBoxBuilder optionalImage(int flag) {
            beginImageView = view.findViewById(R.id.imageViewCheckBoxLayout);
            beginImageView.setImageDrawable(context.getDrawable(flag));
            beginImageView.setVisibility(View.VISIBLE);
            return this;
        }

        public CheckBoxBuilder optionalMarginBottom() {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);
            view.setLayoutParams(params);
            return this;
        }


    }
}
