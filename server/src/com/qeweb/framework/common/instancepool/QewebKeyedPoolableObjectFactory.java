package com.qeweb.framework.common.instancepool;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

import com.qeweb.framework.common.utils.BoOperateUtil;
import com.qeweb.framework.manager.VCFactory;
import com.qeweb.framework.manager.VCManager;

/**
 *
 * 实例池工厂类, 负责创建/释放/销毁实例池中的实例,
 * 继承自apache coomon pool 的KeyedPoolableObjectFactory
 * @author pengt
 *
 */
@SuppressWarnings("rawtypes")
class QewebKeyedPoolableObjectFactory extends BaseKeyedPoolableObjectFactory {

	//private static Log log = LogFactory.getLog(QewebKeyedPoolableObjectFactory.class);

	@SuppressWarnings("unchecked")
	@Override
	public void activateObject(Object arg0, Object arg1) throws Exception {
		//log.info("激活对象");
		super.activateObject(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void destroyObject(Object arg0, Object arg1) throws Exception {
		//log.info("销毁对象-->" + arg0);
		super.destroyObject(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object makeObject(Object arg0) throws Exception {
		//log.info("创建一个新对象-->"+ arg0);
		Object o = null;
		Class clasz = (Class) arg0;

		// 创建BO
		if(BoOperateUtil.isBO(clasz)) {
			o = clasz.newInstance();
		}
		// 创建VC
		else {
			VCFactory vcf = VCManager.getVCFactory();
			o = vcf.createVC(clasz);
		}

		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void passivateObject(Object arg0, Object arg1) throws Exception {
		//log.info("挂起对象");
		super.passivateObject(arg0, arg1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateObject(Object arg0, Object arg1) {
		//log.info("验证对象");
		return super.validateObject(arg0, arg1);
	}

}
