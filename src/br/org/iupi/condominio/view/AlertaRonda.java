package br.org.iupi.condominio.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.org.iupi.condominio.R;

public class AlertaRonda extends Activity {

	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alerta_ronda);

		configuraActionBar();

		criaButtonRealizarRonda();

		mediaPlayer = MediaPlayer.create(AlertaRonda.this, R.raw.beep);
		mediaPlayer.setLooping(true);
		mediaPlayer.start();
	}

	@Override
	protected void onPause() {
		mediaPlayer.stop();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mediaPlayer.release();
		super.onDestroy();
	}

	private void criaButtonRealizarRonda() {
		Button btnRealizarRonda = (Button) findViewById(R.id.btnRealizarRondaAlertaRonda);
		btnRealizarRonda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AlertaRonda.this, PrincipalActivity.class);
				startActivity(intent);
			}
		});
	}

	private void configuraActionBar() {
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#4682b4")));
	}
}
