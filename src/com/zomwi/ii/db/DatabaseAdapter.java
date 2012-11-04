package com.zomwi.ii.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zomwi.ii.clases.Activo;
import com.zomwi.ii.clases.Registro;

public class DatabaseAdapter {

	public static final String DB_NAME = "ii.sqlite";
	public static final int DB_VERSION = 1;

	// ***** ACTIVOS *****
	public static final String TABLA_ACTIVOS = "activos";
	public static final String ID_ACTIVO = "id_activo";
	public static final String NOMBRE = "nombre";
	public static final String DESCRIPCION = "descripcion";
	public static final String FECHA_INGRESO = "fecha_ingreso";
	public static final String ESTADO = "estado";
	// ***** REGISTRO *****
	public static final String TABLA_REGISTROS = "registros";
	public static final String ID_REGISTRO = "id_registro";
	public static final String FECHA = "fecha";
	// *****
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String CREAR_TABLA_ACTIVOS = "CREATE TABLE " + TABLA_ACTIVOS + " (" + ID_ACTIVO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NOMBRE + " TEXT NOT NULL, " + DESCRIPCION + " TEXT NOT NULL, " + FECHA_INGRESO + " TIMESTAMP, " + ESTADO
			+ " INTEGER NOT NULL);";

	private static final String CREAR_TABLA_REGISTROS = "CREATE TABLE " + TABLA_REGISTROS + " (" + ID_REGISTRO
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_ACTIVO + " INTEGER, " + FECHA + " TIMESTAMP);";

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREAR_TABLA_ACTIVOS);
			db.execSQL(CREAR_TABLA_REGISTROS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLA_ACTIVOS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLA_REGISTROS);
			onCreate(db);
		}
	}

	public DatabaseAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DatabaseAdapter abrir() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void cerrar() {
		mDbHelper.close();
	}

	public long adicionarActivo(Activo activo) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(NOMBRE, activo.getNombre());
		
		initialValues.put(FECHA_INGRESO, activo.getFechaIngreso().toString());
		initialValues.put(ESTADO, activo.getEstado());
		initialValues.put(DESCRIPCION, activo.getDescripcion());
		return mDb.insert(TABLA_ACTIVOS, null, initialValues);
		
	}

	public long adiccionarRegistro(Registro registro) {
		return 0;
	}

	public boolean eliminarActivo(int rowId) {
		return mDb.delete(TABLA_ACTIVOS, ID_ACTIVO + "=" + rowId, null) > 0;
	}

	public Cursor obtenerTodosActivos() {
		return mDb.query(TABLA_ACTIVOS, new String[] { ID_ACTIVO, NOMBRE, DESCRIPCION, FECHA_INGRESO, ESTADO },
				null, null, null, null, null);
	}

}
