package com.volcengine.zeus.plugin_api;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * 可以在这里定义SDK对宿主暴露的接口
 *
 * @author xuekai
 * @date 8/31/21
 */
public interface IPluginApi {

    View getPluginView(Context context);

    void addPluginView(ViewGroup viewGroup);

    void startActivity(Context context, String tag);

    void startActivity(Context context, Intent intent);

    Fragment getFragment();

    void startPluginService(Context context);

    void bingPluginService(Context context);

    void sendBroadcastByPlugin(Context context);

    void insertProviderByPlugin(Context context);

    void startPluginIntentServiceByPlugin(Context context);
}
