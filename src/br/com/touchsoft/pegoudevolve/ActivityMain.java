package br.com.touchsoft.pegoudevolve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import br.com.touchsoft.pegoudevolve.db.ObjetoDataSource;
//TODO: Lembrar de implementar o princípio "Não é minha culpa".
/* Tem certeza que quer apagar isso? ---> Isso foi deletado (opção undo) */
public class ActivityMain extends Activity {

	ObjetoDataSource objetoDS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Abre a conexão e cria a tabela caso não exista
		objetoDS = new ObjetoDataSource(this);
        objetoDS.open();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		objetoDS.close();
		super.onPause();
	}

	/**
	 * Método para carregar uma lista de forma genérica, independente se 
	 * a ação é Emprestar ou Pegar Emprestado. Isso será passado através
	 * do view.getId().
	 * 
	 * @param view
	 */
	public void carregaLista(View view) {
		Intent intent = new Intent(this, ActivityLista.class);
		intent.putExtra("idAcao", view.getId());
		startActivity(intent);
	}

    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.options_menu, menu);
    	return true;
    }

    //TODO: Colocar as opções de configurção no arquivo de preferências, ver Storage01
	public boolean onOptionsItemSelected(MenuItem item) {
    	return true;
	}
}
