package com.volcengine.zeusscaffold;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apm.applog.AppLog;
import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.ZeusPluginStateListener;
import com.volcengine.zeus.demo.R;
import com.volcengine.zeus.plugin.Plugin;
import com.volcengine.zeus.plugin_api.PluginMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        initData();
        Zeus.registerPluginStateListener(new ZeusPluginStateListener() {
            @Override
            public void onPluginStateChange(String s, int i, Object... objects) {
                updatePluginState(s, i, objects);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String did = AppLog.getInstance("334386").getDid();
        if (TextUtils.isEmpty(did)) {
            did = "首次运行或者网络错误，还没有did，重启即可展示";
        }
        ((TextView) findViewById(R.id.did)).setText("did：" + did);
        if (!hasUpdate) {//没更新过，才更新
            updatePluginState(null, -1, null);
        }
    }

    boolean hasUpdate = false;

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

    private SimpleAdapter mLeftAdapter;
    private SimpleAdapter mRightAdapter;
    protected final List<ItemAction> data = new ArrayList<>();


    private void setupView() {
        RecyclerView mLeftRecyclerView = findViewById(R.id.leftRecyclerView);
        RecyclerView mRightRecyclerView = findViewById(R.id.rightRecyclerView);
        mLeftAdapter = new SimpleAdapter(item -> mRightAdapter.setData(item.subItem));
        mRightAdapter = new SimpleAdapter();
        mLeftRecyclerView.setAdapter(mLeftAdapter);
        mRightRecyclerView.setAdapter(mRightAdapter);
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void updatePluginState(String callBackPackageName, int event, Object[] objects) {
        hasUpdate = true;
        final TextView globalTips = findViewById(R.id.global_tips);
        final String s = updatePluginState(callBackPackageName, PluginMain.pluginPkgName, event, objects);
        runOnUiThread(() -> globalTips.setText(String.format("%s", s)));
    }

    private String updatePluginState(String callBackPackageName, String pkgName, int event, Object[] objects) {
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
        return format;
    }


    public void fillData() {
        // 日常开发的case
        List<ItemAction> devCase = getDevCase();
        // 调整下面顺序，可以改变左侧的顺序
        data.add(new ItemAction("日常开发case", devCase));
    }

    private List<ItemAction> getDevCase() {
        ArrayList<ItemAction> subItems = new ArrayList<>();

        subItems.add(new ItemAction("同意隐私权限，并请求插件", v -> {
            Zeus.onPrivacyAgreed();
            Zeus.fetchPlugin(PluginMain.pluginPkgName);
        }));
        subItems.add(new ItemAction("展示插件的Fragment到宿主Activity", v -> {
            boolean result = PluginMain.preparePlugin(MainActivity.this);
            if (result) {
                getSupportFragmentManager().beginTransaction().add(v.getId(), PluginMain.api.getFragment()).commit();
            }
        }));
        subItems.add(new ItemAction("添加插件View到宿主", v -> {
            View pluginView = PluginMain.callPluginByReflect(MainActivity.this);
            if (pluginView != null) {
                ((ViewGroup) v).addView(pluginView);
            }
        }));

        subItems.add(new ItemAction("加载插件，跳转到插件MainActivity", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.jump2PluginActivity(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("startPluginService By Host", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.startPluginServiceByHost(MainActivity.this);
            }
        }));
        subItems.add(new ItemAction("startPluginService By Plugin", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.startPluginServiceByPlugin(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("startPluginIntentService By Plugin", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.startPluginIntentServiceByPlugin(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("bindPluginService By Host", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.bindPluginServiceByHost(MainActivity.this);
            }
        }));
        subItems.add(new ItemAction("bindPluginService By Plugin", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.bindPluginServiceByPlugin(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("sendBroadcast By Host", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.sendBroadcastByHost(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("sendBroadcast By Plugin", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.sendBroadcastByPlugin(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("insert Provider By Host", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.insertProviderByHost(MainActivity.this);
            }
        }));

        subItems.add(new ItemAction("insert Provider By Plugin", v -> {
            boolean b = PluginMain.preparePlugin(MainActivity.this);
            if (b) {
                PluginMain.insertProviderByPlugin(MainActivity.this);
            }
        }));
        return subItems;
    }

    private void initData() {
        new Thread(() -> {
            MainActivity.this.fillData();
            MainActivity.this.runOnUiThread(() -> {
                mLeftAdapter.setData(data);
                mRightAdapter.setData(data.get(0).subItem);
            });
        }).start();
    }

}
