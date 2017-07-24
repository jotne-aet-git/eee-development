package edmws.webapp.servlets;

import org.json.JSONObject;

import edmws.webapp.exception.E3GeneralException;
import edmws.ws.model.E3ResourceData;

public interface IE3ResourcePath {
	/**
	 * 
	 * @param restOp: "GET" "POST" "DELETE" "PUT"
	 * @param urlArguments: URL?<arg1=val1><&arg2=val2>....
	 * @return
	 * @throws E3GeneralException
	 */
	public abstract JSONObject analyzeResource(E3ResourceData e3data, IE3JSONSource jsonBodySource)
			throws E3GeneralException;


}