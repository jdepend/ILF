package com.rofine.platform.web.model.viewmodel.form;

/**
 * Created by wangdg on 2016/1/26.
 */
public class FormElement {

    //界面标示
    private String type;
    //标题
    private String title;
    //是否必填
    private boolean required = false;
    //最大长度
    private int maxLength = -1;
    //最小长度
    private int minLength = -1;
    //界面类型
    private String uiType;

    public static final String TEXT = "TEXT";
    public static final String AREA = "AREA";
    public static final String SELECT = "SELECT";
    public static final String RADIO = "RADIO";

    //界面状态
    private String uiState;

    public static final String DISABLED = "DISABLED";
    public static final String READONLY = "READONLY";
    public static final String DISPLAY = "DISPLAY";
    public static final String NODISPLAY = "NODISPLAY";

    public FormElement() {
    }

    public FormElement(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getUiType() {
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public String getUiState() {
        return uiState;
    }

    public void setUiState(String uiState) {
        this.uiState = uiState;
    }
}
