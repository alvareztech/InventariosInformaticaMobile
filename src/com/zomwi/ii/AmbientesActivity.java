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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.zomwi.ii.adapters.NormalListViewAdapter;

public class AmbientesActivity extends SherlockActivity implements OnItemClickListener{
	
	private ListView lista;
	private NormalListViewAdapter adaptadorLista;
	private List<Integer> ids = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar_ForceOverflow);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ambientes);
		
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
		HttpGet httpGet = new HttpGet("http://192.168.43.241:8080/II/ambiente/listMobile");
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
				String descripcion = obj.getString("edificio");
				adaptadorLista.adicionarItem(R.drawable.ic_action_edificio, nombre, descripcion);
			}

		} catch (Exception ex) {
			Log.e("ii", "Error!", ex);
		}

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

}
