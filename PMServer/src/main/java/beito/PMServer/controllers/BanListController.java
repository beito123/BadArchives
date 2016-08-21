package beito.PMServer.controllers;

import beito.PMServer.MainWindow;
import beito.PMServer.SettingManager;
import beito.PMServer.model.AppSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

/*
	author: beito123
*/

public class BanListController extends BaseController implements Initializable {

    @FXML
    private ComboBox<?> cmbSort;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField fidSearch;

    @FXML
    private Label labCount;

    @FXML
    private ListView<?> livBanList;

    @FXML
    private Button btnDel;

    @Override
    public String getTitle() {
        return "Banリスト";
    }

    @Override
    public String getFxmlPath() {
        return "./fxml/ban-list.fxml";
    }

    @Override
    public String getStylePath(){
        return "/styles/main.css";
    }

    @Override
    public Window getOwner(){
        return MainWindow.getInstance().getPrimaryStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onAddButton(ActionEvent event) {

    }

    @FXML
    public void onDelButton(ActionEvent event) {

    }

    @FXML
    public void onSearchInputFieldChange(ActionEvent event) {

    }

    @FXML
    public void onSortChange(ActionEvent event) {

    }
}
