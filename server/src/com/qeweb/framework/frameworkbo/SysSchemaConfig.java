package com.qeweb.framework.frameworkbo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pal.Schema;

/**
 * SysSchemaConfig对应 sys_schemaconfig表, 
 * 负责查找不同页面的展示元素和展示方案的对应关系
 *
 */
public class SysSchemaConfig extends BusinessObject {

	private static final long serialVersionUID = 1L;
	private SysSchema sysSchema;
	private String containerId;
	private String bindBO;
	private String bindBOP;
	private String sourcePage;
	
	public SysSchemaConfig() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object query(BOTemplate bot, int start) {
		Schema schema = null;
		DetachedCriteria dc = DetachedCriteria.forClass(SysSchemaConfig.class);
		dc.add(Restrictions.eq("containerId", bot.getValue("containerId")));
		dc.add(Restrictions.eq("bindBO", bot.getValue("bindBO")));
		dc.add(Restrictions.eq("bindBOP", bot.getValue("bindBOP")));
		dc.add(Restrictions.eq("sourcePage", bot.getValue("sourcePage")));
		List<SysSchemaConfig> list = getDao().findByCriteria(dc);
		if (ContainerUtil.isNotNull(list) && list.size() == 1) {
			SysSchemaConfig schemaConfig = list.get(0);
			SysSchema sysSchema = schemaConfig.getSysSchema();
			Long sid = sysSchema.getId();
			schema = new Schema(sid.intValue(), sysSchema.getBackgroundColor(),
					sysSchema.getFrontgroundColor(), sysSchema.getFont());
		}
		return schema;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public SysSchema getSysSchema() {
		return sysSchema;
	}

	public void setSysSchema(SysSchema sysSchema) {
		this.sysSchema = sysSchema;
	}

	public String getBindBO() {
		return bindBO;
	}

	public void setBindBO(String bindBO) {
		this.bindBO = bindBO;
	}

	public String getBindBOP() {
		return bindBOP;
	}

	public void setBindBOP(String bindBOP) {
		this.bindBOP = bindBOP;
	}

	public String getSourcePage() {
		return sourcePage;
	}

	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
}
