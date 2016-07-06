package br.org.iupi.condominio.service;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.org.iupi.condominio.dao.LeituraDAO;
import br.org.iupi.condominio.helper.DateHelper;
import br.org.iupi.condominio.model.Leitura;

public class LeituraService {

	private LeituraDAO dao;

	public LeituraService(Context context) {
		dao = new LeituraDAO(context);
	}

	/**
	 * Insere uma nova <code>Leitura<code> no banco de dados.
	 * 
	 * @param leitura
	 *            Leitura a ser inserida.
	 * 
	 * @return Chave primária da leitura inserida.
	 */
	public void insereLeitura(Leitura leitura) {
		validaAntesDeInserir(leitura);
		
		dao.insere(leitura);
		dao.close();
	}

	/**
	 * Atualiza uma <code>Leitura<code> no banco de dados.
	 * 
	 * @param leitura
	 *            Leitura a ser atualizada.
	 */
	public void atualizaLeitura(Leitura leitura) {
		dao.atualiza(leitura);
		dao.close();
	}

	/**
	 * Consulta todas <code>Leitura<code> cadastradas.
	 * 
	 * @return Lista de <code>Leitura<code> cadastradas.
	 */
	public List<Leitura> consultaTodasLeituras() {
		List<Leitura> leituras = dao.consultaTodos();
		dao.close();

		if (leituras == null || leituras.isEmpty()) {
			Log.d("LeituraService", "Não há leituras cadastradas.");
		}

		return leituras;
	}
	
	public List<Leitura> consultaLeiturasDoMesAno(int mes, int ano) {		
		Date inicioMes = DateHelper.getStartDateOfMonth(mes, ano);
		Date finalMes = DateHelper.getEndDateOfMonth(mes, ano);
		
		return dao.consultaPorPeriodo(inicioMes, finalMes);
	}
	
	protected void validaAntesDeInserir(Leitura leitura) {
		/* Verifica se já existe leitura para o mes/ano, unidade e tipo */
		
		/* Verifica se a leitura atual é maior que a leitura anterior */
	}
}
