package br.org.iupi.condominio.timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import br.org.iupi.condominio.view.AlertaRonda;

public class AlertaRondaReceiver extends BroadcastReceiver {

	private static final String TAG = "AlertaRondaReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent intentAlertaRonda = new Intent(context, AlertaRonda.class);
		intentAlertaRonda.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		context.startActivity(intentAlertaRonda);

		String msg = "Alerta de Ronda Disparado!";
		Log.i(TAG, msg);
	}
}
