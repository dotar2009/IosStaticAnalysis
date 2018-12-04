package com.bupt.ios.report;

import com.bupt.ios.analyzer.IdaResultSqlParser;
import com.bupt.ios.commonData.ProjectParameters;
import com.bupt.ios.rule.SecRule;
import com.bupt.ios.scheduler.PreScheduler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.OutputKeys;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.List;


public class WriteXml {
	private static String fileSeparator = System.getProperty("file.separator");
	
	public void write() {
		String reportpath = ProjectParameters.getReportPath();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			
			Element root = doc.createElement("report");
			doc.appendChild(root);
			
			//type 1
			Element re1 = doc.createElement("unsecure_match");
			root.appendChild(re1);
			for(String funcname : ReportSet.getRe1().keySet()){
				List<SecRule> rules = ReportSet.getRe1().get(funcname);
				
				Element funcElement = doc.createElement("Function");
				Attr attr = doc.createAttribute("name");
				attr.setValue(funcname);
				funcElement.setAttributeNode(attr);
				re1.appendChild(funcElement);
				
				for(SecRule rule : rules){
					Element ruleElement = doc.createElement("Matched_rule");
					funcElement.appendChild(ruleElement);
					Attr attr_id = doc.createAttribute("id");
					attr_id.setValue(rule.getRuleId());
					ruleElement.setAttributeNode(attr_id);
					
					Attr attr_name = doc.createAttribute("name");
					attr_name.setValue(rule.getRuleName());
					ruleElement.setAttributeNode(attr_name);
					
					Attr attr_des = doc.createAttribute("description");
					attr_des.setValue(rule.getDescription());
					ruleElement.setAttributeNode(attr_des);
					
					Attr attr_risk = doc.createAttribute("risk_level");
					attr_risk.setValue(rule.getRiskLevel() + "");
					ruleElement.setAttributeNode(attr_risk);
					
					Attr attr_solution = doc.createAttribute("solution");
					attr_solution.setValue(rule.getSolution());
					ruleElement.setAttributeNode(attr_solution);
					
				}
				
			}
			
			// 匹配安全的策略
			Element secure = doc.createElement("secure_match");
			root.appendChild(secure);
			for(String funcname : ReportSet.getProtectInfoMap().keySet()){
				List<SecRule> rules = ReportSet.getProtectInfoMap().get(funcname);
				
				Element funcElement = doc.createElement("Function");
				Attr attr = doc.createAttribute("name");
				attr.setValue(funcname);
				funcElement.setAttributeNode(attr);
				secure.appendChild(funcElement);
				
				for(SecRule rule : rules){
					Element ruleElement = doc.createElement("Matched_rule");
					funcElement.appendChild(ruleElement);
					Attr attr_id = doc.createAttribute("id");
					attr_id.setValue(rule.getRuleId());
					ruleElement.setAttributeNode(attr_id);
					
					Attr attr_name = doc.createAttribute("name");
					attr_name.setValue(rule.getRuleName());
					ruleElement.setAttributeNode(attr_name);
					
					Attr attr_des = doc.createAttribute("description");
					attr_des.setValue(rule.getDescription());
					ruleElement.setAttributeNode(attr_des);
					
					Attr attr_risk = doc.createAttribute("risk_level");
					attr_risk.setValue(rule.getRiskLevel() + "");
					ruleElement.setAttributeNode(attr_risk);
					
					Attr attr_solution = doc.createAttribute("solution");
					attr_solution.setValue(rule.getSolution());
					ruleElement.setAttributeNode(attr_solution);
					
				}
				
			}
			
			// 未匹配上的安全策略
			Element secure_not_match = doc.createElement("secure_not_match");
			root.appendChild(secure_not_match);
			
			for(SecRule rule : ReportSet.getRe2()){
				Element ruleElement = doc.createElement("rule");
				secure_not_match.appendChild(ruleElement);

				Attr attr_id = doc.createAttribute("id");
				attr_id.setValue(rule.getRuleId());
				ruleElement.setAttributeNode(attr_id);

				Attr attr_name = doc.createAttribute("name");
				attr_name.setValue(rule.getRuleName());
				ruleElement.setAttributeNode(attr_name);
				
				Attr attr_des = doc.createAttribute("description");
				attr_des.setValue(rule.getDescription());
				ruleElement.setAttributeNode(attr_des);

				Attr attr_risk = doc.createAttribute("risk_level");
				attr_risk.setValue(rule.getRiskLevel() + "");
				ruleElement.setAttributeNode(attr_risk);

				Attr attr_solution = doc.createAttribute("solution");
				attr_solution.setValue(rule.getSolution());
				ruleElement.setAttributeNode(attr_solution);		
			}
				
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); 
			DOMSource source = new DOMSource(doc);
			File xmlFile = new File(reportpath + fileSeparator + ProjectParameters.getCurrentime() + ".xml");
			if (!xmlFile.exists()){
				xmlFile.createNewFile();
			}
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
			// Output to console for testing
//			StreamResult consoleResult = new StreamResult(System.out);
//			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	public static void main(String argv[]) {
		ProjectParameters.setReportPath("/Users/gjy/Desktop/iOS_static_analyzer/report");
		
		new PreScheduler().schdular();
		IdaResultSqlParser.parseBLSql("/Users/gjy/Desktop/iOS_static_analyzer/temp/bl.db");
		new WriteXml().write();
	}
} 
	
//	 //  supercars element
//	 Element supercar = doc.createElement("supercars");
//	 rootElement.appendChild(supercar);
//	
//	 // setting attribute to element
//	 Attr attr = doc.createAttribute("company");
//	 attr.setValue("Ferrari");
//	 supercar.setAttributeNode(attr);
//	
//	 // carname element
//	 Element carname = doc.createElement("carname");
//	 Attr attrType = doc.createAttribute("type");
//	 attrType.setValue("formula one");
//	 carname.setAttributeNode(attrType);
//	 carname.appendChild(
//	 doc.createTextNode("Ferrari 101"));
//	 supercar.appendChild(carname);
//	
//	 Element carname1 = doc.createElement("carname");
//	 Attr attrType1 = doc.createAttribute("type");
//	 attrType1.setValue("sports");
//	 carname1.setAttributeNode(attrType1);
//	 carname1.appendChild(
//	 doc.createTextNode("Ferrari 202"));
//	 supercar.appendChild(carname1);
	
	 // write the content into xml file
	 

