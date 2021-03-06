package br.org.iupi.condominio.model;

import java.io.Serializable;
import java.util.Date;

import android.annotation.SuppressLint;
import br.org.iupi.condominio.dao.Entidade;

@SuppressLint("DefaultLocale")
public class Leitura extends Entidade<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String unidade;

	private TipoLeitura tipo;

	private Integer medido;

	private byte[] foto;

	private Date data;

	public Leitura() {
		this.data = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public TipoLeitura getTipo() {
		return tipo;
	}

	public void setTipo(TipoLeitura tipo) {
		this.tipo = tipo;
	}

	public Integer geMedido() {
		return medido;
	}

	public void setMedido(Integer medido) {
		this.medido = medido;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}