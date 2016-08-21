package beito.PMServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
	author: beito123
*/

@JsonIgnoreProperties(ignoreUnknown=true)
public class AppSettings {

	@JsonProperty("version")
	private String version;

	@JsonProperty("setting")
	private Setting setting;

	public String getVersion(){
		return version;
	}

	public Setting getSetting(){
		return setting;
	}

	@JsonIgnoreProperties(ignoreUnknown=true)
	public class Setting{

		@JsonProperty("launch")
		private Launch launch;

		public Launch getLaunch(){
			return launch;
		}

		@JsonIgnoreProperties(ignoreUnknown=true)
		public class Launch {
			@JsonProperty("PMPath")
			private String PMPath;

			@JsonProperty("PharPath")
			private String PharPath;

			@JsonProperty("PHPPath")
			private String PHPPath;

			@JsonProperty("IsUseSrc")
			private boolean IsUseSrc;

			@JsonProperty("SrcPath")
			private String SrcPath;

			public String getPMPath(){
				return PMPath;
			}

			public void setPMPath(String path){
				PMPath = path;
			}

			public String getPharPath(){
				return PharPath;
			}

			public void setPharPath(String path){
				PharPath = path;
			}

			public String getPHPPath(){
				return PHPPath;
			}

			public void setPHPPath(String path){
				this.PHPPath = path;
			}

			public boolean getIsUseSrc(){
				return IsUseSrc;
			}

			public void setIsUseSrc(boolean bool){
				IsUseSrc = bool;
			}

			public String getSrcPath(){
				return SrcPath;
			}

			public void setSrcPath(String path){
				SrcPath = path;
			}
		}
	}
}