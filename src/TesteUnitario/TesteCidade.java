package TesteUnitario;

import java.util.List;

import dao.CidadeDao;
import dao.DaoFactory;
import model.entidades.Cidade;
import model.entidades.Estados;

public class TesteCidade {

	public static void main(String[] args) {
		
		CidadeDao cidadeDao = DaoFactory.createCidadeDao();
		Estados est = new Estados(2);
		
		System.out.println("=== TESTE 1: INSERIR ===");
		Cidade obj = new Cidade("Ribeirão Pires", est);
		cidadeDao.inserir(obj);
		
		System.out.println("=== TESTE 2: EXCLUIR ===");
		obj = new Cidade(2, null, null);
		cidadeDao.excluir(obj);
		
		System.out.println("=== TESTE 3: EDITAR ===");
		obj = new Cidade(3, "maua", est);
		cidadeDao.editar(obj);
		
		System.out.println("=== TESTE 4: ENCONTRAR TUDO ===");
		List<Cidade> list = cidadeDao.encontrarTudo();
		for(Cidade c : list) {
			System.out.println(c);
		}
		
	}
}
