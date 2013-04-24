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
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Home extends MenuActivity {

	private Spinner classSpinner;
	private Spinner locationSpinner;
	private JSONParser jsonParser;
	Button currLocBtn;
	String netid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
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
        Log.d("classes result", json2.toString());
        JSONArray classArray;
        try {
			classArray = json2.getJSONArray("data");
		} catch (JSONException e) {
			classArray =  new JSONArray();
			e.printStackTrace();
		}
        List<String> classList = new ArrayList<String>();
        for (int i=0; i<classArray.length(); i++) {
            try {
				classList.add(classArray.getString(i) );
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        
        classSpinner = (Spinner) findViewById(R.id.classSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, classList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(dataAdapter);
        
        
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
        
        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, locList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(dataAdapter2);
        
        currLocBtn = (Button) findViewById(R.id.currLocBtn);
		currLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		String location = locationSpinner.getSelectedItem().toString();
        		String course = classSpinner.getSelectedItem().toString();
        		EditText timeText = (EditText) findViewById(R.id.time);
        		String time = timeText.getText().toString();
        		EditText descriptionText = (EditText) findViewById(R.id.description);
        		String description = descriptionText.getText().toString();
            	
            	jsonParser = new JSONParser();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("time", time));
                try {
					netid = userData.getString("netid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
                Log.i("netid", netid);
                nameValuePairs.add(new BasicNameValuePair("netid", netid));
                nameValuePairs.add(new BasicNameValuePair("Place", location));
                nameValuePairs.add(new BasicNameValuePair("text", description));
                nameValuePairs.add(new BasicNameValuePair("Course", course));
                
                JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/setCurrentLocation.php", nameValuePairs);

                Toast toast = Toast.makeText(Home.this, "Location Set", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 120);
                toast.show();
 
            }
        });
		
		
	}

}
