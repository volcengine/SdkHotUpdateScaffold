package com.volcengine.zeus.plugin1_api;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.volcengine.zeus.Zeus;

import java.lang.reflect.Constructor;

/**
 * 宿主对Plugin1的调用都从这里触发
 *
 * @author xuekai
 * @date 8/31/21
 */
public class Plugin1 {
    public static IPlugin1Api api = null;

    public static final String plugin1PkgName = BuildConfig.PLUGIN_PKG_NAME;

    public static void init(Context context) {

        Zeus.init((Application) context.getApplicationContext(), true);
        Zeus.installFromDownloadDir();
        Zeus.fetchPlugin(plugin1PkgName);
    }

    public static boolean preparePlugin(Context context) {
        if (!Zeus.isPluginInstalled(plugin1PkgName)) {
            Toast.makeText(context, "插件1未安装", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (Zeus.isPluginLoaded(plugin1PkgName)) {
                // ok
                return true;
            } else {
                boolean loadPlugin = Zeus.loadPlugin(plugin1PkgName);
                if (loadPlugin) {
                    Toast.makeText(context, "插件1加载成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "插件1加载失败", Toast.LENGTH_SHORT).show();
                }
                return loadPlugin;
            }
        }
    }

    /**
     * 通过Spi的形式访问插件
     */
    public static IPlugin1Api getApi() {
        if (api == null) {
            return emptyImpl;
        }
        return api;
    }

    /**
     * 通过反射的形式访问插件
     */
    public static View callPluginByReflect(Context context) {
        boolean b = preparePlugin(context);
        if (b) {
            try {
                Class<?> aClass = Zeus.getPlugin(plugin1PkgName).mClassLoader.loadClass("com.volcengine.zeus.plugin1_impl.PluginView");
                Constructor<?> constructor = aClass.getConstructor(Context.class);
                return (View) constructor.newInstance(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static final IPlugin1Api emptyImpl = new IPlugin1Api() {
        @Override
        public void startPluginActivity(Context context) {
            Toast.makeText(context, "插件未加载", Toast.LENGTH_SHORT).show();
        }

        @Override
        public View getPluginView(Context context) {
            Toast.makeText(context, "插件未加载", Toast.LENGTH_SHORT).show();
            return null;
        }

        @Override
        public void addPluginView(ViewGroup viewGroup) {
            Toast.makeText(viewGroup.getContext(), "插件未加载", Toast.LENGTH_SHORT).show();
        }
    };
}
