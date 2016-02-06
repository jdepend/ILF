package com.qeweb.framework.common.instancepool;

/**
 *
 * 实例池接口
 *
 */
public interface InstancePool {

	/**
	 * 从对象池中获取clasz类型的实例
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object getObject(Class clasz);

	/**
	 * 将实例释放到对象池
	 * @param object
	 */
	public void freeObject(Object object);

}
