package com.qeweb.framework.bc;

import java.io.Serializable;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.common.UserContext;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.manager.BOManager;

/**
 * Business Object, extends BusinessObject <br>
 * BO中可以包含BO, 也可以包含BOP.<br>
 * 粗粒度组件的校验/关联/持久化在BO 中实现;
 * <br>
 * ExtendsBusinessObject 的持久化方法中加入了回溯字段，
 * 关于追述历史记录的数据库表结构设计的详细内容请参照平台是用手册。
 */
public class ExtendsBusinessObject extends BusinessObject implements Serializable {

	private static final long serialVersionUID = 4976621536338555558L;
	private long oldId;						//上一条记录的id
	private long outerId;					//外码
	private ExtendsBusinessObject history;  // 历史修改记录
	
	public ExtendsBusinessObject(){
		super();
	}

	/**
	 * 持久化操作-插入
	 */
	public void insert() throws Exception {	
		this.setOldId(0L);
		this.setDeleteFlag(IBaseDao.UNDELETE_SIGNE);
		this.setOuterId(this.getId());
		this.setCreateTime(DateUtils.getCurrentTimestamp());
		this.setLastModifyTime(DateUtils.getCurrentTimestamp());
		getDao().save(this);
	}

	/**
	 * 持久化操作-修改
	 */
	public void update() throws Exception {
		//获得老数据
		ExtendsBusinessObject oldBO = (ExtendsBusinessObject)getDao().getById(this.getClass(), getId());
		//创建备份BO
		ExtendsBusinessObject backupBO = (ExtendsBusinessObject) BOManager.createBOByType(this.getClass());
		//拷贝老数据到备份BO
		PropertyUtils.copyProperties(backupBO, oldBO);
		//拷贝新数据到oldBO
		PropertyUtils.copyPropertyWithOutNull(oldBO, this);
		//新增备份记录
		backupBO.setOldId(backupBO.getId());
		backupBO.setOuterId(backupBO.getOuterId());
		backupBO.setCreateUserId(UserContext.getUserId());
		backupBO.setCreateTime(DateUtils.getCurrentTimestamp());
		backupBO.setLastModifyUserId(UserContext.getUserId());
		backupBO.setLastModifyTime(DateUtils.getCurrentTimestamp());
		getDao().save(backupBO);
		
		oldBO.setLastModifyTime(DateUtils.getCurrentTimestamp());
		//更新记录
		getDao().update(oldBO);
	}
	
	@Override
	protected Object getDC(BOTemplate bot) {
		// oldid等于0的记录为最新记录
		bot.push(IBaseDao.FIELD_OLDID, IBaseDao.OLD_NO);
		return super.getDC(bot);
	}

	public long getOldId() {
		return oldId;
	}

	public void setOldId(long oldId) {
		this.oldId = oldId;
	}
	
	public void setOldId(Long oldId) {
		this.oldId = (oldId == null) ? Long.MAX_VALUE : oldId;
	}

	public long getOuterId() {
		//如果外码为空时返回内码
		if(getId() != 0 && outerId == 0){
			return getId();
		}
		return outerId;
	}

	public void setOuterId(long outerId) {
		this.outerId = outerId;
	}
	
	public void setOuterId(Long outerId) {
		this.outerId = (outerId == null) ? Long.MAX_VALUE : outerId;
	}

	public void setHistory(ExtendsBusinessObject history) {
		this.history = history;
	}

	public ExtendsBusinessObject getHistory() {
		return history;
	}
}
