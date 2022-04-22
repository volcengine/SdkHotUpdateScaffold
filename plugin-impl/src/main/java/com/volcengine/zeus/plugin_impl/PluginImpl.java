package com.volcengine.zeus.plugin_impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.volcengine.zeus.plugin_api.IPluginApi;

/**
 * 插件中的实现类
 *
 * @author xuekai
 * @date 8/31/21
 */
class PluginImpl implements IPluginApi {

    @Override
    public void startPluginActivity(Context context) {
        context.startActivity(new Intent(context, PluginMainActivity.class));
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
