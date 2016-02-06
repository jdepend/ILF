package com.qeweb.busplatform.masterdata.template;

import java.io.File;
import java.util.Map;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.common.utils.excel.ExcelUtil;
import com.qeweb.framework.exception.BOException;

/**
 * excel模板创建者
 */
public abstract class TemplateCreater {

	//导入模版存放目录
	public final static String IMPORT_TEMPLATE_DIR = "import_template";

	/**
	 * 根据模板名称模板FileBOP
	 * @return
	 */
	public FileBOP createTemplateBOP() {
		File file = new File(FileHandleHelp.makdSaveDir(getTmplateDir()), getTemplateName());
		FileBOP bop = new FileBOP();
		if(file.exists()) {
			bop.setDisplayName(getTemplateName());
			bop.setPath(getTmplateDir()+ getTemplateName());
			bop.setFile(file);
		}

		return bop;
	}

	/**
	 * 生成模版
	 * @return
	 * @throws Exception
	 */
	public void createTemplate(BusinessObject bo) throws Exception{
		File savefile = ExcelUtil.createExcel(getTemplateName(), getTmplateDir(), getTemplateTitle());
		if(savefile == null)
			throw new BOException("生成失败");
	}

	/**
	 * 模板名称
	 * @return
	 */
	protected abstract String getTemplateName();

	/**
	 * 获取模板内容: 批注和标题
	 * @return key: 批注  value :标题.
	 * <ul>例: 物料excel模板的单元格内容是 物料名称, 物料编码, 物料价格, 它们对应的字段是name, code, price.
	 * 此时批注为:name, code, price; 标题为: 物料名称, 物料编码, 物料价格.
	 */
	protected abstract Map<String, String> getTemplateTitle();

	/**
	 * 模版文件存放路径
	 * @return
	 */
	public static String getTmplateDir() {
		return FileHandleHelp.getFilePath(IMPORT_TEMPLATE_DIR, false);
	}
}
