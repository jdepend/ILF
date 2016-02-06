package com.qeweb.framework.app.pageflow;


/**
 * 页面流的通用接口, 所有涉及到页面流程的操作(即除超连接外的逻辑控制组件触发的操作)皆由GA处理,
 * 也就是说所有涉及到页面流程的操作不再需要单独写action代码.
 * <br>
 * 流程操作: 查询操作,翻页操作,增/删/改等持久化操作,通过逻辑控制组件触发的页面跳转操作,执行这些操作后可能会跳转到下个页面或更新本页的数据.
 * <br>
 * 需要注意的是单纯的超连接(即由<qeweb:anchor>标签指定的超链接,这类超链接可以跳转到herf指定的任意页面)不需要GA处理.
 */
public interface IGenerationAction {

	/**
     * 执行持久化操作
     * @return null
     * @throws Exception
     */
    String execute() throws Exception ;
    
    /**
	 * 根据指定的方法(targetVC.method)刷新指定组件(targetVC), 待刷新的组件由页面流配置标签中的targetVC属性指定.
	 * <p>
	 * <ul>例: 假设form1 bind bo1, form2 bind bo2, table1 bind bo3
	 * <li>1. 如果 targetVC=form1.m1,form2.m2,table1.m3, 则按先后顺序执行bo1.m1, bo2.m2, bo3.m3方法, 并根据结果按顺序刷新 form1,form2,table1;
	 * <li>2. 如果targetVC=form1.m1,table1, 则按先后顺序执行bo1.m1, bo3.query方法, 并根据结果按顺序刷新 form1,table1.
	 */
	void reloadTargetVC();
	
	/**
	 * 根据页面流配置标签中的targetVC属性, 刷新全局按钮.
	 * <p>
	 * <ul>例: 假设page bind bo
	 * <li>1.targetVC=page, 则执行bo.query后刷新全局按钮;
	 * <li>2.targetVC=page.m, 则执行bo.m后刷新全局按钮.
	 */
	void reloadPageBtn();
	
    /**
     * 执行查询操作.将关联也看作一种查询
     * <br>
     * 由源BO查询关联的BO,在jsp页面上,源和目标由<qeweb:group>标签指定
     * @throws Exception
     */
    void search() throws Exception;
    
    /**
	 * 控制页面跳转<br>
	 * <b>描述</b>：与直接跳转页面有所区别，该方法是带有数据岛的页面跳转
	 * <br>
	 * 跳转到目标页面后,可能需要执行目标页Bo的方法,该方法可能带有参数.
	 * <br>
	 * 跳转时将页面的数据岛传至目标页面,目标页面的Bo将接收参数,将数据岛转换为相应的bo或bolist.
	 * @return
	 */
    String redirect();
    
    /**
	 * 根据recordId获取唯一记录, 在table弹出框的修改和查看中使用
	 * @return
	 */
	void findRecord();
	
	/**
	 * 设置上下文跳转的参数
	 */
	void saveParam();
	
	/**
	 * 文件导出
	 * 如果按钮绑定的方法以exp开头(expExl, expDoc等), 则认为该方法是导出方法, 将执行的export()操作.
	 * export()将不流经拦截器.
	 * @return
	 * @throws Exception
	 */
	String export();
}
