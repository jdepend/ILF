/**
 * 表格序号
 */
Ext.grid.RowNumberer = Ext.extend(Ext.grid.RowNumberer, {  
	renderer : function(value, cellmeta, record, rowIndex, columnIndex, store){  
		var start = 0;
		if(store.lastOptions && store.lastOptions.params)
	    	start = store.lastOptions.params.start
	    	return start + rowIndex + 1;  
	    }  
	});


/**
 * 屏蔽特殊键
 */
Ext.getDoc().on('keydown',function(e){
	var targetType = e.getTarget().type;
	//屏蔽Backspace
	if(e.getKey() == 8){
		if((targetType =='text' || targetType =='textarea' || targetType == 'password') 
				&& !e.getTarget().readOnly)
			;
		else 
			e.preventDefault();
	}
});