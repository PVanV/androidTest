package app.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends MenuActivity {

	private ListViewAdapter adapter = null;
    private ListView myList = null;
    private JSONArray items;
    private Spinner classList;
	private Spinner locationList;
	private JSONParser jsonParser;
	Button currLocBtn;
	String netid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		myList = (ListView) findViewById(R.id.resultList);
		Bundle extras = getIntent().getExtras();
		String userString = extras.getString("userJSON");
		netid = "error";
		try {
			JSONObject userJSON = new JSONObject(userString);
			userData = userJSON;
			netid = userJSON.getString("netid");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		JSONParser jsonParser2 = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "currentCourses"));
        params.add(new BasicNameValuePair("netid", netid));
        
        JSONObject json2 = jsonParser2.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);
        Log.d("currClasses result", json2.toString());
        JSONArray classArray;
        try {
			classArray = json2.getJSONArray("data");
		} catch (JSONException e) {
			classArray =  new JSONArray();
			e.printStackTrace();
		}
        List<String> classlist = new ArrayList<String>();
        for (int i=0; i<classArray.length(); i++) {
            try {
				classlist.add(classArray.getString(i) );
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        
        classList = (Spinner) findViewById(R.id.classList);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, classlist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classList.setAdapter(dataAdapter);
        
        
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "locations"));
        json2 = jsonParser2.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);
        Log.d("location result", json2.toString());
        JSONArray locArray;
        try {
			locArray = json2.getJSONArray("data");
		} catch (JSONException e) {
			locArray =  new JSONArray();
			e.printStackTrace();
		}
        List<String> locList = new ArrayList<String>();
        for (int i=0; i<locArray.length(); i++) {
            try {
				locList.add(locArray.getString(i) );
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        
        locationList = (Spinner) findViewById(R.id.locationList);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, locList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationList.setAdapter(dataAdapter2);
        
        Button locationBtn = (Button) findViewById(R.id.locationBtn);
		locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                String location = locationList.getSelectedItem().toString();
                try {
					netid = userData.getString("netid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
                Log.i("netid", netid);
                params.add(new BasicNameValuePair("netid", netid));
                params.add(new BasicNameValuePair("location", location));
                params.add(new BasicNameValuePair("tag", "locationSearch"));
                
                JSONParser jsonParser2 = new JSONParser();
                JSONObject json = jsonParser2.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);
                try {
        			items = json.getJSONArray("data");
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
                adapter = new ListViewAdapter(SearchActivity.this, items);
                myList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
 
            }
        });
		
		Button classBtn = (Button) findViewById(R.id.classBtn);
		classBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                String course = classList.getSelectedItem().toString();
                try {
					netid = userData.getString("netid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
                Log.i("netid", netid);
                Log.i("course", course);
                params.add(new BasicNameValuePair("netid", netid));
                params.add(new BasicNameValuePair("course", course));
                params.add(new BasicNameValuePair("tag", "classSearch"));
                
                JSONParser jsonParser2 = new JSONParser();
                JSONObject json = jsonParser2.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);
                try {
        			items = json.getJSONArray("data");
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
                Log.i("items", items.toString());
                adapter = new ListViewAdapter(SearchActivity.this, items);
                myList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
 
            }
        });
        
	}

	

}
