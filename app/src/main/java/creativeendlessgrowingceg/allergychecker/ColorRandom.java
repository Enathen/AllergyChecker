package creativeendlessgrowingceg.allergychecker;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Enathen on 2017-12-09.
 */

public class ColorRandom {
    private static final ColorRandom ourInstance = new ColorRandom();
    private static final String TAG = "ColorRandom";

    public static int getRandomColorFromArray(ArrayList<Integer> color,int i){
        return color.get(i%color.size());
    }
    public static ArrayList<Integer> getRandomColor() {
        ArrayList<Integer> colors = new ArrayList<>();


        colors.add(Color.parseColor("#FFEBEE"));
        colors.add(Color.parseColor("#FFCDD2"));
        colors.add(Color.parseColor("#EF9A9A"));
        colors.add(Color.parseColor("#E57373"));
        colors.add(Color.parseColor("#EF5350"));
        colors.add(Color.parseColor("#F44336"));
        colors.add(Color.parseColor("#E53935"));
        colors.add(Color.parseColor("#D32F2F"));
        colors.add(Color.parseColor("#C62828"));
        colors.add(Color.parseColor("#B71C1C"));
        colors.add(Color.parseColor("#FF8A80"));
        colors.add(Color.parseColor("#FF5252"));
        colors.add(Color.parseColor("#FF1744"));
        colors.add(Color.parseColor("#D50000"));
        colors.add(Color.parseColor("#E91E63"));
        colors.add(Color.parseColor("#FCE4EC"));
        colors.add(Color.parseColor("#F8BBD0"));
        colors.add(Color.parseColor("#F48FB1"));
        colors.add(Color.parseColor("#F06292"));
        colors.add(Color.parseColor("#EC407A"));
        colors.add(Color.parseColor("#E91E63"));
        colors.add(Color.parseColor("#D81B60"));
        colors.add(Color.parseColor("#C2185B"));
        colors.add(Color.parseColor("#AD1457"));
        colors.add(Color.parseColor("#880E4F"));
        colors.add(Color.parseColor("#FF80AB"));
        colors.add(Color.parseColor("#FF4081"));
        colors.add(Color.parseColor("#F50057"));
        colors.add(Color.parseColor("#C51162"));
        colors.add(Color.parseColor("#9C27B0"));

        colors.add(Color.parseColor("#F3E5F5"));
        
        colors.add(Color.parseColor("#E1BEE7"));
        
        colors.add(Color.parseColor("#CE93D8"));
        
        colors.add(Color.parseColor("#BA68C8"));
        
        colors.add(Color.parseColor("#AB47BC"));
        
        colors.add(Color.parseColor("#9C27B0"));
        
        colors.add(Color.parseColor("#8E24AA"));
        
        colors.add(Color.parseColor("#7B1FA2"));
        
        colors.add(Color.parseColor("#6A1B9A"));
        
        colors.add(Color.parseColor("#4A148C"));

        colors.add(Color.parseColor("#EA80FC"));

        colors.add(Color.parseColor("#E040FB"));

        colors.add(Color.parseColor("#D500F9"));

        colors.add(Color.parseColor("#AA00FF"));


        colors.add(Color.parseColor("#673AB7"));
        
        colors.add(Color.parseColor("#EDE7F6"));
        
        colors.add(Color.parseColor("#D1C4E9"));
        
        colors.add(Color.parseColor("#B39DDB"));
        
        colors.add(Color.parseColor("#9575CD"));
        
        colors.add(Color.parseColor("#7E57C2"));
        
        colors.add(Color.parseColor("#673AB7"));
        
        colors.add(Color.parseColor("#5E35B1"));
        
        colors.add(Color.parseColor("#512DA8"));
        
        colors.add(Color.parseColor("#4527A0"));
        
        colors.add(Color.parseColor("#311B92"));

        colors.add(Color.parseColor("#B388FF"));

        colors.add(Color.parseColor("#7C4DFF"));

        colors.add(Color.parseColor("#651FFF"));

        colors.add(Color.parseColor("#6200EA"));

        
        colors.add(Color.parseColor("#3F51B5"));
        
        colors.add(Color.parseColor("#E8EAF6"));
        
        colors.add(Color.parseColor("#C5CAE9"));
        
        colors.add(Color.parseColor("#9FA8DA"));
        
        colors.add(Color.parseColor("#7986CB"));
        
        colors.add(Color.parseColor("#5C6BC0"));
        
        colors.add(Color.parseColor("#3F51B5"));
        
        colors.add(Color.parseColor("#3949AB"));
        
        colors.add(Color.parseColor("#303F9F"));
        
        colors.add(Color.parseColor("#283593"));
        
        colors.add(Color.parseColor("#1A237E"));

        colors.add(Color.parseColor("#8C9EFF"));

        colors.add(Color.parseColor("#536DFE"));

        colors.add(Color.parseColor("#3D5AFE"));

        colors.add(Color.parseColor("#304FFE"));

        
        colors.add(Color.parseColor("#2196F3"));
        
        colors.add(Color.parseColor("#E3F2FD"));
        
        colors.add(Color.parseColor("#BBDEFB"));
        
        colors.add(Color.parseColor("#90CAF9"));
        
        colors.add(Color.parseColor("#64B5F6"));
        
        colors.add(Color.parseColor("#42A5F5"));
        
        colors.add(Color.parseColor("#2196F3"));
        
        colors.add(Color.parseColor("#1E88E5"));
        
        colors.add(Color.parseColor("#1976D2"));
        
        colors.add(Color.parseColor("#1565C0"));
        
        colors.add(Color.parseColor("#0D47A1"));

        colors.add(Color.parseColor("#82B1FF"));

        colors.add(Color.parseColor("#448AFF"));

        colors.add(Color.parseColor("#2979FF"));

        colors.add(Color.parseColor("#2962FF"));

        
        colors.add(Color.parseColor("#03A9F4"));
        
        colors.add(Color.parseColor("#E1F5FE"));
        
        colors.add(Color.parseColor("#B3E5FC"));
        
        colors.add(Color.parseColor("#81D4FA"));
        
        colors.add(Color.parseColor("#4FC3F7"));
        
        colors.add(Color.parseColor("#29B6F6"));
        
        colors.add(Color.parseColor("#03A9F4"));
        
        colors.add(Color.parseColor("#039BE5"));
        
        colors.add(Color.parseColor("#0288D1"));
        
        colors.add(Color.parseColor("#0277BD"));
        
        colors.add(Color.parseColor("#01579B"));

        colors.add(Color.parseColor("#80D8FF"));

        colors.add(Color.parseColor("#40C4FF"));

        colors.add(Color.parseColor("#00B0FF"));

        colors.add(Color.parseColor("#0091EA"));

        
        colors.add(Color.parseColor("#00BCD4"));
        
        colors.add(Color.parseColor("#E0F7FA"));
        
        colors.add(Color.parseColor("#B2EBF2"));
        
        colors.add(Color.parseColor("#80DEEA"));
        
        colors.add(Color.parseColor("#4DD0E1"));
        
        colors.add(Color.parseColor("#26C6DA"));
        
        colors.add(Color.parseColor("#00BCD4"));
        
        colors.add(Color.parseColor("#00ACC1"));
        
        colors.add(Color.parseColor("#0097A7"));
        
        colors.add(Color.parseColor("#00838F"));
        
        colors.add(Color.parseColor("#006064"));

        colors.add(Color.parseColor("#84FFFF"));

        colors.add(Color.parseColor("#18FFFF"));

        colors.add(Color.parseColor("#00E5FF"));

        colors.add(Color.parseColor("#00B8D4"));

        
        colors.add(Color.parseColor("#009688"));
        
        colors.add(Color.parseColor("#E0F2F1"));
        
        colors.add(Color.parseColor("#B2DFDB"));
        
        colors.add(Color.parseColor("#80CBC4"));
        
        colors.add(Color.parseColor("#4DB6AC"));
        
        colors.add(Color.parseColor("#26A69A"));
        
        colors.add(Color.parseColor("#009688"));
        
        colors.add(Color.parseColor("#00897B"));
        
        colors.add(Color.parseColor("#00796B"));
        
        colors.add(Color.parseColor("#00695C"));
        
        colors.add(Color.parseColor("#004D40"));

        colors.add(Color.parseColor("#A7FFEB"));

        colors.add(Color.parseColor("#64FFDA"));

        colors.add(Color.parseColor("#1DE9B6"));

        colors.add(Color.parseColor("#00BFA5"));

        
        colors.add(Color.parseColor("#4CAF50"));
        
        colors.add(Color.parseColor("#E8F5E9"));
        
        colors.add(Color.parseColor("#C8E6C9"));
        
        colors.add(Color.parseColor("#A5D6A7"));
        
        colors.add(Color.parseColor("#81C784"));
        
        colors.add(Color.parseColor("#66BB6A"));
        
        colors.add(Color.parseColor("#4CAF50"));
        
        colors.add(Color.parseColor("#43A047"));
        
        colors.add(Color.parseColor("#388E3C"));
        
        colors.add(Color.parseColor("#2E7D32"));
        
        colors.add(Color.parseColor("#1B5E20"));

        colors.add(Color.parseColor("#B9F6CA"));

        colors.add(Color.parseColor("#69F0AE"));

        colors.add(Color.parseColor("#00E676"));

        colors.add(Color.parseColor("#00C853"));

        
        colors.add(Color.parseColor("#8BC34A"));
        
        colors.add(Color.parseColor("#F1F8E9"));
        
        colors.add(Color.parseColor("#DCEDC8"));
        
        colors.add(Color.parseColor("#C5E1A5"));
        
        colors.add(Color.parseColor("#AED581"));
        
        colors.add(Color.parseColor("#9CCC65"));
        
        colors.add(Color.parseColor("#8BC34A"));
        
        colors.add(Color.parseColor("#7CB342"));
        
        colors.add(Color.parseColor("#689F38"));
        
        colors.add(Color.parseColor("#558B2F"));
        
        colors.add(Color.parseColor("#33691E"));

        colors.add(Color.parseColor("#CCFF90"));

        colors.add(Color.parseColor("#B2FF59"));

        colors.add(Color.parseColor("#76FF03"));

        colors.add(Color.parseColor("#64DD17"));

        
        colors.add(Color.parseColor("#CDDC39"));
        
        colors.add(Color.parseColor("#F9FBE7"));
        
        colors.add(Color.parseColor("#F0F4C3"));
        
        colors.add(Color.parseColor("#E6EE9C"));
        
        colors.add(Color.parseColor("#DCE775"));
        
        colors.add(Color.parseColor("#D4E157"));
        
        colors.add(Color.parseColor("#CDDC39"));
        
        colors.add(Color.parseColor("#C0CA33"));
        
        colors.add(Color.parseColor("#AFB42B"));
        
        colors.add(Color.parseColor("#9E9D24"));
        
        colors.add(Color.parseColor("#827717"));

        colors.add(Color.parseColor("#F4FF81"));

        colors.add(Color.parseColor("#EEFF41"));

        colors.add(Color.parseColor("#C6FF00"));

        colors.add(Color.parseColor("#AEEA00"));

        
        colors.add(Color.parseColor("#FFEB3B"));
        
        colors.add(Color.parseColor("#FFFDE7"));
        
        colors.add(Color.parseColor("#FFF9C4"));
        
        colors.add(Color.parseColor("#FFF59D"));
        
        colors.add(Color.parseColor("#FFF176"));
        
        colors.add(Color.parseColor("#FFEE58"));
        
        colors.add(Color.parseColor("#FFEB3B"));
        
        colors.add(Color.parseColor("#FDD835"));
        
        colors.add(Color.parseColor("#FBC02D"));
        
        colors.add(Color.parseColor("#F9A825"));
        
        colors.add(Color.parseColor("#F57F17"));

        colors.add(Color.parseColor("#FFFF8D"));

        colors.add(Color.parseColor("#FFFF00"));

        colors.add(Color.parseColor("#FFEA00"));

        colors.add(Color.parseColor("#FFD600"));

        
        colors.add(Color.parseColor("#FFC107"));
        
        colors.add(Color.parseColor("#FFF8E1"));
        
        colors.add(Color.parseColor("#FFECB3"));
        
        colors.add(Color.parseColor("#FFE082"));
        
        colors.add(Color.parseColor("#FFD54F"));
        
        colors.add(Color.parseColor("#FFCA28"));
        
        colors.add(Color.parseColor("#FFC107"));
        
        colors.add(Color.parseColor("#FFB300"));
        
        colors.add(Color.parseColor("#FFA000"));
        
        colors.add(Color.parseColor("#FF8F00"));
        
        colors.add(Color.parseColor("#FF6F00"));

        colors.add(Color.parseColor("#FFE57F"));

        colors.add(Color.parseColor("#FFD740"));

        colors.add(Color.parseColor("#FFC400"));

        colors.add(Color.parseColor("#FFAB00"));

        
        colors.add(Color.parseColor("#FF9800"));
        
        colors.add(Color.parseColor("#FFF3E0"));
        
        colors.add(Color.parseColor("#FFE0B2"));
        
        colors.add(Color.parseColor("#FFCC80"));
        
        colors.add(Color.parseColor("#FFB74D"));
        
        colors.add(Color.parseColor("#FFA726"));
        
        colors.add(Color.parseColor("#FF9800"));
        
        colors.add(Color.parseColor("#FB8C00"));
        
        colors.add(Color.parseColor("#F57C00"));
        
        colors.add(Color.parseColor("#EF6C00"));
        
        colors.add(Color.parseColor("#E65100"));

        colors.add(Color.parseColor("#FFD180"));

        colors.add(Color.parseColor("#FFAB40"));

        colors.add(Color.parseColor("#FF9100"));

        colors.add(Color.parseColor("#FF6D00"));

        
        colors.add(Color.parseColor("#FF5722"));
        
        colors.add(Color.parseColor("#FBE9E7"));
        
        colors.add(Color.parseColor("#FFCCBC"));
        
        colors.add(Color.parseColor("#FFAB91"));
        
        colors.add(Color.parseColor("#FF8A65"));
        
        colors.add(Color.parseColor("#FF7043"));
        
        colors.add(Color.parseColor("#FF5722"));
        
        colors.add(Color.parseColor("#F4511E"));
        
        colors.add(Color.parseColor("#E64A19"));
        
        colors.add(Color.parseColor("#D84315"));
        
        colors.add(Color.parseColor("#BF360C"));

        colors.add(Color.parseColor("#FF9E80"));

        colors.add(Color.parseColor("#FF6E40"));

        colors.add(Color.parseColor("#FF3D00"));

        colors.add(Color.parseColor("#DD2C00"));

        
        colors.add(Color.parseColor("#795548"));
        
        colors.add(Color.parseColor("#EFEBE9"));
        
        colors.add(Color.parseColor("#D7CCC8"));
        
        colors.add(Color.parseColor("#BCAAA4"));
        
        colors.add(Color.parseColor("#A1887F"));
        
        colors.add(Color.parseColor("#8D6E63"));
        
        colors.add(Color.parseColor("#795548"));
        
        colors.add(Color.parseColor("#6D4C41"));
        
        colors.add(Color.parseColor("#5D4037"));
        
        colors.add(Color.parseColor("#4E342E"));
        
        colors.add(Color.parseColor("#3E2723"));

        
        colors.add(Color.parseColor("#9E9E9E"));
        
        colors.add(Color.parseColor("#FAFAFA"));
        
        colors.add(Color.parseColor("#F5F5F5"));
        
        colors.add(Color.parseColor("#EEEEEE"));
        
        colors.add(Color.parseColor("#E0E0E0"));
        
        colors.add(Color.parseColor("#BDBDBD"));
        
        colors.add(Color.parseColor("#9E9E9E"));
        
        colors.add(Color.parseColor("#757575"));
        
        colors.add(Color.parseColor("#616161"));
        
        colors.add(Color.parseColor("#424242"));
        
        colors.add(Color.parseColor("#212121"));
        
        
        colors.add(Color.parseColor("#607D8B"));
        
        colors.add(Color.parseColor("#ECEFF1"));
        
        colors.add(Color.parseColor("#CFD8DC"));
        
        colors.add(Color.parseColor("#B0BEC5"));
        
        colors.add(Color.parseColor("#90A4AE"));
        
        colors.add(Color.parseColor("#78909C"));
        
        colors.add(Color.parseColor("#607D8B"));
        return colors;
    }

    private ColorRandom() {
    }
}
