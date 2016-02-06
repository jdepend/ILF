package com.qeweb.framework.common.instancepool;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPool.Config;

import com.qeweb.framework.common.appconfig.AppConfig;
import com.qeweb.framework.common.utils.StringUtils;

/**
 * 实例池对象管理类
 *
 * @author pengt
 *
 */
public class QewebInstancePool implements InstancePool {

	private static Config poolConfig = new GenericKeyedObjectPool.Config();

	static {
		// 允许最大活动对象数
		poolConfig.maxActive = StringUtils.convertToInt(AppConfig
				.getPropValue("maxActive"));
		// 允许最大空闲对象数
		poolConfig.maxIdle = StringUtils.convertToInt(AppConfig
				.getPropValue("maxIdle"));
		// 允许最大对象数
		poolConfig.maxTotal = StringUtils.convertToInt(AppConfig
				.getPropValue("maxTotal"));
		// 允许最大等待时间毫秒数
		poolConfig.maxWait = StringUtils.convertToInt(AppConfig
				.getPropValue("maxWait"));
		// 被空闲对象回收器回收前在池中保持空闲状态的最小时间毫秒数
		poolConfig.minEvictableIdleTimeMillis = StringUtils.convertToInt(AppConfig
				.getPropValue("minEvictableIdleTimeMillis"));
		// 允许最小空闲对象数
		poolConfig.minIdle = StringUtils.convertToInt(AppConfig
				.getPropValue("minIdle"));
		// 设定在进行后台对象清理时，每次检查对象数
		poolConfig.numTestsPerEvictionRun = StringUtils.convertToInt(AppConfig
				.getPropValue("numTestsPerEvictionRun"));
		// 在空闲连接回收器线程运行期间休眠的时间毫秒数. 如果设置为非正数,则不运行空闲连接回收器线程
		poolConfig.timeBetweenEvictionRunsMillis = StringUtils
				.convertToInt(AppConfig
						.getPropValue("timeBetweenEvictionRunsMillis"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static GenericKeyedObjectPool pool = new GenericKeyedObjectPool(new QewebKeyedPoolableObjectFactory(), poolConfig);

	private static QewebInstancePool instance = null;

	public static QewebInstancePool getInstance() {
		if(instance == null)
			instance = new QewebInstancePool();

		return instance;
	}

	/**
	 * 释放对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void freeObject(Object object) {
		try {
			pool.returnObject(object.getClass(), object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getObject(Class clasz) {
		Object o = null;
		try {
			o = pool.borrowObject(clasz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
