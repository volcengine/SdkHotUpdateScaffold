package com.volcengine.zeus.plugin_impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.volcengine.zeus.log.ZeusLogger;

/**
 * Create by WUzejian on 2021/6/18.
 * demo test
 */
public class PluginReceiver extends BroadcastReceiver {
    String from;

    public PluginReceiver(String from) {
        this.from = from;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ZeusLogger.d(ZeusLogger.TAG_RECEIVER, "PluginReceiver onReceive from " + from);
    }
}
