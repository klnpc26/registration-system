package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import JDBC.BdException;
import app.appFx;
import dao.CidadeDao;
import dao.DaoFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import listerner.MudancaDados;
import model.entidades.Cidade;
import model.entidades.Estados;
import model.impl.CidadeJDBC;
import model.servicos.CidadeServico;
import model.servicos.EstadosServico;
import util.Alerta;

public class CidadeListController implements Initializable, MudancaDados{ // A classe que RECEBE o evento (observer)
	
	private CidadeServico servico;
	
	private Cidade obj;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<Cidade> tableViewCidade;
	
	@FXML
	private TableColumn<Cidade, Integer> columnId;
	
	@FXML
	private TableColumn<Cidade, String> columnNome; 
	
	@FXML
	private TableColumn<Cidade, Estados> columnEstados;
		
	@FXML
	private TableColumn<Cidade, Cidade> tableColumnEDITAR;
	
	@FXML
	private TableColumn<Cidade, Cidade> tableColumnREMOVER;
	
	
	private ObservableList<Cidade> obsList;//Carrega as ciadades
	
	@FXML
	public void acaoBotaoNovo(ActionEvent evento) {
		Stage parentStage = (Stage) ((Node) evento.getSource()).getScene().getWindow();// acessa o Stage do botão novo, ou seja, abre o stage do formulário de Cidade. 
		Cidade obj = new Cidade();// Instanciar uma Cidade vazio
		formDialogo(obj, "/view/CidadeForm.fxml", parentStage);
	}
	
	public void setCidadeServico(CidadeServico servico) {
		this.servico = servico;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() { //iniciar os comportamentos das colunas da tabela
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome_cidade"));
		columnEstados.setCellValueFactory(new PropertyValueFactory<>("estados"));
		
		//Fazer a tabela acompanhar a altura e largura da janela
		Stage stage = (Stage) appFx.getMainScene().getWindow(); //pega a referencia para a janela
		tableViewCidade.prefHeightProperty().bind(stage.heightProperty()); // A tabela acompanha a altura da janela 
	}
	
	public void atualizaTabela() { //acessa o serviço, carrega as cidades e joga o estados dentro da ObservavleList, logo após associo ele com tableView.
		if(servico == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		List<Cidade> list = servico.encontrarTudo();// Pegou a lista de Cidade
		obsList = FXCollections.observableArrayList(list);// joga os Cidade carregados dentro do obsList
		tableViewCidade.setItems(obsList);// Mostra as cidades na tabela
		botaoEditar();
		botaoRemover();
	}
	
	private void formDialogo(Cidade obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));//abre uma tela
			Pane pane = loader.load();// carrega a view
			
			CidadeFormController controller = loader.getController();// Pega o controlador da tela que acabou de carregar, que é o formulário
			controller.setCidade(obj);// injeta o Estado
			controller.setServicos(new CidadeServico(), new EstadosServico());// injeta CidadeServico
			controller.carregarObj();
			controller.inscreverAlteracaoDados(this);
			controller.attDadosForm();

			Stage dialogStage = new Stage();// criar uma stage na frente do outro. Janela na frente de outra
			dialogStage.setTitle("Entrar dados de cidade");// Configura o titulo da janela
			dialogStage.setScene(new Scene(pane));// representa um novo conteúdo exibido dentro de uma janela
			dialogStage.setResizable(false);// a janela não pode ser redimensionada
			dialogStage.initOwner(parentStage);// quem é o Stage pai dessa janela
			dialogStage.initModality(Modality.WINDOW_MODAL);// vai ficar trava, sem acesso a janela anterior, até você fechar
			dialogStage.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
			Alerta.mostrarAlerta("IO Exception", "Ao carregar tabela", e.getMessage(), AlertType.ERROR);
			
		}
	}

	@Override
	public void mudancaDeDados() {
		atualizaTabela();
	}
	
	public void botaoEditar() { // criar um botão de edição em cada linha da tabela
		tableColumnEDITAR.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
		tableColumnEDITAR.setCellFactory(x -> new TableCell<Cidade, Cidade>() {
				private final Button bt = new Button("Editar");
				
				@Override
				protected void updateItem(Cidade obj, boolean vazia) {
					super.updateItem(obj, vazia);
					
					if(obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(bt);
					bt.setOnAction(
					evento -> formDialogo(
							obj, "/view/CidadeForm.fxml", (Stage) ((Node) evento.getSource()).getScene().getWindow()));// Quando clicar no botão "Editar" vai abrir o formulário de edição
				}
		});
	}
	
	public void botaoRemover() {
		tableColumnREMOVER.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
		tableColumnREMOVER.setCellFactory(x -> new TableCell<Cidade, Cidade>() {
			private final Button bt = new Button("Remover");
			
			@Override
			protected void updateItem(Cidade obj, boolean vazia) {
				super.updateItem(obj, vazia);
				
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(bt);
				bt.setOnAction(x -> removeEntidade(obj));// Quando clicar no botão "Remover" vai remover Cidade
			}
	});
}
	
	protected void removeEntidade(Cidade obj) {
		Optional<ButtonType> resultado = Alerta.mostrarConfirmacao("Confirmar", "Tem certeza que deja excluir? ");
		
		if(resultado.get() == ButtonType.OK) {
			if(servico == null) {
				throw new IllegalStateException("Serviço nulo");
			}
			try {
				servico.remover(obj);// remove
				atualizaTabela();// atualiza tabela
			}
			catch(BdException e) {
				e.printStackTrace();
				Alerta.mostrarAlerta("Erro em remover objeto", null, e.getMessage(), AlertType.ERROR);
			} 
		}	
	}
}
