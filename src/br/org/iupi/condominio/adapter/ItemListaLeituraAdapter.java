package br.org.iupi.condominio.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.model.Leitura;

public class ItemListaLeituraAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Leitura> leituras;

	public ItemListaLeituraAdapter(LayoutInflater inflater, List<Leitura> leituras) {
		this.inflater = inflater;
		this.leituras = leituras;
	}

	@Override
	public int getCount() {
		return leituras.size();
	}

	@Override
	public Object getItem(int position) {
		return leituras.get(position);
	}

	@Override
	public long getItemId(int position) {
		return leituras.get(position).getId();
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Leitura leitura = leituras.get(position);

		View linha = inflater.inflate(R.layout.item_lista_leitura, null);

		TextView txvUnidade = (TextView) linha.findViewById(R.id.txvItemListaLeituraUnidade);
		txvUnidade.setText(leitura.getUnidade());

		TextView txvTipoMedicao = (TextView) linha.findViewById(R.id.txvItemListaLeituraTipoMedicao);
		txvTipoMedicao.setText(leitura.getTipo().getValor());

		TextView txvMedido = (TextView) linha.findViewById(R.id.txvItemListaLeituraMedido);
		txvMedido.setText(leitura.geMedido().toString());

		return linha;
	}
}