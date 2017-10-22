package bwie.com.myshop.myapp;

import android.app.Application;
import android.content.SharedPreferences;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/9/8 12:33
 */

public class MyApp extends Application {
    public static MyApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ZXingLibrary.initDisplayOpinion(this);
    }
    public static MyApp getInstance() {
        return mInstance;
    }
}
