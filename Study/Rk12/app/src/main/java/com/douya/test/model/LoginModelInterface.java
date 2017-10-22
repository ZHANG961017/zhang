package com.douya.test.model;

import android.content.Context;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public interface LoginModelInterface {
    void loginModel(Context context,String loginName,String loginPwd);
    void loginRequest(Context context,String loginName,String loginPwd);
}
