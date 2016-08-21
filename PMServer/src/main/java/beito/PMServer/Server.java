package beito.PMServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

import beito.PMServer.controllers.MainWindowController;
import beito.PMServer.model.AppSettings.Setting.Launch;
import beito.PMServer.utils.ErrorDump;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/*
	author: beito123
*/

public class Server {// TODO: 全体的コードの書き直し...

	private String dirPath;

	private String binPath;

	private String srcPath;

	private boolean isRunning = false;

	private boolean isStopping = false;// TODO: 必要性...

	private boolean isFStopping = false;

	private boolean restart = false;

	private Console console;

	private ServerThread serverThread;

	private ConsoleUpdater consoleUpdater;

	private static Server obj = null;

	public static Server getInstance(){
		return obj;
	}

	public Server(Console console){
		this(console, null, null, null);
	}

	public Server(Console console, String dirPath, String binPath, String srcPath){
		if(obj == null) obj = this;
		this.console = console;
		this.dirPath = dirPath;
		this.binPath = binPath;
		this.srcPath = srcPath;
	}

	public boolean isRunning(){
		return isRunning;
	}

	public boolean isStopping(){
		return isStopping;
	}

	public boolean isFStopping(){
		return isFStopping;
	}

	private void setRuning(boolean bool){
		isRunning = bool;
	}

	private void setStopping(boolean bool){
		isStopping = bool;
	}

	private void setFStopping(boolean bool){
		isFStopping = bool;
	}

	public String getPath(){
		return dirPath;
	}

	public String getSrcPath(){
		return srcPath;
	}

	public String getBinPath(){
		return binPath;
	}

	public void setDirPath(String path){
		dirPath = path;
	}

	public void setSrcPath(String path){
		srcPath = path;
	}

	public void setBinPath(String path){
		binPath = path;
	}

	public Console getConsole(){
		return console;
	}

	public boolean startServer(){
		if(!isRunning()){
			try{
				System.out.println("start...");

				if(!new File(dirPath).exists()){
					System.out.println("not exist! dir." + dirPath);
					Alert alert = new Alert(AlertType.ERROR,
							"指定された作業ディレクトリが見つかりませんでした!\n正しいパスを指定して下さい!", ButtonType.OK);
					alert.setTitle("Error");
					alert.getDialogPane().setHeaderText(null);
					alert.getDialogPane().getStylesheets().add("/styles/dialog.css");
					alert.showAndWait();
					return false;
				}
				if(!new File(srcPath).exists()){
					System.out.println("not exist! src." + srcPath);
					Alert alert = new Alert(AlertType.ERROR,
							"指定されたソース(Src, Phar)が見つかりませんでした!\n正しいパスを指定して下さい!", ButtonType.OK);
					alert.setTitle("Error");
					alert.getDialogPane().setHeaderText(null);
					alert.getDialogPane().getStylesheets().add("/styles/dialog.css");
					alert.showAndWait();
					return false;
				}
				if(!new File(binPath).exists()){
					System.out.println("not exist! bin." + binPath);
					Alert alert = new Alert(AlertType.ERROR,
							"指定されたBin(php.exe)が見つかりませんでした!\n正しいパスを指定して下さい!", ButtonType.OK);
					alert.setTitle("Error");
					alert.getDialogPane().setHeaderText(null);
					alert.getDialogPane().getStylesheets().add("/styles/dialog.css");
					alert.showAndWait();
					return false;
				}

				setRuning(true);
				setStopping(false);
				setFStopping(false);

				beito.PMServer.controllers.MainWindowController.getInstance().setRunMode(true);
				PMServer.getConsole().clearConsoleLog();

				//相対パス変換
				URI uriBase = new File(dirPath).toURI();
				String src = uriBase.relativize(new File(srcPath).toURI()).getPath();

				System.out.println("test:dir:" + dirPath + " src:" + src + " bin:" + binPath);

				String[] cmd = {binPath, "-c", dirPath + "/bin/php", src};
				//String[] cmd = {dirPath + "/bin/php/php.exe", "-c", dirPath + "/bin/php", "src/pocketmine/PocketMine.php"};
				ProcessBuilder builder = new ProcessBuilder(cmd);
				builder.redirectErrorStream(true);
				builder.directory(new File(dirPath));
				Process process = builder.start();

				console.addNotice("サーバーを開始します。");

				serverThread = new ServerThread(process);
				serverThread.start();

				consoleUpdater = new ConsoleUpdater();
				consoleUpdater.start();

				return true;
			}catch(IOException e){
				if(serverThread != null){
					serverThread.halt(true);
				}else if(consoleUpdater != null){
					consoleUpdater.interrupt();
				}
				handleStopped();
				ErrorDump.dump(e);
				e.printStackTrace();
			}
		}
		return false;
	}

	public void stopServer(){
		if(isRunning()){
			setStopping(true);
			sendCommand("stop");
			if(restart){
				startServer();
			}
		}
	}

	public void fstopServer(){
		fstopServer(false);
	}

	public void fstopServer(boolean force){
		if(isRunning()){
			setStopping(true);
			setFStopping(true);
			serverThread.halt(force);
			if(force) handleStopped();
		}
	}

	public void restartServer(){
		if(isRunning() && !isStopping()){
			restart = true;
			stopServer();
		}
	}

	public void saveServer(){
		if(isRunning()){
			sendCommand("save-all");
		}
	}

	private void handleStopped(){
		setRuning(false);
		setStopping(false);
		MainWindowController.getInstance().setRunMode(false);

		//ｓ
		console.addNotice("サーバーが停止しました。");
		console.updateConsoleLog();
	}

	public boolean sendCommand(String cmd){
		if(isRunning()){
			serverThread.sendCommand(cmd);
			return true;
		}
		return false;
	}

	public void setSettings(){
		Launch launch = SettingManager.getInstance().getSettings().getSetting().getLaunch();
		setDirPath(launch.getPMPath());
		setSrcPath(launch.getPharPath());
		if(launch.getIsUseSrc()){
			setSrcPath(launch.getSrcPath());
		}
		setBinPath(launch.getPHPPath());
	}

	private class ConsoleUpdater extends Thread {
		@Override
		public void run(){
			System.out.println("consoleUpdater.run");
			try{
				while(isRunning()){
					Platform.runLater(() -> PMServer.getConsole().updateConsoleLog());
					Thread.sleep(80);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println("consoleUpdate.stop");
		}
	}

	private class ServerThread extends Thread {

		private Process process;
		private InputStream is;
		private OutputStream os;

		private BufferedWriter writer;

		private boolean halt;

		public ServerThread(Process process){
			this.process = process;
			this.is = process.getInputStream();
			this.os = process.getOutputStream();
			halt = false;
		}

		public void sendCommand(String cmd){
			try{
				writer.write(cmd + "\n");
			}catch(IOException e){
				e.printStackTrace();
			}finally{
				try{
					writer.flush();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}

		@Override
		public void run(){
			try {
				java.lang.System.out.println("run...");

				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
				writer = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));

				String line;
				while ((line = reader.readLine()) != null && !line.trim().equals("--EOF--") && !halt) {
					System.out.println(line);
					console.addConsoleLog(PMServer.getConsole().toConsoleLog(line));
					Thread.sleep(50);
				}
				writer.flush();

				process.destroy();

				handleStopped();
				java.lang.System.out.println("stop");
			}catch(InterruptedException e){

			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

		public void halt(boolean force){
			halt = true;
			if(writer != null){
				try{
					writer.flush();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			if(force){
				if(process != null && process.isAlive()){
					process.destroy();
				}
				java.lang.System.out.println("fstop!");
				interrupt();
			}
		}
	}
}
