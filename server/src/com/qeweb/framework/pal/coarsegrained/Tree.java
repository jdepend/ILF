package com.qeweb.framework.pal.coarsegrained;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qeweb.framework.bc.sysbo.CheckTreeBO;
import com.qeweb.framework.bc.sysbo.MenuBO;
import com.qeweb.framework.bc.sysbo.TreeBO;
import com.qeweb.framework.common.constant.ConstantParam;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.pl.ext.NodeComparator;
import com.qeweb.framework.pl.ext.coarsegrained.ExtContainerHelper;

/**
 * 树形结构组件. 树形结构组件也是一类粗粒度组件, 它绑定TreeBo
 */
public abstract class Tree extends Container {
	private Object data;

	@Override
	public void loadData(Object data) {
		this.data = data;
	}

    @Override
    public TreeBO getBc() {
        return (TreeBO) super.getBc();
    }

    final public String getTreePanelName() {
		return ExtContainerHelper.getContainerPanelName(this);
	}

    /**
     * 构造树形结构
     * @param tree
     * @return
     */
    public List<Map<String, Object>> createTree(Tree tree) {
    	TreeBO root;
    	//执行了上下文跳转操作，借由上一页面的参数构造data
    	if(this.data != null) {
    		root = (TreeBO) this.data;
    	}
    	else {
    		root = tree.getBc();
    		root.create();
    	}

		return createNodes(root.getRootId(), root.getTree());
    }

    /**
     * 创建节点
     * 将TreeBO中的所有节点存储为List结构，
     * @param id
     * @param nodes
     * @return
     */
    private List<Map<String, Object>> createNodes(Long id, List<TreeBO> nodes) {
		List<Map<String, Object>> nodeList = new LinkedList<Map<String, Object>>();
		for (TreeBO node : nodes) {
			if (node.getParentId() != id)
				continue;

			Map<String, Object> nodeMap = new HashMap<String, Object>();
			nodeMap.put("id", node.getId());
			nodeMap.put("text", node.getNodeValue());
			nodeMap.put("sort", node.getSortIndex());

			List<Map<String, Object>> children = createNodes(node.getId(), nodes);
			if ((node instanceof MenuBO) && ((MenuBO) node).getPath() != null) {
				nodeMap.put("path", ((MenuBO) node).getPath() + "?" + ConstantParam.MENU_PATH_PARAM);
			}
			else if ((node instanceof CheckTreeBO)){
				CheckTreeBO ctn = (CheckTreeBO) node;
				if((ContainerUtil.isNull(children) || ((CheckTreeBO)getBc()).isAllOptional()))
					nodeMap.put("checked", ctn.isChecked());
			}

			if (ContainerUtil.isNull(children))
				nodeMap.put("leaf", true);
			else
				nodeMap.put("children", children);
			nodeList.add(nodeMap);
		}

        Collections.sort(nodeList, new NodeComparator());

        return nodeList;
    }

    @Override
    public void free() {
    	data = null;

		super.free();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
