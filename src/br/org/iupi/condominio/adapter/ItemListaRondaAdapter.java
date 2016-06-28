package br.org.iupi.condominio.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.helper.DateHelper;
import br.org.iupi.condominio.model.EstadoDaRonda;
import br.org.iupi.condominio.model.Ronda;

public class ItemListaRondaAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Ronda> rondas;

	public ItemListaRondaAdapter(LayoutInflater inflater, List<Ronda> rondas) {
		this.inflater = inflater;
		this.rondas = rondas;
	}

	@Override
	public int getCount() {
		return rondas.size();
	}

	@Override
	public Object getItem(int position) {
		return rondas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0; // rondas.get(position).getId();
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Ronda ronda = rondas.get(position);

		View linha = inflater.inflate(R.layout.item_lista_ronda, null);

		ImageView imgEstado = (ImageView) linha
				.findViewById(R.id.imgItemListaRondaEstado);
		TextView txvHorario = (TextView) linha
				.findViewById(R.id.txvItemListaRondaHorario);
		TextView txvMensagemEstado = (TextView) linha
				.findViewById(R.id.txvItemListaRondaMensagemEstado);

		if (ronda.getEstado().equals(EstadoDaRonda.REALIZADA)) {
			imgEstado.setImageDrawable(inflater.getContext().getResources()
					.getDrawable(R.drawable.ic_ronda_realizada));

			txvMensagemEstado.setText(R.string.msg_ronda_registrada);
		} else if (ronda.getEstado().equals(EstadoDaRonda.PENDENTE)) {
			imgEstado.setImageDrawable(inflater.getContext().getResources()
					.getDrawable(R.drawable.ic_ronda_pendente));

			txvMensagemEstado.setText(R.string.msg_ronda_pendente);
		} else {
			imgEstado.setImageDrawable(inflater.getContext().getResources()
					.getDrawable(R.drawable.ic_ronda_nao_realizada));

			txvMensagemEstado.setText(R.string.msg_ronda_nao_realizada);
		}

		String horario = "Horário: "
				+ DateHelper.formatTime(ronda.getHorario());
		txvHorario.setText(horario);

		return linha;
	}
}
