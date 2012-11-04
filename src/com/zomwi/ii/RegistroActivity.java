package com.zomwi.ii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.zomwi.ii.adapters.NormalListViewAdapter;

public class RegistroActivity extends SherlockActivity implements OnItemClickListener {

	private ListView lista;
	private NormalListViewAdapter adaptadorLista;

	private TextView textoSN;

	private String serie = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar_ForceOverflow);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		// getSupportActionBar().setDisplayShowHomeEnabled(true);

		setTitle("Selecciona una acción");

		lista = (ListView) findViewById(R.id.lista);
		adaptadorLista = new NormalListViewAdapter(this);
		lista.setAdapter(adaptadorLista);
		lista.setOnItemClickListener(this);

		textoSN = (TextView) findViewById(R.id.sn);

		adaptadorLista.adicionarItem(R.drawable.ic_action_prestar, "Prestar", "Prestar el activo fijo a una persona solicitante.");
		adaptadorLista.adicionarItem(R.drawable.ic_action_cambiar_estado, "Cambiar Estado",
				"Cambiar de estado en activo fijo, ya sea por tiempo o por defecto encontrado.");
		adaptadorLista.adicionarItem(R.drawable.ic_action_mover, "Mover", "Cambiar de ubicación el activo fijo de un ambiente a otro.");

		serie = getIntent().getStringExtra("serie");
		textoSN.setText(serie);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		switch (pos) {
		case 0:
			Intent intentPrestar = new Intent(this, PrestarActivity.class);
			intentPrestar.putExtra("serie", serie);
			startActivity(intentPrestar);
			break;
		case 1:
			Intent intentCambiarEstado = new Intent(this, CambiarEstadoActivity.class);
			intentCambiarEstado.putExtra("serie", serie);
			startActivity(intentCambiarEstado);
			break;
		case 2:

			break;
		}

	}

}
