package com.qeweb.framework.bc;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.bc.prop.MutiValue;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.common.constant.ConstantSplit;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * Business Property, extends BusinessComponent
 * <br>
 * BOP是业务对象的属性，每个细粒度控件绑定一个BOP，每个BOP都包含值、状态、范围三个属性。
 * <br>
 * BOP包含toXXX方法以自适应不同的细粒度控件，即每个细粒度控件对于BOP的一个toXXX方法，如：
 * <br>
 * 文本框控件对应toText()，下拉列表对应toMap，超链接对应toLink()
 * <br>
 * 每个BOP可以根据需要使用或覆写父类的toXXX方法以应对不同的展现,但通常不建议修改。
 * <br>
 * 除了细粒度组件外, 按钮也可以绑定BOP, 如果想给按钮赋予更多的特性, 可令按钮绑定OperateBOP.
 *
 */
public class BOProperty extends BusinessComponent {

	private static final long serialVersionUID = 4016381894798988684L;

	//bop是否进行排序
	private Boolean sortAble;
	
	/*
	 * BOP改变时是否能触发粗粒度组件关联, 默认false.
	 * 例:
	 * <qeweb:group relations="roleForm:roleTable"><br>
		<qeweb:form id="roleForm" bind="roleBO" queryRange="true">
			<qeweb:textField bind='bop'/>
		</qeweb:form>
		<qeweb:table id="roleTable" bind="roleBO">
			...
		</qeweb:table>
		</qeweb:group>
	 * 当bop改变时, 将会执行其所在BO的query方法, 即示BO的query方法, query方法可根据需要返回数据集(Page)或结构(BO).
	 * 由此可见, 如果tiggerCRelation返回true, 则当bop改变时, 相当于点击了其所在bo的查询按钮.
	 */
	private Boolean tiggerCRelation = false;

	/**
	 * bop初始化, 注：该方法不能在构造函数中使用
	 */
	@Override
	public void init() {}

	/**
	 * 校验bc是否满足指定条件
	 *
	 * @return true：校验成功
	 */
	@SuppressWarnings("deprecation")
	public boolean validate() throws Exception {
		//如果BOP允许为空且没有值，无需校验
		if(!getRange().isRequired() && StringUtils.isEmpty(getValue().getValue()))
			return true;

		boolean isIn = getRange().isIN(getValue());
		if(!isIn)
			setErrorMessage(getRange().getValidateMessage());

		return isIn;
	}

	/**
	 * 关联操作.<br>
	 * @param sourceBop 源Bop
	 * @return
	 */
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		return this;
	}

	/**
	 * 是否与其它Bop存在关联
	 * @return
	 */
	final public boolean hasBOPRelation() {
		return ContainerUtil.isNotNull(getRelationBOPList());
	}

	/**
	 * 是否与其它Bo存在关联
	 * @return
	 */
	final public boolean hasBORelation() {
		return ContainerUtil.isNotNull(getRelationBOList());
	}
	
	public boolean isTiggerCRelation() {
		return this.tiggerCRelation;
	}
	
	/**
	 * BOP改变时是否能触发粗粒度组件关联
	 * <br>
	 * 例:
	 * <P>
	 * &lt;qeweb:group relations="roleForm:roleTable"><br>
		&lt;qeweb:form id="roleForm" bind="roleBO" queryRange="true">
			&lt;qeweb:textField bind='bop'/>
		&lt;/qeweb:form>
		<br>
		&lt;qeweb:table id="roleTable" bind="roleBO">
			...
		&lt;/qeweb:table>
		<br>
		&lt;/qeweb:group>
		</p>
	 * 当bop改变时, 将会执行其所在BO的query方法, 即示BO的query方法, query方法可根据需要返回数据集(Page)或结构(BO).
	 * <br>
	 * 由此可见, 如果isTiggerCRelation返回true, 则当bop改变时, 相当于点击了其所在bo的查询按钮.
	 * 
	 * @param boolean
	 */
	public void setTiggerCRelation(Boolean tiggerCRelation) {
		this.tiggerCRelation = tiggerCRelation;
	}

	/**
	 * 获取关联的BOP
	 * @return
	 */
	private List<BOProperty> getRelationBOPList() {
		List<BOProperty> bopList = new LinkedList<BOProperty>();
		List<BusinessComponent> relations = getRelations();
		if(ContainerUtil.isNull(relations))
			return bopList;

		for(BusinessComponent bc : relations) {
			if(bc instanceof BOProperty)
				bopList.add((BOProperty) bc);
		}

		return bopList;
	}

	/**
	 * 获取关联的BO
	 * @return
	 */
	private List<BusinessObject> getRelationBOList() {
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		List<BusinessComponent> relations = getRelations();
		if(ContainerUtil.isNull(relations))
			return boList;

		for(BusinessComponent bc : relations) {
			if(bc instanceof BusinessObject)
				boList.add((BusinessObject) bc);
		}

		return boList;
	}

	/**
	 * BOP关联其他BOP
	 * <br>
	 * 所有BOP与BOP间关联操作都执行bopRelationHandle方法.
	 * <br>
	 * bopRelationHandle方法首先通过getRelationBOPList得到该BOP关联的其它BOP,再循环执行每个BOP的query(sourceBop)方法以更新关联BOP的数据.
	 * @param sourceBop 触发relationHandle的BOP
	 * @return Map<String, Object>  key:bopClassSimpleName  value:bop
	 */
	final public Map<String, BusinessComponent> bopRelationHandle(BOProperty sourceBop) throws Exception {
		if(!hasBOPRelation())
			return null;

		//key:bcName  value:bo/page
		Map<String, BusinessComponent> result = new LinkedHashMap<String, BusinessComponent>();
		for(BOProperty bop : getRelationBOPList()) {
			bop.init();
			result.put(bop.getClass().getName(), bop.query(sourceBop));
		}

		return result;
	}

	/**
	 * BOP关联BO
	 * <br>
	 * 所有的BOP与BO关联操作都执行boRelationHandle方法.
	 * <br>
	 * boRelationHandle方法首先通过relationBOList得到该BOP关联的其它BO,再循环执行每个BO的bopRelationHandle(relationBo)方法以更新关联BO的数据.
	 *
	 * @param bot
	 * @return Map<String, Object>  key:bcName  value:bo/boList/page
	 */
	final public Map<String, Object> boRelationHandle(BusinessObject relationBo) throws Exception {
		if(!hasBORelation())
			return null;

		List<BusinessObject> relationBOList = getRelationBOList();

		//key:bcName  value:bo/boList/page
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		//循环执行每个关联BO的bopRelationHandle方法
		for (BusinessObject bo : relationBOList) {
			Object obj = bo.bopRelationHandle(relationBo);
			if(obj instanceof BusinessObject)
				BOHelper.initPreferencePage((BusinessObject) obj);
			result.put(bo.getClass().getSimpleName(), obj);
		}

		return result;
	}

	/**
	 * 取得查询用范围，子类可按需覆盖getQueryRange方法.
	 * 默认的getQueryRange去掉了必填项校验。
	 * @return
	 */
	public BCRange getQueryRange() {
		BCRange queryRange = getRange();
		if(queryRange != null)
			queryRange.setRequired(false);

		return queryRange;
	}

	/**
	 * 取得客户端方法(JS方法).
	 * <br>
	 * 细粒度组件也可以触发自定义JS方法，
	 * 如：语音识别功能将调用getRecognizer()
	 * getDesirousMethod()将返回自定义的JS，在数据岛中生成相应标识.
	 * @return
	 */
	public String getDesirousMethod(){
		return null;
	}

	/**
	 * BOP对应于textField/textArea/password/hidden/label/dateField 的展现
	 * @return
	 */
	public String toText(){
		Set<Range> rangeList = getRange().getRangeList();
		Value value = getValue();
		for (Range r : rangeList) {
			if(r instanceof EnumRange) {
				EnumRange enumRange = (EnumRange)r;
				if(value instanceof MutiValue) {
					return StringUtils.getUnescapedText(getMutiValueText(enumRange.getResult(), (MutiValue) value));
				}
				else if(StringUtils.isNotEmpty(value.getValue())) {
					List<String> valueList = ContainerUtil.convertToList(value.getValue(), ConstantSplit.COMMA_SPLIT);
					if(valueList.size() == 1) {
						return StringUtils.getUnescapedText(enumRange.getResult().get(value.getValue()));
					}
					else {
						MutiValue mutiValue = new MutiValue();
						mutiValue.setMutiStrValue(valueList);
						return StringUtils.getUnescapedText(getMutiValueText(enumRange.getResult(), mutiValue));
					}
				}
				else {
					return "";
				}
			}
		}
		return StringUtils.getUnescapedText(value.getValue());
	}

	/**
	 * 多值与枚举范围的匹配
	 * @param result
	 * @param mutiValue
	 * @return
	 */
	private String getMutiValueText(Map<String, String> result, MutiValue mutiValue) {
		String str = "";
		if(mutiValue.getMutiValue().isEmpty())
			return str;
		
		for(Value value : mutiValue.getMutiValue()){
			if(StringUtils.isNotEmpty(value.getValue()))
				str += result.get(value.getValue()) + ",";
		}
		
		return StringUtils.removeEnd(str);
	}

	/**
	 * BOP对应于anchor的展现
	 * @return
	 */
	public String toLink() {
		return toText();
	}

	/**
	 * BOP对应于image的展现
	 * @return
	 */
	public String toImage() {
		return toText();
	}

	/**
	 * BOP对应于select/checkbox/radio/optiontransferselect的展现
	 * @return
	 */
	public Map<String, String> toMap(){
		Map<String, String> rangeMap = new LinkedHashMap<String, String>();
		Set<Range> rangeList = getRange().getRangeList();
		//累加枚举型的结果集
		for (Range ran : rangeList) {
			if(ran instanceof EnumRange) {
				EnumRange enumRange = (EnumRange)ran;
				rangeMap.putAll(enumRange.getResult());
			}
		}

		if(ContainerUtil.isNull(rangeMap)) {
			String value = getValue().getValue();
			rangeMap.put(value, value);
		}

		return rangeMap;
	}

	/**
	 * bop在表格中是否是排序字段
	 * @return
	 */
	public Boolean isSortAble() {
		return sortAble;
	}

	public void setSortAble(Boolean sortAble) {
		this.sortAble = sortAble;
	}

	@Override
	public BOProperty cloneBC() {
		BOProperty bop = (BOProperty) super.cloneBC();
		List<String> daoFieldNames = BoOperateUtil.getAllDaoFieldNames(bop.getClass());
		if(ContainerUtil.isNotNull(daoFieldNames)) {
			try {
				for (String fieldName : daoFieldNames) {
					PropertyUtils.setProperty(bop, fieldName, PropertyUtils.getProperty(this, fieldName));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
		return bop;
	}

	@Override
	public void free() {
		Value v = null;
		setValue(v);
		setStatus(null);
		if(getRange() != null) {
			getRange().clear();
		}
		setRange(null);
	}
}
