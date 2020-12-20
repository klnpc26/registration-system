package model.entidades;

import java.io.Serializable;
import java.util.Objects;

public class Cidade implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nome_cidade;
    private String nomeEst;
    
    private Estados estados;
    
    public Cidade(){
    }
    
    public Cidade(String nome_cidade, Estados estados) {
		super();
		this.nome_cidade = nome_cidade;
		this.estados = estados;
	}

	public Cidade(Integer id, String nome_cidade, Estados estados) {
		super();
		this.id = id;
		this.nome_cidade = nome_cidade;
		this.estados = estados;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome_cidade() {
		return nome_cidade;
	}

	public void setNome_cidade(String nome_cidade) {
		this.nome_cidade = nome_cidade;
	}

	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}

	@Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }
	
	public String getNomeEst() {
		return nomeEst;
	}

	public void setNomeEst(String nomeEst) {
		this.nomeEst = nomeEst;
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cidade other = (Cidade) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome_cidade=" + nome_cidade + ", estados=" + estados + "]";
	}
}
