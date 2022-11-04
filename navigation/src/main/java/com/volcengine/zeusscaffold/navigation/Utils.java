package com.volcengine.zeusscaffold.navigation;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;

/**
 * @author xuekai
 * @date 2022/11/03
 */
class Utils {
    // 确保该FragmentActivity的FragmentFactory被替换
    public static void ensureFragmentFactory(FragmentActivity activity) {
        FragmentFactory fragmentFactory = activity.getSupportFragmentManager().getFragmentFactory();
        if (!(fragmentFactory instanceof ZeusFragmentFactory)) {
            // 如果不是ZeusFragmentFactory，替换掉它。
            activity.getSupportFragmentManager().setFragmentFactory(new ZeusFragmentFactory(fragmentFactory));
        }
    }

}
