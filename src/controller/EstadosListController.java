package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import app.appFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import listerner.MudancaDados;
import model.entidades.Estados;
import model.servicos.EstadosServico;

public class EstadosListController implements Initializable, MudancaDados{ // A classe que RECEBE o evento (observer)
	
	private EstadosServico servico;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<Estados> tableViewEstados;
	
	@FXML
	private TableColumn<Estados, Integer> columnId;
	
	@FXML
	private TableColumn<Estados, String> columnNome;
	
	@FXML
	private TableColumn<Estados, String> columnUf;
	
	private ObservableList<Estados> obsList;//Carrega os estados
	
	@FXML
	public void acaoBotaoNovo(ActionEvent evento) {
		Stage parentStage = (Stage) ((Node) evento.getSource()).getScene().getWindow();// acessa o Stage do botão novo, ou seja, abre o stage do formulário de Estados. 
		Estados obj = new Estados();// Instanciar um Estado vazio
		formDialogo(obj, "/view/EstadosForm.fxml", parentStage);
	}
	
	public void setEstadosServico(EstadosServico servico) {
		this.servico = servico;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() { //iniciar os comportamentos das colunas da tabela
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome_estado"));
		columnUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
		
		//Fazer a tabela acompanhar a altura e largura da janela
		Stage stage = (Stage) appFx.getMainScene().getWindow(); //pega a referencia para a janela
		tableViewEstados.prefHeightProperty().bind(stage.heightProperty()); // A tablea acompanha a altura da janela 
	}
	
	public void atualizaTabela() { //acessa o serviço, carrega os estados e joga o estados dentro da ObservavleList, logo após associo ele com tableView.
		if(servico == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		List<Estados> list = servico.encontrarTudo();// Pegou a lista de Estados
		obsList = FXCollections.observableArrayList(list);// joga os Estados carregados dentro do obsList
		tableViewEstados.setItems(obsList);// Mostra os estados na tabela
	}
	
	private void formDialogo(Estados obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));//abre uma tela
			Pane pane = loader.load();// carrega a view
			
			EstadosFormController controller = loader.getController();// Pega o controlador da tela que acabou de carregar, que é o formulário
			controller.setEstados(obj);// injeta o Estado
			controller.setEstadosServico(new EstadosServico());// injeta EstadosServico
			controller.inscreverAlteracaoDados(this);
			controller.attDadosForm();

			Stage dialogStage = new Stage();// criar uma stage na frente do outro. Janela na frente de outra
			dialogStage.setTitle("Enter Department data");// Configuta o titulo da janela
			dialogStage.setScene(new Scene(pane));// representa um novo conteúdo exibido dentro de uma janela
			dialogStage.setResizable(false);// a janela não pode ser redimensionada
			dialogStage.initOwner(parentStage);// quem é o Stage pai dessa janela
			dialogStage.initModality(Modality.WINDOW_MODAL);// vai ficar trava, sem acesso a janela anterior, até você fechar
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void mudancaDeDados() {
		atualizaTabela();
	}
}
