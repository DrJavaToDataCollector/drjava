package edu.rice.cs.drjava.collect;

import org.apache.http.entity.mime.MultipartEntity;
public class DataCollectorImpl{
	public static void drjavaOpened(String os, Sring javaVersion, String interfaceLanguage){
		DataSubmitter.initSequence();
		MultipartEntity mpe = new MultipartEntity();
		mpe.addPart("installation[operating_system]",CollectUtility.toBody(os));
		mpe.addPart("installation[java_version]",CollectUtility.toBody(javaVersion));
		mpe.addPart("installation[interface_language]",CollectUtility.toBody(interfaceLanguage));

		//submitEvent();
	}
}
