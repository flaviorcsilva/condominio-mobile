package br.org.iupi.condominio.model;

public enum TipoLeitura {

	AGUA_FRIA(1, "Água Fria"), AGUA_QUENTE(2, "Água Quente"), GAS(3, "Gás");

	private Integer chave;

	private String valor;

	private TipoLeitura(Integer chave, String valor) {
		this.chave = chave;
		this.setValor(valor);
	}

	public static TipoLeitura get(Integer chave) {
		TipoLeitura tipo = null;
		for (TipoLeitura tipoLeitura : TipoLeitura.values()) {
			if (tipoLeitura.getChave().equals(chave)) {
				tipo = tipoLeitura;

				break;
			}
		}

		return tipo;
	}

	public Integer getChave() {
		return chave;
	}

	public void setChave(Integer chave) {
		this.chave = chave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}