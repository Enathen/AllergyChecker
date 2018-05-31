package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static creativeendlessgrowingceg.allergychecker.ConfigureTheme.getCheckBoxColor;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class ButtonLayout {

    private Button button;
    private LinearLayout view;


    public ButtonLayout(ButtonLayoutBuilder ButtonLayoutBuilder) {
        this.view = ButtonLayoutBuilder.view;
        this.button = ButtonLayoutBuilder.button;
    }

    public LinearLayout getView() {
        return view;
    }

    public void setView(LinearLayout view) {
        this.view = view;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public static class ButtonLayoutBuilder {
        private Button button;
        private LinearLayout view;
        Context context;

        public ButtonLayoutBuilder(Context context, String buttonText, View.OnClickListener onClickListener) {
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.button_layout, null);
            button = view.findViewById(R.id.buttonLayout);
            button.setText(buttonText);
            button.setBackgroundColor(getCheckBoxColor(context));
            button.setOnClickListener(onClickListener);

        }
        public ButtonLayout buildButtonLayout() {

            return new ButtonLayout(this);
        }


        public ButtonLayoutBuilder optionalGradient(Drawable specificGradient) {
            button.setBackground(specificGradient);
            return this;
        }

        public ButtonLayoutBuilder optionalButtonTextColor(int fontColor) {
            button.setTextColor(fontColor);
            return this;
        }
    }
}
