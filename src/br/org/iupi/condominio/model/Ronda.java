package br.org.iupi.condominio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.org.iupi.condominio.dao.Entidade;

public class Ronda extends Entidade<Long> implements Serializable {

	private static final long serialVersionUID = 9206873985044885786L;

	private Long id;

	private Date data;

	private Date horario;

	private List<LocalRonda> locais = new ArrayList<LocalRonda>();

	private EstadoDaRonda estado;

	public Ronda() {
		//
	}

	public Ronda(Date data, Date horario, List<LocalRonda> locais,
			EstadoDaRonda estado) {
		super();
		this.data = data;
		this.horario = horario;
		this.locais = locais;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public List<LocalRonda> getLocais() {
		return locais;
	}

	public void setLocais(List<LocalRonda> locais) {
		this.locais = locais;
	}

	public EstadoDaRonda getEstado() {
		return estado;
	}

	public void setEstado(EstadoDaRonda estado) {
		this.estado = estado;
	}
}
