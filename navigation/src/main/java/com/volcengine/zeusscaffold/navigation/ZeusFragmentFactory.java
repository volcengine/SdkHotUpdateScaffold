package com.volcengine.zeusscaffold.navigation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

/**
 * @author xuekai
 * @date 2022/11/03
 */
public class ZeusFragmentFactory extends FragmentFactory {
    // 维护Fragment和Context(通过Context获取Classloader)的关系
    public static final SimpleArrayMap<String, Context>
            fragment2Plugin = new SimpleArrayMap<>();

    FragmentFactory originFactory;

    public ZeusFragmentFactory(FragmentFactory originFactory) {
        this.originFactory = originFactory;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
        try {
            // 优先使用原始的instantiate
            return originFactory.instantiate(classLoader, className);
        } catch (Throwable t) {
        }
        // 如果上面catch了，这里通过插件classloader去instantiate class。（注意，这里fragment.getContext()会被Zeus）
        return Fragment.instantiate(fragment2Plugin.get(className), className, null);


        // 这里加载之后，其实可以还原。不过不还原应该也没啥问题，最多就是被别人二次替换后会重复替换。
    }
}
