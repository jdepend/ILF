package com.rofine.platform.model.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangdg on 2015/12/4.
 */
public class DeptUserInfo {

    private String id;

    private String name;

    private List<UserInfo> userInfos = new ArrayList<UserInfo>();

    public DeptUserInfo(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void addUserInfo(UserInfo userInfo) {
        this.userInfos.add(userInfo);
        userInfo.setDept(this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
