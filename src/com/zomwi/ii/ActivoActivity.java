package com.zomwi.ii;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zomwi.ii.adapters.NormalListViewAdapter;

public class ActivoActivity extends Activity {

	private ListView lista;
	private NormalListViewAdapter adaptadorLista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_lista);

		lista = (ListView) findViewById(R.id.lista);
		adaptadorLista = new NormalListViewAdapter(this);
		lista.setAdapter(adaptadorLista);
//		lista.setOnItemClickListener(this);

		int id = getIntent().getIntExtra("id", 0);

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://192.168.43.241:8080/II/activo/"
				+ id);
		httpGet.setHeader("content-type", "application/json");
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			String cadena = EntityUtils.toString(httpResponse.getEntity());
			JSONObject jsonObject = new JSONObject(cadena);

			String nombre = jsonObject.getString("nombre");
			String descripcion = jsonObject.getString("descripcion");
			String estado = jsonObject.getString("estado");
			String fechaAlta = jsonObject.getString("fecha_alta");
			String tipo = jsonObject.getString("tipo");

			adaptadorLista.adicionarItem(R.drawable.ic_activos, "Nombre",
					nombre);
			adaptadorLista.adicionarItem(R.drawable.ic_activos, "Descripción",
					descripcion);
			adaptadorLista.adicionarItem(R.drawable.ic_mouse_black, "Estado",
					estado);
			adaptadorLista.adicionarItem(R.drawable.ic_activos, "Fecha alta",
					fechaAlta);
			adaptadorLista.adicionarItem(R.drawable.ic_laptop_black, "Tipo",
					tipo);
		} catch (Exception ex) {
			Log.e("ii", "Error!", ex);
		}

	}
}
