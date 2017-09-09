package edu.rice.cs.drjava.collect;

import java.io.*;
import java.util.*;

import javafx.application.Platform;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

class DataSubmitter{

	private static final String submitUrl = "http://blackbox.bluej.org/master_events";
	//For testing: "http://localhost:3000/master_events";

    
    	private static AtomicBoolean givenUp = new AtomicBoolean(false);
	/**
     	* isRunning is only touched while synchonized on queue
    	*/
    	private static boolean isRunning = false;
    
    	private static List<Event> queue = new LinkedList<Event>();
    
	private static int sequenceNum;

    	/**
     	* The versions of the files as we have last sent them to the server.
     	* 
     	* Should only be accessed by the postData method, which is running on
     	* the event-sending thread
     	*/
    	private static Map<FileKey, List<String> > fileVersions = new HashMap<FileKey, List<String> >();

	static void submitEvent(Event evt){
		if(giveUp.get())
			return;

		synchronized (queue){
			queue.add(evt);
			if(! isRunning){
				new Thread(){
					public void run(){
						processQueue();
					}
				}.start();
				isRunning = true;
			}
		}
	}

	/**
	 *Process the queue of items to be posted to the server
	 *
	 */

	private static void processQueue(){
		while(true){
			Event evt;
			synchronized(queue){
				if(queue.isEmpty()){
					isRunning = false;
					queue.notifyAll();
					return;
				}
				evt = queue.remove(0);
			}

				
		}
	}

	public static void initSequence(){
		sequenceNum =1;
	}

    	public static boolean hasGivenUp()
    	{
        	return givenUp.get();
    	}
}
