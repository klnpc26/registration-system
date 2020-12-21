package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import JDBC.BdException;
import app.appFx;
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
import model.entidades.Bairro;
import model.entidades.Cidade;
import model.entidades.Estados;
import model.servicos.BairroServico;
import model.servicos.CidadeServico;
import model.servicos.EstadosServico;
import util.Alerta;

public class BairroListController implements Initializable, MudancaDados{ // A classe que RECEBE o evento (observer)
	
	private BairroServico servico;
	
	private Bairro obj;
	
	@FXML
	private Button btNovo;
	
	@FXML
	private TableView<Bairro> tableViewBairro;
	
	@FXML
	private TableColumn<Bairro, Integer> columnId;
	
	@FXML
	private TableColumn<Bairro, String> columnNome; 
	
	@FXML
	private TableColumn<Bairro, Cidade> columnCidade;
	
	@FXML
	private TableColumn<Bairro, Estados> columnEstados;
		
	@FXML
	private TableColumn<Bairro, Bairro> tableColumnEDITAR;
	
	@FXML
	private TableColumn<Bairro, Bairro> tableColumnREMOVER;
	
	
	private ObservableList<Bairro> obsList;//Carrega as ciadades
	
	@FXML
	public void acaoBotaoNovo(ActionEvent evento) {
		Stage parentStage = (Stage) ((Node) evento.getSource()).getScene().getWindow();// acessa o Stage do botão novo, ou seja, abre o stage do formulário de Bairro. 
		Bairro obj = new Bairro();// Instanciar uma Bairro vazio
		formDialogo(obj, "/view/BairroForm.fxml", parentStage);
	}
	
	public void setBairroServico(BairroServico servico) {
		this.servico = servico;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() { //iniciar os comportamentos das colunas da tabela
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome_bairro"));
		columnCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
		columnEstados.setCellValueFactory(new PropertyValueFactory<>("estados"));
		
		//Fazer a tabela acompanhar a altura e largura da janela
		Stage stage = (Stage) appFx.getMainScene().getWindow(); //pega a referencia para a janela
		tableViewBairro.prefHeightProperty().bind(stage.heightProperty()); // A tabela acompanha a altura da janela 
	}
	
	public void atualizaTabela() { //acessa o serviço, carrega as cidades e joga o estados dentro da ObservavleList, logo após associo ele com tableView.
		if(servico == null) {
			throw new IllegalStateException("Serviço nulo");
		}
		List<Bairro> list = servico.encontrarTudo();// Pegou a lista de Bairro
		obsList = FXCollections.observableArrayList(list);// joga os Bairro carregados dentro do obsList
		tableViewBairro.setItems(obsList);// Mostra as cidades na tabela
		botaoEditar();
		botaoRemover();
	}
	
	private void formDialogo(Bairro obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));//abre uma tela
			Pane pane = loader.load();// carrega a view
			
			BairroFormController controller = loader.getController();// Pega o controlador da tela que acabou de carregar, que é o formulário
			controller.setBairro(obj);// injeta o Estado
			controller.setServicos(new BairroServico(), new CidadeServico(), new EstadosServico());// injeta BairroServico e EstadoServico
			controller.carregarObj(); // carrega os Estados do bnco de dados e deixar no controller
			controller.inscreverAlteracaoDados(this);
			controller.attDadosForm();

			Stage dialogStage = new Stage();// criar uma stage na frente do outro. Janela na frente de outra
			dialogStage.setTitle("Entrar dados de Bairro");// Configura o titulo da janela
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
		tableColumnEDITAR.setCellFactory(x -> new TableCell<Bairro, Bairro>() {
				private final Button bt = new Button("Editar");
				
				@Override
				protected void updateItem(Bairro obj, boolean vazia) {
					super.updateItem(obj, vazia);
					
					if(obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(bt);
					bt.setOnAction(
					evento -> formDialogo(
							obj, "/view/BairroForm.fxml", (Stage) ((Node) evento.getSource()).getScene().getWindow()));// Quando clicar no botão "Editar" vai abrir o formulário de edição
				}
		});
	}
	
	public void botaoRemover() {
		tableColumnREMOVER.setCellValueFactory(x -> new ReadOnlyObjectWrapper<>(x.getValue()));
		tableColumnREMOVER.setCellFactory(x -> new TableCell<Bairro, Bairro>() {
			private final Button bt = new Button("Remover");
			
			@Override
			protected void updateItem(Bairro obj, boolean vazia) {
				super.updateItem(obj, vazia);
				
				if(obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(bt);
				bt.setOnAction(x -> removeEntidade(obj));// Quando clicar no botão "Remover" vai remover Bairro
			}
	});
}
	
	protected void removeEntidade(Bairro obj) {
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
