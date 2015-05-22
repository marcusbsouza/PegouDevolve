package br.com.touchsoft.pegoudevolve;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.com.touchsoft.pegoudevolve.db.ObjetoDataSource;
import br.com.touchsoft.pegoudevolve.model.Objeto;
import br.com.touchsoft.pegoudevolve.ui.DatePickerFragment;
import br.com.touchsoft.pegoudevolve.util.Helper;

@SuppressLint("NewApi")
public class ActivityEfetuarAcao extends Activity implements OnClickListener {

	int idAcao;
	//Views que compoẽm essa Activity
	private Button buttonSalvar;
	private Button buttonCancelar;
	private TextView textViewDescricao;
	private EditText editTextViewDescricao;
	private TextView textViewQuem;
	private EditText editTextViewQuem;
	private TextView datePickerDataAcao;
	private TextView datePickerDataFim;

	private ObjetoDataSource objetoDS = null;
	
	private Objeto objetoEdicao = null; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_efetuar_acao);
		
		idAcao = Helper.getIdAcao(this);
		
		recuperarViews();
		
		switch (idAcao) {
			case R.id.btn_emprestar:
				setLabelsEmprestar();
				break;
			case R.id.btn_pegaremprestado:
				setLabelsPegarEmprestado();
				break;
			default:
				break;
		}

		buttonSalvar.setOnClickListener(this);
		buttonCancelar.setOnClickListener(this);
		objetoDS = new ObjetoDataSource(this);
		
		//Se idObjeto = 0, novo se não é edição de existente
		long idObjeto = Helper.getIdObjeto(this);
		if (idObjeto > 0) {
			objetoEdicao = objetoDS.recuperarObjetoById(idObjeto);
			carregarDadosViewObjeto(objetoEdicao);
		}
	}

	private void carregarDadosViewObjeto(Objeto objetoEdicao) {
		editTextViewDescricao.setText(objetoEdicao.getDescricao());
		editTextViewQuem.setText(objetoEdicao.getQuem());
	}

	private void recuperarViews() {
		buttonSalvar 			= (Button)findViewById(R.id.btn_salvar);
		buttonCancelar 			= (Button)findViewById(R.id.btn_cancelar);
		textViewDescricao 		= (TextView) findViewById(R.id.label_txt_descricao);
		editTextViewDescricao 	= (EditText) findViewById(R.id.txt_descricao);
		textViewQuem   			= (TextView) findViewById(R.id.label_txt_quem);
		editTextViewQuem 		= (EditText) findViewById(R.id.txt_quem);
		datePickerDataAcao 		= (TextView) findViewById(R.id.datePicker_data_acao);
		datePickerDataFim 		= (TextView) findViewById(R.id.datePicker_data_fim);
	}

	private void setLabelsEmprestar() {
		setTitle(R.string.title_activity_emprestar);
		textViewDescricao.setText(R.string.txt_obj_emprestado_label);
		textViewQuem.setText(R.string.txt_para_quem_emprestou_label);
		datePickerDataAcao.setText(R.string.datePicker_emprestimo_label);
		datePickerDataFim.setText(R.string.datePicker_recebimento_label);
	}

	private void setLabelsPegarEmprestado() {
		setTitle(R.string.title_activity_pegaremprestado);
		textViewDescricao.setText(R.string.txt_obj_recebido_label);
		textViewQuem.setText(R.string.txt_de_quem_pegou_label);
		datePickerDataAcao.setText(R.string.datePicker_pegou_label);
		datePickerDataFim.setText(R.string.datePicker_devolucao_label);
	}

	
	/**
	 * Caso o objetoEdicao seja null, significa que estou salvando um objeto novo.
	 * Caso contrário, estou realizando um update
	 */
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.btn_salvar:
				if (objetoEdicao == null) {
					Objeto novoObjeto = new Objeto();
					novoObjeto.setTipo(Helper.getTipo(idAcao));
					novoObjeto.setDescricao(editTextViewDescricao.getText().toString());
					novoObjeto.setQuem(editTextViewQuem.getText().toString());
					//novoObjeto.setDataInicial(dataInicial);

//					if (validarCamposObrigatorios(novoObjeto)) {
						objetoDS.inserirNovoObjeto(novoObjeto);
						Helper.showMessage(this, R.string.toast_salvo_sucesso);
						finish();
//					}
				} else {
					objetoEdicao.setDescricao(editTextViewDescricao.getText().toString());
					objetoEdicao.setQuem(editTextViewQuem.getText().toString());

//					if (validarCamposObrigatorios(objetoEdicao)) {
						objetoDS.updateObjeto(objetoEdicao);
						Helper.showMessage(this, R.string.toast_salvo_sucesso);
						finish();
//					}
				}
				break;
			case R.id.btn_cancelar: 
				finish();
				break;
		}
	}

	/**
	 * Método para validar campos obrigatórios tanto na inclusão quanto na edição
	 * @param objeto
	 */
/*	private boolean validarCamposObrigatorios(Objeto objeto) {
		
		boolean valido = true;
		if(objeto.getDescricao() != null && objeto.getDescricao().isEmpty()) {
			valido = false;
			editTextViewDescricao.requestFocus();
			//editTextViewDescricao.setError(getText(R.string.validacao_campo_obrigatorio), null);
		} else {
			editTextViewDescricao.setError(null);
		}
		if(objeto.getQuem() != null && objeto.getQuem().isEmpty()) {
			valido = false;
			editTextViewQuem.requestFocus();
			editTextViewQuem.setError(getText(R.string.validacao_campo_obrigatorio), null);
		} else {
			editTextViewQuem.setError(null);
		}
		
		return valido;
	}
*/
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	@Override
	protected void onResume() {
		objetoDS.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		objetoDS.close();
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		objetoDS.close();
		super.onDestroy();
	}
}
