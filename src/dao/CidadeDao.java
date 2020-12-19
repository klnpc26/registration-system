package dao;

import java.util.List;
import model.entidades.Cidade;

public interface CidadeDao {
    
    void inserir(Cidade obj);
    void excluir(Cidade obj);
    void editar(Cidade obj);
    List<Cidade> encontrarTudo();
    
}
