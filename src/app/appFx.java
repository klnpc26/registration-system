package app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class appFx extends Application {
	
	private static Scene mainScene;// guarda a referência no mainScene
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Principal.fxml"));
			ScrollPane scrollPane = loader.load();//role o conteúdo, deslocando a janela de visualização ou usando barras de rolagem
			
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Cadastramento de Formulários");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Scene getMainScene() {
		return mainScene;// pega a referência
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}