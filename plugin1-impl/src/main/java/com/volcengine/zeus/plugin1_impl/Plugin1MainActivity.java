package com.volcengine.zeus.plugin1_impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.plugin1_api.Plugin1;

/**
 * @author xuekai
 * @date 2021/6/1
 */
public class Plugin1MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin1_main);
        ((TextView) findViewById(R.id.textView)).setText("插件1 版本号：" + Zeus.getPlugin(Plugin1.plugin1PkgName).getVersion());
    }
}
