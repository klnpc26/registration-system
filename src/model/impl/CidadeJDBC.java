package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import JDBC.BdException;
import JDBC.CDB;
import dao.CidadeDao;
import model.entidades.Cidade;
import model.entidades.Estados;

public class CidadeJDBC implements CidadeDao{
    
    private Connection conn;
    
    public CidadeJDBC() {
    }
    
    public CidadeJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void inserir(Cidade obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("insert into cidade (nome_cidade, id_estado) values (?,?)");
            
            pst.setString(1, obj.getNome_cidade());
            pst.setInt(2, obj.getEstados().getId());
            
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
    public void excluir(Cidade obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("delete from cidade where id_cidade = ?");
            
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
    public void editar(Cidade obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("update cidade set nome_cidade = ?, id_estado = ? where id_cidade = ?");
            
            pst.setString(1, obj.getNome_cidade());
            pst.setInt(2, obj.getEstados().getId());
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
    
    private Estados incrementandoEstados(ResultSet rs) throws SQLException { // Método para pegar os dados de estado
    	Estados est = new Estados();
    	est.setId(rs.getInt("id_estado"));
    	est.setNome_estado("nome_estado");
    	return est;
    }
    
    private Cidade incrementandoCidade(ResultSet rs, Estados est) throws SQLException { // Método para pegar os dados da cidade
    	Cidade cid = new Cidade();
        cid.setId(rs.getInt("id_cidade"));
        cid.setNome_cidade(rs.getString("Estados"));
        cid.setEstados(est);
        return cid;
        
    }

    @Override
    public List<Cidade> encontrarTudo() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try{
            pst = conn.prepareStatement("select cidade.*,estados.nome_estado as Estados from cidade inner join estados on cidade.id_estado = estados.id_estado order by nome_estado;");
            
            rs = pst.executeQuery();// o rs esta na posição 0
            
            List<Cidade> list = new ArrayList<>();
            Map<Integer, Estados> map = new HashMap<>();
            
            while(rs.next()){ 
            	
            	Estados est = map.get(rs.getInt("id_estado"));
            	
            	if(est == null) {
            		est = incrementandoEstados(rs);
            		map.put(rs.getInt("id_estado"), est);
            	}
            	
            	Cidade cid = incrementandoCidade(rs, est);
            	list.add(cid);
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
