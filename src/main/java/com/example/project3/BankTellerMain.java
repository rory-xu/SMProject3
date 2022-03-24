package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BankTellerMain extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("bankTellerView.fxml"));
		fxmlLoader.setController(new Controller());
		Scene scene = new Scene(fxmlLoader.load(), 800, 800);
		stage.setTitle("Bank Teller");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}