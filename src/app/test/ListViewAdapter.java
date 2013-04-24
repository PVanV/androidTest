package app.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    private Context context = null;
    private List<String> fields = null;

    public ListViewAdapter(Context context, JSONArray arr) {
        this.context = context;
        this.fields = new ArrayList<String>();
        for (int i=0; i<arr.length(); ++i) {
            try {
                fields.add(arr.getJSONObject(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getCount() {
        return fields.size();
    }

    @Override
    public Object getItem(int position) {
        return fields.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        TextView txt = (TextView) convertView;//.findViewById(R.id.ItemList_txt); //maybe should be the text view?
        txt.setText(fields.get(position));
        return convertView;
    }

	/*@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}*/

}
