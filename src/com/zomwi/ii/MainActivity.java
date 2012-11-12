package com.zomwi.ii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zomwi.ii.util.Util;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!Util.isOnline(this)) {
			Util.showDialogNoInternet(this);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contenido = data.getStringExtra("SCAN_RESULT");
				// String formato = data.getStringExtra("SCAN_RESULT_FORMAT");

				Intent intentRegistro = new Intent(this, RegistroActivity.class);
				intentRegistro.putExtra("serie", contenido);
				startActivity(intentRegistro);

				Toast.makeText(this, "Adicionado.", Toast.LENGTH_LONG).show();

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, R.string.captura_cancelada,
						Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void capturar(View view) {
		Intent intent = new Intent("com.zomwi.ii.SCAN");
		// intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}
	
	public void verRegistros(View view) {
		
	}

	public void verActivos(View view) {
		Intent intentActivos = new Intent(this, ActivosActivity.class);
		startActivity(intentActivos);
	}

	public void verAmbientes(View view) {
		Intent intentAmbientes = new Intent(this, AmbientesActivity.class);
		startActivity(intentAmbientes);
	}
}
