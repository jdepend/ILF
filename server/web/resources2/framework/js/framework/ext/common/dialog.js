var dialog = {
	/**
	 * 弹出模态对话框 供<qeweb:commandButton>和pageFlow使用
	 * @param pageFlowEvent
	 * @param paramData	数据岛信息
	 */
	openMDialog : function(pageFlowEvent, paramData) {
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.saveParam(),
			method : "POST",
			params : paramData,
			timeout : appConfig.ajaxTimeout,
			
			success : function(response, options) {
				var win = new Ext.Window({
					title : pageFlowEvent.dialogTitle,
					dialogType : 'M',
					id : "qeweb-dialog",
					maximizable : true,
					collapsible : true,
					iconCls : pageFlowEvent.dialogIcon,
					width : getAdaptiveSize(Number(pageFlowEvent.dialogWidth || dialog.getDefWidth())),
					height : getAdaptiveSize(Number(pageFlowEvent.dialogHeight || dialog.getDefHeight())),
					modal : true,
					html : "<iframe src='"
							+ appConfig.ctx + actionURL.getRedirect(pageFlowEvent.path)
							+ "' style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>",
					listeners : {
						'close' : function() {
							//关闭页面后刷新closeTargetVC指定的控件
							if(!!pageFlowEvent.closeTargetVC)
								pageFlowEvent.refreshParentContainer(pageFlowEvent.closeTargetVC);
						}
					} 
				});
				
				//是否添加关闭按钮
				if(pageFlowEvent.hasCloseBtn) {
					win.addButton({
						text : I18N.DIALOG_CLOSE,
						iconCls : 'noIcon',
						height : 25,
						handler : function() {
							win.close();
						}
					});
				}
				win.show(Ext.getBody());
			}
		});
	},

	/**
	 * 弹出模态对话框 <qeweb:source> 使用
	 * @param paramData 存储了弹出框所需的参数信息, 详见ExtSourceBtn.java
	 */
	openDialog : function(paramData) {
		if (!paramData.path)
			return false;

		var boId = paramData.sbopIds.split(DISLAND.SPLIT_LINE)[0];
		var boBind = paramData.sbopIds.split(DISLAND.SPLIT_LINE)[1];
		var bo;
		var dIsland;

		//表格弹出框中的sourceBtn
		if(paramData.sysOperate) {
			bo = DISLAND.getFormPanelDom(boId + DISLAND.SPLIT_LINE + boBind + DISLAND.SPLIT_LINE + paramData.sysOperate);
			BINDDATA.bindBO(bo, paramData.sysOperate, paramData.sysOperate);
			dIsland = xmlToString(bo);
		}
		//细粒度组件中的sourceBtn
		else if(!paramData.insideBtn) {
			bo = DISLAND.getBODom(boId);
			BINDDATA.bindBO(bo, "sysOperate", paramData.sysOperate);
			dIsland = xmlToString(bo);
		}
		//表格级按钮中的sourceBtn, 通常用于表格弹出回填, 将整个页面的数据传递至后台 
		else {
			bo = DISLAND.getBOListDom(boId);
			//同步表单的数据
			DISLAND.getDataIsland().children(DISLAND.BO).each(function() {
				BINDDATA.bindBO($(this));
			});
			DISLAND.setPageOptStatus();
			dIsland = xmlToString(DISLAND.getDataIsland());
		}

		var dialogParams = pageFlow.sourceName + paramValue(DISLANDTODOM.getBoName(bo))
				+ param(GA.dataIsland) + paramValue(dIsland);

		this.handle(dialogParams, paramData);
	},

	/**
	 * 点击<qeweb:source>后触发的操作:
	 * 例:
	 * 如果有标签:
     *  <qeweb:table bind='bo'>
     *  	<qeweb:commandButton name='btn1'>
     *  		<qeweb:source bindBo="bo2" bindBop='...' operate='m1,bo2.m2'/>
     *  	</qeweb:commandButton>
     *  	...
     *  </qeweb:table>;
     *  
	 * 先执行m1, 如果m1执行成功, 则弹出选择页面, 否则不弹出选择页面.
	 * 
	 * @param dialogParams 传递至后台的参数, 
	 * 		包括: 1.sourceName,2.数据岛
	 * @param paramData 存储了弹出框所需的参数信息, 详见ExtSourceBtn.java
	 * 
	 */
	handle : function(dialogParams, paramData) {
		lockSrceen();
		//执行按钮所在BO的方法(跳转前执行) 
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.getGaExe(),
			method : "POST",
			params : dialogParams + param(GA.operation) + paramData.operate,
			timeout : appConfig.ajaxTimeout,

			success : function(response, options) {
				unlockScreen();
				//如果窜session，跳转到登录后的第一个页面
				validateSession(response.responseText);

				//后续操作
				var resultMsg = new ResultMsg(response.responseText);
				//操作成功, 弹出页面
				if(resultMsg.success) {
					dialog.open(dialogParams, paramData);
				}
				//操作失败, 打印错误提示
				else {
					if(!resultMsg.msg || resultMsg.msg == 'null')
						resultMsg.msg = I18N.MSG_OPERATION_FAILURE;
					
					ResultMessage.showMsg(resultMsg.success, resultMsg.msg, true, false);
				}
			},
			failure : function(response) {
				unlockScreen();
				showMSG.showErr(I18N.MSG_AJAX_FAILURE);
			}
		});
	},
	
	/**
	 * 弹出选择页面
	 */
	open : function(dialogParams, paramData) {
		//弹出选择页面
		if(paramData.operate)
			dialogParams += param(pageFlow.contextOperate) + paramData.operate;
		Ext.Ajax.request({
			url : appConfig.ctx + actionURL.saveParam(),
			method : "POST",
			params : dialogParams,
			timeout : appConfig.ajaxTimeout,

			success : function(response, options) {
				var win = new Ext.Window({
					sbopIds : paramData.sbopIds,
					tbopIds : paramData.tbopIds,
					sysOperate : paramData.sysOperate,
					title : paramData.title,
					echo : paramData.echo,
					maximizable : true,
					collapsible : true,
					dialogType : paramData.dialogType,
					id : "qeweb-dialog",
					width : getAdaptiveSize(Number(paramData.width || dialog.getDefWidth())),
					height : getAdaptiveSize(Number(paramData.height || dialog.getDefHeight())),
					modal : true,
					html : "<iframe src='"
							+ appConfig.ctx + actionURL.getRedirect(paramData.path)
							+ param(sourceBtn.sm) + paramData.sm
							+ "' style='scrollbar:true;' height='100%' width='100%' frameborder='0'></iframe>"
				});
				
				//是否带有关闭按钮
				if(paramData.hasCloseBtn) {
					win.addButton({
						text : I18N.DIALOG_CLOSE,
						iconCls : 'noIcon',
						handler : function() {
							win.close();
						}
					});
				}
				
				win.show(Ext.getBody());
			},

			failure : function() {
				showMSG.showErr(I18N.MSG_AJAX_FAILURE);
			}
		});
	},
	
	
	/**
	 * 回填操作, 弹出框回填细粒度组件
	 * @param sbopIds 待填充的bopId, 以 "," 分隔
	 * @param tbopIds 目标页面对应的bopId, 以 "," 分隔 
	 * @param boListDisland  从弹出框返回的数据岛信息
	 * @param sysOperate  sourceBtn所在的系统弹出框类型(新增弹出框/修改弹出框)
	 */
	fillbackFC : function(sbopIds, tbopIds, boListDisland, sysOperate) {
		var boList = XMLDomFactory.getInstance(boListDisland).find(DISLAND.BOLIST);
		var bos = boList.children(DISLAND.BO);
		// 没有选中任何记录或直接关闭对话框
		if (!bos || bos.length == 0)
			return;

		var arrS = strToArray(sbopIds, DISLAND.SPLIT_COMMA);
		var arrT = strToArray(tbopIds, DISLAND.SPLIT_COMMA);
		var cmp = null;
		//将源页面对应组件的值清空
		for (var i = 0, length = arrS.length; i < length; i++) {
			cmp = dialog.getWinCmp(arrS[i], sysOperate, true);
			if(!cmp)
				continue;
			else if(isLabel(cmp))
				dialog.getWinCmp(arrS[i], sysOperate, true).setText("");
			else 
				dialog.getWinCmp(arrS[i], sysOperate, true).setValue("");
		}
		//回填元页面组件
		bos.each(function() {
			var cmp = null;
			for (var i = 0; i < arrT.length; i++) {
				var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + DISLANDTODOM.getBOPBind(arrT[i]) + "']");
				if (!bop)
					continue;

				var result = null;
				cmp = dialog.getWinCmp(arrS[i], sysOperate, true);
				if(cmp) {
					result = isLabel(cmp) ? cmp.text : cmp.getValue();
				}
				
				if (!!result)
					result += (DISLAND.SPLIT_COMMA + getXmlNoteText(bop));
				else
					result = getXmlNoteText(bop);
				
				dialog.setCmpValue(cmp, result);
			}
		});
		
		var cmp = dialog.getWinCmp(arrS[0], sysOperate, true);
		if(cmp && !isLabel(cmp))
			cmp.focus(true, true);
	},
	
	/**
	 * 回填操作(弹出页面回填表格)
	 * @param sbopIds 待填充的bopId, 以 "," 分隔
	 * @param tbopIds 目标页面对应的bopId, 以 "," 分隔 
	 * @param boListDisland  从弹出框返回的数据岛信息
	 * @param echo 源页面的重复者判断列, 仅对表格的弹出回填有效. 回填值以echo指定的bop为准, 判断是否重复, 如果重复则不填入源组件
	 */
	fillbackTable : function(sbopIds, tbopIds, boListDisland, echo) {
		var arrS = strToArray(sbopIds, DISLAND.SPLIT_COMMA);
		var arrT = strToArray(tbopIds, DISLAND.SPLIT_COMMA);
		if(!arrS || !arrT || arrS.length != arrT.length)
			return;
		
		//从弹出页面返回的boList
		var tboListDisland = XMLDomFactory.getInstance(boListDisland);
		var tboList = tboListDisland.find(DISLAND.BOLIST);
		
		// 没有选中任何记录或直接关闭对话框
		if (tboList.children().length == 0)
			return;
		
		var tboLst = XMLDomFactory.getInstance(boListDisland).find(DISLAND.BOLIST);
		var tbos = tboLst.children();
		//返回的数据岛中的bopBind和源页面的bopBind一一对应, 此处将boListDisland中的bopBind全部替换为源页面的bopBind
		//注: 此处不能直接使用 boListDisland.replaceAll 替换——当arrT[i] 是 arrT[j]的子串时将产生错误
		tbos.each(function() {
			$(this).children(DISLAND.BOP).each(function() {
				for(var i = 0, length = arrT.length; i < length; i++) {
					if(DISLANDTODOM.getBOPBind(arrT[i]) == $(this).attr(DISLAND.BIND)) {
						$(this).attr(DISLAND.BIND, DISLANDTODOM.getBOPBind(arrS[i]));
						return true;
					}
				}
				//如果弹出页面的bop在源页面没有对应, 则将其删除
				$(this).remove();
			});
		});
		
		//源页面表格对应的boList
		var sboList = parent.DISLAND.getDataIsland().find(DISLAND.BOLIST + "[" + DISLAND.BO_ID + "='" + DISLANDTODOM.getBOId(arrS[0]) + "']");
		
		//添加tbos中缺失的bop
		this._appendMissingBop(sboList, tbos);
		//添加行级按钮数据岛,以使行级按钮生效;为返回的数据岛tbos构造序号(index).
		this._addOptDoms(sboList, tbos);
		//将返回的数据岛拼接到源页面数据岛
		this._appendToSBoList(sboList, tbos, echo);

		var containerHandler = parent.ContainerHandlerFactory.createContainerHandler(sboList);
		containerHandler.fresh();
	},

	/**
	 * addOptDoms 完成两项工作:
	 * 1. 添加行级按钮数据岛,以使行级按钮生效;
	 * 2. 为返回的数据岛tbos构造序号(index).
	 * @param sboList
	 * @param tbos
	 */
	_addOptDoms : function(sboList, tbos) {
		//需要添加的行级按钮
		var optDoms = sboList.children(DISLAND.OPERATE + "[" + DISLAND.OPERATE_EXPEND + "='true']");
		
		//源页面表格中的的最大序号
		var maxIdx = DISLAND.getMaxIdx(sboList);
		//添加行级按钮数据岛,以使行级按钮生效
		tbos.each(function() {
			maxIdx++;
			var copy = optDoms.clone();
			copy.attr(DISLAND.OPERATE_IDX, maxIdx);
			copy.appendTo($(this));
			$(this).attr(DISLAND.BO_INDEX, maxIdx);
		});
	},
	
	/**
	 * 将目标页面返回的信息添加到源页面, 添加时将根据echo去掉重复信息
	 * @param sboList
	 * @param tbos
	 * @param echo 源页面的重复者判断列, 仅对表格的弹出回填有效. 回填值以echo指定的bop为准, 判断是否重复, 如果重复则不填入源组件
	 * @returns
	 */
	_appendToSBoList : function(sboList, tbos, echo) {
		var echoBoIdxArr = this._getEchoBoIdxArr(sboList, tbos, echo);

		//没有重复数据, 将返回信息全部添加到源页面
		if(echoBoIdxArr.length == 0) {
			sboList.append(tbos);
		}
		//仅将非重复数据添加到源页面
		else {
			tbos.each(function() {
				if(!isInArray(echoBoIdxArr, $(this).attr(DISLAND.BO_INDEX))) 
					sboList.append($(this));
			});
		}
	},
	
	/**
	 * 添加tbos中缺失的bop
	 * 缺失的bop: 在源页面存在, 但tbos中没有对应关系的bop
	 * @param sboList 源页面boList
	 * @param tbos    目标页面bos
	 */
	_appendMissingBop : function(sboList, tbos) {
		var missingBopsStr = "";
		var targetBops = tbos.slice(0).children(DISLAND.BOP);
		//源页面表格的列信息
		var columnInfo = sboList.children(DISLAND.COLUMNINFO);
		columnInfo.children(DISLAND.COLUMNINFO_COLUMN).each(function() {
			var columnBind = $(this).attr(DISLAND.BIND);
			var flag = true;
			targetBops.each(function() {
				if($(this).attr(DISLAND.BIND) == columnBind) {
					flag = false;
					return false;
				}
			});
			
			if(flag) 
				missingBopsStr += "<" + DISLAND.BOP + " " + DISLAND.BIND + "=\"" + columnBind + "\">" + "</" + DISLAND.BOP + ">";
		});
		
		//将缺失的bop添加到tbos, 以使返回的数据岛结构与源页面一致
		if(!!missingBopsStr) {
			missingBopsStr = "<" + DISLAND.BO + ">" + missingBopsStr + "</" + DISLAND.BO + ">"
			var missingBops = XMLDomFactory.getInstance(missingBopsStr).find(DISLAND.BO).children();
			tbos.each(function() {
				var copy = missingBops.clone();
				copy.appendTo($(this));
			});
		}
	},
	
	/**
	 * 获取重复的信息的序号
	 * @param sboList 源页面boList
	 * @param tbos    目标页面bos
	 * @param echo	  过滤重复列的标识
	 */
	_getEchoBoIdxArr : function(sboList, tbos, echo) {
		if(!echo)
			return [];
			
		//源页面中所有echo代表的bop的数据
		var echoArr = [];
		sboList.children(DISLAND.BO).each(function() {
			var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + echo + "']");
			if(bop.length > 0)
				echoArr.push(getRealValue(bop));
		});
		
		var echoBoIdxArr = [];
		tbos.each(function() {
			var bop = $(this).find(DISLAND.BOP + "[" + DISLAND.BIND + "='" + echo + "']");
			if(bop.length > 0 && isInArray(echoArr, getRealValue(bop))) {
				echoBoIdxArr.push($(this).attr(DISLAND.BO_INDEX));
			}
		});	

		return echoBoIdxArr;
	},
	
	clear : function(paramData){
		var arrS = strToArray(paramData.sbopIds, DISLAND.SPLIT_COMMA);
		var cmp = null;
		for (var i = 0, length = arrS.length; i < length; i++) {
			cmp = dialog.getWinCmp(arrS[i], paramData.sysOperate);
			this.setCmpValue(cmp, "");
		}
	},
	
	/**
	 * 获取源页面的组件
	 */
	getWinCmp : function(fcId, sysOperate, isParent){
		var ext = isParent ? parent.Ext : Ext;		
		var winFcId = sysOperate ? fcId + "-" + sysOperate : fcId;
		return ext.getCmp(winFcId);
	},
	
	/**
	 * 为cmp组件设置值value 
	 */
	setCmpValue : function(cmp, value) {
		if(!cmp)
			return;
		
		if(isLabel(cmp)) 
			cmp.setText(value);
		else 
			cmp.setValue(value);
		
		//如果cmp与其它细粒度组件存在关联关系, 则强制执行关联操作
		var relationEventName = FCHandlerFactory.createFCHandler(null, cmp).getRelationEventName();
		cmp.fireEvent(relationEventName);
	},
	
	getDefWidth : function() {
		return Ext.getBody().getWidth() - 50;
	},
	
	getDefHeight : function() {
		return Ext.getBody().getHeight() - 20;
	}
};
