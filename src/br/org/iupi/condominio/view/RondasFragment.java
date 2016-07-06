package br.org.iupi.condominio.view;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.adapter.ItemListaRondaAdapter;
import br.org.iupi.condominio.helper.DateHelper;
import br.org.iupi.condominio.model.Ronda;
import br.org.iupi.condominio.service.RondaService;

public class RondasFragment extends Fragment {

	private Ronda ronda;

	private View rootView;
	private ListView lstRondas;
	private LayoutInflater inflater;

	private RondaService rondaService;

	public RondasFragment() {
		rondaService = RondaService.get();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.rondas_fragment, container, false);

		this.inflater = inflater;
		lstRondas = criaListViewRondas();

		View header = inflater.inflate(R.layout.header_item_lista_ronda, null);
		TextView txvData = (TextView) header.findViewById(R.id.txvHeaderData);
		txvData.setText("Data: " + DateHelper.formatDate(new Date()));

		lstRondas.addHeaderView(header);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		populaListaDeRondas();
	}

	private void populaListaDeRondas() {
		ItemListaRondaAdapter adapter = new ItemListaRondaAdapter(inflater,
				rondaService.consultaTodas());

		lstRondas.setAdapter(adapter);
	}

	private ListView criaListViewRondas() {
		ListView listView = (ListView) rootView.findViewById(R.id.lstRondas);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				ronda = (Ronda) adapter.getItemAtPosition(position);

				if (ronda != null) {
					Intent intent = new Intent(rootView.getContext(),
							DetalheRonda.class);
					intent.putExtra(PutExtra.RONDA.getChave(), ronda);

					startActivity(intent);
				}
			}
		});

		return listView;
	}
}