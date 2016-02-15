var IMG_FILE_NAME_FIELD;
//定位保存对应的属性名
var LOCATION_FIELD;
var BARCODE_FIELD;//条码对应的属性名


//--------------------------- 获取基站信息 start ---------------------------
/** 
 * 获取基站信息 使用观察者模式, 获得基站信息后自动更新数据岛的相关内容
 */
function getCellId() {
	if(!isExistWinQeweb())
		return;
	var lbs = new LBSSubject();
	lbs.addListener(updateLBSDataIsland);
	lbs.notify();
}

function LBSSubject() {
	var reg =  /[^\d,]/g;
	this.cellid = window.qeweb.getLBS().replace(reg, "");
	this.undefinedLocation = "0,0,0,0";
	this.array = [];
}

LBSSubject.prototype.addListener = function(listener) {
	this.array[listener] = listener;
}

LBSSubject.prototype.notify = function() {
	for (i in this.array) {
		this.array[i].call(this);
	}
}

/**
 * 更新数据岛中基站信息的相关内容
 */
function updateLBSDataIsland() {
	if(this.cellid == this.undefinedLocation) 
		this.cellid = null;
	
	updateMobileDIsland(LOCATION_FIELD, this.cellid);
}
//--------------------------- 获取基站信息 end ---------------------------


//--------------------------- 获取GPS信息 start ---------------------------
/** 
 * 获取GPS信息 使用观察者模式, 获得GPS信息后自动更新数据岛的相关内容
 */
function getGPS() {
	if(!isExistWinQeweb())
		return;
	var gps = new GPSSubject();
	gps.addListener(updateGPSDataIsland);
	gps.notify();
}

function GPSSubject() {
	this.gps = window.qeweb.getGPS();
	window.qeweb.closeGPS();
	this.array = [];
}

GPSSubject.prototype.addListener = function(listener) {
	this.array[listener] = listener;
}

GPSSubject.prototype.notify = function() {
	for (i in this.array) {
		this.array[i].call(this);
	}
}

/**
 * 更新数据岛中GPS信息的相关内容
 */
function updateGPSDataIsland() {
	if(isNotEmpty(this.gps))
		updateMobileDIsland(LOCATION_FIELD, this.gps);
}
//--------------------------- 获取GPS信息 end ---------------------------


//--------------------------- 手机拍照start ---------------------------

/** 
 * 手机拍照, 使用观察者模式, 获得fileName后自动更新数据岛的相关内容
 */
function camera() { 
	if(!isExistWinQeweb())
		return;
	var cam = new cameraSubject();
	cam.addListener(updatecameraDataIsland);
	cam.notify();
}

function cameraSubject() {
	this.fileName = new Date().getTime() + ".jpg";
	this.array = [];
	document.location.href = "http://carema/" + this.fileName;
}

cameraSubject.prototype.addListener = function(listener) {
	this.array[listener] = listener;
}

cameraSubject.prototype.notify = function() {
	for (i in this.array) {
		this.array[i].call(this);
	}
}

/**
 * 更新数据岛中GPS信息的相关内容
 */
function updatecameraDataIsland() {
	updateMobileDIsland(IMG_FILE_NAME_FIELD, this.fileName);
}

//--------------------------- 手机拍照end ---------------------------

//手机端上传图片，支持断点.调用手机端上传图片进程
function uploadPic() {
	if(!isExistWinQeweb() || isEmpty(IMG_FILE_NAME_FIELD))
		return;
	
	//文件名
	var uploadFileName = getDataIslandByJQ().find("bop[bind='" + IMG_FILE_NAME_FIELD + "']").children("value").text();
	uploadFileName = uploadFileName.replace("<![CDATA[", "");
	uploadFileName = uploadFileName.replace("]]>", "");
	window.qeweb.setUploadPictureName(uploadFileName);
}

/**
 * 在执行拍照时获取基站信息和GPS信息.
 * 由于获取GPS信息需要一段时间, 且GPS可能被关闭, 而基站信息马上可以获取,
 * 所以在获取定位信息时同时获取二者, 当用户执行提交操作而未成功获取GPS信息时,
 * 使用基站信息定位.如果成功获取了GPS信息,则使用GPS定位.
 */
function cameraAndLocation(){
	if(!isExistWinQeweb())
		return;
	
	camera();
	getCellId();
	window.qeweb.startGPS();
}


/**
 * 执行获取GPS/Lbs/拍照动作后,将更新数据岛
 * @param bind
 * @param value
 */
function updateMobileDIsland(bind, value) {
	var fcHandler = FCHandlerFactory.createFCHandler(getDataIslandByJQ().find("bop[bind='"+ bind +"']"));
	fcHandler.optionDom.val(value);
}

function isExistWinQeweb() {
	if(typeof(window.qeweb) == 'undefined') {
		alert("非手机端环境不能执行手机端特有功能！");
		return false;
	}
	else {
		return true;
	}
}

//-----------------------手机地图---------------------
/**
 * 获取google地图
 * @param title		标记地点的名称
 * @param latitude	标记地点的经度
 * @param longitude	标记地点的纬度
 * 
 */
function getMap(title,latitude,longitude) {
	if(isMobile()){
		var url = appConfig.ctx + actionURL.getMap() + '?title=' + encodeURI(encodeURI(title)) + 
			"&latitude=" + latitude +
			"&longitude=" + longitude;
		window.open (url, title,
			'height=400, width=400, top=50, left=100, ' +
			'toolbar=no, menubar=no, scrollbars=no, ' +
			'resizable=yes, location=no, status=no, ' +
			'depended=yes, alwaysRaised=yes',false);
	}
	else 
		window.qeweb.getGoogleMap(title,latitude,longitude);
}

//-----------------------条码扫描---------------------
/**
 * 条码扫描
 */
function barcodeScan() {
	if(!isExistWinQeweb())
		return;
	//启动条码扫描
	window.qeweb.startCode();
}

/**
 * 条码扫描回调函数
 * @param data	获得的条码
 */
function liSetCode(data){
	var fcHandler = FCHandlerFactory.createFCHandler(getDataIslandByJQ().find("bop[bind='"+ BARCODE_FIELD +"']"));
	fcHandler.optionDom.val(data);
}

//--------------------- 手机端离线提交-------------
/**
 * @param dataStr  Ga 使用的参数，包括数据岛、页面流相关信息等
 * @param btnEvent.unDisable()
 */
function mobileSubmit(dataStr, unDisable) {
	alert('abc')
	//window.qeweb如果存在，说明客户端是手机或pad终端，提交保存使用window.qeweb.saveUnsubmitData(data)
	//上传拍照图片
	uploadPic();
	//获得GPS信息
	getGPS();
	window.qeweb.saveUnsubmitData(dataStr);
	unDisable();
}

function isMobile() {
	return typeof(window.qeweb) != 'undefined';
}
