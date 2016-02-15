/**
 * 表格中的附件编辑回填的观察者
 * @see EditRender.js   fileRender
 */
var CellEditObserver = function(){
	CellEditObserver.superclass.constructor.call(this);
    
	this.tableId;
	this.boIndex;
	this.bopBind;
	this.editParam;
	
    this.update = function(observable, data) {
		var bolist = DISLAND.getDom(this.tableId);
		var bo = bolist.children(DISLAND.BO + "[" + DISLAND.BO_INDEX + "='" + this.boIndex + "']").slice(0);
		var bop = bo.children(DISLAND.BOP + "[" + DISLAND.BIND + "='" + this.bopBind + "']").slice(0);
		
		var curGrid = Ext.getCmp(this.tableId);
		var store = curGrid.getStore();
		var record = store.getAt(this.boIndex - 1);
		
		if(data) {
			bop.attr(DISLAND.BOP_DATA_URL, data.filePath);
			bop.text(data.fileName);
			var columnInfo = bolist.children(DISLAND.COLUMNINFO_COLUMN).slice(0);
			record.set(this.bopBind, data.fileName);
			this.editParam.value = data.fileName;
			TableHelper.formatCellForEditRow(bolist, bo, this.editParam);
		}
		
        observable.callback();  
    };
};
extend(CellEditObserver, Observer);