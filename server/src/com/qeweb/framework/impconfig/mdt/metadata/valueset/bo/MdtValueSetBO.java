package com.qeweb.framework.impconfig.mdt.metadata.valueset.bo;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;

/**
 * 值集BO
 */
public class MdtValueSetBO extends BusinessObject {
	
	private static final long serialVersionUID = 5248775043984126981L;
	
	private String code;
	private String name;
	private String remark;
	private Set<MdtValueBO> mdtValues = new HashSet<MdtValueBO>();
	
	private IMdtValueSetDao mdtValueSetDao;
	
	public MdtValueSetBO(){
		addBOP("code", new NotEmptyBop(1, 50));
		addBOP("name", new NotEmptyBop(1, 50));
		addBOP("remark", new EmptyBop(100));
		addBOP("goback", new NOSubmitBOP());
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("code", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}

	/**
	 * 跳转明细页面后执行此方法
	 * @param valueSet
	 * @return
	 */
	public MdtValueSetBO view(MdtValueSetBO valueSet){
		return valueSet;
	}

	@Override
	public void insert() throws Exception {
		if(validate()){
			super.insert();
		}
	}

	@Override
	public void update() throws Exception {
		if(validate()){
			super.update();
		}
	}

	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		for(BusinessComponent bc : bcList){
			MdtValueSetBO  mdtValue = (MdtValueSetBO) bc;
			Set<MdtValueBO> values = mdtValue.getMdtValues();
			getMdtValueSetDao().deleteValues(values);
		}
		
		super.delete(bcList);
	}
	
	@Override
	public boolean validate() throws Exception {
		return validateCode() && validateName();
	}
	
	/**
	 * 值集中是否存在等于value的值
	 * @param value
	 * @return
	 */
	public boolean isExistValue(String value) {
		if(StringUtils.isEmpty(value))
			return false;
		
		Set<MdtValueBO> mdtValues = getMdtValues();
		if(ContainerUtil.isNull(mdtValues))
			return false;
		
		for(MdtValueBO bo : mdtValues) {
			if(StringUtils.isEqual(bo.getMdtValue(), value))
				return true;
		}
		
		return false;
	}
	
	/**
	 * 验证编码是否存在
	 * @return
	 * @throws Exception
	 */
	private boolean validateCode() throws Exception{
		List<MdtValueSetBO> list = getMdtValueSetDao().getMdtValueSetByCodeAndName(getCode(), null);
		if(BOHelper.saveValidate(getId(), list)) 
			return true;
		else
			throw new BOException("值集编码已存在！");
	}
	
	/**
	 * 验证名称是否存在
	 * @return
	 * @throws Exception
	 */
	private boolean validateName() throws Exception{
		List<MdtValueSetBO> list = getMdtValueSetDao().getMdtValueSetByCodeAndName(null, getName());
		if(BOHelper.saveValidate(getId(), list)) 
			return true;
		else
			throw new BOException("值集名称已存在！");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Set<MdtValueBO> getMdtValues() {
		return mdtValues;
	}

	public void setMdtValues(Set<MdtValueBO> mdtValues) {
		this.mdtValues = mdtValues;
	}

	public IMdtValueSetDao getMdtValueSetDao() {
		if(mdtValueSetDao == null)
			return (IMdtValueSetDao)SpringConstant.getCTX().getBean("mdtValueSetDao");
		return mdtValueSetDao;
	}

	public void setMdtValueSetDao(IMdtValueSetDao mdtValueSetDao) {
		this.mdtValueSetDao = mdtValueSetDao;
	}	
	
}
