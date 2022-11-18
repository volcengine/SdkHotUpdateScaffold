package com.volcengine.zeusscaffold;


import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.ZeusPluginStateListener;
import com.volcengine.zeus.demo.R;
import com.volcengine.zeus.plugin.Plugin;
import com.volcengine.zeus.plugin_api.PluginMain;
import com.volcengine.zeus.plugin_api.RespMock;

import java.util.Locale;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Zeus.registerPluginStateListener(new ZeusPluginStateListener() {
            @Override
            public void onPluginStateChange(String pluginPkg, int event, Object... objects) {
                updatePluginState(pluginPkg, event, objects);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasUpdate) {//没更新过，才更新
            updatePluginState(null, -1, null);
        }
    }

    boolean hasUpdate = false;

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void updatePluginState(String callBackPackageName, int event, Object[] objects) {
        hasUpdate = true;
        updatePluginState(callBackPackageName, PluginMain.pluginPkgName, R.id.pluginState, event, objects);
    }


    private void updatePluginState(String callBackPackageName, String pkgName, int id, int event, Object[] objects) {
        Plugin plugin = Zeus.getPlugin(pkgName);
        String format = String.format(Locale.getDefault(), "%s %s  版本号：%d", pkgName, getLifeCycle(plugin.getLifeCycle()), plugin.getVersion());
        if (TextUtils.equals(callBackPackageName, pkgName)) {
            switch (event) {
                case ZeusPluginStateListener.EVENT_DOWNLOAD_START:
                    format = format + "\n" + "实时状态:下载开始";
                    break;
                case ZeusPluginStateListener.EVENT_DOWNLOAD_PROGRESS:
                    format = format + "\n" + "实时状态:下载中" + objects[0];
                    ;
                    break;
                case ZeusPluginStateListener.EVENT_DOWNLOAD_SUCCESS:
                    format = format + "\n" + "实时状态:下载成功";
                    break;
                case ZeusPluginStateListener.EVENT_DOWNLOAD_FAILED:
                    format = format + "\n" + "实时状态:下载失败" + objects[0];
                    break;
                case ZeusPluginStateListener.EVENT_INSTALL_START:
                    format = format + "\n" + "实时状态:开始安装";
                    break;
                case ZeusPluginStateListener.EVENT_INSTALL_SUCCESS:
                    format = format + "\n" + "实时状态:安装成功";
                    break;
                case ZeusPluginStateListener.EVENT_INSTALL_FAILED:
                    format = format + "\n" + "实时状态:安装失败" + objects[0];
                    break;
                case ZeusPluginStateListener.EVENT_LOAD_START:
                    format = format + "\n" + "实时状态:开始加载";
                    break;
                case ZeusPluginStateListener.EVENT_LOAD_FAILED:
                    format = format + "\n" + "实时状态:加载失败";
                    break;
                case ZeusPluginStateListener.EVENT_LOAD_SUCCESS:
                    format = format + "\n" + "实时状态:加载成功";
                    break;
                default:
                    break;
            }
        }
        String finalFormat = format;
        runOnUiThread(() -> ((TextView) findViewById(id)).setText(finalFormat));
    }

    private String getLifeCycle(int lifeCycle) {
        switch (lifeCycle) {
            case 1:
                return "未安装";
            case 2:
                return "安装成功";
            case 3:
                return "加载成功";
        }
        return "异常";
    }

    public void pluginPlugin(View view) {
        boolean b = PluginMain.preparePlugin(this, () -> {
            // 安装成功的回调。可能在执行preparePlugin很久之后才安装成功（如下载插件耗时1min），所以也不建议在
            // 这里执行逻辑，因为时机不确定。
            // pluginPlugin(view);
        });
        if (b) {
            // 插件准备好了，使用插件功能
            PluginMain.getApi().startPluginActivity(this);
        }
    }

    public void addPluginView(View view) {
        View pluginView = PluginMain.callPluginByReflect(this);
        if (pluginView != null) {
            ((ViewGroup) findViewById(R.id.view_group)).addView(pluginView);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void requestPlugin(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                // 通过网络请求插件
//                PluginMain.injectData(RespMock.DATA);
                PluginMain.injectData(RespMock.EMPTY_DATA);
            }
        }).start();
    }
}
