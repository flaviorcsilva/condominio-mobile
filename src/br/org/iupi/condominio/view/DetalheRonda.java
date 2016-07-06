package br.org.iupi.condominio.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.adapter.ItemListaLocaisRondaAdapter;
import br.org.iupi.condominio.helper.DateHelper;
import br.org.iupi.condominio.model.LocalRonda;
import br.org.iupi.condominio.model.Ronda;

public class DetalheRonda extends Activity {

	private Ronda ronda;
	private LocalRonda localRonda;
	private ListView lstLocaisRonda;
	private View header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhe_ronda);

		Intent intent = getIntent();
		ronda = (Ronda) intent.getSerializableExtra(PutExtra.RONDA.getChave());

		StringBuilder titulo = new StringBuilder("Ronda - ");
		if (ronda != null) {
			titulo.append("Data: " + DateHelper.formatDate(ronda.getData()));
		} else {
			// TODO: Finalizar a activity;
		}

		configuraActionBar(titulo.toString());

		lstLocaisRonda = criaListViewLocaisRonda();

		TextView txvHorario = (TextView) header.findViewById(R.id.txvHeaderHorarioProgramado);
		txvHorario.setText("Horário Programado: " + DateHelper.formatTime(ronda.getHorario()));
	}

	@Override
	public void onResume() {
		super.onResume();

		populaListaDeLocaisRonda();
	}

	@SuppressLint("InflateParams")
	private ListView criaListViewLocaisRonda() {
		ListView lstLocais = (ListView) findViewById(R.id.lstLocaisRonda);

		header = getLayoutInflater().inflate(R.layout.header_item_lista_local_ronda, null);
		lstLocais.addHeaderView(header);

		lstLocais.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				localRonda = (LocalRonda) adapter.getItemAtPosition(position);

				if (localRonda != null) {

				}
			}
		});

		return lstLocais;
	}

	private void populaListaDeLocaisRonda() {
		ItemListaLocaisRondaAdapter adapter = new ItemListaLocaisRondaAdapter(this, ronda.getLocais());

		lstLocaisRonda.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void configuraActionBar(String titulo) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.azul_escuro)));
		getActionBar().setIcon(R.drawable.ic_ronda_actionbar);
		getActionBar().setTitle(titulo);
	}
}
