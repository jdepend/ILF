package com.rofine.platform.web.filter;

import java.io.Serializable;

/**
 * Created by Eric on 2016/1/19.
 */
public class CacheBean implements Serializable {

    private String key;

    private long lasttime;

    private int maxage=0;

    private String path;

    private String lastdate;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getLasttime() {
        return lasttime;
    }

    public void setLasttime(long lasttime) {
        this.lasttime = lasttime;
    }

    public int getMaxage() {
        return maxage;
    }

    public void setMaxage(int maxage) {
        this.maxage = maxage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }


    public boolean isStaticRes(){
        if(this.getKey().startsWith("stat_")){
            return true;
        }else{
            return false;
        }
    }
}
