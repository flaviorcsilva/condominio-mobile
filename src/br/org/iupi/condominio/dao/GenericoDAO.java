package br.org.iupi.condominio.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IDAO Genérico que pode ser extendido pelos demais DAOs específicos para cada
 * entidade persistente. Sua implementação é baseada em {@HashMap}
 * 
 * @param <PK>
 *            - Chave primária da entidade
 * @param <T>
 *            - Entidade persistente
 * 
 * @author Outubro/2014: Flávio Roberto Cruz Silva <DD>
 */
public class GenericoDAO<PK extends Serializable, T extends Entidade<PK>>
		implements DAO<PK, T> {

	protected Map<PK, T> map = new HashMap<PK, T>();

	/**
	 * Insere uma lista de entidades ainda nao persistida,
	 * <tt>lista de entidades</tt>, no banco de dados.
	 * 
	 * @param listaDeEntidades
	 *            Objeto a ser inserido.
	 */
	public void insere(List<T> listaDeEntidades) {
		for (T entidade : listaDeEntidades) {
			insere(entidade);
		}
	}

	/**
	 * Insere uma entidade ainda não persistida, <tt>entidade</tt>, no banco de
	 * dados.
	 * 
	 * @param entidade
	 *            Objeto a ser inserido.
	 */
	public PK insere(T entidade) {
		map.put(entidade.getId(), entidade);

		return entidade.getId();
	}

	/**
	 * Atualiza a <tt>entidade</tt> no banco de dados.
	 * 
	 * @param entidade
	 *            Objeto ser atualizado.
	 */
	public void atualiza(T entidade) {
		map.put(entidade.getId(), entidade);
	}

	/**
	 * Apaga fisicamente um determinado objeto do banco de dados.
	 * 
	 * @param entidade
	 *            Objeto a ser excluído.
	 */
	public void remove(T entidade) {
		map.remove(entidade.getId());
	}

	/**
	 * Apaga fisicamente todos os objetos do banco de dados.
	 * 
	 * @return Quantidade de registros excluídos.
	 */
	public int removeTodos() {
		int size = map.size();
		map.clear();

		return size;
	}

	/**
	 * Consulta todos os objetos desta entidade na base de dados.
	 * 
	 * @return Lista de objetos persistidos em banco.
	 */
	public List<T> consultaTodos() {
		return new ArrayList<T>(map.values());
	}

	/**
	 * Consulta um determinado objeto por sua chave primária.
	 * 
	 * @param id
	 *            Chave primária do objeto a ser consultado.
	 * @return Objeto persistido.
	 */
	public T consultaPorId(PK id) {
		if (map.containsKey(id)) {
			return map.get(id);
		}

		return null;
	}
}
