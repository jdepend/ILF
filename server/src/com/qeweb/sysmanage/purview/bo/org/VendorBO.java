package com.qeweb.sysmanage.purview.bo.org;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MathUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.excel.ExpExlUtil;
import com.qeweb.sysmanage.purview.bo.OrganizationBO;
import com.qeweb.sysmanage.purview.bop.OrgTypeBOP;

/**
 * 供应商管理
 */
public class VendorBO extends OrganizationBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5539730032414579725L;

	public VendorBO() {
		super();
		getBOP("orgType").setValue(OrgTypeBOP.TYPE_VENDOR + "");
//		getBOP("orgType").getStatus().setHidden(true); wangdg
		setOrgType(OrgTypeBOP.TYPE_VENDOR);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected Class getSearchClass() {
		return OrganizationBO.class;
	}

	@Override
	public void insert() throws Exception {
		OrganizationBO orgBO = new OrganizationBO();
		PropertyUtils.copyPropertyWithOutNull(orgBO, this);
		orgBO.setOrgType(OrgTypeBOP.TYPE_VENDOR);
		orgBO.insert();
	}
	
	@Override
	public void update() throws Exception {
		OrganizationBO orgBO = new OrganizationBO();
		BOHelper.copyUpdateValue(orgBO, this);
		orgBO.setOrgType(OrgTypeBOP.TYPE_VENDOR);
		orgBO.update();
	}

	public void saveAll(List<BusinessObject> boList) throws Exception {
		 if(ContainerUtil.isNull(boList))
	     	return;
		 for(BusinessObject vendor : boList){
	     	if(!(vendor instanceof VendorBO))
	        	continue;
	        	
	     	VendorBO bo = (VendorBO) vendor;
	     	bo.setOrgType(OrgTypeBOP.TYPE_VENDOR);
	     	bo.insert();
		 }
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void export(BOTemplate bot) {
		ExpExlUtil expExlUtil = null;
		DetachedCriteria dc = BOTHelper.getDetachedCriteria(bot, getSearchClass());
		//查询到的总记录数
		int totalRecords = this.getDao().findTotleByCriteria(dc);
		expExlUtil = new ExpExlUtil(UserContext.getUserId(), "Vendor");

		if(totalRecords == 0)
			return;

		//将要导出的excel数
		int excelCount = MathUtil.getDivision(totalRecords, ExpExlUtil.XLS_DATA_COUNT);
		List<String[]> titles = getTitles();
		List<Object[]> result = new LinkedList<Object[]>();
		for(int i = 0; i < excelCount && totalRecords > 0; i++) {
			List<VendorBO> data = this.getDao()
				.findListByCriteria(dc, ExpExlUtil.XLS_DATA_COUNT, (i * ExpExlUtil.XLS_DATA_COUNT)); 
			getResult(result, data, titles.get(0));
			totalRecords -= ExpExlUtil.XLS_DATA_COUNT;
		}
		
		boolean successful = true;
		if(excelCount <= 1) {
			successful = expExlUtil.exportExcel(result, titles.get(1));
		}
		else {
			for(int i = 1; i <= excelCount; i++) {
				if(!expExlUtil.exportExcel(result, titles.get(1), i)) {
					successful = false;
					break;
				}
			}

			if(successful) {
				successful = expExlUtil.zipExcel(excelCount);
				expExlUtil.downLoad();
			}
		}

		if(!successful)
			this.setErrorMessage("导出失败！");
	}
    
    private void getResult(List<Object[]> result, List<VendorBO> data, String[] fields) {
		for(OrganizationBO vendor : data){
			BOHelper.initPreferencePage_lazy(vendor, this);
			int size = fields.length;
			String[] objs = new String[size];
			for(int i = 0; i < size; i++){
				objs[i] = vendor.getBOP(fields[i]).getValue().getValue();
			}
			result.add(objs);
		}
		
	}

	private List<String[]> getTitles() {
		List<String[]> titles = new ArrayList<String[]>();
		String[] fields = {"orgCode", "orgName", "orgEngName", "orgDesc"};
		titles.add(fields);
		String[] fieldNames = {AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.org.VendorBO.orgCode"),
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.org.VendorBO.orgName"),
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.orgEngName"),
				AppLocalization.getLocalization("com.qeweb.sysmanage.purview.bo.OrganizationBO.orgDesc")};
		titles.add(fieldNames);
		return titles;
	}
}
