package dao;

import JDBC.CDB;
import model.impl.CidadeJDBC;
import model.impl.EstadosJDBC;

public class DaoFactory {
    
    public static EstadosDao createEstadosDao(){
        return new EstadosJDBC(CDB.conexao());
    }
    
    public static CidadeDao createCidadeDao() {
    	return new CidadeJDBC(CDB.conexao());
    }
}
