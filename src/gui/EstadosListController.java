package gui;

import java.net.URL;
import java.util.ResourceBundle;

import app.appFx;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Estados;
import model.servicos.EstadosServico;

public class EstadosListController implements Initializable{
	
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
	
	@FXML
	public void acaoBotaoNovo() {
		
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
	
	
}
