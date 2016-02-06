package com.qeweb.framework.base.ia;

import java.io.Serializable;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;


/**
 * 解析XML文件接口
 */
public interface IXmlDao extends Serializable {
	
	final static int UNDELETE_SIGNE = IBaseDao.UNDELETE_SIGNE;
	final static int DELETE_SIGNE = IBaseDao.DELETE_SIGNE;
	
	/**
	 * 根据指定路径的XML文件建立JDom对象
	 * 
	 * @param filePath
	 *            XML文件的路径
	 * @return 返回建立的JDom对象，建立不成功返回null 
	 */
	Document buildDomFromFile(String filePath);

	 /**
	  * 根据XML 字符串 建立JDom对象
	  *
	  * @param xmlString
	  *            XML格式的字符串
	  * @return 返回建立的JDom对象，建立不成功返回null
	  */
	Document buildDomFromXMLStr(String xmlString);
	 
	 /**
	  * 获取rootElement下的所有节点
	  * @param xpath
	  * @param rootElement
	  * @return
	  */
	List<Element> getElmentsByXpath(String xpath, Element rootElement);
	
	/**
	 * 获取rootElement下名为nodeName的所有节点
	 * @param nodeName
	 * @param rootElement
	 * @return
	 */
	List<Element> getElmentsByNodeName(String nodeName, Element rootElement);
	 
	 /**
	  * 将节点转存为文件
	  * @param element
	  * @param filePath
	  */
	void outPutElementToFile(Element element, String filePath);
	 
	 /**
	  * 将文档转存为文件
	  * @param element
	  * @param filePath
	  */
	void outPutDocToFile(Document doc, String filePath);
	 
	 /**
	  * 修改属性值
	  * @param element
	  * @param attribute
	  * @param attValue
	  */
	void editElementAttribute(Element element, String attrName, String attrValue);
	 
	 /**
	  * 从clientPath或serverPath指定的xml中获取根节点.
	  * 如果clientPath存在, 从clientPath中获取, 否则从serverPath中获取
	  * @param clientPath
	  * @param serverPath
	  * @return
	  */
	Element getRootElement(String clientPath, String serverPath);
	 
	/**
	 * 根据xpath查询rootElement下的 Element
	 * @param rootElement
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	Element getElementByXpath(Element rootElement, String xpath) throws Exception;
}
