package br.org.iupi.condominio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum TipoLeitura implements Serializable {

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

	public static TipoLeitura get(String valor) {
		TipoLeitura tipo = null;
		for (TipoLeitura tipoLeitura : TipoLeitura.values()) {
			if (tipoLeitura.getValor().equals(valor)) {
				tipo = tipoLeitura;

				break;
			}
		}

		return tipo;
	}

	public static List<String> getValores() {
		List<String> valores = new ArrayList<String>();

		for (TipoLeitura tipoLeitura : TipoLeitura.values()) {
			valores.add(tipoLeitura.getValor());
		}

		return valores;
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