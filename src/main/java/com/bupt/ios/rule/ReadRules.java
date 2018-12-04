package com.bupt.ios.rule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.bupt.ios.commonData.CommonData;



public class ReadRules {
	private static String fileSeparator = System.getProperty("file.separator");
	private static Logger logger = LogManager.getLogger(ReadRules.class);
	
	
	/**
	 * 从工程目录下读取rule文件夹下的rules，存入CommonData.rulePool
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static void readRules() throws JDOMException, IOException{
		String rulePath = System.getProperty("user.dir") + fileSeparator +"rule";
		logger.info(rulePath);
		
		for(File ruleFile : new File(rulePath).listFiles()){
			SecRule secRule = new SecRule();
			if(ruleFile.getName().endsWith("xml")){
				SAXBuilder saxBuilder = new SAXBuilder();
				Document document = saxBuilder.build(ruleFile);
				Element root = document.getRootElement();
				Element ruleDefinitions = root.getChild("RuleDefinitions");
//				List<Element> list = ruleDefinitions.getChildren();
				//获取规则的属性
				secRule.setRuleId(ruleDefinitions.getChildText("RuleID"));
				secRule.setRuleName(ruleDefinitions.getChildText("RuleName"));
				secRule.setDescription(ruleDefinitions.getChildText("Description"));
				secRule.setRuleType(Integer.parseInt(ruleDefinitions.getChildText("RuleType")));
				secRule.setRiskLevel(Integer.parseInt(ruleDefinitions.getChildText("riskLevel")));
				secRule.setSolution(ruleDefinitions.getChildText("solution"));
				secRule.setEnable(ruleDefinitions.getChildText("isEnable").equals("true")?true:false);
				if(!secRule.isEnable()){
					continue;
				}
				//解析Content
				Element content  = ruleDefinitions.getChild("Content");
				List<Function>funList = new ArrayList<Function>();
				List<Element>funEleList = content.getChildren("Function");
				for(Element funEle : funEleList){
					Function function = new Function();
					function.setFuncType(funEle.getAttribute("type").getValue().equals("true")?true:false);
					function.setFunctionName(funEle.getAttribute("name").getValue());
					if(funEle.getChild("Parameter")!=null){
						List<Element> paraEleList = funEle.getChildren("Parameter");
						Map<String, String> parasMap = new HashMap<String, String>();
						for(Element paraEle: paraEleList){
							String paraStr = paraEle.getText();
							parasMap.put(paraStr.split("=")[0], paraStr.split("=")[1]);
						}
						function.setParameters(parasMap);
					}
					funList.add(function);
				}
				secRule.setContent(funList);
//				System.out.println();
				
				//add to SecRulePool
				//将1类规则加入rulePool1 2类规则加入rulePool2
				if(secRule.getRuleType()==1)
					CommonData.addSecRule1(secRule);
				else
					CommonData.addSecRule2(secRule);
			}
		}
	}
	
//	public static void main(String[] args) {
//		try {
//			new ReadRules().readRues();
//		} catch (JDOMException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
