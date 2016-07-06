package br.org.iupi.condominio.view;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.helper.DateHelper;
import br.org.iupi.condominio.model.Leitura;
import br.org.iupi.condominio.model.TipoLeitura;
import br.org.iupi.condominio.service.LeituraService;

public class LeituraFormularioActivity extends Activity {

	private final int RESULT_CODE_CAMERA = 500;
	private String diretorioFoto;

	private Leitura leitura;

	private LeituraFormularioHelper helper;
	private LeituraService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leitura_formulario_activity);

		helper = new LeituraFormularioHelper(this);
		service = new LeituraService(this);

		criaImageButtonFoto();
		criaSpinnerTipoLeitura();

		Intent intent = getIntent();
		leitura = (Leitura) intent.getSerializableExtra(PutExtra.LEITURA.getChave());
		if (leitura != null) {
			helper.populaFormulario(leitura);
		}

		StringBuilder titulo = new StringBuilder("Leitura - ");
		if (leitura != null) {
			if (leitura.getData() == null) {
				titulo.append("Data: " + DateHelper.formatDate(new Date()));
			}
		} else {
			titulo.append("Data: " + DateHelper.formatDate(leitura.getData()));
		}

		configuraActionBar(titulo.toString());
	}

	private ImageButton criaImageButtonFoto() {
		ImageButton button = (ImageButton) findViewById(R.id.leitura_formulario_btnFoto);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				capturaImagem();
			}
		});

		return button;
	}

	private void capturaImagem() {
		diretorioFoto = getExternalFilesDir(null) + "/medicao.jpg";
		File file = new File(diretorioFoto);

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

		startActivityForResult(intent, RESULT_CODE_CAMERA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RESULT_CODE_CAMERA) {
			if (resultCode == Activity.RESULT_OK) {
				helper.carregaFoto(diretorioFoto);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.leitura_formulario, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.leitura_formulario_mnu_salvar:
			Leitura leitura = helper.getLeitura();

			if (leitura.getId() == null) {
				service.insereLeitura(leitura);
			} else {
				service.atualizaLeitura(leitura);
			}

			finish();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private Spinner criaSpinnerTipoLeitura() {
		Spinner spinner = (Spinner) findViewById(R.id.leitura_formulario_spnTipoLeitura);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, TipoLeitura.getValores());
		adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
		spinner.setAdapter(adapter);

		return spinner;
	}

	private void configuraActionBar(String titulo) {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.azul_escuro)));
		getActionBar().setIcon(R.drawable.ic_leitura_branco_24);
		getActionBar().setTitle(titulo);
	}
}