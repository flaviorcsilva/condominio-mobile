package br.org.iupi.condominio.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.helper.NFCHelper;
import br.org.iupi.condominio.model.AcaoTagNFC;

public class GravaTagNFC extends Activity {

	private NFCHelper nfcHelper;
	private Spinner spnAcaoTag;
	private TextView txtTagNFC;
	private ImageView imgTagNFC;
	private Animation animation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grava_tag_nfc);

		nfcHelper = new NFCHelper(this);

		configuraActionBar();

		spnAcaoTag = criaComboAcaoTag();

		animation = criaAnimacao();

		txtTagNFC = (TextView) findViewById(R.id.txtTagNFC);
		txtTagNFC.setAnimation(animation);

		imgTagNFC = (ImageView) findViewById(R.id.imgTagNFC);
		imgTagNFC.setAnimation(animation);

		if (savedInstanceState == null) {
			replaceFragmentInView(new GravaTagRondasFragment());
		}
	}

	private void configuraActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682b4")));
	}

	private Spinner criaComboAcaoTag() {
		Spinner spinner = (Spinner) findViewById(R.id.spnAcaoTag);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				if (position == AcaoTagNFC.MEDICAO_CONSUMO.ordinal()) {
					replaceFragmentInView(new GravaTagLeituraConsumoFragment());
				} else if (position == AcaoTagNFC.RONDAS.ordinal()) {
					replaceFragmentInView(new GravaTagRondasFragment());
				} else if (position == AcaoTagNFC.VERIFICACAO_LUZES.ordinal()) {
					replaceFragmentInView(new GravaTagLeituraConsumoFragment());
				} else if (position == AcaoTagNFC.IRRIGACAO_GRAMA.ordinal()) {
					replaceFragmentInView(new GravaTagLeituraConsumoFragment());
				} else if (position == AcaoTagNFC.PLACAS_SOLARES.ordinal()) {
					replaceFragmentInView(new GravaTagLeituraConsumoFragment());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				//
			}
		});

		return spinner;
	}

	private Animation criaAnimacao() {
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500); // You can manage the time of the blink with
									// this parameter
		animation.setStartOffset(20);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setRepeatCount(Animation.INFINITE);

		return animation;
	}

	private void replaceFragmentInView(Fragment fragment) {
		getFragmentManager().beginTransaction().replace(R.id.grava_tag_container_fragment, fragment).commit();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		try {
			if (nfcHelper.isNFCIntent(intent)) {
				// NdefRecord mimeRecord =
				// NdefRecord.createMime("application/vnd." +
				// this.getPackageName(), objectToBytes(new MyClass()));

				NdefRecord appRecord = NdefRecord.createApplicationRecord(this.getPackageName());

				NdefRecord typeRecord = nfcHelper.createTextRecord(getTipoDaAcaoTag());

				NdefRecord contentRecord = nfcHelper.createTextRecord(getConteudoDaAcaoTag());

				NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] { appRecord, typeRecord, contentRecord });

				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

				boolean writeResult = nfcHelper.writeNdefMessage(tag, ndefMessage);

				if (writeResult) {
					Toast.makeText(this, R.string.msg_nfc_tag_gravada, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(this, R.string.msg_nfc_gravacao_tag_falhou, Toast.LENGTH_LONG).show();
				}

				finish();
			} else {
				Log.d("onNewIntent", "received intent isn't a NFC intent.");
			}

		} catch (Exception e) {
			Log.e("onNewIntent", e.getMessage());
		}

		super.onNewIntent(intent);
	}

	private String getTipoDaAcaoTag() {
		return spnAcaoTag.getSelectedItem().toString();
	}

	private String getConteudoDaAcaoTag() {
		switch (spnAcaoTag.getSelectedItemPosition()) {
		case 0:
			// Ronda
			Spinner spnLocalRonda = (Spinner) findViewById(R.id.spnLocalRonda);
			return spnLocalRonda.getSelectedItem().toString();

		case 1:
			// Medição de Gás
			Spinner spnLocalMedicaoGas = (Spinner) findViewById(R.id.spnLocalMedicaoGas);
			return spnLocalMedicaoGas.getSelectedItem().toString();

		default:
			return "";
		}
	}

	@Override
	protected void onResume() {
		txtTagNFC.getAnimation().start();
		imgTagNFC.getAnimation().start();

		if (nfcHelper.isNFCEnabledDevice()) {
			nfcHelper.enableForegroundDispatch();
		}

		super.onResume();
	}

	@Override
	protected void onPause() {
		txtTagNFC.getAnimation().cancel();
		imgTagNFC.getAnimation().cancel();

		if (nfcHelper.isNFCEnabledDevice()) {
			nfcHelper.disableForegroundDispatch();
		}

		super.onPause();
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
}
