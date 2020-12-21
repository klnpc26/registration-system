package model.servicos;

import java.util.List;

import dao.DaoFactory;
import dao.BairroDao;
import model.entidades.Bairro;

public class BairroServico {// Serviço relacionado com operações das entidades
    
    private BairroDao dao = DaoFactory.createBairroDao();
    
    public List<Bairro> encontrarTudo(){
        return dao.encontrarTudo();
    }
    
    public void salvarOuEditar(Bairro obj){ // Vê se inseri ou atualiza
        if(obj.getId() == null){
            dao.inserir(obj);
        }
        else{
            dao.editar(obj);
        }
    }
    
    public void remover(Bairro obj){ // Remover Bairro no Banco de Dados
        dao.excluir(obj);
    }
}
