package com.volcengine.zeus.plugin_impl;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.volcengine.zeus.log.ZeusLogger;

/**
 * @author xuekai
 * @date 2021/6/10
 */
public class PluginIntentService extends IntentService {
    private static final String TAG = "LivePluginService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public PluginIntentService() {
        super("PluginIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        for (int i = 0; i < 10; i++) {
            ZeusLogger.d(ZeusLogger.TAG_SERVICE, "PluginIntentService onHandleIntent. count = " + i);
            SystemClock.sleep(1000);
        }
    }
}
