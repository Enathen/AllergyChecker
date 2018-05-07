package creativeendlessgrowingceg.allergychecker;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * receive nice randomColor
 *
 * @author Jonathan Alexander Norberg
 * @version 2017-12-09
 */

public class ColorRandom {
    private static ArrayList<Integer> randomColor;
    private static int random;
    public static int getRandomColorFromArray() {
        if(randomColor == null ){
            getRandomColor();
            random = new Random().nextInt(randomColor.size());
        }
        random++;
        return randomColor.get(random % randomColor.size());
    }

    private static ArrayList<Integer> getRandomColor() {
        randomColor= new ArrayList<>();

        randomColor.add(Color.parseColor("#FFEBEE"));
        randomColor.add(Color.parseColor("#FFCDD2"));
        randomColor.add(Color.parseColor("#EF9A9A"));
        randomColor.add(Color.parseColor("#E57373"));
        randomColor.add(Color.parseColor("#EF5350"));
        randomColor.add(Color.parseColor("#F44336"));
        randomColor.add(Color.parseColor("#E53935"));
        randomColor.add(Color.parseColor("#D32F2F"));
        randomColor.add(Color.parseColor("#C62828"));
        randomColor.add(Color.parseColor("#B71C1C"));
        randomColor.add(Color.parseColor("#FF8A80"));
        randomColor.add(Color.parseColor("#FF5252"));
        randomColor.add(Color.parseColor("#FF1744"));
        randomColor.add(Color.parseColor("#D50000"));
        randomColor.add(Color.parseColor("#E91E63"));
        randomColor.add(Color.parseColor("#FCE4EC"));
        randomColor.add(Color.parseColor("#F8BBD0"));
        randomColor.add(Color.parseColor("#F48FB1"));
        randomColor.add(Color.parseColor("#F06292"));
        randomColor.add(Color.parseColor("#EC407A"));
        randomColor.add(Color.parseColor("#E91E63"));
        randomColor.add(Color.parseColor("#D81B60"));
        randomColor.add(Color.parseColor("#C2185B"));
        randomColor.add(Color.parseColor("#AD1457"));
        randomColor.add(Color.parseColor("#880E4F"));
        randomColor.add(Color.parseColor("#FF80AB"));
        randomColor.add(Color.parseColor("#FF4081"));
        randomColor.add(Color.parseColor("#F50057"));
        randomColor.add(Color.parseColor("#C51162"));
        randomColor.add(Color.parseColor("#9C27B0"));
        randomColor.add(Color.parseColor("#F3E5F5"));
        randomColor.add(Color.parseColor("#E1BEE7"));
        randomColor.add(Color.parseColor("#CE93D8"));
        randomColor.add(Color.parseColor("#BA68C8"));
        randomColor.add(Color.parseColor("#AB47BC"));
        randomColor.add(Color.parseColor("#9C27B0"));
        randomColor.add(Color.parseColor("#8E24AA"));
        randomColor.add(Color.parseColor("#7B1FA2"));
        randomColor.add(Color.parseColor("#6A1B9A"));
        randomColor.add(Color.parseColor("#4A148C"));
        randomColor.add(Color.parseColor("#EA80FC"));
        randomColor.add(Color.parseColor("#E040FB"));
        randomColor.add(Color.parseColor("#D500F9"));
        randomColor.add(Color.parseColor("#AA00FF"));


        randomColor.add(Color.parseColor("#673AB7"));

        randomColor.add(Color.parseColor("#EDE7F6"));

        randomColor.add(Color.parseColor("#D1C4E9"));

        randomColor.add(Color.parseColor("#B39DDB"));

        randomColor.add(Color.parseColor("#9575CD"));

        randomColor.add(Color.parseColor("#7E57C2"));

        randomColor.add(Color.parseColor("#673AB7"));

        randomColor.add(Color.parseColor("#5E35B1"));

        randomColor.add(Color.parseColor("#512DA8"));

        randomColor.add(Color.parseColor("#4527A0"));

        randomColor.add(Color.parseColor("#311B92"));

        randomColor.add(Color.parseColor("#B388FF"));

        randomColor.add(Color.parseColor("#7C4DFF"));

        randomColor.add(Color.parseColor("#651FFF"));

        randomColor.add(Color.parseColor("#6200EA"));


        randomColor.add(Color.parseColor("#3F51B5"));

        randomColor.add(Color.parseColor("#E8EAF6"));

        randomColor.add(Color.parseColor("#C5CAE9"));

        randomColor.add(Color.parseColor("#9FA8DA"));

        randomColor.add(Color.parseColor("#7986CB"));

        randomColor.add(Color.parseColor("#5C6BC0"));

        randomColor.add(Color.parseColor("#3F51B5"));

        randomColor.add(Color.parseColor("#3949AB"));

        randomColor.add(Color.parseColor("#303F9F"));

        randomColor.add(Color.parseColor("#283593"));

        randomColor.add(Color.parseColor("#1A237E"));

        randomColor.add(Color.parseColor("#8C9EFF"));

        randomColor.add(Color.parseColor("#536DFE"));

        randomColor.add(Color.parseColor("#3D5AFE"));

        randomColor.add(Color.parseColor("#304FFE"));


        randomColor.add(Color.parseColor("#2196F3"));

        randomColor.add(Color.parseColor("#E3F2FD"));

        randomColor.add(Color.parseColor("#BBDEFB"));

        randomColor.add(Color.parseColor("#90CAF9"));

        randomColor.add(Color.parseColor("#64B5F6"));

        randomColor.add(Color.parseColor("#42A5F5"));

        randomColor.add(Color.parseColor("#2196F3"));

        randomColor.add(Color.parseColor("#1E88E5"));

        randomColor.add(Color.parseColor("#1976D2"));

        randomColor.add(Color.parseColor("#1565C0"));

        randomColor.add(Color.parseColor("#0D47A1"));

        randomColor.add(Color.parseColor("#82B1FF"));

        randomColor.add(Color.parseColor("#448AFF"));

        randomColor.add(Color.parseColor("#2979FF"));

        randomColor.add(Color.parseColor("#2962FF"));


        randomColor.add(Color.parseColor("#03A9F4"));

        randomColor.add(Color.parseColor("#E1F5FE"));

        randomColor.add(Color.parseColor("#B3E5FC"));

        randomColor.add(Color.parseColor("#81D4FA"));

        randomColor.add(Color.parseColor("#4FC3F7"));

        randomColor.add(Color.parseColor("#29B6F6"));

        randomColor.add(Color.parseColor("#03A9F4"));

        randomColor.add(Color.parseColor("#039BE5"));

        randomColor.add(Color.parseColor("#0288D1"));

        randomColor.add(Color.parseColor("#0277BD"));

        randomColor.add(Color.parseColor("#01579B"));

        randomColor.add(Color.parseColor("#80D8FF"));

        randomColor.add(Color.parseColor("#40C4FF"));

        randomColor.add(Color.parseColor("#00B0FF"));

        randomColor.add(Color.parseColor("#0091EA"));


        randomColor.add(Color.parseColor("#00BCD4"));

        randomColor.add(Color.parseColor("#E0F7FA"));

        randomColor.add(Color.parseColor("#B2EBF2"));

        randomColor.add(Color.parseColor("#80DEEA"));

        randomColor.add(Color.parseColor("#4DD0E1"));

        randomColor.add(Color.parseColor("#26C6DA"));

        randomColor.add(Color.parseColor("#00BCD4"));

        randomColor.add(Color.parseColor("#00ACC1"));

        randomColor.add(Color.parseColor("#0097A7"));

        randomColor.add(Color.parseColor("#00838F"));

        randomColor.add(Color.parseColor("#006064"));

        randomColor.add(Color.parseColor("#84FFFF"));

        randomColor.add(Color.parseColor("#18FFFF"));

        randomColor.add(Color.parseColor("#00E5FF"));

        randomColor.add(Color.parseColor("#00B8D4"));


        randomColor.add(Color.parseColor("#009688"));

        randomColor.add(Color.parseColor("#E0F2F1"));

        randomColor.add(Color.parseColor("#B2DFDB"));

        randomColor.add(Color.parseColor("#80CBC4"));

        randomColor.add(Color.parseColor("#4DB6AC"));

        randomColor.add(Color.parseColor("#26A69A"));

        randomColor.add(Color.parseColor("#009688"));

        randomColor.add(Color.parseColor("#00897B"));

        randomColor.add(Color.parseColor("#00796B"));

        randomColor.add(Color.parseColor("#00695C"));

        randomColor.add(Color.parseColor("#004D40"));

        randomColor.add(Color.parseColor("#A7FFEB"));

        randomColor.add(Color.parseColor("#64FFDA"));

        randomColor.add(Color.parseColor("#1DE9B6"));

        randomColor.add(Color.parseColor("#00BFA5"));


        randomColor.add(Color.parseColor("#4CAF50"));

        randomColor.add(Color.parseColor("#E8F5E9"));

        randomColor.add(Color.parseColor("#C8E6C9"));

        randomColor.add(Color.parseColor("#A5D6A7"));

        randomColor.add(Color.parseColor("#81C784"));

        randomColor.add(Color.parseColor("#66BB6A"));

        randomColor.add(Color.parseColor("#4CAF50"));

        randomColor.add(Color.parseColor("#43A047"));

        randomColor.add(Color.parseColor("#388E3C"));

        randomColor.add(Color.parseColor("#2E7D32"));

        randomColor.add(Color.parseColor("#1B5E20"));

        randomColor.add(Color.parseColor("#B9F6CA"));

        randomColor.add(Color.parseColor("#69F0AE"));

        randomColor.add(Color.parseColor("#00E676"));

        randomColor.add(Color.parseColor("#00C853"));


        randomColor.add(Color.parseColor("#8BC34A"));

        randomColor.add(Color.parseColor("#F1F8E9"));

        randomColor.add(Color.parseColor("#DCEDC8"));

        randomColor.add(Color.parseColor("#C5E1A5"));

        randomColor.add(Color.parseColor("#AED581"));

        randomColor.add(Color.parseColor("#9CCC65"));

        randomColor.add(Color.parseColor("#8BC34A"));

        randomColor.add(Color.parseColor("#7CB342"));

        randomColor.add(Color.parseColor("#689F38"));

        randomColor.add(Color.parseColor("#558B2F"));

        randomColor.add(Color.parseColor("#33691E"));

        randomColor.add(Color.parseColor("#CCFF90"));

        randomColor.add(Color.parseColor("#B2FF59"));

        randomColor.add(Color.parseColor("#76FF03"));

        randomColor.add(Color.parseColor("#64DD17"));


        randomColor.add(Color.parseColor("#CDDC39"));

        randomColor.add(Color.parseColor("#F9FBE7"));

        randomColor.add(Color.parseColor("#F0F4C3"));

        randomColor.add(Color.parseColor("#E6EE9C"));

        randomColor.add(Color.parseColor("#DCE775"));

        randomColor.add(Color.parseColor("#D4E157"));

        randomColor.add(Color.parseColor("#CDDC39"));

        randomColor.add(Color.parseColor("#C0CA33"));

        randomColor.add(Color.parseColor("#AFB42B"));

        randomColor.add(Color.parseColor("#9E9D24"));

        randomColor.add(Color.parseColor("#827717"));

        randomColor.add(Color.parseColor("#F4FF81"));

        randomColor.add(Color.parseColor("#EEFF41"));

        randomColor.add(Color.parseColor("#C6FF00"));

        randomColor.add(Color.parseColor("#AEEA00"));


        randomColor.add(Color.parseColor("#FFEB3B"));

        randomColor.add(Color.parseColor("#FFFDE7"));

        randomColor.add(Color.parseColor("#FFF9C4"));

        randomColor.add(Color.parseColor("#FFF59D"));

        randomColor.add(Color.parseColor("#FFF176"));

        randomColor.add(Color.parseColor("#FFEE58"));

        randomColor.add(Color.parseColor("#FFEB3B"));

        randomColor.add(Color.parseColor("#FDD835"));

        randomColor.add(Color.parseColor("#FBC02D"));

        randomColor.add(Color.parseColor("#F9A825"));

        randomColor.add(Color.parseColor("#F57F17"));

        randomColor.add(Color.parseColor("#FFFF8D"));

        randomColor.add(Color.parseColor("#FFFF00"));

        randomColor.add(Color.parseColor("#FFEA00"));

        randomColor.add(Color.parseColor("#FFD600"));


        randomColor.add(Color.parseColor("#FFC107"));

        randomColor.add(Color.parseColor("#FFF8E1"));

        randomColor.add(Color.parseColor("#FFECB3"));

        randomColor.add(Color.parseColor("#FFE082"));

        randomColor.add(Color.parseColor("#FFD54F"));

        randomColor.add(Color.parseColor("#FFCA28"));

        randomColor.add(Color.parseColor("#FFC107"));

        randomColor.add(Color.parseColor("#FFB300"));

        randomColor.add(Color.parseColor("#FFA000"));

        randomColor.add(Color.parseColor("#FF8F00"));

        randomColor.add(Color.parseColor("#FF6F00"));

        randomColor.add(Color.parseColor("#FFE57F"));

        randomColor.add(Color.parseColor("#FFD740"));

        randomColor.add(Color.parseColor("#FFC400"));

        randomColor.add(Color.parseColor("#FFAB00"));


        randomColor.add(Color.parseColor("#FF9800"));

        randomColor.add(Color.parseColor("#FFF3E0"));

        randomColor.add(Color.parseColor("#FFE0B2"));

        randomColor.add(Color.parseColor("#FFCC80"));

        randomColor.add(Color.parseColor("#FFB74D"));

        randomColor.add(Color.parseColor("#FFA726"));

        randomColor.add(Color.parseColor("#FF9800"));

        randomColor.add(Color.parseColor("#FB8C00"));

        randomColor.add(Color.parseColor("#F57C00"));

        randomColor.add(Color.parseColor("#EF6C00"));

        randomColor.add(Color.parseColor("#E65100"));

        randomColor.add(Color.parseColor("#FFD180"));

        randomColor.add(Color.parseColor("#FFAB40"));

        randomColor.add(Color.parseColor("#FF9100"));

        randomColor.add(Color.parseColor("#FF6D00"));


        randomColor.add(Color.parseColor("#FF5722"));

        randomColor.add(Color.parseColor("#FBE9E7"));

        randomColor.add(Color.parseColor("#FFCCBC"));

        randomColor.add(Color.parseColor("#FFAB91"));

        randomColor.add(Color.parseColor("#FF8A65"));

        randomColor.add(Color.parseColor("#FF7043"));

        randomColor.add(Color.parseColor("#FF5722"));

        randomColor.add(Color.parseColor("#F4511E"));

        randomColor.add(Color.parseColor("#E64A19"));

        randomColor.add(Color.parseColor("#D84315"));

        randomColor.add(Color.parseColor("#BF360C"));

        randomColor.add(Color.parseColor("#FF9E80"));

        randomColor.add(Color.parseColor("#FF6E40"));

        randomColor.add(Color.parseColor("#FF3D00"));

        randomColor.add(Color.parseColor("#DD2C00"));


        randomColor.add(Color.parseColor("#795548"));

        randomColor.add(Color.parseColor("#EFEBE9"));

        randomColor.add(Color.parseColor("#D7CCC8"));

        randomColor.add(Color.parseColor("#BCAAA4"));

        randomColor.add(Color.parseColor("#A1887F"));

        randomColor.add(Color.parseColor("#8D6E63"));

        randomColor.add(Color.parseColor("#795548"));

        randomColor.add(Color.parseColor("#6D4C41"));

        randomColor.add(Color.parseColor("#5D4037"));

        randomColor.add(Color.parseColor("#4E342E"));

        randomColor.add(Color.parseColor("#3E2723"));


        randomColor.add(Color.parseColor("#9E9E9E"));

        randomColor.add(Color.parseColor("#FAFAFA"));

        randomColor.add(Color.parseColor("#F5F5F5"));

        randomColor.add(Color.parseColor("#EEEEEE"));

        randomColor.add(Color.parseColor("#E0E0E0"));

        randomColor.add(Color.parseColor("#BDBDBD"));

        randomColor.add(Color.parseColor("#9E9E9E"));

        randomColor.add(Color.parseColor("#757575"));

        randomColor.add(Color.parseColor("#616161"));

        randomColor.add(Color.parseColor("#424242"));

        randomColor.add(Color.parseColor("#212121"));


        randomColor.add(Color.parseColor("#607D8B"));

        randomColor.add(Color.parseColor("#ECEFF1"));

        randomColor.add(Color.parseColor("#CFD8DC"));

        randomColor.add(Color.parseColor("#B0BEC5"));

        randomColor.add(Color.parseColor("#90A4AE"));

        randomColor.add(Color.parseColor("#78909C"));

        randomColor.add(Color.parseColor("#607D8B"));
        return randomColor;
    }
}
