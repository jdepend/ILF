package com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.impl.BaseDaoHibImpl;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.bo.MdtValueSetBO;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;

public class MdtValueSetDaoImpl extends BaseDaoHibImpl implements IMdtValueSetDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7847573150278106074L;


	@SuppressWarnings("unchecked")
	@Override
	public List<MdtValueBO> getMdtValuesBySetId(Long valueSetId) {
		DetachedCriteria dc = DetachedCriteria.forClass(MdtValueBO.class);
		dc.add(Restrictions.eq("valueSet.id", valueSetId));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MdtValueSetBO> getAllMdtValueSet() {
		DetachedCriteria dc = DetachedCriteria.forClass(MdtValueBO.class);
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

	@Override
	public void deleteValues(Set<MdtValueBO> values) {
		if(ContainerUtil.isNotNull(values)){
			for(MdtValueBO value : values){
				value.setDeleteFlag(IBaseDao.DELETE_SIGNE);
				update(value);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MdtValueSetBO> getMdtValueSetByCodeAndName(String code, String name) {
		DetachedCriteria dc = DetachedCriteria.forClass(MdtValueSetBO.class);
		if(!StringUtils.isEmpty(code)){
			dc.add(Restrictions.eq("code", code));
		}
		if(!StringUtils.isEmpty(name)){
			dc.add(Restrictions.eq("name", name));
		}
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MdtValueBO> getMdtValueByValueAndText(String value,
			String text) {
		DetachedCriteria dc = DetachedCriteria.forClass(MdtValueBO.class);
		if(!StringUtils.isEmpty(value)){
			dc.add(Restrictions.eq("mdtValue", value));
		}
		if(!StringUtils.isEmpty(text)){
			dc.add(Restrictions.eq("text", text));
		}
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<MdtValueBO> getMdtValueByValueAndTextAndValueSetId(
			String value, String text, String valueSetId) {
		DetachedCriteria dc = DetachedCriteria.forClass(MdtValueBO.class);
		if(!StringUtils.isEmpty(value)){
			dc.add(Restrictions.eq("mdtValue", value));
		}
		if(!StringUtils.isEmpty(text)){
			dc.add(Restrictions.eq("text", text));
		}
		if(!StringUtils.isEmpty(valueSetId)){
			dc.add(Restrictions.eq("valueSet.id", Long.parseLong(valueSetId)));
		}
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		return findByCriteria(dc);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<MdtValueBO> getMdtValuesBySetCode(String valueSetCode) {
        StringBuilder hql = new StringBuilder("Select v from MdtValueBO v, MdtValueSetBO vs where v.valueSet.id = vs.id ");
        hql.append(" and v.").append(IBaseDao.FIELD_DELETEFLAG).append(" = ").append(IBaseDao.UNDELETE_SIGNE);
        hql.append(" and vs.code = '").append(valueSetCode).append("' ");
        return this.findBySql(hql.toString());
    }

	@Override
	public List<MdtValueSetBO> getMdtValueSet(Set<String> valueSetCodes) {
		if(ContainerUtil.isNull(valueSetCodes))
			return null;
		
		List<MdtValueSetBO> result = new ArrayList<MdtValueSetBO>();
		for(String valueSetCode : valueSetCodes) {
			List<MdtValueSetBO> valueSet = getMdtValueSetByCodeAndName(valueSetCode, null);
			if(ContainerUtil.isNotNull(valueSet))
				result.addAll(valueSet);
		}
		
		return result;
	}
}
