package com.zomwi.ii;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
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

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.zomwi.ii.adapters.NormalListViewAdapter;

public class ActivosActivity extends Activity implements OnItemClickListener {

	private ListView lista;
	private NormalListViewAdapter adaptadorLista;
	private List<Integer> ids = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activos);

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
		HttpGet httpGet = new HttpGet(
				"http://192.168.43.241:8080/II/activo/listMobile");
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
				adaptadorLista.adicionarItem(R.drawable.ic_pantalla, nombre,
						descripcion);
			}

		} catch (Exception ex) {
			Log.e("ii", "Error!", ex);
		}

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int posicion,
			long arg3) {
		Intent intent = new Intent(this, ActivoActivity.class);
		intent.putExtra("id", ids.get(posicion));
		startActivity(intent);
	}

	// ***** CONTEXT MENU *****

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.activity_activos, menu);

	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.accion_editar:
			return true;
		case R.id.accion_eliminar:
			// Toast.makeText(this, "> " + info.position,
			// Toast.LENGTH_LONG).show();
			int id = ids.get(info.position);
			// eliminarActivo(id);

			HttpClient httpClient = new DefaultHttpClient();
			HttpDelete del = new HttpDelete(
					"http://192.168.43.241:8080/II/activo/" + id);
			del.setHeader("content-type", "application/json");

			try {
				HttpResponse resp = httpClient.execute(del);
				String respStr = EntityUtils.toString(resp.getEntity());
				if (respStr.equals("true")) {
					Toast.makeText(this, "Se elimino el activo.",
							Toast.LENGTH_SHORT).show();
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
}
