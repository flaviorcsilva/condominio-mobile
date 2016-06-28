package br.org.iupi.condominio.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.org.iupi.condominio.model.AcaoTagNFC;
import br.org.iupi.condominio.model.Alarme;

public class AlarmeDAO extends GenericoDAO<Long, Alarme> {

	private static AlarmeDAO instancia = null;

	private AlarmeDAO() {
		super();
	}

	public static AlarmeDAO get() {
		if (instancia == null) {
			instancia = new AlarmeDAO();
		}

		return instancia;
	}

	public List<Alarme> consultaPorAcao(AcaoTagNFC acao) {
		List<Alarme> alarmes = new ArrayList<Alarme>();

		for (Alarme alarme : map.values()) {
			if (alarme.getAcao().equals(acao)) {
				alarmes.add(alarme);
			}
		}

		return alarmes;
	}

	public Long consultaProximaPK() {
		if (!map.isEmpty()) {
			Long pk = Collections.max(map.keySet());

			return pk + 1;
		} else {
			return 1L;
		}
	}
}