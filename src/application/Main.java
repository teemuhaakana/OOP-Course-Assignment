package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{
	

	@Override
	//ladataan MenoOhjelma.fxml tiedosto ja asetetaan sen kuvaamalle ikkunalle otsikko
	//ja asetetaan ikkuna näkyväksi
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MenoOhjelma.fxml"));
			Scene scene = new Scene(root);
			//new Paivamaara() -oliolla saadaan tämän päivän päivämäärä
			primaryStage.setTitle("Menot " + new Paivamaara());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
