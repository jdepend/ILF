package com.qeweb.framework.app.pageflow;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;

import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.XMLUtil;
import com.qeweb.framework.common.utils.file.FileUtil;

/**
 * 
 * 页面流PoolXML读取
 */
public class PageFlowPoolHelper {
	
	private static final String PAGEFLOW = "pageflow";

	private static final String PAGEFLOW_BO_ID = "boId";
	private static final String PAGEFLOW_SOURCE_PAGE = "sourcePage";
	private static final String PAGEFLOW_TARGET_PAGE = "targetPage";
	private static final String PAGEFLOW_BTN_NAME = "btnName";
	private static final String PAGEFLOW_BOP_BIND = "bopBind";
	private static final String PAGEFLOW_CLOSE_PAGE = "closePage";
	private static final String PAGEFLOW_IS_POPUP = "isPopup";
	private static final String PAGEFLOW_HAS_CLOSEBTN = "hasCloseBtn";
	private static final String PAGEFLOW_DIALOG_WIDTH = "dialogWidth";
	private static final String PAGEFLOW_DIALOG_HEIGHT = "dialogHeight";
	private static final String PAGEFLOW_DIALOG_TITLE = "dialogTitle";
	private static final String PAGEFLOW_DIALOG_ICON = "dialogIcon";
	private static final String PAGEFLOW_MSG_FLAG = "msgFlag";
	private static final String PAGEFLOW_TARGET_VC = "targetVC";
	private static final String PAGEFLOW_CLOSE_TARGET_VC = "closeTargetVC";
	private static final String PAGEFLOW_ECHO = "echo";
	
	private static final String middlePath = "/WEB-INF/classes";
	
	/**
	 * 从指定包中读取页面流配置信息，将其存储map中，在使用时通过key进行查找
	 * 该包的相对路径可以在application.properties中配置，属性名称为pageflowPath
	 * @return
	 */
	final public static Map<String, SysPageflow> loadPagePool() {
		Map<String, SysPageflow> targetPagePool = new HashMap<String, SysPageflow>();
		for(SysPageflow pageflow : reloadPageFlow()){
			if(StringUtils.isNotEmpty(pageflow.getBtnName()))
				targetPagePool.put(getPageFlowKey(pageflow), pageflow);
			else if(StringUtils.isNotEmpty(pageflow.getBindBop()))
				targetPagePool.put(geturceBtnPageFlowKey(pageflow), pageflow);
		}
		
		return targetPagePool;
	}

	/**
	 * 普通按钮的页面流标识: boid-btnName-sourcePage
	 * @param pageflow
	 * @return
	 */
	private static String getPageFlowKey(SysPageflow pageflow) {
		return pageflow.getBoId() + ConstantSplit.GA_PARAM_SPLIT
				+ pageflow.getBtnName() + ConstantSplit.GA_PARAM_SPLIT
				+ pageflow.getSourcePage();
	}
	
	/**
	 * 弹出选择按钮的页面流标识: boid-bopId 或 boid-bopid-sourcePage
	 * @param pageflow
	 * @return
	 */
	private static String geturceBtnPageFlowKey(SysPageflow pageflow) {
		if(StringUtils.isEmpty(pageflow.getSourcePage()))
			return pageflow.getBoId() + ConstantSplit.GA_PARAM_SPLIT + pageflow.getBindBop();
		else
			return pageflow.getBoId() + ConstantSplit.GA_PARAM_SPLIT
				+ pageflow.getBindBop() + ConstantSplit.GA_PARAM_SPLIT
				+ pageflow.getSourcePage();
	}
	
	/**
	 * 从指定包中读取页面流配置信息
	 * @return
	 */
	private static Set<SysPageflow> reloadPageFlow(){
		Set<SysPageflow> set = new LinkedHashSet<SysPageflow>();
		File[] configFiles = getXMLFiles();
		if(configFiles == null)
			return set;
		
		for(File file : configFiles) {
			if(!file.exists())
				continue;
			
			if(file.isFile()) {
				set.addAll(reloadXMLFile(file));
			}
			else {
				List<File> files = FileUtil.getAllFiles(file);
				if(ContainerUtil.isNull(files))
					continue;
				
				for(File f : files) {
					set.addAll(reloadXMLFile(f));
				}
			}
		}
		
		return set;
	}

	/**
	 * 从指定包中读取页面所有的文件
	 * @return
	 */
	private static File[] getXMLFiles() {
		String realPath = Envir.getContext().getRealPath("/");
		return FileUtil.getfiles(realPath + middlePath + AppConfig.getPropValue(AppConfig.PAGEFLOW_PATH));
	}

	/**
	 * 解析单个配置文件，获取其中的配置流信息
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Set<SysPageflow> reloadXMLFile(File file) {
		Set<SysPageflow> set = new LinkedHashSet<SysPageflow>();
		Document doc = XMLUtil.getXmlFile(file);
		if(doc == null)
			return set;
		
		Element rootElement = doc.getRootElement();
		for(Element pageflowEl : (List<Element>)rootElement.getChildren(PAGEFLOW)){
			SysPageflow pageflow = reloadPageflow(pageflowEl);
			if(pageflow != null)
				set.add(pageflow);
		}
		
		return set;
	}
	
	/**
	 * 将XML中的配置信息组装成页面流对象
	 * @param pageflowEl
	 * @return
	 */
	private static SysPageflow reloadPageflow(Element pageflowEl) {
		SysPageflow pageflow = new SysPageflow();

		pageflow.setBtnName(pageflowEl.getAttributeValue(PAGEFLOW_BTN_NAME));
		pageflow.setBindBop(pageflowEl.getAttributeValue(PAGEFLOW_BOP_BIND));
		pageflow.setBoId(pageflowEl.getAttributeValue(PAGEFLOW_BO_ID));
		pageflow.setSourcePage(pageflowEl.getAttributeValue(PAGEFLOW_SOURCE_PAGE));
		pageflow.setTargetPage(pageflowEl.getAttributeValue(PAGEFLOW_TARGET_PAGE));
		pageflow.setClosePage(StringUtils.convertToBool(pageflowEl.getAttributeValue(PAGEFLOW_CLOSE_PAGE)));			
		pageflow.setPopup(StringUtils.convertToBool(pageflowEl.getAttributeValue(PAGEFLOW_IS_POPUP)));
		pageflow.setHasCloseBtn(isHasCloseBtn(pageflowEl.getAttributeValue(PAGEFLOW_HAS_CLOSEBTN)));
		pageflow.setMsgFlag(StringUtils.convertToBool(pageflowEl.getAttributeValue(PAGEFLOW_MSG_FLAG)));
		pageflow.setDialogWidth(pageflowEl.getAttributeValue(PAGEFLOW_DIALOG_WIDTH));
		pageflow.setDialogHeight(pageflowEl.getAttributeValue(PAGEFLOW_DIALOG_HEIGHT));
		pageflow.setDialogTitle(pageflowEl.getAttributeValue(PAGEFLOW_DIALOG_TITLE));
		pageflow.setDialogIcon(pageflowEl.getAttributeValue(PAGEFLOW_DIALOG_ICON));
		pageflow.setTargetVC(pageflowEl.getAttributeValue(PAGEFLOW_TARGET_VC));
		pageflow.setEcho(pageflowEl.getAttributeValue(PAGEFLOW_ECHO));
		pageflow.setCloseTargetVC(pageflowEl.getAttributeValue(PAGEFLOW_CLOSE_TARGET_VC));

		return pageflow;
	}
	
	/**
	 * 弹出页面是否带有关闭按钮.
	 * 默认值根据全局配置而定. hasCloseBtn仅在isPopup=true时有效.
	 * 如果配置了hasCloseBtn, 则hasCloseBtn优先于全局配置.
	 * @param hasCloseBtn
	 * @return
	 */
	private static boolean isHasCloseBtn(String hasCloseBtn) {
		return StringUtils.isEmpty(hasCloseBtn) ? AppConfig.hasCloseBtn() : StringUtils.convertToBool(hasCloseBtn);
	}
}
