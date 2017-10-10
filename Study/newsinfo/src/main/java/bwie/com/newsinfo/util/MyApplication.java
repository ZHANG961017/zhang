package bwie.com.newsinfo.util;

import com.mob.MobApplication;
import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 13435 on 2017/9/8.
 */

public class MyApplication extends MobApplication {

    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MobSDK.init(this, this.a(), this.b());
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .build();

        ImageLoader.getInstance().init(configuration);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
