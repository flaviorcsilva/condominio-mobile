package br.org.iupi.condominio.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.model.Alarme;

public class ItemListaAlarmeAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Alarme> alarmes;

	public ItemListaAlarmeAdapter(LayoutInflater inflater, List<Alarme> alarmes) {
		this.inflater = inflater;
		this.alarmes = alarmes;
	}

	@Override
	public int getCount() {
		return alarmes.size();
	}

	@Override
	public Object getItem(int position) {
		return alarmes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return alarmes.get(position).getId();
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Alarme alarme = alarmes.get(position);

		View linha = inflater.inflate(R.layout.item_lista_alarme, null);

		TextView txvHorario = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeHorario);

		String horario = "Horário: " + alarme.getHoraFormatada() + ":"
				+ alarme.getMinutoFormatado();
		txvHorario.setText(horario);

		Drawable drawable = linha.getResources().getDrawable(
				R.drawable.shp_arredondado_bkg_pressed);

		TextView txvAlarmeSegunda = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeSegunda);

		if (alarme.getDiasDaSemana().contains(1)) {
			txvAlarmeSegunda.setBackground(drawable);
		}

		TextView txvAlarmeTerca = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeTerca);

		if (alarme.getDiasDaSemana().contains(2)) {
			txvAlarmeTerca.setBackground(drawable);
		}

		TextView txvAlarmeQuarta = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeQuarta);

		if (alarme.getDiasDaSemana().contains(3)) {
			txvAlarmeQuarta.setBackground(drawable);
		}

		TextView txvAlarmeQuinta = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeQuinta);

		if (alarme.getDiasDaSemana().contains(4)) {
			txvAlarmeQuinta.setBackground(drawable);
		}

		TextView txvAlarmeSexta = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeSexta);

		if (alarme.getDiasDaSemana().contains(5)) {
			txvAlarmeSexta.setBackground(drawable);
		}

		TextView txvAlarmeSabado = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeSabado);

		if (alarme.getDiasDaSemana().contains(6)) {
			txvAlarmeSabado.setBackground(drawable);
		}

		TextView txvAlarmeDomingo = (TextView) linha
				.findViewById(R.id.txvItemListaAlarmeDomingo);

		if (alarme.getDiasDaSemana().contains(0)) {
			txvAlarmeDomingo.setBackground(drawable);
		}

		return linha;
	}
}