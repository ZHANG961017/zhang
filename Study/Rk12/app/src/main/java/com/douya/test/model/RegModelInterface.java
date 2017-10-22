package com.douya.test.model;

import android.content.Context;

/**
 * Created by 杨圆圆 on 2017/10/14.
 */

public interface RegModelInterface {
    void regModel(Context context, String regName, String regPwd);
    void regRequest(Context context, String regName, String regPwd);
}
