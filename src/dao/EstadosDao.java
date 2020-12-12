package dao;

import java.util.List;
import model.entidades.Estados;

public interface EstadosDao {
    
    void inserir(Estados obj);
    void excluir(Estados obj);
    void editar(Estados obj);
    List<Estados> encontrarTudo();
    
}
