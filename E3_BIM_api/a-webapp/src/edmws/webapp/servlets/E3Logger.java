package edmws.webapp.servlets;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Gateway to log, all logging should go through this to enable change of log system.
 * @author aet
 *
 */
public class E3Logger {

	public static final int FATAL = 0; // not in SLF4J?
	public static final int ERROR = 1; 
	public static final int WARNING = 2; 
	public static final int INFO = 3;
	public static final int DEBUG = 4; 
	public static final int TRACE = 5; // only SLF4J? 


	private Logger m_logger;
	private String name;
	
	public String getName() {
		return name;
	}

	private E3Logger(String name) {
		this.name = name;
		// log4j: this.m_logger = LogManager.getLogger(this.name);
		this.m_logger = LoggerFactory.getLogger(this.name);
	}
	
	public static E3Logger getLogger(String name) {
		return new E3Logger(name);
	}

	
	
	private static boolean sDirectConsoleOutput4 = false;
	public static void setDirectConsoleOutput(boolean b) {
		sDirectConsoleOutput4 = b;
	}
	
	private static String sDirectLogfileName4 = "";
	private static PrintWriter sPrintWriter4 = null;
	public static void setDirectLogfileName(String s) {
		try {
			if( s == null) s = "";
			if(!s.equals(sDirectLogfileName4)) {
				if(sPrintWriter4 != null) {
					System.out.println("### Closing E3 log to:" + sDirectLogfileName4);
					sPrintWriter4.close();
					sPrintWriter4 = null;
				}
				sDirectLogfileName4 = s;
				if(!s.equals("")){
					System.out.println("### Initializing E3 log to:" + s);
					File f = new File(s);
					sPrintWriter4 = new PrintWriter(f);
				}
			}
		}
		catch(Exception ex) {
			System.err.println("*** E3Logger exception:" + ex.toString());
		}
	}

/*SLF4J*/

	public void log(int severity,String msg) {
		if ((sDirectConsoleOutput4) || (sPrintWriter4 != null)){
			String timeS = getE3DateTime();
			if (sDirectConsoleOutput4) {
				System.out.println(timeS + " " + name + " E3Log(" + severity + "): " + msg); 
			}
			if(sPrintWriter4 != null) {
				sPrintWriter4.println(timeS + " " + name + " E3Log(" + severity + "): " + msg);
				sPrintWriter4.flush();
			}
		}
		switch(severity) {
			case FATAL:
			case ERROR:
				this.m_logger.error(msg);
				break;
			case WARNING:
				this.m_logger.warn(msg);
				break;
			case INFO:
				this.m_logger.info(msg);
				break;
			case DEBUG:
				this.m_logger.debug(msg);
				break;
			default:
				this.m_logger.info(msg);
				break;
		}
	}
	
	
	private static String twodigits(int i) {
		String s = Integer.toString(i);
		if (i < 10) s = "0" + s;
		return s;
	}
	
	private static String threedigits(int i) {
		String s = Integer.toString(i);
		if (i < 100) s = "0" + s;
		if (i < 10) s = "0" + s;
		return s;
	}
	
	public static String getE3DateTime(){
		Calendar cd = Calendar.getInstance();
		String timeS = "" + cd.get(Calendar.YEAR) 
					+ "-" + twodigits(cd.get(Calendar.MONTH))
					+ "-" + twodigits(cd.get(Calendar.DATE))
					+ " " + twodigits(cd.get(Calendar.HOUR))
					+ ":" + twodigits(cd.get(Calendar.MINUTE))
					+ ":" + twodigits(cd.get(Calendar.SECOND))
					+ "." + threedigits(cd.get(Calendar.MILLISECOND));
		return timeS;
	}
	

}
