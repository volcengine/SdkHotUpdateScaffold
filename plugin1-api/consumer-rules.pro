-keep class com.volcengine.zeus.activity.IPluginActivity {
	*;
}
-keepclassmembers class * {
	*** getContext(...);
	*** getActivity(...);
	*** getResources(...);
	*** startActivity(...);
	*** startActivityForResult(...);
	*** registerReceiver(...);
	*** unregisterReceiver(...);
	*** query(...);
	*** getType(...);
	*** insert(...);
	*** delete(...);
	*** update(...);
	*** call(...);
	*** setResult(...);
	*** startService(...);
	*** stopService(...);
	*** bindService(...);
	*** unbindService(...);
	*** requestPermissions(...);
	*** getIdentifier(...);
}
-keep class com.volcengine.zeus.plugin.Plugin {
	*;
}
-keep class android.arch.lifecycle.ViewModelStoreOwner {
	*;
}
-keep class android.arch.lifecycle.LifecycleOwner {
	*;
}
-keep class android.support.v7.app.AppCompatCallback {
	*;
}
-keep class com.volcengine.zeus.plugin1_api.Plugin1 {
	*;
}
-keep class com.volcengine.zeus.ZeusApplication {
	*;
}
-keep class com.volcengine.zeus.activity.GeneratePluginAppCompatActivity {
	*;
}
-keep class android.support.v4.app.ActivityCompat$RequestPermissionsRequestCodeValidator {
	*;
}
-keep class android.support.v7.app.AppCompatActivity {
	*;
}
-keep class android.support.v4.view.KeyEventDispatcher$Component {
	*;
}
-keep class android.support.v4.app.TaskStackBuilder$SupportParentable {
	*;
}
-keep class android.support.v7.app.ActionBarDrawerToggle$DelegateProvider {
	*;
}
-keep class com.volcengine.zeus.Zeus {
	*;
}
-keep class android.support.v4.app.ActivityCompat$OnRequestPermissionsResultCallback {
	*;
}
-keep class com.volcengine.zeus.plugin1_api.IPlugin1Api {
	*;
}
-keep class com.volcengine.zeus.transform.ZeusTransformUtils {
	*;
}
