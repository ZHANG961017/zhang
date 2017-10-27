package com.bwie.text.myapp;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by 13435 on 2017/10/26.
 */

public class MyApplication extends Application {

    public static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        String path = Environment.getExternalStorageDirectory()+"/zhang";
        File file = new File(path);
        ImageLoaderConfiguration ilcf = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(100)
                .threadPoolSize(5)
                .memoryCacheExtraOptions(200,200)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50*1024*1024)
                .build();
        ImageLoader.getInstance().init(ilcf);
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
