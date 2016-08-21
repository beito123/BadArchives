package beito.PMServer;

import beito.PMServer.utils.MainLogger;

/*
	author: beito123
*/

public class PMServer {

	final public static String VERSION = "1.0.1";

	final public static String CONFIG_VERSION = "1.1";

	private static Server server;

	private static Console console;

	private static MainLogger logger;

	static {
		logger = new MainLogger();
		console = new Console();
		server = new Server(console);
		server.setSettings();
	}

	public static void init(){

	}

	public static Server getServer(){
		return server;
	}

	public static Console getConsole(){
		return console;
	}

	public static MainLogger getLogger(){
		return logger;
	}
}
