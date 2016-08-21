package beito.PMServer.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beito.PMServer.model.AppSettings;

/*
	author: beito123
*/

public class Setting {

	private ObjectMapper mapper;

	private AppSettings settings;

	private String filepath;

	public Setting(String filepath){
		this.filepath = filepath;
		try{
			File file = new File(filepath);
			if(!file.exists()){
				file.createNewFile();
			}
			mapper = new ObjectMapper()
					.enable(SerializationFeature.INDENT_OUTPUT);//debug
			settings = mapper.readValue(file, AppSettings.class);
		}catch(JsonProcessingException e){
			ErrorDump.dump(e);
			e.printStackTrace();
		}catch(IOException ex){
			ErrorDump.dump(ex);
			ex.printStackTrace();
		}
	}

	public void save(){
		try{
			File file = new File(filepath);
			if(!file.exists()){
				file.createNewFile();
			}
			mapper.writeValue(file, settings);
		}catch(IOException e){
			ErrorDump.dump(e);
			e.printStackTrace();
		}
	}

	public AppSettings getSettings(){
		return settings;
	}
}
