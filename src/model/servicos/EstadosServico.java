package model.servicos;

import java.util.List;

import dao.DaoFactory;
import dao.EstadosDao;
import model.entidades.Estados;

public class EstadosServico {// Servi�o relacionado com opera��es das entidades
    
    private EstadosDao dao = DaoFactory.createEstadosDao();
    
    public List<Estados> encontrarTudo(){
        return dao.encontrarTudo();
    }
    
    public void salvarOuEditar(Estados obj){ // V� se inseri ou atualiza
        if(obj.getId() == null){
            dao.inserir(obj);
        }
        else{
            dao.editar(obj);
        }
    }
    
    public void remover(Estados obj){ // Remover Estados no Banco de Dados
        dao.excluir(obj);
    }
}
