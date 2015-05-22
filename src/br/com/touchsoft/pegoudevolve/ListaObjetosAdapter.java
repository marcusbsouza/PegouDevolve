package br.com.touchsoft.pegoudevolve;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.touchsoft.pegoudevolve.model.Objeto;

public class ListaObjetosAdapter extends BaseAdapter {

	private ArrayList<Objeto> listaObjetos;

	public ListaObjetosAdapter(ArrayList<Objeto> listaObjetos) {
		this.listaObjetos = listaObjetos;
	}
	
	@Override
	public int getCount() {
		return listaObjetos.size();
	}

	@Override
	public Object getItem(int position) {
		return listaObjetos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return listaObjetos.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Objeto objeto = listaObjetos.get(position);
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			convertView = inflater.inflate(R.layout.activity_lista_item, parent, false);
		}
		//TODO: Acrescentar as datas
		TextView itemViewDescricao = (TextView) convertView.findViewById(R.id.item_view_descricao);
		TextView itemViewQuem      = (TextView) convertView.findViewById(R.id.item_view_quem);
		itemViewDescricao.setText(objeto.getDescricao());
		itemViewQuem.setText(objeto.getQuem());
		
		return convertView;
	}

}
