package com.bupt.ios.commonData;
/**
 * 
 * @author yujianbo
 * 存放各种路径变量
 */
public class ProjectParameters {
	
	private static String ipaPath;
	private static String idaPath;
	private static String detempPath;
	private static String exeFilePath;
	private static String InfoPlistath;
	private static String idbPath;
	private static String idaPythonPath;
	private static String appName;
	private static String reportPath;
	private static String currentime;
	private static String input_type;
	
	public static String getCurrentime() {
		return currentime;
	}
	public static void setCurrentime(String currentime) {
		ProjectParameters.currentime = currentime;
	}
	public static String getReportPath() {
		return reportPath;
	}
	public static void setReportPath(String reportPath) {
		ProjectParameters.reportPath = reportPath;
	}
	public static String getAppName() {
		return appName;
	}
	public static void setAppName(String appName) {
		ProjectParameters.appName = appName;
	}
	public static String getIdaPythonPath() {
		return idaPythonPath;
	}
	public static void setIdaPythonPath(String idaPythonPath) {
		ProjectParameters.idaPythonPath = idaPythonPath;
	}
	public static String getIdbPath() {
		return idbPath;
	}
	public static void setIdbPath(String idbPath) {
		ProjectParameters.idbPath = idbPath;
	}
	public static String getExeFilePath() {
		return exeFilePath;
	}
	public static void setExeFilePath(String exeFilePath) {
		ProjectParameters.exeFilePath = exeFilePath;
	}
	public static String getDetempPath() {
		return detempPath;
	}
	public static void setDetempPath(String detempPath) {
		ProjectParameters.detempPath = detempPath;
	}
	public static String getIpaPath() {
		return ipaPath;
	}
	public static void setIpaPath(String ipaPath) {
		ProjectParameters.ipaPath = ipaPath;
	}
	public static String getIdaPath() {
		return idaPath;
	}
	public static void setIdaPath(String idaPath) {
		ProjectParameters.idaPath = idaPath;
	}
	public static String getInfoPlistath() {
		return InfoPlistath;
	}
	public static void setInfoPlistath(String infoPlistath) {
		InfoPlistath = infoPlistath;
	}
	public static String getInput_type() {
		return input_type;
	}
	public static void setInput_type(String input_type) {
		ProjectParameters.input_type = input_type;
	}

}
