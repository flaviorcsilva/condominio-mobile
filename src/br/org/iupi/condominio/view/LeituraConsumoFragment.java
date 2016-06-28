package br.org.iupi.condominio.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import br.org.iupi.condominio.model.TipoLeitura;

@SuppressLint("SimpleDateFormat")
public class LeituraConsumoFragment extends Fragment {

	private Leitura leitura;

	private View rootView;
	private LayoutInflater inflater;

	private ListView lstLeituras;
	private TextSwitcher txtSwitcherMesMedicao;

	private Calendar mesAno = Calendar.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

	private static List<Leitura> leituras = new ArrayList<Leitura>();

	static {
		leituras.add(new Leitura(1L, "211", TipoLeitura.AGUA_FRIA, 1546));
		leituras.add(new Leitura(2L, "211", TipoLeitura.AGUA_QUENTE, 356));
		leituras.add(new Leitura(3L, "211", TipoLeitura.GAS, 91));
		leituras.add(new Leitura(4L, "212", TipoLeitura.AGUA_FRIA, 546));
		leituras.add(new Leitura(5L, "212", TipoLeitura.AGUA_QUENTE, 1356));
		leituras.add(new Leitura(6L, "212", TipoLeitura.GAS, 9100));
		leituras.add(new Leitura(7L, "213", TipoLeitura.AGUA_FRIA, 1546));
		leituras.add(new Leitura(8L, "213", TipoLeitura.AGUA_QUENTE, 1356));
		leituras.add(new Leitura(9L, "213", TipoLeitura.GAS, 9100));
		leituras.add(new Leitura(10L, "214", TipoLeitura.AGUA_FRIA, 1546));
		leituras.add(new Leitura(11L, "214", TipoLeitura.AGUA_QUENTE, 1356));
		leituras.add(new Leitura(12L, "214", TipoLeitura.GAS, 910));
	}

	public LeituraConsumoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.leitura_consumo_fragment, container, false);

		this.inflater = inflater;
		lstLeituras = criaListViewLeituras();

		criaButtonMedicaoMesAnterior();
		criaButtonMedicaoMesProximo();
		txtSwitcherMesMedicao = criaTextSwitcherMesMedicao();
		criaButtonAdicionaMedicao();

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		populaListaDeLeituras();
	}

	private void populaListaDeLeituras() {
		ItemListaLeituraAdapter adapter = new ItemListaLeituraAdapter(inflater, leituras);

		lstLeituras.setAdapter(adapter);
	}

	private Button criaButtonMedicaoMesAnterior() {
		Button button = (Button) rootView.findViewById(R.id.btnLeituraMesAnterior);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				mesAno.add(Calendar.MONTH, -1);
				txtSwitcherMesMedicao.setText(sdf.format(mesAno.getTime()));
			}
		});

		return button;
	}

	private Button criaButtonMedicaoMesProximo() {
		Button button = (Button) rootView.findViewById(R.id.btnLeituraProximoMes);

		button.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				mesAno.add(Calendar.MONTH, 1);
				txtSwitcherMesMedicao.setText(sdf.format(mesAno.getTime()));
			}
		});

		return button;
	}

	private TextSwitcher criaTextSwitcherMesMedicao() {
		TextSwitcher textSwitcher = (TextSwitcher) rootView.findViewById(R.id.txtSwitcherMesLeitura);
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

	private ImageButton criaButtonAdicionaMedicao() {
		ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.btnAdicionaLeitura);

		imageButton.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {

			}
		});

		return imageButton;
	}

	private ListView criaListViewLeituras() {
		ListView listView = (ListView) rootView.findViewById(R.id.lstLeituras);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				leitura = (Leitura) adapter.getItemAtPosition(position);

				if (leitura != null) {

				}
			}
		});

		return listView;
	}
}