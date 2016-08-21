package beito.PMServer.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import beito.PMServer.PMServer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/*
	author: beito123
*/

public class ErrorDump {

	private String path;

	private PrintWriter pw;

	private Exception exception;

	public static void dump(Exception exception){
		ErrorDump dump = new ErrorDump(exception);
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error!");
		alert.setHeaderText("エラーが発生しました!");
		alert.setContentText(dump.getPath() + " を生成しました。\nErrorDumpを制作者へ提供していただけると幸いです。");
		alert.getDialogPane().getButtonTypes().setAll(ButtonType.OK);
		alert.getDialogPane().getStylesheets().add("/styles/dialog.css");
		alert.showAndWait();
	}

	public ErrorDump(Exception exception){
		this.exception = exception;
		ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));//JST
		path = "ErrorDump_" + Utils.getFormatedTime(time, "yyyy-MM-dd_HH.mm.ss") + ".log";

		try{
			File file = new File("./" + path);
			if(!file.exists()){
				file.createNewFile();
			}
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));

			addLine("PMServer Error Dump beta " + Utils.getFormatedTime(time, "yyyy/MM/dd HH:mm:ss") + " (JST)");
			addLine();
			baseCrash();
			extraData();
			//TODO: add encode data (json/base64)
			addLine("ErrorDumpEnd.");
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(pw != null){
				pw.close();
			}
		}
	}

	private void baseCrash(){
		if(pw == null || exception == null) return;
		addLine("Error: " + exception.toString());
		addLine();
		addLine("PMServer version: " + PMServer.VERSION + " (Config: " + PMServer.CONFIG_VERSION + ")");
		addLine("JavaVersion: " + System.getProperty("java.version"));
		addLine("OS: " + System.getProperty("os.name"));
		addLine();
		addLine("Backtrace:");
		int back = 0;
		if (exception.getCause() != null){
			for (StackTraceElement ste : exception.getCause().getStackTrace()){
				addLine("#" + back + ": "+ ste.toString());
				++back;
			}
		}else{
			for (StackTraceElement ste : exception.getStackTrace()){
				addLine("#" + back + ": "+ ste.toString());
				++back;
			}
		}
		addLine();
	}

	private void extraData(){
		addLine("extraData:");
		addLine("settingdata.setting.dat:");
		File file = new File("./conf/setting.dat");
		if(file.exists()){
			BufferedReader br = null;
			try{
				br = new BufferedReader(new FileReader(file));
				String line;
				int count = 0;
				while((line = br.readLine()) != null){
					addLine("#" + count + ": " + line);
					++count;
				}
				addLine("end.");
			}catch(IOException e){
				addLine("Failed to read file " + file.getPath());
				e.printStackTrace();
			}finally{
				try {
					if(br != null){
						br.close();
					}
				}catch(IOException e){
					addLine("Failed to close file " + file.getPath());
					e.printStackTrace();
				}
			}
		}else{
			addLine(file.getPath() + " file not found");
		}
		addLine();
	}

	public String getPath(){
		return path;
	}

	public void add(String str){
		if(pw != null){
			pw.print(str);
		}
	}

	public void addLine(){
		addLine("");
	}

	public void addLine(String line){
		if(pw != null){
			pw.println(line);
		}
	}
}
