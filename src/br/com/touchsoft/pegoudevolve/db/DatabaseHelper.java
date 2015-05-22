package br.com.touchsoft.pegoudevolve.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe Helper apenas para controlar a criação e atualização do banco de dados da aplicação.
 * @author marcus
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "db_pegoudevolve";
	private static final int    DB_VER  = 2;

    //Campos da tabela objeto
	public static final String TABLE_OBJETO 		= "objeto";
	public static final String COLUMN_ID 			= "_id";
	public static final String COLUMN_TIPO  		= "tipo";
	public static final String COLUMN_DESCRICAO  	= "descricao";
	public static final String COLUMN_QUEM  		= "quem";
	public static final String COLUMN_DATAINICIAL  	= "dataInicial";
	public static final String COLUMN_DATAFINAL  	= "dataFinal";

	//SQL Create DB
	private static final String DB_CREATE = new StringBuilder()
			.append("CREATE TABLE objeto ( ")
			.append(COLUMN_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, ")
			.append(COLUMN_TIPO 		+ " INTEGER NOT NULL, ")
			.append(COLUMN_DESCRICAO	+ " TEXT NOT NULL, ")
			.append(COLUMN_QUEM 		+ " TEXT NOT NULL, ")
			.append(COLUMN_DATAINICIAL 	+ " INTEGER NOT NULL DEFAULT (strftime('%s','now')), ")
			.append(COLUMN_DATAFINAL 	+ " INTEGER NULL);")
			.toString();

	//SQL DROP DB
	private static final String DB_UPDATE = new StringBuilder()
			.append("DROP TABLE IF EXISTS ")
			.append(TABLE_OBJETO)
			.toString();

	
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DB_UPDATE);
		onCreate(db);
	}

}
