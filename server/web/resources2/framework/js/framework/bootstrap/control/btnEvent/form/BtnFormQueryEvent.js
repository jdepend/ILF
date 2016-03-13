/**
 * 执行form按钮query事件, extends BtnExeEvent
 *
 */
function BtnFormQueryEvent() {
	BtnFormQueryEvent.superclass.constructor.call(this);
	this.handleRequest = function(operateDom) {

		this.init(operateDom);
		var boid = operateDom.parent().attr(DISLAND.BO_ID);

		if(observableArr[boid] && this.validate()) {
			this.bindData();

			observableArr[boid].notify();
		}
	}
}

extend(BtnFormQueryEvent, BtnFormExeEvent);
