package com.volcengine.zeus.plugin_impl;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.volcengine.zeus.plugin_api.IPluginApi;
import com.volcengine.zeus.plugin_api.PluginMain;

/**
 * 插件中的实现类
 *
 * @author xuekai
 * @date 8/31/21
 */
class PluginImpl implements IPluginApi {

    @Override
    public View getPluginView(Context context) {
        return new PluginView(context);
    }

    @Override
    public void addPluginView(ViewGroup viewGroup) {
        viewGroup.addView(new PluginView(viewGroup.getContext()));
    }

    @Override
    public void startActivity(Context context, String tag) {
        context.startActivity(new Intent(context, PluginMainActivity.class));
    }

    @Override
    public void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    @Override
    public Fragment getFragment() {
        return new PluginFragment();
    }

    @Override
    public void startPluginService(Context context) {
        context.startService(new Intent(context, PluginService.class));
    }

    @Override
    public void bingPluginService(Context context) {
        context.bindService(new Intent(context, PluginService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ((PluginService.PluginBinder) service).play();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, 0);
    }

    @Override
    public void sendBroadcastByPlugin(Context context) {
        // 注册动态广播，并发送广播
        context.registerReceiver(new PluginReceiver("sendBroadcastByPlugin"), new IntentFilter(PluginMain.pluginPkgName + ".PluginReceiver"));
        context.sendBroadcast(new Intent(PluginMain.pluginPkgName + ".PluginReceiver"));
    }

    @Override
    public void insertProviderByPlugin(Context context) {
        String AUTHORITY = PluginMain.pluginPkgName + ".pluginprovider";
        ContentValues contentValues = new ContentValues();
        contentValues.put("from", "plugin");
        context.getContentResolver().insert(Uri.parse("content://" + AUTHORITY), contentValues);
    }

    @Override
    public void startPluginIntentServiceByPlugin(Context context) {
        context.startService(new Intent(context, PluginIntentService.class));
    }
}
