package com.qeweb.framework.base.ia;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.common.Page;

/**
 * 持久化接口
 *
 */
public interface IBaseDao extends Serializable {
	final static public int UNDELETE_SIGNE = 0;	//未删除标识
	final static public int DELETE_SIGNE = 1;	//已删除标识

	final static public long OLD_NO = 0;	//OLD_ID初始值为0

	//字段名,对应的值是基础BO中的6个属性名称
	final static public String FIELD_ID = "id";								//主键
	final static public String FIELD_DELETEFLAG = "deleteFlag";				//删除标识
	final static public String FIELD_CREATEUSERID = "createUserId"; 		//创建人
	final static public String FIELD_CREATETIME = "createTime"; 			//创建时间
	final static public String FIELD_LASTMODIFYUSERID = "lastModifyUserId"; //最后修改人
	final static public String FIELD_LASTMODIFYTIME = "lastModifyTime"; 	//最后修改时间
	//字段名,对应的值是扩展BO(ExtendsBusinessObject)中的2个属性名称
	final static public String FIELD_OLDID = "oldId";						//上一条记录的id
	final static public String FIELD_OUTERID = "outerId";					//外码
	
	//物理表的字段名称
	final static public String PHY_ID = "ID";									//主键
	final static public String PHY_OLDID = "OLD_ID";							//上一条记录的id
	final static public String PHY_OUTERID = "OUTER_ID";						//外码
	final static public String PHY_DELETEFLAG = "DELETE_FLAG";					//删除标识
	final static public String PHY_CREATEUSERID = "CREATE_USER_ID"; 			//创建人
	final static public String PHY_CREATETIME = "CREATE_TIME"; 					//创建时间
	final static public String PHY_LASTMODIFYUSERID = "LAST_MODIFY_USER_ID"; 	//最后修改人
	final static public String PHY_LASTMODIFYTIME = "LAST_MODIFY_TIME"; 		//最后修改时间

	
	//中间表字段名,对应的值是基础中间表的2个属性名称
	final static public String FIELD_GROUPID = "groupId"; 			//组标识
	final static public String FIELD_GROUPOLDID = "groupOldId"; 	//旧的组标识
	
	//排序方向
	public static String ORDER_BY_ASC = "asc";						//正序
	public static String ORDER_BY_DESC = "desc";					//倒序

	/**
	 * 根据ID获取自定义BO对象,返回一个BO
	 * @param clazz 用户自定义Bo Class类型
	 * @param id
	 * @return T extends BusinessObject
	 */
	Object getById(final Class<?> clazz, final Serializable id);

	/**
	 * 根据查询条件查询BO类型的对象,返回一个BO
	 * @param criteria 查询条件
	 * @return Object
	 */
	Object get(final Object criteria);

	/**
	 * 跟据Sql查询
	 * @param sql
	 * @param criteria
	 * @return ? extends BusinessComponent
	 */
	@SuppressWarnings("rawtypes")
	List findBySql(final String sql, final Object criteria);

	/**
	 * 查询BC中的所有数据
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findAll(final Class<?> clazz);


	/**
	 * 根据查询条件查询,返回一个集合
	 *
	 * @param criteria 查询条件
	 */
	@SuppressWarnings("rawtypes")
	List findByCriteria(final Object criteria);

	/**
	 * 根据查询条件查询数量
	 *
	 * @param criteria 查询条件
	 * @return 查询数量
	 */
	int getCountByCriteria(final Object criteria);



	/**
	 *根据查询条件进行分类查询(有开始与结果标识)
	 *
	 * @param criteria 查询条件
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	Page findPageByCriteria(final Object criteria, final int pageSize, final int startIndex) ;

	/**
	 *根据查询条件进行查询 返回所有记录数，若数据量较大不建议使用
	 * @param criteria 查询条件
	 * @return
	 */
	@Deprecated
	Page findPageByCriteria(final Object criteria) ;

	/**
	 * 根据sql查询唯一数据
	 * @param sql
	 * @return
	 */
	Object createQueryUniqueResult(String sql);

	/**
	 * 根据sql查询数据
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List createQuery(String sql);

	/**
	 * 根据sql查询
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findBySql(String sql);

	/**
	 * 根据sql查询,仅有JDBC实现
	 * @param sql
	 * @param mapper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findBySql(String sql, RowMapper mapper);

	
	/**
	 * 根据sql查询,仅有hbm实现
	 * @param sql
	 * @param values
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findBySql(final String sql, final Object[] values);
	
	
	/**
	 * 保存业务组件，bo或bop
	 * @param bc	业务组件
	 */
	void save(final BusinessComponent bc);

	/**
	 * 根据sql语句保存
	 * @param sql	sql语句
	 */
	void save(final String sql);

	/**
	 * 根据sql语句保存
	 * @param sql	sql语句
	 * @param parameters	参数
	 */
	void save(final String sql, final Object[] parameters);

	/**
	 * 批量保存业务组件
	 * @param bcList	业务组件集合
	 */
	void batchSave(final Collection<BusinessComponent> bcList);

	/**
	 * 根据sql语句，批量保存（带参）
	 * @param sql	sql语句
	 * @param parameters	参数
	 */
	void batchSave(final String sql, final List<Object[]> parameters);

	/**
	 * 保存或更新bo对象
	 * @param
	 */
	void saveOrUpdate(final BusinessComponent bc);

	/**
	 * 更新业务组件
	 * @param bc	业务组件
	 */
	void update(final BusinessComponent bc);

	/**
	 * 根据sql语句更新
	 * @param sql	sql语句
	 */
	void update (final String sql);

	/**
	 * 根据sql语句更新（带参）
	 * @param sql	sql语句
	 * @param parameters	参数
	 */
	void update (final String sql, final Object[] parameters);

	/**
	 * 批量更新业务组件
	 * @param bcList	业务组件集合
	 */
	void batchUpdate(final Collection<BusinessComponent> bcList);

	/**
	 * 根据sql语句批量更新（带参）
	 * @param sql	sql语句
	 * @param parameters	参数
	 */
	void batchUpdate(final String sql, final List<Object[]> parameters);

	/**
	 * 删除业务组件
	 * @param bc	业务组件
	 */
	void delete(final BusinessComponent bc);

	/**
	 * 根据sql语句删除
	 * @param sql	sql
	 */
	void delete (final String sql);

	/**
	 * 根据sql语句删除（带参）
	 * @param sql	sql
	 * @param parameters 参数
	 */
	void delete (final String sql, final Object[] parameters);

	/**
	 * 批量删除业务组件
	 * @param bcList	业务组件集合
	 */
	void batchDelete(final Collection<BusinessComponent> bcList);

	/**
	 * 根据sql语句批量删除（带参）
	 * @param sql
	 * @param parameters
	 */
	void batchDelete (final String sql, final List<Object[]> parameters);

	/**
	 * 根据sql插入，更新，删除，不能查询
	 * @param sql
	 */
	@Deprecated
	void executeSql(final String sql);

	/**
	 * 根据sql插入，更新，删除，不能查询（带参）
	 * @param sql
	 * @param parameters
	 */
	@Deprecated
	void executeSql(final String sql, final Object[] parameters);

	/**
	 * 根据sql批量插入，更新，删除，不能查询（带参）
	 * @param sql
	 * @param parameters
	 */
	@Deprecated
	void batchExecuteSql(final String sql, final List<Object[]> parameters);


	/**
	 *根据查询条件进行总数查询(有开始与结果标识)
	 *
	 * @param criteria 查询条件
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	Integer findTotleByCriteria(final Object criteria);
	


	/**
	 *根据查询条件进行分页查询数据(有开始与结果标识)
	 *
	 * @param criteria 查询条件
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findListByCriteria(final Object criteria, final int pageSize, final int startIndex);
}
