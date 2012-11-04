package com.zomwi.ii;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.zomwi.ii.clases.Activo;
import com.zomwi.ii.db.DatabaseAdapter;

public class NuevoActivoActivity extends SherlockActivity {

	private EditText cajaNombre;
	private Spinner spinnerTipo;
	private Spinner spinnerEstado;
	private EditText cajaDescripcion;
	private Button botonAceptar;
	private Button botonCancelar;
	// Database
	private DatabaseAdapter db;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nuevo_activo);
		// Database
		db = new DatabaseAdapter(this);
		// Controls
		cajaNombre = (EditText) findViewById(R.id.cajaNombre);
		spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
		spinnerEstado = (Spinner) findViewById(R.id.spinnerEstado);
		cajaDescripcion = (EditText) findViewById(R.id.cajaDescripcion);
		botonAceptar = (Button) findViewById(R.id.botonAceptar);
		botonCancelar = (Button) findViewById(R.id.botonCancelar);
		botonAceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				guardarActivoDb();
			}
		});
		botonCancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				NuevoActivoActivity.this.finish();
			}
		});
	}

	protected void guardarActivoDb() {
		String nombre = cajaNombre.getText().toString();
		int tipo = spinnerTipo.getSelectedItemPosition();
		int estado = spinnerEstado.getSelectedItemPosition();
		String descripcion = cajaDescripcion.getText().toString();
		Date fechaIngreso = Calendar.getInstance().getTime();
		Activo activo = new Activo(nombre, tipo, estado, descripcion, fechaIngreso);
		db.abrir();
		db.adicionarActivo(activo);
		db.cerrar();
		
		Toast.makeText(this, "Se adiciono nuevo registro.", Toast.LENGTH_LONG).show();
		this.finish();
	}
}
