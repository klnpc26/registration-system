package TesteUnitario;

import java.util.List;

import dao.BairroDao;
import dao.DaoFactory;
import model.entidades.Bairro;
import model.entidades.Cidade;
import model.entidades.Estados;

public class TesteBairro {

	public static void main(String[] args) {
		
		BairroDao bairroDao = DaoFactory.createBairroDao();
		Cidade cid = new Cidade(17);
		Estados est = new Estados(2);
//		
//		System.out.println("=== TESTE 1: INSERIR ===");
//		Bairro obj = new Bairro("Represa", cid, est);
//		bairroDao.inserir(obj);
//		
//		System.out.println("=== TESTE 2: EXCLUIR ===");
//		obj = new Bairro(2, null, null, null);
//		bairroDao.excluir(obj);
//		
//		System.out.println("=== TESTE 3: EDITAR ===");
//		obj = new Bairro(1, "Caçula", cid, est);
//		bairroDao.editar(obj);
		
		System.out.println("=== TESTE 4: ENCONTRAR TUDO ===");
		List<Bairro> list = bairroDao.encontrarTudo();
		for(Bairro c : list) {
			System.out.println(c);
		}
	}
}
