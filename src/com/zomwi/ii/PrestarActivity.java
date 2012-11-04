package com.zomwi.ii;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class PrestarActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar_ForceOverflow);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prestar);
		
		
	}

}
