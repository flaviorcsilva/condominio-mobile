package br.org.iupi.condominio.dao;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import br.org.iupi.condominio.model.EstadoDaRonda;
import br.org.iupi.condominio.model.LocalRonda;
import br.org.iupi.condominio.model.Ronda;

@SuppressWarnings("deprecation")
public class RondaDAO extends GenericoDAO<Long, Ronda> {

	private static RondaDAO instancia = null;

	{
		Date data01062015 = new Date(115, 06, 01);
		Date data02062015 = new Date(115, 06, 02);

		Ronda ronda1 = new Ronda(data01062015, new Date(2015, 06, 01, 21, 00),
				Arrays.asList(new LocalRonda("Entrada A - 2o. Subsolo Garagem",
						new Date(2015, 06, 01, 21, 05)), new LocalRonda(
						"Entrada C - 1o. Subsolo Garagem", new Date(2015, 06,
								01, 22, 16)), new LocalRonda(
						"Entrada B - Cobertura Coletiva", new Date(2015, 06,
								01, 23, 04))), EstadoDaRonda.REALIZADA);
		ronda1.setId(1L);

		insere(ronda1);

		Ronda ronda2 = new Ronda(data01062015, new Date(2015, 06, 01, 22, 00),
				Arrays.asList(new LocalRonda("Entrada C - 1o. Subsolo Garagem",
						new Date(2015, 06, 01, 22, 16))),
				EstadoDaRonda.NAO_REALIZADA);
		ronda2.setId(2L);

		insere(ronda2);

		Ronda ronda3 = new Ronda(data02062015, new Date(2015, 06, 01, 23, 00),
				Arrays.asList(new LocalRonda("Entrada B - Cobertura Coletiva",
						new Date(2015, 06, 01, 23, 04))),
				EstadoDaRonda.NAO_REALIZADA);
		ronda3.setId(3L);

		insere(ronda3);

		Ronda ronda4 = new Ronda(data02062015, new Date(2015, 06, 02, 24, 00),
				Arrays.asList(new LocalRonda("Bicicletario 2o. Subsolo",
						new Date(2015, 06, 02, 00, 38))),
				EstadoDaRonda.REALIZADA);
		ronda4.setId(4L);

		insere(ronda4);

		Ronda ronda5 = new Ronda(data02062015, new Date(2015, 06, 02, 01, 00),
				Arrays.asList(new LocalRonda("Entrada A - Pilots Hall",
						new Date(2015, 06, 02, 01, 47))),
				EstadoDaRonda.PENDENTE);
		ronda4.setId(5L);

		insere(ronda5);
	}

	private RondaDAO() {
		super();
	}

	public static RondaDAO get() {
		if (instancia == null) {
			instancia = new RondaDAO();
		}

		return instancia;
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