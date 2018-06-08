package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getCheckBoxColor;
import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getFontColor;

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
        private String firstText;
        private LinearLayout view;
        Context context;

        public CheckBoxBuilder(Context context, String firstText) {
            firstText = TextHandler.cutFirstWord(TextHandler.capitalLetter(firstText));
            this.firstText = firstText;
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.checkbox_layout, null);
            lastStringTextView = view.findViewById(R.id.textViewCheckboxLayoutEnd);
            stringTextView = view.findViewById(R.id.textViewCheckboxLayoutStart);
            checkBox = view.findViewById(R.id.checkBoxCheckBoxLayout);
            beginImageView = view.findViewById(R.id.imageViewCheckBoxLayout);
            stringTextView.setText(firstText);
            stringTextView.setTextColor(getFontColor(context));
            lastStringTextView.setTextColor(getCheckBoxColor(context));
        }

        public CheckBoxBuilder optionalImage(int drawable, int color) {

            beginImageView.setImageDrawable(context.getDrawable(drawable));
            beginImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            beginImageView.setVisibility(View.VISIBLE);
            return this;
        }
        public CheckBoxBuilder optionalCheckBox(String checkBoxString) {

            return checkBoxSetup(checkBoxString);
        }
        public CheckBoxBuilder optionalCheckBox(Integer integer) {
            return checkBoxSetup(String.valueOf(integer));
        }
        public CheckBoxBuilder optionalAddAutoLink() {
            stringTextView.setAutoLinkMask(Linkify.WEB_URLS);
            stringTextView.setText(firstText);
            return this;
        }
        private CheckBoxBuilder checkBoxSetup(String string){
            checkBox.setButtonTintList(ColorStateList.valueOf(getCheckBoxColor(context)));
            view.findViewById(R.id.checkBoxCheckBoxLayout).setVisibility(View.VISIBLE);
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

        public CheckBoxBuilder optionalLastString(String lastString) {
            lastStringTextView.setText(lastString);

            lastStringTextView.setVisibility(View.VISIBLE);
            return this;
        }

        public CheckBoxBuilder buildListener(View.OnClickListener onClickListener) {
            view.setOnClickListener(onClickListener);
            return this;
        }
        public CheckBoxLayout buildCheckBoxLayout() {

            return new CheckBoxLayout(this);
        }


        public CheckBoxBuilder optionalImage(int drawable) {
            beginImageView = view.findViewById(R.id.imageViewCheckBoxLayout);
            beginImageView.setImageDrawable(context.getDrawable(drawable));
            beginImageView.setVisibility(View.VISIBLE);
            return this;
        }


        public CheckBoxBuilder optionalOnlyFirstString() {
            lastStringTextView.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
            return this;
        }

        public CheckBoxBuilder optionalRemoveCheckbox() {
            checkBox.setVisibility(View.GONE);
            return this;
        }

        public CheckBoxBuilder optionalBiggerLeftString() {
            lastStringTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 5f));
            stringTextView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f));
            return this;
        }

        public CheckBoxBuilder optionalRemoveImage() {
            beginImageView.setVisibility(View.GONE);
            return this;
        }
    }
}
