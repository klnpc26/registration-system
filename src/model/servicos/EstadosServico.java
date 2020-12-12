package model.servicos;

import java.util.List;

import dao.DaoFactory;
import dao.EstadosDao;
import model.entidades.Estados;

public class EstadosServico {
    
    private EstadosDao dao = DaoFactory.createEstadosDao();
    
    public List<Estados> encontrarTudo(){
        return dao.encontrarTudo();
    }
    
    public void salvarOuEditar(Estados obj){
        if(obj.getId() == null){
            dao.inserir(obj);
        }
        else{
            dao.editar(obj);
        }
    }
    
    public void remover(Estados obj){
        dao.excluir(obj);
    }
}
