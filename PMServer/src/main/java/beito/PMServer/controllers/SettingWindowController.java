package beito.PMServer.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import beito.PMServer.MainWindow;
import beito.PMServer.PMServer;
import beito.PMServer.SettingManager;
import beito.PMServer.model.AppSettings;
import beito.PMServer.model.AppSettings.Setting.Launch;
import beito.PMServer.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/*
	author: beito123
*/

public class SettingWindowController extends BaseController implements Initializable {

	@FXML
	private Button btnPm;

	@FXML
	private TextField fidPhar;

	@FXML
	private CheckBox chkSrc;

	@FXML
	private TextField fidSrc;

	@FXML
	private Button btnPhp;

	@FXML
	private Button btnPhar;

	@FXML
	private TextField fidPm;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnSerPropeties;

	@FXML
	private TextField fidPhp;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnSrc;

	@FXML
	private Button btnPmSetting;

	@FXML
	private TextField fidCmd;

	@Override
	public String getTitle(){
		return "設定";
	}

	@Override
	public String getFxmlPath(){
		return "/fxml/setting.fxml";
	}

	@Override
	public String getStylePath(){
		return "/styles/setting.css";
	}

	@Override
	public Window getOwner(){
		return MainWindow.getInstance().getPrimaryStage();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AppSettings settings = SettingManager.getInstance().getSettings();
		Launch launch = settings.getSetting().getLaunch();
		fidPm.setText(launch.getPMPath());
		fidPhp.setText(launch.getPHPPath());
		fidPhar.setText(launch.getPharPath());
		chkSrc.setSelected(launch.getIsUseSrc());
		if(launch.getIsUseSrc()){
			fidSrc.setText(launch.getSrcPath());
		}
		setIsUseSrc(chkSrc.isSelected());

		// TODO: 実装...
		fidCmd.setDisable(true);
		fidCmd.setText("未実装...");
	}

	@FXML
	public void onSaveButtonClick(){
		saveSettings();
		PMServer.getServer().setSettings();
		btnSave.getScene().getWindow().hide();
	}

	@FXML
	public void onCancelButtonClick(){
		btnCancel.getScene().getWindow().hide();
	}

	@FXML
	public void onPMSelectDialogButton(){
		File dir = Utils.showDirectorySelectDialog(stage);
		if(dir != null){
			fidPm.setText(dir.getAbsolutePath());
			checkMultiByteWarning(dir.getAbsolutePath());

			File file = new File(dir + "/bin/php/php.exe");
			if(file.exists()){
				fidPhp.setText(file.getAbsolutePath());
			}

			file = new File(dir + "/bin/php5/bin/php");
			if(file.exists()){
				fidPhp.setText(file.getAbsolutePath());
			}

			file = new File(dir + "/PocketMine-MP.phar");
			if(file.exists()){
				fidPhar.setText(file.getAbsolutePath());
			}

			file = new File(dir + "/src/pocketmine/PocketMine.php");
			if(file.exists()){
				fidSrc.setText(file.getAbsolutePath());
			}

			if(fidPhar.getText().length() == 0 && fidSrc.getText().length() > 0){
				setIsUseSrc(true);
				chkSrc.setSelected(true);
			}else{
				setIsUseSrc(false);
				chkSrc.setSelected(false);
			}
		}
	}

	@FXML
	public void onPharSelectDialogButton(){
		ExtensionFilter[] filters = {new ExtensionFilter("Pharファィル", "*.phar"), new ExtensionFilter("すべてのファィル", "*.*")};
		File file = Utils.showFileSelectDialog(stage, filters);
		if(file != null){
			fidPhar.setText(file.getAbsolutePath());
			checkMultiByteWarning(file.getAbsolutePath());
		}
	}

	@FXML
	public void onPhpSelectDialogButton(){
		ExtensionFilter[] filters = {new ExtensionFilter("php.exe", "php.exe", "php"), new ExtensionFilter("すべてのファィル", "*.*")};
		File file = Utils.showFileSelectDialog(stage, filters);
		if(file != null){
			fidPhp.setText(file.getAbsolutePath());
			checkMultiByteWarning(file.getAbsolutePath());
		}
	}

	@FXML
	public void onIsUseSrc(){
		setIsUseSrc(chkSrc.isSelected());
	}

	@FXML
	public void onSrcSelectDialogButton(){
		File dir = Utils.showDirectorySelectDialog(stage);
		if(dir != null){
			fidSrc.setText(dir.getAbsolutePath());
			checkMultiByteWarning(dir.getAbsolutePath());
		}
	}

	@FXML
	public void onServerSettingButtonClick(){

	}

	@FXML
	public void onPMSettingButtonClick(){

	}

	public void checkMultiByteWarning(String path){
		if(!path.matches("[0-9a-zA-Z-_\\\\/:\\.]+")){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告!");
			alert.getDialogPane().getButtonTypes().setAll(ButtonType.OK);
			alert.getDialogPane().setHeaderText("パスに不適切な文字が含まれています!");
			alert.getDialogPane().setContentText("パスに英数字以外の文字を含むと正常に動作しない可能性があります!\n英数字、アンダースコア、ドット(.)のみのパスを使用することを推奨します!");
			alert.getDialogPane().getStylesheets().add("/styles/dialog.css");
			alert.showAndWait();
		}
	}

	public void setIsUseSrc(boolean bool){
		if(bool){
			fidPhar.setStyle("-fx-background-color: #F3F3F3; -fx-border-color: #DBDBDB");
			fidSrc.setStyle("");
		}else{
			fidPhar.setStyle("");
			fidSrc.setStyle("-fx-background-color: #F3F3F3; -fx-border-color: #DBDBDB");
		}
	}

	public void saveSettings(){
		AppSettings settings = SettingManager.getInstance().getSettings();
		Launch launch = settings.getSetting().getLaunch();
		launch.setPMPath(fidPm.getText());
		launch.setPHPPath(fidPhp.getText());
		launch.setPharPath(fidPhar.getText());
		launch.setIsUseSrc(chkSrc.isSelected());
		launch.setSrcPath(fidSrc.getText());
		SettingManager.getInstance().save();
	}
}
