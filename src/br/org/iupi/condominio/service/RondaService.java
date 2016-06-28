package br.org.iupi.condominio.service;

import java.util.List;

import android.util.Log;
import br.org.iupi.condominio.dao.RondaDAO;
import br.org.iupi.condominio.model.Ronda;

public class RondaService {

	private static RondaService instancia = null;
	private RondaDAO dao = RondaDAO.get();

	private RondaService() {
		// Construtor privado
	}

	/**
	 * Obtém a instância de <code>RondaService<code>.
	 * 
	 * @return Instância de <code>RondaService<code>
	 */
	public static RondaService get() {
		if (instancia == null) {
			instancia = new RondaService();
		}

		return instancia;
	}

	/**
	 * Insere uma nova <code>Ronda<code> no banco de dados.
	 * 
	 * @param ronda
	 *            Ronda a ser inserida.
	 * 
	 * @return Chave primária da ronda inserida.
	 */
	public Long insere(Ronda ronda) {
		Long id = dao.consultaProximaPK();

		ronda.setId(id);

		dao.insere(ronda);

		return id;
	}

	/**
	 * Consulta todas <code>Ronda<code> cadastradas.
	 * 
	 * @return Lista de <code>Ronda<code> cadastradas.
	 */
	public List<Ronda> consultaTodas() {
		List<Ronda> rondas = dao.consultaTodos();

		if (rondas == null || rondas.isEmpty()) {
			Log.d("RondaService", "Não há rondas cadastradas.");
		}

		return rondas;
	}
}
