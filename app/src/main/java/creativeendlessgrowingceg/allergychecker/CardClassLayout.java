package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class CardClassLayout {

    private LinearLayout parentLinearLayout;
    private ImageView imageViewHorizontal;
    private ImageView imageViewVertical;
    private TextView textViewHorizontal;
    private TextView textViewVertical;
    private LinearLayout linearLayoutHorizontal;
    private LinearLayout linearLayoutVertical;

    public CardClassLayout(CardClassLayoutBuilder cardClassLayoutBuilder) {

        this.imageViewHorizontal = cardClassLayoutBuilder.imageViewHorizontal;
        this.imageViewVertical = cardClassLayoutBuilder.imageViewVertical;
        this.textViewHorizontal = cardClassLayoutBuilder.textViewHorizontal;
        this.textViewVertical = cardClassLayoutBuilder.textViewVertical;
        this.linearLayoutHorizontal = cardClassLayoutBuilder.linearLayoutHorizontal;
        this.linearLayoutVertical = cardClassLayoutBuilder.linearLayoutVertical;
        parentLinearLayout = cardClassLayoutBuilder.parentLinearLayout;
    }

    public LinearLayout getParentLinearLayout() {
        return parentLinearLayout;
    }

    public void setParentLinearLayout(LinearLayout parentLinearLayout) {
        this.parentLinearLayout = parentLinearLayout;
    }

    public ImageView getImageViewHorizontal() {
        return imageViewHorizontal;
    }

    public void setImageViewHorizontal(ImageView imageViewHorizontal) {
        this.imageViewHorizontal = imageViewHorizontal;
    }

    public ImageView getImageViewVertical() {
        return imageViewVertical;
    }

    public void setImageViewVertical(ImageView imageViewVertical) {
        this.imageViewVertical = imageViewVertical;
    }

    public TextView getTextViewHorizontal() {
        return textViewHorizontal;
    }

    public void setTextViewHorizontal(TextView textViewHorizontal) {
        this.textViewHorizontal = textViewHorizontal;
    }

    public TextView getTextViewVertical() {
        return textViewVertical;
    }

    public void setTextViewVertical(TextView textViewVertical) {
        this.textViewVertical = textViewVertical;
    }

    public LinearLayout getLinearLayoutHorizontal() {
        return linearLayoutHorizontal;
    }

    public void setLinearLayoutHorizontal(LinearLayout linearLayoutHorizontal) {
        this.linearLayoutHorizontal = linearLayoutHorizontal;
    }

    public LinearLayout getLinearLayoutVertical() {
        return linearLayoutVertical;
    }

    public void setLinearLayoutVertical(LinearLayout linearLayoutVertical) {
        this.linearLayoutVertical = linearLayoutVertical;
    }

    public static class CardClassLayoutBuilder {

        Context context;
        private ImageView imageViewHorizontal;
        private ImageView imageViewVertical;
        private TextView textViewHorizontal;
        private TextView textViewVertical;
        private LinearLayout linearLayoutHorizontal;
        private LinearLayout linearLayoutVertical;
        private LinearLayout parentLinearLayout;

        public CardClassLayoutBuilder(Context context, String text, int drawable, int color, LinearLayout linearLayout) {
            this.context = context;
            setup(linearLayout, text, drawable, color);

        }

        public CardClassLayoutBuilder(Context context, String text, int drawable, int color) {
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.linear_card, null);
            setup(linearLayout, text, drawable, color);

        }

        private void setup(LinearLayout linearLayout, String text, int drawable, int color) {
            linearLayoutHorizontal = linearLayout.findViewById(R.id.linearLayoutCardHorizontal);
            linearLayoutVertical = linearLayout.findViewById(R.id.linearLayoutCardVertical);
            imageViewHorizontal = linearLayout.findViewById(R.id.imageViewCardHorizontal);
            imageViewVertical = linearLayout.findViewById(R.id.imageViewCardVertical);
            textViewHorizontal = linearLayout.findViewById(R.id.textViewCardHorizontal);
            textViewVertical = linearLayout.findViewById(R.id.textViewCardVertical);
            textViewHorizontal.setText(text);
            textViewHorizontal.setTextColor(color);
            textViewVertical.setText(text);
            imageViewHorizontal.setImageDrawable(context.getDrawable(drawable));
            imageViewVertical.setImageDrawable(context.getDrawable(drawable));
            imageViewHorizontal.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            imageViewVertical.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            parentLinearLayout = linearLayout;
        }

        public CardClassLayoutBuilder optionalLinearSizeVerticalHeight(int size) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayoutVertical.getLayoutParams();
            params.height = size;
            linearLayoutVertical.setLayoutParams(params);

            return this;
        }

        public CardClassLayoutBuilder optionalLinearSizeHorizontalHeight(int size) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayoutHorizontal.getLayoutParams();
            params.height = size;
            linearLayoutHorizontal.setLayoutParams(params);

            return this;
        }

        public CardClassLayoutBuilder optionalLinearSizeVerticalWidth(int size) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayoutVertical.getLayoutParams();
            params.width = size;
            linearLayoutVertical.setLayoutParams(params);

            return this;
        }

        public CardClassLayoutBuilder optionalLinearSizeHorizontalWidth(int size) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayoutHorizontal.getLayoutParams();
            params.width = size;
            linearLayoutHorizontal.setLayoutParams(params);

            return this;
        }

        public CardClassLayout buildCardClassLayout() {

            return new CardClassLayout(this);
        }

    }
}
