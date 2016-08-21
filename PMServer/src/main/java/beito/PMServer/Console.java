package beito.PMServer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import beito.PMServer.controllers.MainWindowController;
import javafx.application.Platform;
import javafx.scene.paint.Color;

/*
	author: beito123
*/

public class Console {

	private ArrayList<ConsoleLog> log = new ArrayList<ConsoleLog>();

	private Pattern logPattern = Pattern.compile("\\[.+?\\]");

	public Console(){

	}

	public void addNotice(String text){
		addConsoleLog(new ConsoleLog(text, ConsoleColor.NOTICE));
	}

	public void addConsoleLog(String text){
		addConsoleLog(new ConsoleLog(text));
	}

	public void addConsoleLog(String text, Color color){
		addConsoleLog(new ConsoleLog(text, color));
	}

	public void addConsoleLog(ConsoleLog clog){
		log.add(clog);
	}

	public void updateConsoleLog(){
		Platform.runLater(()-> {
			beito.PMServer.controllers.MainWindowController.getInstance().addConsoleLogs(log);
			log.clear();
		});
	}

	public void clearConsoleLog(){
		log.clear();
		MainWindowController.getInstance().clearConsoleLog();
	}

	public ConsoleLog toConsoleLog(String text){// TODO: Color
		int index = text.indexOf("]: ");
		if(index != -1){
			String logText = text.substring(index + 3);

			Matcher matcher = logPattern.matcher(text);
			List<String> list = new ArrayList<String>();
			while(matcher.find()){
				list.add(matcher.group());
			}
			String time = list.get(0);// TODO
			Color baseColor = ConsoleColor.getColorByLogType((list.get(1)).substring(1, list.get(1).length() - 1));
			return new ConsoleLog(logText, baseColor);
		}
		return new ConsoleLog(text);
	}

	//

	public static class ConsoleColor {
		public final static Color BLACK = Color.BLACK;

		public final static Color DARK_BLUE = Color.DARKBLUE;

		public final static Color DARK_GREEN = Color.DARKGREEN;

		public final static Color DARK_AQUA = Color.DARKCYAN;

		public final static Color DARK_RED = Color.DARKRED;

		public final static Color DARK_PURPLE = Color.DARKMAGENTA;

		public final static Color GOLD = Color.GOLD;

		public final static Color GRAY = Color.GRAY;

		public final static Color DARK_GRAY = Color.DARKGRAY;

		public final static Color BLUE = Color.BLUE;

		public final static Color GREEN = Color.GREEN;

		public final static Color AQUA = Color.AQUA;

		public final static Color RED = Color.RED;

		public final static Color LIGHT_PURPLE = Color.VIOLET;

		public final static Color YELLOW = Color.YELLOW;

		public final static Color WHITE = Color.WHITE;

		//

		public final static Color DEBUG = GRAY;

		public final static Color INFO = WHITE;

		public final static Color NOTICE = AQUA;

		public final static Color WARNING = YELLOW;

		public final static Color ERROR = DARK_RED;

		public final static Color CRITICAL = RED;

		public final static Color ALERT = RED;

		public final static Color EMERGENCY = RED;

		public static Color getColorByLogType(String type){
			Color color = INFO;//info
			System.out.println("test." + type.toLowerCase());
			switch(type.toLowerCase()){
				case "info":
					color = INFO;
					break;
				case "debug":
					color = DEBUG;
					break;
				case "notice":
					color = NOTICE;
					break;
				case "warning":
					color = WARNING;
					break;
				case "error":
					color = ERROR;
					break;
				case "critical":
					color = CRITICAL;
					break;
				case "alert":
					color = ALERT;
					break;
				case "emergency":
					color = EMERGENCY;
					break;
			}
			return color;
		}
	}
}
