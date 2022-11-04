package com.volcengine.zeusscaffold;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.volcengine.zeus.demo.databinding.FragmentHostBinding;
import com.volcengine.zeusscaffold.fragmentpluginapi.FragmentPlugin;

public class HostFragment extends Fragment {
    private FragmentHostBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentHostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnToFragmentPlugin.setOnClickListener(v -> pluginFragmentPlugin());
    }

    public void pluginFragmentPlugin() {
        boolean b = FragmentPlugin.preparePlugin(requireContext(), () -> {
            // 安装成功的回调。可能在执行preparePlugin很久之后才安装成功（如下载插件耗时1min），所以也不建议在
            // 这里执行逻辑，因为时机不确定。
            // pluginFragmentPlugin(view);
        });
        if (b) {
            // 插件准备好了，使用插件功能
            FragmentPlugin.getApi().startPluginFragment(this);
        }
    }
}
