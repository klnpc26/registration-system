package model.servicos;

import java.util.List;

import dao.DaoFactory;
import dao.CidadeDao;
import model.entidades.Cidade;

public class CidadeServico {// Servi�o relacionado com opera��es das entidades
    
    private CidadeDao dao = DaoFactory.createCidadeDao();
    
    public List<Cidade> encontrarTudo(){
        return dao.encontrarTudo();
    }
    
    public void salvarOuEditar(Cidade obj){ // V� se inseri ou atualiza
        if(obj.getId() == null){
            dao.inserir(obj);
        }
        else{
            dao.editar(obj);
        }
    }
    
    public void remover(Cidade obj){ // Remover Cidade no Banco de Dados
        dao.excluir(obj);
    }
}
