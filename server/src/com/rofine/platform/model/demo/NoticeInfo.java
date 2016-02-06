package com.rofine.platform.model.demo;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wangdg on 2015/12/17.
 */
public class NoticeInfo {

    private String id;

    private String title;

    private String content;

    private Date createDate;

    public NoticeInfo(){
        this.id = UUID.randomUUID().toString();
        this.createDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
