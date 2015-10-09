/**
 * 
 */
package certUuid;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * @author marty
 *
 */
public class UuidGBACert {
	

	/**
	 * Generate v5 UUID
	 * 
	 * Version 5 UUIDs are named based. They require a namespace (a url) 
	 * and a value (the name). Given the same namespace and 
	 * name, the output is always the same.
	 * 
	 * @param	String	namespace
	 * @param	String	name
	 */
	public static String generate(String namespace, String name) 
	{

		// Calculate hash value
		MessageDigest md;
		String hash = null; 
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.reset();
			md.update("6ba7b8119dad11d180b400c04fd430c8".getBytes());
			md.update(namespace.getBytes("UTF-8"));
			md.update(name.getBytes("UTF-8"));
			hash =  byteToHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		String result="";
		result = String.format("%s-%s-%04x-%04x-%s",

		// 32 bits for "time_low"
		hash.substring(0, 8),

		// 16 bits for "time_mid"
		hash.substring(8, 12),

		// 16 bits for "time_hi_and_version",
		// four most significant bits holds version number 3
		(Integer.parseInt(hash.substring(12, 16),16) & 0x0fff) | 0x5000,

		// 16 bits, 8 bits for "clk_seq_hi_res",
		// 8 bits for "clk_seq_low",
		// two most significant bits holds zero and one for variant DCE1.1
		(Integer.parseInt(hash.substring(16, 20),16) & 0x3fff) | 0x8000,

		// 48 bits for "node"
		hash.substring(20, 32)
		);
		
		return result;
	
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
