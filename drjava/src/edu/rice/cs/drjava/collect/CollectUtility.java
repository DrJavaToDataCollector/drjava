package edu.rice.cs.drjava.collect;

import org.apache.http.entity.mime.content.StringBody;
import java.nio.charset.Charset;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import org.apache.http.entity.mime.content.StringBody;

class CollectUtility{
    /**
     * Cached UTF-8 charset:
     */
	private static final Charset utf8 = Charset.forName("UTF-8");

    /**
     * Converts the given String to a StringBody.  Null strings are sent the same as empty strings.
     */
	static StringBody toBody(String s){
		try {
		    return new StringBody(s == null ? "" : s, utf8);
		}
		catch (UnsupportedEncodingException e) {
		    // Shouldn't happen, because UTF-8 is required to be supported
		    return null;
		}	
	}

	/**
	 * Converts the integer to a StringBody
	 */
	static StringBody toBody(int i)
	{
		return toBody(Integer.toString(i));
	}

	/**
	 * Converts the long to a StringBody
	 */
	static StringBody toBody(long l)
	{
		return toBody(Long.toString(l));
	}

	/**
	 * Converts the boolean to a StringBody
	 */
	static StringBody toBody(boolean b)
	{
		return toBody(Boolean.toString(b));
	}

	static String toPath(File dir, File doc){
		return dir.toURI().relativize(doc.toURI()).getPath();
	}
	static StringBody toBodyLocal(File parenDir, OpenDefinitionsDocument sourceFile){
		return toBody(toPath(parentDir, sourceFile));
	}

    /**
     * Performs an md5 hash of the given string
     */
	static String md5Hash(String src){
		byte[] hash;
		try{
			hash = MessageDigest.getInstance("MD5").digest(src.getBytes("UTF-8"));
		}catch(NoSuchAlgorithmException e){
			return "";	
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		for(byte b : hash){
			s.append(String.format("%02X", b));
		}
		return s.toString();

	}

	static String readFileAndAnonymise(DefinitionsDocument doc){
		try{
			StringBuilder sb = new StringBuilder();
			FileInputStream inputStream = new FileInputStream(f);
			InputStreamReader reader = new InputStreamReader(inputStream, "us-ascii");
			char[] buf = new char[4096];

			int read = reader.read(buf);
			while(read!=-1){
				sb.append(buf, 0, read);
				read =reader.read(buf);
			}

			reader.close();
			inputStream.close();
			return anonymise(sb.toString());
		}
		catch (IOException ioe) {return null;}
	}
	private static String anonymise(String sourceCode){
		StringBuilder result = new StringBuilder();
		
	}
}
