package edmws.bimapi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import org.json.JSONObject;

import edmws.webapp.servlets.E3Logger;

public class E3TestWebRequest {

	private static E3Logger logger = E3Logger.getLogger(E3TestWebRequest.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);			
	}
	
	private final String USER_AGENT = "Mozilla/5.0";
	private final String MULTIPART_BOUNDARY = "ZeBoundary_3777";
	private final String MULTIPART_CHARSET = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()

	String multipartBoundary = MULTIPART_BOUNDARY;
	String multipartCRLF = "\r\n"; // Line separator required by multipart/form-data.
	String multipartCharset = MULTIPART_CHARSET;

	
	HttpURLConnection con;
		StringBuffer response = new StringBuffer();
		
		private void doConnect(String url) throws Exception{
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			
		}
	// HTTP GET request
// 			String url = "http://www.google.com/search?q=mkyong";

		public  String sendGet00(E3TestArgs args ) throws Exception {
			doConnect(args.getUrl());

			con.setRequestMethod(args.restOp);
			con.setRequestProperty("User-Agent", USER_AGENT);
			//con.setDoOutput(true);

			log(E3Logger.DEBUG,"\nSending '" + args.restOp + "' request to URL : " + args.getUrl());
			int responseCode = con.getResponseCode();
			log(E3Logger.DEBUG,"Response Code : " + responseCode);

			readResponse();
			printResponse();
			return response.toString();
		}
		
		// HTTP POST request
		// String url = "https://selfsolve.apple.com/wcResults.do";
		public  String sendGet(E3TestArgs args,Writer wr) throws Exception {
			boolean useMultiPart = false;
			String urlArgString = args.getUrl(); 
			if((args.bodyArgs != null) && args.get_file_input() != null){
				for(String s: JSONObject.getNames(args.bodyArgs)) {
					urlArgString = urlArgString + "&" + s + "=" + args.bodyArgs.getString(s);
				}
				useMultiPart = true;
			}

			doConnect(urlArgString);

			con.setRequestMethod(args.restOp);
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			log(E3Logger.DEBUG,"Sending '" + args.restOp + "' request to URL : " + args.getUrl());

			if( 	("POST".equals(args.restOp)) 
				|| 	("DELETE".equals(args.restOp))
			  ){
				con.setDoOutput(true);
				con.setRequestMethod(args.restOp);
				//if((args.bodyArgs != null) && args.get_file_input() != null){
				if(useMultiPart){
					String parameters = args.bodyArgs.toString();
					log(E3Logger.DEBUG,"Post parameters, multipart : " + parameters);
					this.multipartMarkStart();
					this.multipartText(parameters);
					log(E3Logger.DEBUG,"Multipart input file (text) : " + args.get_file_input());
					File textFile = new File(args.get_file_input());
					this.multipartFile(textFile, null);
				//    con.setRequestProperty("Content-Length",		        Integer.toString(urlParameters.getBytes().length));
					this.multipartMarkCompleted();
				} 
				else if(args.bodyArgs != null) 
				{
					String urlParameters = args.bodyArgs.toString();//"sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
					log(E3Logger.DEBUG,"Post parameters(96) : " + urlParameters);
				//    con.setRequestProperty("Content-Length",		        Integer.toString(urlParameters.getBytes().length));
					this.singlePartText(urlParameters);
				}
				else if(args.get_file_input() != null)
				{
					File textFile = new File(args.get_file_input());
					log(E3Logger.DEBUG,"Input file : " + args.get_file_input());
					this.singlePartFileUpload(textFile, null);
				}
			}

			int responseCode = con.getResponseCode();
			log(E3Logger.DEBUG,"Response Code : " + responseCode);
			if(200 == responseCode) {
				if (wr != null) {
					log(E3Logger.DEBUG,"Writing response data to file");			
					readResponse(wr);					
				} else {
					readResponse();
					printResponse();
				}
			} else {
				handleErrResponse(responseCode);
			}
			
			return response.toString();
		}

		
		private void readResponse(Writer writer) throws Exception {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
			    writer.write (inputLine);
			}
			in.close();
		}
		
		private void readResponse() throws Exception {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}

		protected void printResponse() {
			//print result
			log(E3Logger.DEBUG,"Response Data:" + response.toString());			
		}

		protected void handleErrResponse(int code) throws Exception {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			log(E3Logger.WARNING,response.toString());
			
		}

		// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
		void singlePartText(String param) throws Exception {
//			String boundary = MULTIPART_BOUNDARY;
//			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			String charset = MULTIPART_CHARSET;
		    OutputStream output = con.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
			if(param != null) {
			    writer.append(param).flush();
			}
		}		
		
		// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
		void singlePartFileUpload(File textFile,File binaryFile) throws Exception {
			String charset = MULTIPART_CHARSET;
		    OutputStream output = con.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
		    // Send text file.
			if (textFile != null) {
			    Files.copy(textFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.flush(); // CRLF is important! It indicates end of boundary.
			}
		    // Send binary file.
			if(binaryFile != null) {
			    Files.copy(binaryFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.flush(); 
			}
		}
		

		/*
DKR:
	Regarding "The API as defined now uses files on upper level, and since clients are applications, not browsers, it's probably OK" - 
	I just want to repeat that you are now using multipart/form-data in your test client, and this is want expected by Multipart class.
	The difference is very small, in form-data you have (note - 'Content-Disposition' is required and NEED to be 'form-data'):
	  Content-Disposition: form-data; name="textFile"; filename="0000-Referansebygg.ifc"
	  Content-Type: text/plain; charset=UTF-8
	In multipart/mixed you have ("Content-Disposition" is optional and can be ONLY 'inline' or 'attachment' - exactly as in emails):
	  Content-Type: text/plain; charset=UTF-8
	ALL other stuff/content is THE SAME.
	BUT above means that code which expects 'form-data' will fail if it look for "Content-Disposition: form-data;  .....  " and can not not find it.
	I hope that clarifies difference between those 2 and why it's not 100% related to "... uses files on upper level"
	
DKR:
	And one addition - some modern browsers allow selecting multiple files into ONE input form field - this will result in NESTED multiparts:  
	you will have one item in form-data with name of that fields and INSIDE, there will be nested multipart/mixed  encoding containing 
	MULTIPLE files - and this is also supported I think by Multipart class.

		 */
		
		
		
		// ref http://http://stackoverflow.com/questions/913626/what-should-a-multipart-http-request-with-multiple-files-look-like
		private void multipartMarkStart() throws Exception {
			con.setRequestProperty("Content-Type", "multipart/mixed; boundary=" + multipartBoundary);
			// don't know content length :(
		}		
		
		// ref http://http://stackoverflow.com/questions/913626/what-should-a-multipart-http-request-with-multiple-files-look-like
		void multipartText(String param) throws Exception {
		    OutputStream output = con.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, multipartCharset), true);
			writer.append("--" + multipartBoundary).append(multipartCRLF).flush();				
		    writer.append("Content-Disposition: form-data; name=\"param\"").append(multipartCRLF);
		    writer.append("Content-Type: text/plain; charset=" + multipartCharset).append(multipartCRLF); // Text file itself must be saved in this charset!
		    writer.append(multipartCRLF).flush();
			if(param != null) {
			    writer.append(param).append(multipartCRLF).flush();
			}
		}		

	
		// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
		void multipartFile(File textFile,File binaryFile) throws Exception {
		    OutputStream output = con.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, multipartCharset), true);
		    // Send text file.
			if (textFile != null) {
			    writer.append("--" + multipartBoundary).append(multipartCRLF);
			    writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(multipartCRLF);
			    writer.append("Content-Type: text/plain; charset=" + multipartCharset).append(multipartCRLF); // Text file itself must be saved in this charset!
			    writer.append(multipartCRLF).flush();
			    Files.copy(textFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			}
		    // Send binary file.
			if(binaryFile != null) {
			    writer.append("--" + multipartBoundary).append(multipartCRLF);
			    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(multipartCRLF);
			    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(multipartCRLF);
			    writer.append("Content-Transfer-Encoding: binary").append(multipartCRLF);
			    writer.append(multipartCRLF).flush();
			    Files.copy(binaryFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			}
		    writer.append(multipartCRLF).flush(); 
		}
	
		// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
		void multipartMarkCompleted() throws Exception {
		    OutputStream output = con.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, multipartCharset), true);
			writer.append("--" + multipartBoundary + "--").append(multipartCRLF).flush();
		}

	
				
				
		private static String webRequest99(String restOp,String targetURL, String urlParameters) {
			  HttpURLConnection connection = null;  
			  try {
			    //Create connection
			    URL url = new URL(targetURL);
			    connection = (HttpURLConnection)url.openConnection();
			    connection.setRequestMethod(restOp);
			    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			    connection.setRequestProperty("Content-Length",		        Integer.toString(urlParameters.getBytes().length));
			    connection.setRequestProperty("Content-Language", "en-US");  

			    connection.setUseCaches(false);
			    connection.setDoOutput(true);

			    //Send request
			    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());
			    wr.writeBytes(urlParameters);
			    wr.close();

			    //Get Response  
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			    String line;
			    while((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			    }
			    rd.close();
			    return response.toString();
			  } catch (Exception e) {
			    e.printStackTrace();
			    return null;
			  } finally {
			    if(connection != null) {
			      connection.disconnect(); 
			    }
			  }
			}
		
// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
		static void f99fileupload(String url,String param,File textFile,File binaryFile) throws Exception {
			String boundary = "£$€6{[]€6&¤%&££";
			String CRLF = "\r\n"; // Line separator required by multipart/form-data.
			URLConnection connection = new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
			try (
			    OutputStream output = connection.getOutputStream();
			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
			) {
			    // Send normal param.
				if(param != null) {
				    writer.append("--" + boundary).append(CRLF);
				    writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
				    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
				    writer.append(CRLF).append(param).append(CRLF).flush();
				}
			    // Send text file.
				if (textFile != null) {
				    writer.append("--" + boundary).append(CRLF);
				    writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
				    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
				    writer.append(CRLF).flush();
				    Files.copy(textFile.toPath(), output);
				    output.flush(); // Important before continuing with writer!
				    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
				}
			    // Send binary file.
				if(binaryFile != null) {
				    writer.append("--" + boundary).append(CRLF);
				    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
				    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
				    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
				    writer.append(CRLF).flush();
				    Files.copy(binaryFile.toPath(), output);
				    output.flush(); // Important before continuing with writer!
				    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
				}
			    // End of multipart/form-data.
			    writer.append("--" + boundary + "--").append(CRLF).flush();
			}
		}
}
