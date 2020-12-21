package dao;

import java.util.List;
import model.entidades.Bairro;

public interface BairroDao {
    
    void inserir(Bairro obj);
    void excluir(Bairro obj);
    void editar(Bairro obj);
    List<Bairro> encontrarTudo();
    
}
