package com.volcengine.zeusscaffold.fragmentpluginimpl;

import android.app.Application;
import android.content.Context;

import com.volcengine.zeusscaffold.fragmentpluginapi.FragmentPlugin;
import com.volcengine.zeusscaffold.navigation.ZeusFragmentFactory;

public class FragmentPluginApplication extends Application {

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 插件加载时回调该方法，可以在这里为Api层的api对象赋值
        FragmentPlugin.api = new FragmentPluginImpl();
        // 可以在插件生命周期中注入该插件的所有navigation相关的fragment，这样这个配置信息可以让每个插件自己维护，同时也可以实现动态化（当然navigation.xml不能动态化）
        // 也可以在宿主测统一注入，无所谓的。
        ZeusFragmentFactory.fragment2Plugin.put("com.volcengine.zeusscaffold.fragmentpluginimpl.PluginFragment", base);
    }
}
