package br.org.iupi.condominio.dao;

public abstract class Entidade<PK> {

	/**
	 * Retorna a chave primaria da entidade.
	 * 
	 * @return Chave primaria da entidade.
	 */
	public abstract PK getId();

	/**
	 * Atribui uma nova chave primaria para a entidade.
	 * 
	 * @param id
	 *            Chave primaria a ser atribuida.
	 */
	public abstract void setId(PK id);
}
