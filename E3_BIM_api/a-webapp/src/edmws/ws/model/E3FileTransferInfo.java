package edmws.ws.model;

import java.io.File;

import org.json.JSONObject;

public class E3FileTransferInfo extends JSONObject {

	public E3FileTransferInfo() {
		super();
	}
	//public String fileName;
	
	public long getBytesTransferred(){ return this.optLong("bytesTransferred"); }
	public void   setBytesTransferred(long iv){this.put("bytesTransferred", iv);};

	public String getFileNameOnClient(){return this.optString("fileNameOnClient",null);	};
	public void   setFileNameOnClient(String v){this.put("fileNameOnClient",v);	};

	public String getFileNameOnServer(){return this.optString("fileNameOnServer",null);	};
	public void   setFileNameOnServer(String v){this.put("fileNameOnServer",v);	};

	public String getForeignFileUrl(){return this.optString("foreignFileUrl",null);	};
	public void   setForeignFileUrl(String v){this.put("foreignFileUrl",v);	};

	public String getOperation(){return this.optString("operation",null);	};
	public void   setOperation(String v){this.put("operation",v);	};

	public String getProgressInfoUrl(){return this.optString("progressInfoUrl",null);	};
	public void   setProgressInfoUrl(String v){this.put("progressInfoUrl",v);	};

	public String getUploadOrDownloadUrl(){return this.optString("uploadOrDownloadUrl",null);	};
	public void   setUploadOrDownloadUrl(String v){this.put("uploadOrDownloadUrl",v);	};

	public String getUserName(){return this.optString("username",null);	};
	public void   setUserName(String v){this.put("username",v);	};

	public void deleteTempFiles() {
		try
		{
			new File(this.getFileNameOnServer()).delete();
		}
		catch(Exception ex){}
	}

}
