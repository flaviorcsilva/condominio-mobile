package br.org.iupi.condominio.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateHelper {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	public static String getDateFormated(Date date) {
		return dateFormat.format(date);
	}

	public static Date getDateFromString(String date) throws Exception {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new Exception("A data não é válida.");
		}
	}

	public static String getDateTimeFormated(Date date) {
		return dateTimeFormat.format(date);
	}

	public static Date getDateWithoutTime(Date date) {
		Calendar calendar;

		if (date == null) {
			throw new IllegalArgumentException("Data de referência não pode ser nula.");
		}

		calendar = getCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	public static Calendar getCalendar(Date date) {
		Calendar calendar;

		if (date == null) {
			throw new IllegalArgumentException("Data de referência não pode ser nula.");
		}

		calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar;
	}

	public static Date getStartDateOfMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);

		return calendar.getTime();
	}

	public static Date getEndDateOfMonth(int month, int year) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}
}
