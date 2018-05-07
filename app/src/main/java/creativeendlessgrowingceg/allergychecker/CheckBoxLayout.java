package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
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
        public CheckBoxBuilder optionalCheckBox() {
            view.findViewById(R.id.checkBoxCheckBoxLayout).setVisibility(View.VISIBLE);
            checkBox = view.findViewById(R.id.checkBoxCheckBoxLayout);
            return this;
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
    }
}
