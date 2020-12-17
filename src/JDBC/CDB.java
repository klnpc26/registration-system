package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CDB {
    
    private static String driver = "com.mysql.jdbc.Driver";// responsavel por indentificar o serviço de banco de dados 
    private static String URL = "jdbc:mysql://127.0.0.1:3306/sistemavideoaula";// responsavel por setar o local do banco de dados
    private static String user = "root";
    private static String pass = "kaique2613";
    
    public static Connection conn = null;
    
    public static Connection conexao(){
        try{
            if(conn == null){
                Class.forName(driver);
                conn = DriverManager.getConnection(URL, user, pass);
            }else if(conn.isClosed()){
                conn = null;
                return conn;
            }
            System.out.println("Conectado!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
       return conn;
    }
    
    public static void closeConn(){
        if(conn != null){
            try{
                conn.close();
                System.out.println("Desconectado!");
            }
            catch(SQLException e){
                throw new BdException(e.getMessage());
            }
        }
    }
    
    public static void closeStatement(Statement st){
        if(st != null){
            try{
                st.close();
            }
            catch(SQLException e){
                throw new BdException(e.getMessage());
            }
        }
    }
    
    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }
            catch(SQLException e){
                throw new BdException(e.getMessage());
            }
        }
    }
}
