package com.qeweb.busplatform.common.imp;

import com.qeweb.framework.exception.BOException;


/**
 * 业务平台接口——导入操作, 所有业务平台相关的导入操作都应该实现该接口
 */
public interface ImportIA {

	/**
	 * 导入操作
	 * @throws BOException
	 */
	void imp() throws BOException;
}
