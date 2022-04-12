package com.volcengine.zeus.plugin1_api;

import com.volcengine.zeus.activity.GenerateProxyAppCompatActivity;

/**
 * @author xuekai
 * @date 2/25/22
 */
public class StubActivity {

    public static class AppCompat_main_standard extends GenerateProxyAppCompatActivity {
        @Override
        public String getPluginPkgName() {
            return Plugin1.plugin1PkgName;
        }
    }
}
