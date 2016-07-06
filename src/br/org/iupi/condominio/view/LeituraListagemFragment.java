package br.org.iupi.condominio.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.adapter.ItemListaLeituraAdapter;
import br.org.iupi.condominio.model.Leitura;
import br.org.iupi.condominio.service.LeituraService;

@SuppressLint("SimpleDateFormat")
public class LeituraListagemFragment extends Fragment {

	private View rootView;
	private LayoutInflater inflater;

	private ListView lstLeituras;
	private TextView txvTotalLeituras;
	private TextSwitcher txsMesMedicao;

	private Calendar mesAno = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

	private Leitura leitura;
	private LeituraService service;

	public LeituraListagemFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.leitura_listagem_fragment, container, false);

		mesAno = Calendar.getInstance();
		service = new LeituraService(rootView.getContext());

		this.inflater = inflater;
		lstLeituras = criaListViewLeituras();

		criaButtonMedicaoMesAnterior();
		criaButtonMedicaoMesProximo();
		txvTotalLeituras = (TextView) rootView.findViewById(R.id.leitura_listagem_txvTotalLeituras);
		txsMesMedicao = criaTextSwitcherMesMedicao();
		criaButtonAdicionaLeitura();

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		populaListaDeLeituras();
	}

	private void populaListaDeLeituras() {
		List<Leitura> leituras = service.consultaLeiturasDoMesAno(mesAno.get(Calendar.MONTH),
				mesAno.get(Calendar.YEAR));

		ItemListaLeituraAdapter adapter = new ItemListaLeituraAdapter(inflater, leituras);
		lstLeituras.setAdapter(adapter);
		
		atualizaMesAnoETotalLeituras();
	}

	private Button criaButtonMedicaoMesAnterior() {
		Button button = (Button) rootView.findViewById(R.id.leitura_listagem_btnLeituraMesAnterior);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				mesAno.add(Calendar.MONTH, -1);
				populaListaDeLeituras();;
			}
		});

		return button;
	}

	private Button criaButtonMedicaoMesProximo() {
		Button button = (Button) rootView.findViewById(R.id.leitura_listagem_btnLeituraProximoMes);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				mesAno.add(Calendar.MONTH, 1);
				populaListaDeLeituras();
			}
		});

		return button;
	}

	private void atualizaMesAnoETotalLeituras() {
		txsMesMedicao.setText(sdf.format(mesAno.getTime()));
		txvTotalLeituras.setText(lstLeituras.getCount() + " leituras");
	}

	private TextSwitcher criaTextSwitcherMesMedicao() {
		TextSwitcher textSwitcher = (TextSwitcher) rootView.findViewById(R.id.leitura_listagem_txsMesLeitura);
		textSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView textView = new TextView(rootView.getContext());
				textView.setGravity(Gravity.CENTER);
				textView.setTextColor(getResources().getColor(R.color.azul_escuro));
				textView.setTextSize(23);
				textView.setText(sdf.format(mesAno.getTime()));

				return textView;
			}
		});

		return textSwitcher;
	}

	private ImageButton criaButtonAdicionaLeitura() {
		ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.leitura_listagem_btnAdicionaLeitura);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(rootView.getContext(), LeituraFormularioActivity.class);
				startActivity(intent);
			}
		});

		return imageButton;
	}

	private ListView criaListViewLeituras() {
		ListView listView = (ListView) rootView.findViewById(R.id.leitura_listagem_lstLeituras);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				leitura = (Leitura) adapter.getItemAtPosition(position);

				if (leitura != null) {
					Intent intent = new Intent(rootView.getContext(), LeituraFormularioActivity.class);
					intent.putExtra(PutExtra.LEITURA.getChave(), leitura);

					startActivity(intent);
				}
			}
		});

		return listView;
	}
}