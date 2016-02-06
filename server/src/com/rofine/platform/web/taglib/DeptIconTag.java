package com.rofine.platform.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class DeptIconTag extends TagSupport {

    private String name;

    @Override
    public int doStartTag() throws JspException {
        try {
            this.pageContext.getOut().write("<a class=\"thumbnail\" style=\"width:54px;height: 54px;\">");
            this.pageContext.getOut().write(name);
            this.pageContext.getOut().write("</a>");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (SKIP_BODY);
    }

    @Override
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
