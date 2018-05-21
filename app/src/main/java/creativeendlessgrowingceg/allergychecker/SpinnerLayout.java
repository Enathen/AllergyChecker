package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Locale;
import java.util.TreeMap;

/**
 * @author Jonathan Alexander Norberg
 * @version 2018-05-06
 */

public class SpinnerLayout {
    private final TreeMap<String, Locale> stringLocaleTreeMap;
    private Spinner spinner;
    private LinearLayout view;


    public SpinnerLayout(SpinnerLayoutBuilder SpinnerLayoutBuilder) {
        this.view = SpinnerLayoutBuilder.view;
        this.spinner = SpinnerLayoutBuilder.spinner;
        this.stringLocaleTreeMap = SpinnerLayoutBuilder.stringLocaleTreeMap;

    }

    public LinearLayout getView() {
        return view;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setSpinner(Spinner spinner) {
        this.spinner = spinner;
    }

    public void setView(LinearLayout view) {
        this.view = view;
    }

    public TreeMap<String, Locale> getStringLocaleTreeMap() {
        return stringLocaleTreeMap;
    }

    public static class SpinnerLayoutBuilder {
        private static final String TAG = "SLIDERLAYOUT";
        private final TreeMap<String, Locale> stringLocaleTreeMap;
        private Spinner spinner;
        private LinearLayout view;
        private Context context;

        public SpinnerLayoutBuilder(Context context, TreeMap<String, Locale> stringLocaleTreeMap) {
            this.stringLocaleTreeMap = stringLocaleTreeMap;
            this.context = context;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = (LinearLayout) inflater.inflate(R.layout.spinner_layout, null);
            spinner = view.findViewById(R.id.spinnerSpinnerLayout);
            final String[] keys = stringLocaleTreeMap.keySet().toArray(new String[0]);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                    R.layout.spinner_item, keys);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

            spinner.setAdapter(adapter);
            spinner.setSelection(PreferenceManager.getDefaultSharedPreferences(context).getInt(APISharedPreference.getSpinnerPosition(),0));


        }



        public SpinnerLayout buildSpinnerLayout() {

            return new SpinnerLayout(this);
        }


    }
}
