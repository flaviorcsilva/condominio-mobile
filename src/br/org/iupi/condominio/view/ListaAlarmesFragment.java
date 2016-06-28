package br.org.iupi.condominio.view;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.adapter.ItemListaAlarmeAdapter;
import br.org.iupi.condominio.model.Alarme;

public class ListaAlarmesFragment extends Fragment {

	private Alarme alarme;

	private View rootView;
	private LayoutInflater inflater;
	private ListView lstAlarmes;

	private List<Alarme> alarmes;

	public ListaAlarmesFragment(List<Alarme> alarmes) {
		this.alarmes = alarmes;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.lista_alarmes_fragment, container,
				false);
		this.inflater = inflater;

		lstAlarmes = criaListViewAlarmes();

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		populaListaDeAlarmes();
	}

	private void populaListaDeAlarmes() {
		ItemListaAlarmeAdapter adapter = new ItemListaAlarmeAdapter(inflater,
				alarmes);

		lstAlarmes.setAdapter(adapter);
	}

	private ListView criaListViewAlarmes() {
		ListView lstAlarmes = (ListView) rootView.findViewById(R.id.lstAlarmes);

		lstAlarmes.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				alarme = (Alarme) adapter.getItemAtPosition(position);

				if (alarme != null) {
					// TODO
				}
			}
		});

		return lstAlarmes;
	}
}