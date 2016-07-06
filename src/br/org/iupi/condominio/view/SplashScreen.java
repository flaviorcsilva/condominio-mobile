package br.org.iupi.condominio.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import br.org.iupi.condominio.R;

public class SplashScreen extends Activity implements Runnable {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		Handler handler = new Handler();
		handler.postDelayed(this, 3000);
	}

	@Override
	public void run() {
		startActivity(new Intent(this, PrincipalActivity.class));
		finish();
	}
}
