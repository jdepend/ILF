package com.qeweb.framework.impconfig.ddt.manage.bop;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.EnumRange;
import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysAnalyzeFcBO;
import com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysContainerBO;

/**
 * DDT元组件管理-粗粒度组件管理 粗粒度组件类型：1:Form; 2:TABLE; 3:tab
 */
public class ContainerTypeBOP extends BOProperty {
	private static final long serialVersionUID = 4624493881488837032L;
	

	@Override
	public void init() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		EnumRange.addPlease(map);
		map.put(VCType.VC_TYPE_FORM+ "", "Form");
		map.put(VCType.VC_TYPE_TABLE+ "", "Table");
		map.put(VCType.VC_TYPE_TAB+ "", "Tab");		
		
		EnumRange range = new EnumRange();
		range.setResult(map);
		getRange().addRange(range);
	}
	
	@Override
	public List<BusinessComponent> getRelations() {
		List<BusinessComponent> result = new LinkedList<BusinessComponent>();
		result.add(new DdtSysAnalyzeFcBO());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		if(BoOperateUtil.getRealBop(sourceBop) instanceof DdtsysModulesBOP 
				|| StringUtils.isEmpty(sourceBop.getValue().getValue()))
			return this;
		EnumRange range = new EnumRange();
		Map<String, String> map = new LinkedHashMap<String, String>();
		range.setResult(map);
		BCRange bcRange = new BCRange();
		bcRange.addRange(range);
		setRange(bcRange);		

		DetachedCriteria dc = DetachedCriteria.forClass(DdtSysContainerBO.class);
		dc.add(Restrictions.eq("page.id", Long.parseLong(sourceBop.getValue().getValue())));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		List<DdtSysContainerBO> list = getDao().findByCriteria(dc);
			
		for (DdtSysContainerBO bo : list) {
			if (bo.getContainerType() != null 
					&& VCType.VC_TYPE_FORM == bo.getContainerType().intValue()) {
				map.put(VCType.VC_TYPE_FORM + "", "Form");
			}
			else if (bo.getContainerType() != null 
					&& VCType.VC_TYPE_TABLE == bo.getContainerType().intValue()) {
				map.put(VCType.VC_TYPE_TABLE + "", "Table");
			}
			else if (bo.getContainerType() != null 
					&& VCType.VC_TYPE_TAB == bo.getContainerType().intValue()) {
				map.put(VCType.VC_TYPE_TAB + "", "Tab");
			}
		}

		return this;
	}

}
