package beito.PMServer.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import beito.PMServer.Console;
import beito.PMServer.ConsoleLog;
import beito.PMServer.MainWindow;
import beito.PMServer.PMServer;
import beito.PMServer.controls.cell.ConsoleLogCell;
import beito.PMServer.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;

/*
	author: beito123
*/

public class MainWindowController implements Initializable {

	@FXML private Button btnStart;

	@FXML private Button btnStop;

	@FXML private Button btnRestart;

	@FXML private Button btnFstop;

	@FXML private Button btnSave;

	@FXML private Button btnSetting;

	@FXML private Button btnInfo;

	@FXML private Button btnClose;

	@FXML private CheckBox chkSay;

	@FXML private TextField fidCmd;

	@FXML private Pane consolePane;

	@FXML private ListView<ConsoleLog> consoleList;

	private ObservableList<ConsoleLog> consoleRecords = FXCollections.observableArrayList();

	private boolean autoScroll = true;

	private static MainWindowController obj;

	public static MainWindowController getInstance(){
		return obj;
	}

	@Override
    public void initialize(URL location, ResourceBundle resources){
		obj = this;
		setRunMode(false);
		consoleList.setCellFactory(param -> new ConsoleLogCell());
		MainWindow.getInstance().getPrimaryStage().setOnCloseRequest(e -> onClose(e));

		//test
		addConsoleLog(new ConsoleLog("テスト test"));
		addConsoleLog(new ConsoleLog("テスト test", Console.ConsoleColor.NOTICE));
		addConsoleLog(new ConsoleLog("テスト test", Console.ConsoleColor.WARNING));
		addConsoleLog(new ConsoleLog("テスト test", Console.ConsoleColor.ERROR));
		addConsoleLog(new ConsoleLog("テスト test", Console.ConsoleColor.DEBUG));

	}

	public void onClose(WindowEvent e){
		if(PMServer.getServer().isRunning()){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("警告!");
			alert.setHeaderText("本当にサーバーを強制停止して終了しますか?");
			alert.setContentText("ワールドデータが破損する恐れがあります!");
			alert.getDialogPane().getButtonTypes().setAll(new ButtonType("キャンセル", ButtonData.CANCEL_CLOSE), ButtonType.OK);
			alert.getDialogPane().getStylesheets().add("/styles/dialog.css");
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){
				PMServer.getServer().fstopServer(true);
			}else{
				e.consume();
				return;
			}
		}
		Platform.exit();
		System.exit(0);
	}

	//ActionEvents...

	@FXML
	public void onStartButtonClick(){
		PMServer.getServer().startServer();
	}

	@FXML
	public void onStopButtonClick(){
		PMServer.getServer().stopServer();
	}

	@FXML
	public void onRestartButtonClick(){
		PMServer.getServer().restartServer();
	}

	@FXML
	public void onFstopButtonClick(){
		ButtonType[] buttonType = {new ButtonType("キャンセル", ButtonData.CANCEL_CLOSE), ButtonType.OK};
		ButtonType result = Utils.showDialog(AlertType.WARNING, "警告!", buttonType, "サーバーを強制に停止します!",
				"ワールドデータが破損する恐れがあります!\n通常停止に問題がある場合以外は使用しないでください!",
				"/styles/dialog.css", null);
		if(result != null && result == ButtonType.OK){
			System.out.println("fstop.ok...");
			PMServer.getServer().fstopServer();
		}else{
			System.out.println("fstop.cancel..");
		}
	}

	@FXML
	public void onSaveButtonClick(){
		PMServer.getServer().saveServer();
	}

	@FXML
	public void onSettingButtonClick(){
		SettingWindowController settingWindow = new SettingWindowController();
		settingWindow.show();
	}

	@FXML
	public void onInfoButtonClick(){
		ImageView image = new ImageView(getClass().getResource("/icons/icon-64.png").toString());
		image.setFitHeight(50);
		image.setFitWidth(50);
		ButtonType[] buttonType = {ButtonType.OK};
		Utils.showDialog(AlertType.INFORMATION, "情報", buttonType, null,
				"PMServer\nCopyright (c) 2015 beito123 All Rights Reserved.",
				"/styles/dialog.css", image);
	}

	@FXML
	public void onCloseButtonClick(){// TODO
		System.out.println("test");
	}

	@FXML
	public void onSendCommand(){
		if(PMServer.getServer().isRunning() && fidCmd.getText().length() != 0){
			String cmd = fidCmd.getText();
			if(chkSay.isSelected()) cmd = "say " + cmd;
			PMServer.getServer().sendCommand(cmd);
			System.out.println("send: " + cmd + " isSay: " + (chkSay.isSelected()? "true":"false"));
		}
		fidCmd.setText("");
	}

	@FXML
	public void onOpenBanList(){

	}

	@FXML
	public void onOpenBanIPList(){

	}

	@FXML
	public void onOpenWhiteList(){

	}

	@FXML
	public void onOpenRandomSelectWindow(){

	}

	@FXML
	public void onWhiteListActiveChange(){

	}

	//public function

	public void setRunMode(boolean bool){
		if(bool){
			btnStart.setDisable(true);
			btnStop.setDisable(false);
			btnRestart.setDisable(false);
			btnFstop.setDisable(false);
			btnSave.setDisable(false);
		}else{
			btnStart.setDisable(false);
			btnStop.setDisable(true);
			btnRestart.setDisable(true);
			btnFstop.setDisable(true);
			btnSave.setDisable(true);
		}
	}

	public boolean getAutoScroll(){
		return autoScroll;
	}

	public void setAutoScroll(boolean bool){
		autoScroll = bool;
	}

	public void updateScroll(){
		int size = consoleRecords.size();
		if(size >= 8){//
			consoleList.scrollTo(size - 1);
		}
	}

	//

	public void addConsoleLog(ConsoleLog log){
		consoleRecords.add(log);
		consoleList.setItems(consoleRecords);
	}

	public void addConsoleLogs(ArrayList<ConsoleLog> logs){
		for(int i = 0;i < logs.size();i++){
			consoleRecords.add(logs.get(i));
		}
		consoleList.setItems(consoleRecords);
		if(getAutoScroll() && consoleRecords.size() == 8){
			updateScroll();
		}
	}

	public void clearConsoleLog(){
		consoleRecords.clear();
		consoleList.setItems(consoleRecords);
	}
}
