package com.volcengine.zeus.plugin1_impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.volcengine.zeus.plugin1_api.IPlugin1Api;

/**
 * 插件中的实现类
 *
 * @author xuekai
 * @date 8/31/21
 */
class Plugin1Impl implements IPlugin1Api {

    @Override
    public void startPluginActivity(Context context) {
        context.startActivity(new Intent(context, Plugin1MainActivity.class));
    }

    @Override
    public View getPluginView(Context context) {
        return new PluginView(context);
    }

    @Override
    public void addPluginView(ViewGroup viewGroup) {
        viewGroup.addView(new PluginView(viewGroup.getContext()));
    }
}
