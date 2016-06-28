package br.org.iupi.condominio.view;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.model.AcaoTagNFC;
import br.org.iupi.condominio.model.Alarme;
import br.org.iupi.condominio.service.AlarmeService;

public class AgendaAlarme extends Activity {

	private int position = 0;
	private ImageSwitcher imgSwitcher;
	private TextSwitcher txtSwitcher;
	private Button btnAdicionar;
	private String[] titles;
	private TypedArray icons;
	private AcaoTagNFC acao;
	private AlarmeService alarmeService;

	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agenda_alarme);

		alarmeService = AlarmeService.get();

		configuraActionBar();

		titles = getResources().getStringArray(R.array.lst_txt_nfc_acoes_tag);
		icons = getResources().obtainTypedArray(R.array.lst_img_nfc_acoes_tag);

		criaImageSwitcher();
		criaTextSwitcher();

		// icons.recycle();

		btnAdicionar = (Button) findViewById(R.id.btnAdicionarAgendaAlarme);

		onSwitch(null);

		if (savedInstanceState == null) {
			List<Alarme> alarmes = alarmeService.consultaPorAcao(AcaoTagNFC.MEDICAO_CONSUMO);

			replaceFragmentInView(new ListaAlarmesFragment(alarmes));
		}
	}

	public void onSwitch(View view) {
		btnAdicionar.setEnabled(true);
		getFragmentManager().popBackStack();

		acao = AcaoTagNFC.get(titles[position]);
		List<Alarme> alarmes = alarmeService.consultaPorAcao(acao);

		txtSwitcher.setText(titles[position]);
		imgSwitcher.setBackgroundResource(icons.getResourceId(position, -1));
		position = (position + 1) % titles.length;

		replaceFragmentInView(new ListaAlarmesFragment(alarmes));
	}

	public void onAdicionar(View view) {
		replaceFragmentInView(new AdicionaAlarmeFragment(acao));
		btnAdicionar.setEnabled(false);
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

	private void replaceFragmentInView(Fragment fragment) {
		getFragmentManager().beginTransaction().replace(R.id.container_agenda_alarme_fragment, fragment)
				.addToBackStack("listaDeAlarmesFragment").commit();
	}

	private void criaImageSwitcher() {
		imgSwitcher = (ImageSwitcher) findViewById(R.id.imgSwitcherAgendaAlarme);
		imgSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView imageView = new ImageView(AgendaAlarme.this);

				return imageView;
			}
		});

		imgSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
		imgSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
	}

	private void criaTextSwitcher() {
		txtSwitcher = (TextSwitcher) findViewById(R.id.txtSwitcherAgendaAlarme);
		txtSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				TextView textView = new TextView(AgendaAlarme.this);
				textView.setGravity(Gravity.CENTER);
				textView.setTextColor(getResources().getColor(R.color.azul_escuro));

				return textView;
			}
		});

		txtSwitcher.setInAnimation(this, android.R.anim.fade_in);
		txtSwitcher.setOutAnimation(this, android.R.anim.fade_out);
	}

	private void configuraActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682b4")));
	}
}