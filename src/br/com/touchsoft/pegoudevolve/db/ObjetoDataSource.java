package br.com.touchsoft.pegoudevolve.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.touchsoft.pegoudevolve.model.Objeto;

public class ObjetoDataSource {
	
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    
    private String[] todasColunas = {DatabaseHelper.COLUMN_ID
    			, DatabaseHelper.COLUMN_TIPO
    			, DatabaseHelper.COLUMN_DESCRICAO
    			, DatabaseHelper.COLUMN_QUEM
    			, DatabaseHelper.COLUMN_DATAINICIAL
    			, DatabaseHelper.COLUMN_DATAFINAL};
    
	//Construtor da classe criando o Helper
	public ObjetoDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	//Abrindo o banco
	//TODO: Isso deveria ficar aqui mesmo? E não na Helper?
	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	//Fechando o banco
	//TODO: Isso deveria ficar aqui mesmo? E não na Helper?
	public void close() {
		dbHelper.close();
	}
	
	//Inserir um novo objeto (emprestar ou pegar emprestado)
	public long inserirNovoObjeto(Objeto objeto) {
		ContentValues values = putValues(objeto);
		long insertId = db.insert(DatabaseHelper.TABLE_OBJETO, null, values);
		return insertId; 
	}

	public void updateObjeto(Objeto objeto) {
		ContentValues values = putValues(objeto);
		db.update(DatabaseHelper.TABLE_OBJETO, values, DatabaseHelper.COLUMN_ID + " = " + objeto.getId(), null);
	}

	private ContentValues putValues(Objeto objeto) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TIPO, objeto.getTipo());
		values.put(DatabaseHelper.COLUMN_DESCRICAO, objeto.getDescricao());
		values.put(DatabaseHelper.COLUMN_QUEM, objeto.getQuem());
		values.put(DatabaseHelper.COLUMN_DATAINICIAL, objeto.getDataInicialStr());
		values.put(DatabaseHelper.COLUMN_DATAFINAL, objeto.getDataFinalStr());
		return values;
	}
	
	//Deletar um objeto existente 
	public void deletarObjeto(long id) {
		db.delete(DatabaseHelper.TABLE_OBJETO, DatabaseHelper.COLUMN_ID + " = " + id, null);
	}

	//Recupera um objeto pelo Id
	public Objeto recuperarObjetoById(long id) {
		Objeto objeto = null;
		Cursor cursor = dbHelper.getReadableDatabase().
				  rawQuery("select * from " + DatabaseHelper.TABLE_OBJETO + " where _id = ?", new String[] { Long.toString(id) }); 
		if(cursor.moveToFirst()) {
			objeto = cursorToObjeto(cursor);	
		}
		
		cursor.close();
		return objeto;
	}
	
	//Recuperar todos objetos
	//TODO: colocar a cláusula where para tipo de objeto
	public ArrayList<Objeto> getAllObjetosByTipo(int tipo) {
		ArrayList<Objeto> objetos = new ArrayList<Objeto>();
		Cursor cursor = db.query(DatabaseHelper.TABLE_OBJETO, todasColunas
				, DatabaseHelper.COLUMN_TIPO + " = " + tipo, 
				null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Objeto objeto = cursorToObjeto(cursor);
			objetos.add(objeto);
			cursor.moveToNext();
		}
		
		cursor.close();
		return objetos;
	}
	
	private Objeto cursorToObjeto(Cursor cursor) {
		Objeto objeto = new Objeto();
		objeto.setId(cursor.getLong(0));
		objeto.setTipo(cursor.getInt(1));
		objeto.setDescricao(cursor.getString(2));
		objeto.setQuem(cursor.getString(3));
		//objeto.setDataInicial(cursor.getLong(4));
		//objeto.setDataFinal(cursor.getLong(5));
		return objeto;
	}
	
}
