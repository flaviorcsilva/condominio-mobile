package br.org.iupi.condominio.view;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.NumberPicker;
import android.widget.ToggleButton;
import br.org.iupi.condominio.R;
import br.org.iupi.condominio.model.AcaoTagNFC;
import br.org.iupi.condominio.model.Alarme;
import br.org.iupi.condominio.service.AlarmeService;
import br.org.iupi.condominio.timer.AlertaRondaReceiver;

public class AdicionaAlarmeFragment extends Fragment {

	private View rootView;
	private NumberPicker nmbPickerHora;
	private NumberPicker nmbPickerMinuto;
	private ToggleButton tgbSegunda;
	private ToggleButton tgbTerca;
	private ToggleButton tgbQuarta;
	private ToggleButton tgbQuinta;
	private ToggleButton tgbSexta;
	private ToggleButton tgbSabado;
	private ToggleButton tgbDomingo;
	private Button btnCancelar;
	private Button btnSalvar;
	private AlarmManager alarmManager;
	private Intent intent;
	private PendingIntent pendingIntent;
	private Context context;

	private final AcaoTagNFC acao;
	private final int requestCode = 115;
	private Set<Integer> diasDaSemana = null;
	private AlarmeService alarmeService = null;

	public AdicionaAlarmeFragment(AcaoTagNFC acao) {
		this.acao = acao;
		diasDaSemana = new HashSet<Integer>();
		alarmeService = AlarmeService.get();
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.adiciona_alarme_fragment,
				container, false);

		criaNumberPickerHora();
		criaNumberPickerMinuto();
		criaToggleButtonDiasDaSemana();
		criaButtonCancelar();
		criaButtonSalvar();

		context = rootView.getContext();

		intent = new Intent("ALERTA_RONDA");
		intent.putExtra("id", requestCode);
		pendingIntent = PendingIntent.getBroadcast(context, requestCode,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// Get the AlarmManager service
		alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		return rootView;
	}

	/**
	 * Cria um alarme repetitivo para o dia da semana na hora e minuto.
	 * 
	 * @param diaDaSemana
	 *            - Domingo é 0 e assim sucessivamente
	 * @param hora
	 * @param minuto
	 */
	public void criaAlarmeParaODiaDaSemana(int diaDaSemana, int hora, int minuto) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_WEEK, diaDaSemana);
		calendar.set(Calendar.HOUR, hora);
		calendar.set(Calendar.MINUTE, minuto);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		alarmManager.cancel(pendingIntent);

		long interval = calendar.getTimeInMillis() + 604800000L;
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), interval, pendingIntent);
	}

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int diaDaSemana = getDiaDaSemanaDoToggleButton(buttonView);

			if (isChecked) {
				diasDaSemana.add(diaDaSemana);

				buttonView.setBackgroundColor(getResources().getColor(
						R.color.azul_medio_escuro));
			} else {
				diasDaSemana.remove(diaDaSemana);

				buttonView.setBackgroundColor(getResources().getColor(
						R.color.azul_medio_claro));
			}
		}
	};

	/**
	 * Obtém o dia da semana conforme o valor mapeado na tag do toggle button no
	 * XML, onde: 0- Dom, 1- Seg, 2- Ter, 3- Quar, 4- Qui, 5- Sex e 6- Sab.
	 * 
	 * @param toggleButton
	 * @return
	 */
	private int getDiaDaSemanaDoToggleButton(CompoundButton toggleButon) {
		String diaDaSemana = (String) toggleButon.getTag();

		return Integer.parseInt(diaDaSemana);
	}

	private void criaButtonCancelar() {
		btnCancelar = (Button) rootView
				.findViewById(R.id.btnCancelarAdicionaAlarme);
		btnCancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getFragmentManager().popBackStack();

				Button btnAdicionarAgendaAlarme = (Button) getActivity()
						.findViewById(R.id.btnAdicionarAgendaAlarme);
				btnAdicionarAgendaAlarme.setEnabled(true);
			}
		});
	}

	private void criaButtonSalvar() {
		btnSalvar = (Button) rootView
				.findViewById(R.id.btnSalvarAdicionaAlarme);
		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int hora = nmbPickerHora.getValue();
				int minuto = nmbPickerMinuto.getValue();

				for (Integer diaDaSemana : diasDaSemana) {
					criaAlarmeParaODiaDaSemana(diaDaSemana, hora, minuto);
				}

				Alarme alarme = new Alarme(acao, hora, minuto, diasDaSemana);
				alarmeService.insere(alarme);

				getActivity().getFragmentManager().popBackStack();

				Button btnAdicionarAgendaAlarme = (Button) getActivity()
						.findViewById(R.id.btnAdicionarAgendaAlarme);
				btnAdicionarAgendaAlarme.setEnabled(true);
			}
		});
	}

	private void criaToggleButtonDiasDaSemana() {
		tgbSegunda = (ToggleButton) rootView
				.findViewById(R.id.tgbSegundaAdicionaAlarme);
		tgbSegunda.setOnCheckedChangeListener(onCheckedChangeListener);

		tgbTerca = (ToggleButton) rootView
				.findViewById(R.id.tgbTercaAdicionaAlarme);
		tgbTerca.setOnCheckedChangeListener(onCheckedChangeListener);

		tgbQuarta = (ToggleButton) rootView
				.findViewById(R.id.tgbQuartaAdicionaAlarme);
		tgbQuarta.setOnCheckedChangeListener(onCheckedChangeListener);

		tgbQuinta = (ToggleButton) rootView
				.findViewById(R.id.tgbQuintaAdicionaAlarme);
		tgbQuinta.setOnCheckedChangeListener(onCheckedChangeListener);

		tgbSexta = (ToggleButton) rootView
				.findViewById(R.id.tgbSextaAdicionaAlarme);
		tgbSexta.setOnCheckedChangeListener(onCheckedChangeListener);

		tgbSabado = (ToggleButton) rootView
				.findViewById(R.id.tgbSabadoAdicionaAlarme);
		tgbSabado.setOnCheckedChangeListener(onCheckedChangeListener);

		tgbDomingo = (ToggleButton) rootView
				.findViewById(R.id.tgbDomingoAdicionaAlarme);
		tgbDomingo.setOnCheckedChangeListener(onCheckedChangeListener);
	}

	@SuppressLint("DefaultLocale")
	private void criaNumberPickerHora() {
		nmbPickerHora = (NumberPicker) rootView
				.findViewById(R.id.nmbPickerHoraAdicionaAlarme);
		nmbPickerHora.setMinValue(00);
		nmbPickerHora.setMaxValue(23);
		nmbPickerHora.setFormatter(new NumberPicker.Formatter() {
			@Override
			public String format(int i) {
				return String.format("%02d", i);
			}
		});
	}

	@SuppressLint("DefaultLocale")
	private void criaNumberPickerMinuto() {
		nmbPickerMinuto = (NumberPicker) rootView
				.findViewById(R.id.nmbPickerMinutoAdicionaAlarme);
		nmbPickerMinuto.setMinValue(00);
		nmbPickerMinuto.setMaxValue(59);
		nmbPickerMinuto.setFormatter(new NumberPicker.Formatter() {
			@Override
			public String format(int i) {
				return String.format("%02d", i);
			}
		});
	}

	private void init(Context context) {
		Intent intent = new Intent(context, AlertaRondaReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Get the AlarmManager service
		alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
	}

	protected void createAlarmControler(Context context) {
		init(context);
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.DAY_OF_MONTH, 11);
		cal.set(Calendar.HOUR_OF_DAY, 2);
		cal.set(Calendar.MINUTE, 5);
		cal.set(Calendar.SECOND, 0);

		Date now = new Date(System.currentTimeMillis());
		if (cal.getTime().before(now)) {
			cal.add(Calendar.MONTH, 1);
		}

		long firstTime = cal.getTime().getTime();

		alarmManager.cancel(pendingIntent);
		Log.e("", "firstTime: " + firstTime);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, firstTime, 1000 * 60
				* 60 * 24 * 30, pendingIntent);
	}

	protected void cancelAlarmControler(Context context) {
		init(context);
		alarmManager.cancel(pendingIntent);
	}
}