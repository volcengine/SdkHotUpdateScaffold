package com.volcengine.zeus.plugin_impl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.volcengine.zeus.log.ZeusLogger;


/**
 * @author xuekai
 * @date 2021/6/10
 */
public class PluginService extends Service {
    private static final String TAG = "Zeus/PluginService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ZeusLogger.d(TAG, "PluginService onBind.");
        return new PluginBinder();
    }

    static class PluginBinder extends Binder {
        public void play() {
            ZeusLogger.d(TAG, "PluginBinder play.");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ZeusLogger.d(TAG, "PluginService onStartCommand.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ZeusLogger.d(TAG, "PluginService onDestroy.");
    }
}
