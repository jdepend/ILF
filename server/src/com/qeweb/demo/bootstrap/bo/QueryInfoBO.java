package com.qeweb.demo.bootstrap.bo;

import com.qeweb.framework.bc.BOProperty;
import com.qeweb.framework.bc.BOTemplate;
import com.qeweb.framework.bc.BusinessObject;

import java.io.Serializable;

/**
 * Created by wangdg on 2016/2/25.
 */
public class QueryInfoBO extends BusinessObject implements Serializable {

    private String icon;

    private String info;

    public QueryInfoBO(){
        getBOP("icon").setValue("glyphicon-calendar");
        getBOP("info").setValue("2015年2季度21周（05-18至05-24）");
    }

    public void xxx(BOTemplate bot){

//        this.getBOP("info").getValue();
//
//        this.info;

    }

}
