package edmws.webapp.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class E3BasicUtils {

	public static int ERROR_CODE_FAIL = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 500
	public static int ERROR_CODE_PARAMS = HttpServletResponse.SC_BAD_REQUEST; // 400
	public static int ERROR_CODE_AUTH = HttpServletResponse.SC_FORBIDDEN; // 403
	public static int ERROR_CODE_SESSION_EXPIRED = HttpServletResponse.SC_UNAUTHORIZED; // 401

	
    public E3BasicUtils() {
    }

	
	static String jsonStringHalfEncode(String s) {
		if (s == null) return "null"; else if ((s.indexOf('"') == -1) && (s.indexOf('\\') == -1)) return "\""+s+"\"";
		StringBuffer sb = new StringBuffer(s.length() + 16);  sb.append('"');
		int l=s.length();
		for (int i=0; i < l; i++) { char c = s.charAt(i); if ((c == '\\') || (c == '"')) sb.append('\\');  sb.append(c); }
		sb.append('"');  return sb.toString();
	}
	
	static String jsonStringHalfDecode(String s) {
		if (s == null) return s; else if (!s.startsWith("\"")) return s; else s = s.substring(s.indexOf('"')+1, s.lastIndexOf('"'));
		StringBuffer sb = new StringBuffer(s.length());
		int l=s.length();
		for (int i=0; i < l; i++) { char c = s.charAt(i); if (c == '\\') { i++; c = s.charAt(i); };  sb.append(c); }
		return sb.toString();
	}
	
	public static HashMap<String,String> jsonDecodeSimpleObj(String s) {
		HashMap<String,String> rslt = new HashMap<String,String>(); 
		if ((s == null) || "".equals(s.trim()) || (s.indexOf('{') == -1)) return rslt; 
		String[] pairs = s.substring(s.indexOf('{')+1, s.lastIndexOf('}')).split(",");
		for (int i=0; i < pairs.length; i++) if (pairs[i].trim().length() != 0) {
			String name = pairs[i].substring(0,pairs[i].indexOf(':')).trim(), val = pairs[i].substring(pairs[i].indexOf(':')+1).trim();
			if ("null".equals(val)) val = null; else if (val.startsWith("\"")) val = jsonStringHalfDecode(val);
			rslt.put(jsonStringHalfDecode(name),val);
		}
		return rslt;
	}
	
	public static String jsonEncodeSimpleObj(HashMap<String,String> hm) {
		StringBuffer sb = new StringBuffer("{");
		for (Map.Entry<String,String> pair : hm.entrySet()) {
			sb.append(jsonStringHalfEncode(pair.getKey()));  sb.append(':');  sb.append(jsonStringHalfEncode(pair.getValue()));
		}
		sb.append("}");
		return sb.toString();
	}
	


	private static byte[] encodeData = new byte[]{ (byte)'A',(byte)'B',(byte)'C',(byte)'D',(byte)'E',(byte)'F',(byte)'G',(byte)'H',(byte)'I',(byte)'J',(byte)'K',(byte)'L',(byte)'M',(byte)'N',(byte)'O',(byte)'P',(byte)'Q',(byte)'R',(byte)'S',(byte)'T',(byte)'U',(byte)'V',(byte)'W',(byte)'X',(byte)'Y',(byte)'Z',(byte)'a',(byte)'b',(byte)'c',(byte)'d',(byte)'e',(byte)'f',(byte)'g',(byte)'h',(byte)'i',(byte)'j',(byte)'k',(byte)'l',(byte)'m',(byte)'n',(byte)'o',(byte)'p',(byte)'q',(byte)'r',(byte)'s',(byte)'t',(byte)'u',(byte)'v',(byte)'w',(byte)'x',(byte)'y',(byte)'z',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'+',(byte)'/' };
	private static byte[] decodeData = new byte[]{ 62,65,65,65,63,52,53,54,55,56,57,58,59,60,61,65,65,65,65,65,65,65,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,65,65,65,65,65,65,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51 }; /* indexes of chars from '+' to 'z' */
	
    public static String encode64(String input, String encoding) throws Exception {
	    if (input == null) return null;  return new String(encode64(input.getBytes((encoding == null) ? "ISO-8859-1" : encoding)), "US-ASCII");
    }
    
    public static byte[] encode64(byte[] src) {
    	int length = src.length;
        byte[] dst = new byte[(length+2)/3 * 4 + length/72];
        int x = 0, dstIndex = 0, state = 0 /* which char in pattern*/, old = 0 /* previous byte */, len = 0 /* length decoded so far*/;
        for (int srcIndex = 0; srcIndex < length; srcIndex++) {
		    x = src[srcIndex];
		    switch (++state) {
		        case 1: dst[dstIndex++] = encodeData[(x>>2) & 0x3f]; break;
		        case 2: dst[dstIndex++] = encodeData[((old<<4)&0x30) | ((x>>4)&0xf)]; break;
		        case 3: dst[dstIndex++] = encodeData[((old<<2)&0x3C) | ((x>>6)&0x3)]; dst[dstIndex++] = encodeData[x&0x3F];	state = 0; break;
		    }
		    old = x;  if (++len >= 72) { dst[dstIndex++] = (byte) '\n'; len = 0; }
		}
		switch (state) { /* now clean up the end bytes */
		    case 1: dst[dstIndex++] = encodeData[(old<<4) & 0x30];  dst[dstIndex++] = (byte) '=';  dst[dstIndex++] = (byte) '=';  break;
		    case 2: dst[dstIndex++] = encodeData[(old<<2) & 0x3c];  dst[dstIndex++] = (byte) '=';  break;
		}
		return dst;
	}
    
    public static String decode64(String input, String encoding) throws Exception {
	    if (input == null) return null;  return new String(decode64(input.getBytes("US-ASCII")), (encoding == null) ? "ISO-8859-1" : encoding);
    }
    
    public static byte[] decode64(byte[] s) {
        int end = 0; /*end state*/  if ((s.length > 0) && (s[s.length-1] == '=')) end++;  if ((s.length > 1) && (s[s.length-1] == '=') && (s[s.length-2] == '=')) end++;
	    int len = (s.length + 3)/4 * 3 - end, dst = 0;
	    byte[] result = new byte[len];
        try {
		  for(int src = 0; src< s.length; src++) {
			  int chr = s[src], code = ((chr < '+') || (chr > 'z')) ? 64 : decodeData[chr - '+'];  if (code > 63) break;
		      switch (src & 3) {
		      	case 0: result[dst] = (byte) (code<<2); break;
		      	case 1: result[dst++] |= (byte) ((code>>4) & 0x3); result[dst] = (byte) (code<<4);  break;
		      	case 2: result[dst++] |= (byte) ((code>>2) & 0xf); result[dst] = (byte) (code<<6);  break;
		      	case 3: result[dst++] |= (byte) (code & 0x3f);  break;
		      }
		  }
	    } catch (Exception e) {}
        return result;
	}

}
