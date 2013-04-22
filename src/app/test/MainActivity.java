package app.test;

//import app.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {

	Button btnLogin;
	private JSONParser jsonParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
	
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
            @Override
            public void onClick(View view) {
                
            	EditText netidText = (EditText) findViewById(R.id.netid);
        		String netid = netidText.getText().toString();
        		EditText passwordText = (EditText) findViewById(R.id.password);
        		String password = passwordText.getText().toString();
            	
            	jsonParser = new JSONParser();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("tag", "login"));
                nameValuePairs.add(new BasicNameValuePair("netid", netid));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                
                JSONObject json = jsonParser.getJSONFromUrl("http://studygroup.web.engr.illinois.edu/android/noHashIndex.php", nameValuePairs);
                Log.d("login output", json.toString());
                try {
					int loginResult = json.getInt("success");
					String user = json.getString("user");
					if(loginResult == 1){
						Intent i = new Intent(getApplicationContext(), Home.class);
		        		i.putExtra("userJSON", user);
		        		startActivity(i);
					}
					else{
						Toast.makeText(MainActivity.this,
								"Incorrect netid or password",
									Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
            	
                
                
            	
                /*Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        		i.putExtra("loginName", message);
        		i.putExtra("password", pwd);
        		startActivity(i);*/
 
            }
        });
	
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
