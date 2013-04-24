package app.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileActivity extends MenuActivity {

	private Spinner addClassList;
	private Spinner delClassList;
	private JSONParser jsonParser;
	Button addClassBtn;
	Button delClassBtn;
	String netid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
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
		
		jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "allCourses"));
        
        JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);
        Log.d("all classes result", json.toString());
        JSONArray addClassArray;
        try {
			addClassArray = json.getJSONArray("data");
		} catch (JSONException e) {
			addClassArray =  new JSONArray();
			e.printStackTrace();
		}
        List<String> classList = new ArrayList<String>();
        for (int i=0; i<addClassArray.length(); i++) {
            try {
				classList.add(addClassArray.getString(i) );
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        
        addClassList = (Spinner) findViewById(R.id.addClassList);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, classList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addClassList.setAdapter(dataAdapter);
        
        jsonParser = new JSONParser();
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "currentCourses"));
        params.add(new BasicNameValuePair("netid", netid));
        
        json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);
        Log.d("classes result", json.toString());
        JSONArray classArray;
        try {
			classArray = json.getJSONArray("data");
		} catch (JSONException e) {
			classArray =  new JSONArray();
			e.printStackTrace();
		}
        classList = new ArrayList<String>();
        for (int i=0; i<classArray.length(); i++) {
            try {
				classList.add(classArray.getString(i) );
			} catch (JSONException e) {
				e.printStackTrace();
			}
        }
        
        delClassList = (Spinner) findViewById(R.id.delClassList	);
        dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, classList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delClassList.setAdapter(dataAdapter);
        
        addClassBtn = (Button) findViewById(R.id.addClassBtn);
		addClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		String course = addClassList.getSelectedItem().toString();
            	//jsonParser = new JSONParser();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Course", course));
                try {
					netid = userData.getString("netid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
                Log.i("netid", netid);
                nameValuePairs.add(new BasicNameValuePair("netid", netid));
                nameValuePairs.add(new BasicNameValuePair("tag", "addCurrentCourse"));
                
                JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", nameValuePairs);

                Toast toast = Toast.makeText(ProfileActivity.this, "Class Added", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 120);
                toast.show();
 
            }
        });
		
		delClassBtn = (Button) findViewById(R.id.delClassBtn);
		delClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		String course = delClassList.getSelectedItem().toString();
            	//jsonParser = new JSONParser();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Course", course));
                try {
					netid = userData.getString("netid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
                Log.i("netid", netid);
                nameValuePairs.add(new BasicNameValuePair("netid", netid));
                nameValuePairs.add(new BasicNameValuePair("tag", "delCurrentCourse"));
                
                JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", nameValuePairs);

                Toast toast = Toast.makeText(ProfileActivity.this, "Class Removed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 120);
                toast.show();
 
            }
        });
		
		Button delCurrLocBtn = (Button) findViewById(R.id.delCurrLocBtn);
		delCurrLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        		
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                try {
					netid = userData.getString("netid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
                Log.i("netid", netid);
                params.add(new BasicNameValuePair("netid", netid));
                params.add(new BasicNameValuePair("tag", "delCurrLoc"));
                
                JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", params);

                Toast toast = Toast.makeText(ProfileActivity.this, "Location Removed", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 120);
                toast.show();
 
            }
        });
	}

}
