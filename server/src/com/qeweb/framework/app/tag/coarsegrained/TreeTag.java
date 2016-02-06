package com.qeweb.framework.app.tag.coarsegrained;

import com.qeweb.framework.manager.AppManager;
import com.qeweb.framework.pal.coarsegrained.Container;
import com.qeweb.framework.pal.coarsegrained.Tree;

public class TreeTag extends ContainerTag
{
	private static final long serialVersionUID = 8300618739336096983L;

	@Override
	protected Container getContainerInstance() {
		return (Container)AppManager.createVC(Tree.class);
	}
}
