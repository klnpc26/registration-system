package model.entidades;

import java.io.Serializable;

public class Bairro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome_bairro;
	
	private Cidade cidade;
	private Estados estados;
	
	public Bairro() {
	}
	
	public Bairro(String nome_bairro, Cidade cidade, Estados estados) {
		super();
		this.nome_bairro = nome_bairro;
		this.cidade = cidade;
		this.estados = estados;
	}

	public Bairro(Integer id, String nome_bairro, Cidade cidade, Estados estados) {
		super();
		this.id = id;
		this.nome_bairro = nome_bairro;
		this.cidade = cidade;
		this.estados = estados;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_bairro() {
		return nome_bairro;
	}

	public void setNome_bairro(String nome_bairro) {
		this.nome_bairro = nome_bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bairro other = (Bairro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome_bairro;
	}
}
