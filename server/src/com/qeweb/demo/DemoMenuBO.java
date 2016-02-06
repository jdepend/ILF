package com.qeweb.demo;

import java.util.HashSet;
import java.util.Set;

import com.qeweb.framework.bc.sysbo.MenuBO;

/**
 * demo首页菜单
 */
public class DemoMenuBO extends MenuBO {

	private static final long serialVersionUID = -6995483264446267051L;
	
	private IDemoMenuDao demoMenuDao;
	
	public DemoMenuBO() {
		super();
	}
	
	public DemoMenuBO(long id, long parentId, String value, int sortIndex,
			String path) {
		super(id, parentId, value, sortIndex, path);
	}

	@Override
	public long getRootId() {
		return 0L;
	}

	@Override
	public void create() {
		//查询出所有菜单, 将菜单放在modules中
		Set<DemoMenuBO> modules = new HashSet<DemoMenuBO>();
		modules.addAll(getDemoMenuDao().findMenus());

		//构造菜单
		for(DemoMenuBO module : modules) {
			DemoMenuBO menu = 
				new DemoMenuBO(module.getId(), module.getParentId(), module.getNodeValue(),
						module.getSortIndex(), module.getPath());
			this.add(menu);
		}
	}

	public IDemoMenuDao getDemoMenuDao() {
		return demoMenuDao;
	}

	public void setDemoMenuDao(IDemoMenuDao demoMenuDao) {
		this.demoMenuDao = demoMenuDao;
	}
}
