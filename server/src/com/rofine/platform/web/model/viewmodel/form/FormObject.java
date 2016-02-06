package com.rofine.platform.web.model.viewmodel.form;

import com.rofine.platform.web.model.viewmodel.Container;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2016/2/3.
 */
public class FormObject extends Container {

    private List<FormElement> elements = new ArrayList<FormElement>();

    public FormObject() {

    }

    public FormObject(String id) {
        super(id);
    }

    public List<FormElement> getElements() {
        return elements;
    }

    public void setElements(List<FormElement> elements) {
        this.elements = elements;
    }

    public void addElement(FormElement element){
        elements.add(element);
    }
}
