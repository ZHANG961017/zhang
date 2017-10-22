package com.douya.test.port;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public interface RegFinishListener {
    void regNameEmpty();
    void regPwdEmpty();
    void regSucceed();
    void regFailed();
}
