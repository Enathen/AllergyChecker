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

    private ImageView imageViewHorizontal;
    private ImageView imageViewVertical;
    private TextView textViewHorizontal;
    private TextView textViewVertical;
    private LinearLayout linearLayoutHorizontal;
    private LinearLayout linearLayoutVertical;

    public CardClassLayout createCardClassLayoutScratch(Context context,String text,int drawable, int color) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.checkbox_layout, null);
        setup(linearLayout,text,drawable,color,context);

        return this;
    }

    private void setup(LinearLayout linearLayout, String text, int drawable, int color, Context context) {
        linearLayoutHorizontal = linearLayout.findViewById(R.id.linearLayoutCardHorizontal);
        linearLayoutVertical = linearLayout.findViewById(R.id.linearLayoutCardVertical);
        imageViewHorizontal = linearLayout.findViewById(R.id.imageViewCardHorizontal);
        imageViewVertical = linearLayout.findViewById(R.id.imageViewCardVertical);
        textViewHorizontal = linearLayout.findViewById(R.id.textViewCardHorizontal);
        textViewVertical = linearLayout.findViewById(R.id.textViewCardVertical);
        textViewHorizontal.setText(text);
        textViewVertical.setText(text);
        imageViewHorizontal.setImageDrawable(context.getDrawable(drawable));
        imageViewVertical.setImageDrawable(context.getDrawable(drawable));
        imageViewHorizontal.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        imageViewVertical.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        linearLayout.setBackgroundColor(color);
    }

    public CardClassLayout useCardClassLayout(Context context,String text,int drawable,LinearLayout linearLayout, int color) {
        setup(linearLayout,text,drawable,color,context);
        return this;
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
}
