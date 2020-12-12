package TesteUnitario;

import java.util.List;

import dao.DaoFactory;
import dao.EstadosDao;
import model.entidades.Estados;

public class TesteEstado {

	public static void main(String[] args) {
		
		EstadosDao estadosDao = DaoFactory.createEstadosDao();
		
		System.out.println("=== TESTE 1: INSERIR ===");
		Estados obj = new Estados("Sampa", "SP");
		estadosDao.inserir(obj);
		
		System.out.println("=== TESTE 2: EXCLUIR ===");
		obj = new Estados(22, null, null);
		estadosDao.excluir(obj);
		
		System.out.println("=== TESTE 3: EDITAR ===");
		obj = new Estados(20, "Parana", "PR");
		estadosDao.editar(obj);
		
		System.out.println("=== TESTE 4: ENCONTRAR TUDO ===");
		List<Estados> list = estadosDao.encontrarTudo();
		for(Estados e : list) {
			System.out.println(e);
		}
		
	}
}
