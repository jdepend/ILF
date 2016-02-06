package com.qeweb.framework.app.pageflow;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.Envir;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.pageflow.ContextUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MatcherUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.exception.QewebException;
import com.qeweb.framework.log.IQewebLog;
import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.Page;
import com.qeweb.framework.pal.ViewComponent;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Tab;

/**
 * 如果页面跳转后需要执行方法, 则这些被执行的方法统一通过ExeBOMethod处理.
 * <ul>通过ExeBOMethod处理主要的目的:
 * 	<li>如果跳转后的页面需要执行几个方法,且这些方法有的需要提交,有的仅需要读取(只读),
 * 		对这些方法进行事务管理时就要区分是readonly还是submit,否则将会产生两种错误情况：
 * 		<ul>
 * 		<li>1.开启过多的数据库链接,<br>
 * 		<li>2.本应开启只读事务的方法在实例化一个hib对象时将会出错.
 * 		</ul>
 * 		 通过ExeBOMethod将这些方法归类,
 * 		结合transactionManager的配置, 共同规划了哪些方法是只读,哪些是提交(以方法名前缀区别,可通过application.properties的readOnlyMethodPrefix配置).
 * 	<li>在执行只读事务的方法中执行粗粒度组件的的MDT/DDT加载，减少MDT/DDT首次加载时出现的多余的数据库连接。
 * 	<li>在执行只读事务的方法中执行细粒度组件的初始化方法，减少细粒度组件初始化时有可能出现的内容查询出现的多余且不必要的数据库连接。
 * </ul>
 */
public class ExeBOMethod implements Serializable {

	private static final long serialVersionUID = 307832511250115390L;

	/*
	 * 一个页面中所有需要提前加载初始化Page组件和粗粒度组件，
	 * （只读/提交）事务执行方法中会将它们需要的结果回填到组件各自的loadData方法中
	 */
	Map<String, ViewComponent> vcMap = new LinkedHashMap<String, ViewComponent>();

	// 一个页面中包含的所有粗粒度组件, 这些粗粒度组件都将执行两个操作:
	//它们将统一在只读事务执行方法中进行加载
	List<Container> containerList = new LinkedList<Container>();

	//需要执行只读事务的组件及其对应方法
	Map<String, String> readOnlyMethodMap = new LinkedHashMap<String, String>();

	//需要执行提交事务的组件及其对应方法
	Map<String, String> submitMethodMap = new LinkedHashMap<String, String>();

	/*
	 * 自定义事务, 除了全局事务外, 用户还可以为每个方法自定义事务, 这类事务游离于全局事务外.
	 * 自定义事务在conf/customtransaction中定义.
	 *
	 * 例: 点击一个按钮需要执行bo1的3个方法: exe1, exe2, exe3, 默认情况下这三个方法将在一个可提交事务中.
	 * 	   当为exe2配置了提交事务后, 则exe2在一个事务中并且最先执行, exe1和exe3在一个事务中, 在exe1后执行.
	 */
	Map<String, String> customSubmitMethodMap = new LinkedHashMap<String, String>();

	Map<String, String> customReadOnlyMethodMap = new LinkedHashMap<String, String>();

	/**
	 * 1.区分一个Page中所有跳转后执行方法的事务类别<br>
	 * 2.根据区分结果执行各种事务统一执行方法，并向组件中回填执行结果<br>
	 * 3.加载page及粗粒度组件中的DDT<br>
	 * 4.初始化整个页面中包含的所有细粒度组件<br>
	 * 该方法被Page中的init方法调用<br>
	 *
	 * <ul>执行事务的优先级:
	 * <li>1.初始化所有粗粒组件包含的细粒度组件，然后加载DDT，
	 * <li>2.自定义可提交事务，
	 * <li>3.全局可提交事务，
	 * <li>4.自定义只读事务，
	 * <li>5.全局只读事务。
	 * <ul>注意：
	 * <li>如果方法隶属于不同优先级事务, 则按照事务的优先级执行;
	 * <li>如果方法隶属于相同优先级事务, 则按照页面组件的顺序执行.
	 */
	public void execute(Page page) {
		try {
			distinguishBCExeMethodType(page);
			loadData();
			executeCustomSubmitMethods();
			executeSubmitMethods();
			executeCustomReadOnlyMethods();
			executeReadOnlyMethods();
		} catch (BOException e){
			ContextUtil.setTipMsg(e.getErrMessage());
			IQewebLog qewebLog = (IQewebLog) SpringConstant.getCTX().getBean("qewebLog");
			qewebLog.errorLog(e.getErrMessage(), e.getContext(), e);
		}
	}
	
	/**
	 * 通过事务中间层划分方法事务类型
	 * @param vc
	 */
	public void distinguishBCExeMethodType(ViewComponent vc){
        String vcId = vc.getId();
		if(vc instanceof Page){
		    vcId = "VC_Page";
			for(ViewComponent sub_vc : ((Page) vc).getContainerList()){
				this.distinguishBCExeMethodType(sub_vc);
			}
		}
		else if(vc instanceof Tab) {
			for(ViewComponent sub_vc : ((Tab) vc).getContainerList()){
				this.distinguishBCExeMethodType(sub_vc);
			}
		}
		else {
			containerList.add((Container) vc);
		}

		vcMap.put(vcId, vc);
		String bcId = vc.getBcId();

		String operate = ExeBoMethodWithContextParam.getBOMethod(bcId, ContextUtil.getContextOperate());
		if(StringUtils.isEmpty(operate) && !isRequiredParam(vc)
				&& !ExeBoMethodWithContextParam.hasMethod((BusinessObject) vc.getBc(), getOnLoadMethod()))
			return;

		if(CustomTransactionHelper.getInstance().isSubmit(operate))
			customSubmitMethodMap.put(vcId, operate);
		else if(CustomTransactionHelper.getInstance().isReadonly(operate))
			customReadOnlyMethodMap.put(vcId, operate);
		else if(isReadOnly(operate))
			readOnlyMethodMap.put(vcId, operate);
		else
			submitMethodMap.put(vcId, operate);
	}
	
	/**
	 * 装载页面，设定页面组件的值、状态、范围.
	 * <ul>页面组件受两个个因素影响,由低到高依次是:
	 * <li>组件对应的BO;
	 * <li>4+3模型
	 */
	public void loadData() {
		initFC();
		load4a3();
	}
	
	/**
	 * 执行page或粗粒度组件方法，它们都包裹在一个可提交事务中</br>
	 * 自定义可提交事务
	 * @throws BOException 
	 */
	public void executeCustomSubmitMethods() throws BOException {
		if(ContainerUtil.isNotNull(customSubmitMethodMap))
			executeMethods(customSubmitMethodMap);		
	}

	/**
	 * 执行page或粗粒度组件方法，它们都包裹在一个只读事务中</br>
	 * 自定义只读事务
	 * @throws BOException 
	 */
	public void executeCustomReadOnlyMethods() throws BOException {
		if(ContainerUtil.isNotNull(customReadOnlyMethodMap))
			executeMethods(customReadOnlyMethodMap);
	}

	/**
	 * 执行page或粗粒度组件方法，它们都包裹在一个可提交事务中</br>
	 * 全局可提交事务
	 * @throws BOException 
	 */
	public void executeSubmitMethods() throws BOException {
		if(ContainerUtil.isNotNull(submitMethodMap))
			executeMethods(submitMethodMap);
	}

	/**
	 * 执行page或粗粒度组件方法、DDT加载及所有细粒度组件初始化方法，它们都包裹在一个只读事务中</br>
	 * 全局只读事务
	 * @throws BOException 
	 */
	public void executeReadOnlyMethods() throws BOException {
		if(ContainerUtil.isNotNull(vcMap)) {
			executeMethods(readOnlyMethodMap);
			for(Container cntainer : containerList){
				cntainer.loadSchema();
			}
		}
	}

	/**
	 * 方法是否属于使用只读事务.
	 * 在application.properties中通过设置readOnlyMethodPrefix, 可配置BO中的哪些方法是只读事务.
	 * @param operate
	 * @return
	 */
	private boolean isReadOnly(String operate) {
		if(StringUtils.isEmpty(operate))
			return true;

		String opts[] = StringUtils.split(operate, ".");
		String methodName = operate;
		if(opts != null && opts.length > 1)
			methodName = opts[opts.length - 1];

		String[] methodPrefixs = StringUtils.split(AppConfig.getPropValue(AppConfig.READ_ONLY_METHOD_PREFIX), ",");
		for(int i = 0; i < methodPrefixs.length; i++){
			if(StringUtils.isEmpty(methodPrefixs[i]))
				continue;

			if(MatcherUtil.isHeadMatch(methodName, methodPrefixs[i]))
				return true;
		}

		return false;
	}

	/**
	 * 初始化整个页面中的所有细粒度组件
	 */
	private void initFC() {
		if(ContainerUtil.isNotNull(vcMap)) {
			for(Container cntainer : containerList){
				cntainer.init();
			}
		}
	}

	/**
	 * 加载4+3模型:DDT配置, MDT配置
	 */
	private void load4a3() {
		if(this.vcMap.isEmpty())
			return;
		
		for(Entry<String, ViewComponent> entry : vcMap.entrySet()){
			ViewComponent vc = entry.getValue();
			if(vc instanceof Page) {
				((Page)vc).loadDDT();
			}
			else {
				((Container)vc).loadDDT();
				((Container)vc).loadMDT();
			}
		}
	}

	
	/**
	 * 执行page或粗粒度组件方法
	 * @param methodMap
	 */
	private void executeMethods(Map<String, String> methodMap) throws BOException {
		for(Entry<String, String> entry : methodMap.entrySet()){
			ViewComponent vc = this.vcMap.get(entry.getKey());
			BusinessObject bo = (BusinessObject) vc.getBc();
			Object data = getData(vc.getBcId(), 
							bo, 
							isRequiredParam(vc),
							ExeBoMethodWithContextParam.hasMethod(bo, getOnLoadMethod()));
			vc.loadData(data);
		}
	}

	/**
	 * 是否接收参数
	 * @param container
	 * @return
	 */
	private boolean isRequiredParam(ViewComponent vc){
		boolean isRequiredParam = false;
		//page组件
		if(vc instanceof Page)
			return StringUtils.isEqual(ConstantParam.CONTEXT_PARAM_REQURIED, ((Page) vc).getParam());
		//粗粒度组件
		else
			isRequiredParam = StringUtils.isEqual(ConstantParam.CONTEXT_PARAM_REQURIED, ((Container) vc).getParam());

		return isRequiredParam;
	}

	/**
	 * 当页面加载时bo执行的初始方法
	 * 如果链接URL是/WEB-INF/pages/system/systemmgt/userSearch.jsp?load=onLoadMethod&param=a,b,c,d?timeStamp=123234的形式，
	 * 表示页面加载时执行onLoadMethod方法，onLoadMethod需要为粗粒度组件设置值
	 * @return
	 */
	private String getOnLoadMethod() {
		String onload = Envir.getRequest().getParameter(ConstantParam.CONTEXT_ONLOAD);
		if(StringUtils.isNotEmpty(onload)) {
			onload = (onload.indexOf('?') == -1 ? onload : onload.substring(0, onload.indexOf('?')));
		}
		
		return onload;
	}
	
	/**
	 * 当页面加载时bo执行的初始方法
	 * 如果链接URL是/WEB-INF/pages/system/systemmgt/userSearch.jsp?load=onLoadMethod&param=a,b,c,d?timeStamp=123234的形式，
	 * 表示页面加载时执行onLoadMethod方法，onLoadMethod的参数是a,b,c,d
	 * @return
	 */
	private String getOnLoadParam() {
		String param = Envir.getRequest().getParameter(ConstantParam.CONTEXT_ONLOAD_PARAM);
		if(StringUtils.isNotEmpty(param)) {
			param = (param.indexOf('?') == -1 ? param : param.substring(0, param.indexOf('?')));
		}

		return param;
	}

	/**
	 *
	 * @param boId
	 * @param bo
	 * @param isRequired 页面是否接收
	 * @param isLoad 是否执行load对应的方法, 其优先级高于isRequired. 如果执行了load对应的方法, 则不再接收参数.
	 * @return 
	 * @throws BOException
	 */
	private Object getData(String boId, BusinessObject bo, boolean isRequired, boolean isLoad) throws BOException {
		if(Envir.getRequest() == null)
			return null;

		Object containerData = null;
		//页面跳转后需要执行的方法, 格式BO.method
		String operate = ExeBoMethodWithContextParam.getBOMethod(boId, ContextUtil.getContextOperate());
		//页面跳转后接收的参数
		String ctxParam = ContextUtil.getContextParam();

		//执行上下文中指定的bo的方法
		//1、如: <qeweb:commandButton operate="bo2.method2">
		//如果该按钮控制了页面跳转,且目标页面存在bo2对应的bo,则加载时执行bo2.method2
		//2、如果链接URL是/WEB-INF/pages/system/systemmgt/userSearch.jsp?load=onLoadMethod的形式，
		//表示页面加载时执行onLoadMethod方法，onLoadMethod需要为粗粒度组件设置值，onLoadMethod需要返回BO
		try {
			//执行load对应的方法,无论粗粒度组件是否需要参数都将执行
			if(isLoad) {
				containerData = ExeBoMethodWithContextParam.executeOnloadMethod(bo, getOnLoadMethod(), getOnLoadParam());
			}
			//执行源页面指定的方法
			else if(isRequired
					&& StringUtils.isNotEmpty(ctxParam)
					&& ExeBoMethodWithContextParam.hasMethod(bo, operate)) {
				List<BusinessObject> boList = AppManager.createDataIsland().revertBOList(ContextUtil.getSourceVcId(), ctxParam);

				containerData = ExeBoMethodWithContextParam.executeBOMethod(bo,	operate, boList);
			}
			//执行上下文跳转,在目标页面接收参数, 执行默认的方法,即query方法
			else if(isRequired && StringUtils.isNotEmpty(ctxParam)) {
				BOTemplate bot = AppManager.createDataIsland().createBOTemplate(ContextUtil.getSourceVcId(), ctxParam);
				containerData = bo.query(bot, 0);
			}
		}
		catch (Exception e) {
			if(e instanceof BOException)
				throw (BOException) e;
			e.printStackTrace();
			if(!(e instanceof QewebException))
				e = new QewebException("", e.getMessage());
			BOException boe = new BOException("系统内部错误！");
			boe.setQewebException((QewebException) e);
			throw boe;
		}

		return containerData;
	}
}
