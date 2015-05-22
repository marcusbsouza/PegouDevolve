package br.com.touchsoft.pegoudevolve.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import br.com.touchsoft.pegoudevolve.R;
import br.com.touchsoft.pegoudevolve.model.Objeto;


public class Helper {
	
	/**
	 * Método para recuperar a ação que está sendo feita, emprestar ou pegar emprestado.
	 * @param activity
	 * @return idAcao
	 */
	public static int getIdAcao(Activity activity) {
		Bundle extras = activity.getIntent().getExtras();
        return extras.getInt("idAcao");
	}

	/**
	 * Método para recuperar o idObjeto no caso de edição
	 * @param activity
	 * @return idObjeto
	 */
	public static long getIdObjeto(Activity activity) {
		Bundle extras = activity.getIntent().getExtras();
        return extras.getLong("idObjeto");
	}

	/* display a Toast with message text. */
    public static void showMessage(Context context, int resId) {
		Toast toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
		toast.show();    	
    }
    
    public static int getTipo(int idAcao) {
    	int tipo = 0;
		switch (idAcao) {
			case R.id.btn_emprestar:
				tipo = Objeto.TIPO_EMPRESTAR;
				break;
			case R.id.btn_pegaremprestado:
				tipo = Objeto.TIPO_PEGAR_EMPRESTADO;
				break;
		}
		return tipo;
    }
}
