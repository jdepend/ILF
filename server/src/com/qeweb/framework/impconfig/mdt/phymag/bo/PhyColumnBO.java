package com.qeweb.framework.impconfig.mdt.phymag.bo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.phymag.bop.PhyColDataTypeBOP;
import com.qeweb.framework.impconfig.mdt.phymag.bop.PhyColIsNotNullBOP;

/**
 * 物理表字段
 */
public class PhyColumnBO extends BusinessObject {

	private static final long serialVersionUID = -906955680455897379L;

	private String name;		//字段名
	private String alias;		//字段别名
	private String dataType;	//数据类型
	private Integer maxLength;	//最大长度
	private String defValue;	//默认值
	private int isNotNull;		//是否非空
	private String remark;		//备注
	private PhyTableBO phyTable;
	
	public PhyColumnBO() {
		super();
		addBOP("name", new NotEmptyBop(1, 30));
		addBOP("alias", new NotEmptyBop(1, 30));
		addBOP("dataType", new PhyColDataTypeBOP());
		addBOP("isNotNull", new PhyColIsNotNullBOP());
		addBOP("remark", new EmptyBop(128));
	}
	
	@SuppressWarnings("deprecation")
	public Object query(BOTemplate bot, int start) throws Exception {
		//跳转到编辑页面时根据phyTable.id查询
		if(bot != null && StringUtils.isEqual(bot.getBoName(), "phyTableBO")) {
			BOTemplate colBOT = new BOTemplate();
			colBOT.push("phyTable.id", bot.getValue(IBaseDao.FIELD_ID));
			return super.query(colBOT);
		}
		
		return null;
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put(IBaseDao.FIELD_ID, IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}
	
	/**
	 * 新增物理列
	 * @param phyTableBO   表信息
	 * @param phyColumnSet 动态改变的列信息
	 * @throws BOException
	 */
	public void insert(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColumnSet) throws BOException {
		//校验操作，校验表、字段、主外键、索引信息等是否正确
		if(phyTableBO.validate(phyTableBO) && validateAdd(phyColumnSet)) {
			Set<PhyColumnBO> phyColumnSetForSave = new LinkedHashSet<PhyColumnBO>();
			phyColumnSetForSave.addAll(phyColumnSet);
			phyColumnSetForSave.addAll(getQewebCols(phyTableBO));
			phyTableBO.setPhyColumnSet(phyColumnSetForSave);
			phyTableBO.save();
		}
	}
	
	/**
	 * 修改物理列
	 * @param phyTableBO   表信息
	 * @param phyColumnSet 动态改变的列信息
	 * @param delColIds	        动态删除的列信息
	 * @throws Exception 
	 */
	public void update(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColumnSet, List<Long> delColIds) throws Exception {
		if(!phyTableBO.validate(phyTableBO) || !validateCommon(phyColumnSet))
			return;
			
		// 动态删除的字段信息
		Set<PhyColumnBO> delCols = getDelCols(phyTableBO, phyColumnSet, delColIds);
		// 动态修改的字段信息
		Set<PhyColumnBO> modifyCols = getModifyCols(phyTableBO, phyColumnSet);
		// 动态添加的字段信息
		Set<PhyColumnBO> addCols = getAddCols(phyTableBO, phyColumnSet);

		if(ContainerUtil.isNotNull(delCols)) {
			for (PhyColumnBO colBO : delCols) {
				colBO.setDeleteFlag(IBaseDao.DELETE_SIGNE);
			}
			phyTableBO.getPhyColumnSet().addAll(delCols);
		}

		if(ContainerUtil.isNotNull(modifyCols)) 
			phyTableBO.getPhyColumnSet().addAll(modifyCols);
		
		if(ContainerUtil.isNotNull(addCols)) 
			phyTableBO.getPhyColumnSet().addAll(addCols);
		
		phyTableBO.save();
	}
	
	/**
	 * 新增时的校验
	 * @param phyColumnSet 动态改变的列信息
	 * @throws BOException
	 */
	public boolean validateAdd(Set<PhyColumnBO> phyColumnSet) throws BOException {
		if(!validateCommon(phyColumnSet))
			return false; 
		
		//字段名set,用于存储phyColumnSet中的字段名称，以识别是否有重名字段
		Set<String> colNameSet = new LinkedHashSet<String>();
		for(PhyColumnBO colBO : phyColumnSet) {
			colNameSet.add(StringUtils.toUpperCase(colBO.getName()));
		}
		
		if(colNameSet.size() != phyColumnSet.size())
			throw new BOException("字段名称不能重复！");
		else if(ContainerUtil.isNull(phyColumnSet))
			throw new BOException("表中没有字段，请至少添加一个字段！");
		else 
			return true;
	}
	
	/**
	 * 获取待删除的物理表字段信息.
	 * 该方法修改了 phyColumnSet
	 * @param phyTableBO
	 * @param phyColumnSet
	 * @param delColIds
	 * @return
	 */
	public Set<PhyColumnBO> getDelCols(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColumnSet, List<Long> delColIds) {
		if(ContainerUtil.isNull(delColIds)) 
			return null;
		
		//1.找出所有delColIds对应的字段信息
		Set<PhyColumnBO> delCols = new HashSet<PhyColumnBO>();
		for(Long id : delColIds) {
			PhyColumnBO colBO = (PhyColumnBO) getDao().getById(PhyColumnBO.class, id);
			colBO.setPhyTable(phyTableBO);
			delCols.add(colBO);
		}
		if(ContainerUtil.isNull(delCols) || ContainerUtil.isNull(phyColumnSet)) 
			return delCols;
		
		//2.delCols中的数据与phyColumnSet中的数据比较
		Iterator<PhyColumnBO> modifyColItr = phyColumnSet.iterator();
		Iterator<PhyColumnBO> delColItr = delCols.iterator();
		while(delColItr.hasNext()) {
			PhyColumnBO delCol = delColItr.next();
			modifyColItr = phyColumnSet.iterator();
			while(modifyColItr.hasNext()) {
				PhyColumnBO modifyCol = modifyColItr.next();
				//2.1 如果delCol的数据与modifyCol的数据完全相等, 说明modifyCol的信息没有修改, 从phyColumnSet和delCols中删除相应数据
				if(delCol.isEqual(modifyCol)) {
					phyColumnSet.remove(modifyCol);
					delCols.remove(delCol);
				}
				//2.2 如果delCol的数据与modifyCol的数据仅有字段名称相等, 说明修改了字段的定义, 从delCols中删除相应数据
				else if(StringUtils.isEqualIgnoreCase(delCol.getName(), modifyCol.getName())) {
					delCols.remove(delCol);
				}
			}
		}
		
		return delCols;
	}
	
	/**
	 * 获取待修改的物理表字段信息. 
	 * 该方法修改了 phyColumnSet
	 * @param phyTableBO   表信息
	 * @param phyColumnSet 动态改变的列信息
	 * @throws Exception 
	 */
	public Set<PhyColumnBO> getModifyCols(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColumnSet) throws Exception {
		if(ContainerUtil.isNull(phyColumnSet))
			return null;
		
		//1. 查找已经存在的字段信息
		PhyTableBO tableBO = (PhyTableBO)phyTableBO.getRecord(phyTableBO.getId());
		Set<PhyColumnBO> oldCols = tableBO.getPhyColumnSet();
		if(ContainerUtil.isNull(oldCols))
			return null;
		
		Set<PhyColumnBO> result = new HashSet<PhyColumnBO>();
		//1.1 oldCols中的数据与phyColumnSet中的数据比较
		Iterator<PhyColumnBO> modifyColItr = phyColumnSet.iterator();
		Iterator<PhyColumnBO> oldColItr = oldCols.iterator();
		while(oldColItr.hasNext()) {
			PhyColumnBO oldCol = oldColItr.next();
			modifyColItr = phyColumnSet.iterator();
			while(modifyColItr.hasNext()) {
				PhyColumnBO modifyCol = modifyColItr.next();
				//1.2 如果oldCol的数据与modifyCol的数据完全相等, 说明modifyCol的信息没有修改, 从phyColumnSet中删除相应数据
				if(oldCol.isEqual(modifyCol)) {
					phyColumnSet.remove(modifyCol);
				}
				//1.3 如果oldCol的数据与modifyCol仅有字段名称相等, 说明修改了字段的定义, 将数据移至result, 并从phyColumnSet中删除相应数据
				else if(StringUtils.isEqualIgnoreCase(modifyCol.getName(), oldCol.getName())) {
					modifyCol.setPhyTable(phyTableBO);
					result.add(modifyCol);
					phyColumnSet.remove(modifyCol);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取待新增的物理表字段信息. 
	 * 该方法修改了 phyColumnSet
	 * @param phyTableBO	物理表信息
	 * @param phyColumnSet 	动态改变的列信息
	 * @throws Exception 
	 */
	public Set<PhyColumnBO> getAddCols(PhyTableBO phyTableBO, Set<PhyColumnBO> phyColumnSet) throws Exception {
		if(ContainerUtil.isNull(phyColumnSet))
			return null;
		
		Set<PhyColumnBO> result = new LinkedHashSet<PhyColumnBO>();
		for(PhyColumnBO col : phyColumnSet) {
			if(col.getId() == 0L) {
				col.setPhyTable(phyTableBO);
				result.add(col);
			}
		}
		
		return result;
	}
	
	/**
	 * 校验操作（一般性校验）, 主要校验字段属性是否为空, 是否超长等
	 * @param phyColumnSet 动态修改的字段
	 * @return
	 */
	public boolean validateCommon(Set<PhyColumnBO> phyColumnSet) throws BOException {
		//一般性校验
		for(PhyColumnBO colBO : phyColumnSet) {
			if(StringUtils.isEmptyStr(colBO.getAlias()))
				throw new BOException("字段别名不能为空！");
			else if(StringUtils.isEmptyStr(colBO.getDataType()))
				throw new BOException("字段类型不能为空！");
		}
		
		return true;
	}

	/**
	 * bo1的属性是否与当前句柄相等
	 * @param bo1
	 * @return
	 */
	private boolean isEqual(PhyColumnBO bo) {
		return StringUtils.isEqualIgnoreCase(bo.getName(), getName()) 
			&& StringUtils.isEqualIgnoreCase(bo.getAlias(), getAlias())
			&& StringUtils.isEqualIgnoreCase(bo.getDataType(), getDataType())
			&& StringUtils.isEqualIgnoreCase(bo.getDefValue(), getDefValue())
			&& StringUtils.isEqualIgnoreCase(bo.getRemark(), getRemark())
			&& bo.getMaxLength() == getMaxLength()
			&& bo.getIsNotNull() == getIsNotNull();
	}
	
	/**
	 * 添加4个平台字段: id, createUserId, createTime, lastModifyTime, deleteFlag
	 */
	private Set<PhyColumnBO> getQewebCols(PhyTableBO phyTableBO) {
		Set<PhyColumnBO> result = new LinkedHashSet<PhyColumnBO>();
		result.add(createColBO(IBaseDao.PHY_ID, PhyColDataTypeBOP.DATATYPE_INT, 11, PhyColIsNotNullBOP.YES, null));
		result.add(createColBO(IBaseDao.PHY_CREATEUSERID, PhyColDataTypeBOP.DATATYPE_INT, 11, PhyColIsNotNullBOP.NO, null));
		result.add(createColBO(IBaseDao.PHY_CREATETIME, PhyColDataTypeBOP.DATATYPE_DATE, null, PhyColIsNotNullBOP.NO, null));
		result.add(createColBO(IBaseDao.PHY_LASTMODIFYUSERID, PhyColDataTypeBOP.DATATYPE_INT, 11, PhyColIsNotNullBOP.NO, null));
		result.add(createColBO(IBaseDao.PHY_LASTMODIFYTIME, PhyColDataTypeBOP.DATATYPE_DATE, null, PhyColIsNotNullBOP.NO, null));
		result.add(createColBO(IBaseDao.PHY_DELETEFLAG, PhyColDataTypeBOP.DATATYPE_INT, 1, PhyColIsNotNullBOP.YES, IBaseDao.UNDELETE_SIGNE + ""));
		
		for(PhyColumnBO colBO : result) {
			colBO.setPhyTable(phyTableBO);
		}
		
		return result;
	}
	
	/**
	 * 根据参数创建PhyColumnBO
	 * @param name
	 * @param dataType
	 * @param maxLength
	 * @param isNotNull
	 * @param defValue
	 * @return
	 */
	private PhyColumnBO createColBO(String name, String dataType, Integer maxLength, String isNotNull, String defValue) {
		PhyColumnBO colBO = new PhyColumnBO();
		colBO.setName(name);
		colBO.setAlias(name);
		colBO.setDataType(dataType);
		colBO.setMaxLength(maxLength);
		colBO.setIsNotNull(StringUtils.convertToInt(isNotNull));
		colBO.setDefValue(defValue);
		colBO.setRemark("");
		
		return colBO;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	public int getIsNotNull() {
		return isNotNull;
	}
	public void setIsNotNull(int isNotNull) {
		this.isNotNull = isNotNull;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public PhyTableBO getPhyTable() {
		return phyTable;
	}
	public void setPhyTable(PhyTableBO phyTable) {
		this.phyTable = phyTable;
	}
}
