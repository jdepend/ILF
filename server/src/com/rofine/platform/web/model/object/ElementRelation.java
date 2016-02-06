package com.rofine.platform.web.model.object;

/**
 * Created by wangdg on 2016/1/26.
 */
public abstract class ElementRelation {

    private String source;

    private String target;

    public ElementRelation(){}

    protected ElementRelation(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
