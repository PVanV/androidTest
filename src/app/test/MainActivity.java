package app.test;

//import app.test;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {

	Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

        
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
	
		btnLogin.setOnClickListener(new View.OnClickListener() {
			 
            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                EditText netidText = (EditText) findViewById(R.id.netid);
        		String message = netidText.getText().toString();
        		i.putExtra("loginName", message);
        		EditText passwordText = (EditText) findViewById(R.id.password);
        		String pwd = passwordText.getText().toString();
        		i.putExtra("password", pwd);
        		startActivity(i);
 
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
