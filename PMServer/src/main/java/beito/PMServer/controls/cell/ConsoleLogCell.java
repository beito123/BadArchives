package beito.PMServer.controls.cell;

import beito.PMServer.ConsoleLog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/*
	author: beito123
*/

public class ConsoleLogCell extends ListCell<ConsoleLog> {

	private VBox cellContainer;

	private TextField text;

	private Label label;

	public ConsoleLogCell(){
		cellContainer = new VBox();
		text = new TextField();
		text.selectionProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.getStart() != 0 && newValue.getEnd() != 0){
				getListView().getSelectionModel().select(getIndex());
			}
		});
		cellContainer.getChildren().add(text);
	}

	@Override
	protected void updateItem(ConsoleLog item, boolean empty){
		super.updateItem(item, empty);
		if(empty){
			getStylesheets().clear();
			setText(null);
			setGraphic(null);
		}else{
			text.setText(item.getText());
			text.setEditable(false);
			text.getStyleClass().add("text");
			setStyle("-base-color:" + toRGBCode(item.getBaseColor()));
			getStylesheets().add("/styles/clog.css");
			setGraphic(cellContainer);
		}
	}

	public String toRGBCode(Color color){
		return String.format("#%02X%02X%02X",
			(int)(color.getRed() * 255),
			(int)(color.getGreen() * 255),
			(int)(color.getBlue() * 255)
		);
	}
}