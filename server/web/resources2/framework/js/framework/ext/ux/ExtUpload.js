/**
 * 单文件上传，无表单上传文件
 * @param {} fileFieldId 需上传文件控件Id
 */
function ajaxFileUpload(fileFieldId, fileFieldName) {
	Ext.getBody().mask(I18N.FILE_UPLOADING, 'x-mask-loading');
	var sourceName = DISLANDTODOM.getBOBind(fileFieldName);
	var bopName = DISLANDTODOM.getBOPBind(fileFieldId);
	var params = {
		sourceName : sourceName,
		bopName : bopName
	};
	var fc = Ext.getCmp(DISLANDTODOM.getFileUploadFCId(fileFieldId));
	if(fc && fc.boIndex){
		ajaxFileUpload_Table(fileFieldId, fileFieldName, fc);
	}
	else{
		ajaxFileUpload_Form(fileFieldId, fileFieldName, fc);
	}
}

/**
 * 表格中的附件上传
 * @param fileFieldId
 * @param fileFieldName
 * @param fc
 */
function ajaxFileUpload_Table(fileFieldId, fileFieldName, fc) {
	var bop = DISLAND.getColumnByFcName(fileFieldName);
	var params = {
		sourceName : DISLANDTODOM.getBOBind(fileFieldName),
		bopName : DISLANDTODOM.getBOPBind(fileFieldId),
		operate : bop.attr(DISLAND.OPERATE) || ""
	};
	
	unlockScreen();

	var fileElementId = DISLANDTODOM.getFileID(fileFieldId);
	$.ajaxFileUpload({
		url : appConfig.ctx + actionURL.uploadFile(),
		secureuri : false,
		fileElementId : fileElementId,
		dataType : 'json',
		data : params,
		timeout : appConfig.ajaxTimeout,
		success : function(data, status) {
			var fileFiled = Ext.getCmp(fileFieldId);
			fileFiled.setValue("");
			var fileFiledMain = $('#' + fileElementId);
			fileFiledMain.bind('change', function(){
				fileFiled.setValue(fileFiledMain.val());
			});
			if(data.success){
				//更新数据岛
				if(data.filePath){
					//更新文件下载链接
					var anchor = Ext.getCmp(DISLANDTODOM.getFileAnchorID(fileFieldId));
					if(anchor && data.filePath){
						var anchorStr = "<a href='" + data.filePath + "' target='_blank' title='" + data.fileName + "'>" + data.fileName + "</a>";
						anchor.setText(anchorStr, false);
					}
					Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue(data.fileName);
				}
				var winId = DISLANDTODOM.getFileCellWin(fileFieldId);
				var win = Ext.getCmp(winId);
				win.returnData = data;
			}
			else {
				showMSG.showErr(data.message);
			}
			unlockScreen();
		},
		error : function(data, status, e) {
			alert('err')
			unlockScreen();
			showMSG.showErr( I18N.FILE_UPLOAD_FAILURE + I18N.FILE_ERROR_POSSIBILITY);
		}
	});
}

/**
 * 表单中的附件上传
 * @param fileFieldId
 * @param fileFieldName
 * @param fc
 */
function ajaxFileUpload_Form(fileFieldId, fileFieldName, fc) {
	var bop = DISLAND.getBOPByFcName(fileFieldName);
	var params = {
		sourceName : DISLANDTODOM.getBOBind(fileFieldName),
		bopName : DISLANDTODOM.getBOPBind(fileFieldId),
		operate : bop.attr(DISLAND.OPERATE) || "",
		lastPath : bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_URL) || ""
	};

	var fileElementId = DISLANDTODOM.getFileID(fileFieldId);
	$.ajaxFileUpload({
		url : appConfig.ctx + actionURL.uploadFile(),
		secureuri : false,
		fileElementId : DISLANDTODOM.getFileID(fileFieldId),
		dataType : 'json',
		data : params,
		timeout : appConfig.ajaxTimeout,
		success : function(data, status) {
			var fileFiled = Ext.getCmp(fileFieldId);
			fileFiled.setValue("");
			var fileFiledMain = $('#' + fileElementId);
			fileFiledMain.bind('change', function(){
				fileFiled.setValue(fileFiledMain.val());
			});
			if(data.success){
				//更新数据岛
				if(data.fileName){
					bop.children(DISLAND.BOP_VALUE).text(data.fileName);
				}
				if(data.filePath){
					bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_URL,data.filePath);
					//更新文件下载链接
					var anchor = Ext.getCmp(DISLANDTODOM.getFileAnchorID(fileFieldId));
					if(anchor && data.filePath){
						var anchorStr = "<a href='" + data.filePath + "' target='_blank' title='" + data.fileName + "'>" + data.fileName + "</a>";
						anchor.setText(anchorStr, false);
					}
					Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId)).setValue(data.fileName);
				}
				unlockScreen();
				showMSG.showOK(data.message);
			}
			else {
				showMSG.showErr(data.message);
			}
		},
		error : function(data, status, e) {
			Ext.getBody().unmask();
			showMSG.showErr( I18N.FILE_UPLOAD_FAILURE + I18N.FILE_ERROR_POSSIBILITY);
		}
	});	
}


/**
 * 批量文件上传
 * @param {} fileFieldId
 * @param {} fileFieldName
 */
function ajaxMultiFileUpload(fileFieldId, fileFieldName){
	var sourceName = DISLANDTODOM.getBOBind(fileFieldName);
	var bopName = DISLANDTODOM.getBOPBind(fileFieldId);
	var bop = DISLAND.getBOPByFcName(fileFieldName);
	var bopData = bop.children(DISLAND.BOP_DATA);
	var params = {
		sourceName : sourceName,
		bopName : bopName,
		isMulti : 'true',
		//目前已上传的文件数
		uploadNum : bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM),
		//目前已上传的各文件存储空间大小
		uploadSize : bopData.attr(DISLAND.BOP_DATA_UPLOAD_SIZE) || "",
		operate : bop.attr(DISLAND.OPERATE) || "",
		lastPath : bopData.attr(DISLAND.BOP_DATA_URL) || ""
	};

	var dialog = new Ext.ux.UploadDialog.Dialog({  
		url : appConfig.ctx + actionURL.uploadFile(),  
		reset_on_hide : false,  
		allow_close_on_upload : true,
		base_params : params,
		fileFieldId : fileFieldId,
		fileFieldName : fileFieldName
	});  
	dialog.show(Ext.getBody());
	dialog.on('uploadsuccess', onUploadSuccess);  
}  
  
/**
 * 批量上传时，单个文件上传成功后执行的方法
 * @param {} dialog
 * @param {} filename
 * @param {} resp_data
 * @param {} record
 */
function onUploadSuccess(dialog, filename, resp_data, record){
	var bop = DISLAND.getBOPByFcName(dialog.fileFieldName);
	//更新数据岛
	//更新已上传文件说明	
	var anchor = Ext.getCmp(dialog.fileFieldId);	
	//文件名
	if(resp_data.fileName){
		var bopValue = bop.children(DISLAND.BOP_VALUE);
		var value = getRemoveCDATA(bopValue.text()) + resp_data.fileName + DISLAND.SPLIT_TREBLE_BACKSLASH;
		bopValue.text(value);
	}
	if(resp_data.filePath && resp_data.uploadNum){		
		var bopData = bop.children(DISLAND.BOP_DATA);
		//文件路径
		var olaDataUrl = bopData.attr(DISLAND.BOP_DATA_URL) || "";
		var data_url = olaDataUrl + resp_data.filePath + DISLAND.SPLIT_TREBLE_BACKSLASH;
		bopData.attr(DISLAND.BOP_DATA_URL,data_url);
		
		//已上传文件数量
		dialog.base_params.uploadNum = resp_data.uploadNum;
		bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM,resp_data.uploadNum);
		
		var check = Ext.getCmp(DISLANDTODOM.getFileCheckID(dialog.fileFieldId));
		var anchorStr = "<a href='#' onclick='viewFileList(\""+dialog.fileFieldId+"\",\""+dialog.fileFieldName+"\");'>" + I18N.MULTI_FILE_UPLOAD + " " + resp_data.uploadNum + "</a>";
		anchor.setText(anchorStr, false);
		check.setValue(bop.children(DISLAND.BOP_VALUE).text());
	}
	//已上传文件存储空间总量
	if(resp_data.uploadSize){
		dialog.base_params.uploadSize = resp_data.uploadSize;
		bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_UPLOAD_SIZE,resp_data.uploadSize);
	}
} 

function viewFileList(fileFieldId, fileFieldName) {
	var bop = DISLAND.getBOPByFcName(fileFieldName);
	var value = getRemoveCDATA(bop.children(DISLAND.BOP_VALUE).text()).split(DISLAND.SPLIT_TREBLE_BACKSLASH);
	var data_url = bop.children(DISLAND.BOP_DATA).attr(DISLAND.BOP_DATA_URL).split(DISLAND.SPLIT_TREBLE_BACKSLASH);
	var table = new Array();
	for (var i = 0, len = value.length; i < len; i++) {
		var row = new Array();
		if (value[i] == "")
			continue;
		row.push(data_url[i]);
		row.push(value[i]+DISLAND.SPLIT_TREBLE_BACKSLASH+data_url[i]);
		table.push(row);
	}
	
	var store = new Ext.data.ArrayStore({
		fields : ['delete', 'file'],
		idIndex : 0
			// 每条记录的id将会是第一个元素
		});
	// 从数组里面装载数据
	store.loadData(table);
	
	var cm = new Ext.grid.ColumnModel([{
				header : I18N.MULTI_FILE_TITLE_1,
				width : 70,
				resizable : false,
				dataIndex : "delete",
				sortable : true,
				align : 'center',
				renderer : function(data, metadata, record, rowIndex, columnIndex, store){
					return '<a href="#" onclick="delFile(\'' + fileFieldId + "\',\'" + fileFieldName + "\',\'" + data + '\');">' + I18N.MULTI_FILE_DELETE + '</a>';
				}
			}, {
				header : I18N.MULTI_FILE_TITLE_2,
				dataIndex : "file",
				sortable : true,
				renderer : function(data, metadata, record, rowIndex, columnIndex, store){
					var array = data.split(DISLAND.SPLIT_TREBLE_BACKSLASH);
					return '<a href="' + array[1] + '" target="_blank">' + array[0] + '</a>';
				}
			}]);
			
	var grid = new Ext.grid.GridPanel({
				id : 'qeweb-multifile-list',
				ds : store,
				cm : cm,
				border : true,
				viewConfig : {
					autoFill : true,
					forceFit : true
				}
			});

	var win = new Ext.Window({
				maximizable : true,
				collapsible : true,
				width : 450,
				height : 300,
				isTopContainer : true,
				modal : true,
				resizable : false,
				layout : 'fit',
				constrain : true,
				buttons : [{
							text : I18N.DIALOG_CLOSE,
							iconCls : 'no',
							handler : function() {
								win.close();
							}
						}],
				items : [grid]
			});
	win.show(Ext.getBody());
}

/**
 * 删除文件
 * @param {} fileFieldId
 * @param {} fileFieldName
 * @param {} delNum
 */
function delFile(fileFieldId, fileFieldName, fielPath){
	var bop = DISLAND.getBOPByFcName(fileFieldName);
	var value = getRemoveCDATA(bop.children(DISLAND.BOP_VALUE).text()).split(DISLAND.SPLIT_TREBLE_BACKSLASH);
	var bopData = bop.children(DISLAND.BOP_DATA);
	var data_url = bopData.attr(DISLAND.BOP_DATA_URL).split(DISLAND.SPLIT_TREBLE_BACKSLASH);
	var uploadSize = bopData.attr(DISLAND.BOP_DATA_UPLOAD_SIZE).split(DISLAND.SPLIT_COMMA);
	var uploadNum = Number(bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM));
	var new_value = "", new_data_url = "", new_uploadSize = "";
	for(var i = 0, len = value.length; i < len; i++){
		if(data_url[i] != fielPath){
			new_value += value[i];
			new_data_url += data_url[i];
			new_uploadSize += uploadSize[i];
		}
		else{
			uploadNum--;
			var grid_store = Ext.getCmp('qeweb-multifile-list').getStore();
			var record = grid_store.getById(data_url[i]);
			grid_store.remove(record);
		}
		new_value += DISLAND.SPLIT_TREBLE_BACKSLASH;
		new_data_url += DISLAND.SPLIT_TREBLE_BACKSLASH;
		new_uploadSize += DISLAND.SPLIT_COMMA;		
	}
	bop.children(DISLAND.BOP_VALUE).text(new_value);
	bopData.attr(DISLAND.BOP_DATA_URL, new_data_url);
	bopData.attr(DISLAND.BOP_DATA_UPLOAD_NUM, uploadNum);
	bopData.attr(DISLAND.BOP_DATA_UPLOAD_SIZE, new_uploadSize);
	var anchor = Ext.getCmp(fileFieldId);
	var anchorStr;
	if(uploadNum > 0)
	 	anchorStr = "<a href='#' onclick='viewFileList(\""+fileFieldId+"\",\""+fileFieldName+"\");'>" + I18N.MULTI_FILE_UPLOAD + " " + uploadNum + "</a>";
	else
		anchorStr = I18N.MULTI_FILE_UPLOAD + " " + uploadNum;
	var check = Ext.getCmp(DISLANDTODOM.getFileCheckID(fileFieldId));
	anchor.setText(anchorStr, false);
	check.setValue(new_value);
}