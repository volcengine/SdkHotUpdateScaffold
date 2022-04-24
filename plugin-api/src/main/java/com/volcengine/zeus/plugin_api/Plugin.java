package com.volcengine.zeus.plugin_api;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.volcengine.zeus.Zeus;
import com.volcengine.zeus.ZeusPluginStateListener;

import java.lang.reflect.Constructor;

/**
 * 宿主对Plugin的调用都从这里触发
 *
 * @author xuekai
 * @date 8/31/21
 */
public class Plugin {
    public static IPluginApi api = null;

    public static final String pluginPkgName = BuildConfig.PLUGIN_PKG_NAME;

    public static void init(Context context) {
        // Zeus框架的初始化，需要在Application#onCreate中执行。可重复调用，内部有重复调用判断，以第一次为准。
        // 第二个参数为是否已经同意隐私协议，如果传true，表示同意隐私协议，会初始化设备id。
        // 如果传false，则不会初始化，但需要在同意隐私协议之后调用Zeus.onPrivacyAgreed();否则会影响插件下发逻辑。
        Zeus.init((Application) context.getApplicationContext(), true);
        // 触发下载目录中插件的安装（兜底方法，建议每次冷启动都调用）
        // 一般情况下载目录的文件在插件下载完成之后会立即安装，安装成功后文件会被删除。
        // 特殊情况下，插件下载完，还没来得及安装，进程被杀，此时启动后需要触发安装。
        Zeus.installFromDownloadDir();
        // 从平台拉取插件。该方法会触发网络请求，每隔一段时间会向平台请求符合规则的最新插件，并进行下载、安装。
        // 该方法可以在较晚时机触发。但是要保证可以被触发，否则插件可能无法成功下载。
        Zeus.fetchPlugin(pluginPkgName);
    }

    /**
     * 准备插件。
     * 插件需要经过安装、加载，才能使用。安装属于磁盘操作，同一个版本只需要进行一次即可。
     * 加载属于内存操作，每次冷启动都需要进行插件加载。插件安装后才可以加载，为了简化业务方的使用，可以封装类似的方法
     * 处理插件的准备工作。
     * <p>
     * 下面方法中，我们只会做插件的加载，并不会做插件安装。插件安装时机由框架控制：
     * 1.平台下发的插件会在下载完成后自动安装。
     * 2.内置插件会在Plugin初始化时异步安装。
     * 3.adb push、下载完成且没来得及安装完成进程就被杀 这两种插件会在Zeus.installFromDownloadDir();中异步安装。
     * <p>
     * 可以通过设置监听来监听插件的安装状态。
     *
     * @return 插件是否可用
     */
    public static boolean preparePlugin(Context context, OnPluginInstallListener listener) {
        if (!Zeus.isPluginInstalled(pluginPkgName)) {
            Toast.makeText(context, "插件1未安装，不可用，注册安装的回调", Toast.LENGTH_SHORT).show();
            // 插件没安装，添加一个监听，监听插件的安装状态。当安装成功之后给予回调，业务方可以在回调中重新执行相关逻辑（插件加载、插件使用等。）
            // 也可以直接丢弃本次逻辑，因为何时安装好是未知的，可能马上就装好了，也可能永远不会装好（比如网络错误导致插件下载失败等）。
            // 建议在插件未安装时，在业务层进行兜底。
            // 注意：这块代码只是一个事例代码，具体如何编写，根据业务情况来决定。
            Zeus.registerPluginStateListener(new ZeusPluginStateListener() {
                @Override
                public void onPluginStateChange(String s, int i, Object... objects) {
                    if (TextUtils.equals(s, pluginPkgName) && i == EVENT_INSTALL_SUCCESS) {
                        // 安装成功
                        listener.onInstallSuccess();
                        // 反注册
                        Zeus.unregisterPluginStateListener(this);
                    }
                }
            });
            return false;
        } else {
            if (Zeus.isPluginLoaded(pluginPkgName)) {
                // 插件已加载，可用
                return true;
            } else {
                // 插件已安装，未加载，触发加载。
                boolean loadPlugin = Zeus.loadPlugin(pluginPkgName);
                if (loadPlugin) {
                    Toast.makeText(context, "插件1加载成功，可用", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "插件1加载失败，不可用", Toast.LENGTH_SHORT).show();
                }
                return loadPlugin;
            }
        }
    }

    /**
     * 通过Spi的形式访问插件
     */
    public static IPluginApi getApi() {
        if (api == null) {
            return emptyImpl;
        }
        return api;
    }

    /**
     * 通过反射的形式访问插件
     */
    public static View callPluginByReflect(Context context) {
        boolean b = preparePlugin(context, () -> {
            // 安装成功的回调。可能在执行preparePlugin很久之后才安装成功（如下载插件耗时1min），所以也不建议在
            // 这里执行逻辑，因为时机不确定。
            // callPluginByReflect(context);
        });
        if (b) {
            // 插件准备好了，使用插件功能
            try {
                Class<?> aClass = Zeus.getPlugin(pluginPkgName).mClassLoader.loadClass("com.volcengine.zeus.plugin_impl.PluginView");
                Constructor<?> constructor = aClass.getConstructor(Context.class);
                return (View) constructor.newInstance(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static final IPluginApi emptyImpl = new IPluginApi() {
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

    public interface OnPluginInstallListener {
        void onInstallSuccess();
    }
}
