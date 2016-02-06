package com.rofine.platform.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AvatarTag extends TagSupport {

    private String img;

    private String name;

    private final static String grey = "grey.gif";

    @Override
    public int doStartTag() throws JspException {
        try {
            if (this.name != null) {
                this.pageContext.getOut().write("<div class=\"row\" align=\"center\">");
            }
            this.printImg();
            if (this.name != null) {
                this.pageContext.getOut().write("</div>");
            }
            if (this.name != null) {
                this.pageContext.getOut().write("<div class=\"row\" style=\"text-align: center;\">");
                this.printName();
                this.pageContext.getOut().write("</div>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (SKIP_BODY);
    }

    private void printImg() throws IOException {

        this.pageContext.getOut().write("<a class=\"thumbnail\" style=\"width:54px;height: 54px;\">");
        this.pageContext.getOut().write("<img src=\"");
        this.pageContext.getOut().write(((HttpServletRequest) this.pageContext.getRequest()).getContextPath());
        this.pageContext.getOut().write("/resources/images/");
        this.pageContext.getOut().write(grey);
        this.pageContext.getOut().write("\"");

        this.pageContext.getOut().write(" data-original=\"");
        this.pageContext.getOut().write(((HttpServletRequest) this.pageContext.getRequest()).getContextPath());
        this.pageContext.getOut().write("/resources/images/");

        if(this.img != null && this.img.length() > 0) {
            this.pageContext.getOut().write(img);
            this.pageContext.getOut().write(".png");
        }else{
            this.pageContext.getOut().write(grey);
        }
        this.pageContext.getOut().write("\"");

        this.pageContext.getOut().write(" alt=\"...\">");
        this.pageContext.getOut().write("</a>");
    }

    private void printName() throws IOException {
        this.pageContext.getOut().write("<span id=\"avatarName\">");
        this.pageContext.getOut().write(name);
        this.pageContext.getOut().write("</span>");
    }


    @Override
    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
