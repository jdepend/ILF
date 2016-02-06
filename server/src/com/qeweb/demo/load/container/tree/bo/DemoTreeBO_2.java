package com.qeweb.demo.load.container.tree.bo;

import java.util.Set;

import com.qeweb.demo.IDemoMenuDao;
import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.TreeBO;

/**
 * demo: 树示例. 级联多选树
 * 路径: 装载-树-选择树
 */
public class DemoTreeBO_2 extends CheckTreeBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1809981132586344927L;
	
	private IDemoMenuDao demoMenuDao;
	
	public DemoTreeBO_2() {
		super();
	}
	
	public DemoTreeBO_2(long id, long parentId, String value, int sortIndex,
			boolean checked) {
		super(id, parentId, value, sortIndex, checked);
	}
	
	@Override
	public long getRootId() {
		return 0;
	}

	@Override
	public void create() {
		//查询出所有节点, 将节点放在nodes中
		Set<TreeBO> nodes = getDemoMenuDao().findNodes();
		
		//构造选择树
		for(TreeBO node : nodes) {
			DemoTreeBO_2 checkTreeBO = 
				new DemoTreeBO_2(node.getId(), node.getParentId(), node.getNodeValue(),
						node.getSortIndex(), false);
			this.add(checkTreeBO);
		}
	}

	public IDemoMenuDao getDemoMenuDao() {
		return demoMenuDao;
	}

	public void setDemoMenuDao(IDemoMenuDao demoMenuDao) {
		this.demoMenuDao = demoMenuDao;
	}
}
