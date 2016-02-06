package com.qeweb.framework.base.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.file.FileUtil;

public class XmlDaoImpl implements IXmlDao {
	
	private static final long serialVersionUID = -4661475873702952267L;

	@Override
	public Document buildDomFromFile(String filePath) {
		if(StringUtils.isEmpty(filePath))
			return null;
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = builder.build(new File(filePath));
			return anotherDocument;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Document buildDomFromXMLStr(String xmlString) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = builder.build(new StringReader(xmlString));
			return anotherDocument;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void outPutElementToFile(Element element, String filePath) {
		try {
			Format format = Format.getCompactFormat();
			format.setEncoding("UTF-8");
			 //设置缩进
	        format.setIndent("    ");
			Document doc = new Document(element);
			XMLOutputter out = new XMLOutputter(format);
			out.output(doc, new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Element> getElmentsByXpath(String xpath, Element root) {
		List<Element> elements = null; 
		try {              
			List<Element> selectNodes = XPath.selectNodes(root, xpath);
			elements = selectNodes;
		} catch (JDOMException e) {              
			e.printStackTrace();
		}  
		return elements; 
	}

	@Override
	public void editElementAttribute(Element element, String attrName, String attrValue) {
		element.getAttribute(attrName).setValue(attrValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Element> getElmentsByNodeName(String nodeName, Element rootElement) {
		return rootElement.getChildren(nodeName);
	}

	@Override
	public void outPutDocToFile(Document doc, String filePath) {
		try {
			if(!FileUtil.isFile(filePath))
				return;
			XMLOutputter out = new XMLOutputter();
			out.output(doc, new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Element getRootElement(String clientPath, String serverPath) {
		try {
			String path = clientPath;
			if(!FileUtil.isFile(path))
				path = serverPath;
			
			Document doc = buildDomFromFile(path);
			if(doc == null)
				return null;
			
			Element rootElement = doc.getRootElement();
			return rootElement;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Element getElementByXpath(Element rootElement, String xpath)
			throws Exception {
		List<Element> elements = getElmentsByXpath(xpath, rootElement);
		if(ContainerUtil.isNull(elements))
			return null;
		
		for(Element el : elements) {
			if(StringUtils.isEmpty(el.getAttributeValue(IBaseDao.FIELD_DELETEFLAG)) 
					|| StringUtils.isEqual(IBaseDao.UNDELETE_SIGNE + "", el.getAttributeValue(IBaseDao.FIELD_DELETEFLAG))) {
				return el;
			}
		}
		
		return null;
	}
}
