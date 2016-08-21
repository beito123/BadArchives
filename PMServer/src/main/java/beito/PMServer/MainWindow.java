package beito.PMServer;

import java.io.File;
import java.io.IOException;

import beito.PMServer.utils.ErrorDump;
import beito.PMServer.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
	author: beito123
*/

public class MainWindow extends Application {

	private Stage primaryStage;

	private static MainWindow obj;

	public static MainWindow getInstance(){
		return obj;
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void init() throws Exception{
		obj = this;

		File file = new File("./cache");//skin
		if(!file.exists()){
			file.mkdir();
		}

		file = new File("./conf");
		if(!file.exists()){
			file.mkdir();
		}

			try {
				Utils.extractResource("/setting.json", "./conf/setting.dat", false);
			}catch(IOException e){
			ErrorDump.dump(e);
			e.printStackTrace();
		}

		new SettingManager("./conf/setting.dat");

		PMServer.init();


	}

	@Override
	public void start(Stage stage) throws Exception{
		primaryStage = stage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pmserver-2.fxml"));
		Parent root = loader.load();
		root.getStylesheets().addAll("/styles/main.css");

		stage.setTitle("PMServer");
		stage.setResizable(false);
		stage.getIcons().add(new Image("/icons/icon.png"));

		stage.setScene(new Scene(root, 590, 339));
		stage.show();
	}
}
