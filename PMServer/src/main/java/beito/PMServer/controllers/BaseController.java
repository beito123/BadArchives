package beito.PMServer.controllers;

import java.io.IOException;

import beito.PMServer.utils.ErrorDump;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/*
	author: beito123
*/

public abstract class BaseController {

	protected Parent root;

	protected Stage stage;

	final public void show(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(getFxmlPath()));

		try{
			root = loader.load();
			if(loader.getController() == null){
				loader.setController(this);
			}

			Scene scene = new Scene(root);
			if(getStylePath() != null){
				scene.getStylesheets().addAll(getStylePath());
			}

			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(getTitle());
			stage.setResizable(false);
			stage.getIcons().add(new Image(getIconPath()));
			if(getOwner() != null){
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.initOwner(getOwner());
			}

			stage.show();
		}catch(Exception e){
			ErrorDump.dump(e);
			e.printStackTrace();
		}
	}

	abstract public String getTitle();

	abstract public String getFxmlPath();

	public String getStylePath(){
		return null;
	}

	public Window getOwner(){
		return null;
	}

	public String getIconPath(){
		return "/icons/icon.png";
	}
}
