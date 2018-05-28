package creativeendlessgrowingceg.allergychecker;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Integer> Allergies = null;
    private HashMap<Integer, Integer> integerIntegerHashMap;
    private ArrayList<Integer> arraylist;

    public ListViewAdapter(Context context, ArrayList<Integer> Allergies, HashMap<Integer, Integer> integerIntegerHashMap) {
        mContext = context;
        this.Allergies = Allergies;
        this.integerIntegerHashMap = integerIntegerHashMap;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Integer>();
        this.arraylist.addAll(Allergies);
    }

    public class ViewHolder {
        TextView name;
        CheckBox checkBox;
    }


    @Override
    public int getCount() {
        return Allergies.size();
    }

    @Override
    public Integer getItem(int position) {
        return Allergies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        final String correctKey = String.valueOf(integerIntegerHashMap.get(Allergies.get(position)));
        holder = new ViewHolder();
        view = inflater.inflate(R.layout.listview_item, null);
        holder.name = (TextView) view.findViewById(R.id.name);
        holder.checkBox = (CheckBox) view.findViewById(R.id.checkBoxListView);
        holder.name.setText(TextHandler.capitalLetter(TextHandler.cutFirstWord(mContext.getString(Allergies.get(position)))));
        // Set the results into TextViews
        holder.checkBox = (CheckBox) view.findViewById(R.id.checkBoxListView);
        holder.checkBox.setChecked(PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(correctKey,false));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putBoolean(correctKey, b).apply();
            }
        });
        view.setTag(holder);
        return view;
    }
    public View getViewLayout(View view){
        return view;
    }

    public int filter(String charText) {
        charText = charText.toLowerCase();
        Allergies.clear();
        if (charText.length() == 0) {
            Allergies.addAll(arraylist);
        } else {

            for (Integer string : arraylist) {
                String s = TextHandler.capitalLetter(TextHandler.cutFirstWord(mContext.getString(string)));
                if (s.toLowerCase().contains(charText)) {
                    Allergies.add(string);
                }
            }
        }
        notifyDataSetChanged();
        return Allergies.size();
    }

}