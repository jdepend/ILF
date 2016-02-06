package com.rofine.platform.web.event;

import com.rofine.platform.web.controller.BaseController;
import com.rofine.platform.web.model.PageModelFactory;
import com.rofine.platform.web.model.WebModelConstant;
import com.rofine.platform.web.model.event.EventModel;
import com.rofine.platform.web.model.object.Element;
import com.rofine.platform.web.model.object.ObjectModel;
import com.rofine.platform.web.model.object.ValueRangeRelation;
import com.rofine.platform.web.model.object.ValueStateRelation;
import com.rofine.platform.web.model.PageModel;
import com.rofine.platform.web.model.viewmodel.PageMetaDataModel;
import com.rofine.platform.web.model.viewmodel.form.FormElement;
import com.rofine.platform.web.model.viewmodel.form.FormObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangdg on 2015/12/3.
 */
@Controller
@RequestMapping(value = "object")
public class ObjectController extends BaseController{

    @RequestMapping(value = "sample/index", method = RequestMethod.GET)
    public String objectIndex(ModelMap model) {

        this.processModelMap(model, this.getPageMetaDataModel(), "object/sample/eventdata");

        return "event.object/one.page.defpage";
    }

    @RequestMapping(value = "sample/eventdata", method = RequestMethod.GET)
    @ResponseBody
    public PageModel obtainEventData(ModelMap model) {

        EventModel eventModel = new EventModel();

        Map<String, String> targetIds = new LinkedHashMap<String, String> ();
        targetIds.put("cycle", "filter");

        eventModel.addEventChain("type", "change", targetIds);

        targetIds = new LinkedHashMap<String, String> ();
        targetIds.put("cycle_current", "change");

        eventModel.addEventChain("cycle", "change", targetIds);

        eventModel.setObjectModel(this.getObjectModel());

        return PageModelFactory.createPageModel(eventModel);
    }

    private PageMetaDataModel getPageMetaDataModel(){

        FormObject formObject = new FormObject();

        FormElement name = new FormElement("name");
        name.setTitle("项目名称");
        name.setRequired(true);
        name.setUiType(FormElement.TEXT);

        formObject.addElement(name);

        FormElement code = new FormElement("code");
        code.setTitle("项目编码");
        code.setRequired(true);
        code.setUiType(FormElement.TEXT);

        formObject.addElement(code);

        FormElement type = new FormElement("type");
        type.setTitle("项目类型");
        type.setRequired(true);
        type.setUiType(FormElement.SELECT);

        formObject.addElement(type);

        FormElement cycle = new FormElement("cycle");
        cycle.setTitle("项目上报周期");
        cycle.setRequired(true);
        cycle.setUiType(FormElement.RADIO);

        formObject.addElement(cycle);

        FormElement cycle_current = new FormElement("cycle_current");
        cycle_current.setTitle("本次上报周期");
        cycle_current.setRequired(true);
        cycle_current.setUiType(FormElement.TEXT);
        cycle_current.setUiState(FormElement.NODISPLAY);

        formObject.addElement(cycle_current);

        return new PageMetaDataModel(formObject);

    }

    private ObjectModel getObjectModel(){

        ObjectModel objectModel = new ObjectModel();

        Element type = new Element("type");
        type.addCandidateValue("", "请选择");
        type.addCandidateValue("system", "系统方案");
        type.addCandidateValue("office", "机关方案");

        objectModel.addElement(type);

        Element cycle = new Element("cycle");
        cycle.addCandidateValue("10", "日");
        cycle.addCandidateValue("20", "周");
        cycle.addCandidateValue("30", "月");
        cycle.addCandidateValue("40", "年");

        cycle.addRangeValue("a","10");
        cycle.addRangeValue("a","20");
        cycle.addRangeValue("b","30");
        cycle.addRangeValue("b","40");

        objectModel.addElement(cycle);

        ValueRangeRelation relation1 = new ValueRangeRelation("type", "cycle");
        relation1.addValueRange("system", "a");
        relation1.addValueRange("office", "b");

        objectModel.addRelation(relation1);

        ValueStateRelation relation2 = new ValueStateRelation("cycle", "cycle_current");
        relation2.addValueState("10", "open");
        relation2.addValueState("20", "close");

        relation2.addValueState("30", "open");
        relation2.addValueState("40", "close");

        objectModel.addRelation(relation2);

        return objectModel;

    }



}
