package com.volcengine.zeus.plugin1_impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author xuekai
 * @date 3/31/22
 */
public class PluginView extends LinearLayout {
    public PluginView(Context context) {
        super(context);
        init();
    }


    public PluginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundColor(0xffff0000);
        TextView textView = new TextView(this.getContext());
        textView.setText("插件的自定义View");
        addView(textView);
    }
}
