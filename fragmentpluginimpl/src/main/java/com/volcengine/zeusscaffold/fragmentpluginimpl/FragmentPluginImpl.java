package com.volcengine.zeusscaffold.fragmentpluginimpl;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.volcengine.zeusscaffold.fragmentpluginapi.IFragmentPluginApi;
import com.volcengine.zeusscaffold.navigation.NavExtensionsKt;

public class FragmentPluginImpl implements IFragmentPluginApi {
    @Override
    public void startPluginFragment(Fragment fragment) {
        NavController navController = Navigation.findNavController(fragment.requireActivity(), R.id.nav_host_fragment);
        NavExtensionsKt.safeNavigate( navController,fragment.requireActivity(), R.id.action_global_fragment_plugin_flow, null, null);
    }
}
