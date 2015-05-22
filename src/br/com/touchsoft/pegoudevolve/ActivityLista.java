package br.com.touchsoft.pegoudevolve;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.touchsoft.pegoudevolve.db.ObjetoDataSource;
import br.com.touchsoft.pegoudevolve.model.Objeto;
import br.com.touchsoft.pegoudevolve.util.Helper;

@SuppressLint("NewApi")
public class ActivityLista extends Activity implements OnItemClickListener, OnItemLongClickListener {

	private ObjetoDataSource objetoDS;
	private int idAcao;
	private ArrayList<Objeto> objetos;
	private ListView listaObjetosView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
		
		//Carregar a Activity de acordo com a ação - Emprestar ou pegar emprestado
		idAcao = Helper.getIdAcao(this);

		//Carrega os labels do botão e título
		setLabels();
		
		//Criando DS
		objetoDS = new ObjetoDataSource(this);

		listaObjetosView = (ListView) findViewById(R.id.list_objetos);
		listaObjetosView.setOnItemClickListener(this);
		listaObjetosView.setOnItemLongClickListener(this);
	}

	private void recuperarLista() {
		if (objetos != null) objetos.clear();
		objetos = objetoDS.getAllObjetosByTipo(Helper.getTipo(idAcao));
		ListaObjetosAdapter listaObjetosAdapter = new ListaObjetosAdapter(objetos);
		listaObjetosView.setAdapter(listaObjetosAdapter);
	}

	private void setLabels() {
		Button button = (Button) findViewById(R.id.btn_novo);
		switch (idAcao) {
			case R.id.btn_emprestar:
				button.setText(R.string.btn_novo_emprestimo_label);
				setTitle(R.string.title_activity_lista_emprestou);
				break;
	
			case R.id.btn_pegaremprestado:
				button.setText(R.string.btn_novo_pegaremprestado_label);
				setTitle(R.string.title_activity_lista_pegouemprestado);
				break;
		}
	}

	@Override
	protected void onResume() {
		objetoDS.open();
		recuperarLista();
		super.onResume();
	}

	@Override
	protected void onPause() {
		objetoDS.close();
		super.onPause();
	}

	/**
	 * Incluir novo objeto  
	 */
	public void novoObjeto(View v) {
		Intent intent = new Intent(this, ActivityEfetuarAcao.class);
		intent.putExtra("idAcao", idAcao); //Propagando o idAcao
		startActivity(intent);
	}

	/**
	 * Editar objeto existente apenas clicando em cima dele
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(this, ActivityEfetuarAcao.class);
		intent.putExtra("idAcao", idAcao); //Propagando o idAcao
		intent.putExtra("idObjeto", id);
		startActivity(intent);
	}

	/**
	 * Deletar item da lista com alerta para deleção.
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, final long idOjeto) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(R.string.dialog_deletar_item)
					.setPositiveButton(R.string.dialog_sim,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									objetoDS.deletarObjeto(idOjeto);
									recuperarLista();
									Helper.showMessage(ActivityLista.this, R.string.toast_deletado_sucesso);
								}
							})
					.setNegativeButton(R.string.dialog_nao,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
		
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			return true;
	}
}
