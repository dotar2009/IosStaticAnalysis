package com.bupt.ios.scheduler;

import com.bupt.ios.report.WritePdf;
import com.bupt.ios.report.WriteXml;
import com.lowagie.text.DocumentException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bupt.ios.commonData.ProjectParameters;

public class MainScheduler {
	private static Logger logger = LogManager.getLogger(MainScheduler.class);

	public static void main(String[] args) throws DocumentException {

		//project entrance
		//args: ipa path
		ProjectParameters.setIpaPath(args[0]);
		ProjectParameters.setReportPath(args[1]);
		
		//预处理 pre
		new PreScheduler().schdular();
		
		//处理ipa文件
		new AnalyseScheduler().schdular();
		
//		new WriteXml().write();
		WritePdf.write();
		
	}

}
