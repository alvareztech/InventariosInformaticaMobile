package com.zomwi.ii;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
				"http://192.168.43.241:8080/II/activo/all");
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
				String descripcion = obj.getString("ambienteActual");
				adaptadorLista.adicionarItem(R.drawable.ic_pantalla, nombre,
						!descripcion.equals("null") ? descripcion : "Ninguno");
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

}
