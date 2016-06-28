package br.org.iupi.condominio.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import br.org.iupi.condominio.dao.Entidade;

@SuppressLint("DefaultLocale")
public class Alarme extends Entidade<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private AcaoTagNFC acao;

	private int hora;

	private int minuto;

	private Set<Integer> diasDaSemana = new HashSet<Integer>();

	public Alarme() {
		//
	}

	public Alarme(AcaoTagNFC acao, int hora, int minuto, Set<Integer> diasDaSemana) {
		super();

		this.acao = acao;
		this.hora = hora;
		this.minuto = minuto;
		this.diasDaSemana = diasDaSemana;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AcaoTagNFC getAcao() {
		return acao;
	}

	public void setAcao(AcaoTagNFC acao) {
		this.acao = acao;
	}

	public int getHora() {
		return hora;
	}

	public String getHoraFormatada() {
		return String.format("%02d", hora);
	}

	public void setHora(int hora) {
		this.hora = hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public String getMinutoFormatado() {
		return String.format("%02d", minuto);
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	public Set<Integer> getDiasDaSemana() {
		return diasDaSemana;
	}

	public void setDiasDaSemana(Set<Integer> diasDaSemana) {
		this.diasDaSemana = diasDaSemana;
	}

	public void adicionaDiaDaSemana(Integer diaDaSemana) {
		diasDaSemana.add(diaDaSemana);
	}

	public void removeDiaDaSemana(Integer diaDaSemana) {
		diasDaSemana.remove(diaDaSemana);
	}
}
