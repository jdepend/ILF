package com.qeweb.framework.bc.sysbo;


/**
 * MenuBO 是一类特殊的TreeBO, 它用于生成选择树,
 * 每个节点都存在一个选择框.
 *
 */
public abstract class CheckTreeBO extends TreeBO {
	private static final long serialVersionUID = -3257828639305216759L;
	
	/**
	 * 多选模式(默认)
	 */
	static public final String CM_MULTIPLE = "multiple";
	/**
	 * 单选模式
	 */
	static public final String CM_SINGLE = "single";
	
	/**
	 * 是否所有节点可选择（true：所有节点可选择，false：只有叶子节点可选择）
	 */
	private boolean allOptional = true;
	/**
	 * 多选模式时是否级联选择
	 */
	private boolean isCascade = true;
	
	//选择框是否被选中
	private boolean checked;
	//选择模式
	private String checkModel = CM_MULTIPLE;
	
	public CheckTreeBO() {
		super();
	}
	
	public CheckTreeBO(long id, long parentId, String value, int sortIndex, boolean checked) {
		super(id, parentId, value, sortIndex);
		this.checked = checked;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getCheckModel() {
		return checkModel;
	}

	public void setCheckModel(String checkModel) {
		this.checkModel = checkModel;
	}

	public boolean isAllOptional() {
		return allOptional;
	}

	/**
	 * 是否所有节点可选择（true：所有节点可选择，false：只有叶子节点可选择）
	 */
	public void setAllOptional(boolean allOptional) {
		this.allOptional = allOptional;
	}

	public boolean isCascade() {
		if(!this.allOptional)
			return false;
		return isCascade;
	}

	/**
	 * 多选模式时是否级联选择
	 */
	public void setCascade(boolean isCascade) {
		this.isCascade = isCascade;
	}
}
