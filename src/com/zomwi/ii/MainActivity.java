package com.zomwi.ii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.zomwi.ii.adapters.NormalListViewAdapter;

public class MainActivity extends SherlockActivity implements OnItemClickListener {

	private ListView lista;
	private NormalListViewAdapter adaptadorLista;

	// Constantes
	public static final int ITEM_ACTIVOS = 0;
	public static final int ITEM_AMBIENTES = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar_ForceOverflow);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		// getSupportActionBar().setDisplayShowHomeEnabled(true);

		lista = (ListView) findViewById(R.id.lista);
		adaptadorLista = new NormalListViewAdapter(this);
		lista.setAdapter(adaptadorLista);

		lista.setOnItemClickListener(this);

		adaptadorLista.adicionarItem(R.drawable.ic_activos, "Activos", "Listado de todos los activos fijos de la carrera de Informática.");
		adaptadorLista.adicionarItem(R.drawable.ic_registros, "Ambientes",
				"Diferentes ambientes de la carrera de Informática, lugares donde estan ubicados los activos fijos.");
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int posicion, long arg3) {
		switch (posicion) {
		case ITEM_ACTIVOS:
			Intent intentActivos = new Intent(this, ActivosActivity.class);
			startActivity(intentActivos);
			break;
		case ITEM_AMBIENTES:
			Intent intentAmbientes = new Intent(this, AmbientesActivity.class);
			startActivity(intentAmbientes);
			break;
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
				Toast.makeText(this, R.string.captura_cancelada, Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/*********************************** MENU */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home || item.getItemId() == 0) {
			return false;
		}
		switch (item.getItemId()) {
		case R.id.menu_registrar:
			Intent intent = new Intent("com.zomwi.ii.SCAN");
			// intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
			break;

		}
		return true;
	}
}
