package com.volcengine.zeusscaffold;


import android.app.Application;

import com.volcengine.zeus.GlobalParam;
import com.volcengine.zeus.plugin_api.PluginMain;

public class ZeusDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 必须保证在首次触发Zeus.init之前，调用GlobalParam.getInstance()的各种配置方法，否则会报错。
        GlobalParam.getInstance().setDebug(true);
        // 进行Zeus框架的初始化以及插件的初始化
        PluginMain.init(this);
    }
}
