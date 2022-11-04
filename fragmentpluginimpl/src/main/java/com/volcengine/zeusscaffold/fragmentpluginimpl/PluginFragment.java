package com.volcengine.zeusscaffold.fragmentpluginimpl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.volcengine.zeusscaffold.fragmentpluginimpl.databinding.FragmentPluginBinding;

public class PluginFragment extends Fragment {
    private FragmentPluginBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentPluginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}

