package com.rofine.platform.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class StarTag extends TagSupport {

    private int count;
    private int total;

    @Override
    public int doStartTag() throws JspException {
        try {
            for(int i = 0; i < total; i++){
                if(i < count) {
                    this.pageContext.getOut().write("<span class=\"glyphicon glyphicon-star mlt-color\" aria-hidden=\"true\"></span>");
                }else{
                    this.pageContext.getOut().write("<span class=\"glyphicon glyphicon-star-empty mlt-color\" aria-hidden=\"true\"></span>");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (SKIP_BODY);
    }

    @Override
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
