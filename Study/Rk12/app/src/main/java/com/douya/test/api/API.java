package com.douya.test.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public class API {
    //注册接口
    public static final String REG_PATH="http://120.27.23.105/user/reg";
    //登录接口
    public static final String LOGIN_PATH="http://120.27.23.105/user/login";
    //查询商品的接口
    public static final String SELECT_GOODS_PATH="http://120.27.23.105/product/searchProducts";
    //定义全局SharedPreferences
    public static  SharedPreferences sp;
    public static SharedPreferences.Editor mEditor;

    //初始化
    public static void init(Context context){
        sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        mEditor = sp.edit();
    }

}
