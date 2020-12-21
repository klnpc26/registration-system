package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import JDBC.BdException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import listerner.MudancaDados;
import model.entidades.Bairro;
import model.entidades.Cidade;
import model.entidades.Estados;
import model.servicos.BairroServico;
import model.servicos.CidadeServico;
import model.servicos.EstadosServico;
import util.Alerta;
import util.Restricoes;
import util.Utils;

public class BairroFormController implements Initializable{ // A classe que EMITE o evento (subject)
	
	private Bairro entidade;
	
	private BairroServico servico;
	
	private EstadosServico estado; // buscar os dados no banco de dados 
	
	private CidadeServico cidade; // buscar os dados no banco de dados 
	
	private List<MudancaDados> mudancaDados = new ArrayList<>();// a classe vai guardar uma lista de objetos interessados em receber o evento
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private ComboBox<Cidade> comboCidade;
	
	@FXML
	private ComboBox<Estados> comboEstados;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	private ObservableList<Cidade> obsListCidade;
	
	@FXML
	private ObservableList<Estados> obsListEstados;
	
	public void setBairro(Bairro entidade) {
		this.entidade = entidade;
	}
	
	public void setServicos(BairroServico b, CidadeServico c, EstadosServico e) {
		this.servico = b;
		this.cidade = c;
		this.estado = e;
	}
	
	public void inscreverAlteracaoDados(MudancaDados ouvinte) {
		mudancaDados.add(ouvinte);
	}
	
	@FXML
	public void btSalvarAcao(ActionEvent evento) {
		if(entidade == null) {
			throw new IllegalStateException("Entidade nula");
		}
		if(servico == null) {
			throw new IllegalStateException("Servico nula");
		}
		
		try {
			entidade = getDadosForm();// pega os dados da caixa de texto e joga na variável entidade
			servico.salvarOuEditar(entidade);
			notificaMudancaDados(); 
			Stage parentStage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
			parentStage.close();
		}
		catch(BdException e) {
			Alerta.mostrarAlerta("Erro ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificaMudancaDados() { // notifica os dados alterados
		for(MudancaDados ouvinte : mudancaDados) {
			ouvinte.mudancaDeDados();
		}
	}

	private Bairro getDadosForm() { // Responsável por pegar os dados que estão nas caixa de texto do formulário e retornar um novo objeto
		Bairro obj = new Bairro();
		
		obj.setId(Utils.convertInt(txtId.getText()));	
		obj.setNome_bairro(txtNome.getText());
		obj.setCidade(comboCidade.getValue());
		obj.setEstados(comboEstados.getValue());
		
		return obj;
	}

	@FXML
	public void btCancelarAcao(ActionEvent evento) {
		Stage parentStage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
		parentStage.close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() { 
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtNome, 30);
		
		initializeComboCidade();
		initializeComboEstados();
	}
	
	private void initializeComboCidade() {
		Callback<ListView<Cidade>, ListCell<Cidade>> factory = lv -> new ListCell<Cidade>() {
			@Override
			protected void updateItem(Cidade item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome_cidade());
			}
		};
		comboCidade.setCellFactory(factory);
		comboCidade.setButtonCell(factory.call(null));
	}
	
	private void initializeComboEstados() {
		Callback<ListView<Estados>, ListCell<Estados>> factory = lv -> new ListCell<Estados>() {
			@Override
			protected void updateItem(Estados item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome_estado());
			}
		};
		comboEstados.setCellFactory(factory);
		comboEstados.setButtonCell(factory.call(null));
	}
		

	public void attDadosForm() { // responsável por pegar os dados dos Bairro e popular as caixas de texto (TextField) do formulário
		if(entidade == null) {
			throw new IllegalStateException("Entidade nula");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome_bairro());
		
		if(entidade.getCidade() == null) {
			comboCidade.getSelectionModel().selectFirst(); // define que o ComboBox estaja selecionado no 1° elemento 
		}
		else{
			comboCidade.setValue(entidade.getCidade()); // A Cidade que estiver associado com a Bairro vai para o comboBox
		}
		
		if(entidade.getEstados() == null) {
			comboEstados.getSelectionModel().selectFirst(); // define que o ComboBox estaja selecionado no 1° elemento 
		}
		else{
			comboEstados.setValue(entidade.getEstados()); // o Estado que estiver associado com a Bairro vai para o comboBox
		}
	}
	
	public void carregarObj() { // Responsável por chamar os Estados e carregar os Estados do banco de dados preenchendo a Lista "obsList" com esses Estados 
		if(cidade == null) {
			throw new IllegalStateException("CidadeServico nulo");
		}
		if(estado == null) {
			throw new IllegalStateException("EstadosServico nulo");
		}
		
		List<Cidade> cid = cidade.encontrarTudo(); // carrega as cidades do banco de dados
		List<Estados> est = estado.encontrarTudo(); // carrega os estados do banco de dados
		obsListCidade = FXCollections.observableArrayList(cid);
		obsListEstados = FXCollections.observableArrayList(est);
		comboCidade.setItems(obsListCidade);
		comboEstados.setItems(obsListEstados);
	}
}
