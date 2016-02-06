package com.qeweb.demo.load.container.form.bo;

import java.sql.Timestamp;

import com.qeweb.demo.load.container.form.bop.DemoFormCheckBoxBOP;
import com.qeweb.demo.load.container.form.bop.DemoFormImgBOP;
import com.qeweb.demo.load.container.form.bop.DemoFormOTSBOP;
import com.qeweb.demo.load.container.form.bop.DemoFormRadioBOP;
import com.qeweb.demo.load.container.form.bop.DemoFormSelectBOP;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.prop.expend.ExpendStatus;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.bc.sysbop.MultiFileBOP;
import com.qeweb.framework.frameworkbop.NotEmptyBop;

/**
 * demo: 表单示例.
 * 路径: 装载-表单
 */
public class DemoFormBO extends BusinessObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1511655925566708075L;

	//text类型控件
	private String textField;
	private String textArea;
	private String hidden;
	private String password;
	private String label;
	private String label2;
	private String editor;
	private String editor2;
	private String spinner;
	
	//枚举型控件
	private String select;
	private String checkBox;
	private String radio;
	private String optionTransferSelect;
	
	//上传和下载控件
	private FileBOP singleFile;				//单附件上传 
	private MultiFileBOP multiFile;			//多附件上传
	private FileBOP downLoad;
	
	//其它控件
	private Timestamp date_1;
	private Timestamp date_2;
	private Timestamp date_3;
	private Timestamp date_4;
	private String image;
	
	public DemoFormBO() {
		getBOP("label").setValue("This is a label. \nHello, I'm a label, I can break line."
				+ "The label colspan 2 columns."
				+ "这是一个label, 这是一个html的代码:<br>, 这个label占两列, 并且可以折行.");
		addBOP("label2", new DemoFormSelectBOP());
		getBOP("label2").setValue("1");
		
		addBOP("select", new DemoFormSelectBOP());
		addBOP("radio", new DemoFormRadioBOP());
		addBOP("checkBox", new DemoFormCheckBoxBOP());
		addBOP("optionTransferSelect", new DemoFormOTSBOP());
		addBOP("singleFile", new FileBOP());
		addBOP("multiFile", new MultiFileBOP());
		addBOP("image", new DemoFormImgBOP());
		addBOP("editor", new NotEmptyBop());
		getBOP("textArea").setValue("this is a textArea.<br> \t\n this is a textArea too.");
		
		FileBOP bop = (FileBOP)getBOP("downLoad");
		bop.setPath("http://apache.etoak.com//ant/binaries/apache-ant-1.8.2-bin.zip");
		bop.setValue("apache-ant-1.8.2");
		
		getBOP("textField").getValue().setEmptyValue("查询条件1");
		getBOP("select").getValue().setEmptyValue("查询条件2");
		
		getBOP("textField").getValue().setTipValue("这是一个文本框");
		getBOP("select").getValue().setTipValue("这是一个下拉框");
		
		getBOP("editor").setValue("<P><FONT color=#ff0000><STRONG>89879</STRONG>87987中文&lt;br&gt;&amp;nbsp;</FONT></P>");
		getBOP("editor2").setValue("<P><FONT color=#ff0000><STRONG>89879</STRONG>87987中文&lt;br&gt;&amp;nbsp;</FONT></P>");
		
		ExpendStatus s = new ExpendStatus();
		s.getEnd().setDisable(true);
		getBOP("date_3").setStatus(s);
		addBOP("date_4", new NotEmptyBop());
		
		addBOP("date_1", new NotEmptyBop());
	}
	
	/**
	 * 
	 * @param bo
	 */
	public void save(DemoFormBO bo) {
		System.out.println("--------------------------------------------------save start---------------------------------------------------");
		System.out.println("|\ttextField = " + bo.getTextField());
		System.out.println("|\tlabel = " + bo.getLabel());
		System.out.println("|\tlabel2 = " + bo.getLabel2());
		System.out.println("|\tpassword = " + bo.getPassword());
		System.out.println("|\ttextArea = " + bo.getTextArea());
		System.out.println("|\teditor = " + bo.getEditor());
		System.out.println("|\tspinner = " + bo.getSpinner());
		System.out.println("--------------------------------------------------------end----------------------------------------------------");
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getTextArea() {
		return textArea;
	}

	public void setTextArea(String textArea) {
		this.textArea = textArea;
	}

	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(String checkBox) {
		this.checkBox = checkBox;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public String getOptionTransferSelect() {
		return optionTransferSelect;
	}

	public void setOptionTransferSelect(String optionTransferSelect) {
		this.optionTransferSelect = optionTransferSelect;
	}

	public FileBOP getSingleFile() {
		return singleFile;
	}

	public void setSingleFile(FileBOP singleFile) {
		this.singleFile = singleFile;
	}

	public MultiFileBOP getMultiFile() {
		return multiFile;
	}

	public void setMultiFile(MultiFileBOP multiFile) {
		this.multiFile = multiFile;
	}

	public FileBOP getDownLoad() {
		return downLoad;
	}

	public void setDownLoad(FileBOP downLoad) {
		this.downLoad = downLoad;
	}

	public Timestamp getDate_1() {
		return date_1;
	}

	public void setDate_1(Timestamp date_1) {
		this.date_1 = date_1;
	}

	public Timestamp getDate_2() {
		return date_2;
	}

	public void setDate_2(Timestamp date_2) {
		this.date_2 = date_2;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditor2() {
		return editor2;
	}

	public void setEditor2(String editor2) {
		this.editor2 = editor2;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public Timestamp getDate_3() {
		return date_3;
	}

	public void setDate_3(Timestamp date_3) {
		this.date_3 = date_3;
	}

	public Timestamp getDate_4() {
		return date_4;
	}

	public void setDate_4(Timestamp date_4) {
		this.date_4 = date_4;
	}

	public String getSpinner() {
		return spinner;
	}

	public void setSpinner(String spinner) {
		this.spinner = spinner;
	}
	
}
