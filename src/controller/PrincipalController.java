package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import app.appFx;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.servicos.EstadosServico;

public class PrincipalController implements Initializable{
	
	@FXML
	private MenuItem menuItemEstados;
	
	@FXML
	private MenuItem menuItemCidade;
	
	@FXML
	private MenuItem menuItemAjuda;
	
	@FXML
	public void acaoEstados() {
		loadView("/view/EstadosList.fxml", (EstadosListController control) -> {
			control.setEstadosServico(new EstadosServico());
			control.atualizaTabela();
		});
	}
	
	@FXML
	public void acaoCidade() {
		
	}
	
	@FXML
	public void acaoAjuda() {
		loadView("/view/Sobre.fxml", x -> {});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));//abre uma tela
			VBox newVBox = loader.load();//carregar a view
			
			Scene mainScene = appFx.getMainScene();// pega a referência do Scene
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();// pega uma referência para o Vbox que esta na janela principal
			
			Node mainMenu = mainVBox.getChildren().get(0);// recupera o primeiro filho do Vbox da janela principal
			mainVBox.getChildren().clear();// limpa todos os filhos do mainVbox
			mainVBox.getChildren().add(mainMenu);// adiciona mainMenu
			mainVBox.getChildren().addAll(newVBox.getChildren());// adiciona os filhos do newVbox
			
			T controller = loader.getController();// retorna o controlador do tipo que eu chamar no loadView, exemplo: EstadosListContrroller
			initializingAction.accept(controller);// executa a funções que passar como argumento no loadView
		}
		catch (IOException e) {
			
		}
	}	
}
