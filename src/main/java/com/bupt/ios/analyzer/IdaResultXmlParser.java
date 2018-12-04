package com.bupt.ios.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.bupt.ios.commonData.ProjectParameters;

public class IdaResultXmlParser {


	/**
	 * 读取bl.xml文件（IDAPython解析结果）
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static void parseBlXml(String msgPath) throws JDOMException, IOException{
//		String msgPath = "/Users/konghaohao/Documents/git_workspace/ios-vulnerability-detection/detemp/2016-09-08-15-28-40/ida/bl.xml";
		SAXBuilder saxBuilder = new SAXBuilder();
		Document docment = saxBuilder.build(new File(msgPath));
		Element root = docment.getRootElement();
		List<Element> funList = root.getChildren("function");
		for(Element funEle:funList){
			String funName = funEle.getAttributeValue("name");
			List<BlMsg> valueList = new ArrayList<BlMsg>();
			List<Element> bList = funEle.getChildren("b");
			for(Element bEle:bList){
				BlMsg blMsg = new BlMsg();
				blMsg.setAddr(bEle.getAttributeValue("addr"));
				blMsg.setName(bEle.getAttributeValue("name"));
				Map<String, String> regMap = new HashMap<String, String>();
				String regContent = bEle.getText().trim();
				String[] eachReg = regContent.split("\n");
				for(String reg:eachReg)
				{
//					System.out.println(reg);
					int sepIndex = reg.indexOf("=");
					String key = reg.substring(0, sepIndex);
					String value = reg.substring(sepIndex+1, reg.length()).trim();
					if(value.startsWith("_"))
							value = value.substring(1);
//					System.out.println(key+" "+value);
					regMap.put(key, value);
				}
				
				blMsg.setRegs(regMap);
				valueList.add(blMsg);
			}
			IdaResultSet.addMSG(funName, valueList);
		}
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//				try {
//					IdaResultXmlParser.parseBlXml();
//				} catch (JDOMException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		System.out.println(IdaResultSet.getMSG());
//	}

}
