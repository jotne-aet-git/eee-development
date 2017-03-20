	package edmws.bimapi.test;

import org.json.JSONObject;

public class E3TestArgs {
	protected String serverUrl = "";
	protected String baseUrl = "";//"/ifc-api/0.5/" + E3IfcApiResourcePath.MR_IFCMODEL;
	protected JSONObject urlArgs = null;
	protected JSONObject bodyArgs = null;
	protected String restOp;
	
	// URL components
	
	private String model_guid;
	public String getModelGuid() {return model_guid;}
	public void setModelGuid(String model_guid) {this.model_guid = model_guid;}

	private String object_type;
	private String object_guid;
	private String sub_type;
	private String object_function;
	
	// file components
	
	private String _file_input =  null;
	public String get_file_input() {return _file_input;}
	public void set_file_input(String _file_input) {this._file_input = _file_input;}

	public E3TestArgs(String restOp,String baseUrl) {
		this.restOp = restOp;
		this.baseUrl = baseUrl;
	}

	public void setServerUrl(String url) {
		this.serverUrl = url;
	}

	

	public void setIfcType(String ifcType,String globalid) {
		this.object_type = ifcType;
		this.object_guid = globalid;
	}

	public void setIfcSubType(String subType) {
		this.sub_type = subType;
	}

	public void setIfcFunction(String func) {
		this.object_function = func;
	}

	public String getUrl() {
		String url = this.serverUrl + this.baseUrl;
		if(model_guid != null) url += "/" + model_guid;
		if(object_type != null) url += "/" + object_type;
		if(object_guid != null) url += "/" + object_guid;
		if(sub_type != null) url += "/" + sub_type;
		if(object_function != null) url += "/" + object_function;
		char lim = '?';
		if(this.urlArgs != null) {
			for(String key : this.urlArgs.keySet()) {
				url += lim + key + '=' + this.urlArgs.getString(key);
				lim = '&';
			}
		}
		url += lim + "EDMSESSIONID=AUTO_LOGIN"; 
		return url;
	}
}
