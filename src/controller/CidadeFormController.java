package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import JDBC.BdException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import listerner.MudancaDados;
import model.entidades.Estados;
import model.servicos.EstadosServico;
import util.Alerta;
import util.Restricoes;
import util.Utils;

public class CidadeFormController implements Initializable{ // A classe que EMITE o evento (subject)
	
	private Estados entidade;
	
	private EstadosServico servico;
	
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
	
	public void setEstados(Estados entidade) {
		this.entidade = entidade;
	}
	
	public void setEstadosServico(EstadosServico servico) {
		this.servico = servico; 
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
			Alerta.mostrarAlerta("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificaMudancaDados() { // notifica os dados alterados
		for(MudancaDados ouvinte : mudancaDados) {
			ouvinte.mudancaDeDados();
		}
	}

	private Estados getDadosForm() { // Responsável por pegar os dados que estão nas caixa de texto do formulário e retornar um novo objeto
		Estados obj = new Estados();
		
		obj.setId(Utils.convertInt(txtId.getText()));	
		obj.setNome_estado(txtNome.getText());
		obj.setUf(txtUf.getText());
		
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
		Restricoes.setTextFieldMaxLength(txtUf, 2);
	}
	
	public void attDadosForm() { // responsável por pegar os dados dos Estados e popular as caixas de texto (TextField) do formulário
		if(entidade == null) {
			throw new IllegalStateException("Entidade nula");
		}
		
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getNome_estado());
		txtUf.setText(entidade.getUf());
	}
}
