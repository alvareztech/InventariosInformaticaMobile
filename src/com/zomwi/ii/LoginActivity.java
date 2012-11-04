package com.zomwi.ii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText cajaUsername;
	private EditText cajaPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		cajaUsername = (EditText) findViewById(R.id.cajaUsername);
		cajaPassword = (EditText) findViewById(R.id.cajaPassword);
	}
	
	public void loguear(View view) {
		//Toast.makeText(this, "Intentando loguear", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
