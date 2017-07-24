package edmws.webapp.servlets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edmws.webapp.exception.E3GeneralException;
import edmws.ws.model.E3FileTransferInfo;
import edmws.ws.model.FileTransferInfo;

public class E3FileTransfer {

	private static E3Logger logger = E3Logger.getLogger("E3FileTransfer");
	private void log(int severity,String msg) {
		logger.log(severity, msg);
	}


	private static final String SERVICE_TAG = "/eee-transfers/";
	private static final String DEFAULT_ENCODING  = "UTF-8"; 

	public static boolean isURLAssigned(E3AccessUtil carrier, String restOp) {
		String path1 = carrier.request.getPathInfo();/*"/AccessControl/login"*/
		if ((path1 != null) && path1.startsWith(SERVICE_TAG))  return true;
		return false;
	}
	

		private static final int bufSize = 8192;
		private byte[] buffer;
		
		public E3FileTransfer() {
			buffer = new byte[bufSize];
		}

		

		public static String getLocalFilenameWithPath(String filename) {
			String dirname = new File( System.getProperty("java.io.tmpdir") ).getPath();
			return dirname + "/" + filename;
		}

//		private String createTempFile(E3FileTransferInfo transferInfo) {
//		fileName = "temp123";
//		for (int l=fileName.length(); l < 3; l++) fileName = fileName + "_";
//		String fileType = ".txt", operation = "upload";
//		transferInfo = new FileTransferInfo();
//		File tempfile = File.createTempFile(fileName, fileType, new File( System.getProperty("java.io.tmpdir") ));
//		String filePath = tempfile.getPath();
//		transferInfo.fileName = filePath; transferInfo.nBytesTransferred = 0;	edmSession.setFileTransferInfo(transferInfo);
//	}
		
		protected String doDownload(E3AccessUtil carrier,E3FileTransferInfo transferInfo)throws Exception {
			String result = null;
			FileInputStream fileIn = null;
			javax.servlet.ServletOutputStream outStream = null;
			try {
				File f = new File(transferInfo.getFileNameOnServer());
				int nBytesRead;
				if(carrier.isUnitTest()) {
					byte[] encoded = Files.readAllBytes(Paths.get(transferInfo.getFileNameOnServer()));
					result = new String(encoded, StandardCharsets.UTF_8);
				} else {
					fileIn = new FileInputStream(f);
					outStream = carrier.response.getOutputStream();
					carrier.response.setContentLength((int)f.length());
					while ((nBytesRead = fileIn.read(buffer, 0, bufSize)) > 0) {
						outStream.write(buffer, 0, nBytesRead);
						transferInfo.setBytesTransferred(transferInfo.getBytesTransferred() + nBytesRead);
					}
					result = transferInfo.toString();
				}
			}
			catch (Exception ex) {
				throw ex;
			}
			finally {
				if(outStream != null) outStream.close();
				if(fileIn != null) fileIn.close();
			}
			return result;
		}

		protected String doUpload(E3AccessUtil carrier,E3FileTransferInfo transferInfo)throws Exception {
			FileOutputStream fileOut = null;
			InputStream inStream = carrier.getInputStream();
			try {
//				String filename = transferInfo.getFileNameOnServer() + ".keep";
				String filename = transferInfo.getFileNameOnServer();
				logger.log(E3Logger.DEBUG,"Writing to file: " + filename);
				fileOut = new FileOutputStream(new File(filename));
				if (Multipart.RequestParser.isMultipartContent(carrier.request)) {
				// TODO: how can I find encoding ? 
					//String enc = "iso-8859-1";
					String enc = DEFAULT_ENCODING;
					E2MultipartCallback callback = new E2MultipartCallback(transferInfo, fileOut);
					(new Multipart.RequestParser())
					.parseRequest (Multipart.RequestParser.getMultipartContentType(carrier.request), 
							inStream, callback, callback, enc);
					if (callback.fileCount == 0) throw new Exception("No files found in multipart request");
				} else {
					int nBytesRead;
					while ((nBytesRead = inStream.read(buffer, 0, bufSize)) > 0) {
						fileOut.write(buffer, 0, nBytesRead); 
						transferInfo.setBytesTransferred(transferInfo.getBytesTransferred() + nBytesRead);
					}
				}
				return transferInfo.toString(); 
			}
			catch (Exception ex) {
				throw ex;
			}
			finally {
				if(inStream != null) inStream.close();
				if(fileOut != null) fileOut.close();
			}
		}

		protected String doFetch(E3AccessUtil carrier,E3FileTransferInfo transferInfo)throws Exception {
			FileOutputStream fileOut = null;
			InputStream inStream = null; 
			try {
				fileOut = new FileOutputStream(new File(transferInfo.getFileNameOnServer()));
				if (carrier.isUnitTest()) {
					File f = new File(transferInfo.getForeignFileUrl());
					inStream = new FileInputStream(f);
				} else { 
			        URL url = new URL(transferInfo.getForeignFileUrl());
					inStream = url.openStream(); 
				}
				int nBytesRead;
				while ((nBytesRead = inStream.read(buffer, 0, bufSize)) > 0) {
					fileOut.write(buffer, 0, nBytesRead); 
					transferInfo.setBytesTransferred(transferInfo.getBytesTransferred() + nBytesRead);
				}
				return transferInfo.toString(); 
			}
			catch (Exception ex) {
				throw ex;
			}
			finally {
				if(inStream != null) inStream.close();
				if(fileOut != null) fileOut.close();
			}
		}

		protected String doWrite(E3AccessUtil carrier,E3FileTransferInfo transferInfo,String content)throws Exception {
			FileOutputStream fileOut = null;
			try {
				fileOut = new FileOutputStream(new File(transferInfo.getFileNameOnServer()));
				fileOut.write(content.getBytes());
				return transferInfo.toString(); 
			}
			catch (Exception ex) {
				throw ex;
			}
			finally {
				if(fileOut != null) fileOut.close();
			}
		}
		
		
		public /*static*/ class E2MultipartCallback implements Multipart.RequestParserCallback, Multipart.RequestParserProgress {
			private E3FileTransferInfo transferInfo;
			private FileOutputStream fileOut;
			public int fileCount = 0;
			public E2MultipartCallback(E3FileTransferInfo transferInfo, FileOutputStream fileOut) throws Exception {
				this.transferInfo = transferInfo;  
				this.fileOut = fileOut;
			}
	    	public void field(String name, String contentType, byte[] data) {
	    		logger.log(E3Logger.DEBUG,"Field (" + contentType + ") " +  name + "=" + new String(data));			
	    	}
	    	public OutputStream file(String name, String filename, String contentType) throws Exception {
	    		fileCount++;
	    		if (fileCount > 1) throw new E3GeneralException(-1,null,"Multiple files uploaded");
	    		return fileOut;
	    	}
	       	public void chunkRead(int chunkLen) {
	       		long nBytesTransferred = this.transferInfo.getBytesTransferred() + chunkLen;
	       		this.transferInfo.setBytesTransferred(nBytesTransferred);
	       	}
		}
		
		
		
		
		
		public static class E3MultipartCallback implements Multipart.RequestParserCallback, Multipart.RequestParserProgress {
			private FileTransferInfo fti = null;
			private ArrayList<Object[]> files = new ArrayList<Object[]>(); //[4]:  File, (field)name, contenttype, filename
			private HashMap<String,String[]> form = new HashMap<String,String[]>();
			private String encoding = "UTF-8";
			private ByteArrayOutputStream jsonstring = null;
			private boolean readingFile = false;
			private int fileTransfer = -1;
			
			public E3MultipartCallback(FileTransferInfo fti, InputStream inStream, String name, String reqType, int fileTransfer) throws Exception { // single part
				OutputStream os = null;  this.fti = fti;  this.fileTransfer = fileTransfer;
				boolean isjsonstring = (fileTransfer == 1) || ((fileTransfer == -1) && "application/json".equals(reqType) && ((name == null) || "".equals(name) || "jsonstring".equals(name)));
				try {
					int nBytesRead;  byte[] buffer = new byte[4096];
					while ((nBytesRead = inStream.read(buffer, 0, buffer.length)) > 0) {
						if (os == null) {
							if (isjsonstring) os = jsonstring = new ByteArrayOutputStream(); else {
								File tempfile = File.createTempFile("tmp", null, new File( System.getProperty("java.io.tmpdir") ));
								os = new FileOutputStream(tempfile);
								files.add( new Object[]{ tempfile, name, reqType, "" } );
							}
						}
						os.write(buffer, 0, nBytesRead); if (!isjsonstring && (fti != null)) fti.nBytesTransferred += nBytesRead;
					}
				} finally {
					if (os != null) os.close();
				}
			}
			public E3MultipartCallback(FileTransferInfo fti, String encoding, int fileTransfer) { // multi part
				if (encoding != null) this.encoding = encoding;
				this.fti = fti;  this.fileTransfer = fileTransfer;
			}
	    	public void field(String name, String contentType, byte[] data) throws Exception {
	    		form.put(name, new String[]{ new String(data, encoding) });  readingFile = false;
	    	}
	    	public OutputStream file(String name, String filename, String contentType) throws Exception {
	    		if (((fileTransfer == 1) || ((fileTransfer == -1) && ((name == null) || "".equals(name) || "jsonstring".equals(name)) && "application/json".equals(contentType))) && (jsonstring == null)) { 
	    			readingFile = false; 
	    			return jsonstring = new ByteArrayOutputStream(); 
	    		}
	    		File tempfile = File.createTempFile("", "", new File( System.getProperty("java.io.tmpdir") ));
	    		FileOutputStream fos = new FileOutputStream(tempfile);
	    		files.add( new Object[]{ tempfile, name, contentType, filename } );
	    		readingFile = true;  return fos;
	    	}
	       	public void chunkRead(int chunkLen) {
	       		if (readingFile && (fti != null)) fti.nBytesTransferred += chunkLen;
	       	}
	       	public void cleanup() {
	       		for (Object[] f : files) if ((f[0] != null) && ((File)f[0]).delete()) f[0] = null;
	       	}
	       	public Map<String,String[]> getForm() { return (form.size() == 0) ? null : form; }
	       	public String getJsonstring() throws Exception { return (jsonstring == null) || (fileTransfer != -1) ? null : new String(jsonstring.toByteArray(), "UTF-8"); }
	       	public byte[] getJsonbinary() throws Exception { return jsonstring == null ? null : jsonstring.toByteArray(); }
	       	public int getFilesCount() { return files.size(); }
	       	public File takeFile(int i) { File f = (File)files.get(i)[0];  files.get(i)[0] = null;  return f; }
	       	public File getFile(int i) { return (File)files.get(i)[0]; }
	       	public String getFileName(int i) { return (String)files.get(i)[1]; }
	       	public String getFileContenType(int i) { return (String)files.get(i)[2]; }
	       	public String getFileNameClientOriginal(int i) { return (String)files.get(i)[3]; }
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}

	

