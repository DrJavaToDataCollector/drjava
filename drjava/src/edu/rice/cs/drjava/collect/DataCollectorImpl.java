package edu.rice.cs.drjava.collect;

import org.apache.http.entity.mime.MultipartEntity;
public class DataCollectorImpl{

	private static synchronized void submitEvent(final File sourceFile, final EventName eventname, final Event evt){
		final String projectName = "DrJava";
		final String projectPathHash = CollectUtility.md5Hash(sourceFile.getAbsolutePath());
		final String packgeName = "drjava";

		final String uuidCopy = DataCollector.getUserID();
		
		DataSubmitted.submitEvent(new Event() {
			@Override
			public void success(Map<FileKey, List<String>> fileVersions){
				evt.success(fileVersions);
			}

			@Override
			public MultipartEntity makeData(int sequenceNum, Map<FileKey, List<String>> fileVersions){
				 MultipartEntity mpe = evt.makeData(sequenceNum, fileVersions);

		        if (mpe == null)
		            return null;
		        
		        mpe.addPart("user[uuid]", CollectUtility.toBody(uuidCopy));
		        mpe.addPart("session[id]", CollectUtility.toBody(DataCollector.getSessionUuid()));
		    //    mpe.addPart("participant[experiment]", CollectUtility.toBody(experimentCopy));
		      //  mpe.addPart("participant[participant]", CollectUtility.toBody(participantCopy));
		        
		        if (projectName != null)
		        {
		            mpe.addPart("project[name]", CollectUtility.toBody(projectName));
		            mpe.addPart("project[path_hash]", CollectUtility.toBody(projectPathHash));
		            
		            if (packageName != null)
		            {
		                mpe.addPart("package[name]", CollectUtility.toBody(packageName));
		            }
		        }
		        
		        mpe.addPart("event[source_time]", CollectUtility.toBody(DateFormat.getDateTimeInstance().format(new Date())));
		        mpe.addPart("event[name]", CollectUtility.toBody(eventName.getName()));
		        mpe.addPart("event[sequence_id]", CollectUtility.toBody(Integer.toString(sequenceNum)));
		        
		        return mpe;
		    }
		});
	}

	public static void drjavaOpened(String os, Sring javaVersion, String interfaceLanguage){
		DataSubmitter.initSequence();
		MultipartEntity mpe = new MultipartEntity();
		mpe.addPart("installation[operating_system]",CollectUtility.toBody(os));
		mpe.addPart("installation[java_version]",CollectUtility.toBody(javaVersion));
		mpe.addPart("installation[interface_language]",CollectUtility.toBody(interfaceLanguage));

		submitEvent(null, EventName.DRJAVA_START, new PlaintEvent(mpe));
	}

	public static void openClass(File parentDir, OpenDefinitionsDocument sourceFile){
		classEvent(parentDir, sourceFile, EventName.FILE_OPEN);
	}

	public static void closeFile(File parentDir, OpenDefinitionsDocument sourceFile){
		classEvent(parentDir, sourceFile, EventName.FILE_CLOSE);

	}

	public static void classEvent(File parentDir, OpenDefinitionsDocument doc, EventName eventname){
		final MultipartEntity mpe = new MultipartEntity();
		mpe.addPart("event[source_file_name]", CollectUtility.toBodyLocal(parentDir, doc));
		File file = sourceFile.getFile();
		submitEvent(file, eventName, new PlaintEvent(mpe));
	}

/**
 *	new class
 *
 */
	
	public static void newFile(String pkgName, File dir, DefinitionsDocument doc){
		addCompleteFiles(pkgName, dir, Collections.singletonList(doc), EventName.ADD);
	}

/**
 *	called from new class
 *
 */
	
	public static void addCompleteFiles(String pkgName, File dir, List<DefinitionsDocument> docs, EventName eventName){
		final MultipartEntity mpe = new MultipartEntity();
		for(DefinitionsDocument d : docs){
			String relative = CollectUtility.toPath(dir, d.getOpenDefDoc().getFile());
			mpe.addPart("project[source_files][][name]",CollectUtility.toBody(relative));

			mpe.addPart("project[source_files][][source_type]", CollectUtility.toBody("java"));
		//	String anonymisedContent = CollectUtility.readFileAndAnonymise(d);
		} 
		submitEvent(eventName,new Event(){
			@Override
			public void success()
		});	
	}
/**
 *	Rename an existed class
 *
 */
	public static void renamedClass(File oldFile, File newFile){
		MultipartEntity mpe = new MultipartEntity();
		mpe.addPart("source_histories[][source_history_type]", CollectUtility.toBody("rename"));
		mpe.addPart("source_histories[][content]", CollectUtility.toBody)
	}

	public static void compiled(List<File> files, List<DJError> errors, boolean shown, boolean successful){
		MultipartEntity mpe = new MultipartEntity();
		mpe.addPart("event[compile_success]", CollectUtility.toBody(successful));
		mpe.addPart("event[compile_reason]",CollectUtility.toBody("user"));
		for(File f : files){
			mpe.addPart("event[compile_input][][source_file_name]",CollectUtility.toBody(CollectUtility.toPath(f.getParentFile(), f)));
		}
		for(DJError error : errors){
			//show true if it is warning otherwise it is error
			mpe.addPart("event[compile_output][][is_error]",CollectUtility.toBody(error.isWaring()));
			mpe.addPart("event[compile_output][][shown]", CollectUtility.toBody(shown));
			mpe.addPart("event[compile_output][][message]", CollectUtility.toBody(error.message()));
			//can't get in DrJava, always return -2
			mpe.addPart("event[compile_output][][session_sequence]", CollectUtility.toBody("-2"));
			
			if(error.fileName()!=null){
				//only can get one type of line number in DJError, therefore regard start and end line as the same number
				if(error.lineNumber()>=1)
					mpe.addPart("event[compile_output][][start_line]", CollectUtility.toBody(error.lineNumber()));
				if(error.lineNumber()>=1)
					mpe.addPart("event[compile_output][][end_line]", CollectUtility.toBody(error.lineNumber()));
				//start and end column getting from DJError
				if(error.startColumn()>=1)
					mpe.addPart("event[compile_output][][start_column]",CollectUtility.toBody(error.startColumn())); 
				if(error.endColumn()>=1)
					mpe.addPart("event[compile_output][][end_column]", CollectUtility.toBody(error.endColumn()));
				//BlueJ also gives xpath for diagnostic but it can be null, therefore ignore it
				//relative confused?
				String relative = CollectUtility.toPath(f.getParentFile(), new File(error.fileName()));
				mpe.addPart("event[compile_output][][source_file_name]", CollectUtility.toBody(relative));
			}
		}
		
		for(File fi : files){
			submitEvent(fi, EventName.COMPILE, new PlaintEvent(mpe));
		}
	}

}
