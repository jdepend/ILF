package com.rofine.platform.model.demo;

import java.util.List;
import java.util.UUID;

/**
 * Created by wangdg on 2015/12/4.
 */
public class DeptStatisticsInfo {

    private String id;

    private String name;

    private int userCount;

    private int workCount;

    public DeptStatisticsInfo(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }
}
