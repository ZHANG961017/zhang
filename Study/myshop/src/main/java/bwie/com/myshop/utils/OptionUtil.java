package bwie.com.myshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 13435 on 2017/10/11.
 */

public class OptionUtil{

    public static final String IP="http://169.254.71.211/mobile/index.php?act=";//主体
    public static final String CLIENT="android";                                  //client常量
    public static final String LOGIN=IP+"login";                                  //登录
    public static final String REGISTER=IP+"login&op=register";                  //注册
    public static final String LOGOUT=IP+"logout";                                //注销
    public static final String KEY = "user";

    private static SharedPreferences preferences =null;

    public static SharedPreferences getSharedPreferencesInstance(Context ctx){
        if(preferences == null){
            preferences = ctx.getSharedPreferences(KEY,Context.MODE_PRIVATE);
        }
        return preferences;
    }
}
