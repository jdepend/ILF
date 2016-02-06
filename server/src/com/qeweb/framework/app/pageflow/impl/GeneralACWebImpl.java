package com.qeweb.framework.app.pageflow.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qeweb.framework.app.pageflow.ExeBoMethodWithContextParam;
import com.qeweb.framework.app.pageflow.IGenerationAction;
import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.constant.ConstantJSON;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.dataisland.DataIsland;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.JSONUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.VCUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.exception.ExceptionUtil;
import com.qeweb.framework.log.IQewebLog;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.manager.BOManager;

/**
 * 页面流的web实现, 所有涉及到页面流程的操作(即除超连接外的逻辑控制组件触发的操作)皆由GA处理,
 * 也就是说所有涉及到页面流程的操作不再需要单独写action代码.
 * <ul>
 * <li>
 * 流程操作: 查询操作,翻页操作,增/删/改等持久化操作,通过逻辑控制组件触发的页面跳转操作,执行这些操作后可能会跳转到下个页面或更新本页的数据.
 * <li>
 * 需要注意的是超连接(即由<qeweb:anchor>标签指定的超链接), 如果超链接绑定了OperateBOP, 则表示超链接绑定了一个行为, 此时点击超链接将执行GA的execute方法;
 * <li>
 * 如果是单纯的页面跳转(这类超链接可以跳转到herf指定的任意页面, 不定义任何业务操作), 则不需要GA处理.
 * </ul>
 */
public class GeneralACWebImpl extends BaseAction implements IGenerationAction{
	private static final long serialVersionUID = 126143545L;
	private String sourceName; 		//源BC Name， 通过源BC关联目标BC
	private String targetName;		//目标BC Name， 通过源BC关联目标BC
	private String operation; 		//逻辑控制组件指定的bo操作:insert/delete/update/query 等
	private String operationScope; 	//逻辑控制组件的类型(single,list)
	private String dataIsland; 		//数据岛
	private String relations; 		//页面<qeweb:group>标签设定的关联关系
	private boolean result = false; //操作结果（默认为失败）
	private String resultStr; 		//返回值(数据岛或校验信息)
	private String redirectStr;		//跳转请求路径
	private String recordId;		//记录的Id, 通过recordId可从数据库获得唯一的bo
	private String winType;			//系统弹出框操作类型
	private String targetXML;

	private DataIsland disland = AppManager.createDataIsland();
	private static final Log log = LogFactory.getLog(GeneralACWebImpl.class);
	private IQewebLog qewebLog;

	/**
     * 执行持久化操作
     * @return null
     * @throws Exception
     */
	@Override
	public String execute() throws Exception {
    	if(StringUtils.isEmpty(sourceName))
    		return null;
    	
    	BusinessObject bo = null;
    	try {
    		String containerId = VCUtil.getDataIslandBOId(sourceName);
    		List<BusinessObject> boList = disland.revertBOList(containerId, dataIsland);
    		
    		bo = getAppropriateBO(boList);
    		ExeBoMethodWithContextParam.executeBOMethod(
    				bo, ExeBoMethodWithContextParam.getPropMethod(operation), boList);
    		setResult(bo);
    		
    		if(bo.getSuccess())
    			ContextUtil.setTipMsg(bo.getMessage());
		} catch(BOException e){
			this.result = false;
			ContextUtil.setTipMsg(e.getErrMessage());
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
			throw e;
		} catch (Exception e) {
    		log.error(e.getMessage(), e);	
			qewebLog.errorLog(e.getMessage(), null, e);
			throw e;
		}
		return null;
	}

	/**
	 * 根据指定的方法(targetVC.method)刷新指定组件(targetVC), 待刷新的组件由页面流配置标签中的targetVC属性指定.
	 * <p>
	 * <ul>例: 假设form1 bind bo1, form2 bind bo2, table1 bind bo3
	 * <li>1. 如果 targetVC=form1.m1,form2.m2,table1.m3, 则按先后顺序执行bo1.m1, bo2.m2, bo3.m3方法, 并根据结果按顺序刷新 form1,form2,table1;
	 * <li>2. 如果targetVC=form1.m1,table1, 则按先后顺序执行bo1.m1, bo3.query方法, 并根据结果按顺序刷新 form1,table1.
	 */
	@Override
	public void reloadTargetVC() {
		if(StringUtils.isEmpty(sourceName) || StringUtils.isEmpty(targetName))
			resultStr = dataIsland;
    	try {
    		String sourceId = VCUtil.getDataIslandBOId(sourceName);
    		//注意,此处的数据岛应该从上下文信息中获取
    		//参见PageFlow.js freshTargetVC方法中的url : ctx + actionURL.saveParam(),
    		List<BusinessObject> boList = disland.revertBOList(sourceId, ContextUtil.getContextParam());
    		
    		String boBind = VCUtil.getBoBind(targetName);
    		String boId = VCUtil.getDataIslandBOId(targetName);
    		BusinessObject bo = BOManager.getBOInstance(boBind);
    		disland.revertBop(boId, targetXML, bo);
    		
    		Object data = ExeBoMethodWithContextParam.executeBOMethod(
    				bo, ExeBoMethodWithContextParam.getPropMethod(operation), boList);
    		resultStr = disland.updateContainer(targetXML, boId, boBind, data);
		} catch(BOException e){
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		} catch (Exception e) {
    		log.error(e.getMessage(), e);	
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据页面流配置标签中的targetVC属性, 刷新全局按钮.
	 * <p>
	 * <ul>例: 假设page bind bo
	 * <li>1.targetVC=page, 则执行bo.query后刷新全局按钮;
	 * <li>2.targetVC=page.m, 则执行bo.m后刷新全局按钮.
	 */
	@Override
	public void reloadPageBtn() {
		if(StringUtils.isEmpty(sourceName))
			resultStr = dataIsland;
    	try {
    		String sourceId = VCUtil.getDataIslandBOId(sourceName);
    		//注意,此处的数据岛应该从上下文信息中获取
    		//参见PageFlow.js freshTargetVC方法中的url : ctx + actionURL.saveParam(),
    		List<BusinessObject> boList = disland.revertBOList(sourceId, ContextUtil.getContextParam());
    		String boBind = targetName;
    		Object data =  ExeBoMethodWithContextParam.executeBOMethod(
    				BOManager.getBOInstance(boBind), ExeBoMethodWithContextParam.getPropMethod(operation), boList);
    		
    		if(data instanceof BusinessObject)
    			resultStr = disland.updatePageOperate(targetXML, boBind, (BusinessObject)data);
    		else 
    			throw new BOException(operation + "方法未返回BusinessObject类型,不能刷新全局按钮!");
		} catch(BOException e){
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		} catch (Exception e) {
    		log.error(e.getMessage(), e);	
			e.printStackTrace();
		}
	}

    /**
     * 执行查询操作.
     * <br>
     * 由源BO查询关联的BO,在jsp页面上,源和目标由<qeweb:group>标签指定
     * @throws Exception
     */
	@Override
    public void search() {
    	String tableName = getRequest().getParameter(ConstantParam.GA_TABLENAME);
    	try {
    		//table的分页操作, 此时sourceName可以为空(当页面仅有table而没有查询域时)
        	if(StringUtils.isNotEmpty(tableName))
        		resultStr = doSearchOnPageNo(tableName);
        	//弹出页面关闭后直接刷新父页面的form组件时，sourceName为空
        	else if(StringUtils.isEmpty(sourceName))
        		resultStr = reloadByForm();
        	//通过粗粒度关联触发的查询, 此时sourceName不能为空
        	//例1:点击Form的查询按钮查询Table,此时Form对应的Bo为源Bo,Table对应的Bo为目标Bo
    		//例2:双击table1的某行数据关联table2,此时table1对应的Bo为源Bo,Table2对应的Bo为目标Bo
        	else
        		resultStr = doSearchOnConRelation();
    	} catch(BOException e){
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		} catch(Exception e) {
    		log.error(e.getMessage(), e);
			e.printStackTrace();
    	}
	}

	/**
	 * 根据recordId获取唯一记录, 在table弹出框的修改和查看中使用
	 * @return
     * @throws Exception 
	 */
	@Override
	public void findRecord() {
		BusinessObject bo = null;
		try {
			bo = BOManager.getBOInstance(sourceName);
			bo.setId(StringUtils.convertLong(recordId));
			if(StringUtils.isEqual(ConstantBOMethod.BO_VIEW, winType))
				bo = bo.view();
			else
				bo = bo.getRecord(bo.getId());		
			resultStr = JSONUtil.getBOJson(bo);
		} catch(BOException e){
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		} catch(Exception e){
			ExceptionUtil.handle(bo, e);
		}
	}

	/**
	 * 控制页面跳转<br>
	 * <p>
	 * 	<ul>
	 * 	<li>与直接跳转页面有所区别，该方法是带有数据岛的页面跳转
	 * 	<li>跳转到目标页面后,可能需要执行目标页Bo的方法,该方法可能带有参数.
	 * 	<li>跳转时将页面的数据岛传至目标页面,目标页面的Bo将接收参数,将数据岛转换为相应的bo或bolist.
	 * @return
	 */
	@Override
	public String redirect() {
		//配合struts-qeweb.xml文件 <action name="generalSearchAC"
		//里 使用${redirectStr}方式实现动态跳转页面
		redirectStr = StringUtils.removeAllSpace(redirectStr);
		
		//如果通过菜单实现跳转, 则需要清空上下文参数
		if(redirectStr.indexOf(ConstantParam.MENU_PATH_PARAM) != -1)
			ContextUtil.clearContextParam();

		return SUCCESS;
	}
	
	/**
	 * 设置上下文跳转的参数
	 */
	@Override
	public void saveParam() {
		ContextUtil.setContextParam(sourceName, dataIsland);
	}
	
	/**
	 * 文件导出
	 * 如果按钮绑定的方法以exp开头(expExl, expDoc等), 则认为该方法是导出方法, 将执行的export()操作.
	 * export()将不流经拦截器.
	 * @return
	 * @throws Exception
	 */
	@Override
	public String export() {
		if(StringUtils.isEmpty(sourceName))
    		return null;
		
		BusinessObject bo = null;
    	try {
    		String boId = VCUtil.getDataIslandBOId(sourceName);
    		List<BusinessObject> boList = disland.revertBOList(boId, dataIsland);
    		
    		//从数据岛还原BOT
    		BOTemplate bot = disland.createBOTemplate(boId, dataIsland);
    		//根据bot查询后导出
    		bo = getAppropriateBO(boList);
			ExeBoMethodWithContextParam.executeExp(getAppropriateBO(boList), operation, bot);
		} catch(BOException e){
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		} catch (Exception e) {
			ExceptionUtil.handle(bo, e);
		}
		
		return null;
	}
	
    /**
     * 获取应当使用的BO
     * @param boList
     * @return
     */
	private BusinessObject getAppropriateBO(List<BusinessObject> boList) {
		String boBind = VCUtil.getBoBind(sourceName);
		BusinessObject bo = BOManager.getBOInstance(boBind);
		
		//从数据岛还原待持久化的BO
		if(ContainerUtil.isNotNull(boList) && boList.size() == 1 
				&& BoOperateUtil.isSameClass(boList.get(0).getClass(), bo.getClass())) {
			bo = boList.get(0);
		}
		
		return bo;
	}

    /**
     * 设置校验结果
     * @param bo
     */
	private void setResult(BusinessObject bo) {
		this.result = bo.getSuccess();
		if(StringUtils.isNotEmpty(bo.getMessage()))
			ContextUtil.setTipMsg(AppLocalization.getLocalization(bo.getMessage()));
	}

    /**
     * 执行通过table翻页按钮触发的查询
     * @return 更新后的数据岛
     */
	private String doSearchOnPageNo(String tableName) throws Exception {
		//得到当前触发的BO对象
		BusinessObject targetBO = BOManager.getBOInstance(VCUtil.getBoBind(tableName));
		int start = StringUtils.convertToInt(getRequest().getParameter(ConstantParam.GA_START_ROW));

		BOTemplate bot = disland.createBOTemplate(sourceName, dataIsland);
		
		disland.revertBop(VCUtil.getDataIslandBOId(tableName), dataIsland, targetBO);
	
		bot = columnHeaderSort(bot);
    	
		Map<String, String> prop = new LinkedHashMap<String, String>();
		Object result = targetBO.query(bot, start);
		Page page = null;
		if(result instanceof Page || result == null) {
			page = (Page)result;
			String tableIsland = disland.createTableDataIsland(tableName, page, dataIsland, Envir.getRequestURI());
			prop.put(ConstantJSON.PAGEBAR_TABLE, tableIsland);			
		}
		else if(result instanceof BusinessObject) {
			String tableIsland = disland.createTableDataIsland(tableName, (BusinessObject)result, dataIsland, Envir.getRequestURI());
			prop.put(ConstantJSON.PAGEBAR_TABLE, tableIsland);			
		}

		//将Table数据、Table数据岛、数据总数、Table起始页转换成JSON
		if(page == null)
			page = new Page();
		
		JSONObject jsonData = JSONUtil.getPageJson(page, prop);
		if(jsonData != null)
			return jsonData.toString();
		
		return "";
	}

    /**
     * 通过粗粒度关联触发的查询, 此时sourceName不能为空.
     *  <li>例1:点击Form的查询按钮查询Table,此时Form对应的Bo为源Bo,Table对应的Bo为目标Bo
     *	<li>例2:双击table1的某行数据关联table2,此时table1对应的Bo为源Bo,Table2对应的Bo为目标Bo
     * @return 更新后的数据岛
     */
	private String doSearchOnConRelation() throws Exception {
		//循环执行每个Bo,如果状态机为search,执行该Bo的查询方法.
		//每次仅能通过一个按钮执行一次查询(即点击查询按钮仅能执行一个Bo的查询)
		String sourceBoId = VCUtil.getDataIslandBOId(sourceName);
		BOTemplate bot = disland.createBOTemplate(sourceBoId, dataIsland);
		bot = columnHeaderSort(bot);
    	
		//从数据岛还原待持久化的BO
		String targetBoId = VCUtil.getDataIslandBOId(targetName);
		BusinessObject targetBO = BOManager.getBOInstance(VCUtil.getBoBind(targetName));
		disland.revertBop(targetBoId, dataIsland, targetBO);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Object result = targetBO.query(bot, 0);
		if(result != null)
			resultMap.put(VCUtil.getBoBind(targetName), result);
		ContextUtil.setContextParam(sourceName, dataIsland);
		return disland.updateCRelationDataIsland(dataIsland, resultMap, relations, Envir.getRequestURI());
	}
    
    /**
     * 执行form的reload查询
     * @return
     * @throws Exception
     */
    private String reloadByForm() throws Exception {
		//循环执行每个Bo,如果状态机为search,执行该Bo的查询方法.
		//每次仅能通过一个按钮执行一次查询(即点击查询按钮仅能执行一个Bo的查询)
		String targetBoId = VCUtil.getDataIslandBOId(targetName);
		String targetBind = VCUtil.getBoBind(targetName);
		BOTemplate bot = disland.createBOTemplate(targetBoId, dataIsland);
		bot = columnHeaderSort(bot);
    	
		//从数据岛还原待持久化的BO
		BusinessObject formBo = BOManager.getBOInstance(targetBind);
		disland.revertBop(targetBoId, dataIsland, formBo);

		return disland.updateContainer(dataIsland, targetBoId, targetBind, formBo.query(bot, 0));
	}
	
	/**
	 * 列头排序
	 * @param bot
	 */
	private BOTemplate columnHeaderSort(BOTemplate bot){
		//排序字段
		String fileName = getRequest().getParameter(ConstantParam.GA_TABLE_FILENAME);
		//排序方向
    	String order = getRequest().getParameter(ConstantParam.GA_TABLE_ORDER);
    	if(bot == null){
    		bot = new BOTemplate();
    	}
		bot.putColumnHeaderOrder(fileName, order);
		return bot;
	}

	public String getResultStr() {
		return resultStr;
	}
	
	public void setDataIsland(String dataIsland) {
		this.dataIsland = dataIsland;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getRedirectStr() {
		return redirectStr;
	}

	public void setRedirectStr(String redirectStr) {
		this.redirectStr = redirectStr;
	}

	public String getOperationScope() {
		return operationScope;
	}

	public void setOperationScope(String operationScope) {
		this.operationScope = operationScope;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public boolean getResult() {
		return this.result;
	}

	public String getWinType() {
		return winType;
	}

	public void setWinType(String winType) {
		this.winType = winType;
	}

	public IQewebLog getQewebLog() {
		return qewebLog;
	}

	public void setQewebLog(IQewebLog qewebLog) {
		this.qewebLog = qewebLog;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetXML() {
		return targetXML;
	}

	public void setTargetXML(String targetXML) {
		this.targetXML = targetXML;
	}
}
