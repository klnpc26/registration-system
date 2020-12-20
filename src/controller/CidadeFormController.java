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
import model.entidades.Cidade;
import model.entidades.Estados;
import model.servicos.CidadeServico;
import model.servicos.EstadosServico;
import util.Alerta;
import util.Restricoes;
import util.Utils;

public class CidadeFormController implements Initializable{ // A classe que EMITE o evento (subject)
	
	private Cidade entidade;
	
	private CidadeServico servico;
	
	private EstadosServico estado;
	
	private List<MudancaDados> mudancaDados = new ArrayList<>();// a classe vai guardar uma lista de objetos interessados em receber o evento
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private ComboBox<Estados> comboEstados;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancelar;
	
	@FXML
	private ObservableList<Estados> obsList;
	
	public void setCidade(Cidade entidade) {
		this.entidade = entidade;
	}
	
	public void setServicos(CidadeServico c, EstadosServico e) {
		this.servico = c;
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

	private Cidade getDadosForm() { // Responsável por pegar os dados que estão nas caixa de texto do formulário e retornar um novo objeto
		Cidade obj = new Cidade();
		
		obj.setId(Utils.convertInt(txtId.getText()));	
		obj.setNome_cidade(txtNome.getText());
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
		
		initializeComboEstados();
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
		

	public void attDadosForm() { // responsável por pegar os dados dos Cidade e popular as caixas de texto (TextField) do formulário
		if(entidade == null) {
			throw new IllegalStateException("Entidade nula");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome_cidade());
		
		if(entidade.getEstados() == null) {
			comboEstados.getSelectionModel().selectFirst();
		}
		else{
			comboEstados.setValue(entidade.getEstados());
		}
	}
	
	public void carregarObj() {
		if(estado == null) {
			throw new IllegalStateException("EstadosServico nulo");
		}
		
		List<Estados> list = estado.encontrarTudo();
		obsList = FXCollections.observableArrayList(list);
		comboEstados.setItems(obsList);
	}
}
