package com.bupt.ios.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.JDOMException;

import com.bupt.ios.commonData.ProjectParameters;
import com.bupt.ios.rule.ReadRules;


public class PreScheduler {
	private static Logger logger = LogManager.getLogger(PreScheduler.class);
	private String fileSeparator = System.getProperty("file.separator");
	
	public void schdular(){
		//init reportPath
//		ProjectParameters.setReportPath(System.getProperty("user.dir")+fileSeparator+"report");
		
		//获取idaPath
		String idaPath = "";
		String confPath = System.getProperty("user.dir")+fileSeparator+"conf"+fileSeparator+"path_config.properties";
		InputStream configIn;
		Properties configPro = new Properties();
		try {
			configIn = new FileInputStream(new File(confPath));
			configPro.load(configIn);
			idaPath=configPro.getProperty("IDAPath");
			ProjectParameters.setIdaPath(idaPath);
			logger.info("idaPath:"+idaPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
		
		//create detemp directory
		File detmp = new File(System.getProperty("user.dir")+fileSeparator+"detemp");
		if(!detmp.exists()){
			detmp.mkdirs();
		}
		clearDetemp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String detempFileName = dateFormat.format(new Date()).toString();
		String detempFilePath = System.getProperty("user.dir")+fileSeparator+"detemp"+fileSeparator+detempFileName;
		ProjectParameters.setCurrentime(detempFileName);
		File detempDir = new File(detempFilePath);
		detempDir.mkdir();
		ProjectParameters.setDetempPath(detempFilePath);
		File idaDir = new File(ProjectParameters.getDetempPath()+fileSeparator+"ida");
		idaDir.mkdir();
		File unzipDir = new File(ProjectParameters.getDetempPath()+fileSeparator+"unzip");
		unzipDir.mkdir();
//		File resultDir = new File(ProjectParameters.getDetempPath()+fileSeparator+"result");
//		resultDir.mkdir();
//		ProjectParameters.setResultPath(resultDir.getAbsolutePath());
		
		//读取规则xml文件，存放于CommonDataSet
		try {
			ReadRules.readRules();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void clearDetemp(){
		String detmpPath = System.getProperty("user.dir")+fileSeparator+"detemp";
		File detmpFile = new File(detmpPath);
		File[]dirs = detmpFile.listFiles();
		for(File dir : dirs){
			clearDir(dir);
		}
	}
	
	private void clearDir(File root){
		if(root.isDirectory()){
			File[]files = root.listFiles();
			for(File file:files){
				clearDir(file);
			}
		}
		root.delete();
	}

}
