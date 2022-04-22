package com.volcengine.zeus.plugin_impl;

import android.app.Application;
import android.content.Context;

import com.volcengine.zeus.plugin_api.Plugin;

/**
 * @author xuekai
 * @date 2021/6/3
 */
public class PluginApplication extends Application {

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 插件加载时回调该方法，可以在这里为Api层的api对象赋值
        Plugin.api = new com.volcengine.zeus.plugin_impl.PluginImpl();
    }
}
