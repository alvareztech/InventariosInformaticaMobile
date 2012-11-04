package com.zomwi.ii;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class CambiarEstadoActivity extends SherlockActivity {
	
	private String serie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar_ForceOverflow);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_estado);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show home icon

		serie = getIntent().getStringExtra("serie");

	}

	public void aceptar(View view) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://192.168.43.241:8080/II/cambioEstado/saveMobile");
			post.setHeader("content-type", "application/json");

			JSONObject dato = new JSONObject("{\"class\":\"ii.CambioEstado\",\"id\":174,\"actual\":{\"class\":\"EstadoActivo\",\"id\":127},\"anterior\":{\"class\":\"EstadoActivo\",\"id\":127},\"descripcion\":\"DASDASDFASDFASDF\",\"fechaHora\":\"2012-10-24T06:09:00\",\"item\":{\"class\":\"Activo\",\"id\":132},\"responsable\":{\"class\":\"Usuario\",\"id\":85}}");
//			dato.put("serie", serie);
//			dato.put("descripcionx", "Allgooooooooooooooooo");
			
			StringEntity entity = new StringEntity(dato.toString());
			post.setEntity(entity);

			HttpResponse resp = httpClient.execute(post);
			String respStr = EntityUtils.toString(resp.getEntity());

			if (respStr.equals("true")) {
				Toast.makeText(this, "Proceso exitoso.", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Proceso fallido.", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancelar(View view) {

	}

}
