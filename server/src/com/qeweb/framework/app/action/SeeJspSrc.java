package com.qeweb.framework.app.action;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.qeweb.framework.base.ac.BaseAction;
import com.qeweb.framework.common.Constant;
import com.qeweb.framework.common.Envir;

/**
 * 查看JSP源码
 *
 */
public class SeeJspSrc extends BaseAction {
	private static final long serialVersionUID = 8260396003646619285L;

	/**
	 * 显示jsp源码
	 * @param filename
	 */
	public void showRes(String filename) {
		try {
			PrintWriter writer = Envir.getResponse().getWriter();
			Envir.getResponse().setContentType(Constant.CONTENTTYPE);
			Envir.getResponse().setHeader("Content-Disposition", "inline");
			InputStreamReader reader = null;
			reader = new InputStreamReader(Envir.getContext().getResourceAsStream("/WEB-INF/pages/demo/" + filename + ".jsp"), "UTF-8");
			if (reader != null){
				char[] buffer = new char[1024];
				int charRead = 0;
				writer.write("<html><body>源码如下：<br><textarea readonly border=0 cols='150' rows='30'>");
				while ((charRead = reader.read(buffer)) != -1)
					writer.write(buffer, 0, charRead);
				writer.write("</textarea></body></html>");
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			//LOG.error(e.toString(), e);
		}
	}
	
	public void layoutExample() {
		showRes("layoutExample");
	}

	public void bopValidate() {
		showRes("bopValidate");
	}

	public void dataIsland_bopRelation() {
		showRes("dataIsland_bopRelation");
	}
	
	public void btnOperation(){
		showRes("btn/btnOperation");
	}
	
	public void localization() {
		showRes("localization");
	}

	public void showAllFComponent() {
		showRes("showAllFComponent");
	}

	public void styleExample() {
		showRes("styleExample");
	}
	
	public void styleExample2() {
		showRes("styleExample2");
	}

	public void styleExample3() {
		showRes("styleExample3");
	}

	public void table() {
		showRes("table");
	}
	
	public void checkTree() {
		showRes("checkTree");
	}
	
	public void tree() {
		showRes("tree");
	}
	
	public void menu() {
		showRes("menu");
	}
	
	public void purchaseOrderList() {
		showRes("purchaseOrderList");
	}
	
	public void contextRelation() {
		showRes("contextRelation/contextRelation");
	}
	
	public void contextRelation1() {
		showRes("contextRelation/contextRelation1");
	}
	
	public void contextRelation2() {
		showRes("contextRelation/contextRelation2");
	}
	
	public void contextRelation3() {
		showRes("contextRelation/contextRelation3");
	}
	
	public void purcharseOrderDetailsList() {
		showRes("contextRelation/purcharseOrderDetailsList");
	}
	
	public void purcharseOrderDetailsList3() {
		showRes("contextRelation/purcharseOrderDetailsList3");
	}
	
}
