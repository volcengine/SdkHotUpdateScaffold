package com.volcengine.zeus.plugin_impl;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.plugin_api.PluginMain;

/**
 * @author xuekai
 * @date 2021/6/1
 */
public class PluginMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_main);
        ((TextView) findViewById(R.id.textView)).setText("插件1 版本号：" + Zeus.getPlugin(PluginMain.pluginPkgName).getVersion());
    }
}
