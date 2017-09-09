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
    /**
     * Gets the user's UUID
     */
	 public static synchronized String getUserID(){
		return uuid;
	}

    /**
     * Get the session identifier.
     */
    public static synchronized String getSessionUuid()
    {
        return sessionUuid;
    }
    
	//getoptinout method is not used in Bluej, therefore delete it so far


	public static synchronized void changeOptInOut(boolean forceOptIn){

	}

	/*
	 * record when drjava opened
	 *
	 *@os operating system
	 *@javaVersion java version of drjava
	 *@interfaceLanguage drjava UI language
	 */

	public static void drjavaOpened(String os, String javaVersion, String interfaceLanguage){
		startSession();
		if(dontSend()) return;
		DataCollectorImpl.drjavaOpened(os,javaVersion,interfaceLanguage);
	}
	

	/*
	 * Called from drjavaOpened method to record starting session
	 *
	 *
	 */
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

	/*
	 * close drjava
	 */
	    public static void drjavaClosed()
	    {
		if (dontSend()) return;
		DataCollectorImpl.drjavaClosed();
	    }
	    
	/*
	 * check if uuid valid
	 *
	 *
	 */

	private static synchronized boolean uuidValid(){
		return (uuid!=null) && (uuid.length()>=32);
		
	}
	
	public static synchronized void generateUUID(){
		if(!uuidValid()){
			uuid = UUID.randomUUID().toString();
		}
		DrJava.getConfig().setSetting(OptionConstants.UUID, uuid);
		
	}
		
	public static void openClass(File parentDir, OpenDefinitionsDocument sourceFile){
		if(dontSend()) return;
		DataCollectorImpl.openClass(parentDir, sourceFile);
	}
	
	public static void newFile(String pkgName, File dir, DefinitionsDocument doc){
		if(dontSend()) return;
		DataCollectorImpl.newFile(pkgName, dir, doc);	
	}	

	public static void renamedClass(File oldFile, File newFile){
		if(dontSend()) return;
		DataCollectorImpl.renamedClass(oldFile, newFile);	
	}

	public static void closeFile(File parentDir, OpenDefinitionsDocument doc){
		if(dontSend()) return;
		DataCollectorImpl.closeFile(doc);
	}

	public static void compiled(List<File> files, List<DJError> errors, boolean shown, boolean successful){
		if(dontSend()) return;
		DataCollectorImpl.compiled(files, errors, shown,successful);
	}

/*    public static void selectClass(Package pkg, File sourceFile)
    {
        if (dontSend()) return;
        DataCollectorImpl.selectClass(pkg, sourceFile);
    }
 */   
}
