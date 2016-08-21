package beito.PMServer.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import beito.PMServer.PMServer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

/*
	author: beito123
*/

public class Utils {

	public final static String OS_WIN = "win";

	public final static String OS_LINUX = "linux";

	public final static String OS_MAC = "mac";

	public final static String OS_UNKNOWN = "unknown";

	public final static int VERSION_OLD = -1;

	public final static int VERSION_EQUAL = 0;

	public final static int VERSION_NEW = 1;

	public static String getOS(){
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.startsWith("windows")){
			return OS_WIN;
		}else if(osName.startsWith("linux")){
			return OS_LINUX;
		}else if(osName.startsWith("mac")){
			return OS_MAC;
		}
		return OS_UNKNOWN;
	}

	public static boolean isWin(){
		return getOS() == OS_WIN;
	}

	public static boolean isLinux(){
		return getOS() == OS_LINUX;
	}

	public static boolean isMac(){
		return getOS() == OS_MAC;
	}

	public static boolean extractResource(String filepath) throws IOException{
		return extractResource(filepath, new File(filepath).getName(), false);
	}

	public static boolean extractResource(String filename, String savepath, boolean replace) throws IOException{
		if(new File(savepath).exists() && !replace){
			return false;
		}
		InputStream is = null;
		OutputStream os = null;
		try{
			is = Utils.class.getResourceAsStream(filename);
			if(is == null){
				throw new Exception("Cannot get resource \"" + filename + "\" from Jar file.");
			}
			int readBytes;
			byte[] buffer = new byte[4096];
			os = new FileOutputStream(savepath);
			while((readBytes = is.read(buffer)) > 0){
				os.write(buffer, 0, readBytes);
			}
			return true;
		}catch(Exception e){
			ErrorDump.dump(e);
			e.printStackTrace();
		}finally{
			is.close();
			os.close();
		}
		return false;
	}

	public static int compareVersion(String oldver, String newver){//
		int oldv = Integer.parseInt(oldver.replace(".", ""));
		int newv = Integer.parseInt(newver.replace(".", ""));
		if(newv > oldv){
			return VERSION_OLD;
		}else if(oldv > newv){
			return VERSION_NEW;
		}else{
			return VERSION_EQUAL;
		}
	}

	public static File showFileSelectDialog(Window window){
		return showFileSelectDialog(window, null);
	}

	public static File showFileSelectDialog(Window window, ExtensionFilter[] filters){
		FileChooser fc = new FileChooser();
		fc.setTitle("ファイルの選択");
		if(filters != null){
			fc.getExtensionFilters().addAll(filters);
		}
		File file = fc.showOpenDialog(window);
		return file;
	}

	public static File showDirectorySelectDialog(Window window){
		DirectoryChooser fc = new DirectoryChooser();
		fc.setTitle("ディレクトリの選択");
		File dir = fc.showDialog(window);
		return dir;
	}

	public static String getFormatedTime(String timezone, String format){
		return getFormatedTime(ZonedDateTime.now(ZoneId.of(timezone)), format);
	}

	public static String getFormatedTime(ZonedDateTime time, String format){
		return DateTimeFormatter.ofPattern(format).format(time);
	}

	public static ButtonType showDialog(Alert.AlertType type, String title, ButtonType[] buttonTypes, String headerText,
			String contentText, String style, ImageView image){
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.getDialogPane().setHeaderText(headerText);
		alert.getDialogPane().setContentText(contentText);
		if(style != null) {
			alert.getDialogPane().getStylesheets().add(style);
		}
		if(buttonTypes != null){
			alert.getDialogPane().getButtonTypes().setAll(buttonTypes);
		}
		if(image != null){
			alert.setGraphic(image);
		}
		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/icons/icon.png"));
		Optional<ButtonType> result = alert.showAndWait();
		if(result.isPresent()){
			return result.get();
		}
		return null;
	}
}
