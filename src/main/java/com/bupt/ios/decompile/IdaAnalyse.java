package com.bupt.ios.decompile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.*;

import com.bupt.ios.commonData.ProjectParameters;

public class IdaAnalyse {
	private static String fileSeparator = System.getProperty("file.separator");
	private static Logger logger = LogManager.getLogger(IdaAnalyse.class);

	public void genIDB(){

		//运行ida，生成idb文件
		String idaCmdString = ProjectParameters.getIdaPath()+" -A -B "+ProjectParameters.getExeFilePath();
		Process idaProcess;
		try{
			logger.info("ready exe cmd:"+idaCmdString);
			idaProcess = Runtime.getRuntime().exec(idaCmdString);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(idaProcess.getInputStream()), 1024);
			String line;	
			//错误内容输出
			while((line = bufferedReader.readLine()) != null)
				logger.error("<error>"+line);
			bufferedReader.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int preStrIndex = ProjectParameters.getExeFilePath().lastIndexOf(".");
//		String idbPath = ProjectParameters.getExeFilePath().substring(0, preStrIndex)+".i64";
		String idbPath = ProjectParameters.getExeFilePath()+".i64";
		File idbFile = new File(idbPath);
		if(!idbFile.exists()){
			logger.error("generate idb failed");
			logger.error("the path of ida may be wrong");
			System.exit(-1);
		}else{
			logger.info("generate idb success");
		}

		//cp idb文件到detemp-ida下，等待解析
		copy(idbPath, ProjectParameters.getDetempPath()+fileSeparator+"ida");
		ProjectParameters.setIdbPath(ProjectParameters.getDetempPath()+fileSeparator+"ida"+fileSeparator+new File(idbPath).getName());

		//开始解析idb
		analyseIDB();

	}

	private void analyseIDB(){
		//把IDAPython复制到detemp-ida下
		copy(System.getProperty("user.dir")+fileSeparator+"IDAPython", ProjectParameters.getDetempPath()+fileSeparator+"ida");
		ProjectParameters.setIdaPythonPath(ProjectParameters.getDetempPath()+fileSeparator+"ida"+fileSeparator+"IDAPython");

		//调用ida解析idb
		String pythonString = ProjectParameters.getIdaPath()+" -OIDAPython:"+ProjectParameters.getIdaPythonPath()+fileSeparator+"IDAPython_sqlite_new.py "+ProjectParameters.getIdbPath();
		//		System.out.println(pythonString);
		logger.info("ready analyse idb :" + pythonString);
		try {
			Process pythonProcess = Runtime.getRuntime().exec(pythonString);
			pythonProcess.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn("analyse idb failed！");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void copy(String source, String des){
		//判断操作系统类型
		String os = System.getProperty("os.name").toLowerCase();
		String cpCmd = "";
		if(os.contains("win")){
			File file = new File(source);
			if(file.isDirectory()){
				String des_dir = des+"\\"+file.getName();
				new File(des_dir).mkdir();
				cpCmd = "xcopy " + source +" "+des_dir;
			}else{
				cpCmd = "xcopy "+source+ " "+des;
			}
		}else{
			cpCmd = "cp -r "+source+ " "+des;
		}
		Process cpProcess;
		try{
			logger.info("ready exe:"+cpCmd);
			cpProcess=Runtime.getRuntime().exec(cpCmd);
		}catch (IOException e){
			e.printStackTrace();
		}
		logger.info("copy success");
	}

	//	public static void main(String[] args) {
	//		ProjectParamters.setDetempPath("/Users/konghaohao/Documents/git_workspace/ios-vulnerability-detection/detemp/2016-07-14-17-30-11");
	//		ProjectParamters.setIdaPath("/Applications/IDA_Pro_6.5/idaq.app/Contents/MacOS/idaq64");
	//		ProjectParamters.setExeFilePath("/Users/konghaohao/Documents/git_workspace/ios-vulnerability-detection/detemp/2016-07-14-17-30-11/unzip/Payload/DamnVulnerableIOSApp.app/DamnVulnerableIOSApp");
	//		new IdaAnalyse().genIDB();
	//	}
}
