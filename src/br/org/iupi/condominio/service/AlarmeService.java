package br.org.iupi.condominio.service;

import java.util.List;

import android.util.Log;
import br.org.iupi.condominio.dao.AlarmeDAO;
import br.org.iupi.condominio.model.AcaoTagNFC;
import br.org.iupi.condominio.model.Alarme;

public class AlarmeService {

	private static AlarmeService instancia = null;
	private AlarmeDAO dao = AlarmeDAO.get();

	private AlarmeService() {
		// Construtor privado
	}

	/**
	 * Obtém a instância de <code>AlarmeService<code>.
	 * 
	 * @return Instância de <code>AlarmeService<code>
	 */
	public static AlarmeService get() {
		if (instancia == null) {
			instancia = new AlarmeService();
		}

		return instancia;
	}

	/**
	 * Insere uma novo <code>Alarme<code> no banco de dados.
	 * 
	 * @param alarme
	 *            Alarme a ser inserido.
	 * 
	 * @return Chave primária do alarme inserido.
	 */
	public Long insere(Alarme alarme) {
		Long id = dao.consultaProximaPK();

		alarme.setId(id);

		dao.insere(alarme);

		return id;
	}

	public List<Alarme> consultaPorAcao(AcaoTagNFC acao) {
		List<Alarme> alarmes = dao.consultaPorAcao(acao);

		if (alarmes == null || alarmes.isEmpty()) {
			Log.d("AlarmeService",
					"Não há alarmes para a ação " + acao.toString());
		}

		return alarmes;
	}
}
