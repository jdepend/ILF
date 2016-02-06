package com.example.myapp;

import android.os.Bundle;
import org.apache.cordova.Config;
import org.apache.cordova.DroidGap;

/**
 * Created by wangdg on 2015/12/16.
 */
public class WebActivity extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决黑屏问题
        super.init();
        this.appView.addJavascriptInterface(this, "androidWebActivity");
        this.appView.setBackgroundResource(R.drawable.splash);//设置背景图片
        super.setIntegerProperty("splashscreen",R.drawable.splash ); //设置闪屏背景图片

        super.loadUrl(Config.getStartUrl(), 2000);
    }

    public void exit(){
        System.exit(0);
    }
}
