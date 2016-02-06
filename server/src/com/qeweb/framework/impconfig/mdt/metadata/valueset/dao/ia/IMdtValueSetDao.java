package com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia;

import java.util.List;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;

public interface IMdtValueSetDao extends IBaseDao {
	
	
	/**
	 * 通过值集Id 查询值
	 * @param valueSetId
	 * @return
	 */
	List<MdtValueBO> getMdtValuesBySetId(Long valueSetId);
	
	/**
	 * 查询所有值集
	 * @return
	 */
	List<MdtValueSetBO> getAllMdtValueSet(); 
	
	/**
	 * 删除值集所有子项
	 * @param values
	 */
	void deleteValues(Set<MdtValueBO> values);
	
	/**
	 * 根据code查询多值集
	 * @return
	 */
	List<MdtValueSetBO> getMdtValueSet(Set<String> valueSetCodes); 
	
	/**
	 * 根据code和name查询值集
	 * @return
	 */
	List<MdtValueSetBO> getMdtValueSetByCodeAndName(String code, String name); 
	
	/**
	 * 根据value和text查询值集
	 * @return
	 */
	List<MdtValueBO> getMdtValueByValueAndText(String value, String text); 
	
	/**
	 * 根据value和text查询值集
	 * @return
	 */
	List<MdtValueBO> getMdtValueByValueAndTextAndValueSetId(String value, String text,String valueSetId);

    /**
     * 通过值集code查询值
     * @param valueSetCode
     * @return
     */
    List<MdtValueBO> getMdtValuesBySetCode(String valueSetCode);
}
