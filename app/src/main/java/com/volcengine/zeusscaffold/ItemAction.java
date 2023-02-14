package com.volcengine.zeusscaffold;

import android.view.View;

import java.util.List;

/**
 * @author xuekai
 * @date 2020/8/13
 */
public class ItemAction {
    public String title;
    public List<ItemAction> subItem;
    public Runnable action;

    public ItemAction(String title, Runnable action) {
        this.title = title;
        this.action = action;
    }

    public ItemAction(String title, List<ItemAction> subItem) {
        this.title = title;
        this.subItem = subItem;
    }

    public interface Runnable {
        void run(View v);
    }
}