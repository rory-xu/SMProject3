package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class starts the application
 * @author Rory Xu, Hassan Alfareed
 */
public class BankTellerMain extends Application {
	/**
	 * Determines what to display on initial launch
	 * @param stage The starting stage of the application
	 * @throws IOException
	 */
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("bankTellerView.fxml"));
		fxmlLoader.setController(new BankTellerController());
		Scene scene = new Scene(fxmlLoader.load(), 800, 800);
		stage.setTitle("Bank Teller");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	/**
	 * Launches the application
	 * @param args Not used
	 */
	public static void main(String[] args) {
		launch();
	}
}