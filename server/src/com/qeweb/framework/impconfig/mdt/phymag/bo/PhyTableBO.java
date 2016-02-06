package com.qeweb.framework.impconfig.mdt.phymag.bo;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.OperateBOP;
import com.qeweb.framework.common.SpringConstant;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.frameworkbop.EmptyBop;
import com.qeweb.framework.frameworkbop.NotEmptyBop;
import com.qeweb.framework.impconfig.mdt.phymag.dao.ia.IPhyTableDao;

/**
 * 物理表管理BO，负责管理数据库中物理表
 */
public class PhyTableBO extends BusinessObject {
	
	private static final long serialVersionUID = -198141201322756337L;
	
	private String name;	//表名
	private String alias;	//物理表别名
	private String remark;	//备注
	
	private Set<PhyColumnBO> phyColumnSet = new LinkedHashSet<PhyColumnBO>();
	private IPhyTableDao phyTableDao;

	public PhyTableBO() {
		super();
		addBOP("name", new NotEmptyBop(3, 30));
		addBOP("alias", new NotEmptyBop(3, 30));
		addBOP("remark", new EmptyBop(128));
		
		OperateBOP delBOP = new OperateBOP();
		delBOP.setConfirmMsg("删除表将丢失所有数据，确认执行该操作？");
		addOperateBOP("delete", delBOP);
	}
	
	@Override
	public Object query(BOTemplate bot, int start) throws Exception {
		//跳转到编辑页面时根据id查询
		if(bot != null && StringUtils.isNotEmpty(bot.getValue(IBaseDao.FIELD_ID) + "")) {
			return super.query(bot, start);
		}
		
		return null;
	}
	
	@Override
	public Map<String, String> queryOrderBy() {
		Map<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("name", IBaseDao.ORDER_BY_ASC);
		return orderMap;
	}
	
	/**
	 * 保存操作
	 * @throws BOException
	 */
	public void save() throws BOException {
		getPhyTableDao().saveOrUpdate(this);
	}
	
	@Override
	public void delete(List<BusinessComponent> bcList) throws Exception {
		getPhyTableDao().delete(bcList);
	}
	
	/**
	 * save 前的校验操作，校验表、字段信息等是否正确
	 * @param phyTableBO
	 * @return
	 */
	public boolean validate(PhyTableBO phyTableBO) throws BOException {
		PhyTableBO result = null;
		String tableName = StringUtils.toUpperCase(phyTableBO.getName());
		String tableAlias = StringUtils.removeAllSpace(phyTableBO.getAlias());
		
		//新增时的校验
		if(phyTableBO.getId() == 0L) {
			result = getPhyTableDao().findUndelByName(tableName, tableAlias);
		}
		//修改时的校验
		else {
			result = getRecord(phyTableBO.getId());
			//编辑时未修改表名及别名
			if(StringUtils.isEqualIgnoreCase(result.getName(), tableName)) 
				return true;
			
			result = getPhyTableDao().findUndelByName(tableName, tableAlias);
		}
		
		if(result == null) 
			return true;
		else
			throw new BOException("该表名或表别名已经存在！");
	}
	
	@Override
	public PhyTableBO getRecord(long id) {
		return getPhyTableDao().findById(id);
	}
	
	public IPhyTableDao getPhyTableDao() {
		if(phyTableDao == null)
			phyTableDao = (IPhyTableDao)SpringConstant.getCTX().getBean("phyTableDao");
		return phyTableDao;
	}

	public void setPhyTableDao(IPhyTableDao phyTableDao) {
		this.phyTableDao = phyTableDao;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<PhyColumnBO> getPhyColumnSet() {
		return phyColumnSet;
	}

	public void setPhyColumnSet(Set<PhyColumnBO> phyColumnSet) {
		this.phyColumnSet = phyColumnSet;
	}
	
}
