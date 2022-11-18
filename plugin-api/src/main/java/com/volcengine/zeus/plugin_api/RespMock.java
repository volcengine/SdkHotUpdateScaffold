package com.volcengine.zeus.plugin_api;

/**
 * @author xuekai
 * @date 2022/11/18
 */
public class RespMock {
   // 模拟有数据的case，不过这里的插件下载成功后，安装会失败，因为下载的插件包名和脚手架中定义的不一致。
   public static final String DATA = "{\"error_no\":0,\"error_msg\":\"\",\"data\":{\"id\":2713,\"sdk_pkg\":\"com.zeusdemo.forxk\",\"md5\":\"86cd838ea8096e06d17c6922a0bf3a38\",\"url\":\"https:\\/\\/lf-zeus.vemarsstatic.com\\/pkg\\/86cd838ea8096e06d17c6922a0bf3a38\\/plugin1-debug.apk\",\"backup_urls\":[\"https:\\/\\/lf-zeus.vemarsstatic.com\\/pkg\\/86cd838ea8096e06d17c6922a0bf3a38\\/plugin1-debug.apk\"],\"sdk_ver_code\":16,\"ver_size\":4122171,\"flag\":2,\"plugin_info\":\"zeus_plugin_info,test111.\"}}";
   // 模拟无数据的case
   public static final String EMPTY_DATA = "{\"error_no\":0,\"error_msg\":\"\",\"data\":null}";
}
