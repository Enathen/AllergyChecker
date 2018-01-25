package creativeendlessgrowingceg.allergychecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-01-23
 */

public class StartPageTip {
    static String getTip(Context context){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(context.getString(R.string.youCanUse));
        return arrayList.get(new Random().nextInt(arrayList.size()));
    }

}
