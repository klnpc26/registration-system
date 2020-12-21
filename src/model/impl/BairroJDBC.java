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
import dao.BairroDao;
import model.entidades.Bairro;
import model.entidades.Cidade;
import model.entidades.Estados;

public class BairroJDBC implements BairroDao{
    
    private Connection conn;
    
    public BairroJDBC() {
    }
    
    public BairroJDBC(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void inserir(Bairro obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("insert into bairro (nome_bairro, id_cidade, id_estado) values (?,?,?)");
            
            pst.setString(1, obj.getNome_bairro());
            pst.setInt(2, obj.getCidade().getId());
            pst.setInt(3, obj.getEstados().getId());
            
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
    public void excluir(Bairro obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("delete from bairro where id_bairro = ?");
            
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
    public void editar(Bairro obj) {
        PreparedStatement pst = null;
        
        try{
            pst = conn.prepareStatement("update bairro set nome_bairro = ?, id_cidade = ?, id_estado = ? where id_bairro = ?");
            
            pst.setString(1, obj.getNome_bairro());
            pst.setInt(2, obj.getCidade().getId());
            pst.setInt(3, obj.getEstados().getId());
            pst.setInt(4, obj.getId());
            
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
    	est.setNome_estado(rs.getString("Estado"));
    	return est;
    }
    
    private Cidade incrementandoCidade(ResultSet rs, Estados est) throws SQLException { // Método para pegar os dados da cidade
    	Cidade cid = new Cidade();
        cid.setId(rs.getInt("id_cidade"));
        cid.setNome_cidade(rs.getString("Cidade"));
        cid.setEstados(est);
        return cid;
    }
    
    private Bairro incrementandoBairro(ResultSet rs, Cidade cid, Estados est) throws SQLException {
    	Bairro bairro = new Bairro();
    	bairro.setId(rs.getInt("id_bairro"));
    	bairro.setNome_bairro(rs.getString("nome_bairro"));
    	bairro.setCidade(cid);
    	bairro.setEstados(est);
    	return bairro;
    }

    @Override
    public List<Bairro> encontrarTudo() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try{
            pst = conn.prepareStatement("select bairro.*, cidade.nome_cidade as Cidade, estados.nome_estado as Estado from bairro inner join cidade on bairro.id_cidade = cidade.id_cidade inner join estados on bairro.id_estado = estados.id_estado");
            
            rs = pst.executeQuery();// o rs esta na posição 0
            
            List<Bairro> list = new ArrayList<>();
            Map<Integer, Cidade> mapp = new HashMap<>();
            Map<Integer, Estados> map = new HashMap<>();
            
            while(rs.next()){ 
            	
            	Cidade cid = mapp.get(rs.getInt("id_cidade"));
            	Estados est = map.get(rs.getInt("id_estado"));
            	
            	if(cid == null) {
            		cid = incrementandoCidade(rs, est);
            		mapp.put(rs.getInt("id_cidade"), cid);
            	}
            	
            	if(est == null) {
            		est = incrementandoEstados(rs);
            		map.put(rs.getInt("id_estado"), est);
            	}
            	
            	Bairro bairro = incrementandoBairro(rs, cid, est);
            	list.add(bairro);
            }
            
            System.out.println("Encontrado todos os dados!");
            return list;
        }
        catch(SQLException e){
        	e.printStackTrace();
            throw new BdException("Erro ao pegar todos os dados!");
        }
        finally{
            CDB.closeStatement(pst);
            CDB.closeResultSet(rs);
        }
    }

}
