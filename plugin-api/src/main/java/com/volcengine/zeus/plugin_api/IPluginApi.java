package com.volcengine.zeus.plugin_api;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 可以在这里定义SDK对宿主暴露的接口
 *
 * @author xuekai
 * @date 8/31/21
 */
public interface IPluginApi {
    void startPluginActivity(Context context);

    View getPluginView(Context context);

    void addPluginView(ViewGroup viewGroup);
}
