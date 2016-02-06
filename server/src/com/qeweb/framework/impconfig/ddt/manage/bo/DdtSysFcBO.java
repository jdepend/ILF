package com.qeweb.framework.impconfig.ddt.manage.bo;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.NOSubmitBOP;
import com.qeweb.framework.common.internation.AppLocalization;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.frameworkbop.NumberBop;
import com.qeweb.framework.impconfig.common.VCType;
import com.qeweb.framework.impconfig.ddt.manage.bop.FcStatusBOP;
import com.qeweb.framework.impconfig.ddt.manage.bop.FcTypeBOP;
import com.qeweb.framework.impconfig.ddt.manage.bop.MdtFieldsType;
import com.qeweb.framework.impconfig.ddt.manage.bop.RequiredBOP;
import com.qeweb.framework.impconfig.ddt.manage.dao.ia.IDdtSysFcDao;

/**
 * DDT管理-元组件管理-细粒度管理
 * table="qeweb_ddt_sys_fc"
 */
public class DdtSysFcBO extends BusinessObject {

	private static final long serialVersionUID = 6585795162155912527L;
	
	private String bopname;					//细粒度名称
	private Integer fcType;					//细粒度类型1textFeild, 2:textArea, 3:label, 4:PASSWORD, 5:hidden, 6:SELECT, 7:radio, 8,checkbox, 9,OptionTranserSelect, 10:anchor
	private String fcTypeShow;				//细粒度类型
	private Integer mdtFieldsId;			//对应的MDT id
	private Integer mdtFieldsType;			//列类型 1:固定列,2:动态列
	private DdtSysContainerBO container;	//所属粗粒度
	private String validateResult;			//验证结果
	private boolean fcIsRequired;			//是否必填 0:非必填 1:必填  
	private Integer	fcStatus;				//状态 1:可编辑2:只读3:不可编辑4:隐藏  
	private Integer	fcMaxLength;			//最大长度 
	private Double	fcMinValue;				//最小值
	private Double	fcMaxValue;				//最大值     
	private Double	fcStepValue;			//步进值 
	private IDdtSysFcDao ddtSysFcDao;		

	public DdtSysFcBO() {
		super();
		addOperateBOP("goback", new NOSubmitBOP());
		addBOP("bopname", new NotEmptyBop());
		addBOP("fcType", new FcTypeBOP());
		addBOP("fcTypeShow", new NotEmptyBop());
		addBOP("mdtFieldsType", new MdtFieldsType());
		addBOP("fcIsRequired", new RequiredBOP());
		addBOP("fcStatus", new FcStatusBOP());
		addBOP("fcMaxLength", new NumberBop(new BOProperty()));
		addBOP("fcMinValue", new NumberBop(new BOProperty()));
		addBOP("fcMaxValue", new NumberBop(new BOProperty()));
		addBOP("fcStepValue", new NumberBop(new BOProperty()));
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("bopname", IBaseDao.ORDER_BY_ASC);
		return result;
	}
	
	/**
	 * 到新增页面
	 * @return
	 */
	public DdtSysFcBO toAdd() {
		setDisable(this);
		return this;
	}
	
	/**
	 * 到编辑页面
	 * @return
	 */
	public DdtSysFcBO toEdit(DdtSysFcBO bo) {
		setDisable(bo);
		BOHelper.initPreferencePage(bo);
		return bo;
	}

	private void setDisable(DdtSysFcBO bo) {
		DdtSysContainerBO ddtSysContainerBO = (DdtSysContainerBO) bo.getBO("container");
		ddtSysContainerBO.getBO("page").getBOP("name").getStatus().setDisable(true);
		ddtSysContainerBO.getBOP("containerTypeShow").getStatus().setDisable(true);
		ddtSysContainerBO.getBOP("containerId").getStatus().setDisable(true);
		ddtSysContainerBO.getBOP("boName").getStatus().setDisable(true);
		bo.getBOP("fcTypeShow").getStatus().setDisable(true);
	}

	@Override
	public void insert() throws Exception {
		validateFc(this);
		super.insert();
	}

	@Override
	public void update() throws Exception {
		validateFc(this);
		super.update();
	}

	/**
	 * 验证细粒度是否重复
	 * @param ddtSysFcBO
	 * @throws BOException
	 */
	private void validateFc(DdtSysFcBO ddtSysFcBO) throws BOException {
		DdtSysFcBO _bo = ddtSysFcDao.getFc(ddtSysFcBO);
		if(_bo != null && _bo.getId() != ddtSysFcBO.getId())
			throw new BOException("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.exception.fc.repeat");
		return;
	}
	
	/**
	 * 检验细粒度是否可以删除
	 * @param bo
	 * @return
	 */
	public DdtSysFcBO validateDelete(DdtSysFcBO bo) {
		BOHelper.setBOPValue(bo, "validateResult",
				AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.delete.confirm"));
		
		//	如果页面下有粗粒度，提示用户
		if(bo.getMdtFieldsId() != null && !bo.getMdtFieldsId().equals(0)) {
			BOHelper.setBOPValue(bo, "validateResult",
					AppLocalization.getLocalization("com.qeweb.framework.impconfig.ddt.manage.bo.DdtSysFcBO.exception.fc.used"));
			bo.getBOP("validateResult").setHighlight(true);
		}
		
		return bo;
	}

	/**
	 * 删除细粒度组件
	 * @throws Exception
	 */
	public void deleteFc() throws Exception {
		DdtSysFcBO bo = (DdtSysFcBO) getRecord(this.getId());
		BOHelper.setBOPublicFields_delete(bo);
		getDao().update(bo);
	}

	public String getBopname() {
		return bopname;
	}

	public void setBopname(String bopname) {
		this.bopname = bopname;
	}

	public Integer getFcType() {
		return fcType;
	}

	public void setFcType(Integer fcType) {
		this.fcType = fcType;
	}

	public Integer getMdtFieldsId() {
		return mdtFieldsId;
	}

	public void setMdtFieldsId(Integer mdtFieldsId) {
		this.mdtFieldsId = mdtFieldsId;
	}

	public Integer getMdtFieldsType() {
		return mdtFieldsType;
	}

	public void setMdtFieldsType(Integer mdtFieldsType) {
		this.mdtFieldsType = mdtFieldsType;
	}

	public DdtSysContainerBO getContainer() {
		return container;
	}

	public void setContainer(DdtSysContainerBO container) {
		this.container = container;
	}

	public void setValidateResult(String validateResult) {
		this.validateResult = validateResult;
	}

	public String getValidateResult() {
		return validateResult;
	}

	public void setDdtSysFcDao(IDdtSysFcDao ddtSysFcDao) {
		this.ddtSysFcDao = ddtSysFcDao;
	}

	public IDdtSysFcDao getDdtSysFcDao() {
		return ddtSysFcDao;
	}

	public void setFcTypeShow(String fcTypeShow) {
		this.fcTypeShow = fcTypeShow;
	}

	public String getFcTypeShow() {
		//设定细粒度类型显示值
		if(fcType == null)
			return "";
		if (VCType.VC_TYPE_TEXTFEILD == fcType) 
			return "textField";
		if (VCType.VC_TYPE_TEXTAREA == fcType) 
			return "textArea";
		if (VCType.VC_TYPE_LABEL == fcType) 
			return "label";
		if (VCType.VC_TYPE_PASSWORD == fcType) 
			return "password";
		if (VCType.VC_TYPE_HIDDEN == fcType) 
			return "hidden";
		if (VCType.VC_TYPE_SELECT == fcType) 
			return "select";
		if (VCType.VC_TYPE_RADIO == fcType) 
			return "radio";
		if (VCType.VC_TYPE_CHECKBOX == fcType) 
			return "checkBox";
		if (VCType.VC_TYPE_OPTIONTRANSERSELECT == fcType) 
			return "optionTranserSelect";
		if (VCType.VC_TYPE_ANCHOR == fcType) 
			return "anchor";
		return fcTypeShow;
	}

	public void setFcIsRequired(boolean fcIsRequired) {
		this.fcIsRequired = fcIsRequired;
	}

	public boolean isFcIsRequired() {
		return fcIsRequired;
	}

	public Integer getFcStatus() {
		return fcStatus;
	}

	public void setFcStatus(Integer fcStatus) {
		this.fcStatus = fcStatus;
	}

	public Integer getFcMaxLength() {
		return fcMaxLength;
	}

	public void setFcMaxLength(Integer fcMaxLength) {
		this.fcMaxLength = fcMaxLength;
	}

	public Double getFcMinValue() {
		return fcMinValue;
	}

	public void setFcMinValue(Double fcMinValue) {
		this.fcMinValue = fcMinValue;
	}

	public Double getFcMaxValue() {
		return fcMaxValue;
	}

	public void setFcMaxValue(Double fcMaxValue) {
		this.fcMaxValue = fcMaxValue;
	}

	public Double getFcStepValue() {
		return fcStepValue;
	}

	public void setFcStepValue(Double fcStepValue) {
		this.fcStepValue = fcStepValue;
	}
}