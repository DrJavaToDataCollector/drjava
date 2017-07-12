package edu.rice.cs.drjava.collect;

import org.apache.http.entity.mime.content.StringBody;
import java.nio.charset.Charset;

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
}
