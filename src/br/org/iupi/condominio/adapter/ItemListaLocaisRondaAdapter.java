package br.org.iupi.condominio.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.helper.DateHelper;
import br.org.iupi.condominio.model.LocalRonda;

public class ItemListaLocaisRondaAdapter extends BaseAdapter {

	private Activity context;
	private List<LocalRonda> locais;

	public ItemListaLocaisRondaAdapter(Activity context, List<LocalRonda> locais) {
		this.context = context;
		this.locais = locais;
	}

	@Override
	public int getCount() {
		return locais.size();
	}

	@Override
	public Object getItem(int position) {
		return locais.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LocalRonda local = locais.get(position);

		View linha = context.getLayoutInflater().inflate(
				R.layout.item_lista_local_ronda, null);

		ImageView imgEstado = (ImageView) linha
				.findViewById(R.id.imgItemListaLocalRondaEstado);
		TextView txvLocal = (TextView) linha
				.findViewById(R.id.txvItemListaLocalRonda);
		TextView txvMensagem = (TextView) linha
				.findViewById(R.id.txvItemListaLocalRondaMensagem);

		imgEstado.setImageDrawable(context.getResources().getDrawable(
				R.drawable.ic_confirm));
		txvLocal.setText(local.getLocal());
		txvMensagem.setText("Realizada às: "
				+ DateHelper.formatTime(local.getHorarioRealizado()));

		return linha;
	}
}
