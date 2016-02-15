/**
 * 表格行级附件上传的被观察者.
 * 表格中的附件上传: 单击一个单元格, 弹出附件上传控件
 * @see ExtTable.java
 */
var CellEditObservable = function(win){
	CellEditObservable.superclass.constructor.call(this);
	
	this.win = win;
	this.formHandler;
	
	this._getFormHandler = function(tableId, bopBind){
		if(!this.formHandler){
			var bolist = DISLAND.getDom(tableId);
			var formDom  = DISLAND.createCellEditFormDom(bolist, bopBind);
			this.formHandler  = ContainerHandlerFactory.createContainerHandler(formDom);
		}
		return this.formHandler;
	};
	
	this.addObserver = function(observer) {
		this.formHandler = this._getFormHandler(observer.tableId, observer.bopBind);
		this.observers = [];
       	this.observers.push(observer);
    };
	
    this.notify = function(data) {
    	if(!this.formHandler.validate())
    		return;

    	var $super = new Observable();
    	$super.notify.call(this, this.win.returnData);
    };
    
    this.callback = function() {  
		this.win.hide();
    };
};
extend(CellEditObservable, Observable);