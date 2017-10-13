package bwie.com.myshop.mvp.model;

import android.content.Context;
import android.widget.Button;

import bwie.com.myshop.mvp.view.LoginLitener;

/**
 * Created by 13435 on 2017/10/12.
 */

public interface LoginModel {
    void login(Context ctx, String name, String pwd, LoginLitener litener);
}
