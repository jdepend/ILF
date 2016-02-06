package com.rofine.platform.web.model.object;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdg on 2016/1/26.
 */
public class ObjectModel {

    private List<Element> elements = new ArrayList<Element>();

    private List<ElementRelation> relations = new ArrayList<ElementRelation>();

    public List<ElementRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<ElementRelation> relations) {
        this.relations = relations;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void addElement(Element element) {
        this.elements.add(element);
    }

    public void addRelation(ElementRelation r) {
        this.relations.add(r);
    }
}
