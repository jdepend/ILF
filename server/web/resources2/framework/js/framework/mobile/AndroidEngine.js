
var ANDROID_ENGINE = {
	//照片文件名
	picName : "",
	//条码扫描按钮处理者
	barcodeBtnHandler : "",

	/**
	 * 是否是终端环境
	 */
	isTerminalEvnir : function() {
		if(typeof(window.qeweb) == 'undefined') {
			alert("非手机端环境不能执行手机端特有功能！");
			return false;
		}
		else {
			return true;
		}
	},

	/**
	 * 手机拍照
	 */
	camera : function() {
		if(!this.isTerminalEvnir())
			return;

		this.picName = new Date().getTime() + ".jpg";
		window.qeweb.camera(this.picName);
	},

	/**
	 * 获取GPS信息
	 */
	getGPS : function() {
		if (!this.isTerminalEvnir())
			return;

		window.qeweb.startGPS();
		var gps;
		if(appConfig.isBaiduMap)
			gps = window.qeweb.getBaiduGPS();
		else
			gps = window.qeweb.getGPS();
		window.qeweb.closeGPS();
		if (!!gps && gps != 'null'){
			return gps;
		}
		return null;
	},

	/**
	 * 语音识别
	 * @param {} textName
	*/
	getRecognizer : function(soundRecognizHandler) {
		if (!this.isTerminalEvnir())
			return;

		window.qeweb.getRecognizer();
	},

	/**
	 * 在执行拍照时获取基站信息和GPS信息.
	 * 由于获取GPS信息需要一段时间, 且GPS可能被关闭, 而基站信息马上可以获取,
	 * 所以在获取定位信息时同时获取二者, 当用户执行提交操作而未成功获取GPS信息时,
	 * 使用基站信息定位.如果成功获取了GPS信息,则使用GPS定位.
	 */
	cameraAndLocation : function(cameraBtnHandler) {
		if(!this.isTerminalEvnir())
			return;

		this.camera();
		for(var i = 0, length = cameraBtnHandler.groupFCHandlers.length; i < length; i++){
			var fcHandler = cameraBtnHandler.groupFCHandlers[i];
			var bopDom = fcHandler.bopDom;
			//设置对应的基站、GPS数据至关联bop
			if(bopDom.attr(DISLAND.BOP_TERMINAL_LOCATION)){
				var gps = this.getGPS();
				if(!!gps){
					fcHandler.setValue(gps);
				}
			}
			//设置照片文件的值
			else if(bopDom.attr(DISLAND.BOP_TERMINAL_PIC)){
				fcHandler.setValue(this.picName);
			}
		}
	},

	/**
	 * 条码扫描
	 */
	barcodeScan : function(barcodeBtnHandler) {
		if(!this.isTerminalEvnir())
			return;

		this.barcodeBtnHandler = barcodeBtnHandler;
		//启动条码扫描
		window.qeweb.startCode();
	},

	/**
	 * 手机端上传图片，支持断点.调用手机端上传图片进程
	 */
	uploadPic : function() {
		if(!!this.picName) {
			window.qeweb.setUploadPictureName(this.picName);
			this.picName = "";
		}
	},

	/**
	 * 手机端离线提交, 所有保存操作均调用该方法
	 * @param dataStr  Ga 使用的参数，包括数据岛、页面流相关信息等
	 */
	mobileSubmit : function(dataStr) {
		//上传拍照图片
		this.uploadPic();
		//获得GPS信息
//		getGPS();
		window.qeweb.saveUnsubmitData(dataStr);
	}
};


/**
 * 条码扫描回调函数
 * @param data	获得的条码
 */
function liSetCode(data){
	for(var i = 0, length = ANDROID_ENGINE.barcodeBtnHandler.groupFCHandlers.length; i < length; i++){
		var fcHandler = ANDROID_ENGINE.barcodeBtnHandler.groupFCHandlers[i];
		fcHandler.setValue(data);
	}
}

/**
 * 语音识别的回调函数
 * @returns {Boolean}
 */
function liListRecognizer(data) {

}


/**
 * 获取google地图
 * @param title		标记地点的名称
 * @param latitude	标记地点的经度
 * @param longitude	标记地点的纬度
 *
 */
function getMap(title,latitude,longitude) {
//	if(isMobile()){
//		var url = appConfig.ctx + actionURL.getMap() + '?title=' + encodeURI(encodeURI(title)) +
//			"&latitude=" + latitude +
//			"&longitude=" + longitude;
//		window.open (url, title,
//			'height=400, width=400, top=50, left=100, ' +
//			'toolbar=no, menubar=no, scrollbars=no, ' +
//			'resizable=yes, location=no, status=no, ' +
//			'depended=yes, alwaysRaised=yes',false);
//	}
//	else
//		window.qeweb.getGoogleMap(title,latitude,longitude);
}

