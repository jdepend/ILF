/**
 * AnchorHandler继承FCHandler(FCHandler.js), 处理超链接anchor
 * @param bopDom	细粒度组件数据岛
 * @param fc
 *            细粒度组件Ext容器
 * @returns
 */
function AnchorHandler(bopDom, fc) {
	AnchorHandler.superclass.constructor.call(this, bopDom, fc);

	this._addRelation = function(){};

	this._updateRange = function(){};

	this._setDisabled = function(isHidden) {};

	this._setReadonly = function(isReadOnly) {};

	//获取细粒度组件的值
	this._getValue = function(){};

	//细粒度组件设值
	this._setValue = function(value) {
		var anchorDom = this.fc.getEl().dom;
		
		//超链接绑定了一个行为, 此时超链接可看作一个按钮
		var operateId = DISLANDTODOM.getAnchorOperateId(this.bopDom);
		var operateDom = getDataIslandByJQ().find(DISLAND.OPERATE + "[" + DISLAND.OPERATE_ID + "='" + operateId + "']");
		if(operateDom.length > 0){
			var $div =  $(anchorDom).children('div').children('div');
			var $a = $div.children('a');
			$a.text(value);
			
			btnHandler = new ButtonHandler(operateDom);
			btnHandler.init();
			return;
		}
		
		//普通的超链接, 未绑定行为
		var data = this.bopDom.children(DISLAND.BOP_DATA);
		//anchor未绑定fileBOP
		if(data.length == 0) {
			anchorDom.innerText = value;
		}
		else {
			var url = data.attr(DISLAND.BOP_DATA_URL);
			//fileBOP未设置path
			if(!url) {
				anchorDom.innerText = value;
			}
			else {
				if(!isHttp(url)) 
					url = appConfig.ctx + url;
				
				var a = '<A href="' + url + '" target=_blank>' + value + '</A>';
				anchorDom.innerText = a;
				anchorDom.innerHTML = anchorDom.innerHTML.replace(/&lt;A.*&lt;\/A&gt;/, a);
			}
		}
	};
	
	/**
	 * 获取超链接的展示值
	 */
	this._getValue = function(){
		var anchorDom = this.fc.getEl().dom;
		return anchorDom.innerText;
	};
}

extend(AnchorHandler, FCHandler);