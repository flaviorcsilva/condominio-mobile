package br.org.iupi.condominio.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<PK extends Serializable, T extends Entidade<PK>> {

	/**
	 * Insere uma entidade ainda nao persistida, <tt>entidade</tt>, no banco de
	 * dados.
	 * 
	 * @param entidade
	 *            Objeto a ser inserido.
	 */
	public PK insere(T entidade);

	/**
	 * Insere uma lista de entidades ainda nao persistida,
	 * <tt>lista de entidades</tt>, no banco de dados.
	 * 
	 * @param listaDeEntidades
	 *            Objeto a ser inserido.
	 */
	public void insere(List<T> listaDeEntidades);

	/**
	 * Atualiza a <tt>entidade</tt> no banco de dados.
	 * 
	 * @param entidade
	 *            Objeto ser atualizado.
	 */
	public void atualiza(T entidade);

	/**
	 * Apaga fisicamente um determinado objeto do banco de dados.
	 * 
	 * @param entidade
	 *            Objeto a ser excluido.
	 */
	public void remove(T entidade);

	/**
	 * Apaga fisicamente todos os objetos do banco de dados.
	 * 
	 * @return Quantidade de registros excluidos.
	 */
	public int removeTodos();

	/**
	 * Consulta todos os objetos desta entidade na base de dados.
	 * 
	 * @return Lista de objetos persistidos em banco.
	 */
	public List<T> consultaTodos();

	/**
	 * Consulta um determinado objeto por sua chave primaria.
	 * 
	 * @param id
	 *            Chave primaria do objeto a ser consultado.
	 * @return Objeto persistido.
	 */
	public T consultaPorId(PK id);
}