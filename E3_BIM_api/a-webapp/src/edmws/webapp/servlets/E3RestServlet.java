package edmws.webapp.servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class E3RestServlet extends EDMWSServlet {

	private String mWorkerClassName = "<UNSET>";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.mWorkerClassName = config.getInitParameter("e3.worker.classname");
		
		ServletContext context = config.getServletContext(); 
		Enumeration<String> pNames = context.getInitParameterNames();
		while(pNames.hasMoreElements()) {
			String p = pNames.nextElement();
			System.out.println("### " + this.getClass().getName() + " context param:" + p + "=" + context.getInitParameter(p));
		}
		pNames = config.getInitParameterNames();
		while(pNames.hasMoreElements()) {
			String p = pNames.nextElement();
			System.out.println("/// " + this.getClass().getName() + " config param:" + p + "=" + config.getInitParameter(p));
		}
		
		E3Logger.setDirectLogfileName(context.getInitParameter("e3.logger.filename"));
		E3Logger.setDirectConsoleOutput("true".equalsIgnoreCase(context.getInitParameter("e3.logger.consoleoutput")));
	}


	private static E3Logger logger = E3Logger.getLogger("E3ReposServlet");
	private static void log(int severity,String msg) {
		logger.log(severity, msg);
	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		this.doRest("DELETE", request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		this.doRest("GET", request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		this.doRest("POST", request, response);
	}
	public void doPut(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		this.doRest("PUT", request, response);
	}
	
	
	private void doRest(String restOp, HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		E3AccessUtil carrier = new E3AccessUtil(this, request, response);
		try {
			String path0 = request.getServletPath()/*"/REST"*/;
			String path1 = request.getPathInfo();/*"/AccessControl/login"*/
			// Detect access control
			//String jsonIn = carrier.getJsonIn();
			
			log(E3Logger.INFO,"restOP=" + restOp);
			log(E3Logger.INFO,"path0=" + path0);
			log(E3Logger.INFO,"path1=" + path1);
			log(E3Logger.INFO,"param=" + request.getParameter("model_name"));
			log(E3Logger.INFO,"multipart==" + Multipart.RequestParser.isMultipartContent(request));
			
			
			if ((path1 != null) && path1.startsWith("/AccessControl/")) {
				String ACMethod = path1.substring("/AccessControl/".length());
				E3EDMAccessFunctions accessFunctions = new E3EDMAccessFunctions(); 
				String accessServiceResult = accessFunctions.doEDMAccessFunction(ACMethod,carrier);
				if (accessServiceResult == null){
					// TODO: what does it mean then?
				}
				return;
			}
			if ((path1 != null) && path1.startsWith("/EDMWS/")) {
				String result = carrier.checkAuthorization();
				if( result == null) {
					// TODO: map to RESTServlet?! this.dkrQueryFunction(carrier);
					carrier.checkAutoLogout();
				}
				return;
			}
			
			E3ApiActions worker = (E3ApiActions) Class.forName(this.mWorkerClassName).newInstance();

			//E3BimApiActions worker = new E3BimApiActions();
			worker.doRest3(carrier, restOp);
			return;

		} catch (Exception e) {
			carrier.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, "Unknown error: ");
		}
	}

}
