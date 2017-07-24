package edmws.ws.model;

import org.json.JSONObject;

import edmws.webapp.exception.E3GeneralException;
import edmws.webapp.servlets.IQueryPath;

public class E3ResourceData {

	private String RestOP;
	public String getRestOP() {	return RestOP;}
	public void setRestOP(String restOP) {RestOP = restOP;	}

	private String URL;
	public String getURL() {return URL;	}
	public void setURL(String uRL) {URL = uRL;}

	private JSONObject mUrlArgs;
	public JSONObject getUrlArgs() {return mUrlArgs;}
	public void setUrlArgs(JSONObject urlArgs) {mUrlArgs = urlArgs;}

	private IQueryPath QueryPath;
	public IQueryPath getQueryPath() {return QueryPath;}
	public void setQueryPath(IQueryPath queryPath) {QueryPath = queryPath;}

	
	private JSONObject QueryArgs;
	public JSONObject getQueryArgs() {return QueryArgs;}
	public void setQueryArgs(JSONObject queryArgs) {QueryArgs = queryArgs;}
	
}