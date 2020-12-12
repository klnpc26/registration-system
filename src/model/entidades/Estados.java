package model.entidades;

import java.util.Objects;

public class Estados {
    
    private Integer id;
    private String nome_estado;
    private String uf;
    
    public Estados(){
    }
    
    public Estados(String nome_estado, String uf) {
        this.nome_estado = nome_estado;
        this.uf = uf;
    }
    
    public Estados(Integer id, String nome_estado, String uf) {
        this.id = id;
        this.nome_estado = nome_estado;
        this.uf = uf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome_estado() {
        return nome_estado;
    }

    public void setNome_estado(String nome_estado) {
        this.nome_estado = nome_estado;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
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
        final Estados other = (Estados) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

	@Override
	public String toString() {
		return "Estados [id=" + id + ", nome_estado=" + nome_estado + ", uf=" + uf + "]";
	}
}
