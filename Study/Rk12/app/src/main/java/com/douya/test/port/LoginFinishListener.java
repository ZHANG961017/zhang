package com.douya.test.port;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public interface LoginFinishListener {
    void loginNameEmpty();
    void loginPwdEmpty();
    void loginSucceed();
    void loginFailed();
}
