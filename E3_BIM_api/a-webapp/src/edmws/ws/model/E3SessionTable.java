package edmws.ws.model;

import org.json.JSONObject;

public class E3SessionTable extends Thread{

	private static E3SessionTable theStaticTable = null;
	private E3SessionTable()	{}
	
	
	protected static final Object staticLockE3SessionTable = new Object();
	public static E3SessionTable getStaticSessionTable() {
		if(theStaticTable == null) {
			synchronized(staticLockE3SessionTable) {
				if(theStaticTable == null) {
					theStaticTable = new E3SessionTable();
					theStaticTable.setDaemon(true);
					theStaticTable.start();
				}
			}
		}
		return theStaticTable;
	}

    public void run() {
        while (true) {
        	try { 
        		try {
	        	//System.out.println("Starts to examine the session table");
        			this.cleanup();
        			if (Thread.interrupted()) throw new InterruptedException();
        			sleep(600000);
        		} catch (InterruptedException ie) {
        			System.err.println("E3SessionCleaner interrupted.");
	    	        break;
        		} 
    		} catch (Exception e) {
	    		e.printStackTrace();
	    	}
        }
    }

	private void cleanup() {
		
	}
	
	//////////////////////////////////////////

	private JSONObject members = new JSONObject();
	private JSONObject assertObjectAttr(JSONObject root,String name) {
		JSONObject attr =  root.optJSONObject(name);
		if (attr == null) {
			attr = new JSONObject();
			root.put(name,attr);
		}
		return attr;
	}
	
	
	public JSONObject getUserInfo(String username) {
		JSONObject 	 uinfolist = this.assertObjectAttr(members,"user_info");
		JSONObject 	 uinfo = this.assertObjectAttr(uinfolist, username);
		return uinfo;
	}
	
	public E3FileTransferInfo getFileTransfer(String username, String filename) {
		JSONObject 	uinfo = this.getUserInfo(username);
		JSONObject list = this.assertObjectAttr(uinfo,"file_transfer_info");
		E3FileTransferInfo finfo = (E3FileTransferInfo) list.opt(filename);
		if(finfo == null) {
			finfo = new E3FileTransferInfo();
			finfo.setUserName(username);
			finfo.setFileNameOnClient(filename);
			list.put(filename,finfo);
		}
		return finfo;
	}
	
}
