
function lockSrceen(lockMsg) {
	var msg = lockMsg || I18N.COMMON_LOADING;
	Ext.getBody().mask(msg, 'x-mask-loading');
	
}

function unlockScreen() {
	Ext.getBody().unmask();
}

/**
 * 判断cmp是否是label控件
 * @param cmp
 * @returns {Boolean}
 */
function isLabel(cmp) {
	return cmp && cmp.defaultType == 'label';
}