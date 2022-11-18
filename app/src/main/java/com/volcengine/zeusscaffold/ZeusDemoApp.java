package com.volcengine.zeusscaffold;


import android.app.Application;

import com.volcengine.zeus.plugin_api.PluginMain;

public class ZeusDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PluginMain.callFirst();
        // 进行Zeus框架的初始化.如果插件manifest中有子进程的组件，则必须在Application#onCreate中执行，否则只需要在插件使用前初始化即可
        PluginMain.init(this);
    }
}
