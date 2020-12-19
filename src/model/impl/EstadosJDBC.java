package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.BdException;
import JDBC.CDB;
import dao.EstadosDao;
import model.entidades.Estados;

public class EstadosJDBC implements EstadosDao{
    
    private Connection conn;
    
    public EstadosJDBC() {
    }
    
    public EstadosJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void inserir(Estados obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("insert into estados (nome_estado, uf_estado) values (?,?)");
            
            pst.setString(1, obj.getNome_estado());
            pst.setString(2, obj.getUf());
            
            pst.executeUpdate();
            
            System.out.println("Inserido!");
        }
        catch(SQLException e){
            throw new BdException("Erro ao inserir dados");
        }
        finally{
            CDB.closeStatement(pst);
        }
    }

    @Override
    public void excluir(Estados obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("delete from estados where id_estado = ?");
            
            pst.setInt(1, obj.getId());
            
            pst.executeUpdate();
            
            System.out.println("Excluido!");
        }
        catch(SQLException e){
            throw new BdException("Erro ao deletar dados");
        }
        finally{
            CDB.closeStatement(pst);
        }
    }

    @Override
    public void editar(Estados obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("update estados set nome_estado = ?, uf_estado = ? where id_estado = ?");
            
            pst.setString(1, obj.getNome_estado());
            pst.setString(2, obj.getUf());
            pst.setInt(3, obj.getId());
            
            pst.executeUpdate();
            
            System.out.println("Editado!");
        }
        catch(SQLException ex){
            throw new BdException("Erro ao editar dados!");
        }
        finally{
            CDB.closeStatement(pst);
        }
    }

    @Override
    public List<Estados> encontrarTudo() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try{
            pst = conn.prepareStatement("select * from estados order by nome_estado");
            
            rs = pst.executeQuery();
            
            List<Estados> list = new ArrayList<>();
            
            while(rs.next()){
                Estados est = new Estados();
                est.setId(rs.getInt("id_estado"));
                est.setNome_estado(rs.getString("nome_estado"));
                est.setUf(rs.getString("uf_estado"));
                list.add(est);
            }
            
            System.out.println("Encontrado todos os dados!");
            return list;
        }
        catch(SQLException e){
            throw new BdException("Erro ao pegar todos os dados!");
        }
        finally{
            CDB.closeStatement(pst);
            CDB.closeResultSet(rs);
        }
    }

}
