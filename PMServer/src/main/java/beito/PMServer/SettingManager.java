package beito.PMServer;

import beito.PMServer.model.AppSettings;
import beito.PMServer.utils.Setting;

/*
	author: beito123
*/

public class SettingManager {

	private static SettingManager obj;

	private Setting setting;

	public static SettingManager getInstance(){
		return obj;
	}

	public SettingManager(String filepath){
		this(new Setting(filepath));
	}

	public SettingManager(Setting setting){
		obj = this;
		this.setting = setting;
	}

	public void save(){
		setting.save();
	}

	public AppSettings getSettings(){
		return setting.getSettings();
	}
}
