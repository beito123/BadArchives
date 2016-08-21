package beito.PMServer.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/*
	author: beito123
*/

public class MainLogger {

	final Logger logger;

	public MainLogger(){
		logger = Logger.getLogger(getClass().getName());
		logger.log(Level.INFO, "test");
	}

	public void fine(String msg){//詳細レベル(高)
		logger.log(Level.FINE, msg);
	}

	public void finer(String msg){//詳細レベル(中)
		logger.log(Level.FINER, msg);
	}

	public void finest(String msg){//詳細レベル(低)
		logger.log(Level.FINEST, msg);
	}

	public void config(String msg){//設定/構成
		logger.log(Level.CONFIG, msg);
	}

	public void info(String msg){//情報
		logger.log(Level.INFO, msg);
	}

	public void warning(String msg){//警告
		logger.log(Level.WARNING, msg);
	}

	public void severe(String msg){//致命的/重大
		logger.log(Level.SEVERE, msg);
	}

	public void outputErrorLog(Exception ex){
		
	}
}
