package bwie.com.myshop.mvp.model;

import android.content.Context;

import bwie.com.myshop.mvp.view.RegisterListener;


/**
 * Created by 13435 on 2017/10/11.
 */

public interface RegisterModel {
    void register(Context ctx, String name, String pwd, String pwdCfm, String email, RegisterListener listener);
}
