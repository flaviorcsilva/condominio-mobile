package br.org.iupi.condominio.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.org.iupi.condominio.model.Leitura;
import br.org.iupi.condominio.model.TipoLeitura;

public class LeituraDAO extends SQLiteOpenHelper {

	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

	public LeituraDAO(Context context) {
		super(context, "condominio", null, 8);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		StringBuilder ddl = new StringBuilder();

		ddl.append("CREATE TABLE leitura (id INTEGER PRIMARY KEY AUTOINCREMENT, unidade TEXT NOT NULL, ");
		ddl.append("tipo INTEGER NOT NULL, medido INTEGER NOT NULL, foto BLOB, data DATE DEFAULT CURRENT_DATE);");

		database.execSQL(ddl.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		StringBuilder ddl = new StringBuilder();

		switch (oldVersion) {
		case 7:
			ddl.append("DROP TABLE IF EXISTS leitura");
			database.execSQL(ddl.toString());

			onCreate(database);
		}
	}

	public List<Leitura> consultaTodos() {
		SQLiteDatabase database = getWritableDatabase();

		String sql = "SELECT id, data, unidade, tipo, medido, foto FROM leitura ORDER BY unidade, tipo";
		Cursor cursor = database.rawQuery(sql, null);

		List<Leitura> leituras = new ArrayList<Leitura>();
		Leitura leitura = null;

		while (cursor.moveToNext()) {
			leitura = new Leitura();
			leitura.setId(cursor.getLong(cursor.getColumnIndex("id")));

			try {
				leitura.setData(formatter.parse(cursor.getString(cursor.getColumnIndex("data"))));
			} catch (ParseException e) {
				leitura.setData(null);
				e.printStackTrace();
			}

			leitura.setUnidade(cursor.getString(cursor.getColumnIndex("unidade")));

			int tipo = cursor.getInt(cursor.getColumnIndex("tipo"));
			leitura.setTipo(TipoLeitura.get(tipo));

			leitura.setMedido(cursor.getInt(cursor.getColumnIndex("medido")));
			leitura.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));

			leituras.add(leitura);
		}

		cursor.close();

		return leituras;
	}

	public List<Leitura> consultaPorPeriodo(Date inicioMes, Date finalMes) {
		SQLiteDatabase database = getWritableDatabase();

		String sql = "SELECT id, data, unidade, tipo, medido, foto FROM leitura WHERE data BETWEEN ? AND ? ORDER BY unidade, tipo";
		
		String[] whereArgs = {formatter.format(inicioMes), formatter.format(finalMes)};
		Cursor cursor = database.rawQuery(sql, whereArgs);
		
		List<Leitura> leituras = new ArrayList<Leitura>();
		Leitura leitura = null;

		while (cursor.moveToNext()) {
			leitura = new Leitura();
			leitura.setId(cursor.getLong(cursor.getColumnIndex("id")));

			try {
				leitura.setData(formatter.parse(cursor.getString(cursor.getColumnIndex("data"))));
			} catch (ParseException e) {
				leitura.setData(null);
				e.printStackTrace();
			}

			leitura.setUnidade(cursor.getString(cursor.getColumnIndex("unidade")));

			int tipo = cursor.getInt(cursor.getColumnIndex("tipo"));
			leitura.setTipo(TipoLeitura.get(tipo));

			leitura.setMedido(cursor.getInt(cursor.getColumnIndex("medido")));
			leitura.setFoto(cursor.getBlob(cursor.getColumnIndex("foto")));

			leituras.add(leitura);
		}

		cursor.close();

		return leituras;
	}

	public void insere(Leitura leitura) {
		ContentValues values = getContentValues(leitura);

		SQLiteDatabase database = getWritableDatabase();
		database.insert("leitura", null, values);
	}

	public void atualiza(Leitura leitura) {
		ContentValues values = getContentValues(leitura);
		String[] params = { leitura.getId().toString() };

		SQLiteDatabase database = getWritableDatabase();
		database.update("leitura", values, "id = ?", params);
	}

	public void removeTodos() {
		String sql = "DELETE FROM leitura";

		SQLiteDatabase database = getWritableDatabase();
		database.execSQL(sql);
	}

	private ContentValues getContentValues(Leitura leitura) {
		ContentValues values = new ContentValues();

		values.put("unidade", leitura.getUnidade());
		values.put("tipo", leitura.getTipo().getChave());
		values.put("medido", leitura.geMedido());
		values.put("foto", leitura.getFoto());
		values.put("data", formatter.format(leitura.getData()));

		return values;
	}
}
