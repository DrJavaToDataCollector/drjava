package edu.rice.cs.drjava.collect;


import edu.rice.cs.drjava.DrJava;
import edu.rice.cs.drjava.config.OptionConstants;
import java.util.UUID;
/**
 * DataCollector for collecting data from users of DrJava and sending off data to WhiteBox and BlackBox of BlueJ.
 *   
 * This class mainly acts as a proxy for the DataCollectorImpl class, which implements the actual
 * collection logic.
 */
public class DataCollector{
	 /**
     * These three variables can change during the execution:
     */
	
	 private static String uuid;
	 
//	 @onThread(value=Tag.Any, requireSynchronized = true)
	 private static boolean recordingThisSession;
	
	 /**
	  * Session identifier.  Never changes after startSession() has been called:
	 */
//	 @OnThread(value = Tag.Any, requireSynchronized = true) 
	 private static String sessionUUID;
	    
	 	/**
	     * Checks whether we should send data.  This takes into account whether we
	     * are in Greenfoot, and opt-in status.  It doesn't check whether we have stopped
	     * sending due to connection problems -- DataSubmitter keeps track of that.
	     */
	//    @OnThread(Tag.Any)
	    private static synchronized boolean dontSend()
	    {
	        return (!recordingThisSession);
	    } 
	 
	public static void drjavaOpened(String os, String javaVersion, String interfaceLanguage){
		startSession();
		System.out.println(DrJava.getConfig().getSetting(OptionConstants.UUID));
		if(dontSend()) return;
		//DataCollectorImpl.drjavaOpened(os,javaVersion,interfaceLanguage);
	}
	
	public static synchronized void startSession(){
		uuid = DrJava.getConfig().getSetting(OptionConstants.UUID);
		if(!uuidValid()){
			generateUUID();
		}
		recordingThisSession = false;
		if(recordingThisSession){
			sessionUUID = UUID.randomUUID().toString();
			
		}
			
		}
	private static synchronized boolean uuidValid(){
		return (uuid!=null) && (uuid.length()>=32);
		
	}
	
	public static synchronized void generateUUID(){
		if(!uuidValid()){
			uuid = UUID.randomUUID().toString();
		}
		DrJava.getConfig().setSetting(OptionConstants.UUID, uuid);
		
	}
		
	
}
