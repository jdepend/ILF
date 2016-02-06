package com.qeweb.framework.app.action;

import java.util.HashMap;
import java.util.Map;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.MsgService;

/**
 * 将topMenu的节点信息保存至MsgService
 *
 */
public class SaveTopMenuNode extends BaseAction {
	
	private static final long serialVersionUID = -6194818697395010382L;
	
	private String nodeId;
	private String nodeName;
	
	@Override
	public String execute() {
		Map<String, String> nodeMsg = new HashMap<String, String>();
		nodeMsg.put("id", getNodeId());
		nodeMsg.put("name", getNodeName());
		MsgService.setMsg(Constant.TOPMENU_NODE, nodeMsg);
		
		return null;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
