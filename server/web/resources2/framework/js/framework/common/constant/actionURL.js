
/**
 * 平台action路径
 * @returns {actionURL}
 */
var actionURL = {
	getSecurityURL : function(url) {
		var disland = getDataIslandByJQ();
		return url + "?timestemp=" + new Date().getTime()
			+ "&sessionTicket=" + disland.attr(DISLAND.SESSIONTICKET)
			+ "&tokenTicket=" + disland.attr(DISLAND.TOKEN_TICKET);
	},

	//如果窜session，跳转到登录后的第一个页面
	getRelogin : function() {
		return "/system/login.action";
	},
	
	//细粒度组件关联（细细）提交url
	getFcToBopSubmit : function() {
        //扩展 eric  2016  spring mvc
        //this.getSecurityURL("/system/finegrainedSubmitToBopAC.action");
		return this.getSecurityURL("/vsr/boprelation");
	},
	
	//细粒度组件关联（细粗）提交url
	getFcToBoSubmit : function() {
		return this.getSecurityURL("/system/finegrainedSubmitToBoAC.action");
	},

	//细粒度组件服务器端校验url
	getFcServerValidate : function() {
		return this.getSecurityURL("/system/serverValidate.action");
	},

	//粗粒度组件关联
	getBoRelation : function() {
		return this.getSecurityURL("/system/boRelationAC.action");
	},

	//GA根据id查找唯一记录操作
	getGaRecord : function() {
		return this.getSecurityURL("/system/generalRecordAC.action");
	},

	//GA查询url
	getGaSearch : function() {
		return this.getSecurityURL("/system/generalSearchAC.action");
	},
	//GA持久化url
	getGaExe : function() {
		return this.getSecurityURL("/system/generalExeAC.action");
	},

	//页面跳转
	getRedirect : function(target) {
		if(!!target)
			return this.getSecurityURL("/system/generalredirectAC.action?redirectStr=" + target);
		else
			return "/system/generalredirectAC.action?redirectStr=";
	},

	//设置上下文跳转的参数
	saveParam : function() {
		return this.getSecurityURL("/system/generalSaveParamAC.action");
	},

	//文件上传
	uploadFile : function() {
		return this.getSecurityURL("/system/fileUploadAC.action");
	},
	
	//富文本编辑器中上传图片
	uploadImg : function() {
		return this.getSecurityURL("/system/imgUploadAC.action");
	},
	
	//保存查询条件
	saveCase : function() {
		return this.getSecurityURL("/system/saveCaseAC!saveCase.action");
	},
	
	//查找查询条件
	findQueryCase : function() {
		return this.getSecurityURL("/system/saveCaseAC!findQueryCase.action");
	},
	
	//保存表格列的隐藏/显示
	saveHiddenChange : function() {
		return this.getSecurityURL("/system/tableSettingAC!saveHiddenChange.action");
	},
	
	//当某一列位置改变时记忆该列位置
	saveColMoved : function() {
		return this.getSecurityURL("/system/tableSettingAC!saveColMoved.action");
	},
	
	//当某一列宽度改变时记忆该列宽度
	saveWidthChanged : function() {
		return this.getSecurityURL("/system/tableSettingAC!saveWidthChanged.action");
	},
	
	//删除查询条件
	delCase : function() {
		return this.getSecurityURL("/system/saveCaseAC!delCase.action");
	},
	
	//html地图
	getMap : function() {
		return "/framework/common/map/gmap.jsp";
	},
	
	//导出
	doExport : function() {
		return this.getSecurityURL("/system/export.action");
	},
	
	//设置不再显示弹出式成功提示信息
	setPopupStatus : function(){
		return this.getSecurityURL("/system/setPopupStatus.action");
	},
	
	//设置不再显示操作提示信息
	setConfirmStatus : function(){
		return this.getSecurityURL("/system/setConfirmStatus.action");
	},
	
	//GA执行指定方法重载指定组件
	reloadTargetVC : function() {
		return this.getSecurityURL("/system/reloadTargetVC.action");
	},
	
	//刷新父页面全局按钮
	reloadPageBtn : function() {
		return this.getSecurityURL("/system/reloadPageBtn.action");
	},
	
	//将topMenu的节点信息保存至MsgService
	saveTopMenuNode : function() {
		return this.getSecurityURL("/system/saveTopMenuNode.action");
	}
};