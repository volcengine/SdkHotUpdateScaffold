package com.volcengine.zeusscaffold.fragmentpluginimpl;

import android.app.Application;
import android.content.Context;

import com.volcengine.zeusscaffold.fragmentpluginapi.FragmentPlugin;

public class FragmentPluginApplication extends Application {

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 插件加载时回调该方法，可以在这里为Api层的api对象赋值
        FragmentPlugin.api = new FragmentPluginImpl();
    }
}
