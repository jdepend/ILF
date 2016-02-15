/**
 * 执行form按钮query事件, extends BtnExeEvent
 *
 */
function BtnFormQueryEvent() {
	this.sourceName = '';
	
	BtnFormQueryEvent.superclass.constructor.call(this);
	
	this.handleRequest = function(operateDom) {
		this.init(operateDom);
		var boId = operateDom.parent().attr(DISLAND.BO_ID);
		if(observableArr[boId] && this.validate()) {
			lockSrceen();
			this.bindData();
			observableArr[boId].notify();
		}
	};
}

extend(BtnFormQueryEvent, BtnFormExeEvent);