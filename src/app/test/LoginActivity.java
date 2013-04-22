package app.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private JSONParser jsonParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Bundle extras = getIntent().getExtras();
		String netid = extras.getString("loginName");
		String password = extras.getString("password");
		
		
		
		
		// Create the text view
        /*TextView textView = new TextView(this);
        textView.setTextSize(32);
        textView.setText(netid+" - "+password);
        setContentView(textView);*/
        

	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("tag", "login"));
	        nameValuePairs.add(new BasicNameValuePair("netid", netid));
	        nameValuePairs.add(new BasicNameValuePair("password", password));
	        
	        JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", nameValuePairs);
	        Log.d("login output", json.toString());
	        
	        
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
