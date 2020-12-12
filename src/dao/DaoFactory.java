package dao;

import ConnDB.CDB;
import model.impl.EstadosJDBC;

public class DaoFactory {
    
    public static EstadosDao createEstadosDao(){
        return new EstadosJDBC(CDB.conexao());
    }
}
