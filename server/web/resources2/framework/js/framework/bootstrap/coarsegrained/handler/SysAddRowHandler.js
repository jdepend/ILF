/**
 * talbe中的动态增加行TODO::
 */
function SysAddRowHandler(boDom) {
	SysAddRowHandler.superclass.constructor.call(this, boDom);
}

extend(SysAddRowHandler, TableHandler);


/**
 * 处理表格新增行操作
 * @param {} data
 */
SysAddRowHandler.prototype.addRow = function() {

}

/**
 * 处理表格删除行操作
 * @param {} data
 */
SysAddRowHandler.prototype.delRow = function() {

}

/**
 * 用于动态新增行时，保存删除的bolist的id
 */
SysAddRowHandler.prototype.saveDelBoListId = function(boList, delIds) {

}