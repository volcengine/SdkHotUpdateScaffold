package com.volcengine.zeusscaffold;


import android.app.Application;

import com.volcengine.zeus.GlobalParam;
import com.volcengine.zeus.plugin1_api.Plugin1;

public class ZeusDemoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalParam.getInstance().setDebug(true);
        Plugin1.init(this);
    }
}
