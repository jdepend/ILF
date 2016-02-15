package com.qeweb.demo.interaction.validation.bop;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BusinessComponent;
import com.qeweb.framework.frameworkbop.AreaBOP;
import com.qeweb.framework.frameworkbop.CityBOP;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eric on 2016/2/15.
 */
public class Prop1BOP  extends BOProperty {

    @Override
    public List<BusinessComponent> getRelations() {
        List<BusinessComponent> result = new LinkedList<BusinessComponent>();
        result.add(new Prop2BOP());
        return result;
    }
}
