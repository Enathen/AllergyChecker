package creativeendlessgrowingceg.allergychecker;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class Gradient {
    public static void setGradient(final View view, final int color, final int color2, final int color3, final float pos, final Float pos2, final Float pos3) {
        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, 0, view.getHeight(),
                        new int[]{
                                color,
                                color2,
                                color3},
                        new float[]{
                                pos, pos2, pos3},
                        Shader.TileMode.REPEAT);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        view.setBackground((Drawable) p);
    }
}
