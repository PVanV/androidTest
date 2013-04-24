package app.test;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends Activity {

	JSONObject userData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
		Intent i;
	    switch (item.getItemId()) {
	        case R.id.Home:
	        	if(findViewById(R.id.Home) == null ){
		        	i = new Intent(getApplicationContext(), Home.class);
	        		i.putExtra("userJSON", userData.toString());
	        		startActivity(i);
		            return true;
	        	}
	        	return false;
	        case R.id.Search:
	        	if(findViewById(R.id.Home) == null ){
		        	i = new Intent(getApplicationContext(), SearchActivity.class);
	        		i.putExtra("userJSON", userData.toString());
	        		startActivity(i);
		            return true;
	        	}
	        	return false;
	        case R.id.myProfile:
	        	i = new Intent(getApplicationContext(), ProfileActivity.class);
        		i.putExtra("userJSON", userData.toString());
        		startActivity(i);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
