package com.qeweb.framework.impconfig.mdt.pageflow.bo;

import java.util.LinkedList;
import java.util.List;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBopDec;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.PageflowFileBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.PageflowTypeBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrBindBopBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrBtnNameBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrDialogHeightBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrDialogTitleBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrDialogWidthBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrIsClosePage;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrIsPopBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrSourcePageBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrStatusBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.bop.pfpoprelations.PFAttrTargetVCBOP;
import com.qeweb.framework.impconfig.mdt.pageflow.dao.ia.IPageflowConfDao;
import com.qeweb.framework.impconfig.promodule.bop.ProModuleBOP;
import com.qeweb.framework.impconfig.promodule.dao.ia.IProModuleDao;

/**
 * 页面流配置
 *
 */
public class PageflowConfigBO extends BusinessObject {
	
	private static final long serialVersionUID = -5719344967350480393L;
	
	private String moduleId;					//模块Id
	private String fileName;					//页面流文件名
	private String filePath;					//页面流文件路径
	private String boId;						//bo
	private String bindBop;						//<qeweb:source> 绑定的bop
	private String btnName;						//按钮name
	private String sourcePage;					//源jsp
	private String targetPage;					//目标jsp
	private String isPopup;						//是否弹出
	private String dialogWidth;					//弹出框宽度
	private String dialogHeight;				//弹出框高度
	private String dialogTitle;					//弹出框标题
	private String msgFlag;						//是否显示提示信息
	private String closePage;					//是否关闭弹出页面
	private String targetVC;					//刷新指定组件
	private String remark;						//备注
	private String pageflowType;				//
	
	private IProModuleDao proModuleDao;
	private IPageflowConfDao pageflowDao;

	/*old属性在修改页面使用，用于判断是否修改了页面流节点的标识信息，存在如下两种组成页面流的唯一标识情况
	 * 1、boId + btnName + sourcePage
	 * 2、boId + bindBop + sourcePage_old
	 */
	private String boId_old;					//bo
	private String bindBop_old;					//<qeweb:source> 绑定的bop
	private String btnName_old;					//按钮name
	private String sourcePage_old;				//源jsp
	
	
	public PageflowConfigBO(){
		super();
		addBOP("moduleId", new ProModuleBOP());
		addBOP("fileName", new PageflowFileBOP());
		addBOP("boId", new NotEmptyBop(1, 50));
		addBOP("bindBop", new PFAttrBindBopBOP());
		addBOP("btnName", new PFAttrBtnNameBOP());
		addBOP("sourcePage", new PFAttrSourcePageBOP());
		addBOP("targetPage", new EmptyBop(200));	
		addBOP("targetVC", new PFAttrTargetVCBOP());	
		addBOP("msgFlag", new PFAttrStatusBOP());
		addBOP("closePage", new PFAttrIsClosePage());
		addBOP("isPopup", new PFAttrIsPopBOP());
		addBOP("dialogWidth", new PFAttrDialogWidthBOP());
		addBOP("dialogHeight", new PFAttrDialogHeightBOP());
		addBOP("dialogTitle", new PFAttrDialogTitleBOP());
		addBOP("remark", new EmptyBop(1000));	
		addBOP("pageflowType", new NotEmptyBopDec(new PageflowTypeBOP()));
		addOperateBOP("goback", new NOSubmitBOP());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate pfFileBOT = new BOTemplate();
		if(StringUtils.isNotEmpty((String)bot.getValue("moduleId")))
			pfFileBOT.push("moduleId", (String)bot.getValue("moduleId"));
		Page pfFiles = new PageflowFileBO().query(pfFileBOT, start);
		if(pfFiles == null)
			return null;
	
		List<PageflowConfigBO> pfConfs = getPageflowDao().findPFItems(bot, pfFiles.getItems());
		Page result = new Page(pfConfs, pfConfs.size(), getPageSize(), start);
		initPreferencePage(result);
		
		return result;
	}
	
	public BusinessObject toTypeOneAndHasTargetPage(){
		this.getBOP("bindBop").setValue("");
		this.getBOP("targetVC").setValue("");
		return this;
	}

	/**
	 * 跳转到新增页面
	 * @return
	 */
	public PageflowConfigBO toInsert() {
		PageflowConfigBO bo = new PageflowConfigBO();
		
		bo.getBOP("bindBop").getStatus().setHidden(true);
		bo.getBOP("btnName").getStatus().setHidden(true);
		bo.getBOP("targetVC").getStatus().setHidden(true);
		bo.getBOP("dialogWidth").getStatus().setHidden(true);
		bo.getBOP("dialogHeight").getStatus().setHidden(true);
		bo.getBOP("dialogTitle").getStatus().setHidden(true);
		
		return bo;
	}

	/**
	 * 跳转到修改页面
	 * @return
	 */
	public PageflowConfigBO toUpdate(PageflowConfigBO bo) {
		bo.getBOP("moduleId").getStatus().setReadonly(true);
		bo.getBOP("moduleId").getRange().setRequired(false);
		bo.getBOP("fileName").getStatus().setReadonly(true);
		bo.getBOP("fileName").getRange().setRequired(false);
		
		bo.setBoId_old(bo.getBoId());
		bo.setSourcePage_old(bo.getSourcePage());
		if(StringUtils.isNotEmpty(bo.getBtnName())){
			bo.getBOP("bindBop").getStatus().setHidden(true);
			bo.getBOP("btnName").getRange().setRequired(true);
			bo.getBOP("sourcePage").getRange().setRequired(true);
			bo.setBtnName_old(bo.getBtnName());
			bo.setPageflowType(PageflowTypeBOP.TYPE_ONE);
		}
		else{
			bo.getBOP("btnName").getStatus().setHidden(true);
			bo.getBOP("targetVC").getStatus().setHidden(true);
			bo.getBOP("bindBop").getRange().setRequired(true);
			bo.setBindBop_old(bo.getBindBop());
			bo.setPageflowType(PageflowTypeBOP.TYPE_TWO);
		}
		
		if(StringUtils.convertToBool(bo.getClosePage())){
			bo.getBOP("isPopup").getStatus().setHidden(true);
			bo.getBOP("dialogWidth").getStatus().setHidden(true);
			bo.getBOP("dialogHeight").getStatus().setHidden(true);
			bo.getBOP("dialogTitle").getStatus().setHidden(true);
		}
		else if(!StringUtils.convertToBool(bo.getIsPopup())){
			bo.getBOP("dialogWidth").getStatus().setHidden(true);
			bo.getBOP("dialogHeight").getStatus().setHidden(true);
			bo.getBOP("dialogTitle").getStatus().setHidden(true);
		}
		
		BOHelper.initPreferencePage(bo);
		
		return bo;
	}
	
	/**
	 * 跳转到查看页面
	 * @return
	 */
	public PageflowConfigBO toView(PageflowConfigBO bo) {
		bo.getStatus().setDisable(true);
		return bo;
	}
	
	/**
	 * 新增页面流记录
	 * @param pageFlowConfBO
	 * @throws Exception
	 */
	public void insert(PageflowConfigBO pageFlowConfBO) throws Exception {
		pageFlowConfBO.setFilePath(pageFlowConfBO.getFileName());
		if(getPageflowDao().isExists(pageFlowConfBO)) 
			throw new BOException("已存在的页面流记录相同！");
		else 
			getPageflowDao().insert(pageFlowConfBO);
	}
	
	/**
	 * 修改页面流记录
	 * @param pageFlowConfBO
	 * @throws Exception
	 */
	public void update(PageflowConfigBO pageFlowConfBO) throws Exception {
		//判断是未修改
		if(StringUtils.isEqual(pageFlowConfBO.getBoId(), pageFlowConfBO.getBoId_old())
				&& StringUtils.isEqual(pageFlowConfBO.getBindBop(), pageFlowConfBO.getBindBop_old())
				&& StringUtils.isEqual(pageFlowConfBO.getBtnName(), pageFlowConfBO.getBtnName_old())
				&& StringUtils.isEqual(pageFlowConfBO.getSourcePage(), pageFlowConfBO.getSourcePage_old()))
			getPageflowDao().update(pageFlowConfBO);
		//如果修改boid, btnName, sourcePage，需要判断新节点标识是否唯一
		else if(getPageflowDao().isExists(pageFlowConfBO))
			throw new BOException("已存在的页面流记录相同！");
		else 
			getPageflowDao().update(pageFlowConfBO);
	}

	@Override
	public void delete(List<BusinessComponent> pageFlowConfBOList) throws Exception {
		List<PageflowConfigBO> list = new LinkedList<PageflowConfigBO>();
		for(BusinessComponent bc : pageFlowConfBOList) {
			list.add((PageflowConfigBO) bc);
		}
		
		getPageflowDao().delete(list);
	}
	
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}

	public String getTargetPage() {
		return targetPage;
	}

	public void setTargetPage(String targetPage) {
		this.targetPage = targetPage;
	}

	public String getBoId() {
		return boId;
	}

	public void setBoId(String boId) {
		this.boId = boId;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(String isPopup) {
		this.isPopup = isPopup;
	}

	public String getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(String dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public String getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(String dialogHeight) {
		this.dialogHeight = dialogHeight;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	public IPageflowConfDao getPageflowDao() {
		return pageflowDao;
	}

	public void setPageflowDao(IPageflowConfDao pageflowDao) {
		this.pageflowDao = pageflowDao;
	}

	public IProModuleDao getProModuleDao() {
		return proModuleDao;
	}

	public void setProModuleDao(IProModuleDao proModuleDao) {
		this.proModuleDao = proModuleDao;
	}

	public String getClosePage() {
		return closePage;
	}

	public void setClosePage(String closePage) {
		this.closePage = closePage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBoId_old() {
		return boId_old;
	}

	public void setBoId_old(String boId_old) {
		this.boId_old = boId_old;
	}

	public String getBtnName_old() {
		return btnName_old;
	}

	public void setBtnName_old(String btnName_old) {
		this.btnName_old = btnName_old;
	}

	public String getSourcePage_old() {
		return sourcePage_old;
	}

	public void setSourcePage_old(String sourcePage_old) {
		this.sourcePage_old = sourcePage_old;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTargetVC() {
		return targetVC;
	}

	public void setTargetVC(String targetVC) {
		this.targetVC = targetVC;
	}

	public String getBindBop() {
		return bindBop;
	}

	public void setBindBop(String bindBop) {
		this.bindBop = bindBop;
	}

	public String getBindBop_old() {
		return bindBop_old;
	}

	public void setBindBop_old(String bindBop_old) {
		this.bindBop_old = bindBop_old;
	}

	public String getPageflowType() {
		return pageflowType;
	}

	public void setPageflowType(String pageflowType) {
		this.pageflowType = pageflowType;
	}
	
}
