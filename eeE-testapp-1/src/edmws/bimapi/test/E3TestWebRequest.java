package edmws.bimapi.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import edmws.webapp.servlets.E3Logger;

public class E3TestWebRequest {

	private static E3Logger logger = E3Logger.getLogger(E3TestWebRequest.class.getName());
	private static void log(int severity,String msg) {
		logger.log(severity, msg);			
	}
	
	private final String USER_AGENT = "Mozilla/5.0";
	private final String MULTIPART_BOUNDARY = "£$€6{[]€6&¤%&££";
	private final String MULTIPART_CHARSET = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
	
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
		public  String sendGet(E3TestArgs args) throws Exception {
			doConnect(args.getUrl() );

			con.setRequestMethod(args.restOp);
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			log(E3Logger.DEBUG,"Sending '" + args.restOp + "' request to URL : " + args.getUrl());

			if( 	("POST".equals(args.restOp)) 
				|| 	("DELETE".equals(args.restOp))
			  ){
				con.setDoOutput(true);
				con.setRequestMethod(args.restOp);
				if(args.bodyArgs != null) {
					String urlParameters = args.bodyArgs.toString();//"sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
					log(E3Logger.DEBUG,"Post parameters : " + urlParameters);
				//    con.setRequestProperty("Content-Length",		        Integer.toString(urlParameters.getBytes().length));
					this.f96body(urlParameters);
				}
				if(args.get_file_input() != null){
					File textFile = new File(args.get_file_input());
					log(E3Logger.DEBUG,"Input file : " + args.get_file_input());
					this.singlePartFileUpload(textFile, null);
				}
			}

			int responseCode = con.getResponseCode();
			log(E3Logger.DEBUG,"Response Code : " + responseCode);
			if(200 == responseCode) {
				readResponse();
				printResponse();
			} else {
				handleErrResponse(responseCode);
			}
			
			return response.toString();
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
			log(E3Logger.DEBUG,response.toString());
			
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
		void f96body(String param) throws Exception {
			String boundary = MULTIPART_BOUNDARY;
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
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
	
		// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
		void f97fileupload(File textFile,File binaryFile) throws Exception {
			String boundary = MULTIPART_BOUNDARY;
			String CRLF = "\r\n"; // Line separator required by multipart/form-data.
			//con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			String charset = MULTIPART_CHARSET;
		    OutputStream output = con.getOutputStream();
		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
		    // Send text file.
			if (textFile != null) {
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
			    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
			    writer.append(CRLF).flush();
			    Files.copy(textFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.flush(); // CRLF is important! It indicates end of boundary.
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
		    writer.append("--" + boundary + "--").append(CRLF).flush();
		}
	

	
		// ref http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests
				void f98fileupload(String url,String param,File textFile,File binaryFile) throws Exception {
					String boundary = "£$€6{[]€6&¤%&££";
					String CRLF = "\r\n"; // Line separator required by multipart/form-data.
					URLConnection connection = con;//new URL(url).openConnection();
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
