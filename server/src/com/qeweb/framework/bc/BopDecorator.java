package com.qeweb.framework.bc;

import java.util.List;
import com.qeweb.framework.bc.prop.BCRange;
import com.qeweb.framework.bc.prop.Status;
import com.qeweb.framework.bc.prop.Value;
import com.qeweb.framework.bc.prop.BCRange.LogicEnum;

/**
 * bop装配者
 *
 */
public abstract class BopDecorator extends BOProperty {

	/**
	 *
	 */
	private static final long serialVersionUID = -5674651191851804522L;
	protected BOProperty bop;

	public BopDecorator(BOProperty bop) {
		this.bop = bop;
	}

	/**
	 *  添加range信息
	 * @param logicRule 逻辑符号
	 */
	abstract public void addRange(LogicEnum logicRule);

	/**
	 * bop初始化
	 */
	@Override
	public void init() {
		bop.init();
	}

	/**
	 * 校验bc是否满足指定条件
	 *
	 * @return true：校验成功
	 */
	@Override
	public boolean validate() throws Exception{
		return bop.validate();
	}

	/**
	 * 根据一个BusinessComponent获取与之关联的BusinessComponent
	 * @return List
	 */
	@Override
	public List<BusinessComponent> getRelations() {
		return bop.getRelations();
	}

	/**
	 *  关联 操作
	 * @param sourceBopList 源Bop
	 * @return
	 */
	@Override
	public BusinessComponent query(BOProperty sourceBop) throws Exception {
		return bop.query(sourceBop);
	}

	@Override
	public String toText() {
		return bop.toText();
	}

	@Override
	public Value getValue() {
		return bop.getValue();
	}

	@Override
	public void setValue(Value value) {
		bop.setValue(value);
	}

	@Override
	public BCRange getRange() {
		return bop.getRange();
	}

	@Override
	public void setRange(BCRange range) {
		bop.setRange(range);
	}

	@Override
	public Status getStatus() {
		return bop.getStatus();
	}

	@Override
	public void setStatus(Status status) {
		bop.setStatus(status);
	}

	@Override
	public String getLocalName() {
		return bop.getLocalName();
	}

	@Override
	public void setLocalName(String name) {
		bop.setLocalName(name);
	}

	@Override
	public String getMessage() {
		return bop.getMessage();
	}

	@SuppressWarnings("deprecation")
	public void setMessage(String Message) {
		bop.setErrorMessage(Message);
	}

	@Override
	public BCRange getQueryRange() {
		return bop.getQueryRange();
	}

	/**
	 * 自定义成功信息
	 * @param message 信息内容（可为国际化编码）
	 */
	@Override
	public void setSuccessMessage(String message) {
		bop.setSuccessMessage(message);
	}

	/**
	 * 自定义失败信息
	 * @param message 信息内容（可为国际化编码）
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void setErrorMessage(String message) {
		bop.setErrorMessage(message);
	}

	final public BOProperty getBop(){
		return this.bop;
	}

	@Override
	public String toLink() {
		return this.bop.toLink();
	}

	@Override
	public String toImage() {
		return this.bop.toImage();
	}

	@Override
	public Boolean isSortAble() {
		return this.bop.isSortAble();
	}

	@Override
	public void setSortAble(Boolean sortAble) {
		this.bop.setSortAble(sortAble);
	}

	@Override
	public BOProperty cloneBC() {
		return this.bop.cloneBC();
	}
	
	@Override
	public boolean isTiggerCRelation() {
		return bop.isTiggerCRelation();
	}
	
	@Override
	public void free() {
		if(this.bop != null) {
			this.bop.free();
			this.bop = null;
		}
	}
}
