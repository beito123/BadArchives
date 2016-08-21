package beito.PMServer;

import javafx.scene.paint.Color;

/*
	author: beito123
*/

public class ConsoleLog {
	private String text;

	private Color baseColor = Color.WHITE;

	public ConsoleLog(String text){
		this(text, Color.WHITE);
	}

	public ConsoleLog(String text, Color baseColor){
		this.text = text;
		this.baseColor = baseColor;
	}

	public String getText(){
		return text;
	}

	public Color getBaseColor(){
		return baseColor;
	}

	public void setText(String text){
		this.text = text;
	}

	public void setBaseColor(Color color){
		this.baseColor = color;
	}
}
