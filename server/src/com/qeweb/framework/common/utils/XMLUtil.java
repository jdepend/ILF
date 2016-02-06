package com.qeweb.framework.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.dataisland.XMLDataIslandHelper;

/**
 * XML文件操作工具
 */
public class XMLUtil {
	/**
	 * XML 转换至 String
	 * 
	 * @param doc
	 * @return
	 */
	public final static String xmlToString(Document doc) {
		return new XMLOutputter().outputString(doc);
	}

	/**
	 * String 转换至 XML
	 * 
	 * @param dataIsland
	 * @return
	 */
	public final static Document stringToXml(String dataIsland) {
		if (StringUtils.isEmptyStr(dataIsland))
			return null;

		Document doc = null;
		// 火狐创建xml时，会自动增加a0的命名空间
		dataIsland = dataIsland.replace("a0:", "").replace(":a0", "")
				.replace(" xmlns=\"\"", "");
		dataIsland = dataIsland.replace(
				" xmlns=\"http://www.w3.org/1999/xhtml\"", "");
		// 火狐和谷歌数据岛<![CDATA[]]>会被解析为<!--[CDATA[]]-->
		dataIsland = dataIsland.replace("<!--[CDATA[", "<![CDATA[").replace(
				"]]-->", "]]>");
		try {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(new InputSource(new StringReader(dataIsland)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	/**
	 * 通过文件相对路径取得xml文件
	 * 
	 * @param filePath
	 *            相对路径 例："com/qeweb/demo/bo/DemoBO_ShowAllFC.xml";
	 * @return
	 */
	public final static Document getXmlFile(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return null;
		File file = new File(filePath);
		Document doc = null;

		SAXBuilder builder = new SAXBuilder();
		try {
			if (file.exists()) {
				doc = builder.build(file);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 将文件转换为xml对象
	 * 
	 * @param file
	 *            加载文件 例："com/qeweb/demo/bo/DemoBO_ShowAllFC.xml";
	 * @return
	 */
	public final static Document getXmlFile(File file) {
		if (file == null)
			return null;
		Document doc = null;

		SAXBuilder builder = new SAXBuilder();
		try {
			if (file.exists() && file.isFile()) {
				doc = builder.build(file);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return doc;
	}

	/**
	 * 将Element转换成str
	 */
	@SuppressWarnings("unchecked")
	public final static String ElToStr(Element el) {
		StringBuilder sbr = new StringBuilder();
		if (el == null)
			return "";

		XMLDataIslandHelper.appendStartTag(sbr, el.getName());
		List<Attribute> attrs = el.getAttributes();
		for (Attribute attr : attrs) {
			XMLDataIslandHelper
					.appendAttr(sbr, attr.getName(), attr.getValue());
		}

		List<Element> children = el.getChildren();
		if (ContainerUtil.isNotNull(children)) {
			XMLDataIslandHelper.appendEndTag(sbr);
			for (Element child : children) {
				sbr.append(ElToStr(child));
			}
			XMLDataIslandHelper.appendEndTag(sbr, el.getName());
			return sbr.toString();
		}

		if (StringUtils.isEmpty(el.getText())) {
			XMLDataIslandHelper.appendSingleEndTag(sbr);
		} else {
			XMLDataIslandHelper.appendEndTag(sbr);
			XMLDataIslandHelper.appendContent(sbr, el.getText());
			XMLDataIslandHelper.appendEndTag(sbr, el.getName());
		}

		return sbr.toString();
	}

	public static String decode(String str) {
		if (str == null)
			return null;
		return StringUtils.trim(str).replaceAll(":amp;", "&")
				.replaceAll("%2525", "%").replaceAll("%2B", "+")
				.replaceAll(":apos;", "'").replaceAll(":quot;", "\"");
	}

	/**
	 * 修改xml文件
	 * 
	 * @param file
	 * @param document
	 */
	final static public void modifyXML(File file, Document document) {
		try {
			if (file.exists()) {
				String xmlFileData = xmlToString(document);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(xmlFileData
						.getBytes(Constant.ENCODING_UTF8));
				fileOutputStream.close();

			} else {
				System.out.println("File does not exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encode(String str) {
		if (StringUtils.isNotEmpty(str)) {
			return str.replaceAll("&", ":amp;").replaceAll("'", ":apos;")
					.replaceAll("\"", ":quot;");
		}
		return "";
	}

	/**
	 * 获取xml文件的rootElemont(当xml文件在jar包中时使用)
	 * @param clasz		读取xml的类
	 * @param xmlPath	xml文件对应clasz的相对路径
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes") 
	final static public Element getXmlRootElement(Class clasz, String xmlPath) throws IOException {
		SAXBuilder builder = new SAXBuilder();
		
		Document doc = null;
		try {
			doc = builder.build(clasz.getResourceAsStream("demoMenu.xml"));
			return doc.getRootElement();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
