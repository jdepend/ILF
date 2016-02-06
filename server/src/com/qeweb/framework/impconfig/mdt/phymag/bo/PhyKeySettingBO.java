package com.qeweb.framework.impconfig.mdt.phymag.bo;

import java.util.LinkedHashMap;
import java.util.Map;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.impconfig.mdt.phymag.bop.PhyKeyType;

/**
 * 物理列主外键设置
 */
public class PhyKeySettingBO extends BusinessObject {

	private static final long serialVersionUID = -1386019777968005149L;
	
	private String name;			//键名称
	private int type;				//键类型(主键/外键)
	private long columnId;
	private Long referenceTableId;
	private Long referenceColumnId;
	private String remark;			//备注

	public PhyKeySettingBO() {
		super();
		addBOP("type", new PhyKeyType());
	}
	
	@SuppressWarnings("deprecation")
	public Object query(BOTemplate bot, int start) throws Exception {
		BOTemplate phyKeyBOT = new BOTemplate();
		phyKeyBOT.push("columnId", bot.getValue("id"));
		
		return query(phyKeyBOT);
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put(IBaseDao.FIELD_ID, IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public Long getReferenceTableId() {
		return referenceTableId;
	}

	public void setReferenceTableId(Long referenceTableId) {
		this.referenceTableId = referenceTableId;
	}

	public Long getReferenceColumnId() {
		return referenceColumnId;
	}

	public void setReferenceColumnId(Long referenceColumnId) {
		this.referenceColumnId = referenceColumnId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getColumnId() {
		return columnId;
	}

	public void setColumnId(long columnId) {
		this.columnId = columnId;
	}
	
}
