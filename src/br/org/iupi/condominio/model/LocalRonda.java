package br.org.iupi.condominio.model;

import java.io.Serializable;
import java.util.Date;

public class LocalRonda implements Serializable {

	private static final long serialVersionUID = 7900832022956043867L;

	private String local;
	private Date horarioRealizado;

	public LocalRonda() {
		//
	}

	public LocalRonda(String local, Date horarioRealizado) {
		super();
		this.local = local;
		this.horarioRealizado = horarioRealizado;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Date getHorarioRealizado() {
		return horarioRealizado;
	}

	public void setHorarioRealizado(Date horarioRealizado) {
		this.horarioRealizado = horarioRealizado;
	}
}
