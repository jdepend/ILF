package com.qeweb.busplatform.masterdata.bo.manage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.busplatform.masterdata.bop.MaterialImpExlOptBOP;
import com.qeweb.busplatform.masterdata.bop.MaterialStatusBOP;
import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MathUtil;
import com.qeweb.framework.common.utils.excel.ExpExlUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.SelectMdBOP;

/**
 * 物料管理
 */
public class BP_MaterialBO extends BusinessObject {

	private static final long serialVersionUID = 1751664220956136883L;

	private String materialCode;			//物料编码
	private String materialName;			//物料名称
	private String materialDesc;			//物料描述
	private String materialSpec;			//物料规格
	private String stockUnit;				//库存单位
	private String purchaseUnit;			//采购单位
	private Integer materialStatus;			//物料状态
	private BP_MaterialTypeBO materialType; //物料品类

	//扩展字段,供弹性域使用
	private String attr_1;
	private String attr_2;
	private String attr_3;
	private String attr_4;
	private String attr_5;
	private String attr_6;
	private String attr_7;
	private String attr_8;
	private String attr_9;
	private String attr_10;

	public BP_MaterialBO() {
		super();
		addBOP("materialStatus", new MaterialStatusBOP());
		addOperateBOP("enableMaterial", new SelectMdBOP());
		addOperateBOP("disableMaterial", new SelectMdBOP());
		addOperateBOP("goImp", new MaterialImpExlOptBOP());
	}

    public void saveAll(List<BusinessObject> boList) throws Exception{
        if(ContainerUtil.isNull(boList))
            return;
        for(BusinessObject material : boList){
        	if(!(material instanceof BP_MaterialBO))
        		continue;

        	BP_MaterialBO bo = (BP_MaterialBO) material;
    		if(bo.getMaterialStatus() == null)
    			bo.setMaterialStatus(MaterialStatusBOP.YES);
    		bo.insert();
        }
    }

    /**
     * 批量启用物料
     * @param boList
     * @throws Exception
     */
    public void enableMaterial(List<BP_MaterialBO> boList) throws Exception{
        this.changeStatus(boList, MaterialStatusBOP.YES);
    }

    /**
     * 批量禁用物料
     * @param boList
     * @throws Exception
     */
    public void disableMaterial(List<BP_MaterialBO> boList) throws Exception{
        this.changeStatus(boList, MaterialStatusBOP.NO);
    }

    @SuppressWarnings("unchecked")
	public void export(BOTemplate bot) throws BOException {
		ExpExlUtil expExlUtil = null;

		DetachedCriteria dc = BOTHelper.getDetachedCriteria(bot, this.getClass());
		//查询到的总记录数
		int totalRecords = this.getDao().findTotleByCriteria(dc);
		expExlUtil = new ExpExlUtil(UserContext.getUserId(), "Material");

		if(totalRecords == 0)
			return;

		//将要导出的excel数
		int excelCount = MathUtil.getDivision(totalRecords, ExpExlUtil.XLS_DATA_COUNT);
		List<String[]> titles = getTitles();
		List<Object[]> result = new LinkedList<Object[]>();
		for(int i = 0; i < excelCount && totalRecords > 0; i++) {
			List<BP_MaterialBO> data = this.getDao()
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
			throw new BOException("导出失败！");
	}

    private void getResult(List<Object[]> result, List<BP_MaterialBO> data,
			String[] fields) {
		for(BP_MaterialBO material : data){
			BOHelper.initPreferencePage_lazy(material, this);
			int size = fields.length;
			String[] objs = new String[size];
			for(int i = 0; i < size; i++){
				objs[i] = material.getBOP(fields[i]).getValue().getValue();
			}
			result.add(objs);
		}

	}

	private List<String[]> getTitles() {
		List<String[]> titles = new ArrayList<String[]>();
		String[] fields = {"materialCode", "materialName", "materialDesc", "stockUnit", "purchaseUnit"};
		titles.add(fields);
		String[] fieldNames = {AppLocalization.getLocalization("com.qeweb.busplatform.bo.masterdata.manage.BP_MaterialBO.materialCode"),
				AppLocalization.getLocalization("com.qeweb.busplatform.bo.masterdata.manage.BP_MaterialBO.materialName"),
				AppLocalization.getLocalization("com.qeweb.busplatform.bo.masterdata.manage.BP_MaterialBO.materialDesc"),
				AppLocalization.getLocalization("com.qeweb.busplatform.bo.masterdata.manage.BP_MaterialBO.stockUnit"),
				AppLocalization.getLocalization("com.qeweb.busplatform.bo.masterdata.manage.BP_MaterialBO.purchaseUnit")};
		titles.add(fieldNames);
		return titles;
	}

	/**
     * 批量改变状态
     * @param boList
     * @param materialStatus 物料状态
     * @throws Exception
     */
    private void changeStatus(List<BP_MaterialBO> boList, int materialStatus) throws Exception{
        if(ContainerUtil.isNull(boList))
            return;

        if(!validateMaterial(boList, materialStatus))
            return;

        for(BP_MaterialBO paramBO : boList){
            BP_MaterialBO material = (BP_MaterialBO) getRecord(paramBO.getId());
            material.setMaterialStatus(materialStatus);
            material.update();
        }
    }

    /**
     * 批量改变状态校验
     * @param boList
     * @param materialStatus
     * @return
     * @throws BOException
     */
	private boolean validateMaterial(List<BP_MaterialBO> boList, int materialStatus) throws BOException {
		for (BP_MaterialBO material : boList) {
			if (MaterialStatusBOP.YES == materialStatus && materialStatus == material.getMaterialStatus())
				throw new BOException("com.qeweb.busplatform.err.masterdata.err_1");
			else if (MaterialStatusBOP.NO == materialStatus && materialStatus == material.getMaterialStatus())
				throw new BOException("com.qeweb.busplatform.err.masterdata.err_2");
		}

		return true;
	}

	/**
	 * 根据物料编码获取物料BO
	 * @param materialCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BP_MaterialBO findMaterialBO(String materialCode) {
		DetachedCriteria dc = DetachedCriteria.forClass(this.getClass());
		dc.add(Restrictions.eq("materialCode", materialCode));
		dc.add(Restrictions.eq(IBaseDao.FIELD_DELETEFLAG, IBaseDao.UNDELETE_SIGNE));
		List<BP_MaterialBO> result = getDao().findByCriteria(dc);

		return ContainerUtil.isNull(result) ? null : result.get(0);
	}

    public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getMaterialSpec() {
		return materialSpec;
	}

	public void setMaterialSpec(String materialSpec) {
		this.materialSpec = materialSpec;
	}

	public String getStockUnit() {
		return stockUnit;
	}

	public void setStockUnit(String stockUnit) {
		this.stockUnit = stockUnit;
	}

	public String getPurchaseUnit() {
		return purchaseUnit;
	}

	public void setPurchaseUnit(String purchaseUnit) {
		this.purchaseUnit = purchaseUnit;
	}

	public String getAttr_1() {
		return attr_1;
	}

	public void setAttr_1(String attr_1) {
		this.attr_1 = attr_1;
	}

	public String getAttr_2() {
		return attr_2;
	}

	public void setAttr_2(String attr_2) {
		this.attr_2 = attr_2;
	}

	public String getAttr_3() {
		return attr_3;
	}

	public void setAttr_3(String attr_3) {
		this.attr_3 = attr_3;
	}

	public String getAttr_4() {
		return attr_4;
	}

	public void setAttr_4(String attr_4) {
		this.attr_4 = attr_4;
	}

	public String getAttr_5() {
		return attr_5;
	}

	public void setAttr_5(String attr_5) {
		this.attr_5 = attr_5;
	}

	public String getAttr_6() {
		return attr_6;
	}

	public void setAttr_6(String attr_6) {
		this.attr_6 = attr_6;
	}

	public String getAttr_7() {
		return attr_7;
	}

	public void setAttr_7(String attr_7) {
		this.attr_7 = attr_7;
	}

	public String getAttr_8() {
		return attr_8;
	}

	public void setAttr_8(String attr_8) {
		this.attr_8 = attr_8;
	}

	public String getAttr_9() {
		return attr_9;
	}

	public void setAttr_9(String attr_9) {
		this.attr_9 = attr_9;
	}

	public String getAttr_10() {
		return attr_10;
	}

	public void setAttr_10(String attr_10) {
		this.attr_10 = attr_10;
	}

	public Integer getMaterialStatus() {
		return materialStatus;
	}

	public void setMaterialStatus(Integer materialStatus) {
		this.materialStatus = materialStatus;
	}

	public BP_MaterialTypeBO getMaterialType() {
		return materialType;
	}

	public void setMaterialType(BP_MaterialTypeBO materialType) {
		this.materialType = materialType;
	}
}
