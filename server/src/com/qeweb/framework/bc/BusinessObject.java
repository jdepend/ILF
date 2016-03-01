package com.qeweb.framework.bc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.bc.sysbop.mobilebop.BarCode;
import com.qeweb.framework.bc.sysbop.mobilebop.Camera;
import com.qeweb.framework.common.Page;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.constant.ConstantBOMethod;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.manager.BOManager;

/**
 * Business Object, extends BusinessComponent <br>
 * BO中可以包含BO, 也可以包含BOP.<br>
 * 粗粒度组件的校验/关联/持久化在BO 中实现;
 */
public class BusinessObject extends BusinessComponent implements Serializable {

	private static final long serialVersionUID = 4976621536338555558L;

	/*
	 * 触发提交动作的超链接控件对应的bopBind.
	 * 对于表格, 当点击OperateBOP绑定的超链接时, 除传递该行的数据外, 还将额外还原该超链接指定的bopBind.
	 * 对于表单, 当点击OperateBOP绑定的超链接时, 将额外还原该超链接指定的bopBind.
	 */
	private String currentField;		
	// BusinessObject的状态, 根据该状态判断执行的BO操作类型
	private String operationStatus;
	//每页显示的记录数，查询时使用
	private int pageSize;
	// key:bcName,value:bc, BO中包含的所有bo或bop
	private Map<String, BusinessComponent> bcList = new HashMap<String, BusinessComponent>();
	// 页面展示字段的BOP名称集合
	// displayBopNameSet中的BOP名称来源于页面标签, 只有在标签绑定的BOP才被添加到displayBopNameSet中.
	// 当还原数据岛时, 仅需要还原displayBopNameSet中的BOP即可.
	private Set<String> displayBopNameSet = new LinkedHashSet<String>();
	//按钮绑定一个行为，operateMap用于处理按钮的值、状态、范围
	//key: btnName
	private Map<String, OperateBOP> operateMap = new HashMap<String, OperateBOP>();
	
	public BusinessObject() {
		setLocalName(BOHelper.getLocalizationBOName(getSearchClass()));
        //通过配置文件初始化Bop
//        BOHelper.initBop(this,BOPConfigMgr.getInstance().getConfig(this.getClass().getName()));
	}

	/**
	 * Bo 初始化,默认执行每个bc的init
	 */
	@Override
	final public void init() {
		for(BusinessComponent bc : bcList.values()){
			bc.init();
		}
	}

	/**
	 * 持久化操作-插入
	 */
	public void insert() throws Exception {
		BOHelper.setBOPublicFields_insert(this);
		getDao().save(this);
	}

	/**
	 * 持久化操作-修改<br>
	 * 修改过程:  <br>
	 * 1. 根据Id进行查询, 得到一个bo;<br>
	 * 2. 将页面传递的所有数据copy到bo;<br>
	 * 3. 设置lastModifyTime 和 lastModifyUserId 并保存bo<br>
	 * 这样做的目的是因为页面可能仅展示了要修改的部分信息, 故修改时需要先进行一次查找, 以便得到完整的记录.
	 */
	public void update() throws Exception {
		BusinessObject bo = (BusinessObject)getDao().getById(getSearchClass(), getId());
		BOHelper.copyUpdateValue(bo, this);
		BOHelper.setBOPublicFields_update(bo);
		getDao().update(bo);
	}

	/**
	 * 查看操作
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	public BusinessObject view() throws Exception {
		return getRecord(getId());
	}

	/**
	 * 根据Id 获取唯一记录
	 * @param id
	 * @return
	 */
	public BusinessObject getRecord(long id) throws Exception {
		return (BusinessObject) getDao().getById(getSearchClass(), id);
	}

	/**
	 * 持久化操作-删除<br>
	 * 删除的机制： 先查找，后删除。
	 * 这样做的目的是，当配置了many-to-one时，关联对象的属性并不能全部抓取，导致删除失败。
	 * @param bcList
	 * @throws Exception
	 */
	public void delete(List<BusinessComponent> bcList) throws Exception {
		if(ContainerUtil.isNull(bcList))
			return;

		List<BusinessComponent> newBCList = new LinkedList<BusinessComponent>();

		for(BusinessComponent bc : bcList) {
			BusinessComponent bo = getRecord(bc.getId());
			if(bo == null)
				continue;
			BOHelper.setBOPublicFields_delete(bo);
			newBCList.add(bo);
		}
		getDao().batchUpdate(newBCList);
	}

	/**
	 * BO查询.
	 * <br>
	 * 所有的BO查询操作都执行query方法,可能返回BO/BOList/Page, 默认返回Page
	 * <br>
	 * 当触发查询动作时,平台会根据相应的条件生成存储查询条件的结构:BOT.
	 *
	 * @param bot
	 * @param start
	 * @return Object
	 */
	public Object query(BOTemplate bot, int start) throws Exception {
		if(bot == null)
			bot = new BOTemplate();

		Map<String, Object> botMap = bot.getBotMap();
		if(has5BaseField())
			botMap.put(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE + "");
		bot.setBotMap(botMap);
		bot.setOrderMap(queryOrderBy());
		
		Page page = getDao().findPageByCriteria(getDC(bot), getPageSize(), start);
		initPreferencePage(page);

		return page;
	}

	/**
	 * 根据bot查询所有记录，如果数据量较大，可能会发生内存溢出，通常仅在弹出选择中使用
	 * @param bot
	 * @param start
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public Object query(BOTemplate bot) throws Exception {
		if(bot == null)
			bot = new BOTemplate();

		Map<String, Object> botMap = bot.getBotMap();
		if(has5BaseField())
			botMap.put(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE + "");
		bot.setBotMap(botMap);
		bot.setOrderMap(queryOrderBy());
		DetachedCriteria dc = BOTHelper.getDetachedCriteria(bot, getSearchClass());
		Page page = getDao().findPageByCriteria(dc);
		initPreferencePage(page);
		return page;
	}
	
	/**
	 * 构造查询条件
	 * @param bot
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object getDC(BOTemplate bot) {
		return BOTHelper.getDetachedCriteria(bot, getSearchClass());
	}

	/**
	 * 是否需要持久化5个基础字段:
	 * 	<li>private long createUserId;				//创建人
		<li>private Timestamp createTime;			//创建时间
		<li>private long lastModifyUserId; 			//最后修改人
		<li>private Timestamp lastModifyTime; 		//最后修改时间
		<li>private int deleteFlag;					//删除标识
		如果业务表没有这五个字段，可将该方法返回false 
	 * @return
	 */
	protected boolean has5BaseField() {
		return true;
	}
	
	/**
	 * 自定义排序，需使用时重写该方法即可<br>
	 * key ：排序字段名称（"userName"或"user.userName"）; value : 排序方向（"asc"或"desc"，不区分大小写）
	 * @param dc
	 */
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		if(has5BaseField())
			orderMap.put(IBaseDao.FIELD_ID, IBaseDao.ORDER_BY_DESC);

		return orderMap;
	}

	/**
	 * 获取查询用class，配合hibernate查询方法使用，子类可根据需要覆盖
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Class getSearchClass() {
		return this.getClass();
	}

	/**
	 * BO关联
	 * <br>
	 * 所有的BO关联操作都执行relationHandle方法.
	 * <br>
	 * 当触发关联动作时(通常是在table中焦点移动到下一行),平台会根据相应的条件生成存储查询条件的结构:BOT.
	 * <br>
	 * relationHandle方法首先通过getRelations得到该BO关联的其它BO,再循环执行每个BO的query(bot)方法以更新关联BO的数据.
	 *
	 * @param bot
	 * @return Map<String, Object>  key:bcName  value:bo/page
	 */
	final public Map<String, Object> relationHandle(BOTemplate bot) throws Exception {
		List<BusinessComponent> relationBOList = getRelations();
		if(ContainerUtil.isNull(relationBOList))
			return null;

		//key:bcName  value:bo/page
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		//循环执行每个关联BO的查询方法
		for (int i = 0; i < relationBOList.size(); i++) {
			BusinessObject bo = (BusinessObject) relationBOList.get(i);
			result.put(bo.getClass().getSimpleName(), bo.query(bot, 0));
		}

		return result;
	}

	/**
	 * query方法调用initPreferencePage为page设置bolist,以供不同的页面提供展现.
	 * <br>
	 * 该实现仅是简单实现，如果需要在结果中设置更多信息，如：文字突出显示、显示图表、按钮隐藏等功能，
	 * 子类需要覆写该方法。
	 * @param page
	 */
	protected void initPreferencePage(Page page) {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		if(ContainerUtil.isNotNull(page.getItems())) {
			for (int i = 0; i < page.getItems().size(); i++) {
				BusinessObject bo = (BusinessObject) page.getItems().get(i);
				BOHelper.initPreferencePage_lazy(bo, this);
				boList.add(bo);
			}
		}

		page.setBOList(boList);
	}

	/**
	 * BO的validate方法默认将循环调用bcList中所有bc的validate
	 */
	@Override
	public boolean validate() throws Exception{
		Map<String, BusinessComponent> bcList = getBcList();

		for(BusinessComponent bc : bcList.values()) {
			if(!bc.validate())
				return false;
		}

		return true;
	}


	/**
	 * 取得客户端方法(JS函数), 这些方法在点击按钮时最先执行.
	 * 如果执行的JS函数返回true或没有返回值, 则执行完该函数后继续执行按钮绑定的其它方法或页面流跳转;
	 * 如果返回false, 则该JS函数后不会继续向下执行.
	 * <br>
	 * 注:JS方法名不应当和后台方法名重复, 如果重复, 则仅识别JS方法.
	 * <p>
	 * 为了完成某些任务展示组件将会拥有特定的事件,触发该事件后将调用客户端js方法.
	 * <ul>
	 * 如: 拍照功能将调用客户端脚本执行拍照动作.
	 * <br>
	 * 我们认为拍照动作是业务组件自身的行为,为了完成这个行为而调用客户端方法,所以业务对象应当有拍照方法.
	 * </ul><ul>
	 * 又: 单击删除按钮时将弹出是否确定删除的询问框.
	 * <br>
	 * 询问框也是业务组件自身的行为,由业务本身决定了是否需要提示.
	 * <br>
	 * getDesirousMethod用于处理上述问题.
	 * </ul></p>
	 * <p>
	 * commandButton组件绑定一个Bo方法,拍照对应的BO需要重写getDesirousMethod,在画出commandButton组件时先检查getDesirousMethod,
	 * 如果有返回值,说明调用的是客户端方法,应当画出JS,否则不必画出JS
	 * <br>
	 * 默认delete/update方法需要调用客户端方法弹出提示信息.
	 *
	 * @param methodName bind方法名称
	 * @return
	 */
	public String getDesirousMethod(String methodName) {
		//保存查询条件
		if(StringUtils.isEqual(ConstantBOMethod.BO_SYS_SAVECASE, methodName)) 
			return "sysSaveCase(this)";
		else if(StringUtils.isEqual(ConstantBOMethod.BO_SYS_OPENCASE, methodName))
			return "sysOpenCase(this)";
		//手机端拍照
		else if(StringUtils.isEqual(ConstantBOMethod.BO_GET_CAMERA, methodName))
			return Camera.camera();
		//条码扫描
		else if(StringUtils.isEqual(ConstantBOMethod.BO_GET_BARCODE, methodName))
			return BarCode.barcodeScan();
		
		return null;
	}
	
	/**
	 * getDesirousMethodAfter提供了一种执行js的方式, 它将返回一个js函数, 其参数可以指定为this, 表示按钮信息.
	 * 这个函数将在执行bo的方法后,页面流跳转前执行.
	 * <p>
	 * 如果执行的JS函数返回true或没有返回值, 则执行完该函数后继续执行按钮绑定的其它方法或页面流跳转;
	 * <p>
	 * 注:JS方法名不应当和后台方法名重复, 如果重复, 则仅识别JS方法.
	 * @param methodName
	 * @return
	 */
	public String getDesirousMethodAfter(String methodName) {
		//平台JS方法,删除表格弹出回填的行数据
		if(StringUtils.isEqual(ConstantBOMethod.BO_SYS_JSDELETE, methodName))
			return "jsDelete(this)";
		
		return null;		 
	}

	/**
	 * 根据bcName获取bop
	 *
	 * @param bcName
	 * @return
	 */
	public BOProperty getBOP(String bcName) {
		if(bcList.get(bcName) instanceof BOProperty)
			return (BOProperty) bcList.get(bcName);

		//bcName可能是 bo.bop的格式
		String[] bcLevel = StringUtils.split(bcName, ConstantSplit.BIND_SPLIT);
		if(bcLevel.length > 1) {
			BusinessObject bo = this.getBO(bcLevel[0]);
			return bo.getBOP(bcName.substring(bcLevel[0].length() + ConstantSplit.BIND_SPLIT.length()));
		}
		//单文件bop
		else if(BoOperateUtil.isFileBOP(this, bcName)) {
			BOProperty bop = new FileBOP();
			bop.setLocalName(BOHelper.getLocalizationBopName(getSearchClass(), bcName));
			addBOP(bcName, bop);
			return bop;
		}
		//多文件bop
		else if(BoOperateUtil.isMultiFileBOP(this, bcName)) {
			BOProperty bop = new MultiFileBOP();
			bop.setLocalName(BOHelper.getLocalizationBopName(getSearchClass(), bcName));
			addBOP(bcName, bop);
			return bop;
		}
		//默认添加基础Bop, 可以在自定义BO的构造方法中使用addBOP添加具体的BOP
		else if(BoOperateUtil.isExistBOP(this, bcName)) {
			BOProperty bop = new BOProperty();
			bop.setLocalName(BOHelper.getLocalizationBopName(getSearchClass(), bcName));
			addBOP(bcName, bop);
			return bop;
		}
		else {
			return null;
		}
	}

	/**
	 * 根据bcName获取bo
	 *
	 * @param bcName
	 * @return
	 */
	public BusinessObject getBO(String bcName) {
		return this.getBO(bcName, false);
	}

	/**
	 * 根据bcName获取bo.(平台使用,不推荐项目使用)
	 * <li>如果isNotLoadBO是false, 则bcName对应的bo不存在时将实例化一个bo;
	 * <li>如果isNotLoadBO是true,  则bcName对应的bo不存在时返回null.
	 * @param bcName
	 * @param isNotLoadBO
	 * @return
	 */
	@Deprecated
	public BusinessObject getBO(String bcName, boolean isNotLoadBO) {
		if(bcList.get(bcName) instanceof BusinessObject || bcList.get(bcName) instanceof ExtendsBusinessObject)
			return (BusinessObject) bcList.get(bcName);

		if(isNotLoadBO)
			return null;

		Class<?> fieldType = BoOperateUtil.getDeclaredFieldType(BOHelper.getRealClass(this), bcName);
		if(BoOperateUtil.isBO(fieldType)) {
			BusinessObject bo = (BusinessObject) BOManager.createBOByType(fieldType);
			bo.setLocalName(bcName);
			addBO(bcName, bo);
			return bo;
		}

		return null;
	}

	/**
	 * 添加按钮绑定的bop.
	 * <ul>注: 如果超链接控件绑定了行为, 应使用addBOP(String, BOP)
	 * @param btnName	按钮名称
	 * @param bop		按钮对应的operateBOP
	 */
	public void addOperateBOP(String btnName, OperateBOP bop) {
		operateMap.put(btnName, bop);
	}

	/**
	 * 将一个Bop放入bcList
	 *
	 * @param bopName
	 * @param bop
	 */
	public void addBOP(String bopName, BOProperty bop) {
		if(StringUtils.isEmpty(bop.getLocalName()))
			bop.setLocalName(BOHelper.getLocalizationBopName(getSearchClass(), bopName));
		bcList.put(bopName, bop);
	}

	/**
	 * 将一个Bop放入bcList,该bop的名字与sping中配置的bopId 一致
	 *
	 * @param bopName
	 */
	public void addBOP(String bopName) {
		addBOP(bopName, (BOProperty)SpringConstant.getCTX().getBean(bopName));
	}

	/**
	 * 将一个Bop放入bcList
	 * @param boFieldName  Bo的属性名
	 * @param bopName spring中配置的bopName
	 */
	public void addBOP(String boFieldName, String bopName) {
		addBOP(boFieldName, (BOProperty)SpringConstant.getCTX().getBean(bopName));
	}

	/**
	 * 将一个Bo放入bcList
	 * @param boName
	 * @param bo
	 */
	public void addBO(String boName, BusinessObject bo) {
		bcList.put(boName, bo);
	}

	/**
	 * 设置删除标志、最后修改日期、修改人ID
	 * @param bo
	 * @param deleteSign
	 */
	protected void setPublicInfo(BusinessObject bo, int isLastSign) {
		bo.setLastModifyTime(DateUtils.getCurrentTimestamp());
		bo.setLastModifyUserId(UserContext.getUserId());
	}


	@Override
	public List<BusinessComponent> getRelations() {
		return null;
	}

	/**
	 * 获取btnBOP
	 * @param btnName
	 * @return
	 */
	public OperateBOP getOperateBOP(String btnName) {
		if(StringUtils.isEmpty(btnName))
			return null;

		Map<String, OperateBOP> operateMap = getOperateMap();
		if(ContainerUtil.isNull(operateMap))
			return null;

		return operateMap.get(btnName);
	}

	@Override
	public BusinessObject cloneBC() {
		return (BusinessObject) super.cloneBC();
	}

	/**
	 * 处理bop与bo之间的关联
	 * @param relationBo
	 * @return
	 * @throws BOException
	 */
	public Object bopRelationHandle(BusinessObject relationBo) throws BOException {
		return null;
	}

	/**
	 * 可触发提交动作的控件对应的bopBind.
	 * <li>对于表格, 当点击OperateBOP绑定的超链接时, 除传递该行的数据外, 还将额外还原该超链接指定的bopBind.
	 * <li>对于表单, 当点击OperateBOP绑定的超链接时, 将额外还原该超链接指定的bopBind.
	 * @return 可触发提交动作的控件对应的bopBind.
	 */
	public String getCurrentField() {
		return currentField;
	}

	public void setCurrentField(String currentField) {
		this.currentField = currentField;
	}
	
	/**
	 * 当跳转到其它页面时, 如果目标页面仅仅是简单的接收参数, 可直接为按钮的operate指定tbo.toPage方法, 以简化操作.
	 * 该方法返回paramBO, 用在表单按钮或表格行级按钮中
	 * @param paramBO
	 * @return paramBO
	 */
	public BusinessObject toPage(BusinessObject paramBO) {
		BOHelper.initPreferencePage_lazy(paramBO, this);
		return paramBO;
	}
	
	/**
	 * 当跳转到其它页面时, 如果目标页面仅仅是简单的接收参数, 可直接为按钮的operate指定tbo.toPage方法, 以简化操作.
	 * 该方法返回this, 用在表格按钮或全局按钮中
	 * @return this
	 */
	public BusinessObject toPage() {
		return this;
	}

	@Override
	public void free() {
		if(getBcList() != null) {
			for(Entry<String, BusinessComponent> entry : getBcList().entrySet()) {
				entry.getValue().free();
			}
			getBcList().clear();
		}
		if(getOperateMap() != null) {
			for(Entry<String, OperateBOP> entry : getOperateMap().entrySet()) {
				entry.getValue().free();
			}
			getOperateMap().clear();
		}
	}
	
	public void pushDisplayBopName(String bopName){
		this.displayBopNameSet.add(bopName);
	}
	
	public Set<String> getDisplayBopNameSet(){
		return this.displayBopNameSet;
	}
	
	public void setDisplayBopNameSet(Set<String> displayBopNameSet){
		this.displayBopNameSet = displayBopNameSet;
	}
	
	public Map<String, BusinessComponent> getBcList() {
		return bcList;
	}
	
	public String getOperationStatus() {
		return operationStatus;
	}
	
	public void setOperationStatus(String operationStatus) {
		this.operationStatus = operationStatus;
	}
	
	public void setBcList(Map<String, BusinessComponent> bcList) {
		this.bcList = bcList;
	}
	
	public Map<String, OperateBOP> getOperateMap() {
		return operateMap;
	}

	public void setOperateMap(Map<String, OperateBOP> operateMap) {
		this.operateMap = operateMap;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize <= 0 ? AppConfig.getPageSize() : pageSize;
	}
}
