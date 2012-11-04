package com.zomwi.ii;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.zomwi.ii.adapters.NormalListViewAdapter;
import com.zomwi.ii.db.DatabaseAdapter;

public class ActivosActivity extends SherlockActivity implements OnItemClickListener {

	private ListView lista;
	private NormalListViewAdapter adaptadorLista;
	private List<Integer> ids = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar_ForceOverflow);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Lista
		lista = (ListView) findViewById(R.id.lista);
		adaptadorLista = new NormalListViewAdapter(this);
		lista.setAdapter(adaptadorLista);
		lista.setOnItemClickListener(this);
		registerForContextMenu(lista);

	}

	@Override
	protected void onStart() {
		super.onStart();
		adaptadorLista.eliminarTodo();
		ids.clear();

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://192.168.43.241:8080/II/activo/listMobile");
		httpGet.setHeader("content-type", "application/json");

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			String resultado = EntityUtils.toString(httpResponse.getEntity());

			JSONArray json = new JSONArray(resultado);
			Log.i("ii", "Número de activos:" + json.length());
			for (int i = 0; i < json.length(); i++) {
				JSONObject obj = json.getJSONObject(i);
				ids.add(obj.getInt("id"));
				String nombre = obj.getString("nombre");
				String descripcion = obj.getString("descripcion");
				adaptadorLista.adicionarItem(R.drawable.ic_pantalla, nombre, descripcion);
			}

		} catch (Exception ex) {
			Log.e("ii", "Error!", ex);
		}

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int posicion, long arg3) {
		Intent intent = new Intent(this, ActivoActivity.class);
		intent.putExtra("id", ids.get(posicion));
		startActivity(intent);
	}

	// ***** MENU *****
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Actualizar").setIcon(R.drawable.ic_actualizar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add("Nuevo").setIcon(R.drawable.ic_mas).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.add("Nuevo desde QR").setIcon(R.drawable.ic_qrcode).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		String seleccionado = item.getTitle().toString();
		if (seleccionado.equals("Nuevo")) {
			Intent intentNuevoActivo = new Intent(this, NuevoActivoActivity.class);
			startActivity(intentNuevoActivo);
		} else if (seleccionado.equals("Nuevo desde QR")) {
			Intent intent = new Intent("com.zomwi.ii.SCAN");
			// intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} else if (seleccionado.equals("Actualizar")) {
			onStart();
		}

		return super.onOptionsItemSelected(item);
	}

	// ***** CONTEXT MENU *****

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.activity_activos, menu);

	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.accion_editar:
			return true;
		case R.id.accion_eliminar:
			// Toast.makeText(this, "> " + info.position,
			// Toast.LENGTH_LONG).show();
			int id = ids.get(info.position);
			// eliminarActivo(id);

			HttpClient httpClient = new DefaultHttpClient();
			HttpDelete del = new HttpDelete("http://192.168.43.241:8080/II/activo/" + id);
			del.setHeader("content-type", "application/json");

			try {
				HttpResponse resp = httpClient.execute(del);
				String respStr = EntityUtils.toString(resp.getEntity());
				if (respStr.equals("true")) {
					Toast.makeText(this, "Se elimino el activo.", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception ex) {
				Log.e("ServicioRest", "Error!", ex);
			}
			onStart();

			return true;
		case R.id.accion_ver_vodigo_qr:

			Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
			intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
			intent.putExtra("ENCODE_DATA", "Hola ZOMWI!!!");
			startActivity(intent);

			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}


	// ***** RESULT *****
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contenido = data.getStringExtra("SCAN_RESULT");
				String formato = data.getStringExtra("SCAN_RESULT_FORMAT");

				Toast.makeText(this, "Adicionado.", Toast.LENGTH_LONG).show();

				// Hacer algo con los datos obtenidos.
			} else if (resultCode == RESULT_CANCELED) {
				// Si se cancelo la captura.
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
