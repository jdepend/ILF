package com.qeweb.demo;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Element;

import com.qeweb.framework.base.impl.XmlDaoImpl;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.utils.StringUtils;
import com.qeweb.framework.common.utils.XMLUtil;

/**
 * 首页菜单 dao impl, 通过查询demoMenu.xml文件构造菜单树
 */
public class DemoMenuDaoImpl extends XmlDaoImpl implements IDemoMenuDao {

	private static final long serialVersionUID = 6837757159237016894L;
	
	private final String NODE_MENU = "menu";
	private final String ATTR_PARENTID = "parentId";
	private final String ATTR_ID = "id";
	private final String ATTR_NAME = "name";
	private final String ATTR_PATH = "path";
	private final String ATTR_SORTINDEX = "sortIndex";
	

	@Override
	public Set<DemoMenuBO> findMenus() {
		List<Element> modules = getNodes();
		Set<DemoMenuBO> result = new HashSet<DemoMenuBO>();
		for(Element module : modules) {
			DemoMenuBO menu = new DemoMenuBO();
			menu.setId(StringUtils.convertLong(module.getAttributeValue(ATTR_ID)));
			menu.setNodeValue(module.getAttributeValue(ATTR_NAME));
			menu.setParentId(StringUtils.convertLong(module.getAttributeValue(ATTR_PARENTID)));
			menu.setPath(module.getAttributeValue(ATTR_PATH));
			menu.setSortIndex(StringUtils.convertToInt(module.getAttributeValue(ATTR_SORTINDEX)));
			result.add(menu);
		}
		
		return result;
	}
	
	@Override
	public Set<TreeBO> findNodes() {
		class MyTreeBO extends TreeBO {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6699665661602554402L;

			@Override
			public long getRootId() {
				return 0;
			}

			@Override
			public void create() {}
		}
		
		List<Element> nodes = getNodes();
		Set<TreeBO> result = new HashSet<TreeBO>();
		for(Element node : nodes) {
			TreeBO treeBO = new MyTreeBO();
			treeBO.setId(StringUtils.convertLong(node.getAttributeValue(ATTR_ID)));
			treeBO.setNodeValue(node.getAttributeValue(ATTR_NAME));
			treeBO.setParentId(StringUtils.convertLong(node.getAttributeValue(ATTR_PARENTID)));
			treeBO.setSortIndex(StringUtils.convertToInt(node.getAttributeValue(ATTR_SORTINDEX)));
			result.add(treeBO);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected List<Element> getNodes() {
		Element rootElement;
		try {
			rootElement = XMLUtil.getXmlRootElement(getClass(), "demoMenu.xml");
			if(rootElement != null) {
				return rootElement.getChildren(NODE_MENU);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
