package com.qeweb.framework.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import com.qeweb.framework.base.ia.IBaseDao;
import com.qeweb.framework.base.ia.IXmlDao;
import com.qeweb.framework.base.impl.BaseDaoInfo;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.Range;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;

/**
 * 业务组件类
 * <br>
 * 它是一个抽象类，每个业务对象Business Object和业务属性Business Property都是一个BusinessComponent
 */
public abstract class BusinessComponent implements Serializable {
	
	private static final long serialVersionUID = 8039490295815825299L;
	private Value value;	
	private Status status;
	private BCRange range;
	
	private String localName = "";			//属性国际化名称
	private boolean success = true;			//是否操作成功
	private String message;					//提示信息,可保存自定义成功提示或失败提示
	
	private long id;						//主键
	private long createUserId;				//创建人
	private Timestamp createTime;			//创建时间
	private long lastModifyUserId; 			//最后修改人
	private Timestamp lastModifyTime; 		//最后修改时间
	private int deleteFlag;					//删除标识 

	private IBaseDao dao;
	
	/**
	 * 初始化
	 */
	abstract public void init();
	
	/**
	 * 校验bc是否满足指定条件
	 * 
	 * @return true：校验成功
	 */
	abstract public boolean validate() throws Exception;

	/**
	 * 根据一个BusinessComponent获取与之关联的BusinessComponent
	 * @return List
	 */
	public List<BusinessComponent> getRelations() {
		return null;
	}
	
	public IBaseDao getDao() {
		if(dao == null)
			dao = BaseDaoInfo.getDao();
		return dao;
	}
	
	public IBaseDao getJDBCDao() {
		return BaseDaoInfo.getJDBCDao();
	}
	
	public IXmlDao getXmlDao() {
		return BaseDaoInfo.getXmlDao();
	}

	public void setValue(String value) {
		Value thisValue = getValue();
		thisValue.setValue(value);
	}
	
	public Value getValue() {
		if(value == null) 
			value = new Value();
		return value;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	
	public BCRange getRange() {
		if(range == null)
			range = new BCRange();
		return range;
	}

	public void setRange(BCRange range) {
		this.range = range;
	}

	public void addRange(Range range) {
		getRange().addRange(range);
	}
	
	public Status getStatus() {
		if(status == null)
			status = new Status();
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLastModifyUserId() {
		return lastModifyUserId;
	}


	public void setLastModifyUserId(long lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(long createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * 是否高亮显示
	 * @return
	 */
	public boolean isHighlight() {
		return getStatus().isHighlight();
	}

	/**
	 * 设置高亮显示.
	 * 业务对象仅需要标明是否采用高亮显示即可, 其对应的展示组件将自动根据不同页面去数据库中查找对应的schema
	 * @param highlight
	 */
	public void setHighlight(boolean highlight) {
		getStatus().setHighlight(highlight);
	}
	
	public String getStyle() {
		return getStatus().getStyle();
	}
	
	/**
	 * 设置业务对象的样式属性
	 * 业务对象设置style属性时,输入标准css样式即可，例如：xxxBO.setStyle("font-size:12px;color:#FF0000;");
	 * 如果业务对象中同时设置了Style和Highlight,Style优先级高于Highlight
	 * @param style
	 */
	public void setStyle(String style) {
		getStatus().setStyle(style);
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}
	
	/**
	 * 执行结果成功提示信息. 当操作执行成功后, 可使用successMsg存储个性化提示信息, 如果successMsg为空, 使用系统默认的"操作成功"提示. 
	 * 业务校验的失败信息可使用throw BOException抛出.
	 * @param successMsg 国际化编码
	 */
	public void setSuccessMessage(String successMsg) {
		this.success = true;
		this.message = successMsg;
	}
	
	/**
	 * 自定义失败信息[不推荐使用]</br>
	 * (推荐使用抛出业务异常方式)
	 * @param message 信息内容国际化编码
	 * @deprecated
	 */
	public void setErrorMessage(String message) {
		this.success = false;
		this.message = message;
	}
	
	public BusinessComponent cloneBC(){
		BusinessComponent cloneObj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			oos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			cloneObj = (BusinessComponent) ois.readObject();
			ois.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return cloneObj;
	}
	
	/**
	 * 释放内存
	 */
	abstract public void free();

	public void setDao(IBaseDao dao) {
		this.dao = dao;
	}
}
