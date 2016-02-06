package com.qeweb.framework.impconfig.mdt.metadata.valueset.bo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOHelper;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.MsgService;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.metadata.valueset.dao.ia.IMdtValueSetDao;

/**
 * MDT弹性域对应的枚举值
 */
public class MdtValueBO extends BusinessObject implements Serializable {
	
	private static final long serialVersionUID = -682851553271352536L;

	private String mdtValue;
	private String text;
	private MdtValueSetBO valueSet;		//值集

	private IMdtValueSetDao mdtValueSetDao;
	
	public MdtValueBO(){
		super();
		addBOP("mdtValue", new NotEmptyBop(1, 500));
		addBOP("text", new NotEmptyBop(1, 50));
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		String valueSetId = bot.getValue("id") + "";
		bot.getBotMap().clear();
		bot.push("valueSet.id", valueSetId);
		MsgService.setMsg("valueSetId",valueSetId);
		return super.query(bot, start);
	}

	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("mdtValue", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}
	
	@Override
	public void insert() throws Exception {
		if(validate()){
			Long valueSetId = Long.parseLong((String)MsgService.getMsg("valueSetId"));
			this.setValueSet((MdtValueSetBO)getDao().getById(MdtValueSetBO.class, valueSetId));
			super.insert();
		}
	}
	
	@Override
	public void update() throws Exception {
		if(validate())
			super.update();
	}

	@Override
	public boolean validate() throws Exception {
		return validateValue() && validateText();
	}
	
	/**
	 * 验证value是否存在
	 * @return
	 * @throws Exception
	 */
	private boolean validateValue() throws Exception{
		String valueSetId = (String)MsgService.getMsg("valueSetId");
		List<MdtValueBO> list = getMdtValueSetDao().getMdtValueByValueAndTextAndValueSetId(getMdtValue(), null, valueSetId);
		if(BOHelper.saveValidate(getId(), list)) 
			return true;
		else
			throw new BOException("值已存在！");
	}
	
	/**
	 * 验证展示是否存在
	 * @return
	 * @throws Exception
	 */
	private boolean validateText() throws Exception{
		String valueSetId = (String)MsgService.getMsg("valueSetId");
		List<MdtValueBO> list = getMdtValueSetDao().getMdtValueByValueAndTextAndValueSetId(null, getText(),valueSetId);
		if(BOHelper.saveValidate(getId(), list)) 
			return true;
		else
			throw new BOException("值展示已存在！");
	}
	
	
	public String getMdtValue() {
		return mdtValue;
	}
	public void setMdtValue(String mdtValue) {
		this.mdtValue = mdtValue;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public MdtValueSetBO getValueSet() {
		return valueSet;
	}

	public void setValueSet(MdtValueSetBO valueSet) {
		this.valueSet = valueSet;
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
