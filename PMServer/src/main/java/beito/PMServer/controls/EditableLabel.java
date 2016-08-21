package beito.PMServer.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
	author: beito123
*/

public class EditableLabel extends TextField {

	private Label timeLabel;

	public EditableLabel(){
		this("");
	}

	public EditableLabel(String text){
		super(text);
	}
}
