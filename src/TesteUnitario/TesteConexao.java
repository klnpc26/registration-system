package TesteUnitario;

import ConnDB.CDB;

public class TesteConexao {

	public static void main(String[] args) {
		
		CDB conn = new CDB();
		
		conn.conexao();
		CDB.closeConn();

	}
}
