package com.zomwi.ii;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegistroActivity extends Activity {

	private TextView textoSN;
	private String serie = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		textoSN = (TextView) findViewById(R.id.sn);

		serie = getIntent().getStringExtra("serie");
		textoSN.setText(serie);
	}

	public void prestar(View view) {

	}

	public void cambiarEstado(View view) {

	}

	public void mover(View view) {

	}

}
